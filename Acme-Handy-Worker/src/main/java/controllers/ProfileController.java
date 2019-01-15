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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.ActorService;
import services.AdministratorService;
import services.ConfigurationService;
import services.CustomerService;
import services.FixUpTaskService;
import services.HandyWorkerService;
import services.RefereeService;
import services.SocialProfileService;
import services.SponsorService;
import services.TutorialService;
import domain.Actor;
import domain.Administrator;
import domain.Customer;
import domain.HandyWorker;
import domain.Referee;
import domain.SocialProfile;
import domain.Sponsor;

@Controller
@RequestMapping("/profile")
public class ProfileController extends AbstractController {

	@Autowired
	ActorService			actorService;
	@Autowired
	HandyWorkerService		hwService;
	@Autowired
	CustomerService			custoService;
	@Autowired
	RefereeService			rService;
	@Autowired
	SponsorService			sService;
	@Autowired
	AdministratorService	adminService;
	@Autowired
	ConfigurationService	cService;
	@Autowired
	SocialProfileService	spService;
	@Autowired
	TutorialService			tService;
	@Autowired
	FixUpTaskService		futService;


	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView create() {

		final Actor actor = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId());
		HandyWorker hw = this.hwService.create();
		final String role = LoginService.getPrincipal().getAuthorities().toArray()[0].toString();

		ModelAndView res = new ModelAndView("profile/edit");

