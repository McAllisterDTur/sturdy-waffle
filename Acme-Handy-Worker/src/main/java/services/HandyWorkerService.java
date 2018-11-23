
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.HandyWorkerRepository;
import domain.HandyWorker;

@Service
@Transactional
public class HandyWorkerService {

	@Autowired
	private HandyWorkerRepository	repo;


	public HandyWorker create() {
		return new HandyWorker();
	}

	public HandyWorker save(final HandyWorker worker) {
		Assert.notNull(worker);

		return this.repo.save(worker);

	}

}
