
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Section;
import domain.Tutorial;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class SectionServiceTest extends AbstractTest {

	@Autowired
	private SectionService	sectionService;

	@Autowired
	private TutorialService	tutorialService;


	@Test
	public void createGoodTest() {
		final Tutorial tuto = this.tutorialService.findAll().iterator().next();
		super.authenticate(tuto.getWorker().getAccount().getUsername());
		final Section section = new Section();
		final Collection<String> photos = new ArrayList<>();
		section.setPhotoURL(photos);
		final Section ac = this.sectionService.create(tuto);
		Assert.isTrue(section.equals(ac));
		super.unauthenticate();
	}
	@Test
	public void createBadTest() {
		final Tutorial tuto = this.tutorialService.findAll().iterator().next();
		super.authenticate("Customer1");
		final Section a = new Section();
		Section ac = a;
		try {
			ac = this.sectionService.create(tuto);
		} catch (final Exception e) {
			ac = null;
		}
		Assert.isTrue(!(a.equals(ac)));
		super.unauthenticate();
	}

	@Test
	public void findSectionTutorialGoodTest() {
		final Collection<Tutorial> ac = this.tutorialService.findAll();
		final Tutorial a = ac.iterator().next();
		final Collection<Section> tu = this.sectionService.findAllFromTutorial(a.getId());
		Assert.isTrue(!(tu.isEmpty()));
	}

	@Test
	public void findOneGoodTest() {
		final Collection<Tutorial> ac = this.tutorialService.findAll();
		final Tutorial a = ac.iterator().next();
		final Section sec = a.getSections().iterator().next();
		final Section tu = this.sectionService.findOne(sec.getId());
		Assert.isTrue(tu.equals(sec));
	}
	@Test
	public void findSectionssByTutorialGoodTest() {
		final Collection<Tutorial> ac = this.tutorialService.findAll();
		final Tutorial a = ac.iterator().next();
		final Collection<Section> tu = this.sectionService.findAllFromTutorial(a.getId());
		Assert.isTrue(!(tu.isEmpty()));
	}

	@Test
	public void saveNewGoodTest() {
		final Collection<Tutorial> tutorial = this.tutorialService.findAll();
		final Tutorial tuto = tutorial.iterator().next();
		super.authenticate(tuto.getWorker().getAccount().getUsername());
		final Section ac = this.sectionService.create(tuto);
		ac.setNumber(3);
		ac.setText("one");
		ac.setTitle("two");
		final Section tu = this.sectionService.save(ac);
		ac.setId(tu.getId());
		ac.setVersion(tu.getVersion());
		Assert.isTrue(tu.equals(ac));
		super.unauthenticate();
	}

	@Test
	public void saveNewBadTest() {
		final Collection<Tutorial> tutorial = this.tutorialService.findAll();
		final Tutorial tuto = tutorial.iterator().next();
		super.authenticate("Customer1");
		final Section ac = new Section();
		ac.setNumber(1);
		ac.setText("one");
		ac.setTitle("two");
		ac.setTutorial(tuto);
		Section tu = ac;
		try {
			tu = this.sectionService.save(ac);
		} catch (final Exception e) {
			tu = null;
		}
		Assert.isNull(tu);
		super.unauthenticate();
	}

	@Test
	public void SaveEditGoodTest() {
		final Collection<Tutorial> ac = this.tutorialService.findAll();
		final Tutorial tu = ac.iterator().next();
		super.authenticate(tu.getWorker().getAccount().getUsername());
		final Section sec = tu.getSections().iterator().next();
		sec.setTitle("dos");
		final Section sect = this.sectionService.save(sec);
		Assert.isTrue(sec.equals(sect));
		super.unauthenticate();
	}

	@Test
	public void SaveEditBadTest() {
		final Collection<Tutorial> ac = this.tutorialService.findAll();
		final Tutorial tu = ac.iterator().next();
		super.authenticate("handy5");
		final Section sec = tu.getSections().iterator().next();
		sec.setTitle("dos");
		Section sect = sec;
		try {
			sect = this.sectionService.save(sec);
		} catch (final Exception e) {
			sect = null;
		}
		Assert.isNull(sect);
		super.unauthenticate();
	}

	@Test
	public void DeleteGoodTest() {
		final Collection<Tutorial> ac = this.tutorialService.findAll();
		final Tutorial tu = ac.iterator().next();
		super.authenticate(tu.getWorker().getAccount().getUsername());

		final Section acs = new Section();
		acs.setNumber(1);
		acs.setText("one");
		acs.setTitle("two");
		acs.setTutorial(tu);

		final Section sect = this.sectionService.save(acs);
		this.sectionService.delete(sect);
		Assert.isNull(this.sectionService.findOne(sect.getId()));
		super.unauthenticate();
	}

	@Test
	public void DeleteBadTest() {
		final Collection<Tutorial> ac = this.tutorialService.findAll();
		final Tutorial tu = ac.iterator().next();
		super.authenticate("Customer1");
		final Section sec = tu.getSections().iterator().next();
		Boolean sect = null;
		try {
			this.sectionService.delete(sec);
			sect = false;
		} catch (final Exception e) {
			sect = true;
		}
		Assert.isTrue(sect);
		super.unauthenticate();
	}

}
