
package services;

import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import security.Authority;
import security.LoginService;
import security.UserAccount;
import utilities.AbstractTest;
import domain.Actor;
import domain.Box;
import domain.Message;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class MessageServiceTest extends AbstractTest {

	//Service that we are testing
	@Autowired
	MessageService		msgService;
	//Auxiliary services
	@Autowired
	ActorService		aService;
	@Autowired
	BoxService			bService;
	@Autowired
	UserAccountService	uaService;


	@Test
	public void createTest() {
		final Message m = this.msgService.create();
		Assert.notNull(m);
	}

	@Test
	public void findAllTest() {
		super.authenticate("Customer1");
		final Collection<Message> all = this.msgService.findAll();
		Assert.notEmpty(all);
	}

	@Test
	public void findOneTest() {
		super.authenticate("Customer1");
		final Message m = this.msgService.findAll().iterator().next();
		Assert.isTrue(this.msgService.findOne(m.getId()).equals(m));
	}

	@Test
	public void deleteTest() {
		super.authenticate("Customer1");
		final Message m = (Message) this.msgService.findAll().toArray()[0];
		final Actor owner = this.aService.findByUserAccountId(LoginService.getPrincipal().getId());
		this.msgService.delete(m);
		final Box trashU = this.bService.findByName(owner.getId(), "TRASH");
		Assert.isTrue(this.msgService.findByBox(trashU).contains(m));
	}

	@Test
	public void deleteFromTrashTest() {
		super.authenticate("Customer1");
		final Actor owner = this.aService.findByUserAccountId(LoginService.getPrincipal().getId());
		final Message m = this.msgService.findAll().iterator().next();
		this.msgService.delete(m);
		this.msgService.deleteFromTrash(m);
		final Box trashU = this.bService.findByName(owner.getId(), "TRASH");
		Assert.isTrue(!trashU.getMessages().contains(m));
	}
	@Test
	public void sendTest() {
		final Message m = this.msgService.create();
		m.setBody("Test");
		m.setPriority("NEUTRAL");
		m.setSubject("Test");
		final Actor a = this.aService.create();

		final UserAccount ac = new UserAccount();
		ac.setUsername("customerTest");
		ac.setPassword("customerTest");
		final Authority ath = new Authority();
		ath.setAuthority(Authority.CUSTOMER);
		ac.addAuthority(ath);
		final UserAccount sac = this.uaService.save(ac);

		a.setAccount(sac);
		a.setAddress("Test");
		a.setBanned(false);
		a.setEmail("test@tested.com");
		a.setName("Test");
		a.setPhone("678912345");
		a.setPhotoURL("http://tiniutl.com/h/uegmnsas.png");
		a.setSurname("Testing");
		final Actor sa = this.aService.save(a);

		super.authenticate("customerTest");
		this.bService.initializeDefaultBoxes();
		super.unauthenticate();

		super.authenticate("Customer1");
		m.setBody("Test");
		m.setPriority("NEUTRAL");
		m.setSubject("Test");
		final Date d = new Date();
		d.setTime(895442400000L);
		m.setSendTime(d);
		final Message ms = this.msgService.send(m, sa);
		super.unauthenticate();

		super.authenticate("customerTest");
		Assert.isTrue(this.msgService.findAll().contains(ms));
	}

	@Test
	public void findByBoxTest() {
		//First, we log as a customer (for example)
		super.authenticate("Customer1");
		//We get the actor that is logged with that user
		final Actor owner = this.aService.findByUserAccountId(LoginService.getPrincipal().getId());
		final Box b = this.bService.findByName(owner.getId(), "IN");
		Assert.notEmpty(this.msgService.findByBox(b));
	}
}
