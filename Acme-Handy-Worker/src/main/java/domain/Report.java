
package domain;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
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
	@Valid
	@ManyToOne(cascade = CascadeType.ALL)
	public Complaint getComplaint() {
		return this.complaint;
	}

	public void setComplaint(final Complaint complaint) {
		this.complaint = complaint;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "notes")
	public Collection<Notes> getNotes() {
		return this.notes;
	}

	public void setNotes(final Collection<Notes> notes) {
		this.notes = notes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((this.attachment == null) ? 0 : this.attachment.hashCode());
		result = prime * result + ((this.complaint == null) ? 0 : this.complaint.hashCode());
		result = prime * result + ((this.description == null) ? 0 : this.description.hashCode());
		result = prime * result + (this.isFinal ? 1231 : 1237);
		result = prime * result + ((this.notes == null) ? 0 : this.notes.hashCode());
		result = prime * result + ((this.referee == null) ? 0 : this.referee.hashCode());
		result = prime * result + ((this.reportTime == null) ? 0 : this.reportTime.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		final Report other = (Report) obj;
		if (this.attachment == null) {
			if (other.attachment != null)
				return false;
		} else if (!this.attachment.equals(other.attachment))
			return false;
		if (this.complaint == null) {
			if (other.complaint != null)
				return false;
		} else if (!this.complaint.equals(other.complaint))
			return false;
		if (this.description == null) {
			if (other.description != null)
				return false;
		} else if (!this.description.equals(other.description))
			return false;
		if (this.isFinal != other.isFinal)
			return false;
		if (this.notes == null) {
			if (other.notes != null)
				return false;
		} else if (!this.notes.equals(other.notes))
			return false;
		if (this.referee == null) {
			if (other.referee != null)
				return false;
		} else if (!this.referee.equals(other.referee))
			return false;
		if (this.reportTime == null) {
			if (other.reportTime != null)
				return false;
		} else if (!this.reportTime.equals(other.reportTime))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Report [reportTime=" + this.reportTime + ", description=" + this.description + ", attachment=" + this.attachment + ", isFinal=" + this.isFinal + ", referee=" + this.referee + ", complaint=" + this.complaint + ", notes=" + this.notes + "]";
	}

}
