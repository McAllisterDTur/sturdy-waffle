
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.Authority;
import security.UserAccount;
import utilities.AbstractTest;
import domain.Actor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ActorServiceTest extends AbstractTest {

	@Autowired
	private ActorService	actorService;


	@Test
	public void CreateGoodTest() {
		final Actor a = new Actor();
		final Actor ac = this.actorService.create();
		Assert.isTrue(a.equals(ac));
	}

	@Test
	public void SaveNewGoodTest() {
		final Actor a = new Actor();
		final UserAccount account = new UserAccount();
		final Authority auth = new Authority();
		auth.setAuthority(Authority.CUSTOMER);
		account.addAuthority(auth);
		a.setAccount(account);
		a.setName("Juan");
		a.setSurname("Nadie");
		a.setEmail("juan@nadie");
		a.setBanned(false);
		final Actor ac = this.actorService.save(a);
		a.setId(ac.getId());
		a.setVersion(ac.getVersion());
		Assert.isTrue(a.equals(ac));
	}

	@Test
	public void SaveNewbBadTest() {
		final Actor a = new Actor();
		final UserAccount account = new UserAccount();
		final Authority auth = new Authority();
		auth.setAuthority(Authority.CUSTOMER);
		account.addAuthority(auth);
		a.setAccount(account);
		a.setName("Juan");
		a.setSurname("Nadie");
		a.setEmail("juan@nadie");
		a.setBanned(true);
		final Actor ac = this.actorService.save(a);
		Assert.isTrue(!ac.getBanned());

	}

	@Test
	public void SaveBanGoodTest() {
		super.authenticate("admin");
		final Actor a = new Actor();
		final UserAccount account = new UserAccount();
		final Authority auth = new Authority();
		auth.setAuthority(Authority.CUSTOMER);
		account.addAuthority(auth);
		a.setAccount(account);
		a.setName("Juan");
		a.setSurname("Nadie");
		a.setEmail("juan@nadie");
		a.setBanned(false);
		final Actor ac = this.actorService.save(a);

		ac.setBanned(true);

		final Actor act = this.actorService.save(ac);

		Assert.isTrue(act.getBanned());
		super.unauthenticate();
	}

	@Test
	public void SaveBanBadTest() {
		super.authenticate("Customer1");
		final Actor a = new Actor();
		final UserAccount account = new UserAccount();
		final Authority auth = new Authority();
		auth.setAuthority(Authority.CUSTOMER);
		account.addAuthority(auth);
		a.setAccount(account);
		a.setName("Juan");
		a.setSurname("Nadie");
		a.setEmail("juan@nadie");
		a.setBanned(false);
		final Actor ac = this.actorService.save(a);

		ac.setBanned(true);

		final Actor act = this.actorService.save(ac);

		Assert.isTrue(!act.getBanned());
		super.unauthenticate();
	}

	@Test
	public void SaveBadTest() {
		super.authenticate("Customer1");
		final Actor a = new Actor();
		final UserAccount account = new UserAccount();
		final Authority auth = new Authority();
		auth.setAuthority(Authority.CUSTOMER);
		account.addAuthority(auth);
		a.setAccount(account);
		a.setName("Juan");
		a.setSurname("Nadie");
		a.setEmail("juan@nadie");
		a.setBanned(false);
		final Actor ac = this.actorService.save(a);
		ac.setAddress("Sin número");
		final Actor act = this.actorService.save(ac);
		Assert.isTrue(act.equals(ac));
		super.unauthenticate();
	}

	@Test
	public void SaveBadAdminTest() {
		super.authenticate("Customer1");
		final Actor a = new Actor();
		final UserAccount account = new UserAccount();
		final Authority auth = new Authority();
		auth.setAuthority(Authority.ADMIN);
		account.addAuthority(auth);
		a.setAccount(account);
		a.setName("Juan");
		a.setSurname("Nadie");
		a.setEmail("juan@nadie");
		a.setBanned(false);
		Actor ac = null;
		try {
			ac = this.actorService.save(a);
		} catch (final Exception e) {
			ac = null;
		}
		Assert.isNull(ac);
		super.unauthenticate();
	}
	/*
	 * @Test
	 * public void SaveGoodTest() {
	 * super.authenticate("Customer1");
	 * final Actor a = new Actor();
	 * final UserAccount account = new UserAccount();
	 * final Authority auth = new Authority();
	 * auth.setAuthority(Authority.ADMIN);
	 * account.addAuthority(auth);
	 * a.setAccount(account);
	 * a.setName("Juan");
	 * a.setSurname("Nadie");
	 * a.setEmail("juan@nadie");
	 * a.setBanned(false);
	 * Actor ac = null;
	 * try {
	 * ac = this.actorService.save(a);
	 * } catch (final Exception e) {
	 * ac = null;
	 * }
	 * Assert.isNull(ac);
	 * super.unauthenticate();
	 * }
	 */
	@Test
	public void FindAllGoodTest() {
		final Actor a = new Actor();
		final UserAccount account = new UserAccount();
		final Authority auth = new Authority();
		auth.setAuthority(Authority.CUSTOMER);
		account.addAuthority(auth);
		a.setAccount(account);
		a.setName("Juan");
		a.setSurname("Nadie");
		a.setEmail("juan@nadie");
		a.setBanned(false);
		this.actorService.save(a);
		final Collection<Actor> act = this.actorService.findAll();
		Assert.isTrue(!(act.isEmpty()));
	}

	@Test
	public void FindOneGoodTest() {
		super.authenticate("admin");
		final Actor a = new Actor();
		final UserAccount account = new UserAccount();
		final Authority auth = new Authority();
		auth.setAuthority(Authority.CUSTOMER);
		account.addAuthority(auth);
		a.setAccount(account);
		a.setName("Juan");
		a.setSurname("Nadie");
		a.setEmail("juan@nadie");
		a.setBanned(false);
		final Actor ac = this.actorService.save(a);
		final int id = ac.getId();
		final Actor ab = this.actorService.findOne(id);
		Assert.isTrue(ac.equals(ab));
		super.unauthenticate();

	}
}
