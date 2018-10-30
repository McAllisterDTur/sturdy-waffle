package domain;
import java.util.Collection;
import java.util.Date;

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
public class Report  extends DomainEntity{
	
	//Constructor
	public Report() {
		super();
	}

	//Atributes
	private Date reportTime;

	private String description;

	private String[] attachment;

	private boolean isFinal;
	
	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getReportTime() {
		return reportTime;
	}

	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String[] getAttachment() {
		return attachment;
	}

	public void setAttachment(String[] attachment) {
		this.attachment = attachment;
	}

	@NotBlank
	public boolean isFinal() {
		return isFinal;
	}

	public void setFinal(final boolean isFinal) {
		this.isFinal = isFinal;
	}

	//Relationships
	private Referee referee;

	private Complaint complaint;

	private Collection<Notes> notes;
	
	@NotNull
	@Valid
	@ManyToOne(cascade = CascadeType.ALL)
	public Referee getReferee() {
		return referee;
	}

	public void setReferee(final Referee referee) {
		this.referee = referee;
	}
	
	@NotNull
	@Valid
	@ManyToOne(cascade = CascadeType.ALL)
	public Complaint getComplaint() {
		return complaint;
	}

	public void setComplaint(final Complaint complaint) {
		this.complaint = complaint;
	}
	
	@NotNull
	@Valid
	@OneToMany(mappedBy = "notes")
	public Collection<Notes> getNotes() {
		return notes;
	}

	public void setNotes(final Collection<Notes> notes) {
		this.notes = notes;
	}

}
