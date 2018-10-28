
package domain;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public class Sponsorship {

	@NotBlank
	@URL
	public String		bannerURL;
	@NotBlank
	@URL
	public String		targetPageLink;
	@NotNull
	public CreditCard	creditCard;
	@NotNull
	private Sponsor		sponsor;
	@NotNull
	private Tutorial	tutorial;


	public Sponsorship(final String bannerURL, final String targetPageLink, final CreditCard creditCard, final Sponsor sponsor, final Tutorial tutorial) {
		super();
		this.bannerURL = bannerURL;
		this.targetPageLink = targetPageLink;
		this.creditCard = creditCard;
		this.sponsor = sponsor;
		this.tutorial = tutorial;
	}

	public String getBannerURL() {
		return this.bannerURL;
	}

	public void setBannerURL(final String bannerURL) {
		this.bannerURL = bannerURL;
	}

	public String getTargetPageLink() {
		return this.targetPageLink;
	}

	public void setTargetPageLink(final String targetPageLink) {
		this.targetPageLink = targetPageLink;
	}

	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	public Sponsor getSponsor() {
		return this.sponsor;
	}

	public void setSponsor(final Sponsor sponsor) {
		this.sponsor = sponsor;
	}

	public Tutorial getTutorial() {
		return this.tutorial;
	}

	public void setTutorial(final Tutorial tutorial) {
		this.tutorial = tutorial;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.bannerURL == null) ? 0 : this.bannerURL.hashCode());
		result = prime * result + ((this.sponsor == null) ? 0 : this.sponsor.hashCode());
		result = prime * result + ((this.targetPageLink == null) ? 0 : this.targetPageLink.hashCode());
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
		final Sponsorship other = (Sponsorship) obj;
		if (this.bannerURL == null) {
			if (other.bannerURL != null)
				return false;
		} else if (!this.bannerURL.equals(other.bannerURL))
			return false;
		if (this.sponsor == null) {
			if (other.sponsor != null)
				return false;
		} else if (!this.sponsor.equals(other.sponsor))
			return false;
		if (this.targetPageLink == null) {
			if (other.targetPageLink != null)
				return false;
		} else if (!this.targetPageLink.equals(other.targetPageLink))
			return false;
		return true;
	}

}
