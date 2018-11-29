
package tests;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.UserAccount;
import services.ActorService;
import services.NotesServices;
import services.ReportService;
import utilities.AbstractTest;
import domain.Notes;
import domain.Referee;
import domain.Report;

// Indica que se tiene que ejecutar a través de Spring
@RunWith(SpringJUnit4ClassRunner.class)
//Indica los ficheros de configuración
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
//Para que la base de datos no quede incoherente
@Transactional
public class NotesServicesTest extends AbstractTest {

	@Autowired
	private NotesServices	notesServices;
	@Autowired
	private ReportService	reportService;
	@Autowired
	private ActorService	actorService;

	private UserAccount		account;


	@Test
	public void testCreateAndTestReferee() {
		super.authenticate("referee1");
		final Notes n = this.notesServices.create();

		Assert.notNull(n);
		final Collection<Report> repor = this.reportService.findReportsReferee((Referee) this.actorService.findByUserAccountId(this.account.getId()));

		n.setMoment(new Date());
		n.setReport();

		super.unauthenticate();
	}
}
