
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

import services.ComplaintService;
import services.FixUpTaskService;
import services.HandyWorkerService;
import domain.Complaint;
import domain.Warranty;

@Controller
@RequestMapping("complaint")
public class ComplaintController {

	@Autowired
	ComplaintService	complaintService;
	@Autowired
	FixUpTaskService	futService;
	@Autowired
	HandyWorkerService	hwService;


	//TODO: ¿Qué pasa con los reports?

	@RequestMapping(value = "/customer/finalComplaints", method = RequestMethod.GET)
	public ModelAndView listFinalComplaints() {
		final Collection<Complaint> all = this.complaintService.findFinalFromLoggedCustomer();
		final ModelAndView result = new ModelAndView("complaint/list");
		result.addObject("complaints", all);
		result.addObject("requestURI", "/customer/finalComplaints.do");
		result.addObject("final", true);
		return result;
	}

	@RequestMapping(value = "/customer/draftedComplaints", method = RequestMethod.GET)
	public ModelAndView listDraftedComplaints() {
		final Collection<Complaint> all = this.complaintService.findDraftedFromLoggedCustomer();
		final ModelAndView result = new ModelAndView("complaint/list");
		result.addObject("complaints", all);
		result.addObject("requestURI", "/customer/draftedComplaints.do");
		result.addObject("final", false);
		return result;
	}

	@RequestMapping(value = "/referee/unassignedComplaints", method = RequestMethod.GET)
	public ModelAndView listUnassignedComplaints() {
		final Collection<Complaint> all = this.complaintService.findUnassigned();
		final ModelAndView result = new ModelAndView("complaint/list");
		result.addObject("complaints", all);
		result.addObject("requestURI", "/referee/unassignedComplaints.do");
		return result;
	}

	@RequestMapping(value = "/referee/myAssignedComplaints", method = RequestMethod.GET)
	public ModelAndView listSelfassignedComplaints() {
		final Collection<Complaint> all = this.complaintService.findSelfassigned();
		final ModelAndView result = new ModelAndView("complaint/list");
		result.addObject("complaints", all);
		result.addObject("requestURI", "/referee/myAssignedComplaints.do");
		return result;
	}

	@RequestMapping(value = "/customer/new", method = RequestMethod.GET)
	public ModelAndView newWarranty() {
		final Complaint c = this.complaintService.create();
		final ModelAndView result = new ModelAndView("complaint/edit");
		result.addObject("complaint", c);
		result.addObject("futs", this.futService.findFromLoggedCustomer());
		return result;
	}

	@RequestMapping(value = "/customer/edit", method = RequestMethod.GET)
	public ModelAndView editWarranty(@RequestParam final Integer id) {
		ModelAndView result;
		final Complaint c = this.complaintService.findOne(id);
		if (c == null || c.getIsFinal())
			result = new ModelAndView("redirect:draftedComplaints.do");
		else {
			result = new ModelAndView("complaint/edit");
			result.addObject("complaint", c);
			result.addObject("futs", this.futService.findFromLoggedCustomer());
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

	//TODO: see more

	@RequestMapping(value = "/administrator/save", method = RequestMethod.POST, params = "saveDraft")
	public ModelAndView saveDraftWarranty(@Valid final Warranty w, final BindingResult br) {
		ModelAndView result;
		w.setDraft(true);
		if (br.hasErrors())
			result = new ModelAndView("warranty/edit");
		else
			try {
				this.wService.save(w);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				oops.printStackTrace();
				result = new ModelAndView("warranty/edit");
				result.addObject("success", false);
			}
		return result;
	}

	@RequestMapping(value = "/administrator/delete", method = RequestMethod.GET)
	public ModelAndView deleteWarranty(@RequestParam final Integer id) {
		ModelAndView result;
		try {
			this.wService.delete(id);
			result = new ModelAndView("redirect:list.do");
			result.addObject("success", true);
		} catch (final Throwable oops) {
			oops.printStackTrace();
			result = new ModelAndView("redirect:list.do");
			result.addObject("success", false);
		}
		return result;
	}

	@RequestMapping(value = "/administrator/see", method = RequestMethod.GET)
	public ModelAndView seeWarranty(@RequestParam final Integer id) {
		ModelAndView result;
		try {
			final Warranty w = this.wService.findOne(id);
			result = new ModelAndView("warranty/see");
			result.addObject("warranty", w);
		} catch (final Throwable oops) {
			oops.printStackTrace();
			result = new ModelAndView("redirect:list.do");
			result.addObject("success", false);
		}
		return result;
	}
}
