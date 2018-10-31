
package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.joda.time.DateTime;

@Entity
@Access(AccessType.PROPERTY)
public class Tutorial extends DomainEntity {

	private String			title;
	private DateTime		lastTimeUpdated;
	private String			summary;
	private List<String>	photoURL;


	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}
	@Past
	public DateTime getLastTimeUpdated() {
		return this.lastTimeUpdated;
	}

	public void setLastTimeUpdated(final DateTime lastTimeUpdated) {
		this.lastTimeUpdated = lastTimeUpdated;
	}
	@NotBlank
	public String getSummary() {
		return this.summary;
	}

	public void setSummary(final String summary) {
		this.summary = summary;
	}
	@URL
	public List<String> getPhotoURL() {
		return this.photoURL;
	}

	public void setPhotoURL(final List<String> photoURL) {
		this.photoURL = photoURL;
	}
	
	@Override
	public String toString() {
		return "Tutorial [title=" + this.title + ", lastTimeUpdated=" + this.lastTimeUpdated + ", summary=" + this.summary + ", photoURL=" + this.photoURL + "]";
	}

}
