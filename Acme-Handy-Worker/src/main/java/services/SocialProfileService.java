
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SocialProfileRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.SocialProfile;

@Service
@Transactional
public class SocialProfileService {

	//Managed repo
	@Autowired
	private SocialProfileRepository	spRepository;

	//Auxiliary services
	@Autowired
	private ActorService			actorService;


	//CRUDs
	public SocialProfile create() {
		return new SocialProfile();
	}

	public SocialProfile save(final SocialProfile sp) {
		final SocialProfile saved = this.spRepository.save(sp);
		return saved;
	}

	public Collection<SocialProfile> findAll() {
		return this.spRepository.findAll();
	}

	public SocialProfile findOne(final int spId) {
		return this.spRepository.findOne(spId);
	}

	public SocialProfile update(final SocialProfile sp) {
		final UserAccount ua = LoginService.getPrincipal();
		final Actor owner = this.actorService.findByUserAccountId(ua.getId());
		final SocialProfile toUpdate = this.spRepository.findOne(sp.getId());
		Assert.isTrue(owner.equals(sp.getActor()));
		toUpdate.setNick(sp.getNick());
		toUpdate.setProfileLink(sp.getProfileLink());
		toUpdate.setSocialNetwork(sp.getSocialNetwork());
		final SocialProfile updated = this.spRepository.save(toUpdate);
		return updated;
	}

	public void delete(final SocialProfile sp) {
		final UserAccount ua = LoginService.getPrincipal();
		final Actor owner = this.actorService.findByUserAccountId(ua.getId());
		final SocialProfile toUpdate = this.spRepository.findOne(sp.getId());
		Assert.isTrue(owner.equals(sp.getActor()));
		this.spRepository.delete(sp);
	}

	//Other methods
	public Collection<SocialProfile> findByActor(final int actorId) {
		return this.spRepository.findByActor(actorId);
	}
}
