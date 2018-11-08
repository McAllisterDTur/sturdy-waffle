
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;

@Entity
@Access(AccessType.PROPERTY)
public class Endorsement extends DomainEntity {

	private Endorsable	sender;
	private Endorsable	reciever;
	private DateTime	writeTime;
	private String		comment;


	@NotNull
	@OneToOne
	public Endorsable getSender() {
		return this.sender;
	}

	public void setSender(final Endorsable sender) {
		this.sender = sender;
	}

	@NotNull
	@OneToOne
	public Endorsable getReciever() {
		return this.reciever;
	}

	public void setReciever(final Endorsable reciever) {
		this.reciever = reciever;
	}

	@Past
	public DateTime getWriteTime() {
		return this.writeTime;
	}

	public void setWriteTime(final DateTime writeTime) {
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
