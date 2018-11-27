
package tests;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.LoginService;
import services.ActorService;
import services.EndorsementService;
import utilities.AbstractTest;
import domain.Application;
import domain.Customer;
import domain.Endorsement;
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
public class EndorsementServiceTest extends AbstractTest {

	@Autowired
	private EndorsementService	endorsementService;
	@Autowired
	private ActorService		actorService;


	@Test
	public void testCreateAndSave() {

		final Endorsement e = this.endorsementService.create();
		Assert.notNull(e);
		System.out.println("Endorsement vacío creado");
		super.authenticate("Customer4");
		final Customer c = (Customer) this.actorService.findByUserAccountId(LoginService.getPrincipal().getId());
		System.out.println("Obtenido el customer loggeado");
		//Aqui selecciono el HandyWorker que tiene un Application "Accepted" para un fixUpTask del Cliente
		Application accepted = null;
		System.out.println("Buscando la application aceptada");
		for (final FixUpTask f : c.getFixUpTasks()) {
			int i = 0;
			while (f.getApplications().iterator().hasNext()) {
				Application a;
				a = (Application) f.getApplications().toArray()[i];
				if (a.getStatus().equals("ACCEPTED")) {
					accepted = a;
					System.out.println("application encontrada");
					break;
				}
				i++;
			}
		}

		System.out.println("setteando los valores");
		e.setComment("A comment");
		e.setSender(c);
		e.setReciever(accepted.getHandyWorker());
		e.setWriteTime(new Date());

		System.out.println("Guardando en BBDD");
		final Endorsement res = this.endorsementService.save(e);
		Assert.notNull(res);

		super.unauthenticate();
	}

	@Test
	public void testFindCustomer() {
		super.authenticate("Customer2");
		final Customer c = (Customer) this.actorService.findByUserAccountId(LoginService.getPrincipal().getId());
		final Collection<Endorsement> res = this.endorsementService.findAllCustomer(c.getId());

		Assert.notNull(res);

		super.unauthenticate();
	}

	@Test
	public void testFindWorker() {
		super.authenticate("handy2");

		final HandyWorker w = (HandyWorker) this.actorService.findByUserAccountId(LoginService.getPrincipal().getId());
		final Collection<Endorsement> res = this.endorsementService.findAllWorker(w.getId());

		Assert.notNull(res);

		super.unauthenticate();
	}

}
