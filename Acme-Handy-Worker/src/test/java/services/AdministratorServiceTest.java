
package services;

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
	public void testCreate() {

		//Login as Administrator
		super.authenticate("admin");
		//Creating the administrator
		final Administrator a = this.administratorService.create();
		//Is it actually created?
		Assert.notNull(a);
	}
}
