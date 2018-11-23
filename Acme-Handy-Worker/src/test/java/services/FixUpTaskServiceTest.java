
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.FixUpTask;

// Indica que se tiene que ejecutar a través de Spring
@RunWith(SpringJUnit4ClassRunner.class)
// Indica los ficheros de configuración
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
// Para que la base de datos no quede incoherente
@Transactional
public class FixUpTaskServiceTest extends AbstractTest {

	@Autowired
	private FixUpTaskService	fixUpTaskService;


	@Test
	public void saveGood() {
		FixUpTask f = fixUpTaskService.create();
		
		super.authenticate("Customer1");

		FixUpTask f2 = fixUpTaskService.save(f);
		FixUpTask f3 = fixUpTaskService.findOne(f2.getId());
		

		Assert.isTrue(f3 != null);

	}

	@Test
	public void saveBad() {
		super.authenticate(null);
		FixUpTask f = fixUpTaskService.create();
		FixUpTask f3 = null;
		try {
			FixUpTask f2 = fixUpTaskService.save(f);
			f3 = fixUpTaskService.findOne(f2.getId());
			
		} catch (final Exception e) {
			f3 = null;
		}
		Assert.isTrue(f3 == null);

	}
}
