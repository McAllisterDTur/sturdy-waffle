
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
import domain.ProfessionalRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ProfessionalRecordServiceTest extends AbstractTest {

	@Autowired
	private ProfessionalRecordService	prRecord;
	@Autowired
	private CurriculaService			cRecord;


	@Test
	public void createTest() {
		super.authenticate("handy1");
		Assert.notNull(this.prRecord.create());
	}

	@Test
	public void findAllTest() {
		super.authenticate("handy1");
		Assert.notEmpty(this.prRecord.findAll());
	}

	@Test
	public void findOneTest() {
		super.authenticate("handy1");
		final ProfessionalRecord pr = this.prRecord.findAll().iterator().next();
		Assert.isTrue(this.prRecord.findOne(pr.getId()).equals(pr));
	}

	@Test
	public void saveTest() {
		super.authenticate("handy1");
		final ProfessionalRecord pr = this.prRecord.findAll().iterator().next();
		final ProfessionalRecord prn = this.prRecord.create();
		prn.setAttachmentURL(pr.getAttachmentURL());
		prn.setCompanyName(pr.getCompanyName());
		prn.setEnd(pr.getEnd());
		prn.setRole(pr.getRole());
		prn.setStart(pr.getStart());
		final ProfessionalRecord ers = this.prRecord.save(prn);
		Assert.isTrue(this.prRecord.findAll().contains(ers));
	}

	@Test
	public void findByCurriculaTest() {
		super.authenticate("handy1");
		final Curricula c = this.cRecord.findFromLoggedHandyWorker();
		Assert.notNull(this.prRecord.findByCurricula(c.getId()));

	}
}
