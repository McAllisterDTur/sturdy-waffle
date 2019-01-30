
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Phase extends DomainEntity {

	private String		title;

	private String		description;

	private Date		startTime;

	private Date		endTime;

	// +
	private Application	application;


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
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(final Date startTime) {
		this.startTime = startTime;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(final Date endTime) {
		this.endTime = endTime;
	}

	@ManyToOne(optional = false)
	public Application getApplication() {
		return this.application;
	}

	public void setApplication(final Application application) {
		this.application = application;
	}

	@Override
	public String toString() {
		return "Phase [ID=" + this.getId() + "title=" + this.title + ", description=" + this.description + ", startTime=" + this.startTime + ", endTime=" + this.endTime + "]";
	}

}
