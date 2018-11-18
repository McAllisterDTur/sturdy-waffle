package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Sponsorship extends DomainEntity {

	private String bannerURL;
	private String targetPageLink;
	private CreditCard creditCard;
	private Sponsor sponsor;

	@NotBlank
	@URL
	public String getBannerURL() {
		return this.bannerURL;
	}

	public void setBannerURL(final String bannerURL) {
		this.bannerURL = bannerURL;
	}

	@NotBlank
	@URL
	public String getTargetPageLink() {
		return this.targetPageLink;
	}

	public void setTargetPageLink(final String targetPageLink) {
		this.targetPageLink = targetPageLink;
	}

	@NotNull
	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Sponsor getSponsor() {
		return this.sponsor;
	}

	public void setSponsor(final Sponsor sponsor) {
		this.sponsor = sponsor;
	}

	@Override
	public String toString() {
		return "Sponsorship [bannerURL=" + bannerURL + ", targetPageLink="
				+ targetPageLink + ", creditCard=" + creditCard + ", sponsor="
				+ sponsor + "]";
	}

}
