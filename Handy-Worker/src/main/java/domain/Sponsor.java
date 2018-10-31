
package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class Sponsor extends Actor {

	private List<Sponsorship>	sponsorship;


	public List<Sponsorship> getSponsorship() {
		return this.sponsorship;
	}

	public void setSponsorship(final List<Sponsorship> sponsorship) {
		this.sponsorship = sponsorship;
	}

	@Override
	public String toString() {
		return "Sponsor [sponsorship=" + this.sponsorship + "]";
	}

}
