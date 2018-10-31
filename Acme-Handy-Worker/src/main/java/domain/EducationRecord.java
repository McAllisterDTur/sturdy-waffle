
package domain;

import java.util.Date;
import java.util.List;

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
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((this.attachmentURL == null) ? 0 : this.attachmentURL.hashCode());
		result = prime * result + ((this.comments == null) ? 0 : this.comments.hashCode());
		result = prime * result + ((this.diplomaTitle == null) ? 0 : this.diplomaTitle.hashCode());
		result = prime * result + ((this.end == null) ? 0 : this.end.hashCode());
		result = prime * result + ((this.institution == null) ? 0 : this.institution.hashCode());
		result = prime * result + ((this.start == null) ? 0 : this.start.hashCode());
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
		final EducationRecord other = (EducationRecord) obj;
		if (this.attachmentURL == null) {
			if (other.attachmentURL != null)
				return false;
		} else if (!this.attachmentURL.equals(other.attachmentURL))
			return false;
		if (this.comments == null) {
			if (other.comments != null)
				return false;
		} else if (!this.comments.equals(other.comments))
			return false;
		if (this.diplomaTitle == null) {
			if (other.diplomaTitle != null)
				return false;
		} else if (!this.diplomaTitle.equals(other.diplomaTitle))
			return false;
		if (this.end == null) {
			if (other.end != null)
				return false;
		} else if (!this.end.equals(other.end))
			return false;
		if (this.institution == null) {
			if (other.institution != null)
				return false;
		} else if (!this.institution.equals(other.institution))
			return false;
		if (this.start == null) {
			if (other.start != null)
				return false;
		} else if (!this.start.equals(other.start))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "EducationRecord [diplomaTitle=" + this.diplomaTitle + ", institution=" + this.institution + ", start=" + this.start + ", end=" + this.end + ", attachmentURL=" + this.attachmentURL + ", comment=" + this.comments + "]";
	}

}
