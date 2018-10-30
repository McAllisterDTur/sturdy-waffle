package domain;

import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class Warranty extends DomainEntity{

	//Constructor
	public Warranty() {
		super();
	}
	
	//Atributes
	private String title;

	private String terms;

	private String[] law;

	private boolean draft;

	@NotBlank
	public String getTitle() {
		return title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}
	
	@NotBlank
	public String getTerms() {
		return terms;
	}

	public void setTerms(final String terms) {
		this.terms = terms;
	}
	
	@NotBlank
	public String[] getLaw() {
		return law;
	}

	public void setLaw(final String[] law) {
		this.law = law;
	}
	
	@NotBlank
	public boolean isDraft() {
		return draft;
	}

	public void setDraft(final boolean draft) {
		this.draft = draft;
	}

}
