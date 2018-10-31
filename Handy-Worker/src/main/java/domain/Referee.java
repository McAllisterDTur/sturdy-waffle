
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class Referee extends Actor {

	//Atributes

	//Relationships
	private Complaint	complaint;

	public Complaint getComplaint() {
		return this.complaint;
	}

	public void setComplaint(final Complaint complaint) {
		this.complaint = complaint;
	}

	@Override
	public String toString() {
		return "Referee [complaint=" + this.complaint + "]";
	}

}
