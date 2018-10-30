
package domain;

import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class Warranty extends DomainEntity {

	//Atributes
	private String		title;

	private String		terms;

	private String[]	law;

	private boolean		draft;


	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	public String getTerms() {
		return this.terms;
	}

	public void setTerms(final String terms) {
		this.terms = terms;
	}

	@NotBlank
	public String[] getLaw() {
		return this.law;
	}

	public void setLaw(final String[] law) {
		this.law = law;
	}

	@NotBlank
	public boolean isDraft() {
		return this.draft;
	}

	public void setDraft(final boolean draft) {
		this.draft = draft;
	}

}
