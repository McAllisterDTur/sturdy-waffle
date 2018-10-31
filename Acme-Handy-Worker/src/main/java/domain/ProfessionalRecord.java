
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
public class ProfessionalRecord extends DomainEntity {

	// Atributes
	private String			companyName;

	private String			role;

	private Date			start;

	private Date			end;

	private List<String>	attachmentURL;

	private List<String>	comments;


	@NotBlank
	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(final String companyName) {
		this.companyName = companyName;
	}

	@NotBlank
	public String getRole() {
		return this.role;
	}

	public void setRole(final String role) {
		this.role = role;
	}

	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getStart() {
		return this.start;
	}

	public void setStart(final Date start) {
		this.start = start;
	}

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

	public List<String> getComments() {
		return this.comments;
	}

	public void setComments(final List<String> comments) {
		this.comments = comments;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((this.attachmentURL == null) ? 0 : this.attachmentURL.hashCode());
		result = prime * result + ((this.comments == null) ? 0 : this.comments.hashCode());
		result = prime * result + ((this.companyName == null) ? 0 : this.companyName.hashCode());
		result = prime * result + ((this.end == null) ? 0 : this.end.hashCode());
		result = prime * result + ((this.role == null) ? 0 : this.role.hashCode());
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
		final ProfessionalRecord other = (ProfessionalRecord) obj;
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
		if (this.companyName == null) {
			if (other.companyName != null)
				return false;
		} else if (!this.companyName.equals(other.companyName))
			return false;
		if (this.end == null) {
			if (other.end != null)
				return false;
		} else if (!this.end.equals(other.end))
			return false;
		if (this.role == null) {
			if (other.role != null)
				return false;
		} else if (!this.role.equals(other.role))
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
		return "ProfessionalRecord [companyName=" + this.companyName + ", role=" + this.role + ", start=" + this.start + ", end=" + this.end + ", attachmentURL=" + this.attachmentURL + ", comments=" + this.comments + "]";
	}

}
