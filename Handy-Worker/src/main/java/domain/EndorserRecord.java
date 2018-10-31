
package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
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
	public String toString() {
		return "EndorserRecord [endorserName=" + this.endorserName + ", email=" + this.email + ", phoneNumber=" + this.phoneNumber + ", linkedInURL=" + this.linkedInURL + ", comments=" + this.comments + "]";
	}

}
