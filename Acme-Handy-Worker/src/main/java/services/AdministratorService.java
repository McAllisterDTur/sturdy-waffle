
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.Authority;
import security.UserAccount;
import utilities.AuthenticationUtility;
import domain.Actor;
import domain.Administrator;

@Service
@Transactional
public class AdministratorService {

	//Managed repo
	@Autowired
	private AdministratorRepository	administratorRepository;
	//Auxiliary services
	@Autowired
	private UserAccountService		uaService;
	@Autowired
	private BoxService				boxService;
	@Autowired
	private UserAccountService		userAccountService;


	//CRUDs

	public Administrator create() {
		Assert.isTrue(AuthenticationUtility.checkAuthority(Authority.ADMIN));
		final Administrator a = new Administrator();
		a.setAccount(this.uaService.create());
		return a;
	}
	public Administrator save(final Administrator admin) {
		Assert.isTrue(AuthenticationUtility.checkAuthority(Authority.ADMIN));
		final Administrator result;
		Assert.notNull(admin);
		if (admin.getId() == 0) {
			final UserAccount account = admin.getAccount();
			final UserAccount savedAccount = this.userAccountService.save(account);
			admin.setAccount(savedAccount);
			result = this.administratorRepository.save(admin);
			this.boxService.initializeDefaultBoxes(result);
		} else
			result = this.administratorRepository.save(admin);
		return result;
	}

	public Collection<Administrator> findAll() {
		final Collection<Administrator> res = this.administratorRepository.findAll();
		return res;
	}

	public Administrator findOne(final int adminId) {
		final Administrator res = this.administratorRepository.findOne(adminId);
		return res;
	}
	public Administrator actorToAdmin(final Actor a) {
		final Administrator res = new Administrator();
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
