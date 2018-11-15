
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Message extends DomainEntity {

	private Actor				sender;
	private Actor				reciever;
	private Date			sendTime;
	private String				subject;
	private String				body;
	private Collection<String>	tags;
	private String				priority;

	//+
	private Collection<Box>		boxes;


	@NotNull
	@ManyToOne(optional=false)
	public Actor getSender() {
		return this.sender;
	}

	public void setSender(final Actor sender) {
		this.sender = sender;
	}

	@NotNull
	@ManyToOne(optional=false)
	public Actor getReciever() {
		return this.reciever;
	}

	public void setReciever(final Actor reciever) {
		this.reciever = reciever;
	}

	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getSendTime() {
		return this.sendTime;
	}

	public void setSendTime(final Date sendTime) {
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

	@ElementCollection
	public Collection<String> getTags() {
		return this.tags;
	}

	public void setTags(final Collection<String> tags) {
		this.tags = tags;
	}
	
	@NotBlank
	@Pattern(regexp = "\\b(HIGH|NEUTRAL|LOW)\\b")
	public String getPriority() {
		return this.priority;
	}

	public void setPriority(final String priority) {
		this.priority = priority;
	}

	@Valid
	@ManyToMany
	public Collection<Box> getBoxes() {
		return this.boxes;
	}

	public void setBoxes(final Collection<Box> boxes) {
		this.boxes = boxes;
	}

	@Override
	public String toString() {
		return "Message [sender=" + this.sender + ", reciever=" + this.reciever + ", sendTime=" + this.sendTime + ", subject=" + this.subject + ", body=" + this.body + ", tags=" + this.tags + ", priority=" + this.priority + "]";
	}

}
