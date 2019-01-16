
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Configuration extends DomainEntity {

	// Getters that returns lists are mutable 
	private int					cacheTime;

	private double				vat;

	private String				bannerURL;

	private String				nameSystem;

	private String				welcomeEN;

	private String				welcomeSP;

	private Collection<String>	spamWords;

	private String				countryCode;

	private Collection<String>	positiveWords;

	private Collection<String>	negativeWords;

	private int					finderResults;

	private Collection<String>	cardMaker;


	@NotBlank
	public String getNameSystem() {
		return this.nameSystem;
	}

	public void setNameSystem(final String nameSystem) {
		this.nameSystem = nameSystem;
	}

	@Min(1)
	@Max(24)
	public int getCacheTime() {
		return this.cacheTime;
	}

	public void setCacheTime(final int cacheTime) {
		this.cacheTime = cacheTime;
	}

	@Min(0)
	public double getVat() {
		return this.vat;
	}

	public void setVat(final double vat) {
		this.vat = vat;
	}
	@URL
	@NotBlank
	public String getBannerURL() {
		return this.bannerURL;
	}

	public void setBannerURL(final String bannerURL) {
		this.bannerURL = bannerURL;
	}
	@NotBlank
	public String getWelcomeEN() {
		return this.welcomeEN;
	}

	public void setWelcomeEN(final String welcomeEN) {
		this.welcomeEN = welcomeEN;
	}
	@NotBlank
	public String getWelcomeSP() {
		return this.welcomeSP;
	}

	public void setWelcomeSP(final String welcomeSP) {
		this.welcomeSP = welcomeSP;
	}
	@ElementCollection
	@NotEmpty
	public Collection<String> getSpamWords() {
		return this.spamWords;
	}

	public void setSpamWords(final Collection<String> spamWords) {
		this.spamWords = spamWords;
	}
	@NotBlank
	public String getCountryCode() {
		return this.countryCode;
	}

	public void setCountryCode(final String countryCode) {
		this.countryCode = countryCode;
	}
	@ElementCollection
	@NotEmpty
	public Collection<String> getPositiveWords() {
		return this.positiveWords;
	}

	public void setPositiveWords(final Collection<String> positiveWords) {
		this.positiveWords = positiveWords;
	}
	@ElementCollection
	@NotEmpty
	public Collection<String> getNegativeWords() {
		return this.negativeWords;
	}

	public void setNegativeWords(final Collection<String> negativeWords) {
		this.negativeWords = negativeWords;
	}

	@Max(100)
	@Min(1)
	public int getFinderResults() {
		return this.finderResults;
	}

	public void setFinderResults(final int finderResults) {
		this.finderResults = finderResults;
	}

	@ElementCollection
	public Collection<String> getCardMaker() {
		return this.cardMaker;
	}

	public void setCardMaker(final Collection<String> cardMaker) {
		this.cardMaker = cardMaker;
	}

	@Override
	public String toString() {
		return "Configuration [cacheTime=" + this.cacheTime + ", vat=" + this.vat + ", bannerURL=" + this.bannerURL + ", welcomeEN=" + this.welcomeEN + ", welcomeSP=" + this.welcomeSP + ", spamWords=" + this.spamWords + ", countryCode=" + this.countryCode
			+ ", positiveWords=" + this.positiveWords + ", negativeWords=" + this.negativeWords + ", finderResults=" + this.finderResults + "]";
	}

}
