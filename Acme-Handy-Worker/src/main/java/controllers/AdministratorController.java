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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.CustomerService;
import services.FixUpTaskService;
import services.HandyWorkerService;
import domain.Customer;
import domain.HandyWorker;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	@Autowired
	private FixUpTaskService	taskService;

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private HandyWorkerService	workerService;


	// Constructors -----------------------------------------------------------

	public AdministratorController() {
		super();
	}

	// Action-1 ---------------------------------------------------------------		

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView action1() {
		ModelAndView result;

		result = this.calcStats("administrator/dashboard");

		return result;
	}

	private ModelAndView calcStats(final String model) {
		final ModelAndView res = new ModelAndView(model);

		final List<Object[]> stats1 = this.taskService.avgMinMaxDevFixUpTaskCount();
		res.addObject("stats", stats1);
		res.addObject("avgPerUser", stats1.get(0)[0]);
		res.addObject("minPerUser", stats1.get(0)[1]);
		res.addObject("maxPerUser", stats1.get(0)[2]);
		res.addObject("devPerUser", stats1.get(0)[3]);

		final List<Object[]> stats2 = this.applicationService.statictisApplications();
		res.addObject("avgAppPrice", stats2.get(0)[0]);
		res.addObject("minAppPrice", stats2.get(0)[1]);
		res.addObject("maxAppPrice", stats2.get(0)[2]);
		res.addObject("devAppPrice", stats2.get(0)[3]);

		final List<Object[]> stats3 = this.taskService.avgMinMaxDevFixUpTaskPrice();
		res.addObject("avgTaskPrice", stats3.get(0)[0]);
		res.addObject("minTaskPrice", stats3.get(0)[1]);
		res.addObject("maxTaskPrice", stats3.get(0)[2]);
		res.addObject("devTaskPrice", stats3.get(0)[3]);

		final List<Customer> stats4 = (List<Customer>) this.customerService.findCustomerMaxAverage();
		//TODO: Ordenar por número de applications
		res.addObject("customers", stats4);

		final List<HandyWorker> stats5 = (List<HandyWorker>) this.workerService.findWorkerMoreAverage();
		res.addObject("handyWorkers", stats5);

		final Double stat6 = this.applicationService.ratioPedingApplications();
		final Double stat7 = this.applicationService.ratioAcceptedApplications();
		final Double stat8 = this.applicationService.ratioRejectedApplications();
		final Double stat9 = this.applicationService.ratioElapsedApplications();

		res.addObject("pendingRatio", stat6);
		res.addObject("acceptedRatio", stat7);
		res.addObject("rejectedRatio", stat8);
		res.addObject("elapsedRatio", stat9);

		return res;

	}
}
