
package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

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
	BoxService	boxService;


	@Test
	public void testCreate() {
		//We create a box
		final Box box = this.boxService.create();
		//Did we make it?
		Assert.notNull(box);
	}

	@Test
	public void testFindAll() {
		//Let's find all the boxes
		final Collection<Box> allBoxes = this.boxService.findAll();
		//Is this working?
		Assert.notEmpty(allBoxes);
	}

	@Test
	public void testSave() {
		//First, we log as a customer (for example)
		super.authenticate("Customer2");
		//TODO: We get the actor that is logged with that user
		final Actor owner = new Actor();
		//Now we create a box
		final Box n = this.boxService.create();
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
	public void testFindOne() {
		//First, we log as a customer (for example)
		super.authenticate("Customer2");
		//TODO: We get the actor that is logged with that user
		final Actor owner = new Actor();
		//Now we create a box
		final Box n = this.boxService.create();
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
	public void testDelete() {
		//First, we log as a customer (for example)
		super.authenticate("Customer2");
		//TODO: We get the actor that is logged with that user
		final Actor owner = new Actor();
		//Now we create a box
		final Box n = this.boxService.create();
		n.setDeleteable(true);
		n.setName("TEST BOX");
		n.setOwner(owner);
		n.setMessages(null);
		//And now we save it
		final Box saved = this.boxService.save(n);
		//Knowing as we do that save is working, let's delete it
		this.boxService.delete(saved);
		//But, did we delete it?
		Assert.isTrue(!(this.boxService.findAll().contains(saved)));
	}

	@Test
	public void testFindByOwner() {
		//First, we log as a customer (for example)
		super.authenticate("Customer2");
		//TODO: We get the actor that is logged with that user
		final Actor owner = new Actor();
		//Now we create a box
		final Box n = this.boxService.create();
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
	public void testInitializeDefaultBoxes() {
		//First, we log as a customer (for example)
		super.authenticate("Customer2");
		//Now we get the id of this actor
		//TODO: int id = id del actor que hemos creado arriba 
		//Let's initialize the four default boxes [IN, OUT, TRASH, SPAM]
		this.boxService.initializeDefaultBoxes();
		//Have we created four boxes?
		Assert.isTrue(this.boxService.findByOwner(id).size() == 4);
	}

}
