
package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

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


	@Test
	public void testSaving() {

		//Initialising the administrator
		final Administrator admin = new Administrator();
		admin.setAddress("Dirección");
		admin.setBanned(false);
		admin.setEmail("email@domain.com");
		admin.setName("Nombre");
		admin.setSurname("Apellido");

		//Saving the administrator
		final Administrator saved = this.administratorService.save(admin);

		//Asserting
		final Collection<Administrator> allAdmins = this.administratorService.findAll();
		Assert.isTrue(allAdmins.contains(saved));
	}
}
