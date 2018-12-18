
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.FixUpTaskService;
import domain.FixUpTask;

@Controller
@RequestMapping("/fixuptask")
public class FixUpTaskController extends AbstractController {

	@Autowired
	FixUpTaskService	taskService;


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
}
