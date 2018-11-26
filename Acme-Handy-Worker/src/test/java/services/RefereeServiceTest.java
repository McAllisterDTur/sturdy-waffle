
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
	public void testCreate() {
		super.authenticate("admin");
		final Referee r = this.refService.create();
		Assert.notNull(r);
	}

	@Test
	public void testFindAll() {
		Assert.notEmpty(this.refService.findAll());
	}

	@Test
	public void testSave() {
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
		ref.setAddress("Dirección");
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
	public void tesFindOne() {
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
		ref.setAddress("Dirección");
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

	@Test
	public void testUpdate() {
		final Referee toUpd = (Referee) this.refService.findAll().toArray()[0];
		toUpd.setEmail("updated@dp.com");
		final Referee updtd = this.refService.update(toUpd);
		Assert.isTrue(this.refService.findOne(updtd.getId()).getEmail().equals(toUpd.getEmail()));
	}

	@Test
	public void testBan() {
		super.authenticate("admin");
		final Referee r = (Referee) this.refService.findAll().toArray()[0];
		this.refService.ban(r);
		final Referee rs = this.refService.save(r);
		Assert.isTrue(this.refService.findOne(rs.getId()).getBanned());
	}
	@Test
	public void testUnBan() {
		super.authenticate("admin");
		final Referee r = (Referee) this.refService.findAll().toArray()[0];
		this.refService.ban(r);
		final Referee rs = this.refService.save(r);
		this.refService.unban(rs);
		final Referee rsu = this.refService.save(rs);
		Assert.isTrue(!this.refService.findOne(rsu.getId()).getBanned());
	}
}
