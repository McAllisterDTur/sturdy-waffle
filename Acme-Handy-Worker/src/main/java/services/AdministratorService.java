
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.Authority;
import utilities.AuthenticationUtility;
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


	//CRUDs

	public Administrator create() {
		Assert.isTrue(AuthenticationUtility.checkAuthority(Authority.ADMIN));
		final Administrator a = new Administrator();
		a.setAccount(this.uaService.create());
		return a;
	}
	public Administrator save(final Administrator admin) {
		Assert.isTrue(AuthenticationUtility.checkAuthority(Authority.ADMIN));
		final Administrator res = this.administratorRepository.save(admin);
		return res;
	}

	public Collection<Administrator> findAll() {
		final Collection<Administrator> res = this.administratorRepository.findAll();
		return res;
	}

	public Administrator findOne(final int adminId) {
		final Administrator res = this.administratorRepository.findOne(adminId);
		return res;
	}
}
