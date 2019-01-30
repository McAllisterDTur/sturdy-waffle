
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
		ModelAndView result;
		try {
			final Box b = this.boxService.findOne(boxId);
			final Collection<Message> messages = this.messageService.findByBox(b);
			result = new ModelAndView("message/list");
			result.addObject("messages", messages);
			result.addObject("box", b);
			result.addObject("requestURI", "/message/list.do");
		} catch (final Throwable opps) {
			result = new ModelAndView("redirect:../box/list.do");
			result.addObject("messageCode", "box.commit.error");
		}
		result = this.configService.configGeneral(result);
		result = this.actorService.isBanned(result);
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
			result.addObject("messageCode", "message.commit.error");
		}
		result = this.configService.configGeneral(result);
		result = this.actorService.isBanned(result);

		return result;
	}
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView saveMessage(@Valid final Message messageO, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors() || messageO.getReciever().size() > 1) {
			System.out.println(binding.getAllErrors());
			//result = new ModelAndView("redirect:create.do");
			result = new ModelAndView("message/edit");

			final Collection<Actor> actores = this.actorService.findAll();
			result.addObject("messageO", messageO);
			result.addObject("actors", actores);
			result.addObject("message", "message.sendError");
		} else
			try {
				final String username = messageO.getReciever().iterator().next().getAccount().getUsername();
				final UserAccount accountId = this.userAccountService.findByName(username);
				final Actor actor = this.actorService.findByUserAccountId(accountId.getId());
				this.messageService.send(messageO, actor);
				final UserAccount account = LoginService.getPrincipal();
				final Actor sender = this.actorService.findByUserAccountId(account.getId());
				final Box bout = this.messageService.checkSystemBox(this.boxService.findByName(sender.getId(), "OUT"));
				result = new ModelAndView("redirect:list.do?boxId=" + bout.getId());
			} catch (final Throwable opps) {
				result = new ModelAndView("redirect:create.do");
			}
		result = this.configService.configGeneral(result);
		result = this.actorService.isBanned(result);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editMessage(@RequestParam final int messageId) {
		ModelAndView result;
		Message message;
		try {
			message = this.messageService.findOne(messageId);
			Assert.notNull(message);
			result = new ModelAndView("message/edit");
		} catch (final Throwable opps) {
			result = new ModelAndView("redirect:../box/list.do");
			result.addObject("messageCode", "box.commit.error");
		}
		result = this.configService.configGeneral(result);
		result = this.actorService.isBanned(result);

		return result;
	}

	@RequestMapping(value = "/copy", method = RequestMethod.POST)
	public ModelAndView copyMessage(@Valid final Message messageO, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors() || messageO.getBoxes().size() > 1)
			result = new ModelAndView("redirect:/message/copy.do?messageId=" + messageO.getId());
		else
			try {
				this.messageService.copy(messageO);
				result = new ModelAndView("redirect:/box/list.do");
			} catch (final Throwable opps) {
				result = new ModelAndView("redirect:/message/copy.do?=messageId=" + messageO.getId());
			}
		result = this.configService.configGeneral(result);
		result = this.actorService.isBanned(result);

		return result;
	}
	@RequestMapping(value = "/copy", method = RequestMethod.GET)
	public ModelAndView displayCopy(@RequestParam final int messageId) {
		ModelAndView result;
		Message message;
		try {
			message = this.messageService.findOne(messageId);
			Assert.notNull(message);
			final UserAccount account = LoginService.getPrincipal();
			final Actor actor = this.actorService.findByUserAccountId(account.getId());
			final Collection<Box> boxes = this.boxService.findByOwner(actor.getId());
			result = new ModelAndView("message/copy");
			result.addObject("messageO", message);
			result.addObject("boxes", boxes);
		} catch (final Throwable opps) {
			result = new ModelAndView("redirect:../box/list.do");
			result.addObject("messageCode", "box.commit.error");
		}
		result = this.configService.configGeneral(result);
		result = this.actorService.isBanned(result);

		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView displayMessage(@RequestParam final int messageId) {
		ModelAndView result;
		Message message;
		try {
			message = this.messageService.findOne(messageId);
			Assert.notNull(message);
			result = new ModelAndView("message/display");
			result.addObject("messageO", message);
		} catch (final Throwable opps) {
			result = new ModelAndView("redirect:../box/list.do");
			result.addObject("messageCode", "box.commit.error");
		}
		result = this.configService.configGeneral(result);
		result = this.actorService.isBanned(result);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		try {
			final UserAccount uaccount = LoginService.getPrincipal();
			final Actor actor = this.actorService.findByUserAccountId(uaccount.getId());
			final Message message = this.messageService.create(actor);
			final Collection<Actor> actores = this.actorService.findAll();
			result = new ModelAndView("message/edit");
			result.addObject("messageO", message);
			result.addObject("actors", actores);
		} catch (final Throwable opps) {
			//result = new ModelAndView("redirect:../box/list.do");
			opps.printStackTrace();
			result = new ModelAndView("redirect:../welcome/index.do");
			result.addObject("messageCode", "box.commit.error");
		}
		result = this.configService.configGeneral(result);
		result = this.actorService.isBanned(result);

		return result;
	}

	@RequestMapping(value = "/administrator/broadcast", method = RequestMethod.POST)
	public ModelAndView saveBroadcast(@Valid final Message messageO, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			binding.getAllErrors();
			result = new ModelAndView("redirect:broadcast.do");
		} else
			try {
				this.messageService.broadcastMessage(messageO);
				final UserAccount account = LoginService.getPrincipal();
				final Actor sender = this.actorService.findByUserAccountId(account.getId());
				final Box bout = this.messageService.checkSystemBox(this.boxService.findByName(sender.getId(), "OUT"));
				result = new ModelAndView("redirect:../list.do?boxId=" + bout.getId());
			} catch (final Throwable opps) {
				opps.printStackTrace();
				result = new ModelAndView("redirect:broadcast.do");
			}
		result = this.configService.configGeneral(result);
		result = this.actorService.isBanned(result);

		return result;
	}
	@RequestMapping(value = "/administrator/broadcast", method = RequestMethod.GET)
	public ModelAndView displayBroadcast() {
		ModelAndView result;
		try {
			final UserAccount uaccount = LoginService.getPrincipal();
			final Actor actor = this.actorService.findByUserAccountId(uaccount.getId());
			final Message message = this.messageService.create(actor);
			result = new ModelAndView("message/broadcast");
			result.addObject("messageO", message);
		} catch (final Throwable opps) {
			result = new ModelAndView("redirect:../box/list.do");
			result.addObject("messageCode", "box.commit.error");
		}
		result = this.configService.configGeneral(result);
		result = this.actorService.isBanned(result);
		return result;
	}

}
