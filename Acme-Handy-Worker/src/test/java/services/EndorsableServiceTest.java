
package services;

import java.util.Collection;

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
import domain.Endorsable;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class EndorsableServiceTest extends AbstractTest {

	@Autowired
	EndorsableService	endService;

	@Autowired
	UserAccountService	userAccountService;


	@Test
	public void testCreate() {
		final Endorsable e = this.endService.create();
		Assert.notNull(e);
	}

	@Test
	public void testFindAll() {
		final Collection<Endorsable> alle = this.endService.findAll();
		Assert.notEmpty(alle);
	}

	@Test
	public void testSave() {
		final UserAccount ac = new UserAccount();
		ac.setUsername("endorsable1");
		ac.setPassword("endorsable1");
		final Authority a = new Authority();
		a.setAuthority(Authority.CUSTOMER);
		ac.addAuthority(a);
		final UserAccount sac = this.userAccountService.save(ac);

		final Endorsable e = this.endService.create();
		e.setAccount(sac);
		e.setAddress("Dirección");
		e.setBanned(false);
		e.setEmail("yeah@rig.com");
		e.setName("Endor");
		e.setSurname("Sable");
		e.setPhone("+34 673721182");
		e.setPhotoURL("http://tiniurl.com/profilePic.jpg");

		final Endorsable es = this.endService.save(e);
		Assert.isTrue(this.endService.findAll().contains(es));
	}

	@Test
	public void testFindOne() {
		final UserAccount ac = new UserAccount();
		ac.setUsername("endorsable1");
		ac.setPassword("endorsable1");
		final Authority a = new Authority();
		a.setAuthority(Authority.CUSTOMER);
		ac.addAuthority(a);
		final UserAccount sac = this.userAccountService.save(ac);

		final Endorsable e = this.endService.create();
		e.setAccount(sac);
		e.setAddress("Dirección");
		e.setBanned(false);
		e.setEmail("yeah@rig.com");
		e.setName("Endor");
		e.setSurname("Sable");
		e.setPhone("+34 673721182");
		e.setPhotoURL("http://tiniurl.com/profilePic.jpg");

		final Endorsable es = this.endService.save(e);
		Assert.isTrue(this.endService.findOne(es.getId()).equals(es));
	}

	@Test
	public void testUpdate() {
		final Endorsable e = (Endorsable) this.endService.findAll().toArray()[0];
		e.setName("Updated");
		final Endorsable saved = this.endService.save(e);
		Assert.isTrue(e.getName().equals(this.endService.findOne(saved.getId()).getName()));
	}

	@Test
	public void testBan() {
		super.authenticate("admin");
		final Endorsable e = (Endorsable) this.endService.findAll().toArray()[0];
		this.endService.ban(e);
		final Endorsable es = this.endService.save(e);
		Assert.isTrue(this.endService.findOne(es.getId()).getBanned());
	}
	@Test
	public void testUnBan() {
		super.authenticate("admin");
		final Endorsable e = (Endorsable) this.endService.findAll().toArray()[0];
		this.endService.ban(e);
		final Endorsable es = this.endService.save(e);
		this.endService.unban(es);
		final Endorsable esu = this.endService.save(es);
		Assert.isTrue(!this.endService.findOne(esu.getId()).getBanned());
	}

	//TODO: Test score

}
