
package domain;

import java.util.List;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public class EndorserRecord extends DomainEntity {

	private String			endorserName;
	private String			email;
	private List<String>	phoneNumber;
	private String			linkedInURL;
	private List<String>	comments;


	@NotBlank
	public String getEndorserName() {
		return this.endorserName;
	}

	public void setEndorserName(final String endorserName) {
		this.endorserName = endorserName;
	}

	@Email
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public List<String> getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(final List<String> phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@URL
	@NotBlank
	public String getLinkedInURL() {
		return this.linkedInURL;
	}

	public void setLinkedInURL(final String linkedInURL) {
		this.linkedInURL = linkedInURL;
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
		result = prime * result + ((this.comments == null) ? 0 : this.comments.hashCode());
		result = prime * result + ((this.email == null) ? 0 : this.email.hashCode());
		result = prime * result + ((this.endorserName == null) ? 0 : this.endorserName.hashCode());
		result = prime * result + ((this.linkedInURL == null) ? 0 : this.linkedInURL.hashCode());
		result = prime * result + ((this.phoneNumber == null) ? 0 : this.phoneNumber.hashCode());
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
		final EndorserRecord other = (EndorserRecord) obj;
		if (this.comments == null) {
			if (other.comments != null)
				return false;
		} else if (!this.comments.equals(other.comments))
			return false;
		if (this.email == null) {
			if (other.email != null)
				return false;
		} else if (!this.email.equals(other.email))
			return false;
		if (this.endorserName == null) {
			if (other.endorserName != null)
				return false;
		} else if (!this.endorserName.equals(other.endorserName))
			return false;
		if (this.linkedInURL == null) {
			if (other.linkedInURL != null)
				return false;
		} else if (!this.linkedInURL.equals(other.linkedInURL))
			return false;
		if (this.phoneNumber == null) {
			if (other.phoneNumber != null)
				return false;
		} else if (!this.phoneNumber.equals(other.phoneNumber))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "EndorserRecord [endorserName=" + this.endorserName + ", email=" + this.email + ", phoneNumber=" + this.phoneNumber + ", linkedInURL=" + this.linkedInURL + ", comments=" + this.comments + "]";
	}

}
