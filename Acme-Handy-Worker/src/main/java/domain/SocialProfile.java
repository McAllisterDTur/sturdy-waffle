
package domain;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public class SocialProfile {

	private String	nick;
	private String	socialNetwork;
	private String	profileLink;


	@NotBlank
	public String getNick() {
		return this.nick;
	}

	public void setNick(final String nick) {
		this.nick = nick;
	}

	@NotBlank
	public String getSocialNetwork() {
		return this.socialNetwork;
	}

	public void setSocialNetwork(final String socialNetwork) {
		this.socialNetwork = socialNetwork;
	}

	@NotBlank
	@URL
	public String getProfileLink() {
		return this.profileLink;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.nick == null) ? 0 : this.nick.hashCode());
		result = prime * result + ((this.profileLink == null) ? 0 : this.profileLink.hashCode());
		result = prime * result + ((this.socialNetwork == null) ? 0 : this.socialNetwork.hashCode());
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
		final SocialProfile other = (SocialProfile) obj;
		if (this.nick == null) {
			if (other.nick != null)
				return false;
		} else if (!this.nick.equals(other.nick))
			return false;
		if (this.profileLink == null) {
			if (other.profileLink != null)
				return false;
		} else if (!this.profileLink.equals(other.profileLink))
			return false;
		if (this.socialNetwork == null) {
			if (other.socialNetwork != null)
				return false;
		} else if (!this.socialNetwork.equals(other.socialNetwork))
			return false;
		return true;
	}

}
