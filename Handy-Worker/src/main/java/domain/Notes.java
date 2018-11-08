
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.joda.time.DateTime;

@Entity
@Access(AccessType.PROPERTY)
public class Notes extends DomainEntity {

	private DateTime			moment;
	private Collection<String>	customerComments;
	private Collection<String>	refereeComments;
	private Collection<String>	handyCommetns;
	private Boolean				isFinal;
	//+
	private Report				report;


	@Past
	@NotNull
	public DateTime getMoment() {
		return this.moment;
	}

	public void setMoment(final DateTime moment) {
		this.moment = moment;
	}

	public Collection<String> getCustomerComments() {
		return this.customerComments;
	}

	public void setCustomerComments(final Collection<String> customerComments) {
		this.customerComments = customerComments;
	}

	public Collection<String> getRefereeComments() {
		return this.refereeComments;
	}

	public void setRefereeComments(final Collection<String> refereeComments) {
		this.refereeComments = refereeComments;
	}

	public Collection<String> getHandyCommetns() {
		return this.handyCommetns;
	}

	public void setHandyCommetns(final Collection<String> handyCommetns) {
		this.handyCommetns = handyCommetns;
	}

	public Boolean getIsFinal() {
		return this.isFinal;
	}

	public Report getReport() {
		return this.report;
	}

	public void setReport(final Report report) {
		this.report = report;
	}

	public void setIsFinal(final Boolean isFinal) {
		this.isFinal = isFinal;
	}

	@Override
	public String toString() {
		return "Notes [moment=" + this.moment + ", customerComments=" + this.customerComments + ", refereeComments=" + this.refereeComments + ", handyCommetns=" + this.handyCommetns + ", isFinal=" + this.isFinal + "]";
	}

}
