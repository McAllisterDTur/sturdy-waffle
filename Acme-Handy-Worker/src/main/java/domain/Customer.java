package domain;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
public class Customer extends Endorsable {

	// Constructor

	public Customer() {
		super();
	}

	// Atributes

	// Relationships

	private Collection<FixUpTask> fixUpTask;

	@NotNull
	@Valid
	@OneToMany(mappedBy = "fixUpTask")
	public Collection<FixUpTask> getFixUpTask() {
		return fixUpTask;
	}

	public void setFixUpTask(final Collection<FixUpTask> fixUpTask) {
		this.fixUpTask = fixUpTask;
	}

}
