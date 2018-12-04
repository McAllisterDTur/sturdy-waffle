
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ConfigurationRepository;
import security.Authority;
import utilities.AuthenticationUtility;
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
		Assert.isTrue(AuthenticationUtility.checkAuthority(Authority.ADMIN));

		final Configuration configuration = new Configuration();

		final Collection<String> cardMaker = new ArrayList<>();
		final Collection<String> negativeWords = new ArrayList<>();
		final Collection<String> positiveWords = new ArrayList<>();
		final Collection<String> spamWords = new ArrayList<>();
		configuration.setCardMaker(cardMaker);
		configuration.setNegativeWords(negativeWords);
		configuration.setPositiveWords(positiveWords);
		configuration.setSpamWords(spamWords);

		return configuration;
	}

	/**
	 * Admin gets all configuration from DB
	 * 
	 * @return Collection<Configuration>
	 */
	public Collection<Configuration> findAll() {
		final Collection<Configuration> result = this.configurationRepository.findAll();
		return result;
	}

	/**
	 * Admin saves a configuration to DB
	 * 
	 * @param configuration
	 * @return Configuration
	 */
	public Configuration save(final Configuration configuration) {
		Assert.isTrue(AuthenticationUtility.checkAuthority(Authority.ADMIN));
		Configuration result = null;
		Assert.notNull(configuration);
		final Collection<Configuration> config = this.findAll();

		//comprobar que la modificada sea la que está en bd
		if (configuration.getId() != 0) {
			Assert.isTrue(configuration.getId() == config.iterator().next().getId());
			result = this.configurationRepository.save(configuration);
		} else {
			Assert.isTrue(config.isEmpty());
			result = this.configurationRepository.save(configuration);
		}
		return result;
	}

	/**
	 * Admin deletes a configuration from DB
	 * with an especific Id
	 * 
	 * @param configuration
	 */
	public void delete(final Configuration configuration) {
		Assert.isTrue(AuthenticationUtility.checkAuthority(Authority.ADMIN));
		Assert.notNull(configuration);
		this.configurationRepository.delete(configuration);
	}

}
