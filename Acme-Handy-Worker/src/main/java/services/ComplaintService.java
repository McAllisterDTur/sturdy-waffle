
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ComplaintRepository;
import security.Authority;
import security.LoginService;
import utilities.AuthenticationUtility;
import domain.Complaint;
import domain.Customer;

@Service
@Transactional
public class ComplaintService {

	@Autowired
	private ComplaintRepository	complaintRepository;
	@Autowired
	private ActorService		actorService;


	public Complaint create() {
		return new Complaint();
	}

	//====== CUSTOMER
	/**
	 * Checks customer authority. Saves a complaint. Complaints can't be updated. (Req 35.1)
	 * 
	 * @param complaint
	 * @return the complaint saved in the database
	 */
	public Complaint save(final Complaint complaint) {
		final boolean hasAu = AuthenticationUtility.checkAuthority(Authority.CUSTOMER);
		Assert.isTrue(hasAu);

		// We can't update
		Assert.isTrue(complaint.getId() == 0);

		return this.complaintRepository.save(complaint);
	}
	/**
	 * Checks customer authority (Req 35.1)
	 * 
	 * @return Collection of the complaints related to the logged customer
	 */
	public Collection<Complaint> findFromLoggedCustomer() {
		final boolean hasAu = AuthenticationUtility.checkAuthority(Authority.CUSTOMER);
		Assert.isTrue(hasAu);
		final Customer c = (Customer) this.actorService.findByUserAccountId(LoginService
			.getPrincipal().getId());
		return this.complaintRepository.findFromCustomer(c.getId());
	}

	public int getNumberOfTickers(final String ticker) {
		return this.complaintRepository.getNumberOfTickers(ticker);
	}

}
