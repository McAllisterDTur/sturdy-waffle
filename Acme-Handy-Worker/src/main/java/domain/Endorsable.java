
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Endorsable extends Actor {

	private Double	score;


	@NotNull
	public Double getScore() {
		return this.score;
	}

	public void setScore(final Double score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "Endorsable [score=" + this.score + "]";
	}

}
