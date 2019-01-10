/*
 * LoginController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package security;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AdministratorService;
import services.ConfigurationService;
import services.CustomerService;
import services.HandyWorkerService;
import services.RefereeService;
import services.SponsorService;
import controllers.AbstractController;
import domain.Actor;
import domain.Administrator;
import domain.Configuration;
import domain.Customer;
import domain.HandyWorker;
import domain.Referee;
import domain.Sponsor;

@Controller
@RequestMapping("/security")
public class LoginController extends AbstractController {

	// Supporting services ----------------------------------------------------

	@Autowired
	LoginService					service;
	@Autowired
	private ActorService			actorService;
	@Autowired
	private HandyWorkerService		handyService;
	@Autowired
	private CustomerService			customerService;
	@Autowired
	private SponsorService			sponsorService;
	@Autowired
	private AdministratorService	administratorService;
	@Autowired
	private RefereeService			refereeService;
	@Autowired
	private ConfigurationService	configurationService;


	// Constructors -----------------------------------------------------------

	public LoginController() {
		super();
	}

	// Login ------------------------------------------------------------------

	@RequestMapping("/login")
	public ModelAndView login(@Valid final Credentials credentials, final BindingResult bindingResult, @RequestParam(required = false) final boolean showError) {
		Assert.notNull(credentials);
		Assert.notNull(bindingResult);

		ModelAndView result;

		result = new ModelAndView("security/login");
		result.addObject("credentials", credentials);
		result.addObject("showError", showError);
		final Configuration configuration = this.configurationService.findAll().iterator().next();
		result.addObject("configuration", configuration);
		System.out.println("THIS IS AMERICA");
		return result;
	}

	// LoginFailure -----------------------------------------------------------

	@RequestMapping("/loginFailure")
	public ModelAndView failure() {
		ModelAndView result;

		result = new ModelAndView("redirect:login.do?showError=true");
		final Configuration configuration = this.configurationService.findAll().iterator().next();
		result.addObject("configuration", configuration);
		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView registerGET() {
		final Actor a = this.actorService.create();
		Collection<Authority> authorities = null;
		authorities = this.getDefaultRegisterAuthorities();
		ModelAndView result;
		result = new ModelAndView("actor/register");
		result.addObject("actor", a);
		result.addObject("authorities", authorities);
		final Configuration configuration = this.configurationService.findAll().iterator().next();
		result.addObject("configuration", configuration);
		result.addObject("uri", "security/register.do");
		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView registerPOST(@Valid final Actor actor, final BindingResult binding) {
		ModelAndView result;
		Collection<Authority> authorities = null;
		authorities = this.getDefaultRegisterAuthorities();
		final String validEmail = this.actorService.validateEmail(actor.getEmail());
		if (binding.hasErrors() || !validEmail.isEmpty()) {
			System.out.println(binding.getFieldErrors());
			result = new ModelAndView("actor/register");
			result.addObject("uri", "security/register.do");
			result.addObject("actor", actor);
			result.addObject("authorities", authorities);
			result.addObject("emailError", validEmail);
		} else
			try {
				final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
				actor.setPhone(this.actorService.checkSetPhoneCC(actor.getPhone()));
				switch (actor.getAccount().getAuthorities().iterator().next().getAuthority()) {
				case Authority.HANDYWORKER:
					HandyWorker handy = this.handyService.actorToHandy(actor);

					final String hash = encoder.encodePassword(actor.getAccount().getPassword(), null);
					handy = this.handyService.save(handy);
					handy.getAccount().setPassword(hash);
					handy = this.handyService.save(handy);
					break;
				case Authority.CUSTOMER:
					Customer customer = this.customerService.actorToCustomer(actor);

					final String hash1 = encoder.encodePassword(actor.getAccount().getPassword(), null);
					customer = this.customerService.save(customer);
					customer.getAccount().setPassword(hash1);
					customer = this.customerService.save(customer);
					break;
				case Authority.SPONSOR:
					Sponsor sponsor = this.sponsorService.actorToSponsor(actor);
					final String hash2 = encoder.encodePassword(actor.getAccount().getPassword(), null);
					sponsor = this.sponsorService.save(sponsor);
					sponsor.getAccount().setPassword(hash2);
					sponsor = this.sponsorService.save(sponsor);
					break;
				}
				result = new ModelAndView("redirect:/security/login.do");
			} catch (final Throwable opps) {
				result = new ModelAndView("actor/register");
				result.addObject("uri", "security/register.do");
				result.addObject("messageCode", "actor.commit.error");
				result.addObject("actor", actor);
				result.addObject("authorities", authorities);
				opps.printStackTrace();
			}
		final Configuration configuration = this.configurationService.findAll().iterator().next();
		result.addObject("configuration", configuration);
		return result;
	}

	@RequestMapping(value = "/administrator/register", method = RequestMethod.GET)
	public ModelAndView registerAdminGET() {
		final Actor a = this.actorService.create();
		Collection<Authority> authorities = null;
		authorities = this.getAdminRegisterAuthorities();
		ModelAndView result;
		result = new ModelAndView("actor/register");
		result.addObject("actor", a);
		result.addObject("authorities", authorities);
		result.addObject("uri", "security/administrator/register.do");
		final Configuration configuration = this.configurationService.findAll().iterator().next();
		result.addObject("configuration", configuration);
		return result;
	}

	@RequestMapping(value = "/administrator/register", method = RequestMethod.POST)
	public ModelAndView registerAdminPOST(@Valid final Actor actor, final BindingResult binding) {
		ModelAndView result;
		Collection<Authority> authorities = null;
		authorities = this.getAdminRegisterAuthorities();

		String validEmail = "";
		final Collection<Authority> auths = actor.getAccount().getAuthorities();
		if (!auths.isEmpty() && (auths.iterator().next().getAuthority().equals("ADMIN")))
			validEmail = this.administratorService.validateEmail(actor.getEmail());
		System.out.println("MAJAAAAALAAAAA ACMA" + validEmail);
		if (binding.hasErrors() || !validEmail.isEmpty()) {
			System.out.println(binding.getFieldErrors());
			result = new ModelAndView("actor/register");
			result.addObject("uri", "security/administrator/register.do");
			result.addObject("actor", actor);
			result.addObject("authorities", authorities);
			result.addObject("emailError", validEmail);
		} else
			try {
				final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
				actor.setPhone(this.actorService.checkSetPhoneCC(actor.getPhone()));
				switch (actor.getAccount().getAuthorities().iterator().next().getAuthority()) {
				case Authority.ADMIN:
					final Administrator admin = this.administratorService.actorToAdmin(actor);

					final String hash = encoder.encodePassword(actor.getAccount().getPassword(), null);
					this.administratorService.save(admin);
					admin.getAccount().setPassword(hash);
					this.administratorService.save(admin);
					break;
				case Authority.REFEREE:
					final Referee referee = this.refereeService.actorToReferee(actor);

					final String hash1 = encoder.encodePassword(actor.getAccount().getPassword(), null);
					this.refereeService.save(referee);
					referee.getAccount().setPassword(hash1);
					this.refereeService.save(referee);
					break;
				}
				result = new ModelAndView("redirect:");
			} catch (final Throwable opps) {
				result = new ModelAndView("actor/register");
				result.addObject("uri", "security/administrator/register.do");
				result.addObject("messageCode", "actor.commit.error");
				result.addObject("actor", actor);
				result.addObject("authorities", authorities);
				System.out.println(opps.getMessage());
			}
		final Configuration configuration = this.configurationService.findAll().iterator().next();
		result.addObject("configuration", configuration);
		return result;
	}

	private List<Authority> getDefaultRegisterAuthorities() {
		final Authority handyWorker = new Authority();
		handyWorker.setAuthority(Authority.HANDYWORKER);
		final Authority customer = new Authority();
		customer.setAuthority(Authority.CUSTOMER);
		final Authority sponsor = new Authority();
		sponsor.setAuthority(Authority.SPONSOR);

		return Arrays.asList(handyWorker, customer, sponsor);
	}

	private List<Authority> getAdminRegisterAuthorities() {
		final Authority admin = new Authority();
		admin.setAuthority(Authority.ADMIN);
		final Authority referee = new Authority();
		referee.setAuthority(Authority.REFEREE);

		return Arrays.asList(admin, referee);
	}
}
