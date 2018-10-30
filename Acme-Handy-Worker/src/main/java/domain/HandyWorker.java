
package domain;

import org.hibernate.validator.constraints.NotBlank;

public class HandyWorker extends Actor {

	private String	make;


	@NotBlank
	public String getMake() {
		return this.make;
	}

	public void setMake(final String make) {
		this.make = make;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((this.make == null) ? 0 : this.make.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof HandyWorker))
			return false;
		final HandyWorker other = (HandyWorker) obj;
		if (this.make == null) {
			if (other.make != null)
				return false;
		} else if (!this.make.equals(other.make))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "HandyWorker [" + super.toString() + "make=" + this.make + "]";
	}

}
