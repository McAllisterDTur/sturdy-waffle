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

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.TutorialService;
import domain.Tutorial;

@Controller
@RequestMapping("/tutorial")
public class TutorialController extends AbstractController {

	@Autowired
	private TutorialService	tutorialService;

	@Autowired
	private ActorService	actorService;


	// Constructors -----------------------------------------------------------

	public TutorialController() {
		super();
	}

	// Tutorials ---------------------------------------------------------------		

	// La url es la que se va a ver en la página
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView allTutorials() {
		final Collection<Tutorial> tutorials = this.tutorialService.findAll();
		//Result
		ModelAndView result;
		// Esta url es la de la vista
		result = new ModelAndView("tutorial/list");
		result.addObject("tutorials", tutorials);
		// TODO: Test. Este atributo es la especificación de la url de la vista para la paginación (Creo)
		result.addObject("url", "all");
		return result;
	}

	@RequestMapping(value = "/see", method = RequestMethod.GET)
	public ModelAndView seeTutorial(@RequestParam("id") final int id) {
		final Tutorial tutorial = this.tutorialService.findOne(id);
		//Result
		ModelAndView result;
		result = new ModelAndView("tutorial/see");
		result.addObject("tutorial", tutorial);
		return result;
	}
	// TODO: Mirar como poner parámetros opcionales
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView newTutorial() {
		final Tutorial tutorial = this.tutorialService.create();
		//Result
		ModelAndView result;
		result = new ModelAndView("tutorial/form");
		result.addObject("tutorial", tutorial);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView saveTutorial(@Valid final Tutorial tutorial, final BindingResult binding) {
		//Result
		System.out.println(binding.getFieldErrors());
		ModelAndView result;
		this.tutorialService.save(tutorial);
		result = new ModelAndView("redirect:all.do");
		return result;
	}

}
