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
import services.AdministratorService;
import services.CustomerService;
import services.HandyWorkerService;
import services.RefereeService;
import services.SponsorService;
import domain.Actor;
import domain.Administrator;
import domain.Customer;
import domain.HandyWorker;
import domain.Referee;
import domain.Sponsor;

@Controller
@RequestMapping("/profile")
public class ProfileController extends AbstractController {

	@Autowired
	ActorService			actorService;
	@Autowired
	HandyWorkerService		hwService;
	@Autowired
	CustomerService			cService;
	@Autowired
	RefereeService			rService;
	@Autowired
	SponsorService			sService;
	@Autowired
	AdministratorService	adminService;


	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView create() {

		final Actor actor = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId());
		HandyWorker hw = this.hwService.create();
		final String role = LoginService.getPrincipal().getAuthorities().toArray()[0].toString();

		final ModelAndView res = new ModelAndView("profile/edit");

		if (role.equals("HANDYWORKER")) {
			hw = this.hwService.findOne(actor.getId());
			res.addObject("worker", hw);
			res.addObject("handy", true);
		} else {
			res.addObject("actor", actor);
			res.addObject("handy", false);
		}
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Actor actor) {
		final ModelAndView result = new ModelAndView("profile/edit");
		result.addObject("actor", actor);
		result.addObject("handy", false);
		final String role = LoginService.getPrincipal().getAuthorities().toArray()[0].toString();
		final Integer id = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();

		try {
			switch (role) {
			case "CUSTOMER":
				final Customer c = this.cService.findOne(id);
				c.setAddress(actor.getAddress());
				c.setEmail(actor.getEmail());
				c.setMiddleName(actor.getMiddleName());
				c.setName(actor.getName());
				c.setPhone(actor.getPhone());
				c.setPhotoURL(actor.getPhotoURL());
				c.setSurname(actor.getSurname());
				this.cService.save(c);
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
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveHandy")
	public ModelAndView saveHandy(final HandyWorker worker) {

		final ModelAndView result = new ModelAndView("profile/edit");
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
			return result;
		}

		return result;
	}

	@RequestMapping(value = "see", method = RequestMethod.GET)
	public ModelAndView seeProfile() {
		final ModelAndView result = new ModelAndView("profile/see");
		final Actor actor = this.actorService.findByUserAccountId(LoginService.getPrincipal().getId());
		final String role = actor.getAccount().getAuthorities().iterator().next().toString();
		result.addObject("actor", actor);
		result.addObject("username", actor.getAccount().getUsername());
		result.addObject("logged", true);
		if (role.equals("CUSTOMER")) {
			result.addObject("endorsable", true);
			result.addObject("score", this.cService.findOne(actor.getId()).getScore());
			result.addObject("handy", false);
		} else if (role.equals("HANDYWORKER")) {
			result.addObject("handy", true);
			result.addObject("make", this.hwService.findOne(actor.getId()).getMake());
			result.addObject("endorsable", true);
			result.addObject("score", this.hwService.findOne(actor.getId()).getScore());
		} else {
			result.addObject("endorsable", false);
			result.addObject("handy", false);
		}
		return result;
	}

	@RequestMapping(value = "seeId", method = RequestMethod.GET)
	public ModelAndView seeProfile(@RequestParam final Integer id) {
		final ModelAndView result = new ModelAndView("profile/see");
		final Actor actor = this.actorService.findOne(id);
		final String role = actor.getAccount().getAuthorities().iterator().next().toString();
		result.addObject("actor", actor);
		result.addObject("username", actor.getAccount().getUsername());
		if (LoginService.getPrincipal().equals(actor.getAccount()))
			result.addObject("logged", true);
		else
			result.addObject("logged", false);
		if (role.equals("CUSTOMER")) {
			result.addObject("endorsable", true);
			result.addObject("score", this.cService.findOne(id).getScore());
			result.addObject("handy", false);
		} else if (role.equals("HANDYWORKER")) {
			result.addObject("handy", true);
			result.addObject("make", this.hwService.findOne(id).getMake());
			result.addObject("endorsable", true);
			result.addObject("score", this.hwService.findOne(id).getScore());
		} else {
			result.addObject("endorsable", false);
			result.addObject("handy", false);
		}
		return result;
	}
	// Action-1 ---------------------------------------------------------------		

	@RequestMapping("/action-1")
	public ModelAndView action1() {
		ModelAndView result;

		result = new ModelAndView("profile/action-1");

		return result;
	}

	// Action-2 ---------------------------------------------------------------		

	@RequestMapping("/action-2")
	public ModelAndView action2() {
		ModelAndView result;

		result = new ModelAndView("profile/action-2");

		return result;
	}

	// Action-2 ---------------------------------------------------------------		

	@RequestMapping("/action-3")
	public ModelAndView action3() {
		throw new RuntimeException("Oops! An *expected* exception was thrown. This is normal behaviour.");
	}

}
