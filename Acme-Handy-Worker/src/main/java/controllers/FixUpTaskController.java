
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
import services.CategoryService;
import services.ConfigurationService;
import services.FixUpTaskService;
import services.WarrantyService;
import domain.Finder;
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
	@Autowired
	private ActorService			actorService;


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
		result.addObject("finder", new Finder());
		result.addObject("categories", this.catService.findAll());
		result.addObject("vat", this.confService.findAll().iterator().next().getVat());
		result = this.confService.configGeneral(result);
		result = this.actorService.isBanned(result);
		return result;

	}

	@RequestMapping(value = "/customer/list", method = RequestMethod.GET)
	public ModelAndView listFromCustomer() {
		ModelAndView result;

		final Collection<FixUpTask> tasks = this.taskService.findFromLoggedCustomer();

		result = new ModelAndView("fixuptask/list");
		result.addObject("fixuptasks", tasks);
		result.addObject("requestURI", "/fixuptask/customer/list.do");
		result.addObject("vat", this.confService.findAll().iterator().next().getVat());
		result = this.confService.configGeneral(result);
		result = this.actorService.isBanned(result);
		return result;

	}

	@RequestMapping(value = "/handyworker/list", method = RequestMethod.POST)
	public ModelAndView listHandySearch(final Finder finder) {
		ModelAndView result;
		result = new ModelAndView("fixuptask/list");
		Collection<FixUpTask> tasks;
		tasks = this.taskService.findByFilter(finder.getKeyWord(), finder.getCategory(), finder.getWarranty(), finder.getMinPrice(), finder.getMaxPrice(), finder.getStartDate(), finder.getEndDate());
		result.addObject("requestURI", "/fixuptask/handyworker/list.do");
		result.addObject("fixuptasks", tasks);
		result.addObject("finder", finder);
		result.addObject("categories", this.catService.findAll());
		result.addObject("vat", this.confService.findAll().iterator().next().getVat());
		result = this.confService.configGeneral(result);
		result = this.actorService.isBanned(result);

		return result;
	}

	@RequestMapping(value = "/customer/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;

		final FixUpTask task = this.taskService.create();

		result = new ModelAndView("fixuptask/edit");
		result = this.addCategoriesWarrantiesConfiguration(result);
		result.addObject("fixUpTask", task);

		result = this.confService.configGeneral(result);
		result = this.actorService.isBanned(result);
		return result;

	}

	@RequestMapping(value = "/customer/edit", method = RequestMethod.GET)
	public ModelAndView editFixUpTask(@RequestParam final int fixuptaskId) {
		ModelAndView result;
		FixUpTask task;

		task = this.taskService.findOne(fixuptaskId);
		Assert.notNull(task);
		result = new ModelAndView("fixuptask/edit");
		result = this.addCategoriesWarrantiesConfiguration(result);
		result.addObject("fixUpTask", task);

		result = this.confService.configGeneral(result);
		result = this.actorService.isBanned(result);
		return result;
	}

	@RequestMapping(value = "/customer/edit", method = RequestMethod.POST)
	public ModelAndView saveFixUpTask(@Valid final FixUpTask fixUpTask, final BindingResult binding) {
		ModelAndView result;
		String dateError = "";
		if (fixUpTask.getPeriodStart() != null && fixUpTask.getPeriodEnd() != null)
			dateError = this.taskService.checkIfBefore(fixUpTask.getPeriodStart(), fixUpTask.getPeriodEnd());
		if (binding.hasErrors() || !dateError.isEmpty()) {
			System.out.println(binding.getFieldErrors());
			result = new ModelAndView("fixuptask/edit");
			result = this.addCategoriesWarrantiesConfiguration(result);
			result.addObject("fixUpTask", fixUpTask);
			result.addObject("dateError", dateError);

		} else
			try {
				this.taskService.save(fixUpTask);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable opps) {
				result = new ModelAndView("fixuptask/edit");
				result.addObject("messageCode", "fixuptask.commit.error");
				result = this.addCategoriesWarrantiesConfiguration(result);
				result.addObject("fixUpTask", fixUpTask);
			}
		result.addObject("vat", this.confService.findAll().iterator().next().getVat());
		result = this.confService.configGeneral(result);
		result = this.actorService.isBanned(result);
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

		result = this.confService.configGeneral(result);
		result = this.actorService.isBanned(result);
		return result;
	}

	@RequestMapping(value = "/customer,handyworker/display", method = RequestMethod.GET)
	public ModelAndView displayFixUpTask(@RequestParam final int fixuptaskId) {
		ModelAndView result;
		FixUpTask task;

		final double vat = this.confService.findAll().iterator().next().getVat();
		try {
			task = this.taskService.findOne(fixuptaskId);
			Assert.notNull(task);
			result = new ModelAndView("fixuptask/display");
			result.addObject("fixuptask", task);
			result.addObject("vat", vat);
		} catch (final Exception oops) {
			result = new ModelAndView("welcome/index");
		}

		result = this.confService.configGeneral(result);
		result = this.actorService.isBanned(result);
		return result;
	}
	private ModelAndView addCategoriesWarrantiesConfiguration(final ModelAndView model) {
		model.addObject("categories", this.catService.findAll());
		model.addObject("configuration", this.confService.findAll().iterator().next());
		model.addObject("warranties", this.warrantyService.findNotDraft());
		return model;
	}
}
