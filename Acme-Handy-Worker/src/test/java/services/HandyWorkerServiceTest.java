
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

import security.Authority;
import security.LoginService;
import security.UserAccount;
import utilities.AbstractTest;
import domain.HandyWorker;

// Indica que se tiene que ejecutar a trav�s de Spring
@RunWith(SpringJUnit4ClassRunner.class)
//Indica los ficheros de configuraci�n
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
//Para que la base de datos no quede incoherente
@Transactional
public class HandyWorkerServiceTest extends AbstractTest {

	@Autowired
	private UserAccountService	account;
	@Autowired
	private HandyWorkerService	worker;
	@Autowired
	private ActorService		actorService;


	@Test
	@Rollback
	public void createAndSave() {

		//Creamos un nuevo worker
		final HandyWorker w = this.worker.create();
		Assert.notNull(w);

		//Creamos un UserAccount para este worker
		final UserAccount a = this.account.create();

		//Asignamos los valores 'username', 'pass1' y 'authorities'
		a.setUsername("prueba1");
		a.setPassword("pass1");

		//Creamos un nuevo Authority
		final Authority auth = new Authority();

		//Asignamos el valor 'HANDYWORKER'
		auth.setAuthority(Authority.HANDYWORKER);
		a.addAuthority(auth);

		//Almacenamos el valor en al base de datos
		final UserAccount aF = this.account.save(a);

		//A�adimos los valores restantes al worker
		w.setAccount(aF);
		//Setteamos los valores 'name', 'surname', 'address', 'banned', 'email', 'phone' y 'photoURL'
		w.setAddress("Av. Tres Mil Viviendas");
		w.setEmail("chapuzas3000@gmail.com");
		w.setBanned(false);
		w.setPhone("+34 666 00 3000");
		w.setName("Fulgencio");
		w.setSurname("Ramirez");
		w.setPhotoURL("https://www.tuenti.com/FulgenR/Albun:?noxesitawena/1682903.jpg");
		w.setMake("FRChapuzas");
		//w.setScore(0.0);

		//Guardamos el nuevo worker en la base de datos
		final HandyWorker wF = this.worker.save(w);
		//Verificamos que no es un objeto nulo
		Assert.notNull(wF);

	}

	@Test
	public void testFindOne() {
		super.authenticate("handy3");
		final HandyWorker w = (HandyWorker) this.actorService.findByUserAccountId(LoginService.getPrincipal().getId());
		Assert.notNull(w);
	}

	@Test
	public void testAverage() {
		super.authenticate("admin");

		final Collection<HandyWorker> res = this.worker.findWorkerMoreAverage();

		Assert.notNull(res);

		super.unauthenticate();
	}

	@Test
	public void testTop3Workers() {
		super.authenticate("admin");

		final Collection<HandyWorker> res = this.worker.findTop3HandyWorkers();

		Assert.notNull(res);
		Assert.notEmpty(res);

		super.unauthenticate();
	}
}
