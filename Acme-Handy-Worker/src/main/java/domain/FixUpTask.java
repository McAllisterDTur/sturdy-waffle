
package domain;

import java.util.List;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;

public class FixUpTask {

	private String				ticker;
	private DateTime			publishTime;
	private String				description;
	private String				address;
	private Money				maxPrice;
	private DateTime			periodStart;
	private DateTime			periodEnd;
	private CreditCard			creditCard;
	private Category			category;
	private Warranty			warranty;
	private Customer			customer;
	private List<Complaint>		complaint;
	private List<Application>	application;


	@NotBlank
	@Column(unique = true)
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	@Past
	@NotNull
	public DateTime getPublishTime() {
		return this.publishTime;
	}

	public void setPublishTime(final DateTime publishTime) {
		this.publishTime = publishTime;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@NotBlank
	public String getAddress() {
		return this.address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	@NotNull
	public Money getMaxPrice() {
		return this.maxPrice;
	}

	public void setMaxPrice(final Money maxPrice) {
		this.maxPrice = maxPrice;
	}

	@NotNull
	public DateTime getPeriodStart() {
		return this.periodStart;
	}

	public void setPeriodStart(final DateTime periodStart) {
		this.periodStart = periodStart;
	}

	public DateTime getPeriodEnd() {
		return this.periodEnd;
	}

	public void setPeriodEnd(final DateTime periodEnd) {
		this.periodEnd = periodEnd;
	}

	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	public Category getCategory() {
		return this.category;
	}

	public void setCategory(final Category category) {
		this.category = category;
	}

	public Warranty getWarranty() {
		return this.warranty;
	}

	public void setWarranty(final Warranty warranty) {
		this.warranty = warranty;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(final Customer customer) {
		this.customer = customer;
	}

	public List<Complaint> getComplaint() {
		return this.complaint;
	}

	public void setComplaint(final List<Complaint> complaint) {
		this.complaint = complaint;
	}

	public List<Application> getApplication() {
		return this.application;
	}

	public void setApplication(final List<Application> application) {
		this.application = application;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.address == null) ? 0 : this.address.hashCode());
		result = prime * result + ((this.description == null) ? 0 : this.description.hashCode());
		result = prime * result + ((this.periodEnd == null) ? 0 : this.periodEnd.hashCode());
		result = prime * result + ((this.periodStart == null) ? 0 : this.periodStart.hashCode());
		result = prime * result + ((this.publishTime == null) ? 0 : this.publishTime.hashCode());
		result = prime * result + ((this.ticker == null) ? 0 : this.ticker.hashCode());
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
		final FixUpTask other = (FixUpTask) obj;
		if (this.address == null) {
			if (other.address != null)
				return false;
		} else if (!this.address.equals(other.address))
			return false;
		if (this.description == null) {
			if (other.description != null)
				return false;
		} else if (!this.description.equals(other.description))
			return false;
		if (this.periodEnd == null) {
			if (other.periodEnd != null)
				return false;
		} else if (!this.periodEnd.equals(other.periodEnd))
			return false;
		if (this.periodStart == null) {
			if (other.periodStart != null)
				return false;
		} else if (!this.periodStart.equals(other.periodStart))
			return false;
		if (this.publishTime == null) {
			if (other.publishTime != null)
				return false;
		} else if (!this.publishTime.equals(other.publishTime))
			return false;
		if (this.ticker == null) {
			if (other.ticker != null)
				return false;
		} else if (!this.ticker.equals(other.ticker))
			return false;
		return true;
	}

}