		if (role.equals("HANDYWORKER")) {
			hw = this.hwService.findOne(actor.getId());
			res.addObject("worker", hw);
			res.addObject("handy", true);
		} else {
			res.addObject("actor", actor);
			res.addObject("handy", false);
		}
		res = this.cService.configGeneral(res);
		res = this.actorService.isBanned(res);
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Actor actor, final BindingResult br) {
		ModelAndView result;
		if (br.hasErrors()) {
			result = new ModelAndView("profile/edit");

			result.addObject("handy", false);
		} else {
			result = new ModelAndView("profile/edit");
			result.addObject("actor", actor);
			result.addObject("handy", false);
			final String role = LoginService.getPrincipal().getAuthorities().toArray()[0].toString();
			final Integer id = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();

			try {
				switch (role) {
				case "CUSTOMER":
					final Customer c = this.custoService.findOne(id);
					c.setAddress(actor.getAddress());
					c.setEmail(actor.getEmail());
					c.setMiddleName(actor.getMiddleName());
					c.setName(actor.getName());
					c.setPhone(actor.getPhone());
					c.setPhotoURL(actor.getPhotoURL());
					c.setSurname(actor.getSurname());
					this.custoService.save(c);
					break;
				case "ADMIN":
					final Administrator admin = this.adminService.findOne(id);
					admin.setAddress(actor.getAddress());
					admin.setEmail(actor.getEmail());
					admin.setMiddleName(actor.getMiddleName());
					admin.setName(actor.getName());
					admin.setPhone(actor.getPhone());
					admin.setPhotoURL(actor.getPhotoURL());
					admin.setSurname(actor.getSurname());
					this.adminService.save(admin);
					break;
				case "SPONSOR":
					final Sponsor s = this.sService.findOne(id);
					s.setAddress(actor.getAddress());
					s.setEmail(actor.getEmail());
					s.setMiddleName(actor.getMiddleName());
					s.setName(actor.getName());
					s.setPhone(actor.getPhone());
					s.setPhotoURL(actor.getPhotoURL());
					s.setSurname(actor.getSurname());
					this.sService.save(s);
					break;
				case "REFEREE":
					final Referee r = this.rService.findOne(id);
					r.setAddress(actor.getAddress());
					r.setEmail(actor.getEmail());
					r.setMiddleName(actor.getMiddleName());
					r.setName(actor.getName());
					r.setPhone(actor.getPhone());
					r.setPhotoURL(actor.getPhotoURL());
					r.setSurname(actor.getSurname());
					this.rService.save(r);
					break;
				default:
					throw new NullPointerException();
				}

			} catch (final Throwable oops) {
				result.addObject("success", false);
				oops.printStackTrace();
				return result;
			}
			result.addObject("success", true);
		}
		result = this.cService.configGeneral(result);
		result = this.actorService.isBanned(result);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveHandy")
	public ModelAndView saveHandy(@Valid final HandyWorker worker, final BindingResult br) {

		ModelAndView result;
		if (br.hasErrors()) {

			result = new ModelAndView("profile/edit");

			result.addObject("handy", true);
		} else {
			result = new ModelAndView("profile/edit");
			result.addObject("worker", worker);
			result.addObject("handy", true);
			final String role = LoginService.getPrincipal().getAuthorities().toArray()[0].toString();
			final Integer id = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
			try {
				if (role.equals("HANDYWORKER")) {
					final HandyWorker hw = this.hwService.findOne(id);
					hw.setAddress(worker.getAddress());
					hw.setEmail(worker.getEmail());
					hw.setMiddleName(worker.getMiddleName());
					hw.setMake(worker.getMake());
					hw.setName(worker.getName());
					hw.setPhone(worker.getPhone());
					hw.setPhotoURL(worker.getPhotoURL());
					hw.setSurname(worker.getSurname());
					this.hwService.save(hw);
					result.addObject("success", true);
				} else
					throw new IllegalAccessError();
			} catch (final Throwable oops) {
				result.addObject("success", false);
				oops.printStackTrace();
			}
		}
		result = this.cService.configGeneral(result);
		result = this.actorService.isBanned(result);
		return result;
	}

	@RequestMapping(value = "see", method = RequestMethod.GET)
	public ModelAndView seeProfile() {
		ModelAndView result = new ModelAndView("profile/see");
		final Actor actor = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId());
		final String role = actor.getAccount().getAuthorities().iterator().next().toString();
		result.addObject("actor", actor);
		result.addObject("socialProfiles", this.spService.findByActor(actor.getId()));
		result.addObject("username", actor.getAccount().getUsername());
		result.addObject("logged", true);
		result.addObject("customer", role.equals("CUSTOMER"));
		if (role.equals("CUSTOMER")) {
			result.addObject("endorsable", true);
			result.addObject("score", this.custoService.findOne(actor.getId()).getScore());
			result.addObject("handy", false);
			result.addObject("fixUpTasks", this.futService.findFromCustomer(actor.getId()));
		} else if (role.equals("HANDYWORKER")) {
			result.addObject("handy", true);
			result.addObject("make", this.hwService.findOne(actor.getId()).getMake());
			result.addObject("endorsable", true);
			result.addObject("score", this.hwService.findOne(actor.getId()).getScore());
			result.addObject("tutorials", this.tService.findAllFromHandyworker(actor.getId()));
		} else {
			result.addObject("endorsable", false);
			result.addObject("handy", false);
		}
		result = this.cService.configGeneral(result);
		result = this.actorService.isBanned(result);
		return result;
	}

	@RequestMapping(value = "seeId", method = RequestMethod.GET)
	public ModelAndView seeProfile(@RequestParam final Integer id) {
		ModelAndView result = new ModelAndView("profile/see");
		final Actor actor = this.actorService.findOne(id);
		final String role = actor.getAccount().getAuthorities().iterator().next().toString();
		result.addObject("actor", actor);
		result.addObject("banned", actor.getBanned());
		result.addObject("socialProfiles", this.spService.findByActor(actor.getId()));
		result.addObject("username", actor.getAccount().getUsername());
		final UserAccount logged = LoginService.getPrincipal();
		if (logged != null && logged.equals(actor.getAccount()))
			result.addObject("logged", true);
		else
			result.addObject("logged", false);
		if (role.equals("CUSTOMER")) {
			result.addObject("endorsable", true);
			result.addObject("score", this.custoService.findOne(id).getScore());
			result.addObject("handy", false);
		} else if (role.equals("HANDYWORKER")) {
			result.addObject("handy", true);
			result.addObject("make", this.hwService.findOne(id).getMake());
			result.addObject("endorsable", true);
			result.addObject("score", this.hwService.findOne(id).getScore());
			result.addObject("tutorials", this.tService.findAllFromHandyworker(actor.getId()));
		} else {
			result.addObject("endorsable", false);
			result.addObject("handy", false);
		}
		result = this.cService.configGeneral(result);
		result = this.actorService.isBanned(result);
		return result;
	}

	@RequestMapping(value = "administrator/ban", method = RequestMethod.GET)
	public ModelAndView banProfile(@RequestParam final Integer id) {
		ModelAndView result = new ModelAndView("redirect:/profile/seeId.do?id=" + id);
		final Actor a = this.actorService.findOne(id);
		if (!a.getBanned())
			this.actorService.ban(a);
		result = this.cService.configGeneral(result);
		result = this.actorService.isBanned(result);
		return result;
	}

	@RequestMapping(value = "administrator/unban", method = RequestMethod.GET)
	public ModelAndView unbanProfile(@RequestParam final Integer id) {
		ModelAndView result = new ModelAndView("redirect:/profile/seeId.do?id=" + id);
		final Actor a = this.actorService.findOne(id);
		if (a.getBanned())
			this.actorService.unban(a);
		result = this.cService.configGeneral(result);
		result = this.actorService.isBanned(result);
		return result;
	}

	// Social Profiles -------------------------------------------------------

	@RequestMapping(value = "social/edit")
	public ModelAndView newSocialProfile() {
		ModelAndView result;

		final SocialProfile sp = this.spService.create();
		final Actor actor = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId());
		sp.setActor(actor);

		result = new ModelAndView("profile/social/seeId");
		result.addObject("socialProfile", sp);

		result = this.cService.configGeneral(result);
		result = this.actorService.isBanned(result);
		return result;
	}

	@RequestMapping(value = "social/save", method = RequestMethod.POST)
	public ModelAndView saveSocialProfile(@Valid final SocialProfile socialProfile, final BindingResult br) {
		ModelAndView result;

		final Actor actor = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId());
		socialProfile.setActor(actor);

		if (br.hasErrors())
			result = new ModelAndView("profile/social/seeId");
		else
			try {
				this.spService.save(socialProfile);
				result = new ModelAndView("redirect:/profile/see.do");
			} catch (final Throwable oops) {
				oops.printStackTrace();
				result = new ModelAndView("profile/social/seeId");
				result.addObject("socialProfile", socialProfile);
			}

		result = this.cService.configGeneral(result);
		result = this.actorService.isBanned(result);
		return result;
	}

	@RequestMapping(value = "social/delete", method = RequestMethod.GET)
	public ModelAndView saveSocialProfile(@RequestParam final Integer id) {
		ModelAndView result = new ModelAndView("redirect:/profile/see.do");

		final Actor actor = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId());
		final SocialProfile sp = this.spService.findOne(id);

		if (sp.getActor().equals(actor))
			this.spService.delete(sp);

		result = this.cService.configGeneral(result);
		result = this.actorService.isBanned(result);
		return result;
	}

}
