
package tests;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.Authority;
import security.UserAccount;
import services.UserAccountService;

// Indica que se tiene que ejecutar a través de Spring
@RunWith(SpringJUnit4ClassRunner.class)
//Indica los ficheros de configuración
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
//Para que la base de datos no quede incoherente
@Transactional
public class UserAccountTest {

	@Autowired
	private UserAccountService	serv;


	@Test
	@Rollback
	public void CreateAndSave() {
		//Creamos el account vacío
		final UserAccount account = this.serv.create();
		//Nos aseguramos que no sea null
		Assert.notNull(account);

		//Setteamos los parametros 'username', 'password' y 'authoprities'
		account.setUsername("Prueba1");
		account.setPassword("Contrasenna");
		//Creamos un nuevo 'Authority' necesario para settear el valor
		final Authority a = new Authority();
		a.setAuthority(Authority.ADMIN);
		account.addAuthority(a);

		//Guardamos el 'account' ya creado
		final UserAccount res = this.serv.save(account);
		//Vemos que no sea 'null'
		Assert.notNull(res);

		//Vemos su id para comprobar que esté bien guardado
		Assert.isTrue(res.getId() != 0);

		//		//Borramos el account creado para no concurrir en duplicidad
		//		this.repo.delete(res);
	}

}
