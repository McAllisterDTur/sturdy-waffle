package domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class ProfessionalRecord extends DomainEntity {

	// Constructor
	public ProfessionalRecord() {
		super();
	}

	// Atributes
	private String companyName;

	private String role;

	private Date start;

	private Date end;

	private String[] attachmentURL;

	private String comments;

	@NotBlank
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(final String companyName) {
		this.companyName = companyName;
	}

	@NotBlank
	public String getRole() {
		return role;
	}

	public void setRole(final String role) {
		this.role = role;
	}

	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getStart() {
		return start;
	}

	public void setStart(final Date start) {
		this.start = start;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getEnd() {
		return end;
	}

	public void setEnd(final Date end) {
		this.end = end;
	}

	@URL
	public String[] getAttachmentURL() {
		return attachmentURL;
	}

	public void setAttachmentURL(final String[] attachmentURL) {
		this.attachmentURL = attachmentURL;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(final String comments) {
		this.comments = comments;
	}

}
