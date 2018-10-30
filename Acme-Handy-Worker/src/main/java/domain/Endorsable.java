
package domain;

import java.util.List;

import org.joda.time.DateTime;

public class Endorsable extends Actor {

	private DateTime		writeTime;
	private Double			score;
	private List<String>	comments;


	public DateTime getWriteTime() {
		return this.writeTime;
	}

	public void setWriteTime(final DateTime writeTime) {
		this.writeTime = writeTime;
	}

	public Double getScore() {
		//Puntuacion segun palabras en endorses
		//La idea sería:
		final Double res = 0.0;
		final Integer p = 0;
		final Integer n = 0;
		for (final String c : this.getComments()) {
			//Cogemos cada palabra de la lista de badwords y, si contiene una, n++
			//Cogemos cada palabra de la lista de goodwords y, si contiene una, p++
		}
		final Integer preRes = p - n;
		//Hay que normalizarlo, aka meterlo en el rango [-1.0, 1.0]
		return res;
	}

	public void setScore(final Double score) {
		this.score = score;
	}

	public List<String> getComments() {
		return this.comments;
	}

	public void setComments(final List<String> comments) {
		this.comments = comments;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.comments == null) ? 0 : this.comments.hashCode());
		result = prime * result + ((this.score == null) ? 0 : this.score.hashCode());
		result = prime * result + ((this.writeTime == null) ? 0 : this.writeTime.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		final Endorsable other = (Endorsable) obj;
		if (this.comments == null) {
			if (other.comments != null)
				return false;
		} else if (!this.comments.equals(other.comments))
			return false;
		if (this.score == null) {
			if (other.score != null)
				return false;
		} else if (!this.score.equals(other.score))
			return false;
		if (this.writeTime == null) {
			if (other.writeTime != null)
				return false;
		} else if (!this.writeTime.equals(other.writeTime))
			return false;
		return true;
	}

}
