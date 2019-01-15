
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import security.LoginService;
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
	private MessageService		msgService;
	//Auxiliary services
	@Autowired
	private ActorService		aService;
	@Autowired
	private BoxService			bService;
	@Autowired
	private UserAccountService	uaService;


	@Test
	public void createTest() {
		super.authenticate("Customer1");
		final Actor a = this.aService.findByUserAccountId(LoginService.getPrincipal().getId());
		final Message m = this.msgService.create(a);
		Assert.notNull(m);
		super.unauthenticate();

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
		this.msgService.delete(m, m.getBoxes().iterator().next());
		final Box trashU = this.msgService.checkSystemBox(this.bService.findByName(owner.getId(), "TRASH"));

		Assert.isTrue(this.msgService.findByBox(trashU).contains(m));
	}

	@Test
	public void sendTest() {
		super.authenticate("Customer2");
		final Actor reciever = this.aService.findByUserAccountId(LoginService.getPrincipal().getId());
		super.unauthenticate();
		super.authenticate("Customer1");
		final Actor owner = this.aService.findByUserAccountId(LoginService.getPrincipal().getId());
		final Message m = this.msgService.create(owner);
		m.setBody("Test");
		m.setPriority("NEUTRAL");
		m.setSubject("Test");
		final Collection<Actor> receivers = new ArrayList<>();
		receivers.add(reciever);
		m.setReciever(receivers);
		final Message ms = this.msgService.send(m, reciever);
		super.unauthenticate();

		super.authenticate("Customer2");
		Assert.isTrue(this.msgService.findOne(ms.getId()).equals(ms));

		super.unauthenticate();
	}
	@Test
	public void findByBoxTest() {
		//First, we log as a customer (for example)
		super.authenticate("Customer1");
		//We get the actor that is logged with that user
		final Actor owner = this.aService.findByUserAccountId(LoginService.getPrincipal().getId());
		final Box b = this.msgService.checkSystemBox((this.bService.findByName(owner.getId(), "IN")));
		Assert.notEmpty(this.msgService.findByBox(b));
	}
}
