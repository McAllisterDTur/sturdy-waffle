/*
 * CustomerController.java
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
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ConfigurationService;
import domain.Configuration;

@Controller
@RequestMapping("configuration")
public class ConfigurationController extends AbstractController {

	@Autowired
	ConfigurationService	cService;
	@Autowired
	ActorService			aService;


	@RequestMapping(value = "/administrator/customize", method = RequestMethod.GET)
	public ModelAndView getConfiguration() {
		ModelAndView result = new ModelAndView("configuration/edit");
		final Configuration c = this.cService.findAll().iterator().next();
		result.addObject("configuration", c);
		result = this.cService.configGeneral(result);
		result = this.aService.isBanned(result);

		return result;
	}

	@RequestMapping(value = "/administrator/save", method = RequestMethod.POST)
	public ModelAndView getConfiguration(@Valid final Configuration configuration, final BindingResult br) {
		ModelAndView result = new ModelAndView("configuration/edit");
		if (br.hasErrors())
			result.addObject("configuration", configuration);
		else
			try {
				this.cService.save(configuration);
				result.addObject("configuration", configuration);

			} catch (final Throwable oops) {
				oops.printStackTrace();
				result.addObject("configuration", configuration);
			}
		result = this.cService.configGeneral(result);
		result = this.aService.isBanned(result);
		return result;
	}
}
