
package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
@Access(AccessType.PROPERTY)
public class Finder extends DomainEntity {

	private String			warranty;
	private String			keyWord;
	private String			category;
	private String			priceRange;
	private String			dateRange;
	private List<FixUpTask>	fixUpTask;

	//+
	private HandyWorker		worker;


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

	public String getPriceRange() {
		return this.priceRange;
	}

	public void setPriceRange(final String priceRange) {
		this.priceRange = priceRange;
	}

	public String getDateRange() {
		return this.dateRange;
	}

	public void setDateRange(final String dateRange) {
		this.dateRange = dateRange;
	}

	@OneToMany
	public List<FixUpTask> getFixUpTask() {
		return this.fixUpTask;
	}

	public void setFixUpTask(final List<FixUpTask> fixUpTask) {
		this.fixUpTask = fixUpTask;
	}

	@OneToOne
	public HandyWorker getWorker() {
		return this.worker;
	}

	public void setWorker(final HandyWorker worker) {
		this.worker = worker;
	}

	@Override
	public String toString() {
		return "Finder [warranty=" + this.warranty + ", keyWord=" + this.keyWord + ", category=" + this.category + ", priceRange=" + this.priceRange + ", dateRange=" + this.dateRange + ", fixUpTask=" + this.fixUpTask + "]";
	}

}
