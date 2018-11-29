
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
import domain.Endorsable;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class EndorsableServiceTest extends AbstractTest {

	@Autowired
	EndorsableService	endService;

	@Autowired
	UserAccountService	userAccountService;
	@Autowired
	ActorService		actorService;


	@Test
	public void testScore() {
		super.authenticate("handy2");
		final Actor a = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId());
		final Endorsable e = this.endService.findOne(a.getId());
		this.endService.computeScore(e);
		System.out.println(e.getScore());
		Assert.isTrue(e.getScore() != 0.0);
	}
}
