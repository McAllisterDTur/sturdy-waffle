
package controllers;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
		final double vat = this.confService.findAll().iterator().next().getVat();

		result = new ModelAndView("fixuptask/list");
		result.addObject("fixuptasks", tasks);
		result.addObject("requestURI", "/fixuptask/handyworker/list.do");
		result.addObject("vat", vat);
		return result;

	}
	@RequestMapping(value = "/customer/list", method = RequestMethod.GET)
	public ModelAndView listFromCustomer() {
		ModelAndView result;

		final Collection<FixUpTask> tasks = this.taskService.findFromLoggedCustomer();
		final double vat = this.confService.findAll().iterator().next().getVat();

		result = new ModelAndView("fixuptask/list");
		result.addObject("fixuptasks", tasks);
		result.addObject("requestURI", "/fixuptask/customer/list.do");
		result.addObject("vat", vat);
		return result;

	}

	@RequestMapping(value = "/handyworker/list", method = RequestMethod.GET, params = "keyword")
	public ModelAndView listHandySearch(@RequestParam final String keyword) {
		ModelAndView result;
		result = new ModelAndView("fixuptask/list");
		Collection<FixUpTask> tasks;
		final double vat = this.confService.findAll().iterator().next().getVat();
		try {
			final String decodedKeyword = URLDecoder.decode(keyword, "UTF-8");
			tasks = this.taskService.findByFilter(decodedKeyword);
			result.addObject("requestURI", "/fixuptask/handyworker/list.do");
			result.addObject("vat", vat);
			System.out.println("Decoded Keyword:" + decodedKeyword);
		} catch (final UnsupportedEncodingException e) {
			tasks = this.taskService.findAll();
			result.addObject("requestURI", "/fixuptask/handyworker/list.do");
		}
		System.out.println("Tasks: " + tasks);
		result.addObject("fixuptasks", tasks);
		return result;
	}

	@RequestMapping(value = "/customer/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;

		final FixUpTask task = this.taskService.create();

		result = new ModelAndView("fixuptask/edit");
		result = this.addCategoriesWarrantiesConfiguration(result);
		result.addObject("fixUpTask", task);

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

		return result;
	}

	@RequestMapping(value = "/customer/edit", method = RequestMethod.POST)
	public ModelAndView saveFixUpTask(@Valid final FixUpTask fixUpTask, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			System.out.println(binding.getFieldErrors());
			result = new ModelAndView("fixuptask/edit");
			result = this.addCategoriesWarrantiesConfiguration(result);
			result.addObject("fixUpTask", fixUpTask);

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

		final double vat = this.confService.findAll().iterator().next().getVat();
		task = this.taskService.findOne(fixuptaskId);
		Assert.notNull(task);
		result = new ModelAndView("fixuptask/display");
		result.addObject("fixuptask", task);
		result.addObject("vat", vat);

		return result;
	}
	private ModelAndView addCategoriesWarrantiesConfiguration(final ModelAndView model) {
		model.addObject("categories", this.catService.findAll());
		model.addObject("configuration", this.confService.findAll().iterator().next());
		model.addObject("warranties", this.warrantyService.findNotDraft());
		return model;
	}
}
