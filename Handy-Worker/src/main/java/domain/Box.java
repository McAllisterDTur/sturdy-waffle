
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Box extends DomainEntity {

	private String				name;

	private boolean				deleteable;

	private Collection<Message>	messages;

	//+
	private Actor				owner;


	//private Collection<Message> messages;

	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setDeleteable(final boolean deleteable) {
		this.deleteable = deleteable;
	}

	@ManyToMany
	public Collection<Message> getMessages() {
		return this.messages;
	}

	public void setMessages(final Collection<Message> messages) {
		this.messages = messages;
	}

	@NotBlank
	public boolean getDeleteable() {
		return this.deleteable;
	}

	@ManyToOne
	public Actor getOwner() {
		return this.owner;
	}

	public void setOwner(final Actor owner) {
		this.owner = owner;
	}

	@Override
	public String toString() {
		return "Box [name=" + this.name + ", deleteable=" + this.deleteable + ", messages=" + this.messages + "]";
	}

}
