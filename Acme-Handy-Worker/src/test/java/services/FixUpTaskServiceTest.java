
package services;

import java.util.Collection;
import java.util.GregorianCalendar;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.LoginService;
import security.UserAccount;
import utilities.AbstractTest;
import domain.Actor;
import domain.Customer;
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
	@Autowired
	private ActorService		actorService;


	@Test
	public void saveAndFindOneGood() {
		final FixUpTask f = this.fixUpTaskService.create();

		super.authenticate("Customer1");
		final UserAccount userAccount = LoginService.getPrincipal();
		final Actor c = this.actorService.findByUserAccountId(userAccount.getId());
		// TODO: Esto no peta y debería porque no tenemos los valores necesarios.
		f.setCustomer((Customer) c);
		final FixUpTask f2 = this.fixUpTaskService.save(f);
		final FixUpTask f3 = this.fixUpTaskService.findOne(f2.getId());
		System.out.println("=============TEST SAVE AND FIND=================");
		System.out.println(f3.getId());

		Assert.isTrue(f3 != null);
		super.unauthenticate();

	}
	@Test
	public void saveAndFindOneBad() {
		super.authenticate(null);
		final FixUpTask f = this.fixUpTaskService.create();
		FixUpTask f3 = null;
		try {
			final UserAccount userAccount = LoginService.getPrincipal();
			final Actor c = this.actorService.findByUserAccountId(userAccount.getId());
			f.setCustomer((Customer) c);
			final FixUpTask f2 = this.fixUpTaskService.save(f);
			f3 = this.fixUpTaskService.findOne(f2.getId());

		} catch (final Exception e) {
			f3 = null;
		}
		Assert.isTrue(f3 == null);
		super.unauthenticate();
	}

	@Test
	public void deleteGood1() {
		final FixUpTask f = this.fixUpTaskService.create();

		super.authenticate("Customer1");
		final UserAccount userAccount = LoginService.getPrincipal();
		final Actor c = this.actorService.findByUserAccountId(userAccount.getId());
		f.setCustomer((Customer) c);
		final FixUpTask f2 = this.fixUpTaskService.save(f);
		this.fixUpTaskService.delete(f2);

		final FixUpTask f3 = this.fixUpTaskService.findOne(f2.getId());
		Assert.isTrue(f3 == null);
		super.unauthenticate();

	}

	@Test
	public void deleteGood2() {
		final FixUpTask f = this.fixUpTaskService.create();

		super.authenticate("Customer1");
		final UserAccount userAccount = LoginService.getPrincipal();
		final Actor c = this.actorService.findByUserAccountId(userAccount.getId());
		f.setCustomer((Customer) c);
		final FixUpTask f2 = this.fixUpTaskService.save(f);
		this.fixUpTaskService.delete(f2.getId());

		final FixUpTask f3 = this.fixUpTaskService.findOne(f2.getId());
		super.unauthenticate();
		Assert.isTrue(f3 == null);
	}

	@Test
	public void deleteBad() {
		final FixUpTask f = this.fixUpTaskService.create();

		super.authenticate(null);
		FixUpTask f2 = null;
		FixUpTask f3 = null;
		try {

			final UserAccount userAccount = LoginService.getPrincipal();
			final Actor c = this.actorService.findByUserAccountId(userAccount.getId());
			f.setCustomer((Customer) c);
			f2 = this.fixUpTaskService.save(f);
			this.fixUpTaskService.delete(f2.getId());
			f3 = this.fixUpTaskService.findOne(f2.getId());
		} catch (final Exception e) {

		}

		Assert.isTrue(f3 == null);
		super.unauthenticate();
	}
	@Test
	public void findAsHandyWorkerGood() {
		super.authenticate("handy1");
		final Collection<FixUpTask> f = this.fixUpTaskService.findAsHandyWorker();
		Assert.isTrue(f != null);
		super.unauthenticate();
	}
	@Test
	public void findAsHandyWorkerBad() {
		super.authenticate("Customer1");
		Collection<FixUpTask> f = null;
		try {
			f = this.fixUpTaskService.findAsHandyWorker();

		} catch (final Exception e) {
			f = null;
		}
		Assert.isTrue(f == null);
		super.unauthenticate();
	}
	@Test
	public void findFromCustomerGood1() {
		super.authenticate("Customer1");
		final Customer c = (Customer) this.actorService.findByUserAccountId(LoginService
			.getPrincipal().getId());
		final Collection<FixUpTask> f = this.fixUpTaskService.findFromCustomer(c.getId());
		Assert.isTrue(f != null);
		super.unauthenticate();
	}
	@Test
	public void findFromCustomerGood2() {
		super.authenticate("Customer1");
		final Customer c = (Customer) this.actorService.findByUserAccountId(LoginService
			.getPrincipal().getId());
		super.unauthenticate();
		super.authenticate("handy1");
		final Collection<FixUpTask> f = this.fixUpTaskService.findFromCustomer(c.getId());
		Assert.isTrue(f != null);
		super.unauthenticate();
	}

	@Test
	public void findFromCustomerBad() {
		super.authenticate("Customer1");
		final Customer c = (Customer) this.actorService.findByUserAccountId(LoginService
			.getPrincipal().getId());
		Collection<FixUpTask> f = null;
		try {
			super.unauthenticate();
			super.authenticate(null);
			f = this.fixUpTaskService.findFromCustomer(c.getId());
		} catch (final Exception e) {

		}
		Assert.isTrue(f == null);
		super.unauthenticate();
	}

	@Test
	public void findByFilterTest() {
		super.authenticate("handy1");
		final Collection<FixUpTask> f = this.fixUpTaskService.findByFilter("niño", null, null,
			null, null, new GregorianCalendar(2018, 2, 11, 19, 0, 0).getTime(),
			new GregorianCalendar(2018, 2, 11, 20, 0, 0).getTime());
		for (final FixUpTask a : f) {
			System.out.println("==========FIX UP TASK Test 0===========");
			System.out.println("Ticker: " + a.getTicker());
			System.out.println("Descripción: " + a.getDescription());
			System.out.println("Dirección: " + a.getAddress());
			System.out.println("Categoría: " + a.getCategory());
			System.out.println("Precio máximo: " + a.getMaxPrice());
			System.out.println("Fecha de inicio: " + a.getPeriodStart());
		}
		Assert.isTrue(f != null);
		super.unauthenticate();
	}

	@Test
	public void findByFilterTest1() {
		super.authenticate("handy1");
		final Collection<FixUpTask> f = this.fixUpTaskService.findByFilter("niño", null, null,
			null, null, null, null);
		for (final FixUpTask a : f) {
			System.out.println("==========FIX UP TASK Test 1===========");
			System.out.println("Ticker: " + a.getTicker());
			System.out.println("Descripción: " + a.getDescription());
			System.out.println("Dirección: " + a.getAddress());
			System.out.println("Categoría: " + a.getCategory());
			System.out.println("Precio máximo: " + a.getMaxPrice());
			System.out.println("Fecha de inicio: " + a.getPeriodStart());
		}
		Assert.isTrue(f != null);
		super.unauthenticate();
	}
}
