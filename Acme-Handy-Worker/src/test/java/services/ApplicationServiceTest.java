
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.LoginService;
import utilities.AbstractTest;
import domain.Application;
import domain.Customer;
import domain.FixUpTask;
import domain.HandyWorker;

// Indica que se tiene que ejecutar a trav�s de Spring
@RunWith(SpringJUnit4ClassRunner.class)
//Indica los ficheros de configuraci�n
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
//Para que la base de datos no quede incoherente
@Transactional
public class ApplicationServiceTest extends AbstractTest {

	@Autowired
	private ApplicationService	applicationService;
	@Autowired
	private FixUpTaskService	taskService;
	@Autowired
	private ActorService		actorService;


	@Test
	@Rollback
	public void createAndSave() {

		//Creamos una application vac�a
		final Application a = this.applicationService.create();

		Assert.notNull(a);

		//Creamos el worker due�o de la aplicacion
		super.authenticate("handy3");
		final HandyWorker worker = (HandyWorker) this.actorService.findByUserAccountId(LoginService.getPrincipal().getId());

		final FixUpTask task = (FixUpTask) this.taskService.findAsHandyWorker().toArray()[0];

		a.setHandyWorker(worker);
		a.setFixUpTask(task);
		a.setOfferedPrice(2400.00);
		//super.unauthenticate();
		//super.authenticate("Customer1");
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
