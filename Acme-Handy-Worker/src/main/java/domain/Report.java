
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Report extends DomainEntity {

	//Atributes
	private Date				reportTime;
	private String				description;
	private Collection<String>	attachment;
	private Boolean				isFinal;
	private Collection<Notes>	notes;

	//+
	private Referee				referee;
	private Complaint			complaint;


	@NotNull
	public Boolean getIsFinal() {
		return this.isFinal;
	}

	public void setIsFinal(final Boolean isFinal) {
		this.isFinal = isFinal;
	}

	@OneToMany(cascade = CascadeType.ALL)
	public Collection<Notes> getNotes() {
		return this.notes;
	}

	public void setNotes(final Collection<Notes> notes) {
		this.notes = notes;
	}

	@NotNull
	//@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getReportTime() {
		return this.reportTime;
	}

	public void setReportTime(final Date reportTime) {
		this.reportTime = reportTime;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@ElementCollection
	public Collection<String> getAttachment() {
		return this.attachment;
	}

	public void setAttachment(final Collection<String> attachment) {
		this.attachment = attachment;
	}

	@NotNull
	@ManyToOne(optional = false)
	public Referee getReferee() {
		return this.referee;
	}

	public void setReferee(final Referee referee) {
		this.referee = referee;
	}

	@NotNull
	@ManyToOne(optional = false)
	public Complaint getComplaint() {
		return this.complaint;
	}

	public void setComplaint(final Complaint complaint) {
		this.complaint = complaint;
	}

	@Override
	public String toString() {
		return "Report [reportTime=" + this.reportTime + ", description=" + this.description + ", attachment=" + this.attachment + ", isFinal=" + this.isFinal + ", referee=" + this.referee + ", complaint=" + this.complaint + "]";
	}

}
