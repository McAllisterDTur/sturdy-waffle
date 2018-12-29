
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
import services.MessageService;
import services.UserAccountService;
import domain.Actor;
import domain.Box;
import domain.Message;

@Controller
@RequestMapping("/message")
public class MessageController extends AbstractController {

	@Autowired
	private MessageService		messageService;

	@Autowired
	private BoxService			boxService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private UserAccountService	userAccountService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listAll(@RequestParam final int boxId) {
		final ModelAndView result;
		final Box b = this.boxService.findOne(boxId);
		final Collection<Message> messages = this.messageService.findByBox(b);
		result = new ModelAndView("message/list");
		result.addObject("messages", messages);
		result.addObject("requestURI", "/message/list.do");
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView deleteBox(@RequestParam final int messId) {
		ModelAndView result;
		try {
			final Message message = this.messageService.findOne(messId);
			this.messageService.delete(message);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable opps) {
			result = new ModelAndView("box/list");
			result.addObject("messageCode", "box.commit.error");
		}
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView saveMessage(@Valid final Message message, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			System.out.println(binding.getFieldErrors());
			result = new ModelAndView("message/edit");
			result.addObject("messageO", message);
			final Collection<Actor> actores = this.actorService.findAll();
			result.addObject("actors", actores);
		} else
			try {
				final String username = message.getReciever().getAccount().getUsername();
				final UserAccount accountId = this.userAccountService.findByName(username);
				final Actor actor = this.actorService.findByUserAccountId(accountId.getId());
				this.messageService.send(message, actor);
				final Box bout = this.boxService.findByName(actor.getId(), "OUT");
				result = new ModelAndView("redirect:list.do?boxId=" + bout.getId());
			} catch (final Throwable opps) {
				result = new ModelAndView("message/edit");
				result.addObject("messageCode", "message.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editMessage(@RequestParam final int messageId) {
		ModelAndView result;
		Message message;

		message = this.messageService.findOne(messageId);
		Assert.notNull(message);
		result = new ModelAndView("box/edit");
		result.addObject("messageO", message);

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
		return result;
	}
}
