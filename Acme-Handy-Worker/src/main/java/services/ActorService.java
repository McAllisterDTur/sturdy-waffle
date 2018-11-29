
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import utilities.AuthenticationUtility;
import domain.Actor;

@Service
@Transactional
public class ActorService {

	@Autowired
	private ActorRepository	actorRepository;


	/**
	 * Creates a new empty actor
	 * 
	 * @return actor
	 */
	public Actor create() {
		final Actor a = new Actor();
		return a;
	}

	/**
	 * Saves a new actor in the DB or update it
	 * If an admin is updating other actor, only can ban
	 * 
	 * @param actor
	 * @return actor
	 */
	public Actor save(final Actor actor) {
		Assert.notNull(actor);
		Actor result = null;
		if (actor.getId() != 0) {
			final UserAccount ua = LoginService.getPrincipal();
			final Actor ac = this.actorRepository.findOne(actor.getId());
			if (AuthenticationUtility.checkAuthority(Authority.ADMIN)) {
				if (ac.getAccount().equals(actor.getAccount()) && actor.getAccount().equals(ua))
					result = this.actorRepository.save(actor);
				else {
					ac.setBanned(actor.getBanned());
					result = this.actorRepository.save(ac);
				}
			} else {
				Assert.isTrue(ac.getAccount().equals(actor.getAccount()));
				Assert.isTrue(actor.getAccount().equals(ua));
				actor.setBanned(false);
				result = this.actorRepository.save(actor);
			}
		} else {
			//TODO Por ahora, por decisión de grupo, la useracount se agrega
			//en el controller
			actor.setBanned(false);
			result = this.actorRepository.save(actor);
		}
		return result;
	}

	/**
	 * Get all actors from db
	 * 
	 * @return actors
	 */
	public Collection<Actor> findAll() {
		//TODO Posee más lógica de negocio?
		final UserAccount ua = LoginService.getPrincipal();
		Assert.notNull(ua);
		final Collection<Actor> result = this.actorRepository.findAll();
		return result;
	}

	/**
	 * Find an actor by id in the db
	 * 
	 * @param actorId
	 * @return actor
	 */
	public Actor findOne(final int actorId) {
		//TODO Posee más lógica de negocio?
		final UserAccount ua = LoginService.getPrincipal();
		Assert.notNull(ua);
		final Actor result = this.actorRepository.findOne(actorId);
		return result;
	}

	/**
	 * Finds an actor by his/her user account
	 * 
	 * @param userAccount
	 * @return an actor
	 */
	public Actor findByUserAccountId(final int accountId) {

		return this.actorRepository.findByUserAccountId(accountId);
	}
}
