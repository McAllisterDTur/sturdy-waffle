
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Endorsement extends DomainEntity {

	private Endorsable	sender;
	private Endorsable	reciever;
	private Date		writeTime;
	private String		comment;


	@NotNull
	@OneToOne(optional = true)
	public Endorsable getSender() {
		return this.sender;
	}

	public void setSender(final Endorsable sender) {
		this.sender = sender;
	}

	@NotNull
	@OneToOne(optional = true)
	public Endorsable getReciever() {
		return this.reciever;
	}

	public void setReciever(final Endorsable reciever) {
		this.reciever = reciever;
	}

	@Past
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
