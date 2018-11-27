
package services;

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
import domain.Customer;

// Indica que se tiene que ejecutar a trav�s de Spring
@RunWith(SpringJUnit4ClassRunner.class)
//Indica los ficheros de configuraci�n
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
//Para que la base de datos no quede incoherente
@Transactional
public class ActorServiceTest extends AbstractTest {

	@Autowired
	public ActorService	actorService;


	@Test
	public void findByAccountTest() {
		super.authenticate("Customer1");

		final UserAccount userAccount = LoginService.getPrincipal();

		final Customer c = (Customer) this.actorService.findByUserAccountId(userAccount.getId());

		System.out.println(c);
		Assert.isTrue(c != null);

		super.unauthenticate();

	}
}