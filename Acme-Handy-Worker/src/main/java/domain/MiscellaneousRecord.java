
package domain;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public class MiscellaneousRecord extends Curricula {

	private String	title;
	private String	attachmentURL;


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

}
