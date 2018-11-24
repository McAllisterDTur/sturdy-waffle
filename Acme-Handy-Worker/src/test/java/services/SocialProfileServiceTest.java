
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
import domain.SocialProfile;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class SocialProfileServiceTest extends AbstractTest {

	//Service that we are testing
	@Autowired
	SocialProfileService	spService;

	//Auxiliary services
	@Autowired
	ActorService			aService;


	@Test
	public void testCreate() {
		//First, we create a new SocialProfile
		final SocialProfile sp = this.spService.create();
		//But, did we?
		Assert.notNull(sp);
	}

	@Test
	public void testFindAll() {
		//Let's find all the Social Profiles
		final Collection<SocialProfile> allsp = this.spService.findAll();
		//Did it work?
		Assert.notEmpty(allsp);
	}

	@Test
	public void testSave() {
		//Let's log as a HandyWorker (just because)
		super.authenticate("handy1");
		//Now we get the actual Actor
		final Actor owner = this.aService.findByUserAccountId(LoginService.getPrincipal().getId());
		//Let's create a SocialProfile
		final SocialProfile sp = this.spService.create();
		sp.setActor(owner);
		sp.setNick("Test");
		sp.setProfileLink("http://test.com/u/test");
		sp.setSocialNetwork("Test Network");
		//We save it
		final SocialProfile saved = this.spService.save(sp);
		//Is it there?
		Assert.isTrue(this.spService.findAll().contains(saved));
	}

	@Test
	public void testFindOne() {
		//Let's log as a HandyWorker (just because)
		super.authenticate("handy1");
		//Now we get the actual Actor
		final Actor owner = this.aService.findByUserAccountId(LoginService.getPrincipal().getId());
		//Let's create a SocialProfile
		final SocialProfile sp = this.spService.create();
		sp.setActor(owner);
		sp.setNick("Test");
		sp.setProfileLink("http://test.com/u/test");
		sp.setSocialNetwork("Test Network");
		//We save it
		final SocialProfile saved = this.spService.save(sp);
		//Can we find it?
		Assert.isTrue(this.spService.findOne(saved.getId()).equals(saved));
	}

	@Test
	public void testUpdate() {
		//First, let's authenticate
		super.authenticate("handy1");
		//We take one arbitrary SocialProfile
		final SocialProfile spNU = (SocialProfile) this.spService.findAll().toArray()[0];
		//We update, let's say, the nickname
		spNU.setNick("Updated nick");
		//Now, let's save it
		this.spService.save(spNU);
		//Is it updated?
		final String updatedNick = this.spService.findOne(spNU.getId()).getNick();
		Assert.isTrue(spNU.getNick().equals(updatedNick));
	}

	@Test
	public void testDelete() {
		//Let's log as a HandyWorker (just because)
		super.authenticate("handy1");
		//Now we get the actual Actor
		final Actor owner = this.aService.findByUserAccountId(LoginService.getPrincipal().getId());
		//Let's create a SocialProfile
		final SocialProfile sp = this.spService.create();
		sp.setActor(owner);
		sp.setNick("Test");
		sp.setProfileLink("http://test.com/u/test");
		sp.setSocialNetwork("Test Network");
		//We save it
		final SocialProfile saved = this.spService.save(sp);
		//And delete it
		this.spService.delete(saved);
		//Did it work?
		Assert.isTrue(!this.spService.findAll().contains(saved));
	}

	@Test
	public void testFindByActor() {
		//Let's log as a HandyWorker (just because)
		super.authenticate("handy1");
		//Now we get the actual Actor
		final Actor owner = this.aService.findByUserAccountId(LoginService.getPrincipal().getId());
		//Let's create a SocialProfile
		final SocialProfile sp = this.spService.create();
		sp.setActor(owner);
		sp.setNick("Test");
		sp.setProfileLink("http://test.com/u/test");
		sp.setSocialNetwork("Test Network");
		//We save it
		final SocialProfile saved = this.spService.save(sp);
		//Can we find this social profile within the list of this actor?
		Assert.isTrue(this.spService.findByActor(owner.getId()).contains(saved));
	}
}
