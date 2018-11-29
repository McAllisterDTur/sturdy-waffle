
package services;

import java.util.Collection;
import java.util.Iterator;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Sponsorship;
import domain.Tutorial;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class SponsorshipServiceTest extends AbstractTest {

	@Autowired
	private SponsorshipService	sponsorshipService;

	@Autowired
	private TutorialService		tutorialService;


	@Test
	public void CreateGoodTest() {
		final Tutorial tuto = this.tutorialService.findAll().iterator().next();
		super.authenticate("sponsor1");
		final Sponsorship a = new Sponsorship();
		final Sponsorship ac = this.sponsorshipService.create(tuto);
		Assert.isTrue(a.equals(ac));
		super.unauthenticate();
	}
	@Test
	public void CreateBadTest() {
		super.authenticate("Customer1");
		final Tutorial tuto = this.tutorialService.findAll().iterator().next();
		final Sponsorship a = new Sponsorship();
		a.setTutorials(tuto);
		Sponsorship ac = null;
		try {
			ac = this.sponsorshipService.create(tuto);
		} catch (final Exception e) {
			ac = null;
		}
		Assert.isTrue(!(a.equals(ac)));
		super.unauthenticate();
	}

	@Test
	public void FindAllSponsorshipFromTutorialGoodTest() {
		final Collection<Tutorial> ac = this.tutorialService.findAll();
		final Tutorial a = ac.iterator().next();
		final Collection<Sponsorship> tu = this.sponsorshipService.findAllSponsorshipFromTutorial(a.getId());
		Assert.isTrue(!(tu.isEmpty()));
	}

	@Test
	public void FindOneGoodTest() {
		final Collection<Tutorial> ac = this.tutorialService.findAll();
		final Tutorial a = ac.iterator().next();
		final Sponsorship tu = this.sponsorshipService.findOne(a.getSponsorships().iterator().next().getId());
		Assert.isTrue((tu.equals(a.getSponsorships().iterator().next())));
	}

	@Test
	public void SaveNewGoodTest() {
		final Iterator<Tutorial> ac = this.tutorialService.findAll().iterator();
		final Tutorial tu = ac.next();
		final Sponsorship s = tu.getSponsorships().iterator().next();
		super.authenticate(s.getSponsor().getAccount().getUsername());

		final Sponsorship acs = new Sponsorship();
		acs.setBannerURL("www.us.es");
		acs.setCreditCard(s.getCreditCard());
		acs.setTargetPageLink("lsi.us.es");
		acs.setTutorials(tu);
		System.out.println(acs);
		final Sponsorship sect = this.sponsorshipService.save(acs);
		System.out.println(sect);
		acs.setId(sect.getId());
		acs.setVersion(sect.getVersion());
		acs.setSponsor(sect.getSponsor());
		Assert.isTrue(sect.equals(acs));
		super.unauthenticate();
	}

	@Test
	public void SaveNewBadTest() {
		final Collection<Tutorial> ac = this.tutorialService.findAll();
		final Tutorial tu = ac.iterator().next();
		final Sponsorship s = tu.getSponsorships().iterator().next();
		super.authenticate("Customer1");

		final Sponsorship acs = new Sponsorship();
		acs.setBannerURL(s.getBannerURL());
		acs.setCreditCard(s.getCreditCard());
		acs.setTargetPageLink(s.getTargetPageLink());
		acs.setSponsor(s.getSponsor());
		acs.setTutorials(s.getTutorials());
		Sponsorship sect = acs;
		try {
			sect = this.sponsorshipService.save(acs);
		} catch (final Exception e) {
			sect = null;
		}
		Assert.isNull(sect);
		super.unauthenticate();
	}

	@Test
	public void SaveEditGoodTest() {
		final Collection<Tutorial> ac = this.tutorialService.findAll();
		final Tutorial tu = ac.iterator().next();
		final Sponsorship s = tu.getSponsorships().iterator().next();
		super.authenticate(s.getSponsor().getAccount().getUsername());
		s.setBannerURL("www.us.es");
		final Sponsorship sect = this.sponsorshipService.save(s);
		Assert.isTrue(s.equals(sect));
		super.unauthenticate();
	}

	@Test
	public void SaveEditBadTest() {
		final Collection<Tutorial> ac = this.tutorialService.findAll();
		final Tutorial tu = ac.iterator().next();
		final Sponsorship s = tu.getSponsorships().iterator().next();
		super.authenticate("Customer1");
		s.setBannerURL("www.us.es");
		Sponsorship sect = null;
		try {
			sect = this.sponsorshipService.save(s);
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
		super.authenticate(tu.getSponsorships().iterator().next().getSponsor().getAccount().getUsername());
		final Sponsorship s = tu.getSponsorships().iterator().next();

		final Sponsorship acs = new Sponsorship();
		acs.setBannerURL(s.getBannerURL());
		acs.setCreditCard(s.getCreditCard());
		acs.setTargetPageLink(s.getTargetPageLink());
		acs.setSponsor(s.getSponsor());
		acs.setTutorials(s.getTutorials());

		final Sponsorship sect = this.sponsorshipService.save(acs);
		this.sponsorshipService.delete(sect);
		Assert.isNull(this.sponsorshipService.findOne(sect.getId()));
		super.unauthenticate();
	}

	@Test
	public void DeleteBadTest() {
		final Collection<Tutorial> ac = this.tutorialService.findAll();
		final Tutorial tu = ac.iterator().next();
		super.authenticate("sponsor2");
		final Sponsorship sec = tu.getSponsorships().iterator().next();
		Boolean prueba = null;
		try {
			this.sponsorshipService.delete(sec);
			prueba = false;
		} catch (final Exception e) {
			prueba = true;
		}
		Assert.isTrue(prueba);
		super.unauthenticate();
	}

}
