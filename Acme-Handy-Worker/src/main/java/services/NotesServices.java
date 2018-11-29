
package services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.NotesRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import utilities.AuthenticationUtility;
import domain.Notes;

@Service
public class NotesServices {

	@Autowired
	private NotesRepository		notesRepo;
	@Autowired
	private ActorService		actorService;
	@Autowired
	private ComplaintService	complaintService;
	@Autowired
	private SpamService			spamService;

	private UserAccount			account;


	public Notes create() {
		final Notes n = new Notes();

		n.setCustomerComments(new ArrayList<String>());
		n.setHandyComments(new ArrayList<String>());
		n.setRefereeComments(new ArrayList<String>());

		return n;
	}

	public Notes save(final Notes notes) {

		Assert.notNull(notes);
		this.account = LoginService.getPrincipal();
		Assert.isTrue(AuthenticationUtility.checkAuthority(Authority.REFEREE) || AuthenticationUtility.checkAuthority(Authority.CUSTOMER) || AuthenticationUtility.checkAuthority(Authority.HANDYWORKER));

		if (AuthenticationUtility.checkAuthority(Authority.REFEREE))
			Assert.isTrue(this.complaintService.findSelfassigned().contains(notes.getReport().getComplaint().getReferee()));
		else if (AuthenticationUtility.checkAuthority(Authority.CUSTOMER))
			Assert.isTrue(this.complaintService.findFromLoggedCustomer().contains(notes.getReport().getComplaint().getFixUpTask().getCustomer()));
		else if (AuthenticationUtility.checkAuthority(Authority.HANDYWORKER))
			Assert.isTrue(this.complaintService.findFromLoggedHandyWorker().contains(notes.getReport().getComplaint()));//TODO: esperando query de Anthoni

		Assert.isTrue(notes.getReport().getIsFinal());

		return this.notesRepo.save(notes);
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
			Assert.isTrue(this.complaintService.findFromLoggedHandyWorker().contains(notes.getReport().getComplaint()));//TODO: esperando query de Anthoni
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
