
package domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Box extends DomainEntity {

	private String			name;

	private Boolean			deleteable;

	private List<Message>	messages;


	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public boolean isDeleteable() {
		return this.deleteable;
	}

	public void setDeleteable(final boolean deleteable) {
		this.deleteable = deleteable;
	}

	public List<Message> getMessages() {
		return new ArrayList<Message>(this.messages);
	}

	public void setMessages(final List<Message> messages) {
		this.messages = new ArrayList<Message>(messages);
	}

	@Override
	public String toString() {
		return "Box [name=" + this.name + ", deleteable=" + this.deleteable + ", messages=" + this.messages + "]";
	}

}
