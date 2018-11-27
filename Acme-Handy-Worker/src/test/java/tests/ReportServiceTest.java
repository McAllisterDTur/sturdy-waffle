
package tests;

import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.LoginService;
import services.ActorService;
import services.ReportService;
import utilities.AbstractTest;
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
public class ReportServiceTest extends AbstractTest {

	@Autowired
	private ReportService	reportService;
	@Autowired
	private ActorService	actorService;


	//	@Autowired
	//	private ComplaintService	complaintService;

	@Test
	public void testCreateAndSave() {

		super.authenticate("referee1");

		final Report r = this.reportService.create();
		Assert.notNull(r);

		r.setIsFinal(false);
		r.setReportTime(new Date());
		r.setDescription("some Description");
		//TODO: pendiente de cambio en el UML
		r.setReferee((Referee) this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()));
		//r.setComplaint();

		final Report res = this.reportService.save(r);
		Assert.notNull(res);
		Assert.isTrue(res.getId() > 0);
		super.unauthenticate();
	}

}
