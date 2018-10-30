package domain;

import javax.persistence.Embeddable;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

@Embeddable
public class CreditCard {
	
	//Constructor

	public CreditCard() {
		super();
	}
	
	//Atributes
	private String holderName;
	private String brandName;
	private String number;
	private int expirationMonth;
	private int expirationYear;
	private Integer codeCVV;

	@NotBlank
	public String getHolderName() {
		return holderName;
	}
	
	public void setHolderName(final String holderName) {
		this.holderName = holderName;
	}
	
	@NotBlank
	public String getBrandName() {
		return brandName;
	}
	
	public void setBrandName(final String brandName) {
		this.brandName = brandName;
	}
	
	@NotBlank
	@CreditCardNumber
	public String getNumber() {
		return number;
	}
	
	public void setNumber(final String number) {
		this.number = number;
	}
	
	@Range(min = 1, max = 12)
	@NotBlank
	public int getExpirationMonth() {
		return expirationMonth;
	}
	
	public void setExpirationMonth(final int expirationMonth) {
		this.expirationMonth = expirationMonth;
	}
	
	@Range(min = 2018, max = 10000)
	@NotBlank
	public int getExpirationYear() {
		return expirationYear;
	}
	
	public void setExpirationYear(final int expirationYear) {
		this.expirationYear = expirationYear;
	}
	
	@Range(min = 100, max = 999)
	@NotBlank
	public Integer getCodeCVV() {
		return codeCVV;
	}
	
	public void setCodeCVV(Integer codeCVV) {
		this.codeCVV = codeCVV;
	}

}
