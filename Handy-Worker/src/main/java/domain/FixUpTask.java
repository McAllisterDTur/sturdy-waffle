
package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;

@Entity
@Access(AccessType.PROPERTY)
public class FixUpTask extends DomainEntity {

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
	public String toString() {
		return "FixUpTask [ticker=" + this.ticker + ", publishTime=" + this.publishTime + ", description=" + this.description + ", address=" + this.address + ", maxPrice=" + this.maxPrice + ", periodStart=" + this.periodStart + ", periodEnd="
			+ this.periodEnd + ", creditCard=" + this.creditCard + ", category=" + this.category + ", warranty=" + this.warranty + ", customer=" + this.customer + ", complaint=" + this.complaint + ", application=" + this.application + "]";
	}

}
