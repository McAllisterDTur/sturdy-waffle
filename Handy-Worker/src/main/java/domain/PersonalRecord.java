
package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class PersonalRecord extends DomainEntity {

	private String			fullName;

	private String			photo;

	private String			email;

	private String			phoneNumber;

	private String			linkedInURL;

	private List<String>	comments;

	//+
	private Curricula		curricula;


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

	@Pattern(regexp = "((([+][1-9]{1}[0-9]{0,2}[\\s]){0,1}([(][1-9]{1}[0-9]{0,2}[)][\\s]){0,1})){0,1}([0-9]{9}){1}")
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

	public List<String> getComments() {
		return this.comments;
	}

	public void setComments(final List<String> comments) {
		this.comments = comments;
	}

	@OneToOne
	public Curricula getCurricula() {
		return this.curricula;
	}

	public void setCurricula(final Curricula curricula) {
		this.curricula = curricula;
	}

	@Override
	public String toString() {
		return "PersonalRecord [fullName=" + this.fullName + ", photo=" + this.photo + ", email=" + this.email + ", phoneNumber=" + this.phoneNumber + ", linkedInURL=" + this.linkedInURL + ", comments=" + this.comments + "]";
	}

}
