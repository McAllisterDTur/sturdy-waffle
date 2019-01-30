/*
 * AdministratorController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AdministratorService;
import services.ApplicationService;
import services.ConfigurationService;
import services.CustomerService;
import services.FixUpTaskService;
import services.HandyWorkerService;
import domain.Actor;
import domain.Customer;
import domain.HandyWorker;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	@Autowired
	private FixUpTaskService		taskService;

	@Autowired
	private ApplicationService		applicationService;

	@Autowired
	private CustomerService			customerService;

	@Autowired
	private HandyWorkerService		workerService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private AdministratorService	administratorService;


	// Constructors -----------------------------------------------------------

	public AdministratorController() {
		super();
	}

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView dashboard() {
		ModelAndView result;

		result = this.calcStats("administrator/dashboard");

		result = this.configurationService.configGeneral(result);
		result = this.actorService.isBanned(result);
		return result;
	}

	@RequestMapping(value = "/suspiciousActors", method = RequestMethod.GET)
	public ModelAndView suspiciousActors() {
		ModelAndView result;
		final Collection<Actor> actors = this.actorService.findBySuspicious();
		result = new ModelAndView("administrator/suspiciousActors");
		result.addObject("actors", actors);
		result.addObject("requestURI", "/administrator/suspiciousActors.do");

		result = this.configurationService.configGeneral(result);
		result = this.actorService.isBanned(result);
		return result;
	}
	private ModelAndView calcStats(final String model) {
		ModelAndView res = new ModelAndView(model);

		// 12.5.1
		final List<Object[]> stats1 = this.taskService.avgMinMaxDevFixUpTaskCount();
		//res.addObject("stats", stats1);
		if (stats1.get(0) == null || stats1.get(0).length == 0) {
			res.addObject("avgPerUser", 0.0);
			res.addObject("minPerUser", 0.0);
			res.addObject("maxPerUser", 0.0);
			res.addObject("devPerUser", 0.0);
		} else {
			res.addObject("avgPerUser", stats1.get(0)[0]);
			res.addObject("minPerUser", stats1.get(0)[1]);
			res.addObject("maxPerUser", stats1.get(0)[2]);
			res.addObject("devPerUser", stats1.get(0)[3]);
		}

		// 12.5.4
		final List<Object[]> stats2 = this.applicationService.statictisApplications();
		if (stats2.get(0) == null || stats2.get(0).length == 0) {
			res.addObject("avgAppPrice", 0.0);
			res.addObject("minAppPrice", 0.0);
			res.addObject("maxAppPrice", 0.0);
			res.addObject("devAppPrice", 0.0);
		} else {
			res.addObject("avgAppPrice", stats2.get(0)[0]);
			res.addObject("minAppPrice", stats2.get(0)[1]);
			res.addObject("maxAppPrice", stats2.get(0)[2]);
			res.addObject("devAppPrice", stats2.get(0)[3]);
		}

		// 12.5.3
		final List<Object[]> stats3 = this.taskService.avgMinMaxDevFixUpTaskPrice();
		if (stats3.get(0) == null || stats3.get(0).length == 0) {
			res.addObject("avgTaskPrice", 0.0);
			res.addObject("minTaskPrice", 0.0);
			res.addObject("maxTaskPrice", 0.0);
			res.addObject("devTaskPrice", 0.0);
		} else {
			res.addObject("avgTaskPrice", stats3.get(0)[0]);
			res.addObject("minTaskPrice", stats3.get(0)[1]);
			res.addObject("maxTaskPrice", stats3.get(0)[2]);
			res.addObject("devTaskPrice", stats3.get(0)[3]);
		}

		final List<String> l = new ArrayList<>();
		// 12.5.9
		final List<Customer> stats4 = (List<Customer>) this.customerService.findCustomerMaxAverage();
		if (stats4 == null || stats4.isEmpty())
			res.addObject("customers", l);
		else
			res.addObject("customers", stats4);

		// 12.5.10
		final List<HandyWorker> stats5 = (List<HandyWorker>) this.workerService.findWorkerMoreAverage();
		if (stats5 == null || stats5.isEmpty())
			res.addObject("handyWorkers", l);
		else
			res.addObject("handyWorkers", stats5);

		// 12.5.5
		final Double stat6 = this.applicationService.ratioPedingApplications();
		// 12.5.6
		final Double stat7 = this.applicationService.ratioAcceptedApplications();
		// 12.5.7
		final Double stat8 = this.applicationService.ratioRejectedApplications();
		// 12.5.8
		final Double stat9 = this.applicationService.ratioElapsedApplications();

		if (stat6 == null)
			res.addObject("pendingRatio", 0.0);
		else
			res.addObject("pendingRatio", stat6);

		if (stat7 == null)
			res.addObject("acceptedRatio", 0.0);
		else
			res.addObject("acceptedRatio", stat7);

		if (stat8 == null)
			res.addObject("rejectedRatio", 0.0);
		else
			res.addObject("rejectedRatio", stat8);

		if (stat9 == null)
			res.addObject("elapsedRatio", 0.0);
		else
			res.addObject("elapsedRatio", stat9);

		// 12.5.2
		final List<Object[]> stats10 = this.applicationService.findApplicationsPerTask();

		if (stats10.get(0) == null || stats10.get(0).length == 0) {
			res.addObject("avgAppNum", 0.0);
			res.addObject("minAppNum", 0.0);
			res.addObject("maxAppNum", 0.0);
			res.addObject("devAppNum", 0.0);
		} else {
			res.addObject("avgAppNum", stats10.get(0)[0]);
			res.addObject("minAppNum", stats10.get(0)[1]);
			res.addObject("maxAppNum", stats10.get(0)[2]);
			res.addObject("devAppNum", stats10.get(0)[3]);
		}

		// 38.5.1
		final List<Object[]> complaintsPerFixUpTaskStatistics = this.administratorService.complaintsPerFixUpTaskStatistics();
		if (complaintsPerFixUpTaskStatistics.get(0) == null || complaintsPerFixUpTaskStatistics.get(0).length == 0) {
			res.addObject("maxComplaintsPerTask", 0.0);
			res.addObject("minComplaintsPerTask", 0.0);
			res.addObject("avgComplaintsPerTask", 0.0);
			res.addObject("devComplaintsPerTask", 0.0);
		} else {
			res.addObject("maxComplaintsPerTask", complaintsPerFixUpTaskStatistics.get(0)[0]);
			res.addObject("minComplaintsPerTask", complaintsPerFixUpTaskStatistics.get(0)[1]);
			res.addObject("avgComplaintsPerTask", complaintsPerFixUpTaskStatistics.get(0)[2]);
			res.addObject("devComplaintsPerTask", complaintsPerFixUpTaskStatistics.get(0)[3]);
		}

		// 38.5.2
		final List<Object[]> notesPerRefereeReportStatistics = this.administratorService.notesPerRefereeReportStatistics();
		if (notesPerRefereeReportStatistics.get(0) == null || notesPerRefereeReportStatistics.get(0).length == 0) {
			res.addObject("maxNotesPerReferee", 0.0);
			res.addObject("minNotesPerReferee", 0.0);
			res.addObject("avgNotesPerReferee", 0.0);
			res.addObject("devNotesPerReferee", 0.0);
		} else {
			res.addObject("maxNotesPerReferee", notesPerRefereeReportStatistics.get(0)[0]);
			res.addObject("minNotesPerReferee", notesPerRefereeReportStatistics.get(0)[1]);
			res.addObject("avgNotesPerReferee", notesPerRefereeReportStatistics.get(0)[2]);
			res.addObject("devNotesPerReferee", notesPerRefereeReportStatistics.get(0)[3]);
		}

		// 38.5.3
		final Double fixUpTaskWithComplaintRatio = this.administratorService.fixUpTaskWithComplaintRatio();
		if (fixUpTaskWithComplaintRatio == null)
			res.addObject("taskComplaintRatio", 0.0);
		else
			res.addObject("taskComplaintRatio", fixUpTaskWithComplaintRatio);

		// 38.5.4
		final List<Customer> topThreeCustomersByComplaints = this.administratorService.topThreeCustomersByComplaints();
		if (topThreeCustomersByComplaints == null)
			res.addObject("topThreeCustomerComplaint", l);
		else
			res.addObject("topThreeCustomerComplaint", topThreeCustomersByComplaints);

		// 38.5.5
		final List<HandyWorker> topThreeHandyByComplaints = this.administratorService.topThreeHandyByComplaints();
		if (topThreeHandyByComplaints == null)
			res.addObject("topThreeHandyComplaint", l);
		else
			res.addObject("topThreeHandyComplaint", topThreeHandyByComplaints);

		res = this.configurationService.configGeneral(res);
		res = this.actorService.isBanned(res);
		return res;

	}
}
