
package converters;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.CreditCard;

@Component
@Transactional
public class CreditCardToStringConverter implements Converter<CreditCard, String> {

	@Override
	public String convert(final CreditCard creditCard) {
		final String result;
		final StringBuilder builder;

		if (creditCard == null)
			result = null;
		else
			try {
				builder = new StringBuilder();
				// HolderName
				builder.append(this.encodeUTF8(creditCard.getHolderName()));
				builder.append("|");
				// BrandName
				builder.append(this.encodeUTF8(creditCard.getBrandName()));
				builder.append("|");
				// Number
				builder.append(this.encodeUTF8(creditCard.getNumber()));
				builder.append("|");
				// ExpirationMonth
				builder.append(this.encodeUTF8(Integer.toString(creditCard.getExpirationMonth())));
				builder.append("|");
				// ExpirationYear
				builder.append(this.encodeUTF8(Integer.toString(creditCard.getExpirationYear())));
				builder.append("|");
				// CodeCVV
				builder.append(this.encodeUTF8(Integer.toString(creditCard.getCodeCVV())));
				builder.append("|");
				// Maker
				builder.append(this.encodeUTF8(creditCard.getMaker()));
				result = builder.toString();
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			}
		return result;
	}

	private String encodeUTF8(final String text) throws UnsupportedEncodingException {
		return URLEncoder.encode(text, "URF-8");

	}
}
