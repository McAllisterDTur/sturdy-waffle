
package domain;

import java.util.List;

public class Sponsor extends Actor {

	private List<Sponsorship>	sponsorship;


	public List<Sponsorship> getSponsorship() {
		return this.sponsorship;
	}

	public void setSponsorship(final List<Sponsorship> sponsorship) {
		this.sponsorship = sponsorship;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((this.sponsorship == null) ? 0 : this.sponsorship.hashCode());
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
		final Sponsor other = (Sponsor) obj;
		if (this.sponsorship == null) {
			if (other.sponsorship != null)
				return false;
		} else if (!this.sponsorship.equals(other.sponsorship))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Sponsor [sponsorship=" + this.sponsorship + "]";
	}

}
