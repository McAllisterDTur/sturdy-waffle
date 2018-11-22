
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

	private static final int	TASK_ID	= 1064;

	@Autowired
	private FixUpTaskService	fixUpTaskService;


	@Test
	public void testFindFixUpTask1() {
		super.authenticate("Customer1");

		final FixUpTask f3 = this.fixUpTaskService.findOne(FixUpTaskServiceTest.TASK_ID);

		Assert.isTrue(f3 != null);

	}

	@Test
	public void testFindFixUpTask2() {
		super.authenticate("Customer2");
		FixUpTask f3 = null;
		try {
			f3 = this.fixUpTaskService.findOne(FixUpTaskServiceTest.TASK_ID);

		} catch (final Exception e) {

		}
		Assert.isTrue(f3 == null);

	}
}
