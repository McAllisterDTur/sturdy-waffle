
package domain;

import java.util.List;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public class MiscellaneousRecord extends Curricula {

	private String			title;
	private String			attachmentURL;
	private List<String>	comments;


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
		result = prime * result + ((this.title == null) ? 0 : this.title.hashCode());
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
		final MiscellaneousRecord other = (MiscellaneousRecord) obj;
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
		if (this.title == null) {
			if (other.title != null)
				return false;
		} else if (!this.title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MiscellaneousRecord [title=" + this.title + ", attachmentURL=" + this.attachmentURL + ", comments=" + this.comments + "]";
	}

}
