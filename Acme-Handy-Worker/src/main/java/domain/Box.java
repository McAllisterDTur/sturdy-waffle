
package domain;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.NotBlank;

public class Box extends DomainEntity {

	private String			name;

	private boolean			deleteable;

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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (this.deleteable ? 1231 : 1237);
		result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
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
		final Box other = (Box) obj;
		if (this.deleteable != other.deleteable)
			return false;
		if (this.name == null) {
			if (other.name != null)
				return false;
		} else if (!this.name.equals(other.name))
			return false;
		return true;
	}

}
