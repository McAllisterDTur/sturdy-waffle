
package tests;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import repositories.HandyWorkerRepository;
import security.Authority;
import security.UserAccount;
import security.UserAccountRepository;
import services.HandyWorkerService;
import services.UserAccountService;
import domain.HandyWorker;

// Indica que se tiene que ejecutar a través de Spring
@RunWith(SpringJUnit4ClassRunner.class)
//Indica los ficheros de configuración
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
//Para que la base de datos no quede incoherente
@Transactional
public class HandyWorkerServiceTest {

	@Autowired
	private UserAccountService		account;
	@Autowired
	private UserAccountRepository	accountRepo;
	@Autowired
	private HandyWorkerService		worker;
	@Autowired
	private HandyWorkerRepository	workerRepo;


	@Test
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
		final UserAccount aF = this.accountRepo.save(a);

		//Añadimos los valores restantes al worker
		w.setAccount(aF);
		//Setteamos los valores 'name', 'surname', 'address', 'banned', 'email', 'phone' y 'photoURL'
		w.setAddress("Av. Tres Mil Viviendas");
		w.setEmail("chapuzas3000@gmail.com");
		w.setBanned(false);
		w.setPhone("+34 666 00 3000");
		w.setName("Fulgencio");
		w.setSurname("Ramirez");
		w.setPhotoURL("https://www.tuenti.com/FulgenR/Albun:?noxesitawena/1682903.jpg");

		//Guardamos el nuevo worker en la base de datos
		final HandyWorker wF = this.workerRepo.save(w);
		//Verificamos que no es un objeto nulo
		Assert.notNull(wF);
		//Verificamos que el id se ha asignado correctamente
		//Assert.isTrue(wF.getId() != 0);

		//Borramos los elementos creados para no incurrir en duplicidades
		this.accountRepo.delete(aF);
		this.workerRepo.delete(wF);

	}

}
