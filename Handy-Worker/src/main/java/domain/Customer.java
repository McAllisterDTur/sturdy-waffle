
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
@Access(AccessType.PROPERTY)
public class Customer extends Endorsable {

	// Atributes

	// Relationships

	private Collection<FixUpTask>	fixUpTask;


	@OneToMany
	public Collection<FixUpTask> getFixUpTask() {
		return this.fixUpTask;
	}

	public void setFixUpTask(final Collection<FixUpTask> fixUpTask) {
		this.fixUpTask = fixUpTask;
	}

	@Override
	public String toString() {
		return "Customer [" + super.toString() + "fixUpTask=" + this.fixUpTask + "]";
	}

}
