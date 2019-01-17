
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

import services.ActorService;
import services.CategoryService;
import services.ConfigurationService;
import services.FinderService;
import services.WarrantyService;
import domain.Category;
import domain.Configuration;
import domain.Finder;
import domain.Warranty;

@Controller
@RequestMapping("/finder")
public class FinderController extends AbstractController {

	@Autowired
	private FinderService			finderService;

	@Autowired
	private ConfigurationService	configService;

	@Autowired
	private CategoryService			categoryService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private WarrantyService			warrantyService;


	@RequestMapping(value = "/finder", method = RequestMethod.POST)
	public ModelAndView saveFinder(@Valid final Finder finder, final BindingResult binding) {
		ModelAndView result;
		final Configuration conf = this.configService.findAll().iterator().next();
		if (binding.hasErrors()) {
			result = new ModelAndView("finder/finder");
			final Collection<Category> categories = this.categoryService.findAll();
			final Collection<Warranty> warranty = this.warrantyService.findAll();
			result.addObject("warranty", warranty);
			result.addObject("categories", categories);
			result.addObject("finder", finder);
		} else
			try {
				result = new ModelAndView("redirect:finder.do");
				final Collection<Category> categories = this.categoryService.findAll();
				final Collection<Warranty> warranty = this.warrantyService.findAll();
				final Finder f = this.finderService.save(finder);
				result.addObject("warranty", warranty);
				result.addObject("categories", categories);
				result.addObject("finder", f);
				System.out.println("finders -> " + f.getFixUpTask());
			} catch (final Throwable opps) {
				opps.printStackTrace();
				result = new ModelAndView("finder/finder");
				result.addObject("finder", finder);
				final Collection<Category> categories = this.categoryService.findAll();
				final Collection<Warranty> warranty = this.warrantyService.findAll();
				result.addObject("warranty", warranty);
				result.addObject("categories", categories);
				result.addObject("messageCode", "finder.commit.error.edit");
			}
		result.addObject("vat", conf.getVat());
		result = this.configService.configGeneral(result);
		result = this.actorService.isBanned(result);
		return result;
	}

	@RequestMapping(value = "/finder", method = RequestMethod.GET)
	public ModelAndView editFinder() {
		ModelAndView result;
		Finder finder;
		final Configuration conf = this.configService.findAll().iterator().next();
		try {
			final Collection<Category> categories = this.categoryService.findAll();
			final Collection<Warranty> warranty = this.warrantyService.findAll();
			finder = this.finderService.findByLoggedHandyWorker();
			Assert.notNull(finder);
			result = new ModelAndView("finder/finder");
			result.addObject("finder", finder);
			result.addObject("warranty", warranty);
			result.addObject("categories", categories);
			result.addObject("requestURI", "/finder/finder.do");

		} catch (final Throwable opps) {
			result = new ModelAndView("welcome/home");
			result.addObject("messageCode", "finder.commit.error.edit");
		}
		result.addObject("vat", conf.getVat());

		result = this.configService.configGeneral(result);
		result = this.actorService.isBanned(result);
		return result;
	}
}
