
package domain;

import javax.persistence.Embeddable;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;

@Embeddable
public class Money {

	//Attributes
	private Double	amount;
	private String	currency;


	@Min(0)
	@Digits(fraction = 2, integer = 9)
	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(final Double amount) {
		this.amount = amount;
	}

	@NotBlank
	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(final String currency) {
		this.currency = currency;
	}

	@Override
	public String toString() {
		return "Money [amount=" + this.amount + ", currency=" + this.currency + "]";
	}

}
