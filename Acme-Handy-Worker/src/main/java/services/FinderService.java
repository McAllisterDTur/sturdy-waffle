
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
		final Finder f = this.finderRepository.findOne(id);
		this.checkOutdated(f);
		return f;
	}

	public Finder save(final Finder f) {
		Finder result;
		Assert.isTrue(AuthenticationUtility.checkAuthority(Authority.HANDYWORKER));
		final HandyWorker logged = (HandyWorker) this.aService.findByUserAccountId(LoginService.getPrincipal().getId());
		if (f.getId() == 0) {
			final Finder fh = this.findByHandyWorker(logged);
			Assert.isNull(fh);
		} else {
			final Finder fh = this.findByHandyWorker(logged);
			Assert.isTrue(f.getWorker().equals(logged));
			Assert.isTrue(f.getId() == fh.getId());
		}

		final Configuration configuration = this.cService.findAll().iterator().next();

		List<FixUpTask> futSearched = (List) this.taskService.findByFilter(f.getKeyWord(), f.getCategory(), f.getWarranty(), f.getMinPrice(), f.getMaxPrice(), f.getStartDate(), f.getEndDate());
		if (futSearched.size() > configuration.getFinderResults())
			futSearched = futSearched.subList(0, configuration.getFinderResults());
		f.setCacheUpdate(new Date());
		f.setFixUpTask(futSearched);
		f.setWorker(logged);
		result = this.finderRepository.save(f);
		return result;

	}
	private Finder findByHandyWorker(final HandyWorker h) {
		return this.finderRepository.findByHandyWorker(h.getId());

	}

	public Finder findByLoggedHandyWorker() {
		Assert.isTrue(AuthenticationUtility.checkAuthority(Authority.HANDYWORKER));
		final HandyWorker logged = (HandyWorker) this.aService.findByUserAccountId(LoginService.getPrincipal().getId());
		final Finder f = this.finderRepository.findByHandyWorker(logged.getId());
		if (f != null)
			this.checkOutdated(f);
		return f;
	}

	private void checkOutdated(final Finder f) {
		//		final Date now = new Date();
		//		final Configuration c = this.cService.findAll().iterator().next();
		//		final Date whenToUpdate = now;
		//		if (now.getHours() + c.getCacheTime() >= 24) {
		//			whenToUpdate.setHours(now.getHours() + c.getCacheTime() - 24);
		//			whenToUpdate.setDate(now.getDate() + 1);
		//		} else
		//			whenToUpdate.setHours(now.getHours() + c.getCacheTime());
		//		if (whenToUpdate.compareTo(now) > 0)
		//			this.finderRepository.save(f);
		final Date now = new Date();
		final Date finderDate = f.getCacheUpdate();
		final long diff = now.getTime() - finderDate.getTime();
		if (diff >= (this.cService.findAll().iterator().next().getCacheTime() * 60 * 60 * 1000)) {
			f.setFixUpTask(new ArrayList<FixUpTask>());
			this.finderRepository.save(f);
		}

	}

}
