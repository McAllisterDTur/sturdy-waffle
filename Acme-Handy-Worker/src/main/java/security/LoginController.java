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

		result = this.configurationService.configGeneral(result);
		result = this.actorService.isBanned(result);
		return result;
	}

	// LoginFailure -----------------------------------------------------------

	@RequestMapping("/loginFailure")
	public ModelAndView failure() {
		ModelAndView result;

		result = new ModelAndView("redirect:login.do?showError=true");

		result = this.configurationService.configGeneral(result);
		result = this.actorService.isBanned(result);
		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView registerGET() {
		final Actor a = this.actorService.create();
		ModelAndView result;
		result = new ModelAndView("actor/register");
		result.addObject("actor", a);
		Collection<Authority> authorities = null;
		authorities = this.getDefaultRegisterAuthorities();
		result.addObject("authorities", authorities);
		result.addObject("uri", "security/register.do");

		result = this.configurationService.configGeneral(result);
		result = this.actorService.isBanned(result);
		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView registerPOST(@Valid final Actor actor, final BindingResult binding) {
		ModelAndView result;
		String emailError = "";
		if (actor.getEmail() != null)
			emailError = this.actorService.validateEmail(actor.getEmail());
		if (binding.hasErrors() || !emailError.isEmpty()) {
			System.out.println(binding.getFieldErrors());
			result = new ModelAndView("actor/register");
			result.addObject("uri", "security/register.do");
			actor.getAccount().setPassword("");
			result.addObject("actor", actor);
			Collection<Authority> authorities = null;
			authorities = this.getDefaultRegisterAuthorities();
			result.addObject("authorities", authorities);
			result.addObject("emailError", emailError);
		} else
			try {
				actor.setPhone(this.actorService.checkSetPhoneCC(actor.getPhone()));
				final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
				switch (actor.getAccount().getAuthorities().iterator().next().getAuthority()) {
				case Authority.HANDYWORKER:
					final HandyWorker handy = this.handyService.actorToHandy(actor);

					final String hash = encoder.encodePassword(actor.getAccount().getPassword(), null);
					handy.getAccount().setPassword(hash);
					this.handyService.save(handy);
					break;
				case Authority.CUSTOMER:
					final Customer customer = this.customerService.actorToCustomer(actor);

					final String hash1 = encoder.encodePassword(actor.getAccount().getPassword(), null);
					customer.getAccount().setPassword(hash1);
					this.customerService.save(customer);
					break;
				case Authority.SPONSOR:
					final Sponsor sponsor = this.sponsorService.actorToSponsor(actor);
					final String hash2 = encoder.encodePassword(actor.getAccount().getPassword(), null);
					sponsor.getAccount().setPassword(hash2);
					this.sponsorService.save(sponsor);
					break;
				}
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable opps) {
				result = new ModelAndView("actor/register");
				result.addObject("uri", "security/register.do");
				result.addObject("messageCode", "actor.commit.error");
				Collection<Authority> authorities = null;
				authorities = this.getDefaultRegisterAuthorities();
				result.addObject("authorities", authorities);
				result.addObject("emailError", emailError);
				actor.getAccount().setPassword("");
				result.addObject("actor", actor);
				opps.printStackTrace();
			}

		result = this.configurationService.configGeneral(result);
		result = this.actorService.isBanned(result);
		return result;
	}
	@RequestMapping(value = "/administrator/register", method = RequestMethod.GET)
	public ModelAndView registerAdminGET() {
		final Actor a = this.actorService.create();
		ModelAndView result;
		result = new ModelAndView("actor/register");
		result.addObject("actor", a);
		Collection<Authority> authorities = null;
		authorities = this.getAdminRegisterAuthorities();
		result.addObject("authorities", authorities);
		result.addObject("uri", "security/administrator/register.do");
		result = this.configurationService.configGeneral(result);
		result = this.actorService.isBanned(result);
		return result;
	}

	@RequestMapping(value = "/administrator/register", method = RequestMethod.POST)
	public ModelAndView registerAdminPOST(@Valid final Actor actor, final BindingResult binding) {
		ModelAndView result;
		String emailError = "";
		if (actor.getEmail() != null)
			emailError = this.administratorService.validateEmail(actor.getEmail());
		if (binding.hasErrors() || !emailError.isEmpty()) {

			System.out.println(binding.getFieldErrors());
			result = new ModelAndView("actor/register");
			result.addObject("uri", "security/administrator/register.do");
			result.addObject("actor", actor);
			actor.getAccount().setPassword("");
			Collection<Authority> authorities = null;
			authorities = this.getAdminRegisterAuthorities();
			result.addObject("authorities", authorities);
			result.addObject("emailError", emailError);
		} else
			try {
				actor.setPhone(this.actorService.checkSetPhoneCC(actor.getPhone()));
				final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
				switch (actor.getAccount().getAuthorities().iterator().next().getAuthority()) {
				case Authority.ADMIN:
					final Administrator admin = this.administratorService.actorToAdmin(actor);

					final String hash = encoder.encodePassword(actor.getAccount().getPassword(), null);
					admin.getAccount().setPassword(hash);
					this.administratorService.save(admin);
					break;
				case Authority.REFEREE:
					final Referee referee = this.refereeService.actorToReferee(actor);

					final String hash1 = encoder.encodePassword(actor.getAccount().getPassword(), null);
					referee.getAccount().setPassword(hash1);
					this.refereeService.save(referee);
					break;
				}
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable opps) {
				result = new ModelAndView("actor/register");
				result.addObject("uri", "security/administrator/register.do");
				result.addObject("messageCode", "actor.commit.error");
				Collection<Authority> authorities = null;
				authorities = this.getAdminRegisterAuthorities();
				result.addObject("authorities", authorities);
				result.addObject("emailError", emailError);
				actor.getAccount().setPassword("");
				result.addObject("actor", actor);
			}
		result = this.configurationService.configGeneral(result);
		result = this.actorService.isBanned(result);
		return result;
	}
	public List<Authority> getDefaultRegisterAuthorities() {
		final Authority handyWorker = new Authority();
		handyWorker.setAuthority(Authority.HANDYWORKER);
		final Authority customer = new Authority();
		customer.setAuthority(Authority.CUSTOMER);
		return Arrays.asList(handyWorker, customer);
	}

	public List<Authority> getAdminRegisterAuthorities() {
		final Authority admin = new Authority();
		admin.setAuthority(Authority.ADMIN);
		final Authority referee = new Authority();
		referee.setAuthority(Authority.REFEREE);

		return Arrays.asList(admin, referee);
	}
}
