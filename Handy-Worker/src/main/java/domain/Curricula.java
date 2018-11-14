
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Curricula extends DomainEntity {

	// Atributes
	private String							ticker;

	// Relationships
	private HandyWorker					handyWorker;

	@Column(unique = true)
	@NotBlank
	@Pattern(regexp = "(\\d{2})(([0][1-9])|([1][0-2]))(([0][1-9])|[1-2][0-9]|[3][0-1])-(([A-Z]|[0-9]){6})$")
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	@NotNull
	@OneToOne(optional=false)
	public HandyWorker getHandyWorker() {
		return handyWorker;
	}

	public void setHandyWorker(HandyWorker handyWorker) {
		this.handyWorker = handyWorker;
	}

	@Override
	public String toString() {
		return "Curricula [ticker=" + ticker + ", handyWorker=" + handyWorker + "]";
	}

}
