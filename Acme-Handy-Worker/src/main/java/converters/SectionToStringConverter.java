
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Section;

@Component
@Transactional
public class SectionToStringConverter implements Converter<Section, String> {

	@Override
	public String convert(final Section section) {
		String result;
		if (section == null)
			result = null;
		else
			result = String.valueOf(section.getId());
		return result;
	}

}
