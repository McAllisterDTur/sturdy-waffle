
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
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
	private boolean				isFinal;
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

	@ElementCollection
	public Collection<String> getCustomerComments() {
		return this.customerComments;
	}

	public void setCustomerComments(final Collection<String> customerComments) {
		this.customerComments = customerComments;
	}

	@ElementCollection
	public Collection<String> getRefereeComments() {
		return this.refereeComments;
	}

	public void setRefereeComments(final Collection<String> refereeComments) {
		this.refereeComments = refereeComments;
	}

	@ElementCollection
	public Collection<String> getHandyCommetns() {
		return this.handyCommetns;
	}

	public void setHandyCommetns(final Collection<String> handyCommetns) {
		this.handyCommetns = handyCommetns;
	}

	public boolean getIsFinal() {
		return this.isFinal;
	}

	@ManyToOne(optional = true)
	public Report getReport() {
		return this.report;
	}

	public void setReport(final Report report) {
		this.report = report;
	}

	public void setIsFinal(final boolean isFinal) {
		this.isFinal = isFinal;
	}

	@Override
	public String toString() {
		return "Notes [moment=" + this.moment + ", customerComments=" + this.customerComments + ", refereeComments=" + this.refereeComments + ", handyCommetns=" + this.handyCommetns + ", isFinal=" + this.isFinal + "]";
	}

}
