
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class Sponsor extends Actor {

	@Override
	public String toString() {
		return "Sponsor [getId()=" + this.getId() + "]";
	}

}
