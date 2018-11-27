
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SponsorshipRepository;
import repositories.TutorialRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import utilities.AuthenticationUtility;
import domain.Sponsorship;

@Service
@Transactional
public class SponsorshipService {

	@Autowired
	private SponsorshipRepository	sponsorshipRepository;

	@Autowired
	private TutorialRepository		tutorialRepository;


	/**
	 * Sponsor creates a empty sponsorship
	 * 
	 * @return section
	 */
	public Sponsorship create() {
		Assert.isTrue(AuthenticationUtility.checkAuthority(Authority.SPONSOR));
		final Sponsorship sponsorship = new Sponsorship();
		return sponsorship;
	}

	/**
	 * Gets all Sponsorship of a tutorial from DB
	 * 
	 * @param id
	 * @return Collection<Sponsorship>
	 */
	public Collection<Sponsorship> findAllSponsorshipFromTutorial(final int id) {
		final Collection<Sponsorship> result = this.sponsorshipRepository.findAllSponsorchipFromTutorial(id);
		return result;
	}

	/**
	 * Gets a Sponsorship from DB
	 * with an especific id
	 * 
	 * @param id
	 * @return Sponsorship
	 */
	public Sponsorship findOne(final int id) {
		final Sponsorship result = this.sponsorshipRepository.findOne(id);
		return result;
	}

	/**
	 * Sponsor saves a Sponsorship to DB
	 * 
	 * @param Sponsorship
	 * @return Sponsorship
	 */
	public Sponsorship save(final Sponsorship sponsorship) {
		Assert.notNull(sponsorship);
		Assert.isTrue(AuthenticationUtility.checkAuthority(Authority.SPONSOR));
		final UserAccount ua = LoginService.getPrincipal();
		Assert.isTrue(sponsorship.getSponsor().getAccount().equals(ua));
		Sponsorship result = null;
		//TODO suponemos que las Sponsorship son creadas con una llamada en el controller
		// y se insertan en el tutorial dentro del controller
		if (sponsorship.getId() != 0) {
			final Sponsorship ac = this.findOne(sponsorship.getId());
			Assert.isTrue(ac.getSponsor().getAccount().equals(sponsorship.getSponsor().getAccount()));
			result = this.sponsorshipRepository.save(sponsorship);

		} else
			result = this.sponsorshipRepository.save(sponsorship);
		return result;
	}

	/**
	 * Sponsor deletes a Sponsorship from DB
	 * 
	 * @param Sponsorship
	 */
	public void delete(final Sponsorship sponsorship) {
		Assert.isTrue(AuthenticationUtility.checkAuthority(Authority.SPONSOR));
		final UserAccount ua = LoginService.getPrincipal();
		final Sponsorship ac = this.findOne(sponsorship.getId());
		Assert.isTrue(ac.getSponsor().getAccount().equals(sponsorship.getSponsor().getAccount()));
		Assert.isTrue(sponsorship.getSponsor().getAccount().equals(ua));
		this.sponsorshipRepository.delete(sponsorship);
	}

}
