
package domain;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public class MiscelaneousRecord extends Curricula {

	private String	title;
	private String	attachmentURL;


	public String getTitle() {
		return this.title;
	}
	@NotBlank
	public void setTitle(final String title) {
		this.title = title;
	}

	public String getAttachmentURL() {
		return this.attachmentURL;
	}
	@URL
	public void setAttachmentURL(final String attachmentURL) {
		this.attachmentURL = attachmentURL;
	}

}
