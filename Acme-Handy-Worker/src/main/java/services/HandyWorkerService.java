
package services;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.HandyWorkerRepository;
import security.UserAccount;
import domain.HandyWorker;

@Service
@Transactional
public class HandyWorkerService {

	private HandyWorkerRepository	repo;
	private UserAccount				account;


	public HandyWorker create() {
		return new HandyWorker();
	}

	public HandyWorker save(final HandyWorker worker) {
		Assert.notNull(worker);

		return this.repo.save(worker);

	}

}
