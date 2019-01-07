
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import security.LoginService;
import security.UserAccount;
import services.ActorService;
import services.ApplicationService;
import services.NotesService;
import services.ReportService;
import domain.Actor;
import domain.Customer;
import domain.HandyWorker;
import domain.Referee;
import domain.Report;

@Controller
@RequestMapping("/report/**")
public class ReportController extends AbstractController {

	private UserAccount			account;

	@Autowired
	private ActorService		actorService;
	@Autowired
	private ReportService		reportService;
	@Autowired
	private ApplicationService	appService;
	@Autowired
	private NotesService		noteServices;


	public ReportController() {
		super();
	}

	@RequestMapping("/customer,handyworker,referee/list")
	public ModelAndView list() {

		final ModelAndView res;

		res = new ModelAndView("report/customer,handyworker,referee/list");

		this.account = LoginService.getPrincipal();

		Actor act = null;

		if (this.account.getAuthorities().iterator().next().getAuthority().equals(Authority.REFEREE))
			act = this.actorService.findByUserAccountId(this.account.getId());
		else if (this.account.getAuthorities().iterator().next().getAuthority().equals(Authority.HANDYWORKER))
			act = this.actorService.findByUserAccountId(this.account.getId());
		else if (this.account.getAuthorities().iterator().next().getAuthority().equals(Authority.CUSTOMER))
			act = this.actorService.findByUserAccountId(this.account.getId());

		final Collection<Report> reports = this.reportService.findReportsActor(act.getId());

		res.addObject("reports", reports);
		res.addObject("requestURI", "report/customer,handyworker,referee/list.do");

		return res;
	}

	@RequestMapping("/customer,handyworker,referee/display")
	public ModelAndView display(@RequestParam final int reportId) {

		ModelAndView res;

		res = new ModelAndView("report/customer,handyworker,referee/display");
		final Report r = this.reportService.findOne(reportId);

		final Referee ref = r.getComplaint().getReferee();
		final Customer cust = r.getComplaint().getFixUpTask().getCustomer();
		final HandyWorker worker = this.appService.getApplicationAcceptedForFixUpTask(r.getComplaint().getFixUpTask().getId()).getHandyWorker();

		res.addObject("report", r);

		res.addObject("referee", ref);
		res.addObject("customer", cust);
		res.addObject("worker", worker);

		res.addObject("notes", r.getNotes());
		res.addObject("requestURI", "report/customer,handyworker,referee/display");

		return res;

	}
}
