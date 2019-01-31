
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.FinderRepository;
import security.Authority;
import security.LoginService;
import utilities.AuthenticationUtility;
import domain.Actor;
import domain.Configuration;
import domain.Finder;
import domain.FixUpTask;
import domain.HandyWorker;

@Service
@Transactional
public class FinderService {

	@Autowired
	private FinderRepository		finderRepository;
	@Autowired
	private ActorService			aService;
	@Autowired
	private ConfigurationService	cService;
	@Autowired
	private FixUpTaskService		taskService;


	public Finder create(final Actor actor) {
		final HandyWorker handy = (HandyWorker) actor;
		final Finder f = new Finder();
		f.setWorker(handy);
		f.setFixUpTask(new ArrayList<FixUpTask>());
		return f;
	}
	public Collection<Finder> findAll() {
		return this.finderRepository.findAll();
	}

	public Finder findOne(final int id) {
		Finder f = this.finderRepository.findOne(id);
		this.checkOutdated(f);
		f = this.finderRepository.findOne(id);
		return f;
	}

	public Finder save(final Finder f) {
		Finder result;
		Assert.isTrue(f.getMinPrice() > 0.0 && f.getMaxPrice() > 0.0);
		if (f.getId() != 0) {
			Assert.isTrue(AuthenticationUtility.checkAuthority(Authority.HANDYWORKER));
			final HandyWorker logged = (HandyWorker) this.aService.findByUserAccountId(LoginService.getPrincipal().getId());
			final Finder fh = this.findByHandyWorker(logged);
			Assert.isTrue(f.getWorker().equals(logged));
			Assert.isTrue(f.getId() == fh.getId());

			final Configuration configuration = this.cService.findAll().iterator().next();

			List<FixUpTask> futSearched = (List<FixUpTask>) this.taskService.findByFilter(f.getKeyWord(), f.getCategory(), f.getWarranty(), f.getMinPrice(), f.getMaxPrice(), f.getStartDate(), f.getEndDate());
			if (futSearched.size() > configuration.getFinderResults())
				futSearched = futSearched.subList(0, configuration.getFinderResults());
			f.setCacheUpdate(new Date());
			f.setFixUpTask(futSearched);
		}
		result = this.finderRepository.save(f);
		return result;

	}
	private Finder findByHandyWorker(final HandyWorker h) {
		return this.finderRepository.findByHandyWorker(h.getId());
	}

	public Finder findByLoggedHandyWorker() {
		Assert.isTrue(AuthenticationUtility.checkAuthority(Authority.HANDYWORKER));
		final HandyWorker logged = (HandyWorker) this.aService.findByUserAccountId(LoginService.getPrincipal().getId());
		Finder f = this.finderRepository.findByHandyWorker(logged.getId());
		this.checkOutdated(f);
		f = this.finderRepository.findByHandyWorker(logged.getId());
		return f;
	}

	private void checkOutdated(final Finder f) {
		final Date now = new Date();
		final Date finderDate = f.getCacheUpdate();
		final long diff = now.getTime() - finderDate.getTime();
		if (diff >= (this.cService.findAll().iterator().next().getCacheTime() * 60 * 60 * 1000)) {
			f.setFixUpTask(new ArrayList<FixUpTask>());
			this.finderRepository.save(f);
		}

	}

}
