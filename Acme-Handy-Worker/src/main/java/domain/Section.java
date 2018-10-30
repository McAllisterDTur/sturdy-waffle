package domain;

import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
public class Section  extends DomainEntity{

	//Constructor
	public Section() {
		super();
	}
	
	//Atributes
	private int number;

	private String title;

	private String text;

	private String[] photoURL;
	
	@NotBlank
	public int getNumber() {
		return number;
	}

	public void setNumber(final int number) {
		this.number = number;
	}
	
	@NotBlank
	public String getTitle() {
		return title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	public String getText() {
		return text;
	}

	public void setText(final String text) {
		this.text = text;
	}
	
	@URL
	public String[] getPhotoURL() {
		return photoURL;
	}

	public void setPhotoURL(final String[] photoURL) {
		this.photoURL = photoURL;
	}


}
