
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


	//CRUDs

	public Administrator create() {
		Assert.isTrue(AuthenticationUtility.checkAuthority(Authority.ADMIN));
		return new Administrator();
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

	public Administrator update(final Administrator admin) {
		final Administrator toUpdate = this.administratorRepository.findOne(admin.getId());
		toUpdate.setBanned(admin.getBanned());
		toUpdate.setAddress(admin.getAddress());
		toUpdate.setEmail(admin.getEmail());
		toUpdate.setMiddleName(admin.getMiddleName());
		toUpdate.setName(admin.getName());
		toUpdate.setPhone(admin.getPhone());
		toUpdate.setPhotoURL(admin.getPhotoURL());
		toUpdate.setSurname(admin.getSurname());
		final Administrator updated = this.administratorRepository.save(toUpdate);
		return updated;
	}
}
