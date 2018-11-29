
package tests;

import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

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
import security.Authority;
import security.LoginService;
import security.UserAccount;
import services.ActorService;
import services.ApplicationService;
import services.CustomerService;
import services.FixUpTaskService;
import services.HandyWorkerService;
import services.TickerService;
import services.UserAccountService;
import utilities.AbstractTest;
import domain.Application;
import domain.Category;
import domain.Customer;
import domain.FixUpTask;
import domain.HandyWorker;
import domain.Warranty;

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
		final HandyWorker worker = this.generaWorker();

		//Creamos el customer dueño de la FixUpTask a aplicar	
		final Customer customer = this.generaCustomer();
		super.authenticate(customer.getAccount().getUsername());

		//Creamos FixUpTask al que se aplica
		final FixUpTask fixUpTask = this.generaTask(customer);
		super.unauthenticate();

		a.setHandyWorker(worker);
		a.setFixUpTask(fixUpTask);
		a.setRegisterTime(new Date());
		a.setOfferedPrice(2400.00);
		a.setStatus("PENDING");

		super.authenticate(worker.getAccount().getUsername());
		final Application aFinal = this.applicationService.save(a);
		super.unauthenticate();

		Assert.notNull(aFinal);
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

	private HandyWorker generaWorker() {
		//Creamos el HandyWorker Dueño
		final HandyWorker worker = this.workerService.create();

		//Creamos un nuevo userAccount para HandyWorker
		final UserAccount accountW = this.accountService.create();
		final Authority authW = new Authority();
		authW.setAuthority(Authority.HANDYWORKER);
		accountW.addAuthority(authW);
		accountW.setPassword("pass1");
		accountW.setUsername("worker");

		//Almacenamos el valor en al base de datos
		final UserAccount accountFinalW = this.accountService.save(accountW);

		//Añadimos los valores restantes al worker
		worker.setAccount(accountFinalW);
		//Setteamos los valores 'name', 'surname', 'address', 'banned', 'email', 'phone' y 'photoURL'
		worker.setAddress("Av. Tres Mil Viviendas");
		worker.setEmail("chapuzas3000@gmail.com");
		worker.setBanned(false);
		worker.setPhone("+34 666003000");
		worker.setName("Fulgencio");
		worker.setSurname("Ramirez");
		worker.setPhotoURL("https://www.tuenti.com/FulgenR/Albun:?noxesitawena/1682903.jpg");
		worker.setMake("FRChapuzas");
		worker.setScore(0.0);

		//Guardamos el nuevo worker en la base de datos
		final HandyWorker workerFinal = this.workerService.save(worker);
		return workerFinal;
	}

	private Customer generaCustomer() {
		//Creamos el Customer dueño del FixUpTask
		final Customer customer = this.customerService.create();

		//Creamos un nuevo userAccount para Customer
		final UserAccount accountC = this.accountService.create();
		final Authority authC = new Authority();
		authC.setAuthority(Authority.CUSTOMER);
		accountC.addAuthority(authC);
		accountC.setPassword("pass2");
		accountC.setUsername("prueba2");
		final UserAccount accountFinalC = this.accountService.save(accountC);

		//Añadimos los valores restantes al worker
		customer.setAccount(accountFinalC);
		//Setteamos los valores 'name', 'surname', 'address', 'banned', 'email', 'phone' y 'photoURL'
		customer.setAddress("Calle callao");
		customer.setEmail("josefi_G@yahoo.com");
		customer.setBanned(false);
		customer.setPhone("+34 678113890");
		customer.setName("Josefa");
		customer.setSurname("Gonzalez");
		customer.setPhotoURL("https://tiny.url/josefi_G/profile/12679.gif");
		customer.setScore(0.0);

		final Customer customerFinal = this.customerService.save(customer);
		return customerFinal;
	}
	private FixUpTask generaTask(final Customer customer) {
		final FixUpTask task = this.taskService.create();

		task.setTicker(this.tikerService.getTicker());
		task.setPeriodStart(new GregorianCalendar(2018, 4, 13, 13, 00, 00).getTime());
		task.setPeriodEnd(new GregorianCalendar(2018, 11, 13, 20, 00, 00).getTime());

		task.setPublishTime(new Date());
		task.setAddress("Calle primera");
		task.setMaxPrice(3000.00);

		final Category cat = this.categoryRepository.findOne(1043);
		task.setCategory(cat);

		final Warranty warranty = this.warrantyRepository.findOne(1047);
		task.setWarranty(warranty);

		task.setCustomer(customer);

		final FixUpTask taskFinal = this.taskService.save(task);

		return taskFinal;

	}
}
