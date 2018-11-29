
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
import domain.Application;
import domain.HandyWorker;

@Service
@Transactional
public class HandyWorkerService {

	@Autowired
	private HandyWorkerRepository	repo;
	@Autowired
	private UserAccountService		userAccountService;
	private UserAccount				account;


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

		return this.repo.save(worker);

	}

	public HandyWorker findOne(final int id) {
		Assert.isTrue(id != 0);

		return this.repo.findOne(id);
	}

	public Collection<HandyWorker> findWorkerMoreAverage() {
		this.account = LoginService.getPrincipal();

		Assert.isTrue(this.account.getAuthorities().iterator().next().getAuthority().equals(Authority.ADMIN));

		return this.repo.findHandyWorkerMoreAverage();
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
}
