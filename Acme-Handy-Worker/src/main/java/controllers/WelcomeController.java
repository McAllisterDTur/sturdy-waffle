/*
 * WelcomeController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.ActorService;
import services.ConfigurationService;
import domain.Configuration;

@Controller
@RequestMapping("/welcome")
public class WelcomeController extends AbstractController {

	@Autowired
	ActorService			aService;
	@Autowired
	ConfigurationService	cService;


	// Constructors -----------------------------------------------------------

	public WelcomeController() {
		super();
	}

	// Index ------------------------------------------------------------------

	@RequestMapping(value = "/index")
	public ModelAndView index() {
		ModelAndView result;
		SimpleDateFormat formatter;
		String moment;
		String name;

		try {
			final UserAccount logged = LoginService.getPrincipal();
			if (logged == null)
				name = "";
			else
				name = " " + this.aService.findByUserAccountId(logged.getId()).getName();
		} catch (final Throwable oops) {
			name = "";
		}

		formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		moment = formatter.format(new Date());

		result = new ModelAndView("welcome/index");
		final Configuration config = this.cService.findAll().iterator().next();
		result.addObject("bannerURL", config.getBannerURL());
		result.addObject("welcomeEN", config.getWelcomeEN());
		result.addObject("welcomeSP", config.getWelcomeSP());
		result.addObject("namesystem", config.getNameSystem());
		result.addObject("name", name);
		result.addObject("moment", moment);
		result.addObject("bannerURL", this.cService.findAll().iterator().next().getBannerURL());
		return result;
	}
}
