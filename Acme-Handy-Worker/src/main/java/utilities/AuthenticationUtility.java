
package utilities;

import security.Authority;
import security.LoginService;
import security.UserAccount;

public class AuthenticationUtility {

	public static Boolean checkAuthority(final String authority) {
		final UserAccount ua = LoginService.getPrincipal();
		if (ua == null)
			return false;
		else {
			final Authority a = new Authority();
			a.setAuthority(authority);
			return (ua.getAuthorities().contains(a));
		}

	}
}
