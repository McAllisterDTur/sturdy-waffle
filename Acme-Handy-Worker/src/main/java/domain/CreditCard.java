
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

@Embeddable
@Access(AccessType.PROPERTY)
public class CreditCard {

	//Atributes
	private String	holderName;
	private String	brandName;
	private String	number;
	private int		expirationMonth;
	private int		expirationYear;
	private Integer	codeCVV;


	@NotBlank
	public String getHolderName() {
		return this.holderName;
	}

	public void setHolderName(final String holderName) {
		this.holderName = holderName;
	}

	@NotBlank
	public String getBrandName() {
		return this.brandName;
	}

	public void setBrandName(final String brandName) {
		this.brandName = brandName;
	}

	@NotBlank
	@CreditCardNumber
	public String getNumber() {
		return this.number;
	}

	public void setNumber(final String number) {
		this.number = number;
	}

	@Range(min = 1, max = 12)
	@NotBlank
	public int getExpirationMonth() {
		return this.expirationMonth;
	}

	public void setExpirationMonth(final int expirationMonth) {
		this.expirationMonth = expirationMonth;
	}

	@Range(min = 2018, max = 10000)
	@NotBlank
	public int getExpirationYear() {
		return this.expirationYear;
	}

	public void setExpirationYear(final int expirationYear) {
		this.expirationYear = expirationYear;
	}

	@Range(min = 100, max = 999)
	@NotBlank
	public Integer getCodeCVV() {
		return this.codeCVV;
	}

	@NotNull
	public void setCodeCVV(final Integer codeCVV) {
		this.codeCVV = codeCVV;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.brandName == null) ? 0 : this.brandName.hashCode());
		result = prime * result + ((this.codeCVV == null) ? 0 : this.codeCVV.hashCode());
		result = prime * result + this.expirationMonth;
		result = prime * result + this.expirationYear;
		result = prime * result + ((this.holderName == null) ? 0 : this.holderName.hashCode());
		result = prime * result + ((this.number == null) ? 0 : this.number.hashCode());
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
		final CreditCard other = (CreditCard) obj;
		if (this.brandName == null) {
			if (other.brandName != null)
				return false;
		} else if (!this.brandName.equals(other.brandName))
			return false;
		if (this.codeCVV == null) {
			if (other.codeCVV != null)
				return false;
		} else if (!this.codeCVV.equals(other.codeCVV))
			return false;
		if (this.expirationMonth != other.expirationMonth)
			return false;
		if (this.expirationYear != other.expirationYear)
			return false;
		if (this.holderName == null) {
			if (other.holderName != null)
				return false;
		} else if (!this.holderName.equals(other.holderName))
			return false;
		if (this.number == null) {
			if (other.number != null)
				return false;
		} else if (!this.number.equals(other.number))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CreditCard [holderName=" + this.holderName + ", brandName=" + this.brandName + ", number=" + this.number + ", expirationMonth=" + this.expirationMonth + ", expirationYear=" + this.expirationYear + ", codeCVV=" + this.codeCVV + "]";
	}

}
