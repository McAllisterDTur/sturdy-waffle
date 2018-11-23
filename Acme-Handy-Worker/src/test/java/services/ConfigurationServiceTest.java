
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Configuration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ConfigurationServiceTest extends AbstractTest {

	@Autowired
	private ConfigurationService	configurationService;


	@Test
	public void CreateGoodTest() {
		super.authenticate("admin");
		final Configuration a = new Configuration();
		final Configuration ac = this.configurationService.create();
		Assert.isTrue(a.equals(ac));
		super.unauthenticate();
	}

	@Test
	public void CreateBadTest() {
		super.authenticate("Customer1");
		final Configuration a = new Configuration();
		Configuration ac = null;
		try {
			ac = this.configurationService.create();
		} catch (final Exception e) {
			ac = null;
		}
		Assert.isTrue(!(a.equals(ac)));
		super.unauthenticate();
	}

	@Test
	public void SaveGoodTest() {
		super.authenticate("admin");
		final Configuration a = new Configuration();
		a.setBannerURL("www.us.es");
		a.setCacheTime(1);
		a.setWelcomeEN("haha");
		a.setWelcomeSP("jaja");
		a.setFinderResults(10);
		final Configuration ac = this.configurationService.save(a);
		a.setId(ac.getId());
		a.setVersion(ac.getVersion());
		Assert.isTrue(a.equals(ac));
		super.unauthenticate();
	}

	@Test
	public void SavebBadTest() {
		super.authenticate("Customer1");
		final Configuration a = new Configuration();
		a.setBannerURL("www.us.es");
		a.setCacheTime(1);
		a.setWelcomeEN("haha");
		a.setWelcomeSP("jaja");
		a.setFinderResults(10);
		Configuration ac = null;
		try {
			ac = this.configurationService.save(a);
		} catch (final Exception e) {
			ac = null;
		}
		Assert.isNull(ac);
		super.unauthenticate();
	}

	@Test
	public void FindAllGoodTest() {
		super.authenticate("admin");
		final Collection<Configuration> ac = this.configurationService.findAll();
		Assert.isTrue(!(ac.isEmpty()));
		super.unauthenticate();
	}

	@Test
	public void FindAllBadTest() {
		super.authenticate("Customer1");
		Collection<Configuration> ac = null;
		try {
			ac = this.configurationService.findAll();
		} catch (final Exception e) {
			ac = null;
		}
		Assert.isNull(ac);
		super.unauthenticate();

	}

	@Test
	public void FindOneGoodTest() {
		super.authenticate("admin");
		final Configuration a = new Configuration();
		a.setBannerURL("www.us.es");
		a.setCacheTime(1);
		a.setWelcomeEN("haha");
		a.setWelcomeSP("jaja");
		a.setFinderResults(10);
		final Configuration ac = this.configurationService.save(a);
		final int id = ac.getId();
		final Configuration ab = this.configurationService.findOne(id);
		Assert.isTrue(ac.equals(ab));
		super.unauthenticate();

	}

	@Test
	public void FindOneBadTest() {
		super.authenticate("admin");
		final Configuration a = new Configuration();
		a.setBannerURL("www.us.es");
		a.setCacheTime(1);
		a.setWelcomeEN("haha");
		a.setWelcomeSP("jaja");
		a.setFinderResults(10);
		final Configuration ac = this.configurationService.save(a);
		final int id = ac.getId();
		super.unauthenticate();
		super.authenticate("Customer1");
		Configuration ab = null;
		try {
			ab = this.configurationService.findOne(id);
		} catch (final Exception e) {
			ab = null;
		}
		Assert.isNull(ab);
		super.unauthenticate();

	}

	@Test
	public void DeleteGoodTest() {
		super.authenticate("admin");
		final Configuration a = new Configuration();
		a.setBannerURL("www.us.es");
		a.setCacheTime(1);
		a.setWelcomeEN("haha");
		a.setWelcomeSP("jaja");
		a.setFinderResults(10);
		final Configuration ac = this.configurationService.save(a);
		this.configurationService.delete(ac);
		Assert.isNull(this.configurationService.findOne(ac.getId()));
		super.unauthenticate();
	}

	@Test
	public void DeleteBadTest() {
		super.authenticate("admin");
		Configuration a = new Configuration();
		a.setBannerURL("www.us.es");
		a.setCacheTime(1);
		a.setWelcomeEN("haha");
		a.setWelcomeSP("jaja");
		a.setFinderResults(10);
		final Configuration ac = this.configurationService.save(a);
		super.unauthenticate();
		super.authenticate("Customer1");
		try {
			this.configurationService.delete(ac);
		} catch (final Exception e) {
			a = null;
		}
		Assert.isNull(a);
		super.unauthenticate();
		super.authenticate("admin");
		Assert.isTrue((this.configurationService.findOne(ac.getId()).equals(ac)));
		super.unauthenticate();
	}

}
