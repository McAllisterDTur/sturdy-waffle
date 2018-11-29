
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Endorsement extends DomainEntity {

	private Endorsable	sender;
	private Endorsable	reciever;
	private Date		writeTime;
	private String		comment;


	@NotNull
	@ManyToOne(optional = false)
	public Endorsable getSender() {
		return this.sender;
	}

	public void setSender(final Endorsable sender) {
		this.sender = sender;
	}

	@NotNull
	@OneToOne(optional = false)
	public Endorsable getReciever() {
		return this.reciever;
	}

	public void setReciever(final Endorsable reciever) {
		this.reciever = reciever;
	}

	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getWriteTime() {
		return this.writeTime;
	}

	public void setWriteTime(final Date writeTime) {
		this.writeTime = writeTime;
	}

	@NotBlank
	public String getComment() {
		return this.comment;
	}

	public void setComment(final String comment) {
		this.comment = comment;
	}

	@Override
	public String toString() {
		return "Endorsement [sender=" + this.sender + ", reciever=" + this.reciever + ", writeTime=" + this.writeTime + ", comment=" + this.comment + "]";
	}

}
