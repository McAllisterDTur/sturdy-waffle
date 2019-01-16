
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.UserAccount;
import utilities.AbstractTest;
import domain.Sponsor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class SponsorServiceTest extends AbstractTest {

	@Autowired
	private SponsorService		sponsorService;

	@Autowired
	private UserAccountService	useraccountService;


	@Test
	public void createGoodTest() {
		final Sponsor a = new Sponsor();
		final UserAccount ua = this.useraccountService.create();
		a.setAccount(ua);
		final Sponsor ac = this.sponsorService.create();
		Assert.isTrue(a.equals(ac));
	}
	//	@Test
	//	public void CreateBadTest() {
	//		super.authenticate("Customer");
	//		final Sponsor a = new Sponsor();
	//		final UserAccount ua = this.useraccountService.create();
	//		a.setAccount(ua);
	//		Sponsor ac = null;
	//		try {
	//			ac = this.sponsorService.create();
	//		} catch (final Exception e) {
	//			ac = null;
	//		}
	//		Assert.isTrue(!(a.equals(ac)));
	//		super.unauthenticate();
	//	}

	@Test
	public void findAllTest() {
		final Collection<Sponsor> a = this.sponsorService.findAll();
		Assert.notEmpty(a);
	}

	@Test
	public void findOneTest() {
		final Collection<Sponsor> acs = this.sponsorService.findAll();
		final Sponsor base = acs.iterator().next();
		final Sponsor a = this.sponsorService.findOne(base.getId());
		Assert.isTrue(base.equals(a));
	}

	@Test
	public void saveNewGoodTest() {
		final Sponsor ac = this.sponsorService.create();
		ac.setAddress("one");
		ac.setEmail("two");
		ac.setMiddleName("three");
		ac.setName("four");
		ac.setPhone("999666333");
		ac.setPhotoURL("www.us.es");
		ac.setSurname("five");
		ac.getAccount().setPassword("hola");
		ac.getAccount().setUsername("hello");
		final Sponsor sponsor = this.sponsorService.save(ac);
		Assert.notNull(sponsor);
	}

	//	@Test
	//	public void SaveNewBadTest() {
	//		final Sponsor ac = this.sponsorService.create();
	//		ac.setAddress("one");
	//		ac.setEmail("two");
	//		ac.setMiddleName("three");
	//		ac.setName("four");
	//		ac.setPhone("999666333");
	//		ac.setPhotoURL("www.us.es");
	//		ac.setSurname("five");
	//		ac.getAccount().setPassword("hola");
	//		ac.getAccount().setUsername("hello");
	//		super.authenticate("handy1");
	//		Sponsor spon = ac;
	//		try {
	//			spon = this.sponsorService.save(ac);
	//		} catch (final Exception e) {
	//			spon = null;
	//		}
	//		Assert.isNull(spon);
	//		super.unauthenticate();
	//
	//	}

	@Test
	public void saveEditGoodTest() {
		final Collection<Sponsor> acs = this.sponsorService.findAll();
		final Sponsor ac = acs.iterator().next();
		super.authenticate(ac.getAccount().getUsername());
		ac.setAddress("Prueba");
		final Sponsor spon = this.sponsorService.save(ac);
		Assert.isTrue(spon.equals(ac));
		super.unauthenticate();
	}

	@Test
	public void saveEditBadTest() {
		final Collection<Sponsor> acs = this.sponsorService.findAll();
		final Sponsor ac = acs.iterator().next();
		super.authenticate("Sponsor2");
		ac.setAddress("Manue");
		Sponsor a = null;
		try {
			a = this.sponsorService.save(ac);
		} catch (final Exception e) {
			a = null;
		}
		Assert.isNull(a);
		super.unauthenticate();
	}
}
