
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import security.UserAccount;
import security.UserAccountRepository;

@Service
@Transactional
public class UserAccountService {

	@Autowired
	private UserAccountRepository	repo;


	public UserAccount create() {
		return new UserAccount();
	}

	public UserAccount save(final UserAccount user) {
		Assert.notNull(user);

		//TODO: comprobar que solo existe un authority para el account

		return this.repo.save(user);
	}

	public UserAccount findByName(final String username) {
		Assert.notNull(username);

		final UserAccount user = this.repo.findByUsername(username);
		return this.repo.save(user);
	}

}
