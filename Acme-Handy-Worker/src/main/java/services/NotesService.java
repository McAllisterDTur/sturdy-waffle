
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.NotesRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import utilities.AuthenticationUtility;
import domain.Actor;
import domain.Notes;
import domain.Report;

@Service
public class NotesService {

	@Autowired
	private NotesRepository		notesRepo;
	@Autowired
	private ActorService		actorService;
	@Autowired
	private ComplaintService	complaintService;
	@Autowired
	private ReportService		reportService;
	@Autowired
	private SpamService			spamService;

	private UserAccount			account;


	public Notes create(final Report report) {

		Assert.notNull(report);
		final UserAccount ua = LoginService.getPrincipal();
		final Actor actorId = this.actorService.findByUserAccountId(ua.getId());
		final Collection<Report> reports = this.reportService.findReportsActor(actorId.getId());
		Assert.isTrue(reports.contains(report));
		Assert.isTrue(AuthenticationUtility.checkAuthority(Authority.REFEREE) || AuthenticationUtility.checkAuthority(Authority.CUSTOMER) || AuthenticationUtility.checkAuthority(Authority.HANDYWORKER));

		final Notes n = new Notes();
		final Date d = new Date();
		n.setReport(report);
		n.setMoment(d);
		n.setCustomerComments(new ArrayList<String>());
		n.setHandyComments(new ArrayList<String>());
		n.setRefereeComments(new ArrayList<String>());
		n.setIsFinal(true);

		return n;
	}

	public Notes save(final Notes notes) {
		Assert.notNull(notes);
		final UserAccount uaccount = LoginService.getPrincipal();
		Collection<String> comments = new ArrayList<>();
		Notes ac = notes;
		if (notes.getId() != 0)
			ac = this.findOne(notes.getId());
		Assert.isTrue(AuthenticationUtility.checkAuthority(Authority.REFEREE) || AuthenticationUtility.checkAuthority(Authority.CUSTOMER) || AuthenticationUtility.checkAuthority(Authority.HANDYWORKER));
		notes.setIsFinal(true);
		if (AuthenticationUtility.checkAuthority(Authority.REFEREE)) {
			Assert.isTrue(this.complaintService.findSelfassigned().contains(ac.getReport().getComplaint().getReferee()));
			comments = notes.getRefereeComments();
			ac.setRefereeComments(comments);
		} else if (AuthenticationUtility.checkAuthority(Authority.CUSTOMER)) {
			Assert.isTrue(this.complaintService.findFromLoggedCustomer().contains(ac.getReport().getComplaint().getFixUpTask().getCustomer()));
			comments = notes.getCustomerComments();
			ac.setCustomerComments(comments);
		} else if (AuthenticationUtility.checkAuthority(Authority.HANDYWORKER)) {
			Assert.isTrue(this.complaintService.findFromLoggedHandyWorker().contains(ac.getReport().getComplaint()));
			comments = notes.getHandyComments();
			ac.setHandyComments(comments);
		}
		Assert.isTrue(ac.getReport().getIsFinal());
		final Report report = ac.getReport();
		final Collection<Notes> nuevaNota = report.getNotes();
		nuevaNota.add(ac);
		report.setNotes(nuevaNota);
		this.reportService.save(report);
		return this.notesRepo.save(ac);

	}

	public Notes saveComment(final int noteId, final String comment) {
		Assert.isTrue(noteId > 0);
		Assert.isTrue((comment != null) && !(comment.equals("")));
		this.account = LoginService.getPrincipal();

		Assert.isTrue(AuthenticationUtility.checkAuthority(Authority.REFEREE) || AuthenticationUtility.checkAuthority(Authority.CUSTOMER) || AuthenticationUtility.checkAuthority(Authority.HANDYWORKER));

		final Notes notes = this.findOne(noteId);

		if (AuthenticationUtility.checkAuthority(Authority.REFEREE)) {
			Assert.isTrue(this.complaintService.findSelfassigned().contains(notes.getReport().getComplaint().getReferee()));
			this.spamService.isSpam(this.actorService.findByUserAccountId(this.account.getId()), notes.getRefereeComments());
			notes.getRefereeComments().add(comment);
		} else if (AuthenticationUtility.checkAuthority(Authority.CUSTOMER)) {
			Assert.isTrue(this.complaintService.findFromLoggedCustomer().contains(notes.getReport().getComplaint().getFixUpTask().getCustomer()));
			this.spamService.isSpam(this.actorService.findByUserAccountId(this.account.getId()), notes.getCustomerComments());
			notes.getCustomerComments().add(comment);
		} else if (AuthenticationUtility.checkAuthority(Authority.HANDYWORKER)) {
			Assert.isTrue(this.complaintService.findFromLoggedHandyWorker().contains(notes.getReport().getComplaint()));
			this.spamService.isSpam(this.actorService.findByUserAccountId(this.account.getId()), notes.getHandyComments());
			notes.getHandyComments().add(comment);
		}

		return this.notesRepo.save(notes);

	}

	public Notes findOne(final int notesId) {
		Assert.isTrue(notesId > 0);
		return this.notesRepo.findOne(notesId);

	}

}
