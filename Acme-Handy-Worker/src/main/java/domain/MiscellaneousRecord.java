
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class MiscellaneousRecord extends DomainEntity {

	private String				title;
	private String				attachmentURL;
	private Collection<String>	comments;


	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@URL
	public String getAttachmentURL() {
		return this.attachmentURL;
	}

	public void setAttachmentURL(final String attachmentURL) {
		this.attachmentURL = attachmentURL;
	}

	@ElementCollection
	public Collection<String> getComments() {
		return this.comments;
	}

	public void setComments(final Collection<String> comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return "MiscellaneousRecord [title=" + this.title + ", attachmentURL=" + this.attachmentURL + ", comments=" + this.comments + "]";
	}

}
