
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Complaint;

@Component
@Transactional
public class ComplaintToStringConverter implements Converter<Complaint, String> {

	@Override
	public String convert(final Complaint complaint) {
		String result;

		if (complaint == null)
			result = null;
		else
			result = String.valueOf(complaint.getId());
		return result;
	}

}
