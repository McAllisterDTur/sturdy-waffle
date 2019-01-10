
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import security.UserAccount;

@Entity
@Access(AccessType.PROPERTY)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Actor extends DomainEntity {

	private String		name;
	private String		middleName;
	private String		surname;
	private String		photoURL;
	private String		email;
	private String		phone;
	private String		address;
	private Boolean		banned;
	//+
	private UserAccount	account;
	private Boolean		isSuspicious;


	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getMiddleName() {
		return this.middleName;
	}

	public void setMiddleName(final String middleName) {
		this.middleName = middleName;
	}

	@NotBlank
	public String getSurname() {
		return this.surname;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	@URL
	public String getPhotoURL() {
		return this.photoURL;
	}

	public void setPhotoURL(final String photoURL) {
		this.photoURL = photoURL;
	}

	@NotBlank
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	//@Pattern(regexp = "((([+][1-9]{1}[0-9]{0,2}[\\s]){0,1}([(][1-9]{1}[0-9]{0,2}[)][\\s]){0,1})){0,1}([0-9]{9}){1}")
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

	@NotNull
	public Boolean getBanned() {
		return this.banned;
	}

	public void setBanned(final Boolean banned) {
		this.banned = banned;
	}

	@Valid
	@OneToOne(optional = false)
	public UserAccount getAccount() {
		return this.account;
	}

	public void setAccount(final UserAccount account) {
		this.account = account;
	}

	public Boolean getIsSuspicious() {
		return this.isSuspicious;
	}

	public void setIsSuspicious(final Boolean isSuspicious) {
		this.isSuspicious = isSuspicious;
	}

	@Override
	public String toString() {
		return "Actor [name=" + this.name + ", middleName=" + this.middleName + ", surname=" + this.surname + ", photoURL=" + this.photoURL + ", email=" + this.email + ", phone=" + this.phone + ", address=" + this.address + ", banned=" + this.banned + "]";
	}

}
