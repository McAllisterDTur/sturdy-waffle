
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

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

	private Referee				referee;

	private Collection<Report>	reports;


	@Column(unique = true)
	@Pattern(regexp = "^(\\d{2})(\\d{2})(\\d{2})\\-([0-9a-zA-Z]{6})$")
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getComplaintTime() {
		return this.complaintTime;
	}

	public void setComplaintTime(final Date complaintTime) {
		this.complaintTime = complaintTime;
	}

	@NotNull
	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@ElementCollection
	public Collection<String> getAttachments() {
		return this.attachments;
	}

	public void setAttachments(final Collection<String> attachments) {
		this.attachments = attachments;
	}

	public Boolean getIsFinal() {
		return this.isFinal;
	}

	public void setIsFinal(final Boolean isFinal) {
		this.isFinal = isFinal;
	}
	@NotNull
	@ManyToOne(optional = false)
	public FixUpTask getFixUpTask() {
		return this.fixUpTask;
	}

	public void setFixUpTask(final FixUpTask fixUpTask) {
		this.fixUpTask = fixUpTask;
	}

	@ManyToOne(optional = true)
	public Referee getReferee() {
		return this.referee;
	}

	public void setReferee(final Referee referee) {
		this.referee = referee;
	}

	@OneToMany(cascade = CascadeType.ALL)
	public Collection<Report> getReports() {
		return this.reports;
	}

	public void setReports(final Collection<Report> reports) {
		this.reports = reports;
	}

	@Override
	public String toString() {
		return "Complaint [ticker=" + this.ticker + ", complaintTime=" + this.complaintTime + ", description=" + this.description + ", attachments=" + this.attachments + ", isFinal=" + this.isFinal + ", fixUpTask=" + this.fixUpTask + "]";
	}

}
