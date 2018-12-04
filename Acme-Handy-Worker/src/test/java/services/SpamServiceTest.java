
package services;

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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class SpamServiceTest extends AbstractTest {

	//Service that we are testing
	@Autowired
	SpamService			sService;
	//Auxiliary
	@Autowired
	ActorService		aService;
	@Autowired
	UserAccountService	uaService;


	@Test
	public void spamGoodTest() {
		super.authenticate("Customer2");
		final Actor tester = this.aService.findByUserAccountId(LoginService.getPrincipal().getId());
		final String s = "sex viagra";
		final Boolean spam = this.sService.isSpam(tester, s);
		Assert.isTrue(spam);
		Assert.isTrue(tester.getIsSuspicious());
	}

	@Test
	public void spamBadTest() {
		super.authenticate("Customer2");
		final Actor tester = this.aService.findByUserAccountId(LoginService.getPrincipal().getId());
		final String s = "Tryig to recognize myself when I feel I've been replaced";
		final Boolean spam = this.sService.isSpam(tester, s);
		Assert.isTrue(!spam);
		Assert.isTrue(!tester.getIsSuspicious());
	}
}
