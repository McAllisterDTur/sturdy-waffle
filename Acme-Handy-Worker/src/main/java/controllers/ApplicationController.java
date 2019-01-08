
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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

	@RequestMapping(value = "/customer,handyworker/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int fixuptaskId) {
		final ModelAndView result;
		this.account = LoginService.getPrincipal();
		Collection<Application> applications;

		if (fixuptaskId == 0)
			applications = this.applicationService.findAllWorker(this.actorService.findByUserAccountId(this.account.getId()).getId());
		else
			applications = this.applicationService.findAllTask(fixuptaskId);

		result = new ModelAndView("application/customer,handyworker/list");
		result.addObject("applications", applications);
		result.addObject("requestURI", "applications/customer,handyworker/list.do");

		return result;
	}

	@RequestMapping(value = "/customer,handyworker/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int applicationId) {
		final ModelAndView res;

		this.account = LoginService.getPrincipal();

		final Application a = this.applicationService.findOne(applicationId);
		res = new ModelAndView("application/customer,handyworker/display");
		res.addObject("application", a);
		//res.addObject("phases", a.getPhases());

		return res;
	}

	@RequestMapping(value = "/customer/accept", method = RequestMethod.GET)
	public ModelAndView accept(@RequestParam final int applicationId) {
		final ModelAndView res;

		this.account = LoginService.getPrincipal();

		final Application a = this.applicationService.findOne(applicationId);

		//a.setStatus("ACCEPTED");

		this.applicationService.save(a);

		Collection<Application> applications;

		applications = this.applicationService.findAllTask(a.getFixUpTask().getId());

		res = new ModelAndView("application/customer,handyworker/list");
		res.addObject("applications", applications);
		res.addObject("requestURI", "applications/customer,handyworker/list.do?fixuptaskId=?" + a.getFixUpTask().getId());

		return res;
	}

	@RequestMapping(value = "/handyworker/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int fixuptaskId) {
		final ModelAndView res;

		final Application a = this.applicationService.create(fixuptaskId);

		System.out.println(a);
		res = new ModelAndView("application/handyworker/edit");
		res.addObject("application", a);
		//res.addObject("phases", a.getPhases());

		return res;
	}

	@RequestMapping(value = "/handyworker/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int applicationId) {
		final ModelAndView res;

		final Application a = this.applicationService.findOne(applicationId);

		res = new ModelAndView("application/handyworker/edit");
		res.addObject("application", a);
		//res.addObject("phases", a.getPhases());

		return res;
	}

	@RequestMapping(value = "/handyworker/save", method = RequestMethod.POST)
	public ModelAndView save(@Valid final Application application, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {

			System.out.println(binding.getAllErrors());
			result = new ModelAndView("application/handyworker/edit");
			result.addObject("application", application);
		} else
			try {
				this.applicationService.save(application);
				result = new ModelAndView("redirect:/application/customer,handyworker/list.do");
			} catch (final Throwable opps) {
				System.out.println(opps.getMessage());
				opps.printStackTrace();
				result = new ModelAndView("application/handyworker/edit");
				result.addObject("messageCode", "application.commit.error");
			}

		return result;
	}
}
