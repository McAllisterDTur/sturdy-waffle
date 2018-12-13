
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.TutorialRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import utilities.AuthenticationUtility;
import domain.HandyWorker;
import domain.Section;
import domain.Sponsorship;
import domain.Tutorial;

@Service
@Transactional
public class TutorialService {

	@Autowired
	private TutorialRepository	tutorialRepository;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private SpamService			spamService;


	/**
	 * HandyWorker creates a empty tutorial
	 * 
	 * @return tutorial
	 */
	public Tutorial create() {
		Assert.isTrue(AuthenticationUtility.checkAuthority(Authority.HANDYWORKER));

		final Tutorial tutorial = new Tutorial();
		//We do empty collections
		final Collection<String> photos = new ArrayList<>();
		final Collection<Section> sections = new ArrayList<>();
		tutorial.setPhotoURL(photos);
		tutorial.setSections(sections);
		return tutorial;
	}

	/**
	 * Gets all tutorial from DB
	 * 
	 * @return Collection<Tutorial>
	 */
	public Collection<Tutorial> findAll() {
		final Collection<Tutorial> result = this.tutorialRepository.findAll();
		return result;
	}

	/**
	 * Gets all tutorial of a HandyWorker from DB
	 * 
	 * @param id
	 * @return Collection<Tutorial>
	 */
	public Collection<Tutorial> findAllFromHandyworker(final int id) {
		final Collection<Tutorial> result = this.tutorialRepository.findAllFromHandyworker(id);
		return result;
	}

	/**
	 * Gets a tutorial from DB
	 * with an especific id
	 * 
	 * @param id
	 * @return Tutorial
	 */
	public Tutorial findOne(final int id) {
		final Tutorial result = this.tutorialRepository.findOne(id);
		return result;
	}

	/**
	 * HandyWorker saves a tutorial to DB
	 * 
	 * @param tutorial
	 * @return Tutorial
	 */
	public Tutorial save(final Tutorial tutorial) {
		Assert.notNull(tutorial);
		//Is a handyworker?
		Assert.isTrue(AuthenticationUtility.checkAuthority(Authority.HANDYWORKER));
		final UserAccount ua = LoginService.getPrincipal();
		Tutorial result = null;
		final HandyWorker worker = (HandyWorker) this.actorService.findByUserAccountId(ua.getId());
		if (tutorial.getId() != 0) {
			//If the tutorial isn't new, we look if the tutorial belong the 
			//login user and the tutorial id from bd belong to the loggin user
			final Tutorial ac = this.findOne(tutorial.getId());
			Assert.isTrue(tutorial.getWorker().getAccount().equals(ua));
			Assert.isTrue(ac.getWorker().getAccount().equals(tutorial.getWorker().getAccount()));
			//we set the date
			final Date lastTimeUpdated = new Date();
			tutorial.setLastTimeUpdated(lastTimeUpdated);
			this.spamService.isSpam(worker, tutorial.getSummary());
			this.spamService.isSpam(worker, tutorial.getTitle());
			result = this.tutorialRepository.save(tutorial);
		} else {
			//If the tutorial is new, we set the creator and the date
			final Date lastTimeUpdated = new Date();
			this.spamService.isSpam(worker, tutorial.getSummary());
			this.spamService.isSpam(worker, tutorial.getTitle());
			tutorial.setLastTimeUpdated(lastTimeUpdated);
			tutorial.setWorker(worker);
			result = this.tutorialRepository.save(tutorial);
		}
		return result;
	}

	/**
	 * Sponsor make a sponsorship for a tutorial
	 * 
	 * @param sponsorship
	 * @return tutorial
	 */
	public Tutorial sponsorshipToTutorial(final Sponsorship sponsorship) {
		Assert.notNull(sponsorship.getTutorials());
		Assert.isTrue(AuthenticationUtility.checkAuthority(Authority.SPONSOR));
		final UserAccount ua = LoginService.getPrincipal();
		Assert.isTrue(sponsorship.getSponsor().getAccount().equals(ua));
		final Tutorial tutorial = this.findOne(sponsorship.getTutorials().getId());
		final Collection<Sponsorship> newSpon = new ArrayList<>(tutorial.getSponsorships());
		newSpon.add(sponsorship);
		tutorial.setSponsorships(newSpon);
		final Tutorial result = this.tutorialRepository.save(tutorial);
		Assert.notNull(result);
		return result;
	}

	/**
	 * HandyWorker deletes a tutorial from DB
	 * 
	 * @param tutorial
	 */
	public void delete(final Tutorial tutorial) {
		Assert.isTrue(AuthenticationUtility.checkAuthority(Authority.HANDYWORKER));
		final UserAccount ua = LoginService.getPrincipal();
		final Tutorial ac = this.findOne(tutorial.getId());
		Assert.isTrue(ac.getWorker().getAccount().equals(tutorial.getWorker().getAccount()));
		Assert.isTrue(tutorial.getWorker().getAccount().equals(ua));
		this.tutorialRepository.delete(tutorial);
	}

}
