
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
	public void testCreate() {
		final Message m = this.msgService.create();
		Assert.notNull(m);
	}

	@Test
	public void testFindAll() {
		super.authenticate("Customer1");
		final Collection<Message> all = this.msgService.findAll();
		Assert.notEmpty(all);
	}

	@Test
	public void testSave() {
		super.authenticate("Customer1");
		final Message m = this.msgService.create();
		m.setBody("Test");
		m.setPriority("NEUTRAL");
		m.setSubject("Test");
		final Message saved = this.msgService.save(m);
		Assert.isTrue(this.msgService.findAll().contains(saved));
	}

	@Test
	public void testFindOne() {
		super.authenticate("Customer1");
		final Message m = this.msgService.create();
		m.setBody("Test");
		m.setPriority("NEUTRAL");
		m.setSubject("Test");
		final Message saved = this.msgService.save(m);
		Assert.isTrue(this.msgService.findOne(saved.getId()).equals(saved));
	}

	@Test
	public void testUpdate() {
		super.authenticate("Customer1");
		final Message toUpdate = (Message) this.msgService.findAll().toArray()[0];
		toUpdate.setBody("Updated");
		final Message m = this.msgService.update(toUpdate);
		Assert.isTrue(this.msgService.findOne(m.getId()).getBody().equals(toUpdate.getBody()));
	}

	@Test
	public void testDelete() {
		super.authenticate("Customer1");
		final Message m = (Message) this.msgService.findAll().toArray()[0];
		final Actor owner = this.aService.findByUserAccountId(LoginService.getPrincipal().getId());
		this.msgService.delete(m);
		final Box trashU = this.bService.findByName(owner.getId(), "TRASH");
		Assert.isTrue(this.msgService.findByBox(trashU).contains(m));
	}

	@Test
	public void testDeleteFromTrash() {
		super.authenticate("Customer1");
		final Actor owner = this.aService.findByUserAccountId(LoginService.getPrincipal().getId());
		final Message m = this.msgService.create();
		final Box trash = this.bService.findByName(owner.getId(), "TRASH");
		final Collection<Box> bxs = new ArrayList<>();
		bxs.add(trash);
		m.setBody("Test");
		m.setPriority("NEUTRAL");
		m.setSubject("Test");
		m.setBoxes(bxs);
		final Message ms = this.msgService.save(m);
		final Collection<Message> newMsg = this.msgService.findByBox(trash);
		newMsg.add(ms);
		trash.setMessages(newMsg);
		this.bService.save(trash);
		this.msgService.deleteFromTrash(ms);
		final Box trashU = this.bService.findByName(owner.getId(), "TRASH");
		Assert.isTrue(!trashU.getMessages().contains(ms));
	}
	@Test
	public void testSend() {
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
		final Message ms = this.msgService.save(m);
		this.msgService.send(ms, sa);
		super.unauthenticate();

		super.authenticate("customerTest");
		Assert.isTrue(this.msgService.findAll().contains(ms));
	}

	@Test
	public void testFindByBox() {
		//First, we log as a customer (for example)
		super.authenticate("Customer2");
		//We get the actor that is logged with that user
		final Actor owner = this.aService.findByUserAccountId(LoginService.getPrincipal().getId());
		//Now we create a box
		final Box n = this.bService.create();
		n.setDeleteable(true);
		n.setName("TEST BOX");
		n.setOwner(owner);
		n.setMessages(null);
		//And now we save it
		final Box b = this.bService.save(n);
		final Message m = this.msgService.create();
		m.setBody("Test");
		m.setPriority("NEUTRAL");
		m.setSubject("Test");
		final Collection<Box> boxes = new ArrayList<>();
		boxes.add(b);
		m.setBoxes(boxes);
		final Message saved = this.msgService.save(m);
		final Collection<Message> msgs = new ArrayList<>();
		msgs.add(saved);
		b.setMessages(msgs);
		Assert.notEmpty(this.msgService.findByBox(b));
	}
}
