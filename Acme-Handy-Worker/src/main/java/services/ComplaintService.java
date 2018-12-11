
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ComplaintRepository;
import security.Authority;
import security.LoginService;
import utilities.AuthenticationUtility;
import domain.Actor;
import domain.Complaint;
import domain.Customer;
import domain.FixUpTask;
import domain.HandyWorker;
import domain.Referee;

@Service
@Transactional
public class ComplaintService {

	@Autowired
	private ComplaintRepository	complaintRepository;
	@Autowired
	private ActorService		actorService;
	@Autowired
	private TickerService		tickerService;
	@Autowired
	private SpamService			spamService;
	@Autowired
	private FixUpTaskService	taskService;


	public Complaint create() {
		final Complaint c = new Complaint();
		c.setAttachments(new ArrayList<String>());
		c.setFixUpTask(new FixUpTask());
		return c;
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
		complaint.setTicker(this.tickerService.getTicker());
		complaint.setComplaintTime(new Date());
		final Actor actor = this.actorService.findByUserAccountId(LoginService.getPrincipal()
			.getId());
		this.spamService.isSpam(actor, complaint.getDescription());
		final FixUpTask f = complaint.getFixUpTask();
		f.getComplaints().add(complaint);
		this.taskService.saveInternal(f);
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

	/**
	 * Checks admin authority (Req 38.5.1)
	 * 
	 * @return Collection of complaints per fix up task statistics
	 */
	public Collection<Double> minMaxAvgDevComplaintsPerFixUpTask() {
		final boolean au = AuthenticationUtility.checkAuthority(Authority.ADMIN);
		Assert.isTrue(au);
		return this.complaintRepository.minMaxAvgDevComplaintsPerFixUpTask();
	}

	//===============REFEREE
	/**
	 * Checks referee authority (Req 36.1)
	 * 
	 * @return Collection of the complaints that have no referee
	 */
	public Collection<Complaint> findUnassigned() {
		final boolean hasAu = AuthenticationUtility.checkAuthority(Authority.REFEREE);
		Assert.isTrue(hasAu);
		return this.complaintRepository.findUnassigned();
	}
	/**
	 * Checks referee authority (Req 36.2)
	 * 
	 * @return Collection of the complaints of the logged referee
	 */
	public Collection<Complaint> findSelfassigned() {
		final boolean hasAu = AuthenticationUtility.checkAuthority(Authority.REFEREE);
		Assert.isTrue(hasAu);
		final Referee referee = (Referee) this.actorService.findByUserAccountId(LoginService
			.getPrincipal().getId());
		return this.complaintRepository.findSelfassigned(referee.getId());
	}

	/**
	 * Checks referee or customer authority (Req 36.2)
	 * 
	 * @param complaintId
	 * @return Complaint of the logged referee that has that id;
	 */
	public Complaint findOne(final int complaintId) {
		final boolean hasAu = AuthenticationUtility.checkAuthority(Authority.REFEREE);
		final boolean hasAu1 = AuthenticationUtility.checkAuthority(Authority.CUSTOMER);
		Assert.isTrue(hasAu || hasAu1);
		final Actor actor = this.actorService.findByUserAccountId(LoginService.getPrincipal()
			.getId());
		final Complaint c1 = this.complaintRepository.findOne(complaintId);
		Assert.isTrue(c1.getReferee() != null && c1.getReferee().equals(actor)
			|| c1.getFixUpTask().getCustomer().equals(actor));
		return c1;
	}

	//===============HANDY WORKER
	/**
	 * Checks handy worker authority (Req 37.3)
	 * 
	 * @return Collection of the complaints related to the logged handy worker
	 */
	public Collection<Complaint> findFromLoggedHandyWorker() {
		final boolean hasAu = AuthenticationUtility.checkAuthority(Authority.HANDYWORKER);
		Assert.isTrue(hasAu);
		final HandyWorker h = (HandyWorker) this.actorService.findByUserAccountId(LoginService
			.getPrincipal().getId());
		return this.complaintRepository.findFromHandyWorker(h.getId());
	}

	public int getNumberOfTickers(final String ticker) {
		return this.complaintRepository.getNumberOfTickers(ticker);
	}
}
