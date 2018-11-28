package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Curricula;

// Indica que se tiene que ejecutar a través de Spring
@RunWith(SpringJUnit4ClassRunner.class)
// Indica los ficheros de configuración
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
// Para que la base de datos no quede incoherente
@Transactional
public class CurriculaServiceTest extends AbstractTest {

	@Autowired
	private CurriculaService curriculaService;

	@Test
	public void saveGood() {
		super.authenticate("handy1");
		Curricula c = curriculaService.create();
		curriculaService.save(c);
		super.unauthenticate();
	}

	@Test
	public void saveBad() {
		super.authenticate("Customer1");
		Curricula c = curriculaService.create();
		Curricula c2 = null;
		try {
			c2 = curriculaService.save(c);

		} catch (Exception e) {
		}
		Assert.isNull(c2);
		super.unauthenticate();
	}

	@Test
	public void findOneGood() {
		super.authenticate("handy1");
		Curricula c = curriculaService.create();
		Curricula c2 = curriculaService.save(c);
		Curricula c3 = curriculaService.findOne(c2.getId());
		Assert.notNull(c3);
		super.unauthenticate();
	}

	@Test
	public void findFromCurrentHandyWorkerGood() {
		super.authenticate("handy1");
		Curricula c = curriculaService.create();
		curriculaService.save(c);
		Curricula c2 = curriculaService.findFromLoggedHandyWorker();
		Assert.notNull(c2);
		super.unauthenticate();
	}

	@Test
	public void findFromCurrentHandyWorkerBad() {
		super.authenticate("Customer1");
		Curricula c = null;
		try {
			c = curriculaService.findFromLoggedHandyWorker();
		} catch (Exception e) {
		}
		Assert.isNull(c);
		super.unauthenticate();
	}
}
