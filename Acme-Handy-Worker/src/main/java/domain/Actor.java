
package domain;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public class Actor extends DomainEntity {

	private String	name;
	private String	middleName;
	private String	surname;
	private String	photoURL;
	private String	email;
	private String	phone;
	private String	address;
	private Boolean	banned;


	public String getName() {
		return this.name;
	}

	@NotBlank
	public void setName(final String name) {
		this.name = name;
	}

	public String getMiddleName() {
		return this.middleName;
	}

	public void setMiddleName(final String middleName) {
		this.middleName = middleName;
	}

	public String getSurname() {
		return this.surname;
	}
	@NotBlank
	public void setSurname(final String surname) {
		this.surname = surname;
	}

	public String getPhotoURL() {
		return this.photoURL;
	}

	@URL
	public void setPhotoURL(final String photoURL) {
		this.photoURL = photoURL;
	}

	public String getEmail() {
		return this.email;
	}

	@NotBlank
	@Email
	public void setEmail(final String email) {
		this.email = email;
	}
	// Regex: ((([+][1-9]{1}[0-9]{0,2}[\s]){0,1}([(][1-9]{1}[0-9]{0,2}[)][\s]){0,1})){0,1}([0-9]{9}){1}
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	public Boolean getBanned() {
		return this.banned;
	}

	public void setBanned(final Boolean banned) {
		this.banned = banned;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.address == null) ? 0 : this.address.hashCode());
		result = prime * result + ((this.banned == null) ? 0 : this.banned.hashCode());
		result = prime * result + ((this.email == null) ? 0 : this.email.hashCode());
		result = prime * result + ((this.middleName == null) ? 0 : this.middleName.hashCode());
		result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
		result = prime * result + ((this.phone == null) ? 0 : this.phone.hashCode());
		result = prime * result + ((this.photoURL == null) ? 0 : this.photoURL.hashCode());
		result = prime * result + ((this.surname == null) ? 0 : this.surname.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Actor))
			return false;
		final Actor other = (Actor) obj;
		if (this.address == null) {
			if (other.address != null)
				return false;
		} else if (!this.address.equals(other.address))
			return false;
		if (this.banned == null) {
			if (other.banned != null)
				return false;
		} else if (!this.banned.equals(other.banned))
			return false;
		if (this.email == null) {
			if (other.email != null)
				return false;
		} else if (!this.email.equals(other.email))
			return false;
		if (this.middleName == null) {
			if (other.middleName != null)
				return false;
		} else if (!this.middleName.equals(other.middleName))
			return false;
		if (this.name == null) {
			if (other.name != null)
				return false;
		} else if (!this.name.equals(other.name))
			return false;
		if (this.phone == null) {
			if (other.phone != null)
				return false;
		} else if (!this.phone.equals(other.phone))
			return false;
		if (this.photoURL == null) {
			if (other.photoURL != null)
				return false;
		} else if (!this.photoURL.equals(other.photoURL))
			return false;
		if (this.surname == null) {
			if (other.surname != null)
				return false;
		} else if (!this.surname.equals(other.surname))
			return false;
		return true;
	}

}
