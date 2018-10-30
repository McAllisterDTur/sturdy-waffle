
package domain;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;

public class Phase extends DomainEntity {

	private String		title;

	private String		description;

	private DateTime	startTime;

	private DateTime	endTime;


	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}
	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}
	@NotNull
	public DateTime getStartTime() {
		return this.startTime;
	}

	public void setStartTime(final DateTime startTime) {
		this.startTime = startTime;
	}
	@NotNull
	public DateTime getEndTime() {
		return this.endTime;
	}

	public void setEndTime(final DateTime endTime) {
		this.endTime = endTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.description == null) ? 0 : this.description.hashCode());
		result = prime * result + ((this.endTime == null) ? 0 : this.endTime.hashCode());
		result = prime * result + ((this.startTime == null) ? 0 : this.startTime.hashCode());
		result = prime * result + ((this.title == null) ? 0 : this.title.hashCode());
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
		final Phase other = (Phase) obj;
		if (this.description == null) {
			if (other.description != null)
				return false;
		} else if (!this.description.equals(other.description))
			return false;
		if (this.endTime == null) {
			if (other.endTime != null)
				return false;
		} else if (!this.endTime.equals(other.endTime))
			return false;
		if (this.startTime == null) {
			if (other.startTime != null)
				return false;
		} else if (!this.startTime.equals(other.startTime))
			return false;
		if (this.title == null) {
			if (other.title != null)
				return false;
		} else if (!this.title.equals(other.title))
			return false;
		return true;
	}

}
