
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.AdministratorRepository;
import domain.Administrator;

@Service
@Transactional
public class AdministratorService {

	//Managed repo
	@Autowired
	private AdministratorRepository	administratorRepository;


	//CRUDs

	public Administrator create() {
		//TODO: ?????
		return null;
	}

	public Collection<Administrator> findAll() {
		final Collection<Administrator> res = this.administratorRepository.findAll();
		return res;
	}

	public Administrator findOne(final int adminId) {
		final Administrator res = this.administratorRepository.findOne(adminId);
		return res;
	}

	public Administrator save(final Administrator admin) {
		final Administrator res = this.administratorRepository.save(admin);
		return res;
	}

	public void delete(final Administrator admin) {
		this.administratorRepository.delete(admin);
	}

	//Extra methods

	public Collection<Double> fixUpTaskPerUserStats() {
		final Collection<Double> res = this.administratorRepository.fixUpTaskPerUserStats();
		return res;
	}
}
