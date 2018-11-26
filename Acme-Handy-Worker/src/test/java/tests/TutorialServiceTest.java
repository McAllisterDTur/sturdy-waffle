
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
public class TutorialServiceTest extends AbstractTest {

	@Autowired
	private TutorialService	tutorialService;


	//@Autowired
	//private HandyWorkerService	handyWorkerService;

	@Test
	public void CreateGoodTest() {
		super.authenticate("handy1");
		final Tutorial tutorial = new Tutorial();
		final Collection<String> photos = new ArrayList<>();
		final Collection<Section> sections = new ArrayList<>();
		tutorial.setPhotoURL(photos);
		tutorial.setSections(sections);
		final Tutorial ac = this.tutorialService.create();
		Assert.isTrue(tutorial.equals(ac));
		super.unauthenticate();
	}

	@Test
	public void CreateBadTest() {
		super.authenticate("Customer1");
		final Tutorial a = new Tutorial();
		Tutorial ac = null;
		try {
			ac = this.tutorialService.create();
		} catch (final Exception e) {
			ac = null;
		}
		Assert.isTrue(!(a.equals(ac)));
		super.unauthenticate();
	}

	@Test
	public void FindOneGoodTest() {
		final Collection<Tutorial> ac = this.tutorialService.findAll();
		final Tutorial a = ac.iterator().next();
		final Tutorial tu = this.tutorialService.findOne(a.getId());
		Assert.isTrue((tu.equals(a)));
	}

	@Test
	public void FindTutorialsByHandyWorkerGoodTest() {
		final Collection<Tutorial> ac = this.tutorialService.findAll();
		final Tutorial a = ac.iterator().next();
		final Collection<Tutorial> tu = this.tutorialService.findAllFromHandyworker(a.getWorker().getId());
		Assert.isTrue(!(tu.isEmpty()));
	}

	//	@Test
	//	public void SaveNewGoodTest() {
	//		final Collection<HandyWorker> workers = this.handyWorkerService.findAll();
	//		final HandyWorker worker = workers.iterator().next();
	//		super.authenticate(worker.getAccount().getUsername());
	//		final Tutorial ac = this.tutorialService.create();
	//		ac.setSummary("one");
	//		ac.setTitle("two");
	//		ac.setWorker(worker);
	//		final Tutorial tu = this.tutorialService.save(ac);
	//		ac.setId(tu.getId());
	//		ac.setVersion(tu.getVersion());
	//		Assert.isTrue(tu.equals(ac));
	//		super.unauthenticate();
	//	}
	//
	//	@Test
	//	public void SaveNewBadTest() {
	//		final Collection<HandyWorker> workers = this.handyWorkerService.findAll();
	//		final HandyWorker worker = workers.iterator().next();
	//		super.authenticate("Customer1");
	//		final Tutorial ac = this.tutorialService.create();
	//		ac.setSummary("one");
	//		ac.setTitle("two");
	//		ac.setWorker(worker);
	//		final Tutorial tu = this.tutorialService.save(ac);
	//		ac.setId(tu.getId());
	//		ac.setVersion(tu.getVersion());
	//		Assert.isTrue(tu.equals(ac));
	//		super.unauthenticate();
	//	}

	@Test
	public void SaveEditGoodTest() {
		final Collection<Tutorial> ac = this.tutorialService.findAll();
		final Tutorial tu = ac.iterator().next();
		super.authenticate(tu.getWorker().getAccount().getUsername());
		tu.setTitle("dos");
		final Tutorial tuto = this.tutorialService.save(tu);
		tu.setLastTimeUpdated(tu.getLastTimeUpdated());
		Assert.isTrue(tuto.equals(tu));
		super.unauthenticate();
	}

	@Test
	public void SaveEditBadTest() {

		final Collection<Tutorial> ac = this.tutorialService.findAll();
		final Tutorial tuto = ac.iterator().next();
		super.authenticate("Customer1");
		tuto.setTitle("dos");
		Tutorial tu = null;
		try {
			tu = this.tutorialService.save(tuto);
		} catch (final Exception e) {
			tu = null;
		}
		Assert.isNull(tu);
		super.unauthenticate();
	}

	//	@Test
	//	public void DeleteGoodTest() {
	//		final Collection<HandyWorker> workers = this.handyWorkerService.findAll();
	//		final HandyWorker worker = workers.iterator().next();
	//		super.authenticate(worker.getAccount().getUsername());
	//		final Tutorial ac = this.tutorialService.create();
	//		ac.setSummary("one");
	//		ac.setTitle("two");
	//		ac.setWorker(worker);
	//		final Tutorial tu = this.tutorialService.save(ac);
	//		this.tutorialService.delete(tu);
	//		Assert.isNull(this.tutorialService.findOne(tu.getId()));
	//		super.unauthenticate();
	//	}

	@Test
	public void DeleteBadTest() {
		final Collection<Tutorial> ac = this.tutorialService.findAll();
		final Tutorial tuto = ac.iterator().next();
		super.authenticate("Customer1");
		Tutorial tu = null;
		try {
			this.tutorialService.delete(tuto);
		} catch (final Exception e) {
			tu = null;
		}
		Assert.isNull(tu);
		super.unauthenticate();
	}
}
