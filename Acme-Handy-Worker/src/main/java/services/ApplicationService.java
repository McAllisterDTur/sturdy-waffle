
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ApplicationRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import utilities.AuthenticationUtility;
import domain.Application;
import domain.Customer;
import domain.FixUpTask;
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
	@Autowired
	private FixUpTaskService		taskService;
	@Autowired
	private ActorService			actorService;
	private UserAccount				account;


	public Application create(final int fixuptaskId) {
		final Application res = new Application();

		final HandyWorker w = (HandyWorker) this.actorService.findByUserAccountId(LoginService.getPrincipal().getId());
		res.setHandyWorker(w);

		final FixUpTask f = this.taskService.findOne(fixuptaskId);
		res.setFixUpTask(f);

		res.setCustomerComments(new ArrayList<String>());
		res.setHandyComments(new ArrayList<String>());
		res.setPhases(new ArrayList<Phase>());

		return res;
	}
	public Application save(final Application application) {
		Assert.notNull(application);

		Assert.isTrue(AuthenticationUtility.checkAuthority(Authority.HANDYWORKER) || AuthenticationUtility.checkAuthority(Authority.CUSTOMER));
		Application a;
		if (application.getId() == 0) {//creacci�n port parte del handyWorker
			Assert.isTrue(AuthenticationUtility.checkAuthority(Authority.HANDYWORKER));
			Assert.isTrue(application.getOfferedPrice() != 0);

			application.setRegisterTime(new Date());
			application.setStatus("PENDING");
			a = this.applicationRepo.save(application);

			final FixUpTask task = a.getFixUpTask();
			task.getApplications().add(a);

			this.taskService.save(task);
		} else {//Actualizacion del status por parte del customer due�o de la fixUpTask
			Assert.isTrue(AuthenticationUtility.checkAuthority(Authority.CUSTOMER));
			Assert.isTrue(application.getFixUpTask().getCustomer().getAccount().equals(this.account));//customer loggeado due�o de la task
			if (application.getStatus().equals("ACCEPTED"))
				Assert.notNull(application.getFixUpTask().getCreditCard());
			a = this.applicationRepo.save(application);
		}
		return a;
	}

	public Application saveComment(final int applicationId, final String comment) {

		Assert.isTrue(applicationId > 0);
		Assert.notNull(comment);
		Assert.isTrue(AuthenticationUtility.checkAuthority(Authority.CUSTOMER) || AuthenticationUtility.checkAuthority(Authority.HANDYWORKER));
		this.account = LoginService.getPrincipal();
		final Application application = this.findOne(applicationId);
		Application res;
		if (AuthenticationUtility.checkAuthority(Authority.CUSTOMER)) {
			final Customer c = (Customer) this.actorService.findByUserAccountId(this.account.getId());
			Assert.isTrue(c.getFixUpTasks().contains(application));

			application.getCustomerComments().add(comment);
		} else {
			final HandyWorker h = (HandyWorker) this.actorService.findByUserAccountId(this.account.getId());
			Assert.isTrue(h.getApplications().contains(application));

			application.getHandyComments().add(comment);
		}

		res = this.applicationRepo.save(application);

		return res;
	}

	public Collection<Application> findAllCustomer(final int customerId) {
		this.account = LoginService.getPrincipal();
		final Customer c = this.customerService.findOne(customerId);
		Assert.isTrue(this.account.getId() == c.getAccount().getId());

		//Al comprobar el id, como no pueden exixtir dos usuarios con el mismo id, te certificas que ya es Worker, aun as�
		Assert.isTrue(c.getAccount().getAuthorities().iterator().next().getAuthority().equals(Authority.CUSTOMER));

		return this.applicationRepo.findAllCustomer(customerId);
	}

	public Collection<Application> findAllWorker(final int workerId) {
		this.account = LoginService.getPrincipal();
		final HandyWorker w = this.workerService.findOne(workerId);
		Assert.isTrue(w.getAccount().getId() == this.account.getId());

		//Al comprobar el id, como no pueden exixtir dos usuarios con el mismo id, te certificas que ya es Worker, aun as�
		//Assert.isTrue(w.getAccount().getAuthorities().iterator().next().getAuthority().equals(Authority.HANDYWORKER));

		return this.applicationRepo.findAllWorker(workerId);
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
