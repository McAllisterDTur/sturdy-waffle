
package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.FixUpTaskRepository;
import domain.FixUpTask;

@Component
@Transactional
public class StringToFixUpTaskConverter implements Converter<String, FixUpTask> {

	@Autowired
	private FixUpTaskRepository	taskRepository;


	@Override
	public FixUpTask convert(final String text) {
		FixUpTask result;
		final int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.taskRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}
