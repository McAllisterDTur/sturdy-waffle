
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

	public Complaint save(final Complaint complaint) {
		final boolean hasAu = AuthenticationUtility.checkAuthority(Authority.CUSTOMER);
		Assert.isTrue(hasAu);

		// We can't update
		Assert.isTrue(complaint.getId() == 0);

		return this.complaintRepository.save(complaint);
	}

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
