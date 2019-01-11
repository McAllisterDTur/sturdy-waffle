
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Tutorial extends DomainEntity {

	private String					title;
	private Date					lastTimeUpdated;
	private String					summary;
	private Collection<String>		photoURL;

	//+
	private HandyWorker				worker;
	private Collection<Sponsorship>	sponsorships;
	private Collection<Section>		sections;


	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotNull
	//@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getLastTimeUpdated() {
		return this.lastTimeUpdated;
	}

	public void setLastTimeUpdated(final Date lastTimeUpdated) {
		this.lastTimeUpdated = lastTimeUpdated;
	}

	@NotBlank
	public String getSummary() {
		return this.summary;
	}

	public void setSummary(final String summary) {
		this.summary = summary;
	}

	@ElementCollection
	public Collection<String> getPhotoURL() {
		return this.photoURL;
	}

	public void setPhotoURL(final Collection<String> photoURL) {
		this.photoURL = photoURL;
	}

	@NotNull
	@ManyToOne(optional = false)
	public HandyWorker getWorker() {
		return this.worker;
	}

	public void setWorker(final HandyWorker worker) {
		this.worker = worker;
	}

	@OneToMany
	public Collection<Sponsorship> getSponsorships() {
		return this.sponsorships;
	}

	public void setSponsorships(final Collection<Sponsorship> sponsorships) {
		this.sponsorships = sponsorships;
	}

	@OneToMany(cascade = CascadeType.ALL)
	public Collection<Section> getSections() {
		return this.sections;
	}

	public void setSections(final Collection<Section> sections) {
		this.sections = sections;
	}

	@Override
	public String toString() {
		return "Tutorial [title=" + this.title + ", lastTimeUpdated=" + this.lastTimeUpdated + ", summary=" + this.summary + ", photoURL=" + this.photoURL + "]";
	}

}
