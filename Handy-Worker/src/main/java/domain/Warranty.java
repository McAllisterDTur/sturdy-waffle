
package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Warranty extends DomainEntity {

	//Atributes
	private String			title;

	private String			terms;

	private List<String>	law;

	private Boolean			draft;


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
	public List<String> getLaw() {
		return this.law;
	}

	public void setLaw(final List<String> law) {
		this.law = law;
	}

	@NotBlank
	public Boolean isDraft() {
		return this.draft;
	}

	public void setDraft(final Boolean draft) {
		this.draft = draft;
	}

	@Override
	public String toString() {
		return "Warranty [title=" + this.title + ", terms=" + this.terms + ", law=" + this.law.toString() + ", draft=" + this.draft + "]";
	}

}
