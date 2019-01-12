
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import security.UserAccount;

@Component
@Transactional
public class UserAccountToStringConverter implements Converter<UserAccount, String> {

	@Override
	public String convert(final UserAccount ua) {
		String res;

		if (ua == null)
			res = null;
		else
			res = String.valueOf(ua.getId());

		return res;
	}

}
