
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Finder extends DomainEntity {

	private String					warranty;
	private String					keyWord;
	private String					category;
	private Double					minPrice;
	private Double					maxPrice;
	private String					dateRange;
	private Date					cacheUpdate;
	private Collection<FixUpTask>	fixUpTask;

	//+
	private HandyWorker				worker;


	public Date getCacheUpdate() {
		return this.cacheUpdate;
	}

	public void setCacheUpdate(final Date cacheUpdate) {
		this.cacheUpdate = cacheUpdate;
	}

	public String getWarranty() {
		return this.warranty;
	}

	public void setWarranty(final String warranty) {
		this.warranty = warranty;
	}

	public String getKeyWord() {
		return this.keyWord;
	}

	public void setKeyWord(final String keyWord) {
		this.keyWord = keyWord;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(final String category) {
		this.category = category;
	}

	public Double getMinPrice() {
		return this.minPrice;
	}

	public void setMinPrice(final Double minPrice) {
		this.minPrice = minPrice;
	}

	public Double getMaxPrice() {
		return this.maxPrice;
	}

	public void setMaxPrice(final Double maxPrice) {
		this.maxPrice = maxPrice;
	}

	public String getDateRange() {
		return this.dateRange;
	}

	public void setDateRange(final String dateRange) {
		this.dateRange = dateRange;
	}

	@ManyToMany()
	public Collection<FixUpTask> getFixUpTask() {
		return this.fixUpTask;
	}

	public void setFixUpTask(final Collection<FixUpTask> fixUpTask) {
		this.fixUpTask = fixUpTask;
	}

	@NotNull
	@OneToOne(optional = false)
	public HandyWorker getWorker() {
		return this.worker;
	}

	public void setWorker(final HandyWorker worker) {
		this.worker = worker;
	}

}
