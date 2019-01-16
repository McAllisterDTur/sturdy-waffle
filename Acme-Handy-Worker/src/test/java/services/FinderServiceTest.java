
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
import domain.Finder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class FinderServiceTest extends AbstractTest {

	@Autowired
	FinderService	finderService;
	@Autowired
	ActorService	aService;


	@Test
	public void createTest() {
		final Actor a = this.aService.findByUserAccountId(LoginService.getPrincipal().getId());
		Assert.notNull(this.finderService.create(a));
	}

	@Test
	public void createBadTest() {
		try {
			final Actor a = this.aService.findByUserAccountId(LoginService.getPrincipal().getId());
			super.authenticate("handy1");
			final Finder f = this.finderService.create(a);
			Assert.notEmpty(this.finderService.save(f).getFixUpTask());
		} catch (final IllegalArgumentException e) {
			Assert.notNull(e);
		}
	}
	@Test
	public void findAllTest() {
		Assert.notEmpty(this.finderService.findAll());
	}

	@Test
	public void saveTest() {
		super.authenticate("handy2");
		final Actor a = this.aService.findByUserAccountId(LoginService.getPrincipal().getId());
		final Finder f = this.finderService.create(a);
		Assert.notNull(this.finderService.save(f).getFixUpTask());
	}

	@Test
	public void findOneTest() {
		super.authenticate("handy1");
		final Finder f = this.finderService.findAll().iterator().next();
		Assert.isTrue(this.finderService.findOne(f.getId()).equals(f));
	}

	@Test
	public void findByLoggedHandyWorkerTest() {
		super.authenticate("handy1");
		Assert.notNull(this.finderService.findByLoggedHandyWorker());
	}

}
