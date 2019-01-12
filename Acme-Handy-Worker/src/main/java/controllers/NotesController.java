
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ConfigurationService;
import services.NotesService;
import services.ReportService;
import domain.Notes;
import domain.Report;

@Controller
@RequestMapping("/notes")
public class NotesController extends AbstractController {

	@Autowired
	private NotesService			notesService;

	@Autowired
	private ReportService			reportService;

	@Autowired
	private ConfigurationService	configService;

	@Autowired
	private ActorService			actorService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listAll(@RequestParam final int reportId) {
		ModelAndView result;

		final Report report = this.reportService.findOne(reportId);
		result = new ModelAndView("notes/list");
		result.addObject("notes", report.getNotes());
		result.addObject("requestURI", "/notes/list.do");

		result = this.configService.configGeneral(result);
		result = this.actorService.isBanned(result);
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int reportId) {
		ModelAndView result;
		final Report report = this.reportService.findOne(reportId);
		final Notes note = this.notesService.create(report);

		result = new ModelAndView("notes/edit");
		result.addObject("note", note);

		result = this.configService.configGeneral(result);
		result = this.actorService.isBanned(result);
		return result;

	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView displayMessage(@RequestParam final int noteId) {
		ModelAndView result;
		Notes note;
		note = this.notesService.findOne(noteId);
		Assert.notNull(note);
		result = new ModelAndView("notes/display");
		result.addObject("note", note);

		result = this.configService.configGeneral(result);
		result = this.actorService.isBanned(result);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int noteId) {
		ModelAndView result;
		Notes note = this.notesService.findOne(noteId);
		if (noteId != 0)
			note = this.notesService.comment(note);
		result = new ModelAndView("notes/edit");
		result.addObject("note", note);

		result = this.configService.configGeneral(result);
		result = this.actorService.isBanned(result);
		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView saveNote(@Valid final Notes note, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = new ModelAndView("notes/edit");
			result.addObject("note", note);
		} else
			try {
				final Notes save = this.notesService.save(note);
				result = new ModelAndView("redirect:list.do?reportId=" + save.getReport().getId());
			} catch (final Throwable opps) {
				opps.printStackTrace();
				result = new ModelAndView("notes/edit");
				result.addObject("note", note);
				result.addObject("messageCode", "note.commit.error.edit");
			}

		result = this.configService.configGeneral(result);
		result = this.actorService.isBanned(result);
		return result;
	}

}
