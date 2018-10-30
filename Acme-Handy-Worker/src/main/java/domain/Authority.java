
package domain;

import org.hibernate.validator.constraints.NotBlank;

public class Authority {

	public static String	ADMIN		= "ADMIN";
	public static String	CUSTOMER	= "CUSTOMER";
	public static String	HANDYWORKER	= "HANDYWORKER";
	public static String	REFEREE		= "REFEREE";
	public static String	SPONSOR		= "SPONSOR";

	private String			authority;


	public String getAuthority() {
		return this.authority;
	}

	@NotBlank
	public void setAuthority(final String authority) {
		this.authority = authority;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.authority == null) ? 0 : this.authority.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Authority))
			return false;
		final Authority other = (Authority) obj;
		if (this.authority == null) {
			if (other.authority != null)
				return false;
		} else if (!this.authority.equals(other.authority))
			return false;
		return true;
	}

}
