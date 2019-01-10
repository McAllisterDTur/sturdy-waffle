
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ApplicationService;
import services.ConfigurationService;
import services.FixUpTaskService;
import domain.Application;
import domain.FixUpTask;

@Controller
@RequestMapping("creditCard")
public class CreditCardController {

	@Autowired
	private FixUpTaskService		taskService;
	@Autowired
	private ApplicationService		applicationService;
	@Autowired
	private ConfigurationService	confignService;
	@Autowired
	private ActorService			aService;


	public CreditCardController() {
		super();
	}

	@RequestMapping(value = "/customer/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int fixuptaskId) {
		ModelAndView result;
		final FixUpTask t = this.taskService.findOne(fixuptaskId);

		result = new ModelAndView("creditCard/create");

		final Collection<String> makers = this.confignService.findAll().iterator().next().getCardMaker();

		result.addObject("fixuptask", t);
		result.addObject("makers", makers);

		result = this.confignService.configGeneral(result);
		result = this.aService.isBanned(result);
		return result;
	}

	@RequestMapping(value = "/customer/save", method = RequestMethod.POST)
	public ModelAndView save(@RequestParam final FixUpTask task, final BindingResult binding) {

		ModelAndView result;
		if (binding.hasErrors())
			result = new ModelAndView("creditCard/create");
		else
			try {
				final FixUpTask fin = this.taskService.save(task);
				final Application a = this.applicationService.getApplicationAcceptedForFixUpTask(task.getId());
				result = new ModelAndView("redirect:../../application/customer,handyworker/display.do?applicationId=" + a.getId());
			} catch (final Throwable oops) {
				oops.printStackTrace();
				result = new ModelAndView("creditCard/create");
				result.addObject(task);
			}

		result = this.confignService.configGeneral(result);
		result = this.aService.isBanned(result);
		return result;
	}
}
