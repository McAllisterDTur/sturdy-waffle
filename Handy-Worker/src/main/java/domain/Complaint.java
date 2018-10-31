
package domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;

@Entity
@Access(AccessType.PROPERTY)
public class Complaint extends DomainEntity {

	private String			ticker;

	private DateTime		complaintTime;

	private String			description;

	private List<String>	attachments;

	private boolean			isFinal;

	private FixUpTask		fixUpTask;

	private List<Report>	reports;


	@NotBlank
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	@Past
	@NotNull
	public DateTime getComplaintTime() {
		return this.complaintTime;
	}

	public void setComplaintTime(final DateTime complaintTime) {
		this.complaintTime = complaintTime;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public List<String> getAttachments() {
		return new ArrayList<String>(this.attachments);
	}

	public void setAttachments(final List<String> attachments) {
		this.attachments = new ArrayList<String>(attachments);
	}

	public boolean isFinal() {
		return this.isFinal;
	}

	public void setFinal(final boolean isFinal) {
		this.isFinal = isFinal;
	}

	public FixUpTask getFixUpTask() {
		return this.fixUpTask;
	}

	public void setFixUpTask(final FixUpTask fixUpTask) {
		this.fixUpTask = fixUpTask;
	}

	public List<Report> getReports() {
		return new ArrayList<Report>(this.reports);
	}

	public void setReports(final List<Report> reports) {
		this.reports = new ArrayList<Report>(reports);
	}

	@Override
	public String toString() {
		return "Complaint [ticker=" + this.ticker + ", complaintTime=" + this.complaintTime + ", description=" + this.description + ", attachments=" + this.attachments + ", isFinal=" + this.isFinal + ", fixUpTask=" + this.fixUpTask + ", reports="
			+ this.reports + "]";
	}

}
