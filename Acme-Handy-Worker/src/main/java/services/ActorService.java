
package services;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.servlet.ModelAndView;

import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import utilities.AuthenticationUtility;
import domain.Actor;
import domain.Administrator;
import domain.Customer;
import domain.HandyWorker;
import domain.Referee;
import domain.Sponsor;

@Service
@Transactional
public class ActorService {

	@Autowired
	private ActorRepository			actorRepository;
	@Autowired
	HandyWorkerService				hwService;
	@Autowired
	CustomerService					cService;
	@Autowired
	RefereeService					rService;
	@Autowired
	SponsorService					sService;
	@Autowired
	AdministratorService			adminService;
	@Autowired
	private UserAccountService		accountService;
	@Autowired
	private BoxService				boxService;
	@Autowired
	private ConfigurationService	configurationService;


	/**
	 * Creates a new empty actor
	 * 
	 * @return actor
	 */
	public Actor create() {
		final Actor a = new Actor();
		a.setBanned(false);
		return a;
	}

	/**
	 * Saves a new actor in the DB or update it
	 * If an admin is updating other actor, only can ban
	 * 
	 * @param actor
	 * @return actor
	 */
	public Actor save(final Actor actor) {
		Assert.notNull(actor);
		Actor result = null;
		if (actor.getId() != 0) {
			final UserAccount ua = LoginService.getPrincipal();
			final Actor ac = this.actorRepository.findOne(actor.getId());
			if (AuthenticationUtility.checkAuthority(Authority.ADMIN)) {
				if (ac.getAccount().equals(actor.getAccount()) && actor.getAccount().equals(ua))
					result = this.actorRepository.save(actor);
				else {
					ac.setBanned(actor.getBanned());
					result = this.actorRepository.save(ac);
				}
			} else {
				Assert.isTrue(ac.getAccount().equals(actor.getAccount()));
				Assert.isTrue(actor.getAccount().equals(ua));
				actor.setBanned(false);
				result = this.actorRepository.save(actor);
			}
		} else {
			final UserAccount account = actor.getAccount();
			final UserAccount savedAccount = this.accountService.save(account);
			actor.setAccount(savedAccount);
			result = this.actorRepository.save(actor);
			this.boxService.initializeDefaultBoxes(result);
		}
		return result;
	}

	/**
	 * Get all actors from db
	 * 
	 * @return actors
	 */
	public Collection<Actor> findAll() {
		final Collection<Actor> result = this.actorRepository.findAll();
		return result;
	}

	/**
	 * Find an actor by id in the db
	 * 
	 * @param actorId
	 * @return actor
	 */
	public Actor findOne(final int actorId) {
		final Actor result = this.actorRepository.findOne(actorId);
		return result;
	}

	/**
	 * Finds an actor by his/her user account
	 * 
	 * @param userAccount
	 * @return an actor
	 */
	public Actor findByUserAccountId(final int accountId) {

		return this.actorRepository.findByUserAccountId(accountId);
	}

	public Collection<Actor> findBySuspicious() {
		return this.actorRepository.findSuspiciousActors();
	}

	//B
	public void ban(final Actor actor) {
		System.out.println("Vamo a banea");
		Assert.isTrue(AuthenticationUtility.checkAuthority(Authority.ADMIN));
		final Integer id = actor.getId();
		final String role = actor.getAccount().getAuthorities().iterator().next().toString();
		switch (role) {
		case "HANDYWORKER":
			final HandyWorker hw = this.hwService.findOne(id);
			hw.setBanned(true);
			this.hwService.save(hw);
			break;
		case "CUSTOMER":
			final Customer c = this.cService.findOne(id);
			c.setBanned(true);
			this.cService.save(c);
			break;
		case "ADMIN":
			final Administrator admin = this.adminService.findOne(id);
			admin.setBanned(true);
			this.adminService.save(admin);
			break;
		case "SPONSOR":
			final Sponsor s = this.sService.findOne(id);
			s.setBanned(true);
			this.sService.save(s);
			break;
		case "REFEREE":
			final Referee r = this.rService.findOne(id);
			r.setBanned(true);
			this.rService.save(r);
			break;
		default:
			throw new NullPointerException();
		}
	}
	//B
	public void unban(final Actor actor) {
		Assert.isTrue(AuthenticationUtility.checkAuthority(Authority.ADMIN));
		final Integer id = actor.getId();
		final String role = actor.getAccount().getAuthorities().iterator().next().toString();
		switch (role) {
		case "HANDYWORKER":
			final HandyWorker hw = this.hwService.findOne(id);
			hw.setBanned(false);
			this.hwService.save(hw);
			break;
		case "CUSTOMER":
			final Customer c = this.cService.findOne(id);
			c.setBanned(false);
			this.cService.save(c);
			break;
		case "ADMIN":
			final Administrator admin = this.adminService.findOne(id);
			admin.setBanned(false);
			this.adminService.save(admin);
			break;
		case "SPONSOR":
			final Sponsor s = this.sService.findOne(id);
			s.setBanned(false);
			this.sService.save(s);
			break;
		case "REFEREE":
			final Referee r = this.rService.findOne(id);
			r.setBanned(false);
			this.rService.save(r);
			System.out.println(r.getBanned());
			break;
		default:
			throw new NullPointerException();
		}
	}

	public ModelAndView isBanned(ModelAndView result) {
		try {
			final UserAccount logged = LoginService.getPrincipal();
			if (logged != null) {
				final Actor a = this.findByUserAccountId(logged.getId());
				if (a.getBanned())
					result = new ModelAndView("security/banned");
			}
		} catch (final Throwable oops) {

		}
		return result;
	}

	public String checkSetPhoneCC(String phoneNumber) {
		final Pattern p = Pattern.compile("([0-9]{4}){1}([0-9]{0,})");
		final Matcher m = p.matcher(phoneNumber);
		final boolean b = m.matches();
		if (b)
			phoneNumber = this.configurationService.findAll().iterator().next().getCountryCode() + " " + phoneNumber;
		return phoneNumber;
	}

	public String validateEmail(final String email) {
		final Pattern pattern = Pattern.compile("(([a-z]|[0-9]){1,}[@]{1}([a-z]|[0-9]){1,}([.]{1}([a-z]|[0-9]){1,}){0,})|((([a-z]|[0-9]){1,}[ ]{1}){1,}<(([a-z]|[0-9]){1,}[@]{1}([a-z]|[0-9]){1,}([.]{1}([a-z]|[0-9]){1,}){0,})>)");
		final Matcher matcher = pattern.matcher(email);
		return matcher.matches() ? "" : "actor.email.error";
	}
}
