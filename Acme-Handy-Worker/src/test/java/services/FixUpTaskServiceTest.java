
package services;

import java.util.Arrays;
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
import domain.Warranty;

// Indica que se tiene que ejecutar a trav�s de Spring
@RunWith(SpringJUnit4ClassRunner.class)
// Indica los ficheros de configuraci�n
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
	@Autowired
	private WarrantyService		warrantyService;


	@Test
	public void saveAndFindOneGood() {
		super.authenticate("Customer1");
		final FixUpTask f = this.fixUpTaskService.create();

		final UserAccount userAccount = LoginService.getPrincipal();
		final Actor c = this.actorService.findByUserAccountId(userAccount.getId());
		f.setCustomer((Customer) c);
		super.unauthenticate();
		super.authenticate("admin");
		final Warranty w = this.warrantyService.create();
		w.setDraft(false);
		w.setLaw(Arrays.asList("Ley 1", "Ley 2"));
		w.setTerms("T�rminos");
		w.setTitle("La ley de la selva");
		f.setWarranty(this.warrantyService.save(w));
		f.setAddress("Godric's Hollow");
		f.setDescription("Por favor maten a Voldemort. Gracias.");
		f.setMaxPrice(50d);
		f.setPeriodStart(new GregorianCalendar(2020, 0, 1).getTime());
		f.setPeriodEnd(new GregorianCalendar(2021, 0, 1).getTime());
		super.unauthenticate();
		super.authenticate("Customer1");
		final FixUpTask f2 = this.fixUpTaskService.save(f);
		final FixUpTask f3 = this.fixUpTaskService.findOne(f2.getId());
		System.out.println("=============TEST SAVE AND FIND=================");
		System.out.println(f3.getId());

		Assert.isTrue(f3 != null);
		super.unauthenticate();
	}
	@Test
	public void saveAndFindOneBad() {
		super.authenticate("Customer1");
		final FixUpTask f = this.fixUpTaskService.create();
		super.unauthenticate();
		super.authenticate(null);
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
	public void avgMinMaxDevFixUpTaskCountGood() {
		super.authenticate("admin");
		final Collection<Object[]> c = this.fixUpTaskService.avgMinMaxDevFixUpTaskCount();
		Assert.notNull(c);
		super.unauthenticate();
	}

	@Test
	public void avgMinMaxDevFixUpTaskPriceGood() {
		super.authenticate("admin");
		final Collection<Object[]> c = this.fixUpTaskService.avgMinMaxDevFixUpTaskPrice();
		Assert.notNull(c);
		super.unauthenticate();
	}

	@Test
	public void ratioFixUpTaskComplaintGood() {
		super.authenticate("admin");
		final Double c = this.fixUpTaskService.ratioFixUpTaskComplaint();
		Assert.notNull(c);
		super.unauthenticate();
	}

	@Test
	public void avgMinMaxDevFixUpTaskCountBad() {
		super.authenticate("Customer1");
		Collection<Object[]> c = null;
		try {
			c = this.fixUpTaskService.avgMinMaxDevFixUpTaskCount();
		} catch (final Exception e) {
		}
		Assert.isNull(c);
		super.unauthenticate();
	}

	@Test
	public void avgMinMaxDevFixUpTaskPriceBad() {
		super.authenticate("Customer1");
		Collection<Object[]> c = null;
		try {
			c = this.fixUpTaskService.avgMinMaxDevFixUpTaskPrice();
		} catch (final Exception e) {
		}
		Assert.isNull(c);
		super.unauthenticate();
	}

	@Test
	public void ratioFixUpTaskComplaintBad() {
		super.authenticate("Customer1");
		Double c = null;
		try {
			c = this.fixUpTaskService.ratioFixUpTaskComplaint();
		} catch (final Exception e) {
		}
		Assert.isNull(c);
		super.unauthenticate();
	}

	@Test
	public void saveAndFindOneBad2() {
		super.authenticate("Customer1");
		final FixUpTask f = this.fixUpTaskService.create();
		super.unauthenticate();

		super.authenticate("admin");
		final Warranty w = this.warrantyService.create();
		w.setDraft(true);
		w.setLaw(Arrays.asList("Ley 1", "Ley 2"));
		w.setTerms("T�rminos");
		w.setTitle("La ley de la selva");
		f.setWarranty(w);
		FixUpTask f1 = null;
		try {
			super.unauthenticate();
			super.authenticate("Customer2");
			f1 = this.fixUpTaskService.save(f);
		} catch (final Exception e) {
		}
		Assert.isNull(f1);
		super.unauthenticate();
	}

	@Test
	public void deleteGood1() {
		super.authenticate("Customer1");
		final FixUpTask f = this.fixUpTaskService.create();

		final UserAccount userAccount = LoginService.getPrincipal();
		final Actor c = this.actorService.findByUserAccountId(userAccount.getId());
		f.setCustomer((Customer) c);
		super.unauthenticate();

		super.authenticate("admin");
		final Warranty w = this.warrantyService.create();
		w.setDraft(false);
		w.setLaw(Arrays.asList("Ley 1", "Ley 2"));
		w.setTerms("T�rminos");
		w.setTitle("La ley de la selva");
		f.setWarranty(this.warrantyService.save(w));
		f.setAddress("Godric's Hollow");
		f.setDescription("Por favor maten a Voldemort. Gracias.");
		f.setMaxPrice(50d);
		f.setPeriodStart(new GregorianCalendar(2020, 0, 1).getTime());
		f.setPeriodEnd(new GregorianCalendar(2021, 0, 1).getTime());
		super.unauthenticate();

		super.authenticate("Customer1");
		final FixUpTask f2 = this.fixUpTaskService.save(f);
		this.fixUpTaskService.delete(f2);

		final FixUpTask f3 = this.fixUpTaskService.findOne(f2.getId());
		Assert.isTrue(f3 == null);
		super.unauthenticate();

	}
	@Test
	public void deleteGood2() {
		super.authenticate("Customer1");
		final FixUpTask f = this.fixUpTaskService.create();

		final UserAccount userAccount = LoginService.getPrincipal();
		final Actor c = this.actorService.findByUserAccountId(userAccount.getId());
		f.setCustomer((Customer) c);
		super.unauthenticate();
		super.authenticate("admin");
		final Warranty w = this.warrantyService.create();
		w.setDraft(false);
		w.setLaw(Arrays.asList("Ley 1", "Ley 2"));
		w.setTerms("T�rminos");
		w.setTitle("La ley de la selva");
		f.setWarranty(this.warrantyService.save(w));
		f.setAddress("Godric's Hollow");
		f.setDescription("Por favor maten a Voldemort. Gracias.");
		f.setMaxPrice(50d);
		f.setPeriodStart(new GregorianCalendar(2020, 0, 1).getTime());
		f.setPeriodEnd(new GregorianCalendar(2021, 0, 1).getTime());
		super.unauthenticate();
		super.authenticate("Customer1");
		final FixUpTask f2 = this.fixUpTaskService.save(f);

		this.fixUpTaskService.delete(f2.getId());

		final FixUpTask f3 = this.fixUpTaskService.findOne(f2.getId());
		super.unauthenticate();
		Assert.isTrue(f3 == null);
	}
	@Test
	public void deleteBad() {
		super.authenticate("Customer1");
		final FixUpTask f = this.fixUpTaskService.create();
		super.unauthenticate();

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
		final Collection<FixUpTask> f = this.fixUpTaskService.findAll();
		Assert.isTrue(f != null);
		super.unauthenticate();
	}
	@Test
	public void findAsHandyWorkerBad() {
		super.authenticate("Customer1");
		Collection<FixUpTask> f = null;
		try {
			f = this.fixUpTaskService.findAll();

		} catch (final Exception e) {
			f = null;
		}
		Assert.isTrue(f == null);
		super.unauthenticate();
	}

	@Test
	public void findFromCustomerGood1() {
		super.authenticate("Customer1");
		final Customer c = (Customer) this.actorService.findByUserAccountId(LoginService.getPrincipal().getId());
		super.unauthenticate();
		super.authenticate("handy1");
		final Collection<FixUpTask> f = this.fixUpTaskService.findFromCustomer(c.getId());
		Assert.isTrue(f != null);
		super.unauthenticate();
	}

	@Test
	public void findFromCustomerBad() {
		super.authenticate("Customer1");
		final Customer c = (Customer) this.actorService.findByUserAccountId(LoginService.getPrincipal().getId());
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
		final Collection<FixUpTask> f = this.fixUpTaskService.findByFilter("ni�o", null, null, null, null, new GregorianCalendar(2018, 2, 11, 19, 0, 0).getTime(), new GregorianCalendar(2018, 2, 11, 20, 0, 0).getTime());
		for (final FixUpTask a : f) {
			System.out.println("==========FIX UP TASK Test 0===========");
			System.out.println("Ticker: " + a.getTicker());
			System.out.println("Descripci�n: " + a.getDescription());
			System.out.println("Direcci�n: " + a.getAddress());
			System.out.println("Categor�a: " + a.getCategory());
			System.out.println("Precio m�ximo: " + a.getMaxPrice());
			System.out.println("Fecha de inicio: " + a.getPeriodStart());
		}
		Assert.isTrue(f != null);
		super.unauthenticate();
	}

	@Test
	public void findByFilterTest1() {
		super.authenticate("handy1");
		final Collection<FixUpTask> f = this.fixUpTaskService.findByFilter("ni�o", null, null, null, null, null, null);
		for (final FixUpTask a : f) {
			System.out.println("==========FIX UP TASK Test 1===========");
			System.out.println("Ticker: " + a.getTicker());
			System.out.println("Descripci�n: " + a.getDescription());
			System.out.println("Direcci�n: " + a.getAddress());
			System.out.println("Categor�a: " + a.getCategory());
			System.out.println("Precio m�ximo: " + a.getMaxPrice());
			System.out.println("Fecha de inicio: " + a.getPeriodStart());
		}
		Assert.isTrue(f != null);
		super.unauthenticate();
	}
}
