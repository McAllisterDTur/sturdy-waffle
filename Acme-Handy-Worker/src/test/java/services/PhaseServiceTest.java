
package services;

import java.util.Collection;
import java.util.GregorianCalendar;

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
import domain.HandyWorker;
import domain.Phase;

// Indica que se tiene que ejecutar a trav�s de Spring
@RunWith(SpringJUnit4ClassRunner.class)
//Indica los ficheros de configuraci�n
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
//Para que la base de datos no quede incoherente
@Transactional
public class PhaseServiceTest extends AbstractTest {

	@Autowired
	private PhaseService	phaseService;
	@Autowired
	private ActorService	actorService;


	@Test
	public void createAndSaveTest() {
		super.authenticate("handy6");
		final HandyWorker worker = (HandyWorker) this.actorService.findByUserAccountId(LoginService.getPrincipal().getId());

		final Application a = worker.getApplications().iterator().next();

		final Phase p = this.phaseService.create(a.getId());
		Assert.notNull(p);

		p.setApplication(a);

		p.setDescription("Consiste en mirar por la ventana.");
		p.setStartTime(new GregorianCalendar(2018, 4, 25, 10, 30, 00).getTime());
		p.setEndTime(new GregorianCalendar(2018, 7, 12, 20, 00, 00).getTime());
		p.setTitle("Ventaneo");

		final Phase res = this.phaseService.save(p);
		Assert.notNull(res);

		super.unauthenticate();

	}

	@Test
	@Rollback
	public void findAllWorkerTest() {
		super.authenticate("handy6");
		final HandyWorker worker = (HandyWorker) this.actorService.findByUserAccountId(LoginService.getPrincipal().getId());

		final Collection<Phase> phases = this.phaseService.findAllWorker(worker);

		Assert.notNull(phases);
		super.unauthenticate();
	}

	@Test
	public void deleteTest() {
		super.authenticate("handy6");
		final HandyWorker worker = (HandyWorker) this.actorService.findByUserAccountId(LoginService.getPrincipal().getId());

		final Collection<Phase> phases = this.phaseService.findAllWorker(worker);
		final Phase p = phases.iterator().next();

		this.phaseService.delete(p);

		super.unauthenticate();
	}

}
