
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
import domain.Section;
import domain.Tutorial;

@Service
@Transactional
public class TutorialService {

	@Autowired
	private TutorialRepository	tutorialRepository;


	/**
	 * HandyWorker creates a empty tutorial
	 * 
	 * @return tutorial
	 */
	public Tutorial create() {
		Assert.isTrue(AuthenticationUtility.checkAuthority(Authority.HANDYWORKER));

		final Tutorial tutorial = new Tutorial();
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
		Assert.isTrue(AuthenticationUtility.checkAuthority(Authority.HANDYWORKER));
		final UserAccount ua = LoginService.getPrincipal();
		Assert.isTrue(tutorial.getWorker().getAccount().equals(ua));
		Tutorial result = null;
		//TODO suponemos que las secciones son creadas con una llamada en el controller
		// y se insertan en el tutorial dentro del controller
		if (tutorial.getId() != 0) {
			final Tutorial ac = this.findOne(tutorial.getId());
			Assert.isTrue(ac.getWorker().getAccount().equals(tutorial.getWorker().getAccount()));
			final Date lastTimeUpdated = new Date();
			tutorial.setLastTimeUpdated(lastTimeUpdated);
			result = this.tutorialRepository.save(tutorial);

		} else {
			final Date lastTimeUpdated = new Date();
			System.out.println(lastTimeUpdated);
			tutorial.setLastTimeUpdated(lastTimeUpdated);
			result = this.tutorialRepository.save(tutorial);
		}
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
