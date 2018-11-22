
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ApplicationRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Application;

@Service
@Transactional
public class ApplicationService {

	@Autowired
	private ApplicationRepository	applicationRepo;
	private UserAccount				account;


	public Application create() {
		return new Application();
	}

	public Application save(final Application application) {
		Assert.notNull(application);
		Assert.isTrue(application.getOfferedPrice() != 0);

		return this.applicationRepo.save(application);
	}

	public Collection<Application> findAllCustomer(final int customerId) {
		this.account = LoginService.getPrincipal();
		Assert.isTrue(customerId == this.account.getId());

		Assert.isTrue(this.account.getAuthorities().iterator().next().getAuthority().equals(Authority.CUSTOMER));

		return this.applicationRepo.findAllCustomer(customerId);
	}

	public Collection<Application> findAllWorker(final int workerId) {
		this.account = LoginService.getPrincipal();
		Assert.isTrue(workerId == this.account.getId());

		Assert.isTrue(this.account.getAuthorities().iterator().next().getAuthority().equals(Authority.HANDYWORKER));

		return this.applicationRepo.findAllCustomer(workerId);
	}

}
