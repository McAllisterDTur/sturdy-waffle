
package domain;

import java.util.List;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;

@Entity
public class Message extends DomainEntity {

	private Actor			sender;
	private Actor			reciever;
	private DateTime		sendTime;
	private String			subject;
	private String			body;
	private List<String>	tags;
	private String			priority;


	@NotNull
	public Actor getSender() {
		return this.sender;
	}

	public void setSender(final Actor sender) {
		this.sender = sender;
	}

	@NotNull
	public Actor getReciever() {
		return this.reciever;
	}

	public void setReciever(final Actor reciever) {
		this.reciever = reciever;
	}

	@Past
	public DateTime getSendTime() {
		return this.sendTime;
	}

	public void setSendTime(final DateTime sendTime) {
		this.sendTime = sendTime;
	}

	@NotBlank
	public String getSubject() {
		return this.subject;
	}

	public void setSubject(final String subject) {
		this.subject = subject;
	}

	@NotBlank
	public String getBody() {
		return this.body;
	}

	public void setBody(final String body) {
		this.body = body;
	}

	@NotNull
	public List<String> getTags() {
		return this.tags;
	}

	public void setTags(final List<String> tags) {
		this.tags = tags;
	}

	@NotBlank
	public String getPriority() {
		return this.priority;
	}

	public void setPriority(final String priority) {
		this.priority = priority;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((this.body == null) ? 0 : this.body.hashCode());
		result = prime * result + ((this.priority == null) ? 0 : this.priority.hashCode());
		result = prime * result + ((this.reciever == null) ? 0 : this.reciever.hashCode());
		result = prime * result + ((this.sendTime == null) ? 0 : this.sendTime.hashCode());
		result = prime * result + ((this.sender == null) ? 0 : this.sender.hashCode());
		result = prime * result + ((this.subject == null) ? 0 : this.subject.hashCode());
		result = prime * result + ((this.tags == null) ? 0 : this.tags.hashCode());
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
		final Message other = (Message) obj;
		if (this.body == null) {
			if (other.body != null)
				return false;
		} else if (!this.body.equals(other.body))
			return false;
		if (this.priority == null) {
			if (other.priority != null)
				return false;
		} else if (!this.priority.equals(other.priority))
			return false;
		if (this.reciever == null) {
			if (other.reciever != null)
				return false;
		} else if (!this.reciever.equals(other.reciever))
			return false;
		if (this.sendTime == null) {
			if (other.sendTime != null)
				return false;
		} else if (!this.sendTime.equals(other.sendTime))
			return false;
		if (this.sender == null) {
			if (other.sender != null)
				return false;
		} else if (!this.sender.equals(other.sender))
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

	@Override
	public String toString() {
		return "Message [sender=" + this.sender + ", reciever=" + this.reciever + ", sendTime=" + this.sendTime + ", subject=" + this.subject + ", body=" + this.body + ", tags=" + this.tags + ", priority=" + this.priority + "]";
	}

}
