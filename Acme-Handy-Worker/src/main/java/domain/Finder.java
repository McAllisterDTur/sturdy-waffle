
package domain;

import java.util.List;

public class Finder {

	private String			searchString;
	private String			warranty;
	private String			keyWord;
	private String			category;
	private String			priceRange;
	private String			dateRange;
	private List<FixUpTask>	fixUpTask;


	public Finder(final String searchString, final String warranty, final String keyWord, final String category, final String priceRange, final String dateRange, final List<FixUpTask> fixUpTask) {
		super();
		this.searchString = searchString;
		this.warranty = warranty;
		this.keyWord = keyWord;
		this.category = category;
		this.priceRange = priceRange;
		this.dateRange = dateRange;
		this.fixUpTask = fixUpTask;
	}

	public String getSearchString() {
		return this.searchString;
	}

	public void setSearchString(final String searchString) {
		this.searchString = searchString;
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

	public List<FixUpTask> getFixUpTask() {
		return this.fixUpTask;
	}

	public void setFixUpTask(final List<FixUpTask> fixUpTask) {
		this.fixUpTask = fixUpTask;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.category == null) ? 0 : this.category.hashCode());
		result = prime * result + ((this.dateRange == null) ? 0 : this.dateRange.hashCode());
		result = prime * result + ((this.keyWord == null) ? 0 : this.keyWord.hashCode());
		result = prime * result + ((this.priceRange == null) ? 0 : this.priceRange.hashCode());
		result = prime * result + ((this.searchString == null) ? 0 : this.searchString.hashCode());
		result = prime * result + ((this.warranty == null) ? 0 : this.warranty.hashCode());
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
		final Finder other = (Finder) obj;
		if (this.category == null) {
			if (other.category != null)
				return false;
		} else if (!this.category.equals(other.category))
			return false;
		if (this.dateRange == null) {
			if (other.dateRange != null)
				return false;
		} else if (!this.dateRange.equals(other.dateRange))
			return false;
		if (this.keyWord == null) {
			if (other.keyWord != null)
				return false;
		} else if (!this.keyWord.equals(other.keyWord))
			return false;
		if (this.priceRange == null) {
			if (other.priceRange != null)
				return false;
		} else if (!this.priceRange.equals(other.priceRange))
			return false;
		if (this.searchString == null) {
			if (other.searchString != null)
				return false;
		} else if (!this.searchString.equals(other.searchString))
			return false;
		if (this.warranty == null) {
			if (other.warranty != null)
				return false;
		} else if (!this.warranty.equals(other.warranty))
			return false;
		return true;
	}

}
