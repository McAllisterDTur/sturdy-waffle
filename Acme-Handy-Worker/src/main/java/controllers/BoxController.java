
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
		final ModelAndView result;
		final UserAccount uaccount = LoginService.getPrincipal();
		final Actor actorId = this.actorService.findByUserAccountId(uaccount.getId());
		final Collection<Box> boxes = this.boxService.findByOwner(actorId.getId());
		result = new ModelAndView("box/list");
		result.addObject("boxes", boxes);
		result.addObject("requestURI", "/box/list.do");
		result.addObject("bannerURL", this.configService.findAll().iterator().next().getBannerURL());

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
			result = new ModelAndView("box/list");
			result.addObject("messageCode", "box.commit.error.delete");
		}
		result.addObject("bannerURL", this.configService.findAll().iterator().next().getBannerURL());

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView saveBox(@Valid final Box box, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			System.out.println(binding.getFieldErrors());
			result = new ModelAndView("box/edit");
			result.addObject("box", box);
		} else
			try {
				this.boxService.save(box);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable opps) {
				result = new ModelAndView("box/edit");
				result.addObject("messageCode", "box.commit.error.edit");
			}
		result.addObject("bannerURL", this.configService.findAll().iterator().next().getBannerURL());

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editBox(@RequestParam final int boxId) {
		ModelAndView result;
		Box box;

		box = this.boxService.findOne(boxId);
		Assert.notNull(box);
		result = new ModelAndView("box/edit");
		result.addObject("box", box);
		result.addObject("bannerURL", this.configService.findAll().iterator().next().getBannerURL());

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final UserAccount uaccount = LoginService.getPrincipal();
		final Actor actor = this.actorService.findByUserAccountId(uaccount.getId());
		final Box box = this.boxService.create(actor);

		result = new ModelAndView("box/edit");
		result.addObject("box", box);
		result.addObject("bannerURL", this.configService.findAll().iterator().next().getBannerURL());

		return result;

	}
}
