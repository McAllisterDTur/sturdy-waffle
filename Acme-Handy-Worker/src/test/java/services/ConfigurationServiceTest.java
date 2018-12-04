
package services;

import java.util.ArrayList;
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
		final Configuration config = (this.configurationService.findAll()).iterator().next();
		super.authenticate("admin");
		config.setCacheTime(2);
		final Configuration ac = this.configurationService.save(config);
		Assert.isTrue(config.equals(ac));

	}

	@Test
	public void SaveBadTest() {
		super.authenticate("admin");
		final Configuration a = new Configuration();
		a.setBannerURL("www.us.es");
		a.setCacheTime(1);
		a.setWelcomeEN("haha");
		a.setWelcomeSP("jaja");
		a.setFinderResults(10);
		a.setVat(0.2);
		a.setFinderResults(10);
		final Collection<String> cardMaker = new ArrayList<>();
		final Collection<String> negativeWords = new ArrayList<>();
		final Collection<String> positiveWords = new ArrayList<>();
		final Collection<String> spamWords = new ArrayList<>();
		a.setCardMaker(cardMaker);
		a.setNegativeWords(negativeWords);
		a.setPositiveWords(positiveWords);
		a.setSpamWords(spamWords);
		Configuration ac = a;
		try {
			ac = this.configurationService.save(a);
		} catch (final Exception e) {
			ac = null;
		}
		Assert.isNull(ac);
		super.unauthenticate();
	}
	@Test
	public void SaveBad2Test() {
		super.authenticate("Customer1");
		final Configuration a = new Configuration();
		a.setBannerURL("www.us.es");
		a.setCacheTime(1);
		a.setWelcomeEN("haha");
		a.setWelcomeSP("jaja");
		a.setFinderResults(10);
		Configuration ac = a;
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
		final Collection<Configuration> ac = this.configurationService.findAll();
		Assert.isTrue(!(ac.isEmpty()));
	}

	@Test
	public void DeleteGoodTest() {
		super.authenticate("admin");
		final Configuration config = (this.configurationService.findAll()).iterator().next();
		this.configurationService.delete(config);
		Assert.isTrue(this.configurationService.findAll().isEmpty());
		super.unauthenticate();
	}

	@Test
	public void DeleteBadTest() {
		final Configuration config = (this.configurationService.findAll()).iterator().next();
		super.authenticate("Customer1");
		Boolean a = null;
		try {
			this.configurationService.delete(config);
			a = false;
		} catch (final Exception e) {
			a = true;
		}
		Assert.isTrue(a);
		super.unauthenticate();
	}

}
