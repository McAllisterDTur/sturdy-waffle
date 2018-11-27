
package services;

import java.util.Random;

import javax.transaction.Transactional;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TickerService {

	@Autowired
	private CurriculaService	curriculaService;
	@Autowired
	private FixUpTaskService	fixUpTaskService;
	@Autowired
	private ComplaintService	complaintService;


	/**
	 * Generates a ticker that is not in the database
	 * 
	 * @return a valid ticker with the format yymmdd-xxxxxx
	 */
	public String getTicker() {
		final LocalDate date = LocalDate.now();

		final String year = Integer.toString(date.getYear()).substring(2, 4);

		final int monthOfYear = date.getMonthOfYear();
		final String month = monthOfYear < 10 ? ("0" + monthOfYear) : "" + monthOfYear;

		final int dayOfMonth = date.getDayOfMonth();
		final String day = dayOfMonth < 10 ? ("0" + dayOfMonth) : "" + dayOfMonth;

		final String tickerBase = year + month + day;
		String ticker = "";
		Integer t = 1;
		while (t != 0) {
			final String s = this.randomAlphaNumeric();
			ticker = tickerBase + "-" + s;

			t = 0;
			t += this.curriculaService.getNumberOfTickers(ticker);
			t += this.fixUpTaskService.getNumberOfTickers(ticker);
			t += this.complaintService.getNumberOfTickers(ticker);

		}
		return ticker;
	}

	private String randomAlphaNumeric() {
		final int leftNumberLimit = 48; // letter '0'
		final int rightNumberLimit = 57; // letter '9'

		final int leftCharLimit = 65; // letter 'A'
		final int rightCharLimit = 90; // letter 'Z'

		final int targetStringLength = 6; // String length = 6

		final Random random = new Random();
		final StringBuilder buffer = new StringBuilder(targetStringLength);

		for (int i = 0; i < targetStringLength; i++) {

			int randomLimitedInt = 0;
			if (random.nextFloat() < 0.5)
				randomLimitedInt = leftNumberLimit
					+ (int) (random.nextFloat() * (rightNumberLimit - leftNumberLimit + 1));
			else
				randomLimitedInt = leftCharLimit
					+ (int) (random.nextFloat() * (rightCharLimit - leftCharLimit + 1));

			buffer.append((char) randomLimitedInt);
		}

		final String generatedString = buffer.toString();

		return generatedString;
	}

}
