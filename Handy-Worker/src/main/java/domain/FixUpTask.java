
package domain;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class FixUpTask extends DomainEntity {

	private String					ticker;
	private Date					publishTime;
	private String					description;
	private String					address;
	private Double					maxPrice;
	private Date					periodStart;
	private Date					periodEnd;
	private CreditCard				creditCard;
	private Category				category;
	private Warranty				warranty;
	private Customer				customer;
	private Collection<Complaint>	complaint;
	private Collection<Application>	application;


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
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public Date getPublishTime() {
		return this.publishTime;
	}

	public void setPublishTime(final Date publishTime) {
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
	public Double getMaxPrice() {
		return this.maxPrice;
	}

	public void setMaxPrice(final Double maxPrice) {
		this.maxPrice = maxPrice;
	}

	@NotNull
	public Date getPeriodStart() {
		return this.periodStart;
	}

	public void setPeriodStart(final Date periodStart) {
		this.periodStart = periodStart;
	}

	public Date getPeriodEnd() {
		return this.periodEnd;
	}

	public void setPeriodEnd(final Date periodEnd) {
		this.periodEnd = periodEnd;
	}

	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	@ManyToOne
	public Category getCategory() {
		return this.category;
	}

	public void setCategory(final Category category) {
		this.category = category;
	}

	@ManyToOne
	public Warranty getWarranty() {
		return this.warranty;
	}

	public void setWarranty(final Warranty warranty) {
		this.warranty = warranty;
	}
	@ManyToOne
	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(final Customer customer) {
		this.customer = customer;
	}

	@OneToMany
	public Collection<Complaint> getComplaint() {
		return this.complaint;
	}

	public void setComplaint(final Collection<Complaint> complaint) {
		this.complaint = complaint;
	}

	@OneToMany
	public Collection<Application> getApplication() {
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
