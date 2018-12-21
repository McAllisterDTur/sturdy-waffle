
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.PhaseService;
import domain.Phase;

@Controller
@RequestMapping("/phase/**")
public class PhaseController extends AbstractController {

	@Autowired
	private PhaseService	phaseService;


	@RequestMapping(value = "handyworker/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int phaseId) {

		ModelAndView res;

		final Phase p = this.phaseService.findOne(phaseId);

		res = new ModelAndView("phase/handyworker/display");
		res.addObject("phase", p);

		return res;
	}

	@RequestMapping(value = "/handyworker/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int applicationId) {
		final ModelAndView res;

		final Phase p = this.phaseService.create(applicationId);
		System.out.println(p);
		res = new ModelAndView("phase/handyworker/create");
		res.addObject("phase", p);

		return res;
	}

	@RequestMapping(value = "/handyworker/save", method = RequestMethod.POST)
	public ModelAndView save(@Valid final Phase phase, final BindingResult bind) {
		ModelAndView res;
		System.out.println("Errors: " + phase);
		if (bind.hasErrors())
			res = this.createMAV(phase);
		else
			try {
				final Phase p = this.phaseService.save(phase);
				res = new ModelAndView("redirect:display.do?phaseId=" + p.getId());
			} catch (final Throwable oops) {
				oops.printStackTrace();
				res = this.createMAV(phase);
			}

		return res;
	}
	private ModelAndView createMAV(final Phase p) {
		ModelAndView res = null;
		try {
			res = new ModelAndView("phase/handyworker/create");
			res.addObject("phase", p);
		} catch (final Exception oops) {
			oops.printStackTrace();
		}

		return res;
	}

}
