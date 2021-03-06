
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import security.Authority;
import security.UserAccount;
import utilities.AbstractTest;
import domain.Referee;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class RefereeServiceTest extends AbstractTest {

	//Service that we are testing
	@Autowired
	public RefereeService		refService;

	//Auxiliary services
	@Autowired
	public UserAccountService	userAccountService;


	@Test
	public void createTest() {
		super.authenticate("admin");
		final Referee r = this.refService.create();
		Assert.notNull(r);
	}

	@Test
	public void findAllTest() {
		Assert.notEmpty(this.refService.findAll());
	}

	@Test
	public void saveTest() {
		super.authenticate("admin");
		final UserAccount ac = new UserAccount();
		ac.setUsername("refereeTest");
		ac.setPassword("refereeTest");
		final Authority a = new Authority();
		a.setAuthority(Authority.REFEREE);
		ac.addAuthority(a);
		final UserAccount sac = this.userAccountService.save(ac);

		final Referee ref = this.refService.create();
		ref.setAccount(sac);
		ref.setAddress("Direcci�n");
		ref.setBanned(false);
		ref.setEmail("yeah@rig.com");
		ref.setName("Ref");
		ref.setSurname("Eree");
		ref.setPhone("+34 673721182");
		ref.setPhotoURL("http://tiniurl.com/profilePic.jpg");
		super.unauthenticate();

		final Referee saved = this.refService.save(ref);
		Assert.isTrue(this.refService.findAll().contains(saved));
	}

	@Test
	public void findOneTest() {
		super.authenticate("admin");
		final UserAccount ac = new UserAccount();
		ac.setUsername("refereeTest");
		ac.setPassword("refereeTest");
		final Authority a = new Authority();
		a.setAuthority(Authority.REFEREE);
		ac.addAuthority(a);
		final UserAccount sac = this.userAccountService.save(ac);

		final Referee ref = this.refService.create();
		ref.setAccount(sac);
		ref.setAddress("Direcci�n");
		ref.setBanned(false);
		ref.setEmail("yeah@rig.com");
		ref.setName("Ref");
		ref.setSurname("Eree");
		ref.setPhone("+34 673721182");
		ref.setPhotoURL("http://tiniurl.com/profilePic.jpg");
		super.unauthenticate();

		final Referee saved = this.refService.save(ref);
		Assert.isTrue(this.refService.findOne(saved.getId()).equals(saved));
	}
}
