
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class SocialProfile extends DomainEntity {

	private String	nick;
	private String	socialNetwork;
	private String	profileLink;
	private Actor	actor;


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

	public void setProfileLink(final String profileLink) {
		this.profileLink = profileLink;
	}

	@ManyToOne(optional = true)
	public Actor getActor() {
		return this.actor;
	}

	public void setActor(final Actor actor) {
		this.actor = actor;
	}

	@Override
	public String toString() {
		return "SocialProfile [nick=" + this.nick + ", socialNetwork=" + this.socialNetwork + ", profileLink=" + this.profileLink + ", actor=" + this.actor + "]";
	}

}
