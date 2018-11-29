
package tests;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import repositories.CategoryRepository;
import repositories.WarrantyRepository;
import security.LoginService;
import services.ActorService;
import services.ApplicationService;
import services.CustomerService;
import services.FixUpTaskService;
import services.HandyWorkerService;
import services.TickerService;
import services.UserAccountService;
import utilities.AbstractTest;
import domain.Application;
import domain.Customer;
import domain.FixUpTask;
import domain.HandyWorker;

// Indica que se tiene que ejecutar a través de Spring
@RunWith(SpringJUnit4ClassRunner.class)
//Indica los ficheros de configuración
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
//Para que la base de datos no quede incoherente
@Transactional
public class ApplicationServiceTest extends AbstractTest {

	@Autowired
	private ApplicationService	applicationService;
	@Autowired
	private HandyWorkerService	workerService;
	@Autowired
	private CustomerService		customerService;
	@Autowired
	private UserAccountService	accountService;
	@Autowired
	private FixUpTaskService	taskService;
	@Autowired
	private TickerService		tikerService;
	@Autowired
	private ActorService		actorService;

	//TODO: implementar los services cuando esten o modificar a un fixuptask creado
	@Autowired
	private WarrantyRepository	warrantyRepository;
	@Autowired
	private CategoryRepository	categoryRepository;


	@Test
	@Rollback
	public void createAndSave() {

		//Creamos una application vacía
		final Application a = this.applicationService.create();

		Assert.notNull(a);

		//Creamos el worker dueño de la aplicacion
		super.authenticate("handy3");
		final HandyWorker worker = (HandyWorker) this.actorService.findByUserAccountId(LoginService.getPrincipal().getId());

		final FixUpTask task = (FixUpTask) this.taskService.findAsHandyWorker().toArray()[0];

		a.setHandyWorker(worker);
		a.setFixUpTask(task);
		a.setOfferedPrice(2400.00);

		final Application aFinal = this.applicationService.save(a);

		Assert.notNull(aFinal);
		super.unauthenticate();
	}

	@Test
	public void listCustomer() {

		super.authenticate("customer2");
		final Customer c = (Customer) this.actorService.findByUserAccountId(LoginService.getPrincipal().getId());

		final Collection<Application> res = this.applicationService.findAllCustomer(c.getId());
		Assert.notNull(res);
		super.unauthenticate();

	}
	@Test
	public void listWorker() {
		super.authenticate("handy3");
		final HandyWorker w = (HandyWorker) this.actorService.findByUserAccountId(LoginService.getPrincipal().getId());

		final Collection<Application> res = this.applicationService.findAllWorker(w.getId());
		Assert.notNull(res);
		super.unauthenticate();
	}

	@Test
	public void testQueries() {

		super.authenticate("Admin");

		final Collection<Double> statictics = this.applicationService.statictisApplications();
		Assert.notNull(statictics);
		Assert.notEmpty(statictics);

		final double pending = this.applicationService.ratioPedingApplications();
		Assert.isTrue(pending > 0);
		final double accepted = this.applicationService.ratioAcceptedApplications();
		Assert.isTrue(accepted > 0);
		final double rejected = this.applicationService.ratioRejectedApplications();
		Assert.isTrue(rejected > 0);
		final double elapsed = this.applicationService.ratioElapsedApplications();
		Assert.isTrue(elapsed > 0);

		super.unauthenticate();

	}

	@Test
	public void testAddComment() {
		super.authenticate("handy3");

		final HandyWorker worker = (HandyWorker) this.actorService.findByUserAccountId(LoginService.getPrincipal().getId());
		final Application a = (Application) worker.getApplications().toArray()[2];

		final Application res = this.applicationService.saveComment(a.getId(), "Aceptala porfitas... :)");
		Assert.notNull(res);

		super.unauthenticate();
	}
}
