
package domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Complaint extends DomainEntity {

	private String				ticker;

	private Date				complaintTime;

	private String				description;

	private Collection<String>	attachments;

	private Boolean				isFinal;

	private FixUpTask			fixUpTask;


	@Column(unique = true)
	@NotBlank
	//@Pattern(regexp = "^(\\d{2})(\\d{2})(\\d{2})\\-([1-9a-zA-Z]{6})$")
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	@NotNull
	//@Past
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getComplaintTime() {
		return this.complaintTime;
	}

	public void setComplaintTime(final Date complaintTime) {
		this.complaintTime = complaintTime;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@ElementCollection
	public Collection<String> getAttachments() {
		return new ArrayList<String>(this.attachments);
	}

	public void setAttachments(final Collection<String> attachments) {
		this.attachments = new ArrayList<String>(attachments);
	}
	@NotNull
	public Boolean getIsFinal() {
		return this.isFinal;
	}

	public void setIsFinal(final Boolean isFinal) {
		this.isFinal = isFinal;
	}

	@ManyToOne(optional = true)
	public FixUpTask getFixUpTask() {
		return this.fixUpTask;
	}

	public void setFixUpTask(final FixUpTask fixUpTask) {
		this.fixUpTask = fixUpTask;
	}

	@Override
	public String toString() {
		return "Complaint [ticker=" + this.ticker + ", complaintTime=" + this.complaintTime + ", description=" + this.description + ", attachments=" + this.attachments + ", isFinal=" + this.isFinal + ", fixUpTask=" + this.fixUpTask + "]";
	}

}
