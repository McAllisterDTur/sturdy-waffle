
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import security.UserAccount;

@Component
@Transactional
public class AccountToStringConverter implements Converter<UserAccount, String> {

	@Override
	public String convert(final UserAccount account) {
		String result;

		if (account == null)
			result = null;
		else
			result = String.valueOf(account.getId());
		return result;
	}

}
