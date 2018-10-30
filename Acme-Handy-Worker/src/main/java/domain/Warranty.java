
package domain;

import java.util.List;

import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;

@Entity
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
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (this.draft ? 1231 : 1237);
		result = prime * result + ((this.law == null) ? 0 : this.law.hashCode());
		result = prime * result + ((this.terms == null) ? 0 : this.terms.hashCode());
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
		final Warranty other = (Warranty) obj;
		if (this.draft != other.draft)
			return false;
		if (this.law != other.law)
			return false;
		if (this.terms == null) {
			if (other.terms != null)
				return false;
		} else if (!this.terms.equals(other.terms))
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
		return "Warranty [title=" + this.title + ", terms=" + this.terms + ", law=" + this.law.toString() + ", draft=" + this.draft + "]";
	}

}
