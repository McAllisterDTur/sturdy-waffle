
package domain;

import javax.persistence.Entity;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
public class PersonalRecord extends DomainEntity {

	private String	fullName;

	private String	photo;

	private String	email;

	private String	phoneNumber;

	private String	linkedInURL;


	@NotBlank
	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(final String fullName) {
		this.fullName = fullName;
	}
	@URL
	public String getPhoto() {
		return this.photo;
	}

	public void setPhoto(final String photo) {
		this.photo = photo;
	}
	@Email
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}
	//@Pattern(regexp = "((([+][1-9]{1}[0-9]{0,2}[\s]){0,1}([(][1-9]{1}[0-9]{0,2}[)][\s]){0,1})){0,1}([0-9]{9}){1}")
	@NotBlank
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	@URL
	public String getLinkedInURL() {
		return this.linkedInURL;
	}

	public void setLinkedInURL(final String linkedInURL) {
		this.linkedInURL = linkedInURL;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.email == null) ? 0 : this.email.hashCode());
		result = prime * result + ((this.fullName == null) ? 0 : this.fullName.hashCode());
		result = prime * result + ((this.linkedInURL == null) ? 0 : this.linkedInURL.hashCode());
		result = prime * result + ((this.phoneNumber == null) ? 0 : this.phoneNumber.hashCode());
		result = prime * result + ((this.photo == null) ? 0 : this.photo.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		final PersonalRecord other = (PersonalRecord) obj;
		if (this.email == null) {
			if (other.email != null)
				return false;
		} else if (!this.email.equals(other.email))
			return false;
		if (this.fullName == null) {
			if (other.fullName != null)
				return false;
		} else if (!this.fullName.equals(other.fullName))
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
		if (this.photo == null) {
			if (other.photo != null)
				return false;
		} else if (!this.photo.equals(other.photo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PersonalRecord [fullName=" + this.fullName + ", photo=" + this.photo + ", email=" + this.email + ", phoneNumber=" + this.phoneNumber + ", linkedInURL=" + this.linkedInURL + "]";
	}

}
