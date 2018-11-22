
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

		return this.repo.save(user);
	}

}
