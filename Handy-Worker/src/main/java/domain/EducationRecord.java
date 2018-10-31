
package domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class EducationRecord extends DomainEntity {

	//Atributes

	private String			diplomaTitle;

	private String			institution;

	private Date			start;

	private Date			end;

	private List<String>	attachmentURL;

	private List<String>	comments;


	public List<String> getComments() {
		return this.comments;
	}

	public void setComments(final List<String> comment) {
		this.comments = comment;
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
	public List<String> getAttachmentURL() {
		return this.attachmentURL;
	}

	public void setAttachmentURL(final List<String> attachmentURL) {
		this.attachmentURL = attachmentURL;
	}

	@Override
	public String toString() {
		return "EducationRecord [diplomaTitle=" + this.diplomaTitle + ", institution=" + this.institution + ", start=" + this.start + ", end=" + this.end + ", attachmentURL=" + this.attachmentURL + ", comment=" + this.comments + "]";
	}

}
