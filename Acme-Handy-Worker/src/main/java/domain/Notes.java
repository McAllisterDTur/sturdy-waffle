
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import org.springframework.format.annotation.DateTimeFormat;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Notes extends DomainEntity {

	private Date				moment;
	private Collection<String>	customerComments;
	private Collection<String>	refereeComments;
	private Collection<String>	handyComments;
	//+
	private Report				report;


	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
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
	public Collection<String> getHandyComments() {
		return this.handyComments;
	}

	public void setHandyComments(final Collection<String> handyComments) {
		this.handyComments = handyComments;
	}

	@ManyToOne(optional = false)
	public Report getReport() {
		return this.report;
	}

	public void setReport(final Report report) {
		this.report = report;
	}

	@Override
	public String toString() {
		return "Notes [moment=" + this.moment + ", customerComments=" + this.customerComments + ", refereeComments=" + this.refereeComments + ", handyCommetns=" + this.handyComments + "]";
	}

}
