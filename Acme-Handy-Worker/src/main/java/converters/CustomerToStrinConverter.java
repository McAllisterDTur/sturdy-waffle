
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Customer;

@Component
@Transactional
public class CustomerToStrinConverter implements Converter<Customer, String> {

	@Override
	public String convert(final Customer customer) {
		String result;

		if (customer == null)
			result = null;
		else
			result = String.valueOf(customer.getId());
		return result;
	}

}
