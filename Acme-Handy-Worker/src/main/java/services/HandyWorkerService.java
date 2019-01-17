
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.HandyWorkerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import utilities.AuthenticationUtility;
import domain.Actor;
import domain.Application;
import domain.Finder;
import domain.FixUpTask;
import domain.HandyWorker;

@Service
@Transactional
public class HandyWorkerService {

	@Autowired
	private HandyWorkerRepository	repo;
	@Autowired
	private UserAccountService		userAccountService;
	@Autowired
	private FixUpTaskService		futService;
	@Autowired
	private BoxService				boxService;
	@Autowired
	private FinderService			finderService;
	private UserAccount				account;
	@Autowired
	private ApplicationService		applicationService;


	public HandyWorker create() {
		final HandyWorker res = new HandyWorker();
		final UserAccount a = this.userAccountService.create();

		final Authority auth = new Authority();
		auth.setAuthority(Authority.HANDYWORKER);
		a.addAuthority(auth);
		res.setAccount(a);
		res.setApplications(new ArrayList<Application>());

		return res;
	}

	public HandyWorker save(final HandyWorker worker) {
		Assert.notNull(worker);
		HandyWorker result;
		if (worker.getId() == 0) {
			final UserAccount account = worker.getAccount();
			final UserAccount savedAccount = this.userAccountService.save(account);
			worker.setAccount(savedAccount);
			result = this.repo.save(worker);
			this.boxService.initializeDefaultBoxes(result);
			final Finder finder = this.finderService.create(result);
			this.finderService.save(finder);
		} else
			result = this.repo.save(worker);

		return result;
	}

	public HandyWorker findOne(final int id) {
		Assert.isTrue(id != 0);

		return this.repo.findOne(id);
	}

	public Collection<HandyWorker> findWorkerMoreAverage() {
		this.account = LoginService.getPrincipal();

		Assert.isTrue(this.account.getAuthorities().iterator().next().getAuthority().equals(Authority.ADMIN));
		Collection<HandyWorker> handyWorkers = this.repo.findHandyWorkerMoreAverage();
		if (this.applicationService.findAll().size() == 0)
			handyWorkers = new ArrayList<HandyWorker>();
		return handyWorkers;
	}

	public Collection<HandyWorker> findTop3HandyWorkers() {

		Assert.isTrue(AuthenticationUtility.checkAuthority(Authority.ADMIN));

		final Collection<HandyWorker> workers = this.repo.findAllHandyWorkerComplains();
		final Collection<HandyWorker> res = new ArrayList<>();

		int i = 0;
		for (final HandyWorker h : workers) {
			if (i > 2)
				break;
			res.add(h);
			i++;
		}

		return res;
	}

	public HandyWorker findHandyWorkerFromFixUpTask(final Integer id) {
		final FixUpTask f = this.futService.findOne(id);
		HandyWorker res = null;
		for (final Application a : f.getApplications())
			if (a.getStatus().equals("ACCEPTED"))
				res = a.getHandyWorker();
		return res;
	}

	public HandyWorker actorToHandy(final Actor a) {
		final HandyWorker res = new HandyWorker();
		res.setAccount(a.getAccount());
		res.setAddress(a.getAddress());
		res.setApplications(new ArrayList<Application>());
		res.setBanned(a.getBanned());
		res.setEmail(a.getEmail());
		res.setId(a.getId());
		res.setIsSuspicious(a.getIsSuspicious());
		res.setMake(a.getName() + " " + a.getSurname());
		res.setMiddleName(a.getMiddleName());
		res.setName(a.getName());
		res.setPhone(a.getPhone());
		res.setPhotoURL(a.getPhotoURL());
		res.setScore(0d);
		res.setSurname(a.getSurname());
		res.setVersion(a.getVersion());
		res.setIsSuspicious(false);

		return res;
	}
}
