
package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.joda.time.DateTime;

@Entity
@Access(AccessType.PROPERTY)
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
	public String toString() {
		return "Notes [moment=" + this.moment + ", customerComments=" + this.customerComments + ", refereeComments=" + this.refereeComments + ", handyCommetns=" + this.handyCommetns + ", isFinal=" + this.isFinal + "]";
	}

}
