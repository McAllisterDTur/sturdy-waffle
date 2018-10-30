
package domain;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;

public class Money {

	//Attributes
	private Double	amount;
	private String	currency;


	public Double getAmount() {
		return this.amount;
	}

	@Min(0)
	@Digits(fraction = 2, integer = 9)
	public void setAmount(final Double amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return this.currency;
	}

	@NotBlank
	public void setCurrency(final String currency) {
		this.currency = currency;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.amount == null) ? 0 : this.amount.hashCode());
		result = prime * result + ((this.currency == null) ? 0 : this.currency.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Money))
			return false;
		final Money other = (Money) obj;
		if (this.amount == null) {
			if (other.amount != null)
				return false;
		} else if (!this.amount.equals(other.amount))
			return false;
		if (this.currency == null) {
			if (other.currency != null)
				return false;
		} else if (!this.currency.equals(other.currency))
			return false;
		return true;
	}

}
