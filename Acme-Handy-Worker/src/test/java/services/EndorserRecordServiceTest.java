
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Curricula;
import domain.EndorserRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class EndorserRecordServiceTest extends AbstractTest {

	@Autowired
	private EndorserRecordService	erRecord;
	@Autowired
	private CurriculaService		cRecord;


	@Test
	public void testCreate() {
		super.authenticate("handy1");
		Assert.notNull(this.erRecord.create());
	}

	@Test
	public void testFindAll() {
		super.authenticate("handy1");
		Assert.notEmpty(this.erRecord.findAll());
	}

	@Test
	public void testFindOne() {
		super.authenticate("handy1");
		final EndorserRecord er = this.erRecord.findAll().iterator().next();
		Assert.isTrue(this.erRecord.findOne(er.getId()).equals(er));
	}

	@Test
	public void testSave() {
		super.authenticate("handy1");
		final EndorserRecord er = this.erRecord.findAll().iterator().next();
		final EndorserRecord ern = this.erRecord.create();
		ern.setEmail(er.getEmail());
		ern.setEndorserName(er.getEndorserName());
		ern.setLinkedInURL(er.getLinkedInURL());
		ern.setPhoneNumber(er.getPhoneNumber());
		final EndorserRecord ers = this.erRecord.save(ern);
		Assert.isTrue(this.erRecord.findAll().contains(ers));
	}

	@Test
	public void testFindByCurricula() {
		super.authenticate("handy1");
		final Curricula c = this.cRecord.findFromLoggedHandyWorker();
		Assert.notNull(this.erRecord.findByCurricula(c.getId()));

	}
}
