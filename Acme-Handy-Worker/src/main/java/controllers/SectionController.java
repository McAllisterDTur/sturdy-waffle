
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ConfigurationService;
import services.SectionService;
import services.TutorialService;
import domain.Section;
import domain.Tutorial;

@Controller
@RequestMapping("/section")
public class SectionController extends AbstractController {

	@Autowired
	private SectionService			sectionService;
	@Autowired
	private TutorialService			tutorialService;
	@Autowired
	private ActorService			aService;
	@Autowired
	private ConfigurationService	configService;


	public SectionController() {
		super();
	}

	@RequestMapping(value = "/handyworker/new", method = RequestMethod.GET)
	public ModelAndView newSectionGET(@RequestParam("tutorialId") final int tutorialId) {
		final Tutorial t = this.tutorialService.findOne(tutorialId);
		final Section section = this.sectionService.create(t);
		ModelAndView result;
		result = new ModelAndView("section/edit");
		result.addObject("section", section);

		result = this.configService.configGeneral(result);
		result = this.aService.isBanned(result);
		return result;
	}
	@RequestMapping(value = "/handyworker/edit", method = RequestMethod.GET)
	public ModelAndView editSectionGET(@RequestParam("id") final int id) {
		final Section section = this.sectionService.findOne(id);
		ModelAndView result;
		result = new ModelAndView("section/edit");
		result.addObject("section", section);
		result = this.configService.configGeneral(result);
		result = this.aService.isBanned(result);
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
			final Tutorial tutorial = this.tutorialService.findOne(section.getTutorial().getId());
			result.addObject("tutorial", tutorial);
		}
		result = this.configService.configGeneral(result);
		result = this.aService.isBanned(result);
		return result;
	}

	@RequestMapping(value = "/handyworker/delete", method = RequestMethod.GET)
	public ModelAndView deleteSection(@RequestParam("id") final int id) {
		final Section section = this.sectionService.findOne(id);
		ModelAndView result;
		try {
			this.sectionService.delete(section);
			final Tutorial tutorial = this.tutorialService.findOne(section.getTutorial().getId());
			result = new ModelAndView("tutorial/see");
			result.addObject("tutorial", tutorial);
		} catch (final Throwable oops) {
			result = new ModelAndView("section/edit");
			result.addObject("section", section);
		}
		result = this.configService.configGeneral(result);
		result = this.aService.isBanned(result);
		return result;
	}

	@RequestMapping(value = "/pictures/list", method = RequestMethod.GET)
	public ModelAndView tutorialPictures(@RequestParam("id") final int id) {
		final Section section = this.sectionService.findOne(id);
		ModelAndView result;
		result = new ModelAndView("section/pictures");
		result.addObject("section", section);
		result.addObject("requestURI", "/section/pictures/list.do");
		result = this.configService.configGeneral(result);
		result = this.aService.isBanned(result);
		return result;
	}

	@RequestMapping(value = "/pictures/handyworker/delete", method = RequestMethod.GET)
	public ModelAndView tutorialDeletePicture(@RequestParam("id") final int id, @RequestParam("picture") final String picture) {
		ModelAndView result = null;
		Section section;
		section = this.sectionService.findOne(id);
		section.getPhotoURL().remove(picture);
		section = this.sectionService.save(section);
		result = new ModelAndView("section/pictures");
		result.addObject("section", section);
		result.addObject("requestURI", "/section/pictures/list.do");
		result = this.configService.configGeneral(result);
		result = this.aService.isBanned(result);
		return result;
	}

	@RequestMapping(value = "/pictures/handyworker/add", method = RequestMethod.GET)
	public ModelAndView tutorialAddPicture(@RequestParam("id") final int id, @RequestParam("picture") final String picture) {
		ModelAndView result = null;
		Section section;
		section = this.sectionService.findOne(id);
		section.getPhotoURL().add(picture);
		section = this.sectionService.save(section);
		result = new ModelAndView("section/pictures");
		result.addObject("section", section);
		result.addObject("requestURI", "/section/pictures/list.do");
		result = this.configService.configGeneral(result);
		result = this.aService.isBanned(result);
		return result;
	}
}
