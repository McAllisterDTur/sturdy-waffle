
package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;

@Entity
@Access(AccessType.PROPERTY)
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
	@Pattern(regexp = "\b(HIGH|NEUTRAL|LOW)\b")
	public String getPriority() {
		return this.priority;
	}

	public void setPriority(final String priority) {
		this.priority = priority;
	}

	@Override
	public String toString() {
		return "Message [sender=" + this.sender + ", reciever=" + this.reciever + ", sendTime=" + this.sendTime + ", subject=" + this.subject + ", body=" + this.body + ", tags=" + this.tags + ", priority=" + this.priority + "]";
	}

}