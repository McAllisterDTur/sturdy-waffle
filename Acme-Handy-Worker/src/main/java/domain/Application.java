
package domain;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;

public class Application extends DomainEntity {

	private DateTime		registerTime;
	private Money			offeredPrice;
	private List<String>	customerComment;
	private List<String>	handyComments;
	private Boolean			status;


	public DateTime getRegisterTime() {
		return this.registerTime;
	}

	@Past
	@NotBlank
	public void setRegisterTime(final DateTime registerTime) {
		this.registerTime = registerTime;
	}

	public Money getOfferedPrice() {
		return this.offeredPrice;
	}
	@NotNull
	public void setOfferedPrice(final Money offeredPrice) {
		this.offeredPrice = offeredPrice;
	}

	public List<String> getCustomerComment() {
		return new ArrayList<String>(this.customerComment);
	}
	@NotNull
	public void setCustomerComment(final List<String> customerComment) {
		this.customerComment = customerComment;
	}

	public List<String> getHandyComments() {
		return this.handyComments;
	}
	@NotNull
	public void setHandyComments(final List<String> handyComments) {
		this.handyComments = handyComments;
	}

	public Boolean getStatus() {
		return this.status;
	}

	public void setStatus(final Boolean status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.customerComment == null) ? 0 : this.customerComment.hashCode());
		result = prime * result + ((this.handyComments == null) ? 0 : this.handyComments.hashCode());
		result = prime * result + ((this.offeredPrice == null) ? 0 : this.offeredPrice.hashCode());
		result = prime * result + ((this.registerTime == null) ? 0 : this.registerTime.hashCode());
		result = prime * result + ((this.status == null) ? 0 : this.status.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Application))
			return false;
		final Application other = (Application) obj;
		if (this.customerComment == null) {
			if (other.customerComment != null)
				return false;
		} else if (!this.customerComment.equals(other.customerComment))
			return false;
		if (this.handyComments == null) {
			if (other.handyComments != null)
				return false;
		} else if (!this.handyComments.equals(other.handyComments))
			return false;
		if (this.offeredPrice == null) {
			if (other.offeredPrice != null)
				return false;
		} else if (!this.offeredPrice.equals(other.offeredPrice))
			return false;
		if (this.registerTime == null) {
			if (other.registerTime != null)
				return false;
		} else if (!this.registerTime.equals(other.registerTime))
			return false;
		if (this.status == null) {
			if (other.status != null)
				return false;
		} else if (!this.status.equals(other.status))
			return false;
		return true;
	}

}
