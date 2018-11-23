
package tests;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import repositories.CustomerRepository;
import security.Authority;
import security.UserAccount;
import security.UserAccountRepository;
import services.CustomerService;
import services.UserAccountService;
import domain.Customer;

// Indica que se tiene que ejecutar a través de Spring
@RunWith(SpringJUnit4ClassRunner.class)
//Indica los ficheros de configuración
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
//Para que la base de datos no quede incoherente
@Transactional
public class CustomerServiceTest {

	@Autowired
	private CustomerService			customerService;
	@Autowired
	private UserAccountService		userAccountService;

	@Autowired
	private UserAccountRepository	accountRepo;
	@Autowired
	private CustomerRepository		customerRepo;


	@Test
	public void testCreateYSave() {
		//creamos un nuevo customer
		final Customer c = this.customerService.create();
		//Verificamos que no es null
		Assert.notNull(c);

		//Creamos un nuevo account para el customer
		final UserAccount account = this.userAccountService.create();

		//Asignamos los valores 'username', 'pass1' y 'authorities'
		account.setUsername("prueba1");
		account.setPassword("pass1");
		//Creamos un nuevo Authority
		final Authority auth = new Authority();
		//Asignamos el valor 'CUSTOMER'
		auth.setAuthority(Authority.CUSTOMER);
		account.addAuthority(auth);

		//Guardamos el userAccount
		final UserAccount a1 = this.userAccountService.save(account);

		//Asignamos el account al nuevo customer
		c.setAccount(a1);

		//Setteamos los valores 'address', 'banned', 'email', 'name', 'surname', 'phone' y 'photoURL'
		c.setAddress("C/Primera 10");
		c.setBanned(false);
		c.setEmail("mailprueba@gmail.com");
		c.setName("CustomerPrueba");
		c.setSurname("Probado Probador");
		c.setPhone("+34 666 777 812");
		c.setPhotoURL("https://tyniurl.com/profilePicture.jpg");

		//Guardamos el nuevo customer
		final Customer c1 = this.customerService.save(c);
		//Verificamos que el custmoer ha sido añadido
		Assert.notNull(c1);

		//Borramos las instacias creadas para evitar duplicidades
		this.customerRepo.delete(c1);
		this.accountRepo.delete(a1);
	}
}
