
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
	public void CreateGoodTest() {
		super.authenticate("handy1");
		final Section section = new Section();
		final Collection<String> photos = new ArrayList<>();
		section.setPhotoURL(photos);
		final Section ac = this.sectionService.create();
		Assert.isTrue(section.equals(ac));
		super.unauthenticate();
	}
	@Test
	public void CreateBadTest() {
		super.authenticate("Customer1");
		final Section a = new Section();
		Section ac = null;
		try {
			ac = this.sectionService.create();
		} catch (final Exception e) {
			ac = null;
		}
		Assert.isTrue(!(a.equals(ac)));
		super.unauthenticate();
	}

	@Test
	public void FindSectionTutorialGoodTest() {
		final Collection<Tutorial> ac = this.tutorialService.findAll();
		final Tutorial a = ac.iterator().next();
		final Collection<Section> tu = this.sectionService.findAllFromTutorial(a.getId());
		Assert.isTrue(!(tu.isEmpty()));
	}

	@Test
	public void FindOneGoodTest() {
		final Collection<Tutorial> ac = this.tutorialService.findAll();
		final Tutorial a = ac.iterator().next();
		final Section tu = this.sectionService.findOne(a.getSections().iterator().next().getId());
		Assert.isTrue((tu.equals(a.getSections().iterator().next())));
	}
	//
	//	@Test
	//	public void FindSectionssByTutorialGoodTest() {
	//		final Collection<Tutorial> ac = this.tutorialService.findAll();
	//		final Tutorial a = ac.iterator().next();
	//		final Collection<Section> tu = this.sectionService.findAllFromTutorial(a.getId());
	//		Assert.isTrue(!(tu.isEmpty()));
	//	}
	//
	//	@Test
	//	public void SaveNewGoodTest() {
	//		final Collection<Tutorial> tutorial = this.tutorialService.findAll();
	//		final Tutorial tuto = tutorial.iterator().next();
	//		super.authenticate(tuto.getWorker().getAccount().getUsername());
	//		final Section ac = this.sectionService.create();
	//		ac.setNumber(1);
	//		ac.setText("one");
	//		ac.setTitle("two");
	//		ac.setTutorial(tuto);
	//		final Section tu = this.sectionService.save(ac);
	//		ac.setId(tu.getId());
	//		ac.setVersion(tu.getVersion());
	//		Assert.isTrue(tu.equals(ac));
	//		super.unauthenticate();
	//	}
	//
	//	@Test
	//	public void SaveNewBadTest() {
	//		final Collection<Tutorial> tutorial = this.tutorialService.findAll();
	//		final Tutorial tuto = tutorial.iterator().next();
	//		super.authenticate("Customer1");
	//		final Section ac = this.sectionService.create();
	//		ac.setNumber(1);
	//		ac.setText("one");
	//		ac.setTitle("two");
	//		ac.setTutorial(tuto);
	//		Section tu = null;
	//		try {
	//			tu = this.sectionService.save(ac);
	//		} catch (final Exception e) {
	//			tu = null;
	//		}
	//		Assert.isNull(tu);
	//		super.unauthenticate();
	//	}
	//
	//	@Test
	//	public void SaveEditGoodTest() {
	//		final Collection<Tutorial> ac = this.tutorialService.findAll();
	//		final Tutorial tu = ac.iterator().next();
	//		super.authenticate(tu.getWorker().getAccount().getUsername());
	//		final Section sec = tu.getSections().iterator().next();
	//		sec.setTitle("dos");
	//		final Section sect = this.sectionService.save(sec);
	//		Assert.isTrue(sec.equals(sect));
	//		super.unauthenticate();
	//	}
	//
	//	@Test
	//	public void SaveEditBadTest() {
	//		final Collection<Tutorial> ac = this.tutorialService.findAll();
	//		final Tutorial tu = ac.iterator().next();
	//		super.authenticate("Customer1");
	//		final Section sec = tu.getSections().iterator().next();
	//		sec.setTitle("dos");
	//		Section sect = null;
	//		try {
	//			sect = this.sectionService.save(sec);
	//		} catch (final Exception e) {
	//			sect = null;
	//		}
	//		Assert.isNull(sect);
	//		super.unauthenticate();
	//	}
	//
	//	@Test
	//	public void DeleteGoodTest() {
	//		final Collection<Tutorial> ac = this.tutorialService.findAll();
	//		final Tutorial tu = ac.iterator().next();
	//		super.authenticate(tu.getWorker().getAccount().getUsername());
	//		tu.setId(0);
	//		tu.setVersion(0);
	//		final Tutorial tuto = this.tutorialService.save(tu);
	//		this.tutorialService.delete(tuto);
	//		Assert.isNull(this.tutorialService.findOne(tuto.getId()));
	//		super.unauthenticate();
	//	}
	//
	//	@Test
	//	public void DeleteBadTest() {
	//		final Collection<Tutorial> ac = this.tutorialService.findAll();
	//		final Tutorial tu = ac.iterator().next();
	//		super.authenticate(tu.getWorker().getAccount().getUsername());
	//		final Section sec = tu.getSections().iterator().next();
	//		Section sect = null;
	//		try {
	//			this.sectionService.delete(sec);
	//		} catch (final Exception e) {
	//			sect = null;
	//		}
	//		Assert.isNull(sect);
	//		super.unauthenticate();
	//	}

}
