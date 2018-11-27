
package services;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Complaint;

// Indica que se tiene que ejecutar a través de Spring
@RunWith(SpringJUnit4ClassRunner.class)
//Indica los ficheros de configuración
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
//Para que la base de datos no quede incoherente
@Transactional
public class ComplaintServiceTest extends AbstractTest {

	@Autowired
	private ComplaintService	cService;


	@Test
	public void saveGood() {
		super.authenticate("Customer1");
		final Complaint c = this.cService.create();
		c.setAttachments(Arrays.asList("Attachment 1", "Attachment 2"));
		c.setComplaintTime(new Date());
		c.setDescription("Escucha la historia de como mi vida cambió mi movida");
		c.setIsFinal(false);
		this.cService.save(c);
		super.unauthenticate();
	}
	@Test
	public void saveBad() {
		super.authenticate("handy1");
		final Complaint c = this.cService.create();
		;
		Complaint c1 = null;
		try {
			c1 = this.cService.save(c);
		} catch (final Exception e) {
		}
		Assert.isNull(c1);
		super.unauthenticate();
	}

	@Test
	public void findFromLoggedCustomerGood() {
		super.authenticate("Customer2");
		final Collection<Complaint> c = this.cService.findFromLoggedCustomer();
		Assert.notNull(c);
		super.unauthenticate();
	}

	@Test
	public void findFromLoggedCustomerBad() {
		super.authenticate("handy1");
		Collection<Complaint> c = null;
		try {
			c = this.cService.findFromLoggedCustomer();
		} catch (final Exception e) {
		}
		Assert.isNull(c);
		super.unauthenticate();
	}
}
