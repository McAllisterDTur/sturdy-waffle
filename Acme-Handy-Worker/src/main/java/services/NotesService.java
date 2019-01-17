
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
	NotesRepository				notesRepo;

	@Autowired
	private ComplaintService	complaintService;
	@Autowired
	private ReportService		reportService;
	@Autowired
	private SpamService			spamService;
	@Autowired
	private ActorService		actorService;

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
		return n;
	}

	public Notes save(final Notes notes) {
		Assert.notNull(notes);
		Collection<String> comments = new ArrayList<>();
		Collection<String> originalComments = new ArrayList<>();
		final Collection<String> cadena = this.convertCollection(notes.getHandyComments());
		notes.setHandyComments(cadena);
		this.account = LoginService.getPrincipal();
		Notes ac = notes;
		if (notes.getId() != 0)
			ac = this.findOne(notes.getId());
		else
			ac.setMoment(new Date());
		final Report report = ac.getReport();
		final Collection<Notes> nuevaNota = report.getNotes();
		nuevaNota.remove(ac);
		Assert.isTrue(AuthenticationUtility.checkAuthority(Authority.REFEREE) || AuthenticationUtility.checkAuthority(Authority.CUSTOMER) || AuthenticationUtility.checkAuthority(Authority.HANDYWORKER));
		if (AuthenticationUtility.checkAuthority(Authority.REFEREE)) {
			Assert.isTrue(this.complaintService.findSelfassigned().contains(ac.getReport().getComplaint().getReferee()));
			comments = notes.getRefereeComments();
			Assert.isTrue((comments.iterator().next() != null) && !(comments.iterator().next().equals("")));
			originalComments = ac.getRefereeComments();
			originalComments.addAll(comments);
			this.spamService.isSpam(this.actorService.findByUserAccountId(this.account.getId()), comments);
			ac.setRefereeComments(originalComments);
		} else if (AuthenticationUtility.checkAuthority(Authority.CUSTOMER)) {
			Assert.isTrue(this.complaintService.findFromLoggedCustomer().contains(ac.getReport().getComplaint().getFixUpTask().getCustomer()));
			comments = notes.getCustomerComments();
			Assert.isTrue((comments.iterator().next() != null) && !(comments.iterator().next().equals("")));
			this.spamService.isSpam(this.actorService.findByUserAccountId(this.account.getId()), comments);
			originalComments = ac.getCustomerComments();
			originalComments.addAll(comments);
			ac.setCustomerComments(originalComments);
		} else if (AuthenticationUtility.checkAuthority(Authority.HANDYWORKER)) {
			Assert.isTrue(this.complaintService.findFromLoggedHandyWorker().contains(ac.getReport().getComplaint()));
			comments = notes.getHandyComments();
			Assert.isTrue((comments.iterator().next() != null) && !(comments.iterator().next().equals("")));
			this.spamService.isSpam(this.actorService.findByUserAccountId(this.account.getId()), comments);
			originalComments = ac.getHandyComments();
			originalComments.addAll(comments);
			ac.setHandyComments(originalComments);
		}
		nuevaNota.add(ac);
		report.setNotes(nuevaNota);
		this.reportService.save(report);
		return this.notesRepo.save(ac);
	}
	public Notes saveComment(final int noteId, final String comment) {
		Assert.isTrue(noteId > 0);
		Assert.isTrue((comment != null) && !(comment.equals("")));
		final Notes notes = this.notesRepo.findOne(noteId);
		this.account = LoginService.getPrincipal();
		Collection<String> comments = new ArrayList<>();

		Assert.isTrue(AuthenticationUtility.checkAuthority(Authority.REFEREE) || AuthenticationUtility.checkAuthority(Authority.CUSTOMER) || AuthenticationUtility.checkAuthority(Authority.HANDYWORKER));
		if (AuthenticationUtility.checkAuthority(Authority.REFEREE)) {
			Assert.isTrue(this.complaintService.findSelfassigned().contains(notes.getReport().getComplaint().getReferee()));
			this.spamService.isSpam(this.actorService.findByUserAccountId(this.account.getId()), notes.getRefereeComments());
			comments = notes.getRefereeComments();
			comments.add(comment);
			notes.setRefereeComments(comments);
		} else if (AuthenticationUtility.checkAuthority(Authority.CUSTOMER)) {
			Assert.isTrue(this.complaintService.findFromLoggedCustomer().contains(notes.getReport().getComplaint().getFixUpTask().getCustomer()));
			this.spamService.isSpam(this.actorService.findByUserAccountId(this.account.getId()), notes.getCustomerComments());
			comments = notes.getCustomerComments();
			comments.add(comment);
			notes.setCustomerComments(comments);
		} else if (AuthenticationUtility.checkAuthority(Authority.HANDYWORKER)) {
			Assert.isTrue(this.complaintService.findFromLoggedHandyWorker().contains(notes.getReport().getComplaint()));
			this.spamService.isSpam(this.actorService.findByUserAccountId(this.account.getId()), notes.getHandyComments());
			comments = notes.getHandyComments();
			comments.add(comment);
			notes.setHandyComments(comments);
		}
		final Notes newComment = this.notesRepo.save(notes);
		return newComment;
	}

	public Notes comment(final Notes note) {
		final Notes newNote = this.create(note.getReport());
		newNote.setId(note.getId());
		return newNote;
	}

	public Notes findOne(final int notesId) {
		Assert.isTrue(notesId > 0);
		final Notes notes = this.notesRepo.findOne(notesId);
		if (AuthenticationUtility.checkAuthority(Authority.REFEREE))
			Assert.isTrue(this.complaintService.findSelfassigned().contains(notes.getReport().getComplaint()));
		else if (AuthenticationUtility.checkAuthority(Authority.CUSTOMER))
			Assert.isTrue(this.complaintService.findFromLoggedCustomer().contains(notes.getReport().getComplaint()));
		else if (AuthenticationUtility.checkAuthority(Authority.HANDYWORKER))
			Assert.isTrue(this.complaintService.findFromLoggedHandyWorker().contains(notes.getReport().getComplaint()));
		return notes;

	}

	public Collection<String> convertCollection(final Collection<String> nota) {
		final String result = nota.toString();
		result.replace("[", "").replace("]", "");
		final Collection<String> ret = new ArrayList<>();
		ret.add(result);
		return ret;
	}

}
