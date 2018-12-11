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
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import services.ActorService;
import domain.Actor;

@Controller
@RequestMapping("/actor")
public class ActorController extends AbstractController {

	@Autowired
	private ActorService	actorService;


	// Constructors -----------------------------------------------------------

	public ActorController() {
		super();
	}

	// Tutorials ---------------------------------------------------------------		

	@RequestMapping("/register")
	public ModelAndView test1() {
		final Collection<Authority> authorities = Authority.listAuthorities();
		final Actor a = this.actorService.create();
		ModelAndView result;
		result = new ModelAndView("actor/register");
		result.addObject("actor", a);
		result.addObject("authorities", authorities);
		return result;
	}
}
