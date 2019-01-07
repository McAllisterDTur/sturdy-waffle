
package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import security.UserAccount;
import security.UserAccountRepository;

@Component
@Transactional
public class StringToUserAccountConverter implements Converter<String, UserAccount> {

	@Autowired
	UserAccountRepository	uaRepo;


	@Override
	public UserAccount convert(final String s) {
		UserAccount res;
		Integer id;

		try {
			if (StringUtils.isEmpty(s))
				res = null;
			else {
				id = Integer.valueOf(s);
				res = this.uaRepo.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return res;
	}
}
