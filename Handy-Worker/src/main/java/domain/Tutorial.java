
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.joda.time.DateTime;

@Entity
@Access(AccessType.PROPERTY)
public class Tutorial extends DomainEntity {

	private String				title;
	private DateTime			lastTimeUpdated;
	private String				summary;
	private Collection<String>	photoURL;

	//+
	private HandyWorker			worker;
	private Sponsorship			sponsorship;


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
	@ElementCollection
	public Collection<String> getPhotoURL() {
		return this.photoURL;
	}

	public void setPhotoURL(final Collection<String> photoURL) {
		this.photoURL = photoURL;
	}

	@ManyToOne(optional = true)
	public HandyWorker getWorker() {
		return this.worker;
	}

	public void setWorker(final HandyWorker worker) {
		this.worker = worker;
	}

	@OneToOne
	public Sponsorship getSponsorship() {
		return this.sponsorship;
	}

	public void setSponsorship(final Sponsorship sponsorship) {
		this.sponsorship = sponsorship;
	}

	@Override
	public String toString() {
		return "Tutorial [title=" + this.title + ", lastTimeUpdated=" + this.lastTimeUpdated + ", summary=" + this.summary + ", photoURL=" + this.photoURL + "]";
	}

}
