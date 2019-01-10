
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Referee;

@Component
@Transactional
public class RefereeToStringConverter implements Converter<Referee, String> {

	@Override
	public String convert(final Referee referee) {
		String result;

		if (referee == null)
			result = null;
		else
			result = String.valueOf(referee.getId());
		return result;
	}

}
