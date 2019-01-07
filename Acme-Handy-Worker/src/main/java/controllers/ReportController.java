
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import security.LoginService;
import security.UserAccount;
import services.ActorService;
import services.ReportService;
import domain.Actor;
import domain.Report;

@Controller
@RequestMapping("/report/**")
public class ReportController extends AbstractController {

	private UserAccount		account;

	@Autowired
	private ActorService	actorService;
	@Autowired
	private ReportService	reportService;


	public ReportController() {
		super();
	}

	@RequestMapping("/handyworker,referee/list")
	public ModelAndView list() {

		final ModelAndView res;

		res = new ModelAndView("report/handyworker,referee/list");

		this.account = LoginService.getPrincipal();

		Actor act = null;

		if (this.account.getAuthorities().iterator().next().getAuthority().equals(Authority.REFEREE))
			act = this.actorService.findByUserAccountId(this.account.getId());
		else if (this.account.getAuthorities().iterator().next().getAuthority().equals(Authority.HANDYWORKER))
			act = this.actorService.findByUserAccountId(this.account.getId());

		final Collection<Report> reports = this.reportService.findReportsActor(act.getId());

		res.addObject("reports", reports);
		res.addObject("requestURI", "report/handyworker,referee/list.do");

		return res;
	}
}
