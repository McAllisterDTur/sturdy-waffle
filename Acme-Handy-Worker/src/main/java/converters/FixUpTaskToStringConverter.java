
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.FixUpTask;

@Component
@Transactional
public class FixUpTaskToStringConverter implements Converter<FixUpTask, String> {

	@Override
	public String convert(final FixUpTask task) {
		String result;

		if (task == null)
			result = null;
		else
			result = String.valueOf(task.getId());
		return result;
	}

}
