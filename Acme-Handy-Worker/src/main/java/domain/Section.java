
package domain;

import java.util.List;

import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
public class Section extends DomainEntity {

	//Atributes
	private int				number;

	private String			title;

	private String			text;

	private List<String>	photoURL;


	@NotBlank
	public int getNumber() {
		return this.number;
	}

	public void setNumber(final int number) {
		this.number = number;
	}

	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	public String getText() {
		return this.text;
	}

	public void setText(final String text) {
		this.text = text;
	}

	@URL
	public List<String> getPhotoURL() {
		return this.photoURL;
	}

	public void setPhotoURL(final List<String> photoURL) {
		this.photoURL = photoURL;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + this.number;
		result = prime * result + ((this.photoURL == null) ? 0 : this.photoURL.hashCode());
		result = prime * result + ((this.text == null) ? 0 : this.text.hashCode());
		result = prime * result + ((this.title == null) ? 0 : this.title.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		final Section other = (Section) obj;
		if (this.number != other.number)
			return false;
		if (this.photoURL == null) {
			if (other.photoURL != null)
				return false;
		} else if (!this.photoURL.equals(other.photoURL))
			return false;
		if (this.text == null) {
			if (other.text != null)
				return false;
		} else if (!this.text.equals(other.text))
			return false;
		if (this.title == null) {
			if (other.title != null)
				return false;
		} else if (!this.title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Section [number=" + this.number + ", title=" + this.title + ", text=" + this.text + ", photoURL=" + this.photoURL + "]";
	}

}
