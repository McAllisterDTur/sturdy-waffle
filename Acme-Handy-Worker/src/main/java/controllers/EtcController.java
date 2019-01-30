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
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ConfigurationService;

@Controller
@RequestMapping("/etc")
public class EtcController extends AbstractController {

	@Autowired
	ActorService			aService;
	@Autowired
	ConfigurationService	cService;


	// Constructors -----------------------------------------------------------

	public EtcController() {
		super();
	}

	@RequestMapping(value = "/license")
	public ModelAndView license() {
		ModelAndView result;
		SimpleDateFormat formatter;
		String year;

		result = new ModelAndView("etc/license");
		formatter = new SimpleDateFormat("yyyy");
		year = formatter.format(new Date());

		result.addObject("year", year);
		result.addObject("locale", LocaleContextHolder.getLocale().getLanguage());
		result = this.cService.configGeneral(result);
		result = this.aService.isBanned(result);

		return result;
	}

	@RequestMapping(value = "/about")
	public ModelAndView about() {
		ModelAndView result;

		result = new ModelAndView("etc/about");

		result.addObject("locale", LocaleContextHolder.getLocale().getLanguage());
		result = this.cService.configGeneral(result);
		result = this.aService.isBanned(result);

		return result;
	}
}
