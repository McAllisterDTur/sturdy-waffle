
package controllers;

import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ActorService;
import services.ComplaintService;
import services.FixUpTaskService;
import services.HandyWorkerService;
import services.RefereeService;
import domain.Complaint;

@Controller
@RequestMapping("complaint")
public class ComplaintController {

	@Autowired
	ComplaintService	complaintService;
	@Autowired
	FixUpTaskService	futService;
	@Autowired
	HandyWorkerService	hwService;
	@Autowired
	RefereeService		rService;
	@Autowired
	ActorService		aService;


	//TODO: En el populate, una complaint drafted tiene report
	//TODO: No salen los mensajes en los dialog boxes
	//TODO: Probar como referee
	//TODO: Probar como handy

	@RequestMapping(value = "/customer/finalComplaints", method = RequestMethod.GET)
	public ModelAndView listFinalComplaints() {
		final Collection<Complaint> all = this.complaintService.findFinalFromLoggedCustomer();
		final ModelAndView result = new ModelAndView("complaint/list");
		result.addObject("complaints", all);
		result.addObject("requestURI", "complaint/customer/finalComplaints.do");
		result.addObject("draft", false);
		return result;
	}

	@RequestMapping(value = "/customer/draftedComplaints", method = RequestMethod.GET)
	public ModelAndView listDraftedComplaints() {
		final Collection<Complaint> all = this.complaintService.findDraftedFromLoggedCustomer();
		final ModelAndView result = new ModelAndView("complaint/list");
		result.addObject("complaints", all);
		result.addObject("requestURI", "complaint/customer/draftedComplaints.do");
		result.addObject("draft", true);
		return result;
	}

	@RequestMapping(value = "/referee/unassignedComplaints", method = RequestMethod.GET)
	public ModelAndView listUnassignedComplaints() {
		final Collection<Complaint> all = this.complaintService.findUnassigned();
		final ModelAndView result = new ModelAndView("complaint/list");
		result.addObject("complaints", all);
		result.addObject("requestURI", "complaint/referee/unassignedComplaints.do");
		result.addObject("mine", false);
		return result;
	}

	@RequestMapping(value = "/referee/myAssignedComplaints", method = RequestMethod.GET)
	public ModelAndView listSelfassignedComplaints() {
		final Collection<Complaint> all = this.complaintService.findSelfassigned();
		final ModelAndView result = new ModelAndView("complaint/list");
		result.addObject("complaints", all);
		result.addObject("requestURI", "complaint/referee/myAssignedComplaints.do");
		result.addObject("mine", true);
		return result;
	}

	@RequestMapping(value = "/handyworker/myComplaints", method = RequestMethod.GET)
	public ModelAndView listInvolvedComplaints() {
		final Collection<Complaint> all = this.complaintService.findFromLoggedHandyWorker();
		final ModelAndView result = new ModelAndView("complaint/list");
		result.addObject("complaints", all);
		result.addObject("requestURI", "complaint/handyworker/myComplaints.do");
		return result;
	}

	@RequestMapping(value = "/customer/new", method = RequestMethod.GET)
	public ModelAndView newWarranty() {
		final Complaint c = this.complaintService.create();
		final ModelAndView result = new ModelAndView("complaint/edit");
		result.addObject("complaint", c);
		result.addObject("futs", this.futService.findFromLoggedCustomer());
		final Locale locale = LocaleContextHolder.getLocale();
		result.addObject("lang", locale.getLanguage());
		return result;
	}

	@RequestMapping(value = "/customer/edit", method = RequestMethod.GET)
	public ModelAndView editComplaint(@RequestParam final Integer id) {
		ModelAndView result;
		final Complaint c = this.complaintService.findOne(id);
		if (c == null || c.getIsFinal())
			result = new ModelAndView("redirect:draftedComplaints.do");
		else {
			result = new ModelAndView("complaint/edit");
			result.addObject("complaint", c);
			result.addObject("futs", this.futService.findFromLoggedCustomer());
			final Locale locale = LocaleContextHolder.getLocale();
			result.addObject("lang", locale.getLanguage());
		}
		return result;
	}

	@RequestMapping(value = "/referee/assign", method = RequestMethod.GET)
	public ModelAndView assignComplaint(@RequestParam final Integer id) {
		final ModelAndView result = new ModelAndView("redirect:unassignedComplaints.do");
		final Complaint c = this.complaintService.findOne(id);
		if (!(c == null || !c.getIsFinal() || c.getReferee() != null)) {
			c.setReferee(this.rService.findOne(this.aService.findByUserAccountId(LoginService.getPrincipal().getId()).getId()));
			this.complaintService.save(c);
		}

		return result;
	}
	@RequestMapping(value = "/customer/saveFinal", method = RequestMethod.POST)
	public ModelAndView saveFinalComplaint(final Complaint c, final BindingResult br) {
		ModelAndView result;
		if (br.hasErrors())
			result = new ModelAndView("complaint/edit");
		else
			try {
				c.setIsFinal(true);
				this.complaintService.save(c);
				result = new ModelAndView("redirect:finalComplaints.do");
			} catch (final Throwable oops) {
				oops.printStackTrace();
				result = new ModelAndView("warranty/edit");
				result.addObject("success", false);
			}
		return result;
	}

	@RequestMapping(value = "/customer/saveDrafted", method = RequestMethod.POST)
	public ModelAndView saveDraftedComplaint(final Complaint c, final BindingResult br) {
		ModelAndView result;
		if (br.hasErrors())
			result = new ModelAndView("complaint/edit");
		else
			try {
				c.setIsFinal(false);
				this.complaintService.save(c);
				result = new ModelAndView("redirect:draftedComplaints.do");
			} catch (final Throwable oops) {
				oops.printStackTrace();
				result = new ModelAndView("warranty/edit");
				result.addObject("success", false);
			}
		return result;
	}

	@RequestMapping(value = "/see", method = RequestMethod.GET)
	public ModelAndView seeComplaint(@RequestParam final Integer id) {
		ModelAndView result;
		try {
			final Complaint c = this.complaintService.findOne(id);
			result = new ModelAndView("complaint/see");
			result.addObject("complaint", c);
			result.addObject("author", c.getFixUpTask().getCustomer());
			result.addObject("handy", this.hwService.findHandyWorkerFromFixUpTask(c.getFixUpTask().getId()));
			result.addObject("authorId", c.getFixUpTask().getCustomer().getId());
			result.addObject("handyId", this.hwService.findHandyWorkerFromFixUpTask(c.getFixUpTask().getId()).getId());
			result.addObject("complaintId", c.getId());
			if (c.getReferee() != null)
				result.addObject("refereeId", c.getReferee().getId());
		} catch (final Throwable oops) {
			oops.printStackTrace();
			result = new ModelAndView("redirect:#");
			result.addObject("success", false);
		}
		return result;
	}
}
