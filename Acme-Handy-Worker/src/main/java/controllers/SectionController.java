
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.SectionService;
import domain.Section;

@Controller
@RequestMapping("/section")
public class SectionController extends AbstractController {

	@Autowired
	private SectionService	sectionService;


	public SectionController() {
		super();
	}

	@RequestMapping(value = "/handyworker/edit", method = RequestMethod.GET)
	public ModelAndView editSectionGET(@RequestParam("id") final int id) {
		final Section section = this.sectionService.findOne(id);
		ModelAndView result;
		result = new ModelAndView("section/edit");
		result.addObject("section", section);
		return result;
	}

	@RequestMapping(value = "/handyworker/edit", method = RequestMethod.POST)
	public ModelAndView editSectionPOST(@Valid final Section section, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println(binding.getFieldErrors());
			result = new ModelAndView("section/edit");
			result.addObject("section", section);
		} else {
			this.sectionService.save(section);
			result = new ModelAndView("tutorial/see");
			result.addObject("tutorial", section.getTutorial());
		}
		return result;
	}
}
