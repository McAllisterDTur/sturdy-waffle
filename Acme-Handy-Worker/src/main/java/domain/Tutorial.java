
package domain;

import java.util.List;

import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.joda.time.DateTime;

public class Tutorial {

	private String			title;
	private DateTime		lastTimeUpdated;
	private String			summary;
	private List<String>	photoURL;


	public String getTitle() {
		return this.title;
	}
	@NotBlank
	public void setTitle(final String title) {
		this.title = title;
	}

	public DateTime getLastTimeUpdated() {
		return this.lastTimeUpdated;
	}
	@Past
	public void setLastTimeUpdated(final DateTime lastTimeUpdated) {
		this.lastTimeUpdated = lastTimeUpdated;
	}

	public String getSummary() {
		return this.summary;
	}
	@NotBlank
	public void setSummary(final String summary) {
		this.summary = summary;
	}

	public List<String> getPhotoURL() {
		return this.photoURL;
	}
	@URL
	public void setPhotoURL(final List<String> photoURL) {
		this.photoURL = photoURL;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.lastTimeUpdated == null) ? 0 : this.lastTimeUpdated.hashCode());
		result = prime * result + ((this.photoURL == null) ? 0 : this.photoURL.hashCode());
		result = prime * result + ((this.summary == null) ? 0 : this.summary.hashCode());
		result = prime * result + ((this.title == null) ? 0 : this.title.hashCode());
		return result;
	}
	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Tutorial))
			return false;
		final Tutorial other = (Tutorial) obj;
		if (this.lastTimeUpdated == null) {
			if (other.lastTimeUpdated != null)
				return false;
		} else if (!this.lastTimeUpdated.equals(other.lastTimeUpdated))
			return false;
		if (this.photoURL == null) {
			if (other.photoURL != null)
				return false;
		} else if (!this.photoURL.equals(other.photoURL))
			return false;
		if (this.summary == null) {
			if (other.summary != null)
				return false;
		} else if (!this.summary.equals(other.summary))
			return false;
		if (this.title == null) {
			if (other.title != null)
				return false;
		} else if (!this.title.equals(other.title))
			return false;
		return true;
	}

}
