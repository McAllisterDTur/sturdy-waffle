
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;

@Entity
@Access(AccessType.PROPERTY)
public class Application extends DomainEntity {

	private DateTime			registerTime;
	private Double				offeredPrice;
	private Collection<String>	customerComment;
	private Collection<String>	handyComments;
	private String				status;
	//+
	private HandyWorker			worker;
	private FixUpTask			task;


	@Past
	@NotBlank
	public DateTime getRegisterTime() {
		return this.registerTime;
	}

	public void setRegisterTime(final DateTime registerTime) {
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
	@NotNull
	public Collection<String> getCustomerComment() {
		return this.customerComment;
	}

	public void setCustomerComment(final Collection<String> customerComment) {
		this.customerComment = customerComment;
	}
	@ElementCollection
	@NotNull
	public Collection<String> getHandyComments() {
		return this.handyComments;
	}

	public void setHandyComments(final Collection<String> handyComments) {
		this.handyComments = handyComments;
	}

	@Pattern(regexp = "\b(ACCEPTED|REJECTED|PENDING)\b")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	@ManyToOne(optional = true)
	public HandyWorker getWorker() {
		return this.worker;
	}

	public void setWorker(final HandyWorker worker) {
		this.worker = worker;
	}
	@ManyToOne(optional = true)
	public FixUpTask getTask() {
		return this.task;
	}

	public void setTask(final FixUpTask task) {
		this.task = task;
	}

	@Override
	public String toString() {
		return "Application [registerTime=" + this.registerTime + ", offeredPrice=" + this.offeredPrice + ", customerComment=" + this.customerComment + ", handyComments=" + this.handyComments + ", status=" + this.status + "]";
	}

}
