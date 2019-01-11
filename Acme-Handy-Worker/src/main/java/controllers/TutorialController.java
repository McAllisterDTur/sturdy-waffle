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

import security.LoginService;
import services.ActorService;
import services.ConfigurationService;
import services.TutorialService;
import domain.HandyWorker;
import domain.Tutorial;

@Controller
@RequestMapping("/tutorial")
public class TutorialController extends AbstractController {

	@Autowired
	private TutorialService			tutorialService;
	@Autowired
	private ActorService			actorService;
	@Autowired
	private ConfigurationService	configService;


	// Constructors -----------------------------------------------------------

	public TutorialController() {
		super();
	}

	// Tutorials ---------------------------------------------------------------		

	// La url es la que se va a ver en la página
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView allTutorials() {
		final Collection<Tutorial> tutorials = this.tutorialService.findAll();
		ModelAndView result;
		result = new ModelAndView("tutorial/list");
		result.addObject("tutorials", tutorials);
		result.addObject("requestURI", "/tutorial/list.do");

		result = this.configService.configGeneral(result);
		result = this.actorService.isBanned(result);
		return result;
	}

	@RequestMapping(value = "/handyworker/myTutorials", method = RequestMethod.GET)
	public ModelAndView loggedWorkerTutorials() {
		final HandyWorker handyWorker = (HandyWorker) this.actorService.findByUserAccountId(LoginService.getPrincipal().getId());
		final Collection<Tutorial> tutorials = this.tutorialService.findAllFromHandyworker(handyWorker.getId());
		ModelAndView result;
		result = new ModelAndView("tutorial/list");
		result.addObject("tutorials", tutorials);
		result.addObject("requestURI", "/tutorial/myTutorials.do");

		result = this.configService.configGeneral(result);
		result = this.actorService.isBanned(result);
		return result;
	}
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView seeTutorial(@RequestParam("id") final int id) {
		final Tutorial tutorial = this.tutorialService.findOne(id);
		//Result
		ModelAndView result;
		result = new ModelAndView("tutorial/see");
		result.addObject("tutorial", tutorial);

		result = this.configService.configGeneral(result);
		result = this.actorService.isBanned(result);
		return result;
	}

	@RequestMapping(value = "/handyworker/new", method = RequestMethod.GET)
	public ModelAndView newTutorial() {
		final Tutorial tutorial = this.tutorialService.create();
		ModelAndView result;
		result = new ModelAndView("tutorial/edit");
		result.addObject("tutorial", tutorial);

		result = this.configService.configGeneral(result);
		result = this.actorService.isBanned(result);
		return result;
	}

	@RequestMapping(value = "/handyworker/edit", method = RequestMethod.GET)
	public ModelAndView editTutorial(@RequestParam("id") final int id) {
		final Tutorial tutorial = this.tutorialService.findOne(id);
		ModelAndView result;
		result = new ModelAndView("tutorial/edit");
		result.addObject("tutorial", tutorial);

		result = this.configService.configGeneral(result);
		result = this.actorService.isBanned(result);
		return result;
	}

	@RequestMapping(value = "/handyworker/edit", method = RequestMethod.POST)
	public ModelAndView saveTutorial(@Valid final Tutorial tutorial, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println(binding.getFieldErrors());
			result = new ModelAndView("tutorial/edit");
			result.addObject("tutorial", tutorial);
		} else {
			this.tutorialService.save(tutorial);
			result = new ModelAndView("redirect:myTutorials.do");

		}

		result = this.configService.configGeneral(result);
		result = this.actorService.isBanned(result);
		return result;
	}

	@RequestMapping(value = "/handyworker/delete", method = RequestMethod.GET)
	public ModelAndView deleteTutorial(@RequestParam("id") final int id) {
		this.tutorialService.delete(id);
		ModelAndView result;
		result = new ModelAndView("redirect:myTutorials.do");

		result = this.configService.configGeneral(result);
		result = this.actorService.isBanned(result);
		return result;
	}

	@RequestMapping(value = "/pictures/list", method = RequestMethod.GET)
	public ModelAndView tutorialPictures(@RequestParam("id") final int id) {
		final Tutorial tutorial = this.tutorialService.findOne(id);
		ModelAndView result;
		result = new ModelAndView("tutorial/pictures");
		result.addObject("tutorial", tutorial);
		result.addObject("requestURI", "/tutorial/pictures/list.do");

		result = this.configService.configGeneral(result);
		result = this.actorService.isBanned(result);
		return result;
	}

	@RequestMapping(value = "/pictures/handyworker/delete", method = RequestMethod.GET)
	public ModelAndView tutorialDeletePicture(@RequestParam("id") final int id, @RequestParam("picture") final String picture) {
		ModelAndView result = null;
		Tutorial tutorial;
		tutorial = this.tutorialService.findOne(id);
		tutorial.getPhotoURL().remove(picture);
		tutorial = this.tutorialService.save(tutorial);
		result = new ModelAndView("tutorial/pictures");
		result.addObject("tutorial", tutorial);
		result.addObject("requestURI", "/tutorial/pictures/list.do");

		result = this.configService.configGeneral(result);
		result = this.actorService.isBanned(result);
		return result;
	}

	@RequestMapping(value = "/pictures/handyworker/add", method = RequestMethod.GET)
	public ModelAndView tutorialAddPicture(@RequestParam("id") final int id, @RequestParam("picture") final String picture) {
		ModelAndView result = null;
		Tutorial tutorial;
		tutorial = this.tutorialService.findOne(id);
		tutorial.getPhotoURL().add(picture);
		tutorial = this.tutorialService.save(tutorial);
		result = new ModelAndView("tutorial/pictures");
		result.addObject("tutorial", tutorial);
		result.addObject("requestURI", "/tutorial/pictures/list.do");

		result = this.configService.configGeneral(result);
		result = this.actorService.isBanned(result);
		return result;
	}
}
