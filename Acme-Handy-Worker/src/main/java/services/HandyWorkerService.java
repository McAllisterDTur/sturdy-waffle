
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
import domain.Application;
import domain.HandyWorker;

@Service
@Transactional
public class HandyWorkerService {

	@Autowired
	private HandyWorkerRepository	repo;
	private UserAccount				account;


	public HandyWorker create() {
		final HandyWorker res = new HandyWorker();

		res.setApplications(new ArrayList<Application>());

		return res;
	}

	public HandyWorker save(final HandyWorker worker) {
		Assert.notNull(worker);

		if (worker.getId() <= 0) {
			worker.setScore(0.0);
			if (worker.getMake().equals(null) || worker.getMake().equals(""))
				worker.setMake(worker.getName() + worker.getMiddleName() + worker.getSurname());
		}

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
}
