
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
import domain.EducationRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class EducationRecordServiceTest extends AbstractTest {

	@Autowired
	private EducationRecordService	edRecord;
	@Autowired
	private CurriculaService		cRecord;


	@Test
	public void testCreate() {
		super.authenticate("handy1");
		Assert.notNull(this.edRecord.create());
	}

	@Test
	public void testFindAll() {
		super.authenticate("handy1");
		Assert.notEmpty(this.edRecord.findAll());
	}

	@Test
	public void testFindOne() {
		super.authenticate("handy1");
		final EducationRecord er = this.edRecord.findAll().iterator().next();
		Assert.isTrue(this.edRecord.findOne(er.getId()).equals(er));
	}

	@Test
	public void testSave() {
		super.authenticate("handy1");
		final EducationRecord ed = this.edRecord.findAll().iterator().next();
		final EducationRecord edn = this.edRecord.create();
		edn.setDiplomaTitle(ed.getDiplomaTitle());
		edn.setEnd(ed.getEnd());
		edn.setInstitution(ed.getInstitution());
		edn.setStart(ed.getStart());
		final EducationRecord ers = this.edRecord.save(edn);
		Assert.isTrue(this.edRecord.findAll().contains(ers));
	}

	@Test
	public void testFindByCurricula() {
		super.authenticate("handy1");
		final Curricula c = this.cRecord.findFromLoggedHandyWorker();
		Assert.notNull(this.edRecord.findByCurricula(c.getId()));

	}
}
