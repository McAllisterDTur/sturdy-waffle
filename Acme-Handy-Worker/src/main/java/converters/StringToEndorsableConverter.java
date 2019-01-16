
package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.EndorsableRepository;
import domain.Endorsable;

@Component
@Transactional
public class StringToEndorsableConverter implements Converter<String, Endorsable> {

	@Autowired
	EndorsableRepository	endorsableRepository;


	@Override
	public Endorsable convert(final String text) {
		Endorsable result;
		final int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.endorsableRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}
