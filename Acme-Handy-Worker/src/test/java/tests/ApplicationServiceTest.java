
package tests;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import repositories.ApplicationRepository;
import repositories.CustomerRepository;
import repositories.HandyWorkerRepository;
import services.ApplicationService;
import domain.Application;

// Indica que se tiene que ejecutar a través de Spring
@RunWith(SpringJUnit4ClassRunner.class)
//Indica los ficheros de configuración
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
//Para que la base de datos no quede incoherente
@Transactional
public class ApplicationServiceTest {

	@Autowired
	private HandyWorkerRepository	workerRepo;
	@Autowired
	private CustomerRepository		customerRepo;
	@Autowired
	private ApplicationRepository	applicationRepo;
	@Autowired
	private ApplicationService		applicationService;


	@Test
	public void createAndSave() {

		final Application a = this.applicationService.create();

		Assert.notNull(a);

	}
	@Test
	public void listCustomer() {

	}

	@Test
	public void listWorker() {

	}

}
