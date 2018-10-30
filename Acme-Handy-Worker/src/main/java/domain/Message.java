
package domain;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;

public class Message {

	private DateTime		sendTime;
	private String			subject;
	private String			body;
	private List<String>	tags;


	public DateTime getSendTime() {
		return this.sendTime;
	}
	@Past
	public void setSendTime(final DateTime sendTime) {
		this.sendTime = sendTime;
	}

	public String getSubject() {
		return this.subject;
	}
	@NotBlank
	public void setSubject(final String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return this.body;
	}
	@NotBlank
	public void setBody(final String body) {
		this.body = body;
	}

	public List<String> getTags() {
		return this.tags;
	}
	@NotNull
	public void setTags(final List<String> tags) {
		this.tags = tags;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.body == null) ? 0 : this.body.hashCode());
		result = prime * result + ((this.sendTime == null) ? 0 : this.sendTime.hashCode());
		result = prime * result + ((this.subject == null) ? 0 : this.subject.hashCode());
		result = prime * result + ((this.tags == null) ? 0 : this.tags.hashCode());
		return result;
	}
	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Message))
			return false;
		final Message other = (Message) obj;
		if (this.body == null) {
			if (other.body != null)
				return false;
		} else if (!this.body.equals(other.body))
			return false;
		if (this.sendTime == null) {
			if (other.sendTime != null)
				return false;
		} else if (!this.sendTime.equals(other.sendTime))
			return false;
		if (this.subject == null) {
			if (other.subject != null)
				return false;
		} else if (!this.subject.equals(other.subject))
			return false;
		if (this.tags == null) {
			if (other.tags != null)
				return false;
		} else if (!this.tags.equals(other.tags))
			return false;
		return true;
	}

}
