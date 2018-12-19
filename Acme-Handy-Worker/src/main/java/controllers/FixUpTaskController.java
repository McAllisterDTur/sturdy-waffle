
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

import services.CategoryService;
import services.ConfigurationService;
import services.FixUpTaskService;
import services.WarrantyService;
import domain.FixUpTask;

@Controller
@RequestMapping("/fixuptask")
public class FixUpTaskController extends AbstractController {

	@Autowired
	private FixUpTaskService		taskService;
	@Autowired
	private ConfigurationService	confService;
	@Autowired
	private CategoryService			catService;
	@Autowired
	private WarrantyService			warrantyService;


	public FixUpTaskController() {
		super();
	}

	@RequestMapping(value = "/handyworker/list", method = RequestMethod.GET)
	public ModelAndView listAll() {
		ModelAndView result;

		final Collection<FixUpTask> tasks = this.taskService.findAll();

		result = new ModelAndView("fixuptask/list");
		result.addObject("fixuptasks", tasks);
		result.addObject("requestURI", "/fixuptask/handyworker/list.do");
		return result;

	}

	@RequestMapping(value = "/customer/list", method = RequestMethod.GET)
	public ModelAndView listFromCustomer() {
		ModelAndView result;

		final Collection<FixUpTask> tasks = this.taskService.findFromLoggedCustomer();

		result = new ModelAndView("fixuptask/list");
		result.addObject("fixuptasks", tasks);
		result.addObject("requestURI", "/fixuptask/customer/list.do");
		return result;

	}

	@RequestMapping(value = "/customer/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;

		final FixUpTask task = this.taskService.create();

		result = new ModelAndView("fixuptask/edit");
		result.addObject("categories", this.catService.findAll());
		result.addObject("configuration", this.confService.findAll().iterator().next());
		// TODO: Encontrar solo las que no est�n en draft mode
		result.addObject("warranties", this.warrantyService.findAll());
		result.addObject("fixuptask", task);

		return result;

	}

	@RequestMapping(value = "/customer/edit", method = RequestMethod.GET)
	public ModelAndView editFixUpTask(@RequestParam final int fixuptaskId) {
		final ModelAndView result;
		FixUpTask task;

		task = this.taskService.findOne(fixuptaskId);
		Assert.notNull(task);
		result = new ModelAndView("fixuptask/edit");
		result.addObject("fixuptask", task);

		return result;
	}

	@RequestMapping(value = "/customer/edit", method = RequestMethod.POST)
	public ModelAndView saveFixUpTask(@Valid final FixUpTask fixuptask, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {

			System.out.println(binding.getFieldErrors());
			result = new ModelAndView("fixuptask/edit");
			result.addObject("fixuptask", fixuptask);
		} else
			try {
				this.taskService.save(fixuptask);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable opps) {
				result = new ModelAndView("fixuptask/edit");
				result.addObject("messageCode", "fixuptask.commit.error");
			}

		return result;
	}
	@RequestMapping(value = "/customer/delete", method = RequestMethod.GET)
	public ModelAndView deleteFixUpTask(@RequestParam final int fixuptaskId) {
		ModelAndView result;
		try {
			this.taskService.delete(fixuptaskId);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable opps) {
			result = new ModelAndView("fixuptask/list");
			result.addObject("messageCode", "fixuptask.commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/customer,handyworker/display", method = RequestMethod.GET)
	public ModelAndView displayFixUpTask(@RequestParam final int fixuptaskId) {
		final ModelAndView result;
		FixUpTask task;

		task = this.taskService.findOne(fixuptaskId);
		Assert.notNull(task);
		result = new ModelAndView("fixuptask/display");
		result.addObject("fixuptask", task);

		return result;
	}
}
