
package services;

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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class BoxServiceTest extends AbstractTest {

	//Service that we are testing
	@Autowired
	BoxService		boxService;

	//Auxiliary services
	@Autowired
	ActorService	actorService;


	@Test
	public void createTest() {
		//We create a box
		final Actor a = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId());
		final Box box = this.boxService.create(a);
		//Did we make it?
		Assert.notNull(box);
	}

	@Test
	public void findAllTest() {
		//Let's find all the boxes
		final Collection<Box> allBoxes = this.boxService.findAll();
		//Is this working?
		Assert.notEmpty(allBoxes);
	}

	@Test
	public void saveTest() {
		//First, we log as a customer (for example)
		super.authenticate("Customer2");
		//We get the actor that is logged with that user
		final Actor owner = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId());
		//Now we create a box
		final Box n = this.boxService.create(owner);
		n.setDeleteable(true);
		n.setName("TEST BOX");
		n.setOwner(owner);
		n.setMessages(null);
		//And now we save it
		final Box saved = this.boxService.save(n);
		//Did it work?
		Assert.isTrue(this.boxService.findAll().contains(saved));
	}

	@Test
	public void findOneTest() {
		//First, we log as a customer (for example)
		super.authenticate("Customer2");
		//We get the actor that is logged with that user
		final Actor owner = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId());
		//Now we create a box
		final Box n = this.boxService.create(owner);
		n.setDeleteable(true);
		n.setName("TEST BOX");
		n.setOwner(owner);
		n.setMessages(null);
		//And now we save it
		final Box saved = this.boxService.save(n);
		//Can we find it?
		Assert.isTrue(saved.equals(this.boxService.findOne(saved.getId())));
	}

	@Test
	public void deleteTest() {
		//First, we log as a customer (for example)
		super.authenticate("Customer2");
		//We get the actor that is logged with that user
		final Actor owner = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId());
		//Now we create a box
		final Box n = this.boxService.create(owner);
		n.setName("TEST BOX");
		//And now we save it
		final Box saved = this.boxService.save(n);
		//Knowing as we do that save is working, let's delete it
		this.boxService.delete(saved);
		//But, did we delete it?
		Assert.isTrue(!(this.boxService.findAll().contains(saved)));
	}

	@Test
	public void findByOwnerTest() {
		//First, we log as a customer (for example)
		super.authenticate("Customer2");
		//We get the actor that is logged with that user
		final Actor owner = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId());
		//Now we create a box
		final Box n = this.boxService.create(owner);
		n.setDeleteable(true);
		n.setName("TEST BOX");
		n.setOwner(owner);
		n.setMessages(null);
		//And now we save it
		final Box saved = this.boxService.save(n);
		//Is it associated with the user?
		Assert.isTrue(this.boxService.findByOwner(owner.getId()).contains(saved));
	}

	@Test
	public void initializeDefaultBoxesTest() {
		//First, we log as a customer (for example) that we know that has no boxes
		super.authenticate("Customer10");
		final Actor owner = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId());
		//Now we get the id of this actor
		final int id = owner.getId();
		//Let's initialize the four default boxes [IN, OUT, TRASH, SPAM]
		this.boxService.initializeDefaultBoxes();
		//The original 4 and the new 4
		Assert.isTrue(this.boxService.findByOwner(id).size() == 8);
	}

	public void findByNameTest() {
		//First, we log as a customer (for example)
		super.authenticate("Customer1");
		//We get the actor that is logged with that user
		final Actor owner = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId());
		//Now we create a box
		final Box n = this.boxService.create(owner);
		n.setDeleteable(true);
		n.setName("TEST BOX");
		n.setOwner(owner);
		n.setMessages(null);
		//Is it associated with the user?
		Assert.notNull(this.boxService.findByName(owner.getId(), "TRASH"));
	}

}
