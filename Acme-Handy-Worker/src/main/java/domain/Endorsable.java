
package domain;

import java.util.List;

public class Endorsable extends Actor {

	private Double				score;
	private List<Endorsement>	endorsements;


	public Double getScore() {
		return this.score;
	}

	public void setScore(final Double score) {
		this.score = score;
	}

	public List<Endorsement> getEndorsements() {
		return this.endorsements;
	}

	public void setEndorsements(final List<Endorsement> endorsements) {
		this.endorsements = endorsements;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((this.endorsements == null) ? 0 : this.endorsements.hashCode());
		result = prime * result + ((this.score == null) ? 0 : this.score.hashCode());
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
		final Endorsable other = (Endorsable) obj;
		if (this.endorsements == null) {
			if (other.endorsements != null)
				return false;
		} else if (!this.endorsements.equals(other.endorsements))
			return false;
		if (this.score == null) {
			if (other.score != null)
				return false;
		} else if (!this.score.equals(other.score))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Endorsable [score=" + this.score + ", endorsements=" + this.endorsements + "]";
	}

}
