
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.LoginService;
import utilities.AbstractTest;
import domain.Actor;
import domain.Complaint;
import domain.Report;

// Indica que se tiene que ejecutar a trav�s de Spring
@RunWith(SpringJUnit4ClassRunner.class)
//Indica los ficheros de configuraci�n
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
//Para que la base de datos no quede incoherente
@Transactional
public class ReportServiceTest extends AbstractTest {

	@Autowired
	private ReportService		reportService;
	@Autowired
	private ActorService		actorService;
	@Autowired
	private ComplaintService	complaintService;


	@Test
	public void createAndSaveTest() {

		super.authenticate("referee1");

		final Report r = this.reportService.create();
		Assert.notNull(r);

		r.setReportTime(new Date());
		r.setDescription("some Description");

		final Complaint c = (Complaint) this.complaintService.findSelfassigned().toArray()[0];
		r.setComplaint(c);

		final Report res = this.reportService.save(r);
		Assert.notNull(res);
		Assert.isTrue(res.getId() > 0);
		super.unauthenticate();
	}

	@Test
	public void selectAllRefereeTest() {
		super.authenticate("referee1");

		final Actor actor = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId());

		final Collection<Report> res = this.reportService.findReportsActor(actor.getId());

		Assert.notNull(res);

		super.unauthenticate();
	}

	@Test
	public void selectAllCustomerTest() {
		super.authenticate("Customer2");

		final Actor actor = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId());

		final Collection<Report> res = this.reportService.findReportsActor(actor.getId());

		Assert.notNull(res);

		super.unauthenticate();
	}

	@Test
	public void selectAllWorkerTest() {
		super.authenticate("handy2");

		final Actor actor = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId());

		final Collection<Report> res = this.reportService.findReportsActor(actor.getId());

		Assert.notNull(res);

		super.unauthenticate();
	}
}
