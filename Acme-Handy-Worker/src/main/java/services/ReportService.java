
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ReportRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import utilities.AuthenticationUtility;
import domain.Actor;
import domain.Notes;
import domain.Report;

@Service
@Transactional
public class ReportService {

	@Autowired
	private ReportRepository	reportRepo;
	@Autowired
	private SpamService			spamService;
	@Autowired
	private ActorService		actorService;
	@Autowired
	private ComplaintService	complaintService;

	private UserAccount			account;


	public Report create(final int complaintId) {
		final Report res = new Report();

		res.setComplaint(this.complaintService.findOne(complaintId));
		res.setIsFinal(false);
		res.setReportTime(new Date());
		res.setAttachment(new ArrayList<String>());
		res.setNotes(new ArrayList<Notes>());

		return res;

	}
	public Report save(final Report report) {
		Assert.notNull(report);
		this.account = LoginService.getPrincipal();
		Assert.isTrue(this.account.getAuthorities().iterator().next().getAuthority().equals(Authority.REFEREE));

		Report res = null;
		Assert.isTrue(report.getComplaint().getReferee().getAccount().equals(this.account));
		if (report.getId() != 0)
			Assert.isTrue(!report.getIsFinal());
		else if (report.getId() == 0) {
			report.setReportTime(new Date());
			report.setIsFinal(false);
		}

		this.spamService.isSpam(this.actorService.findByUserAccountId(this.account.getId()), report.getDescription());

		res = this.reportRepo.save(report);
		res.getComplaint().getReports().add(res);
		return res;
	}
	public Report findOne(final int reportId) {
		Assert.isTrue(reportId > 0);

		this.account = LoginService.getPrincipal();

		final Report res = this.reportRepo.findOne(reportId);

		Assert.isTrue((AuthenticationUtility.checkAuthority(Authority.REFEREE)) || ((AuthenticationUtility.checkAuthority(Authority.CUSTOMER) || AuthenticationUtility.checkAuthority(Authority.HANDYWORKER)) && res.getIsFinal()));

		return res;
	}

	//TODO: cambiar a encontrar por id de un actor para abarcar referee, customer y worker
	public Collection<Report> findReportsActor(final int actorId) {
		Assert.notNull(actorId);

		this.account = LoginService.getPrincipal();
		final Actor actor = this.actorService.findOne(actorId);

		if (AuthenticationUtility.checkAuthority(Authority.REFEREE) && (this.account.getId() == actor.getAccount().getId()))
			return this.reportRepo.findAllReportsReferee(actor.getId());
		else if (AuthenticationUtility.checkAuthority(Authority.CUSTOMER) && (this.account.getId() == actor.getAccount().getId()))
			return this.reportRepo.findAllReportsCustomer(actor.getId());
		else if (AuthenticationUtility.checkAuthority(Authority.HANDYWORKER) && (this.account.getId() == actor.getAccount().getId()))
			return this.reportRepo.findAllReportsWorker(actor.getId());
		else
			return null;

	}

}
