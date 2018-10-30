
package domain;

import java.util.List;

import javax.persistence.Entity;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class Configuration extends DomainEntity {

	// Getters that returns lists are mutable 
	private int				cacheTime;

	private double			vat;

	private String			bannerURL;

	private String			welcomeEN;

	private String			welcomeSP;

	private List<String>	spamWords;

	private String			countryCode;

	private List<String>	positiveWords;

	private List<String>	negativeWords;

	private int				finderResults;


	@Min(1)
	@Max(24)
	public int getCacheTime() {
		return this.cacheTime;
	}

	public void setCacheTime(final int cacheTime) {
		this.cacheTime = cacheTime;
	}

	public double getVat() {
		return this.vat;
	}

	public void setVat(final double vat) {
		this.vat = vat;
	}
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

	public List<String> getSpamWords() {
		return this.spamWords;
	}

	public void setSpamWords(final List<String> spamWords) {
		this.spamWords = spamWords;
	}

	public String getCountryCode() {
		return this.countryCode;
	}

	public void setCountryCode(final String countryCode) {
		this.countryCode = countryCode;
	}

	public List<String> getPositiveWords() {
		return this.positiveWords;
	}

	public void setPositiveWords(final List<String> positiveWords) {
		this.positiveWords = positiveWords;
	}

	public List<String> getNegativeWords() {
		return this.negativeWords;
	}

	public void setNegativeWords(final List<String> negativeWords) {
		this.negativeWords = negativeWords;
	}
	@Max(100)
	public int getFinderResults() {
		return this.finderResults;
	}

	public void setFinderResults(final int finderResults) {
		this.finderResults = finderResults;
	}

	@Override
	public String toString() {
		return "Configuration [cacheTime=" + this.cacheTime + ", vat=" + this.vat + ", bannerURL=" + this.bannerURL + ", welcomeEN=" + this.welcomeEN + ", welcomeSP=" + this.welcomeSP + ", spamWords=" + this.spamWords + ", countryCode=" + this.countryCode
			+ ", positiveWords=" + this.positiveWords + ", negativeWords=" + this.negativeWords + ", finderResults=" + this.finderResults + "]";
	}

}
