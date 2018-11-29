
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
			if (!(sponsor.getPhone().startsWith("+"))) {
				final String cc = this.configurationService.findAll().iterator().next().getCountryCode();
				sponsor.setPhone(cc + " " + sponsor.getPhone());
			}
			res = this.sponsorRepository.save(sponsor);
		} else {
			final UserAccount ua = sponsor.getAccount();
			final Authority auth = new Authority();
			auth.setAuthority(Authority.SPONSOR);
			ua.addAuthority(auth);
			final UserAccount nua = this.useraccountService.save(ua);
			sponsor.setAccount(nua);
			sponsor.setBanned(false);
			sponsor.setIsSuspicious(false);
			if (!(sponsor.getPhone().startsWith("+"))) {
				final String cc = this.configurationService.findAll().iterator().next().getCountryCode();
				sponsor.setPhone(cc + " " + sponsor.getPhone());
			}
			res = this.sponsorRepository.save(sponsor);
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

}
