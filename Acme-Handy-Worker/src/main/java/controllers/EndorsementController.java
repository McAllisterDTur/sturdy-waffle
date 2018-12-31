
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ActorService;
import services.EndorsableService;
import services.EndorsementService;
import domain.Actor;
import domain.Endorsement;

@Controller
@RequestMapping("endorsement")
public class EndorsementController {

	@Autowired
	EndorsementService	endoService;
	@Autowired
	EndorsableService	endorsableService;
	@Autowired
	ActorService		aService;


	//TODO: No recoge bien los customers de un handyworker

	@RequestMapping(value = "/handyworker,customer/receivedEndorsements", method = RequestMethod.GET)
	public ModelAndView listReceivedEndorsements() {
		final Integer id = this.aService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
		final Collection<Endorsement> all = this.endoService.findAllReceivedByEndorsable(id);
		final ModelAndView result = new ModelAndView("endorsement/list");
		result.addObject("endorsements", all);
		result.addObject("forMe", true);
		result.addObject("requestURI", "endorsement/handyworker,customer/receivedEndorsements.do");
		return result;
	}

	@RequestMapping(value = "/handyworker,customer/sentEndorsements", method = RequestMethod.GET)
	public ModelAndView listSentEndorsements() {
		final Integer id = this.aService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
		final Collection<Endorsement> all = this.endoService.findAllSentByEndorsable(id);
		final ModelAndView result = new ModelAndView("endorsement/list");
		result.addObject("endorsements", all);
		result.addObject("forMe", false);
		result.addObject("requestURI", "endorsement/handyworker,customer/sentEndorsements.do");
		return result;
	}

	@RequestMapping(value = "/handyworker,customer/new", method = RequestMethod.GET)
	public ModelAndView newEndorsement() {
		final Endorsement e = this.endoService.create();
		final Actor a = this.aService.findByUserAccountId(LoginService.getPrincipal().getId());
		final ModelAndView result = new ModelAndView("endorsement/edit");
		e.setSender(this.endorsableService.findOne(a.getId()));
		result.addObject("endorsement", e);
		result.addObject("users", this.endorsableService.findAllWorkedWith(a.getId()));
		return result;
	}

	@RequestMapping(value = "/handyworker,customer/edit", method = RequestMethod.GET)
	public ModelAndView editWarranty(@RequestParam final Integer id) {
		ModelAndView result;
		final Endorsement e = this.endoService.findOne(id);
		final Integer ida = this.aService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
		if (e == null || !e.getSender().equals(this.endorsableService.findOne(ida)))
			result = new ModelAndView("redirect:receivedEndorsements.do");
		else {
			result = new ModelAndView("endorsement/edit");
			result.addObject("endorsement", e);
			result.addObject("users", this.endorsableService.findAllWorkedWith(ida));
		}
		return result;
	}
	@RequestMapping(value = "/handyworker,customer/save", method = RequestMethod.POST, params = "save")
	public ModelAndView saveFinalWarranty(final Endorsement e, final BindingResult br) {
		ModelAndView result;
		if (br.hasErrors())
			result = new ModelAndView("endorsement/edit");
		else
			try {
				this.endoService.save(e);
				result = new ModelAndView("redirect:sentEndorsements.do");
			} catch (final Throwable oops) {
				oops.printStackTrace();
				result = new ModelAndView("endorsement/edit");
				result.addObject("success", false);
			}
		return result;
	}

	@RequestMapping(value = "/handyworker,customer/see", method = RequestMethod.GET)
	public ModelAndView seeWarranty(@RequestParam final Integer id) {
		ModelAndView result;
		try {
			final Endorsement e = this.endoService.findOne(id);
			final Integer ida = this.aService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
			result = new ModelAndView("endorsement/see");
			result.addObject("endorsement", e);
			result.addObject("senderid", e.getSender().getId());
			result.addObject("receiverid", e.getReciever().getId());
			result.addObject("mine", e.getSender().getId() == ida);
		} catch (final Throwable oops) {
			oops.printStackTrace();
			result = new ModelAndView("redirect:receivedEndorsements.do");
			result.addObject("success", false);
		}
		return result;
	}

	@RequestMapping(value = "/handyworker,customer/delete", method = RequestMethod.GET)
	public ModelAndView deleteEndorsement(@RequestParam final Integer id) {
		ModelAndView result;
		try {
			final Actor a = this.aService.findByUserAccountId(LoginService.getPrincipal().getId());
			final Endorsement e = this.endoService.findOne(id);
			if (a.getId() == e.getSender().getId())
				this.endoService.delete(e);
			result = new ModelAndView("redirect:sentEndorsements.do");
			result.addObject("success", true);
		} catch (final Throwable oops) {
			oops.printStackTrace();
			result = new ModelAndView("redirect:sentEndorsements.do");
			result.addObject("success", false);
		}
		return result;
	}

}
