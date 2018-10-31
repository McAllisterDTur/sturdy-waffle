
package domain;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Report extends DomainEntity {

	//Atributes
	private Date			reportTime;

	private String			description;

	private List<String>	attachment;

	private boolean			isFinal;


	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getReportTime() {
		return this.reportTime;
	}

	public void setReportTime(final Date reportTime) {
		this.reportTime = reportTime;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public List<String> getAttachment() {
		return this.attachment;
	}

	public void setAttachment(final List<String> attachment) {
		this.attachment = attachment;
	}

	@NotBlank
	public boolean isFinal() {
		return this.isFinal;
	}

	public void setFinal(final boolean isFinal) {
		this.isFinal = isFinal;
	}


	//Relationships
	private Referee				referee;

	private Complaint			complaint;

	private Collection<Notes>	notes;


	@NotNull
	@Valid
	@ManyToOne(cascade = CascadeType.ALL)
	public Referee getReferee() {
		return this.referee;
	}

	public void setReferee(final Referee referee) {
		this.referee = referee;
	}

	@NotNull
	public Complaint getComplaint() {
		return this.complaint;
	}

	public void setComplaint(final Complaint complaint) {
		this.complaint = complaint;
	}

	public Collection<Notes> getNotes() {
		return this.notes;
	}

	public void setNotes(final Collection<Notes> notes) {
		this.notes = notes;
	}
	@Override
	public String toString() {
		return "Report [reportTime=" + this.reportTime + ", description=" + this.description + ", attachment=" + this.attachment + ", isFinal=" + this.isFinal + ", referee=" + this.referee + ", complaint=" + this.complaint + ", notes=" + this.notes + "]";
	}

}
