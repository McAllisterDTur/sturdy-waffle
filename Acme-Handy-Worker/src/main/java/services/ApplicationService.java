
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

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
import domain.Message;
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
	private ActorService			actorService;
	@Autowired
	private FixUpTaskService		taskService;
	@Autowired
	private MessageService			messageService;

	private UserAccount				account;

	private static String			messageAcceptedEN	= "Congratulations, your application for the task has been accepted. Task: ";
	private static String			messageRejectedEN	= "We're so sorry, the customer has rejected your application to: ";

	private static String			messageAcceptedES	= "Enhorabuena, tu soliitud para la tarea ha sido aceptada. Tarea: ";
	private static String			messageRejectedES	= "Lo sentimos, el cliente no ha aceptado tu solicitud para la tarea: ";


	public Application create(final int fixuptaskId) {
		this.account = LoginService.getPrincipal();
		//Assert.isTrue(this.account.getAuthorities().contains(Authority.HANDYWORKER));
		final Application res = new Application();

		final FixUpTask task = this.taskService.findOne(fixuptaskId);
		System.out.println(task);

		res.setFixUpTask(task);
		res.setRegisterTime(new Date());
		res.setStatus("PENDING");
		res.setHandyWorker((HandyWorker) this.actorService.findByUserAccountId(this.account.getId()));

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
			Assert.isTrue(application.getOfferedPrice() >= 0);

			Assert.isTrue(application.getRegisterTime().before(application.getFixUpTask().getPeriodStart()));

			application.setRegisterTime(new Date());

			//application.setHandyComments(this.convertCollection(application.getHandyComments()));

			a = this.applicationRepo.save(application);
			Assert.notNull(this.taskService.updateTask(a));

		} else {//Actualizacion del status por parte del customer due�o de la fixUpTask
			Assert.isTrue(AuthenticationUtility.checkAuthority(Authority.CUSTOMER) || AuthenticationUtility.checkAuthority(Authority.HANDYWORKER));
			//Assert.isTrue(application.getFixUpTask().getCustomer().getAccount().equals(this.account));//customer loggeado due�o de la task
			if (application.getStatus().equals("ACCEPTED")) {
				Assert.isTrue(this.taskHasNoAcceptedApplication(application));
				Assert.notNull(application.getFixUpTask().getCreditCard());
				this.sendAcceptMessageTo(application);
			} else
				this.sendRejectMessageTo(application);

			//application.setHandyComments(this.convertCollection(application.getHandyComments()));
			//application.setCustomerComments(this.convertCollection(application.getCustomerComments()));
			a = this.applicationRepo.save(application);
		}
		return a;
	}
	public boolean taskHasNoAcceptedApplication(final Application a) {
		boolean res = true;

		final FixUpTask t = this.taskService.findOne(a.getFixUpTask().getId());
		final Collection<Application> apps = t.getApplications();

		for (final Application app : apps) {
			System.out.println(app);
			if (app.getId() != a.getId())
				if (app.getStatus().equals("ACCEPTED")) {
					System.out.println("Esta Task ya tiene una solicitud aceptada.");
					res = false;
					break;
				}
		}
		return res;
	}

	public Application accept(final Application a) {

		Assert.notNull(a);
		Assert.isTrue(a.getStatus().equals("PENDING"));

		Assert.isTrue(this.taskHasNoAcceptedApplication(a));

		a.setStatus("ACCEPTED");
		System.out.println(a.getStatus());
		final FixUpTask t = this.rejectRestOfApplications(a);

		Assert.notNull(t);
		System.out.println(t.getTicker());
		a.setFixUpTask(t);

		return this.save(a);

	}

	private FixUpTask rejectRestOfApplications(final Application a) {
		final FixUpTask t = a.getFixUpTask();
		final Collection<Application> apps = t.getApplications();
		System.out.println("Rechazando resto de solicitudes");
		for (final Application app : apps)
			if (app.getId() != a.getId()) {
				System.out.println("Rejecting application: " + app.getId());
				app.setStatus("REJECTED");
				this.save(app);
			}
		return this.taskService.save(t);
	}
	private void sendAcceptMessageTo(final Application a) {
		this.account = LoginService.getPrincipal();
		final Message msg = this.messageService.create(this.actorService.findByUserAccountId(this.account.getId()));
		msg.setPriority("HIGH");
		msg.setSubject("ACCEPTED APPLICATION -- SOLICITUD ACEPTADA");
		msg.setBody(ApplicationService.messageAcceptedEN + a.getFixUpTask().getTicker() + "\n\n" + ApplicationService.messageAcceptedES + a.getFixUpTask().getTicker());

		this.messageService.send(msg, a.getHandyWorker());
	}
	private void sendRejectMessageTo(final Application a) {
		this.account = LoginService.getPrincipal();
		final Message msg = this.messageService.create(this.actorService.findByUserAccountId(this.account.getId()));
		msg.setPriority("HIGH");
		msg.setSubject("REJECTED APPLICATION -- SOLICITUD RECHAZADA");
		msg.setBody(ApplicationService.messageRejectedEN + a.getFixUpTask().getTicker() + "\n\n" + ApplicationService.messageRejectedES + a.getFixUpTask().getTicker());

		this.messageService.send(msg, a.getHandyWorker());
	}

	public Application getApplicationAcceptedForFixUpTask(final int fixuptaskId) {
		Assert.notNull(fixuptaskId);

		return this.applicationRepo.getAcepptedApplicationForFixUpTask(fixuptaskId);
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
		Assert.isTrue(w.getAccount().getAuthorities().iterator().next().getAuthority().equals(Authority.HANDYWORKER));

		return this.applicationRepo.findAllWorker(workerId);
	}

	public Collection<Application> findAllTask(final int taskId) {
		this.account = LoginService.getPrincipal();
		final Customer c = (Customer) this.actorService.findByUserAccountId(this.account.getId());
		//FIXME
		//Al comprobar el id, como no pueden exixtir dos usuarios con el mismo id, te certificas que ya es Worker, aun as�
		Assert.isTrue(c.getAccount().getAuthorities().iterator().next().getAuthority().equals(Authority.CUSTOMER));

		return this.applicationRepo.findAllTask(taskId);
	}

	public List<Object[]> statictisApplications() {

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

	public List<Object[]> findApplicationsPerTask() {
		this.account = LoginService.getPrincipal();
		Assert.isTrue(this.account.getAuthorities().iterator().next().getAuthority().equals(Authority.ADMIN));
		return this.applicationRepo.applicationsPerFTask();
	}

	public Collection<Application> findAll() {
		return this.applicationRepo.findAll();
	}

	public Collection<String> convertCollection(final Collection<String> nota) {
		final String result = nota.toString();
		result.replace("[", "").replace("]", "");
		final Collection<String> ret = new ArrayList<>();
		ret.add(result);
		return ret;
	}

}
