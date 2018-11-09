
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Warranty extends DomainEntity {

	//Atributes
	private String				title;

	private String				terms;

	private Collection<String>	law;

	private boolean				draft;


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
	@ElementCollection
	public Collection<String> getLaw() {
		return this.law;
	}

	public void setLaw(final Collection<String> law) {
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
