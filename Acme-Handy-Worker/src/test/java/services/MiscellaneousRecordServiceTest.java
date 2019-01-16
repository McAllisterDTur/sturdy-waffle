
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
import domain.MiscellaneousRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class MiscellaneousRecordServiceTest extends AbstractTest {

	@Autowired
	private MiscellaneousRecordService	mRecord;
	@Autowired
	private CurriculaService			cRecord;


	@Test
	public void createTest() {
		super.authenticate("handy1");
		Assert.notNull(this.mRecord.create());
	}

	@Test
	public void findAllTest() {
		super.authenticate("handy1");
		Assert.notEmpty(this.mRecord.findAll());
	}

	@Test
	public void findOneTest() {
		super.authenticate("handy1");
		final MiscellaneousRecord mr = this.mRecord.findAll().iterator().next();
		Assert.isTrue(this.mRecord.findOne(mr.getId()).equals(mr));
	}

	@Test
	public void saveTest() {
		super.authenticate("handy1");
		final MiscellaneousRecord mr = this.mRecord.findAll().iterator().next();
		final MiscellaneousRecord mrn = this.mRecord.create();
		mrn.setAttachmentURL(mr.getAttachmentURL());
		mrn.setTitle(mr.getTitle());
		final MiscellaneousRecord mrs = this.mRecord.save(mrn);
		Assert.isTrue(this.mRecord.findAll().contains(mrs));
	}

	@Test
	public void findByCurriculaTest() {
		super.authenticate("handy1");
		final Curricula c = this.cRecord.findFromLoggedHandyWorker();
		Assert.notNull(this.mRecord.findByCurricula(c.getId()));

	}
}
