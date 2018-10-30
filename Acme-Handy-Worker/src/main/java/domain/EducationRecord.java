
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
public class EducationRecord extends DomainEntity {

	//Atributes

	private String		diplomaTitle;

	private String		institution;

	private Date		start;

	private Date		end;

	private String[]	attachmentURL;

	private String		comment;


	public String getComment() {
		return this.comment;
	}

	public void setComment(final String comment) {
		this.comment = comment;
	}

	@NotBlank
	public String getDiplomaTitle() {
		return this.diplomaTitle;
	}

	public void setDiplomaTitle(final String diplomaTitle) {
		this.diplomaTitle = diplomaTitle;
	}

	@NotBlank
	public String getInstitution() {
		return this.institution;
	}

	public void setInstitution(final String institution) {
		this.institution = institution;
	}

	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getStart() {
		return this.start;
	}

	public void setStart(final Date start) {
		this.start = start;
	}

	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getEnd() {
		return this.end;
	}

	public void setEnd(final Date end) {
		this.end = end;
	}

	@URL
	public String[] getAttachmentURL() {
		return this.attachmentURL;
	}

	public void setAttachmentURL(final String[] attachmentURL) {
		this.attachmentURL = attachmentURL;
	}

}
