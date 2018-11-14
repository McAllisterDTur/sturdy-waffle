
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
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Application extends DomainEntity {

	private Date				registerTime;
	private Double				offeredPrice;
	private Collection<String>	customerComments;
	private Collection<String>	handyComments;
	private String				status;
	//+
	private HandyWorker			handyWorker;
	private FixUpTask			fixUpTask;


	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getRegisterTime() {
		return this.registerTime;
	}

	public void setRegisterTime(final Date registerTime) {
		this.registerTime = registerTime;
	}
	
	@NotNull
	public Double getOfferedPrice() {
		return this.offeredPrice;
	}

	public void setOfferedPrice(final Double offeredPrice) {
		this.offeredPrice = offeredPrice;
	}

	@ElementCollection
	public Collection<String> getCustomerComments() {
		return customerComments;
	}

	public void setCustomerComments(final Collection<String> customerComments) {
		this.customerComments = customerComments;
	}
	
	@ElementCollection
	public Collection<String> getHandyComments() {
		return this.handyComments;
	}

	public void setHandyComments(final Collection<String> handyComments) {
		this.handyComments = handyComments;
	}

	@Pattern(regexp = "\\b(ACCEPTED|REJECTED|PENDING)\\b")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	@ManyToOne(optional = false)
	public HandyWorker getHandyWorker() {
		return this.handyWorker;
	}

	public void setHandyWorker(final HandyWorker handyWorker) {
		this.handyWorker = handyWorker;
	}
	
	@ManyToOne(optional = false)
	public FixUpTask getFixUpTask() {
		return fixUpTask;
	}

	public void setFixUpTask(final FixUpTask fixUpTask) {
		this.fixUpTask = fixUpTask;
	}


	@Override
	public String toString() {
		return "Application [registerTime=" + this.registerTime + ", offeredPrice=" + this.offeredPrice + ", customerComment=" + this.customerComments + ", handyComments=" + this.handyComments + ", status=" + this.status + "]";
	}

}
