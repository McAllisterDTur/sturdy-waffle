
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SponsorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import utilities.AuthenticationUtility;
import domain.Actor;
import domain.Sponsor;

@Service
@Transactional
public class SponsorService {

	@Autowired
	private SponsorRepository		sponsorRepository;

	@Autowired
	private UserAccountService		useraccountService;

	@Autowired
	private ConfigurationService	configurationService;
	@Autowired
	private UserAccountService		userAccountService;
	@Autowired
	private BoxService				boxService;


	/**
	 * Creates a empty Sponsor
	 * 
	 * @return Sponsor
	 */
	public Sponsor create() {
		final Sponsor sponsor = new Sponsor();
		final UserAccount ua = this.useraccountService.create();
		sponsor.setAccount(ua);
		return sponsor;
	}

	/**
	 * Saves a new sponsor or update one by id
	 * 
	 * @param sponsor
	 * @return sponsor
	 */
	public Sponsor save(final Sponsor sponsor) {
		Sponsor res = null;
		if (sponsor.getId() != 0) {
			Assert.isTrue(AuthenticationUtility.checkAuthority(Authority.SPONSOR));
			final Sponsor ac = this.findOne(sponsor.getId());
			final UserAccount ua = LoginService.getPrincipal();
			Assert.isTrue(sponsor.getAccount().equals(ua));
			Assert.isTrue(ac.getAccount().equals(sponsor.getAccount()));
			Assert.isTrue(ac.getBanned().equals(sponsor.getBanned()));
			res = this.sponsorRepository.save(sponsor);
		} else {
			final UserAccount account = sponsor.getAccount();
			final UserAccount savedAccount = this.userAccountService.save(account);
			sponsor.setAccount(savedAccount);
			res = this.sponsorRepository.save(sponsor);
			this.boxService.initializeDefaultBoxes(res);
		}
		return res;
	}

	/**
	 * Gets all Sponsor from DB
	 * 
	 * @return
	 */
	public Collection<Sponsor> findAll() {
		final Collection<Sponsor> res = this.sponsorRepository.findAll();
		return res;
	}

	/**
	 * Gets a sponsor from DB with an especific id
	 * 
	 * @param sponsorId
	 * @return Sponsor
	 */
	public Sponsor findOne(final int sponsorId) {
		final Sponsor res = this.sponsorRepository.findOne(sponsorId);
		return res;
	}

	public Sponsor actorToSponsor(final Actor a) {
		final Sponsor res = new Sponsor();
		res.setAccount(a.getAccount());
		res.setAddress(a.getAddress());
		res.setBanned(a.getBanned());
		res.setEmail(a.getEmail());
		res.setId(a.getId());
		res.setIsSuspicious(a.getIsSuspicious());
		res.setMiddleName(a.getMiddleName());
		res.setName(a.getName());
		res.setPhone(a.getPhone());
		res.setPhotoURL(a.getPhotoURL());
		res.setSurname(a.getSurname());
		res.setVersion(a.getVersion());
		res.setIsSuspicious(false);
		return res;
	}
}
