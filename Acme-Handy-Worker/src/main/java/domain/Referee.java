
package domain;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
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

}
