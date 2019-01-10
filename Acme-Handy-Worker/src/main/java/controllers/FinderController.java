
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CategoryService;
import services.ConfigurationService;
import services.FinderService;
import domain.Category;
import domain.Finder;

@Controller
@RequestMapping("/finder")
public class FinderController extends AbstractController {

	@Autowired
	private FinderService			finderService;

	@Autowired
	private ConfigurationService	configService;

	@Autowired
	private CategoryService			categoryService;


	@RequestMapping(value = "/handyworker/finder", method = RequestMethod.POST)
	public ModelAndView saveBox(@Valid final Finder finder, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			System.out.println("Llega al binding" + binding.toString());
			result = new ModelAndView("finder/finder");
			result.addObject("finder", finder);
		} else
			try {
				this.finderService.save(finder);
				final Finder finderO = this.finderService.findByLoggedHandyWorker();
				result = new ModelAndView("redirect:finder.do");
				result.addObject("finder", finderO);
			} catch (final Throwable opps) {
				opps.printStackTrace();
				result = new ModelAndView("finder/finder");
				result.addObject("messageCode", "finder.commit.error.edit");
			}
		result.addObject("bannerURL", this.configService.findAll().iterator().next().getBannerURL());

		return result;
	}

	@RequestMapping(value = "/handyworker/finder", method = RequestMethod.GET)
	public ModelAndView editFinder() {
		ModelAndView result;
		Finder finder;
		final Collection<Category> categories = this.categoryService.findAll();
		finder = this.finderService.findByLoggedHandyWorker();
		Assert.notNull(finder);
		result = new ModelAndView("finder/finder");
		result.addObject("finder", finder);
		result.addObject("categories", categories);
		result.addObject("bannerURL", this.configService.findAll().iterator().next().getBannerURL());

		return result;
	}

}
