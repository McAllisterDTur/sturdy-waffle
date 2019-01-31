
package controllers;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ConfigurationService;
import services.PhaseService;
import domain.Application;
import domain.Phase;

@Controller
@RequestMapping("/phase/**")
public class PhaseController extends AbstractController {

	@Autowired
	private PhaseService			phaseService;
	@Autowired
	private ConfigurationService	configService;
	@Autowired
	private ActorService			aService;


	@RequestMapping(value = "handyworker/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int phaseId) {

		ModelAndView res;

		final Phase p = this.phaseService.findOne(phaseId);

		res = new ModelAndView("phase/handyworker/display");
		res.addObject("phase", p);

		res = this.configService.configGeneral(res);
		res = this.aService.isBanned(res);
		return res;
	}

	@RequestMapping(value = "/handyworker/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int applicationId) {
		ModelAndView res;

		final Phase p = this.phaseService.create(applicationId);
		System.out.println(p);
		res = new ModelAndView("phase/handyworker/create");
		res.addObject("phase", p);

		res = this.configService.configGeneral(res);
		res = this.aService.isBanned(res);
		return res;
	}

	@RequestMapping(value = "/handyworker/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int phaseId) {
		ModelAndView res;

		final Phase p = this.phaseService.findOne(phaseId);
		System.out.println(p);
		res = new ModelAndView("phase/handyworker/edit");
		res.addObject("phase", p);

		res = this.configService.configGeneral(res);
		res = this.aService.isBanned(res);
		return res;
	}
	@RequestMapping(value = "/handyworker/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int phaseId) {
		ModelAndView res;

		final Phase p = this.phaseService.findOne(phaseId);
		final Application a = p.getApplication();
		final int id = a.getId();
		this.phaseService.delete(p);

		res = new ModelAndView("phase/handyworker/exito");
		res = this.configService.configGeneral(res);
		res = this.aService.isBanned(res);
		return res;
	}

	@RequestMapping(value = "/handyworker/save", method = RequestMethod.POST)
	public ModelAndView save(@Valid final Phase phase, final BindingResult bind) {
		ModelAndView res;
		String dateError = "";
		if (phase.getStartTime() != null && phase.getEndTime() != null) {
			dateError = this.phaseService.checkIfBefore(phase.getStartTime(), phase.getEndTime());
			final Date d = new Date();
			dateError = dateError.isEmpty() ? this.phaseService.checkIfBefore(phase.getApplication().getFixUpTask().getPeriodStart(), phase.getStartTime()) : dateError;
		}
		if (bind.hasErrors() || !dateError.isEmpty()) {
			System.out.println("Errors: " + phase);
			res = new ModelAndView("phase/handyworker/create");
			res.addObject("phase", phase);
			res.addObject("dateError", dateError);
		} else
			try {
				final Phase p = this.phaseService.save(phase);
				res = new ModelAndView("redirect:display.do?phaseId=" + p.getId());
			} catch (final Throwable oops) {
				oops.printStackTrace();
				res = this.createMAV(phase);
			}
		res = this.configService.configGeneral(res);
		res = this.aService.isBanned(res);
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

		res = this.configService.configGeneral(res);
		res = this.aService.isBanned(res);
		return res;
	}

}
