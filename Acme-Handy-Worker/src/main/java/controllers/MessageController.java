
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
import services.MessageService;
import services.UserAccountService;
import domain.Actor;
import domain.Box;
import domain.Message;

@Controller
@RequestMapping("/message")
public class MessageController extends AbstractController {

	@Autowired
	private MessageService			messageService;

	@Autowired
	private BoxService				boxService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private UserAccountService		userAccountService;
	@Autowired
	private ConfigurationService	configService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listAll(@RequestParam final int boxId) {
		final ModelAndView result;
		final Box b = this.boxService.findOne(boxId);
		final Collection<Message> messages = this.messageService.findByBox(b);
		result = new ModelAndView("message/list");
		result.addObject("messages", messages);
		result.addObject("box", b);
		result.addObject("requestURI", "/message/list.do");
		result.addObject("bannerURL", this.configService.findAll().iterator().next().getBannerURL());
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView deleteBox(@RequestParam final int messageId, @RequestParam final int boxId) {
		ModelAndView result;
		try {
			final Message message = this.messageService.findOne(messageId);
			final Box box = this.boxService.findOne(boxId);
			this.messageService.delete(message, box);
			result = new ModelAndView("redirect:list.do?boxId=" + boxId);

		} catch (final Throwable opps) {
			result = new ModelAndView("redirect:list.do?boxId=" + boxId);
			result.addObject("messageCode", "box.commit.error");
		}
		result.addObject("bannerURL", this.configService.findAll().iterator().next().getBannerURL());

		return result;
	}
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView saveMessage(@Valid final Message messageO, final BindingResult binding) {
		System.out.println("El mensaje es -> " + messageO);
		ModelAndView result;
		if (binding.hasErrors()) {
			System.out.println(binding.getFieldErrors());
			result = new ModelAndView("message/edit");
			result.addObject("messageO", messageO);
			final Collection<Actor> actores = this.actorService.findAll();
			result.addObject("actors", actores);
		} else
			try {
				final String username = messageO.getReciever().getAccount().getUsername();
				final UserAccount accountId = this.userAccountService.findByName(username);
				final Actor actor = this.actorService.findByUserAccountId(accountId.getId());
				this.messageService.send(messageO, actor);
				final UserAccount account = LoginService.getPrincipal();
				final Actor sender = this.actorService.findByUserAccountId(account.getId());
				final Box bout = this.boxService.findByName(sender.getId(), "OUT");
				result = new ModelAndView("redirect:list.do?boxId=" + bout.getId());
			} catch (final Throwable opps) {
				result = new ModelAndView("message/edit");
				result.addObject("messageO", messageO);
				result.addObject("messageCode", "message.commit.error");
			}
		result.addObject("bannerURL", this.configService.findAll().iterator().next().getBannerURL());

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editMessage(@RequestParam final int messageId) {
		ModelAndView result;
		Message message;
		message = this.messageService.findOne(messageId);
		Assert.notNull(message);
		result = new ModelAndView("message/edit");
		result.addObject("messageO", message);
		result.addObject("bannerURL", this.configService.findAll().iterator().next().getBannerURL());

		return result;
	}

	@RequestMapping(value = "/copy", method = RequestMethod.POST)
	public ModelAndView copyMessage(final Message message) {
		ModelAndView result;
		System.out.println(message);
		try {
			this.messageService.copy(message);
			result = new ModelAndView("redirect:/box/list.do");
		} catch (final Throwable opps) {
			result = new ModelAndView("message/copy");
			result.addObject("messageCode", "message.commit.error");
		}
		result.addObject("bannerURL", this.configService.findAll().iterator().next().getBannerURL());

		return result;
	}
	@RequestMapping(value = "/copy", method = RequestMethod.GET)
	public ModelAndView displayCopy(@RequestParam final int messageId) {
		final ModelAndView result;
		Message message;
		message = this.messageService.findOne(messageId);
		Assert.notNull(message);
		final UserAccount account = LoginService.getPrincipal();
		final Actor actor = this.actorService.findByUserAccountId(account.getId());
		final Collection<Box> boxes = this.boxService.findByOwner(actor.getId());
		result = new ModelAndView("message/copy");
		result.addObject("messageO", message);
		result.addObject("boxes", boxes);
		result.addObject("bannerURL", this.configService.findAll().iterator().next().getBannerURL());

		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView displayMessage(@RequestParam final int messageId) {
		final ModelAndView result;
		Message message;
		message = this.messageService.findOne(messageId);
		Assert.notNull(message);
		result = new ModelAndView("message/display");
		result.addObject("messageO", message);
		result.addObject("bannerURL", this.configService.findAll().iterator().next().getBannerURL());

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final UserAccount uaccount = LoginService.getPrincipal();
		final Actor actor = this.actorService.findByUserAccountId(uaccount.getId());
		final Message message = this.messageService.create(actor);
		final Collection<Actor> actores = this.actorService.findAll();
		result = new ModelAndView("message/edit");
		result.addObject("messageO", message);
		result.addObject("actors", actores);
		result.addObject("bannerURL", this.configService.findAll().iterator().next().getBannerURL());

		return result;
	}
}
