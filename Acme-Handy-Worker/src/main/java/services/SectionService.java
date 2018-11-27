
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
import domain.Section;

@Service
@Transactional
public class SectionService {

	@Autowired
	private SectionRepository	sectionRepository;


	/**
	 * HandyWorker creates a empty section
	 * 
	 * @return section
	 */
	public Section create() {
		Assert.isTrue(AuthenticationUtility.checkAuthority(Authority.HANDYWORKER));
		final Section section = new Section();
		final Collection<String> photos = new ArrayList<>();
		section.setPhotoURL(photos);
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
		Assert.notNull(section);
		Assert.isTrue(AuthenticationUtility.checkAuthority(Authority.HANDYWORKER));
		final UserAccount ua = LoginService.getPrincipal();
		Assert.isTrue(section.getTutorial().getWorker().getAccount().equals(ua));
		Section result = null;
		//TODO suponemos que las secciones son creadas con una llamada en el controller
		// y se insertan en el tutorial dentro del controller
		if (section.getId() != 0) {
			final Section ac = this.findOne(section.getId());
			Assert.isTrue(ac.getTutorial().getWorker().getAccount().equals(section.getTutorial().getWorker().getAccount()));
			result = this.sectionRepository.save(section);

		} else
			result = this.sectionRepository.save(section);
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
