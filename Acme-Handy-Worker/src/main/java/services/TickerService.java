
package services;

import org.joda.time.LocalDate;
import org.springframework.stereotype.Service;

@Service
public class TickerService {

	private CurriculaService	curriculaService;
	private FixUpTaskService	fixUpTaskService;
	private ComplaintService	complaintService;


	public String getTicker() {
		final LocalDate date = LocalDate.now();

		final String year = Integer.toString(date.getYear()).substring(2, 4);

		final int monthOfYear = date.getMonthOfYear();
		final String month = monthOfYear < 0 ? ("0" + monthOfYear) : "" + monthOfYear;

		final int dayOfMonth = date.getDayOfMonth();
		final String day = dayOfMonth < 0 ? ("0" + dayOfMonth) : "" + dayOfMonth;

		final String ticker = year + month + day;
		return ticker;
	}
}
