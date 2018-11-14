package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Section extends DomainEntity {

	// Atributes
	private int number;
	private String title;
	private String text;
	private Collection<String> photoURL;
	private Tutorial tutorial;

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

	@ElementCollection
	public Collection<String> getPhotoURL() {
		return this.photoURL;
	}

	public void setPhotoURL(final Collection<String> photoURL) {
		this.photoURL = photoURL;
	}

	@NotNull
	@ManyToOne(optional=false)
	public Tutorial getTutorial() {
		return tutorial;
	}

	public void setTutorial(Tutorial tutorial) {
		this.tutorial = tutorial;
	}

	@Override
	public String toString() {
		return "Section [number=" + number + ", title=" + title + ", text="
				+ text + ", photoURL=" + photoURL + ", tutorial=" + tutorial
				+ "]";
	}

}
