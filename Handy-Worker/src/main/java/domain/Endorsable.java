
package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
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
	public String toString() {
		return "Endorsable [score=" + this.score + ", endorsements=" + this.endorsements + "]";
	}

}
