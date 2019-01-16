
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RefereeRepository;
import security.Authority;
import security.UserAccount;
import utilities.AuthenticationUtility;
import domain.Actor;
import domain.Referee;

@Service
@Transactional
public class RefereeService {

	//Managed repository
	@Autowired
	RefereeRepository			refRepository;
	@Autowired
	private UserAccountService	userAccountService;
	@Autowired
	private BoxService			boxService;


	public Referee create() {
		Assert.isTrue(AuthenticationUtility.checkAuthority(Authority.ADMIN));

		return new Referee();
	}

	public Collection<Referee> findAll() {
		return this.refRepository.findAll();
	}

	public Referee findOne(final int refId) {
		return this.refRepository.findOne(refId);
	}

	public Referee save(final Referee ref) {
		Assert.notNull(ref);
		final Referee result;
		if (ref.getId() == 0) {
			final UserAccount account = ref.getAccount();
			final UserAccount savedAccount = this.userAccountService.save(account);
			ref.setAccount(savedAccount);
			result = this.refRepository.save(ref);
			this.boxService.initializeDefaultBoxes(result);
		} else
			result = this.refRepository.save(ref);
		return result;
	}

	public void ban(final Referee ref) {
		Assert.isTrue(AuthenticationUtility.checkAuthority(Authority.ADMIN));
		ref.setBanned(true);
		this.refRepository.save(ref);
	}
	public void unban(final Referee ref) {
		Assert.isTrue(AuthenticationUtility.checkAuthority(Authority.ADMIN));
		ref.setBanned(false);
		this.refRepository.save(ref);
	}

	public Referee actorToReferee(final Actor a) {
		final Referee res = new Referee();
		res.setAccount(a.getAccount());
		res.setAddress(a.getAddress());
		res.setBanned(a.getBanned());
		res.setEmail(a.getEmail());
		res.setId(a.getId());
		res.setIsSuspicious(a.getIsSuspicious());
		res.setMiddleName(a.getMiddleName());
		res.setName(a.getName());
		res.setPhone(a.getPhone());
		res.setPhotoURL(a.getPhotoURL());
		res.setSurname(a.getSurname());
		res.setVersion(a.getVersion());
		res.setIsSuspicious(false);
		return res;
	}
}
