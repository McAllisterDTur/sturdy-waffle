
package controllers;

import java.util.Collection;

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
import security.UserAccount;
import services.ActorService;
import services.BoxService;
import services.ConfigurationService;
import domain.Actor;
import domain.Box;

@Controller
@RequestMapping("/box")
public class BoxController extends AbstractController {

	@Autowired
	private BoxService				boxService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private ConfigurationService	configService;


	public BoxController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listAll() {
		ModelAndView result;
		final UserAccount uaccount = LoginService.getPrincipal();
		try {
			result = new ModelAndView("box/list");
			final Actor actorId = this.actorService.findByUserAccountId(uaccount.getId());
			final Collection<Box> boxes = this.boxService.findByOwner(actorId.getId());
			result.addObject("requestURI", "/box/list.do");
			result.addObject("boxes", boxes);
		} catch (final Throwable opps) {
			result = new ModelAndView("redirect:welcome/index.do");
			result.addObject("messageCode", "box.commit.error");
		}
		result = this.configService.configGeneral(result);
		result = this.actorService.isBanned(result);

		return result;
	}
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView deleteBox(@RequestParam final int boxId) {
		ModelAndView result;
		try {
			final Box box = this.boxService.findOne(boxId);
			this.boxService.delete(box);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable opps) {
			result = new ModelAndView("redirect:list.do");
			result.addObject("messageCode", "box.commit.error");
		}
		result = this.configService.configGeneral(result);
		result = this.actorService.isBanned(result);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView saveBox(@Valid final Box box, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = new ModelAndView("box/edit");
			result.addObject("box", box);
		} else
			try {
				this.boxService.save(box);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable opps) {
				result = new ModelAndView("box/edit");
				result.addObject("box", box);
				result.addObject("messageCode", "box.commit.error");
			}
		result = this.configService.configGeneral(result);
		result = this.actorService.isBanned(result);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editBox(@RequestParam final int boxId) {
		ModelAndView result;
		Box box;
		try {
			box = this.boxService.findOne(boxId);
			result = new ModelAndView("box/edit");
			Assert.notNull(box);
			result.addObject("box", box);
		} catch (final Throwable opps) {
			result = new ModelAndView("redirect:list.do");
			result.addObject("messageCode", "box.commit.error");
		}
		result = this.configService.configGeneral(result);
		result = this.actorService.isBanned(result);
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final UserAccount uaccount = LoginService.getPrincipal();
		try {
			final Actor actor = this.actorService.findByUserAccountId(uaccount.getId());
			result = new ModelAndView("box/edit");
			final Box box = this.boxService.create(actor);
			result.addObject("box", box);
		} catch (final Throwable opps) {
			result = new ModelAndView("redirect:list.do");
			result.addObject("messageCode", "box.commit.error");
		}

		result = this.configService.configGeneral(result);
		result = this.actorService.isBanned(result);

		return result;

	}
}
