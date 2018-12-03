
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SectionRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import utilities.AuthenticationUtility;
import domain.HandyWorker;
import domain.Section;
import domain.Tutorial;

@Service
@Transactional
public class SectionService {

	@Autowired
	private SectionRepository	sectionRepository;

	@Autowired
	private SpamService			spamService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private TutorialService		tutorialRepository;


	/**
	 * HandyWorker creates a empty section
	 * 
	 * @return section
	 */
	public Section create(final Tutorial tutorial) {
		Assert.isTrue(AuthenticationUtility.checkAuthority(Authority.HANDYWORKER));
		final UserAccount ua = LoginService.getPrincipal();
		Assert.isTrue(tutorial.getWorker().getAccount().equals(ua));
		final Section section = new Section();
		final Collection<String> photos = new ArrayList<>();
		section.setPhotoURL(photos);
		section.setTutorial(tutorial);
		return section;
	}

	/**
	 * Gets all sections of a tutorial from DB
	 * 
	 * @param id
	 * @return Collection<Section>
	 */
	public Collection<Section> findAllFromTutorial(final int id) {
		final Collection<Section> result = this.sectionRepository.findAllFromTutorial(id);
		return result;
	}

	/**
	 * Gets a section from DB
	 * with an especific id
	 * 
	 * @param id
	 * @return Section
	 */
	public Section findOne(final int id) {
		final Section result = this.sectionRepository.findOne(id);
		return result;
	}

	/**
	 * HandyWorker saves a section to DB
	 * 
	 * @param Section
	 * @return Section
	 */
	public Section save(final Section section) {
		//The section has a tutorial, when enter here
		Assert.notNull(section);
		Assert.isTrue(AuthenticationUtility.checkAuthority(Authority.HANDYWORKER));
		final UserAccount ua = LoginService.getPrincipal();
		final HandyWorker worker = (HandyWorker) this.actorService.findByUserAccountId(ua.getId());
		Assert.isTrue(section.getTutorial().getWorker().equals(worker));
		Section result = null;
		if (section.getId() != 0) {
			final Section ac = this.findOne(section.getId());
			Assert.isTrue(ac.getTutorial().getWorker().getAccount().equals(section.getTutorial().getWorker().getAccount()));
			this.spamService.isSpam(worker, section.getText());
			this.spamService.isSpam(worker, section.getTitle());
			result = this.sectionRepository.save(section);
			final Tutorial tutorial = this.tutorialRepository.findOne(section.getTutorial().getId());
			final Collection<Section> newSect = new ArrayList<>(tutorial.getSections());
			newSect.add(section);
			tutorial.setSections(newSect);
			final Tutorial res = this.tutorialRepository.save(tutorial);
			Assert.notNull(res);
		} else {
			this.spamService.isSpam(worker, section.getText());
			this.spamService.isSpam(worker, section.getTitle());
			result = this.sectionRepository.save(section);
		}
		return result;
	}

	/**
	 * HandyWorker deletes a section from DB
	 * 
	 * @param Section
	 */
	public void delete(final Section section) {
		Assert.isTrue(AuthenticationUtility.checkAuthority(Authority.HANDYWORKER));
		final UserAccount ua = LoginService.getPrincipal();
		final Section ac = this.findOne(section.getId());
		Assert.isTrue(ac.getTutorial().getWorker().getAccount().equals(section.getTutorial().getWorker().getAccount()));
		Assert.isTrue(section.getTutorial().getWorker().getAccount().equals(ua));
		this.sectionRepository.delete(section);
	}
}
