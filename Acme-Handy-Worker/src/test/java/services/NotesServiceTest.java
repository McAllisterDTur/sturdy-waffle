
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
import security.UserAccount;
import utilities.AbstractTest;
import domain.Customer;
import domain.HandyWorker;
import domain.Notes;
import domain.Referee;
import domain.Report;

// Indica que se tiene que ejecutar a trav�s de Spring
@RunWith(SpringJUnit4ClassRunner.class)
//Indica los ficheros de configuraci�n
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
//Para que la base de datos no quede incoherente
@Transactional
public class NotesServiceTest extends AbstractTest {

	@Autowired
	private NotesService		notesServices;
	@Autowired
	private ReportService		reportService;
	@Autowired
	private ActorService		actorService;
	@Autowired
	private ComplaintService	complaintService;

	private UserAccount			account;


	@Test
	public void testCreateAndTestReferee() {
		super.authenticate("referee1");
		this.account = LoginService.getPrincipal();
		final Referee actor = (Referee) this.actorService.findByUserAccountId(this.account.getId());
		final Collection<Report> reports = this.reportService.findReportsActor(actor.getId());
		final Notes n = this.notesServices.create(reports.iterator().next());

		Assert.notNull(n);

		Report r = null;
		for (final Report t : reports)
			if (t.getNotes().isEmpty()) {
				r = t;
				break;
			}

		n.setMoment(new Date());
		n.setReport(r);

		super.unauthenticate();
	}

	@Test
	public void testCreateAndTestCustomer() {
		super.authenticate("Customer1");
		this.account = LoginService.getPrincipal();
		final Customer actor = (Customer) this.actorService.findByUserAccountId(this.account.getId());
		final Collection<Report> reports = this.reportService.findReportsActor(actor.getId());
		final Notes n = this.notesServices.create(reports.iterator().next());

		Assert.notNull(n);

		Report r = null;
		for (final Report t : reports)
			if (t.getNotes().isEmpty()) {
				r = t;
				break;
			}

		n.setMoment(new Date());
		n.setReport(r);

		super.unauthenticate();
	}

	@Test
	public void testCreateAndTestWorker() {
		super.authenticate("handy1");
		this.account = LoginService.getPrincipal();
		final HandyWorker actor = (HandyWorker) this.actorService.findByUserAccountId(this.account.getId());
		final Collection<Report> reports = this.reportService.findReportsActor(actor.getId());
		final Notes n = this.notesServices.create(reports.iterator().next());

		Assert.notNull(n);

		Report r = null;
		for (final Report t : reports)
			if (t.getNotes().isEmpty()) {
				r = t;
				break;
			}

		n.setMoment(new Date());
		n.setReport(r);

		super.unauthenticate();
	}
}
