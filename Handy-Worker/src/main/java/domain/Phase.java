
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;

@Entity
@Access(AccessType.PROPERTY)
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
	public String toString() {
		return "Phase [title=" + this.title + ", description=" + this.description + ", startTime=" + this.startTime + ", endTime=" + this.endTime + "]";
	}

}
