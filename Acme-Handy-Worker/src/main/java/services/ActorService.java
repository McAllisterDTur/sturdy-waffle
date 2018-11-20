
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.ActorRepository;
import domain.Actor;

@Service
@Transactional
public class ActorService {

	@Autowired
	private ActorRepository	actorRepository;


	/**
	 * Create a new empty actor
	 * 
	 * @return actor
	 */
	public Actor create() {
		final Actor a = new Actor();
		return a;
	}

	/**
	 * Save a new actor in the DB or update it
	 * 
	 * @return
	 */
	public Actor save(final Actor a) {
		final Actor save = this.actorRepository.save(a);
		return save;
	}

}
