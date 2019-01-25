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

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ActorService;
import services.ConfigurationService;
import services.CurriculaService;
import services.EducationRecordService;
import services.EndorserRecordService;
import services.HandyWorkerService;
import services.MiscellaneousRecordService;
import services.PersonalRecordService;
import services.ProfessionalRecordService;
import domain.Curricula;
import domain.EducationRecord;
import domain.EndorserRecord;
import domain.HandyWorker;
import domain.MiscellaneousRecord;
import domain.PersonalRecord;
import domain.ProfessionalRecord;

@Controller
@RequestMapping("profile/curricula")
public class CurriculaController extends AbstractController {

	@Autowired
	ActorService				actorService;
	@Autowired
	HandyWorkerService			hwService;
	@Autowired
	ConfigurationService		cService;
	@Autowired
	CurriculaService			currService;
	@Autowired
	PersonalRecordService		perService;
	@Autowired
	EducationRecordService		eduService;
	@Autowired
	ProfessionalRecordService	proService;
	@Autowired
	EndorserRecordService		endoService;
	@Autowired
	MiscellaneousRecordService	miscService;


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

	@RequestMapping(value = "/edit/personalRecord", method = RequestMethod.GET)
	public ModelAndView editPersonalRecord(@RequestParam(required = false, value = "id") final Integer id) {
		ModelAndView result;
		PersonalRecord pr;
		if (id == null) {
			pr = this.perService.create();
			System.out.println("No hecho");
		} else {
			pr = this.perService.findOne(id);
			System.out.println("Está hecho");
		}

		try {
			if (id != null)
				Assert.isTrue(pr.equals(this.perService.findByCurricula(this.currService.findFromLoggedHandyWorker().getId())));
			result = new ModelAndView("curricula/edit");
			result.addObject("personal", true);
			result.addObject("personalRecord", pr);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/profile/see.do");
		}
		return result;
	}
	@RequestMapping(value = "/edit/savePersonal", method = RequestMethod.POST)
	public ModelAndView savePersonalRecord(@Valid final PersonalRecord pr, final BindingResult br) {
		ModelAndView result;
		if (br.hasErrors()) {
			result = new ModelAndView("curricula/edit");
			result.addObject("personal", true);
		} else
			try {
				if (pr.getId() != 0)
					Assert.isTrue(pr.equals(this.perService.findByCurricula(this.currService.findFromLoggedHandyWorker().getId())));
				this.perService.save(pr);
				final Integer id = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
				result = new ModelAndView("redirect:/profile/curricula/see.do?id=" + id);
			} catch (final Throwable oops) {
				oops.printStackTrace();
				return new ModelAndView("redirect:/profile/see.do");
			}
		return result;
	}

	@RequestMapping(value = "/edit/addEducationRecord", method = RequestMethod.GET)
	public ModelAndView addEducationRecord() {
		final ModelAndView result = new ModelAndView("curricula/edit");
		result.addObject("educationRecord", this.eduService.create());
		result.addObject("education", true);
		return result;
	}

	@RequestMapping(value = "/edit/saveEducation", method = RequestMethod.POST)
	public ModelAndView saveEducationRecord(@Valid final EducationRecord er, final BindingResult br) {
		ModelAndView result;
		if (br.hasErrors()) {
			result = new ModelAndView("curricula/edit");
			result.addObject("education", true);
		} else
			try {
				this.eduService.save(er);
				final Integer id = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
				result = new ModelAndView("redirect:/profile/curricula/see.do?id=" + id);
			} catch (final Throwable oops) {
				oops.printStackTrace();
				return new ModelAndView("redirect:/profile/see.do");
			}
		return result;
	}

	@RequestMapping(value = "/edit/addProfessionalRecord", method = RequestMethod.GET)
	public ModelAndView addProfessionalRecord() {
		final ModelAndView result = new ModelAndView("curricula/edit");
		result.addObject("professionalRecord", this.proService.create());
		result.addObject("professional", true);
		return result;
	}

	@RequestMapping(value = "/edit/saveProfessional", method = RequestMethod.POST)
	public ModelAndView saveProfessionalRecord(@Valid final ProfessionalRecord pr, final BindingResult br) {
		ModelAndView result;
		if (br.hasErrors()) {
			result = new ModelAndView("curricula/edit");
			result.addObject("professional", true);
		} else
			try {
				this.proService.save(pr);
				final Integer id = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
				result = new ModelAndView("redirect:/profile/curricula/see.do?id=" + id);
			} catch (final Throwable oops) {
				oops.printStackTrace();
				return new ModelAndView("redirect:/profile/see.do");
			}
		return result;
	}

	@RequestMapping(value = "/edit/addEndorserRecord", method = RequestMethod.GET)
	public ModelAndView addEndorserRecord() {
		final ModelAndView result = new ModelAndView("curricula/edit");
		result.addObject("endorserRecord", this.endoService.create());
		result.addObject("endorser", true);
		return result;
	}

	@RequestMapping(value = "/edit/saveEndorser", method = RequestMethod.POST)
	public ModelAndView saveEndorserRecord(@Valid final EndorserRecord er, final BindingResult br) {
		ModelAndView result;
		if (br.hasErrors()) {
			result = new ModelAndView("curricula/edit");
			result.addObject("endorser", true);
		} else
			try {
				this.endoService.save(er);
				final Integer id = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
				result = new ModelAndView("redirect:/profile/curricula/see.do?id=" + id);
			} catch (final Throwable oops) {
				oops.printStackTrace();
				return new ModelAndView("redirect:/profile/see.do");
			}
		return result;
	}

	@RequestMapping(value = "/edit/addMiscRecord", method = RequestMethod.GET)
	public ModelAndView addMiscellaneousRecord() {
		final ModelAndView result = new ModelAndView("curricula/edit");
		result.addObject("miscellaneousRecord", this.miscService.create());
		result.addObject("miscellaneous", true);
		return result;
	}

	@RequestMapping(value = "edit/saveMiscellaneous", method = RequestMethod.POST)
	public ModelAndView saveMiscellaneousRecord(@Valid final MiscellaneousRecord mr, final BindingResult br) {
		ModelAndView result;
		if (br.hasErrors()) {
			result = new ModelAndView("curricula/edit");
			result.addObject("endorser", true);
		} else
			try {
				this.miscService.save(mr);
				final Integer id = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
				result = new ModelAndView("redirect:/profile/curricula/see.do?id=" + id);
			} catch (final Throwable oops) {
				oops.printStackTrace();
				return new ModelAndView("redirect:/profile/see.do");
			}
		return result;
	}
}
