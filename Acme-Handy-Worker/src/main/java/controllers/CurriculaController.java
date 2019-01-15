/*
 * ProfileController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ActorService;
import services.ConfigurationService;
import services.CurriculaService;
import services.HandyWorkerService;
import domain.Curricula;
import domain.HandyWorker;

@Controller
@RequestMapping("/curricula")
public class CurriculaController extends AbstractController {

	@Autowired
	ActorService			actorService;
	@Autowired
	HandyWorkerService		hwService;
	@Autowired
	ConfigurationService	cService;
	@Autowired
	CurriculaService		currService;


	@RequestMapping(value = "/see", method = RequestMethod.GET)
	public ModelAndView seeCurricula(@RequestParam final Integer id) {
		final HandyWorker hw = this.hwService.findOne(id);
		if (hw == null) {
			final ModelAndView result = new ModelAndView("redirect:welcome/index.do");
			return result;
		}
		Curricula c = this.currService.findFromHandyWorker(hw);
		if (c == null) {
			c = this.currService.create();
			c.setHandyWorker(hw);
			this.currService.save(c);
		}
		ModelAndView result = new ModelAndView("curricula/see");
		result.addObject("handy", hw);
		result.addObject("logged", this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId() == hw.getId());
		result.addObject("personalRecord", c.getPersonalRecord());
		result.addObject("educationRecords", c.getEducationRecord());
		result.addObject("profesionalRecords", c.getProfessionalRecords());
		result.addObject("endorserRecords", c.getEndorserRecords());
		result.addObject("miscellaneousRecords", c.getMiscellaneousRecords());

		result = this.cService.configGeneral(result);
		result = this.actorService.isBanned(result);
		return result;
	}

}
