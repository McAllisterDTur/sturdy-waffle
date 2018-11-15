
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.search.annotations.Indexed;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Indexed
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
	private Collection<Application>	applications;
	private Collection<Complaint>	complaints;


	@OneToMany
	public Collection<Complaint> getComplaints() {
		return this.complaints;
	}

	public void setComplaints(final Collection<Complaint> complaints) {
		this.complaints = complaints;
	}

	@OneToMany
	public Collection<Application> getApplications() {
		return this.applications;
	}

	public void setApplications(final Collection<Application> applications) {
		this.applications = applications;
	}

	@Column(unique = true)
	@NotBlank
	@Pattern(regexp = "(\\d{2})(([0][1-9])|([1][0-2]))(([0][1-9])|[1-2][0-9]|[3][0-1])-(([A-Z]|[0-9]){6})$")
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
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
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getPeriodStart() {
		return this.periodStart;
	}

	public void setPeriodStart(final Date periodStart) {
		this.periodStart = periodStart;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
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

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Category getCategory() {
		return this.category;
	}

	public void setCategory(final Category category) {
		this.category = category;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Warranty getWarranty() {
		return this.warranty;
	}

	public void setWarranty(final Warranty warranty) {
		this.warranty = warranty;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(final Customer customer) {
		this.customer = customer;
	}

	@Override
	public String toString() {
		return "FixUpTask [ticker=" + this.ticker + ", publishTime=" + this.publishTime + ", description=" + this.description + ", address=" + this.address + ", maxPrice=" + this.maxPrice + ", periodStart=" + this.periodStart + ", periodEnd="
			+ this.periodEnd + ", creditCard=" + this.creditCard + ", category=" + this.category + ", warranty=" + this.warranty + ", customer=" + this.customer + "]";
	}

}
