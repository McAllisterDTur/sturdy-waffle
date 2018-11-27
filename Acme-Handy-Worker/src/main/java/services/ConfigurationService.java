
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ConfigurationRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Configuration;

@Service
@Transactional
public class ConfigurationService {

	@Autowired
	private ConfigurationRepository	configurationRepository;


	/**
	 * Admin creates a empty configuration
	 * 
	 * @return configuration
	 */
	public Configuration create() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		System.out.println(userAccount.getAuthorities());
		// Only the admin can save a configuration	
		final Authority a = new Authority();
		a.setAuthority(Authority.ADMIN);
		System.out.println(userAccount.getAuthorities().contains(a));
		Assert.isTrue(userAccount.getAuthorities().contains(a));
		final Configuration configuration = new Configuration();
		return configuration;
	}

	/**
	 * Admin gets all configuration from DB
	 * 
	 * @return Collection<Configuration>
	 */
	public Collection<Configuration> findAll() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		System.out.println(userAccount.getAuthorities());
		// Only the admin can save a configuration	
		final Authority a = new Authority();
		a.setAuthority(Authority.ADMIN);
		System.out.println(userAccount.getAuthorities().contains(a));
		final Collection<Configuration> result = this.configurationRepository.findAll();
		return result;
	}

	/**
	 * Admin get a configuration from DB
	 * with an especific id
	 * 
	 * @param id
	 * @return Configuration
	 */
	public Configuration findOne(final int id) {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		System.out.println(userAccount.getAuthorities());
		// Only the admin can save a configuration	
		final Authority a = new Authority();
		a.setAuthority(Authority.ADMIN);
		System.out.println(userAccount.getAuthorities().contains(a));
		Assert.isTrue(userAccount.getAuthorities().contains(a));
		final Configuration result = this.configurationRepository.findOne(id);
		return result;
	}

	/**
	 * Admin saves a configuration to DB
	 * 
	 * @param configuration
	 * @return Configuration
	 */
	public Configuration save(final Configuration configuration) {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		System.out.println(userAccount.getAuthorities());
		// Only the admin can save a configuration	
		final Authority a = new Authority();
		a.setAuthority(Authority.ADMIN);
		System.out.println(userAccount.getAuthorities().contains(a));
		Assert.isTrue(userAccount.getAuthorities().contains(a));
		Assert.notNull(configuration);
		final Configuration result = this.configurationRepository.save(configuration);
		return result;
	}

	/**
	 * Admin deletes a configuration from DB
	 * with an especific Id
	 * 
	 * @param configuration
	 */
	public void delete(final Configuration configuration) {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		System.out.println(userAccount.getAuthorities());
		// Only the admin can save a configuration	
		final Authority a = new Authority();
		a.setAuthority(Authority.ADMIN);
		System.out.println(userAccount.getAuthorities().contains(a));
		Assert.isTrue(userAccount.getAuthorities().contains(a));
		this.configurationRepository.delete(configuration);
	}

}
