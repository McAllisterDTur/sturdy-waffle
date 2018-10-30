
package domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;

@Entity
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.complaintTime == null) ? 0 : this.complaintTime.hashCode());
		result = prime * result + ((this.description == null) ? 0 : this.description.hashCode());
		result = prime * result + (this.isFinal ? 1231 : 1237);
		result = prime * result + ((this.ticker == null) ? 0 : this.ticker.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		final Complaint other = (Complaint) obj;
		if (this.complaintTime == null) {
			if (other.complaintTime != null)
				return false;
		} else if (!this.complaintTime.equals(other.complaintTime))
			return false;
		if (this.description == null) {
			if (other.description != null)
				return false;
		} else if (!this.description.equals(other.description))
			return false;
		if (this.isFinal != other.isFinal)
			return false;
		if (this.ticker == null) {
			if (other.ticker != null)
				return false;
		} else if (!this.ticker.equals(other.ticker))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Complaint [ticker=" + this.ticker + ", complaintTime=" + this.complaintTime + ", description=" + this.description + ", attachments=" + this.attachments + ", isFinal=" + this.isFinal + ", fixUpTask=" + this.fixUpTask + ", reports="
			+ this.reports + "]";
	}

}
