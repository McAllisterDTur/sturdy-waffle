
package services;

import java.util.ArrayList;
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
import domain.Customer;
import domain.HandyWorker;
import domain.Phase;

@Service
@Transactional
public class ApplicationService {

	@Autowired
	private ApplicationRepository	applicationRepo;
	@Autowired
	private CustomerService			customerService;
	@Autowired
	private HandyWorkerService		workerService;
	private UserAccount				account;


	public Application create() {
		final Application res = new Application();

		res.setCustomerComments(new ArrayList<String>());
		res.setHandyComments(new ArrayList<String>());
		res.setPhases(new ArrayList<Phase>());

		return res;
	}

	public Application save(final Application application) {
		Assert.notNull(application);
		Assert.isTrue(application.getOfferedPrice() != 0);
		//TODO: restricción para aceptarla
		return this.applicationRepo.save(application);
	}

	public Collection<Application> findAllCustomer(final int customerId) {
		this.account = LoginService.getPrincipal();
		final Customer c = this.customerService.findOne(customerId);
		Assert.isTrue(this.account.getId() == c.getAccount().getId());

		//Al comprobar el id, como no pueden exixtir dos usuarios con el mismo id, te certificas que ya es Worker, aun así
		Assert.isTrue(c.getAccount().getAuthorities().iterator().next().getAuthority().equals(Authority.CUSTOMER));

		return this.applicationRepo.findAllCustomer(customerId);
	}

	public Collection<Application> findAllWorker(final int workerId) {
		this.account = LoginService.getPrincipal();
		final HandyWorker w = this.workerService.findOne(workerId);
		Assert.isTrue(w.getAccount().getId() == this.account.getId());

		//Al comprobar el id, como no pueden exixtir dos usuarios con el mismo id, te certificas que ya es Worker, aun así
		Assert.isTrue(w.getAccount().getAuthorities().iterator().next().getAuthority().equals(Authority.HANDYWORKER));

		return this.applicationRepo.findAllCustomer(workerId);
	}

	public Collection<Double> statictisApplications() {

		this.account = LoginService.getPrincipal();
		Assert.isTrue(this.account.getAuthorities().iterator().next().getAuthority().equals(Authority.ADMIN));

		return this.applicationRepo.statictisApplication();
	}

	public double ratioPedingApplications() {

		this.account = LoginService.getPrincipal();
		Assert.isTrue(this.account.getAuthorities().iterator().next().getAuthority().equals(Authority.ADMIN));

		return this.applicationRepo.ratioPendingApplications();
	}
	public double ratioAcceptedApplications() {

		this.account = LoginService.getPrincipal();
		Assert.isTrue(this.account.getAuthorities().iterator().next().getAuthority().equals(Authority.ADMIN));

		return this.applicationRepo.ratioAcceptedApplications();
	}
	public double ratioRejectedApplications() {

		this.account = LoginService.getPrincipal();
		Assert.isTrue(this.account.getAuthorities().iterator().next().getAuthority().equals(Authority.ADMIN));

		return this.applicationRepo.ratioRejectedApplications();
	}
	public double ratioElapsedApplications() {

		this.account = LoginService.getPrincipal();
		Assert.isTrue(this.account.getAuthorities().iterator().next().getAuthority().equals(Authority.ADMIN));

		return this.applicationRepo.ratioElapsedApplications();
	}

	public Application findOne(final int applicationID) {

		Application a;
		Assert.isTrue(applicationID > 0);
		a = this.applicationRepo.findOne(applicationID);
		Assert.notNull(a);
		return a;

	}

}
