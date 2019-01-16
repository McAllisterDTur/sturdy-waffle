
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Endorsable;

@Component
@Transactional
public class EndorsableToStringConverter implements Converter<Endorsable, String> {

	@Override
	public String convert(final Endorsable endorsable) {
		String result;

		if (endorsable == null)
			result = null;
		else
			result = String.valueOf(endorsable.getId());
		return result;
	}

}
