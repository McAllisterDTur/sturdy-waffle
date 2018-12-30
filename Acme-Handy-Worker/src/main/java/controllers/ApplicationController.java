
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.ActorService;
import services.ApplicationService;
import domain.Application;

@Controller
@RequestMapping("/application/**")
public class ApplicationController extends AbstractController {

	@Autowired
	private ApplicationService	applicationService;
	@Autowired
	private ActorService		actorService;
	private UserAccount			account;


	public ApplicationController() {
		super();
	}

	@RequestMapping(value = "/handyworker/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		this.account = LoginService.getPrincipal();
		Collection<Application> applications;

		applications = this.applicationService.findAllWorker(this.actorService.findByUserAccountId(this.account.getId()).getId());

		System.out.println(applications);

		result = new ModelAndView("application/handyworker/list");
		result.addObject("applications", applications);
		result.addObject("requestURI", "applications/handyworker/list.do");

		return result;
	}

	@RequestMapping(value = "/handyworker/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int applicationId) {
		final ModelAndView res;

		final Application a = this.applicationService.findOne(applicationId);
		res = new ModelAndView("application/handyworker/display");
		res.addObject("application", a);
		res.addObject("phases", a.getPhases());

		return res;
	}

	//	@RequestMapping(value = "/handyworker/create", method = RequestMethod.GET)
	//	public ModelAndView create(@RequestParam final int fixuptaskId) {
	//		final ModelAndView res;
	//
	//		final Application a = this.applicationService.create(fixuptaskId);
	//		res = new ModelAndView("application/handyworker/create");
	//		res.addObject("application", a);
	//		//res.addObject("phases", a.getPhases());
	//
	//		return res;
	//	}
}
