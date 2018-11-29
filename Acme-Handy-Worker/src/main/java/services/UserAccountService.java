
package services;

import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import security.Authority;
import security.UserAccount;
import security.UserAccountRepository;

@Service
@Transactional
public class UserAccountService {

	@Autowired
	private UserAccountRepository	repo;


	public UserAccount create() {
		final UserAccount a = new UserAccount();
		a.setAuthorities(new HashSet<Authority>());
		return a;
	}

	public UserAccount save(final UserAccount user) {
		Assert.notNull(user);

		//TODO: comprobar que solo existe un authority para el account
		//Encriptado de la contraseña
		user.setPassword(new Md5PasswordEncoder().encodePassword(user.getPassword(), null));

		return this.repo.save(user);
	}

}
