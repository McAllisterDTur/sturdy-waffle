
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
	private ConfigurationService	configurationService;
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

		final Collection<String> makers = this.configurationService.findAll().iterator().next().getCardMaker();

		result.addObject("fixuptask", t);
		result.addObject("makers", makers);

		result = this.configurationService.configGeneral(result);
		result = this.aService.isBanned(result);
		return result;
	}

	@RequestMapping(value = "/customer/save", method = RequestMethod.POST)
	public ModelAndView save(@Valid final FixUpTask task, final BindingResult binding, @RequestParam final int applicationId) {

		ModelAndView result;

		System.out.println(task.getCreditCard());
		if (binding.hasErrors()) {
			final Application a = this.applicationService.findOne(applicationId);
			result = new ModelAndView("creditCard/create");
			final Collection<String> makers = this.configurationService.findAll().iterator().next().getCardMaker();

			result.addObject("fixuptask", task);
			result.addObject("makers", makers);
			result.addObject("applicationId", a.getId());
			System.out.println("binding hecho");
		} else {
			final Application a = this.applicationService.findOne(applicationId);
			try {
				final FixUpTask fin = this.taskService.save(task);
				Assert.notNull(fin);
				//final Application a = this.applicationService.getApplicationAcceptedForFixUpTask(fin.getId());

				a.setStatus("ACCEPTED");
				final Application afin = this.applicationService.save(a);
				Assert.notNull(afin);

				result = new ModelAndView("redirect:../../application/customer,handyworker/display.do?applicationId=" + a.getId());
			} catch (final Throwable oops) {
				oops.printStackTrace();
				result = new ModelAndView("creditCard/create");
				final Collection<String> makers = this.configurationService.findAll().iterator().next().getCardMaker();

				result.addObject("fixuptask", task);
				result.addObject("makers", makers);
				result.addObject("applicationId", a.getId());
				System.out.println("catch terminado");
			}
		}

		result = this.configurationService.configGeneral(result);
		result = this.aService.isBanned(result);
		return result;
	}
}
