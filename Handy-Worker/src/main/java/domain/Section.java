
package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
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
	public String toString() {
		return "Section [number=" + this.number + ", title=" + this.title + ", text=" + this.text + ", photoURL=" + this.photoURL + "]";
	}

}
