
package domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;

@Entity
@Access(AccessType.PROPERTY)
public class Application extends DomainEntity {

	private DateTime		registerTime;
	private Money			offeredPrice;
	private List<String>	customerComment;
	private List<String>	handyComments;
	private String			status;
	//+
	private FixUpTask		task;
	private List<Phase>		phases;


	@Past
	@NotBlank
	public DateTime getRegisterTime() {
		return this.registerTime;
	}

	public void setRegisterTime(final DateTime registerTime) {
		this.registerTime = registerTime;
	}
	@NotNull
	public Money getOfferedPrice() {
		return this.offeredPrice;
	}

	public void setOfferedPrice(final Money offeredPrice) {
		this.offeredPrice = offeredPrice;
	}
	@NotNull
	public List<String> getCustomerComment() {
		return new ArrayList<String>(this.customerComment);
	}

	public void setCustomerComment(final List<String> customerComment) {
		this.customerComment = customerComment;
	}
	@NotNull
	public List<String> getHandyComments() {
		return this.handyComments;
	}

	public void setHandyComments(final List<String> handyComments) {
		this.handyComments = handyComments;
	}

	@Pattern(regexp = "\b(ACCEPTED|REJECTED|PENDING)\b")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	/*
	 * @ManyToOne
	 * public HandyWorker getWorker() {
	 * return this.worker;
	 * }
	 * 
	 * public void setWorker(final HandyWorker worker) {
	 * this.worker = worker;
	 * }
	 */
	@ManyToOne
	public FixUpTask getTask() {
		return this.task;
	}

	public void setTask(final FixUpTask task) {
		this.task = task;
	}

	@OneToMany
	public List<Phase> getPhases() {
		return this.phases;
	}

	public void setPhases(final List<Phase> phases) {
		this.phases = phases;
	}

	@Override
	public String toString() {
		return "Application [registerTime=" + this.registerTime + ", offeredPrice=" + this.offeredPrice + ", customerComment=" + this.customerComment + ", handyComments=" + this.handyComments + ", status=" + this.status + "]";
	}

}
