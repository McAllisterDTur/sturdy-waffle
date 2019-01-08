
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Warranty;

@Component
@Transactional
public class WarrantyToStringConverter implements Converter<Warranty, String> {

	@Override
	public String convert(final Warranty warranty) {
		String res;
		if (warranty == null)
			res = null;
		else
			res = String.valueOf(warranty.getId());

		return res;
	}

}
