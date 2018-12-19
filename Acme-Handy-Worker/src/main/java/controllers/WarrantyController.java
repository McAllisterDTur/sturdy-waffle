
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

import services.WarrantyService;
import domain.Warranty;

@Controller
@RequestMapping("warranty")
public class WarrantyController {

	@Autowired
	WarrantyService	wService;


	@RequestMapping(value = "/administrator/list", method = RequestMethod.GET)
	public ModelAndView listAllWarranties() {
		final Collection<Warranty> all = this.wService.findAll();
		final ModelAndView result = new ModelAndView("warranty/list");
		result.addObject("warranties", all);
		return result;

	}

	@RequestMapping(value = "/administrator/new", method = RequestMethod.GET)
	public ModelAndView newWarranty() {
		final Warranty w = this.wService.create();
		final ModelAndView result = new ModelAndView("warranty/edit");
		result.addObject("warranty", w);
		return result;

	}

	@RequestMapping(value = "/administrator/save", method = RequestMethod.POST, params = "saveFinal")
	public ModelAndView saveFinalWarranty(@Valid final Warranty w, final BindingResult br) {
		ModelAndView result;
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

	@RequestMapping(value = "/administrator/edit", method = RequestMethod.GET)
	public ModelAndView editWarranty(@RequestParam final Integer id) {
		ModelAndView result;
		final Warranty w = this.wService.findOne(id);
		if (w == null)
			result = new ModelAndView("redirect:list.do");
		else {
			result = new ModelAndView("warranty/edit");
			result.addObject("warranty", w);
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
