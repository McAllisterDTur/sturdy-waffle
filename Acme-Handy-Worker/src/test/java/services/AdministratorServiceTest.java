
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
import domain.Administrator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class AdministratorServiceTest extends AbstractTest {

	//Service that we are testing
	@Autowired
	private AdministratorService	administratorService;

	//Auxiliar services
	@Autowired
	private UserAccountService		userAccountService;


	@Test
	public void testCreate() {
		//Login as Administrator
		super.authenticate("admin");
		//Creating the administrator
		final Administrator a = this.administratorService.create();
		//Is it actually created?
		Assert.notNull(a);
	}

	@Test
	public void testFindAll() {
		//This one is a bit self explanatory
		Assert.notEmpty(this.administratorService.findAll());
	}

	@Test
	public void testSave() {
		//Login as an administrator
		super.authenticate("admin");

		//Creating a new Administrator
		////First, we create and save a UserAccount for this new admin
		final UserAccount ac = new UserAccount();
		ac.setUsername("admin3");
		ac.setPassword("admin3");
		final Authority a = new Authority();
		a.setAuthority(Authority.ADMIN);
		ac.addAuthority(a);
		final UserAccount sac = this.userAccountService.save(ac);

		////Then, we actually create the admin
		final Administrator admin = this.administratorService.create();
		admin.setAccount(sac);
		admin.setAddress("Dirección");
		admin.setBanned(false);
		admin.setEmail("yeah@rig.com");
		admin.setName("Admini");
		admin.setSurname("Strator");
		admin.setPhone("+34 673721182");
		admin.setPhotoURL("http://tiniurl.com/profilePic.jpg");

		//Saving the administrator
		final Administrator saved = this.administratorService.save(admin);

		//Is the administrator actually saved?
		final Collection<Administrator> all = this.administratorService.findAll();
		Assert.isTrue(all.contains(saved));
	}

	@Test
	public void testFindOne() {
		//Creating a new Administrator
		////First, we login as an administrator to create a new one
		super.authenticate("admin");
		////Now, we create and save a UserAccount for this new admin
		final UserAccount ac = new UserAccount();
		ac.setUsername("admin3");
		ac.setPassword("admin3");
		final Authority a = new Authority();
		a.setAuthority(Authority.ADMIN);
		ac.addAuthority(a);
		final UserAccount sac = this.userAccountService.save(ac);

		////Then, we actually create the admin
		final Administrator admin = this.administratorService.create();
		admin.setAccount(sac);
		admin.setAddress("Dirección");
		admin.setBanned(false);
		admin.setEmail("yeah@rig.com");
		admin.setName("Admini");
		admin.setSurname("Strator");
		admin.setPhone("+34 673721182");
		admin.setPhotoURL("http://tiniurl.com/profilePic.jpg");

		//Saving the administrator
		final Administrator saved = this.administratorService.save(admin);

		//Can we find this particular administrator?
		Assert.notNull(this.administratorService.findOne(saved.getId()));
	}

	@Test
	public void testUpdate() {
		//Let's take an arbitrary administrator
		final Administrator test = (Administrator) this.administratorService.findAll().toArray()[0];
		//Now let's update, say, it's name
		test.setName("Updated");
		//And let's update it
		this.administratorService.update(test);
		//Did it work?
		////We take the admin with the id of test
		final Administrator updated = this.administratorService.findOne(test.getId());
		////And we assert
		Assert.isTrue(test.getName().equals(updated.getName()));
	}
}
