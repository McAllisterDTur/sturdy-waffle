
package domain;

import java.util.List;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.joda.time.DateTime;

@Entity
public class Notes extends DomainEntity {

	private DateTime		moment;
	private List<String>	customerComments;
	private List<String>	refereeComments;
	private List<String>	handyCommetns;
	private Boolean			isFinal;


	@Past
	@NotNull
	public DateTime getMoment() {
		return this.moment;
	}

	public void setMoment(final DateTime moment) {
		this.moment = moment;
	}

	public List<String> getCustomerComments() {
		return this.customerComments;
	}

	public void setCustomerComments(final List<String> customerComments) {
		this.customerComments = customerComments;
	}

	public List<String> getRefereeComments() {
		return this.refereeComments;
	}

	public void setRefereeComments(final List<String> refereeComments) {
		this.refereeComments = refereeComments;
	}

	public List<String> getHandyCommetns() {
		return this.handyCommetns;
	}

	public void setHandyCommetns(final List<String> handyCommetns) {
		this.handyCommetns = handyCommetns;
	}

	public Boolean isFinal() {
		return this.isFinal;
	}

	public void setFinal(final Boolean isFinal) {
		this.isFinal = isFinal;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.customerComments == null) ? 0 : this.customerComments.hashCode());
		result = prime * result + ((this.handyCommetns == null) ? 0 : this.handyCommetns.hashCode());
		result = prime * result + (this.isFinal ? 1231 : 1237);
		result = prime * result + ((this.moment == null) ? 0 : this.moment.hashCode());
		result = prime * result + ((this.refereeComments == null) ? 0 : this.refereeComments.hashCode());
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
		final Notes other = (Notes) obj;
		if (this.customerComments == null) {
			if (other.customerComments != null)
				return false;
		} else if (!this.customerComments.equals(other.customerComments))
			return false;
		if (this.handyCommetns == null) {
			if (other.handyCommetns != null)
				return false;
		} else if (!this.handyCommetns.equals(other.handyCommetns))
			return false;
		if (this.isFinal != other.isFinal)
			return false;
		if (this.moment == null) {
			if (other.moment != null)
				return false;
		} else if (!this.moment.equals(other.moment))
			return false;
		if (this.refereeComments == null) {
			if (other.refereeComments != null)
				return false;
		} else if (!this.refereeComments.equals(other.refereeComments))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Notes [moment=" + this.moment + ", customerComments=" + this.customerComments + ", refereeComments=" + this.refereeComments + ", handyCommetns=" + this.handyCommetns + ", isFinal=" + this.isFinal + "]";
	}

}
