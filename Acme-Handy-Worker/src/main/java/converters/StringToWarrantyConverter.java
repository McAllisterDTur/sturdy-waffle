
package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.WarrantyRepository;
import domain.Warranty;

@Component
@Transactional
public class StringToWarrantyConverter implements Converter<String, Warranty> {

	@Autowired
	private WarrantyRepository	warrantyRepository;


	@Override
	public Warranty convert(final String text) {
		Warranty result;
		final int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.warrantyRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}
