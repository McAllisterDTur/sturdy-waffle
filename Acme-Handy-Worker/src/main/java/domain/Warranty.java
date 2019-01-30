
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Access(AccessType.PROPERTY)
public class Warranty extends DomainEntity {

	//Atributes
	private String				title;
	private String				terms;
	private Collection<String>	law;
	private Boolean				draft;


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

	@NotNull
	@NotEmpty
	@ElementCollection(fetch = FetchType.EAGER)
	public Collection<String> getLaw() {
		return this.law;
	}

	public void setLaw(final Collection<String> law) {
		this.law = law;
	}

	@NotNull
	public Boolean getDraft() {
		return this.draft;
	}

	public void setDraft(final Boolean draft) {
		this.draft = draft;
	}

	@Override
	public String toString() {
		return this.title + ": " + this.terms + ". " + this.law.toString();
	}

}
