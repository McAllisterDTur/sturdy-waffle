
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
import domain.PersonalRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class PersonalRecordServiceTest extends AbstractTest {

	@Autowired
	private PersonalRecordService	prRecord;
	@Autowired
	private CurriculaService		cRecord;


	@Test
	public void createTest() {
		super.authenticate("handy2");
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
		final PersonalRecord er = this.prRecord.findAll().iterator().next();
		Assert.isTrue(this.prRecord.findOne(er.getId()).equals(er));
	}

	@Test
	public void saveTest() {
		super.authenticate("handy1");
		final PersonalRecord pr = this.prRecord.findAll().iterator().next();
		super.unauthenticate();
		super.authenticate("handy2");
		final PersonalRecord prn = this.prRecord.create();
		super.unauthenticate();
		super.authenticate("handy1");
		prn.setEmail(pr.getEmail());
		prn.setFullName(pr.getFullName());
		prn.setLinkedInURL(pr.getLinkedInURL());
		prn.setPhoneNumber(pr.getPhoneNumber());
		prn.setPhoto(pr.getPhoto());
		final PersonalRecord ers = this.prRecord.save(prn);
		Assert.isTrue(this.prRecord.findAll().contains(ers));
	}

	@Test
	public void findByCurriculaTest() {
		super.authenticate("handy1");
		final Curricula c = this.cRecord.findFromLoggedHandyWorker();
		Assert.notNull(this.prRecord.findByCurricula(c.getId()));

	}
}
