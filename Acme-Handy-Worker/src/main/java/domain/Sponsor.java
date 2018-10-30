
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

}
