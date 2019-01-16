
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import security.LoginService;
import security.UserAccount;
import services.ActorService;
import services.ApplicationService;
import services.ConfigurationService;
import domain.Application;
import domain.Customer;
import domain.FixUpTask;

@Controller
@RequestMapping("/application/**")
public class ApplicationController extends AbstractController {

	@Autowired
	private ApplicationService		applicationService;
	@Autowired
	private ActorService			actorService;
	@Autowired
	private ConfigurationService	configurationService;
	private UserAccount				account;


	public ApplicationController() {
		super();
	}

	@RequestMapping(value = "/customer,handyworker/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int fixuptaskId) {
		ModelAndView result;
		this.account = LoginService.getPrincipal();
		Collection<Application> applications;

		final double vat = this.configurationService.findAll().iterator().next().getVat();

		if (fixuptaskId == 0)
			applications = this.applicationService.findAllWorker(this.actorService.findByUserAccountId(this.account.getId()).getId());
		else {
			applications = this.applicationService.findAllTask(fixuptaskId);
			//Verificación que se es dueño de la fixuptask
			Assert.isTrue(applications.iterator().next().getFixUpTask().getCustomer().getAccount().equals(this.account));
		}

		result = new ModelAndView("application/customer,handyworker/list");
		result.addObject("applications", applications);
		result.addObject("vat", vat);
		result.addObject("requestURI", "applications/customer,handyworker/list.do?fixuptaskId=" + fixuptaskId);
		result = this.configurationService.configGeneral(result);
		result = this.actorService.isBanned(result);
		return result;
	}

	@RequestMapping(value = "/customer,handyworker/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int applicationId) {
		ModelAndView res;

		this.account = LoginService.getPrincipal();
		final double vat = this.configurationService.findAll().iterator().next().getVat();
		final Application a = this.applicationService.findOne(applicationId);
		//Verificacion que se es dueño de la application o de la fixuptask
		Assert.isTrue(a.getFixUpTask().getCustomer().getAccount().equals(this.account) || a.getHandyWorker().getAccount().equals(this.account));
		res = new ModelAndView("application/customer,handyworker/display");
		res.addObject("application", a);
		res.addObject("vat", vat);

		if (this.account.getAuthorities().iterator().next().getAuthority().equals(Authority.CUSTOMER)) {
			final Customer c = (Customer) this.actorService.findByUserAccountId(this.account.getId());
			res.addObject("customer", c);
		}
		//res.addObject("phases", a.getPhases());
		res = this.configurationService.configGeneral(res);
		res = this.actorService.isBanned(res);
		return res;
	}

	@RequestMapping(value = "/customer/accept", method = RequestMethod.POST)
	public ModelAndView accept(@RequestParam final int applicationId) {
		ModelAndView res;

		this.account = LoginService.getPrincipal();

		final Application a = this.applicationService.findOne(applicationId);
		try {
			Assert.isTrue(this.applicationService.taskHasNoAcceptedApplication(a));

			final FixUpTask task = a.getFixUpTask();
			res = new ModelAndView("creditCard/create");
			final Collection<String> makers = this.configurationService.findAll().iterator().next().getCardMaker();

			res.addObject("fixuptask", task);
			res.addObject("makers", makers);
			res.addObject("applicationId", a.getId());
		} catch (final Throwable oops) {
			oops.printStackTrace();
			res = new ModelAndView("redirect:../../application/customer,handyworker/list.do?fixuptaskId=" + a.getFixUpTask().getId());
		}
		res = this.configurationService.configGeneral(res);
		res = this.actorService.isBanned(res);
		return res;
	}

	@RequestMapping(value = "/customer/reject", method = RequestMethod.POST)
	public ModelAndView reject(@RequestParam final int applicationId) {
		ModelAndView res;

		this.account = LoginService.getPrincipal();

		final Application a = this.applicationService.findOne(applicationId);

		try {
			a.setStatus("REJECTED");

			this.applicationService.save(a);

			final FixUpTask task = a.getFixUpTask();

			res = new ModelAndView("redirect:../customer,handyworker/list.do?fixuptaskId=" + task.getId());

		} catch (final Throwable oops) {
			oops.printStackTrace();
			res = new ModelAndView("application/customer,handyworker/list.do?fixuptaskId=" + a.getFixUpTask().getId());
		}
		res = this.configurationService.configGeneral(res);
		res = this.actorService.isBanned(res);
		return res;
	}

	@RequestMapping(value = "/handyworker/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int fixuptaskId) {
		ModelAndView res;

		final Application a = this.applicationService.create(fixuptaskId);

		System.out.println(a);
		res = new ModelAndView("application/customer,handyworker/edit");
		res.addObject("application", a);
		//res.addObject("phases", a.getPhases());
		res = this.configurationService.configGeneral(res);
		res = this.actorService.isBanned(res);
		return res;
	}

	@RequestMapping(value = "/customer,handyworker/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int applicationId) {
		ModelAndView res;

		final Application a = this.applicationService.findOne(applicationId);

		res = new ModelAndView("application/customer,handyworker/edit");
		res.addObject("application", a);
		//res.addObject("phases", a.getPhases());
		res = this.configurationService.configGeneral(res);
		res = this.actorService.isBanned(res);
		return res;
	}

	@RequestMapping(value = "/customer,handyworker/save", method = RequestMethod.POST)
	public ModelAndView save(@Valid final Application application, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {

			System.out.println("errores:" + binding.getAllErrors());
			result = new ModelAndView("application/customer,handyworker/edit");
			result.addObject("application", application);
		} else {
			Application applicationF = null;
			try {
				applicationF = this.applicationService.save(application);
				result = new ModelAndView("redirect:/application/customer,handyworker/display.do?applicationId=" + applicationF.getId());
			} catch (final Throwable opps) {
				System.out.println(opps.getMessage());
				opps.printStackTrace();
				result = new ModelAndView("application/customer,handyworker/edit");
				result.addObject("messageCode", "application.commit.error");
			}
		}
		result = this.configurationService.configGeneral(result);
		result = this.actorService.isBanned(result);
		return result;
	}
}
