
package domain;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;

@Entity
public class Endorsement extends DomainEntity {

	private Endorsable	sender;
	private Endorsable	reciever;
	private DateTime	writeTime;
	private String		comment;


	@NotNull
	public Endorsable getSender() {
		return this.sender;
	}

	public void setSender(final Endorsable sender) {
		this.sender = sender;
	}

	@NotNull
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.comment == null) ? 0 : this.comment.hashCode());
		result = prime * result + ((this.reciever == null) ? 0 : this.reciever.hashCode());
		result = prime * result + ((this.sender == null) ? 0 : this.sender.hashCode());
		result = prime * result + ((this.writeTime == null) ? 0 : this.writeTime.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		final Endorsement other = (Endorsement) obj;
		if (this.comment == null) {
			if (other.comment != null)
				return false;
		} else if (!this.comment.equals(other.comment))
			return false;
		if (this.reciever == null) {
			if (other.reciever != null)
				return false;
		} else if (!this.reciever.equals(other.reciever))
			return false;
		if (this.sender == null) {
			if (other.sender != null)
				return false;
		} else if (!this.sender.equals(other.sender))
			return false;
		if (this.writeTime == null) {
			if (other.writeTime != null)
				return false;
		} else if (!this.writeTime.equals(other.writeTime))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Endorsement [sender=" + this.sender + ", reciever=" + this.reciever + ", writeTime=" + this.writeTime + ", comment=" + this.comment + "]";
	}

}
