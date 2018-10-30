
package domain;

import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class Referee extends Actor {

	//Atributes

	//Relationships
	private Complaint	complaint;


	@NotNull
	@Valid
	@OneToMany(mappedBy = "complaint")
	public Complaint getComplaint() {
		return this.complaint;
	}

	public void setComplaint(final Complaint complaint) {
		this.complaint = complaint;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((this.complaint == null) ? 0 : this.complaint.hashCode());
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
		final Referee other = (Referee) obj;
		if (this.complaint == null) {
			if (other.complaint != null)
				return false;
		} else if (!this.complaint.equals(other.complaint))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Referee [complaint=" + this.complaint + "]";
	}

}
