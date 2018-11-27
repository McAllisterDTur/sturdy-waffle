
package tests;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.FixUpTaskService;
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
	@Rollback
	public void saveGood() {
		final FixUpTask f = this.fixUpTaskService.create();

		super.authenticate("Customer1");

		final FixUpTask f2 = this.fixUpTaskService.save(f);
		final FixUpTask f3 = this.fixUpTaskService.findOne(f2.getId());

		Assert.isTrue(f3 != null);

	}

	@Test
	@Rollback
	public void saveBad() {
		super.authenticate(null);
		final FixUpTask f = this.fixUpTaskService.create();
		FixUpTask f3 = null;
		try {
			final FixUpTask f2 = this.fixUpTaskService.save(f);
			f3 = this.fixUpTaskService.findOne(f2.getId());

		} catch (final Exception e) {
			f3 = null;
		}
		Assert.isTrue(f3 == null);

	}
}
