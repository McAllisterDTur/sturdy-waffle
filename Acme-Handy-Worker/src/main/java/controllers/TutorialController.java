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

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.TutorialService;
import domain.Tutorial;

@Controller
@RequestMapping("/tutorial")
public class TutorialController extends AbstractController {

	@Autowired
	private TutorialService	tutorialService;


	// Constructors -----------------------------------------------------------

	public TutorialController() {
		super();
	}

	// Tutorials ---------------------------------------------------------------		

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ModelAndView test1() {
		final Collection<Tutorial> tutorials = this.tutorialService.findAll();
		//Result
		ModelAndView result;
		result = new ModelAndView("tutorial/all");
		result.addObject("tutorials", tutorials);
		return result;
	}
}
