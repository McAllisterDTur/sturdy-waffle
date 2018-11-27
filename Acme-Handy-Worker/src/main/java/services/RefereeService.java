
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RefereeRepository;
import security.Authority;
import security.LoginService;
import utilities.AuthenticationUtility;
import domain.Referee;

@Service
@Transactional
public class RefereeService {

	//Managed repository
	@Autowired
	RefereeRepository	refRepository;


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
		return this.refRepository.save(ref);
	}

	public Referee update(final Referee ref) {
		Assert.isTrue(AuthenticationUtility.checkAuthority(Authority.ADMIN) || ref.getAccount().equals(LoginService.getPrincipal()));
		final Referee toUpd = this.refRepository.findOne(ref.getId());
		toUpd.setAddress(ref.getAddress());
		toUpd.setBanned(ref.getBanned());
		toUpd.setEmail(ref.getEmail());
		toUpd.setMiddleName(ref.getMiddleName());
		toUpd.setName(ref.getName());
		toUpd.setPhone(ref.getPhone());
		toUpd.setPhotoURL(ref.getPhotoURL());
		toUpd.setSurname(ref.getSurname());
		return this.refRepository.save(toUpd);
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
}
