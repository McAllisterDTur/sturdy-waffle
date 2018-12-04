
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.EndorsementRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import utilities.AuthenticationUtility;
import domain.Customer;
import domain.Endorsement;
import domain.HandyWorker;

@Service
public class EndorsementService {

	@Autowired
	private EndorsementRepository	endorsementRepository;

	private UserAccount				account;


	public Endorsement create() {
		return new Endorsement();
	}

	public Endorsement save(final Endorsement e) {

		Assert.notNull(e);

		this.account = LoginService.getPrincipal();
		Assert.isTrue((AuthenticationUtility.checkAuthority(Authority.CUSTOMER)) || (AuthenticationUtility.checkAuthority(Authority.HANDYWORKER)));

		if (e.getId() == 0)
			if (AuthenticationUtility.checkAuthority(Authority.CUSTOMER)) {
				final Customer sender = (Customer) e.getSender();
				final HandyWorker reciever = (HandyWorker) e.getReciever();

				final Collection<HandyWorker> res = this.endorsementRepository.getWorkerFromTask(sender.getId());
				Assert.isTrue(res.contains(reciever));

			} else if (AuthenticationUtility.checkAuthority(Authority.HANDYWORKER)) {
				final Customer reciever = (Customer) e.getReciever();
				final HandyWorker sender = (HandyWorker) e.getSender();

				final Collection<Customer> res = this.endorsementRepository.getCustomerFromTask(sender.getId());
				Assert.isTrue(res.contains(reciever));
			}
		return this.endorsementRepository.save(e);
	}
	public Endorsement findOne(final int endorsementId) {
		Assert.isTrue(endorsementId > 0);

		return this.endorsementRepository.findOne(endorsementId);

	}

	public Collection<Endorsement> findAllCustomer(final int customerId) {

		Assert.isTrue(customerId > 0);

		this.account = LoginService.getPrincipal();
		Assert.isTrue(AuthenticationUtility.checkAuthority(Authority.CUSTOMER));

		return this.endorsementRepository.findAllCustomer(customerId);

	}

	public Collection<Endorsement> findAllWorker(final int workerId) {
		Assert.isTrue(workerId > 0);

		this.account = LoginService.getPrincipal();
		Assert.isTrue(AuthenticationUtility.checkAuthority(Authority.HANDYWORKER));

		return this.endorsementRepository.findAllWorker(workerId);
	}

	public void delete(final Endorsement endorsement) {

		Assert.notNull(endorsement);

		this.account = LoginService.getPrincipal();
		Assert.isTrue((this.account.getId() == endorsement.getSender().getAccount().getId()) || (this.account.getId() == endorsement.getReciever().getAccount().getId()));

		this.endorsementRepository.delete(endorsement);

	}

	public Collection<Endorsement> findAllEndorsable(final int endorId) {
		Assert.isTrue(endorId > 0);

		this.account = LoginService.getPrincipal();
		Assert.isTrue(AuthenticationUtility.checkAuthority(Authority.HANDYWORKER) || AuthenticationUtility.checkAuthority(Authority.CUSTOMER));

		return this.endorsementRepository.findAllEndorsable(endorId);
	}
}
