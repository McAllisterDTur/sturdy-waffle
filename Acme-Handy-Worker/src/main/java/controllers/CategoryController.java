
package controllers;

import java.util.Collection;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CategoryService;
import services.ConfigurationService;
import domain.Category;

@Controller
@RequestMapping("category")
public class CategoryController {

	@Autowired
	CategoryService			cService;
	@Autowired
	ConfigurationService	conService;


	@RequestMapping(value = "/administrator/list", method = RequestMethod.GET)
	public ModelAndView listAllCategories() {
		final Collection<Category> all = this.cService.findAll();
		final ModelAndView result = new ModelAndView("category/list");
		final Category o = this.cService.findByName("CATEGORY");
		all.remove(o);
		result.addObject("categories", all);
		final Locale locale = LocaleContextHolder.getLocale();
		result.addObject("lang", locale.getLanguage());
		result.addObject("bannerURL", this.conService.findAll().iterator().next().getBannerURL());
		return result;
	}

	@RequestMapping(value = "/administrator/new", method = RequestMethod.GET)
	public ModelAndView newCategory() {
		final Category c = this.cService.create();
		final ModelAndView result = new ModelAndView("category/edit");
		result.addObject("category", c);
		result.addObject("categories", this.cService.findAll());
		result.addObject("bannerURL", this.conService.findAll().iterator().next().getBannerURL());
		return result;
	}

	@RequestMapping(value = "/administrator/save", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCategory(@Valid final Category c, final BindingResult br) {
		ModelAndView result;
		if (br.hasErrors()) {
			result = new ModelAndView("category/edit");
			result.addObject("success", false);
			result.addObject("categories", this.cService.findAll());
		} else
			try {
				this.cService.save(c);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				oops.printStackTrace();
				result = new ModelAndView("category/edit");
				result.addObject("success", false);
				System.out.println(oops.getMessage());
				if (oops.getMessage().equals("This cannot be null"))
					result.addObject("father", true);
				result.addObject("categories", this.cService.findAll());
			}
		result.addObject("bannerURL", this.conService.findAll().iterator().next().getBannerURL());
		return result;
	}

	@RequestMapping(value = "/administrator/edit", method = RequestMethod.GET)
	public ModelAndView editCategory(@RequestParam final Integer id) {
		ModelAndView result;
		final Category c = this.cService.findOne(id);
		if (c == null)
			result = new ModelAndView("redirect:list.do");
		else {
			result = new ModelAndView("category/edit");
			result.addObject("category", c);
			result.addObject("categories", this.cService.findAll());
		}
		result.addObject("bannerURL", this.conService.findAll().iterator().next().getBannerURL());
		return result;
	}

	@RequestMapping(value = "/administrator/delete", method = RequestMethod.GET)
	public ModelAndView deleteWarranty(@RequestParam final Integer id) {
		ModelAndView result;
		try {
			this.cService.delete(id);
			result = new ModelAndView("redirect:list.do");
			result.addObject("success", true);
		} catch (final Throwable oops) {
			oops.printStackTrace();
			result = new ModelAndView("redirect:list.do");
			result.addObject("success", false);
		}
		result.addObject("bannerURL", this.conService.findAll().iterator().next().getBannerURL());
		return result;
	}

	@RequestMapping(value = "/administrator/see", method = RequestMethod.GET)
	public ModelAndView seeWarranty(@RequestParam final Integer id) {
		ModelAndView result;
		try {
			final Category c = this.cService.findOne(id);
			result = new ModelAndView("category/see");
			result.addObject("category", c);
		} catch (final Throwable oops) {
			oops.printStackTrace();
			result = new ModelAndView("redirect:list.do");
			result.addObject("success", false);
		}
		result.addObject("bannerURL", this.conService.findAll().iterator().next().getBannerURL());
		return result;
	}

}
