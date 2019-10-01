package currency;

import java.util.Currency;

public class CurrencyConverter {

    // STUB TO FAIL THE TEST
    public static CurrencyAmount convert(CurrencyAmount source, Currency target) {
		// URL: https://api.exchangeratesapi.io/latest?symbols=USD&base=GBP
		// source: source.getCurrencyKey().getCurrencyCode()
		// target: target.getCurrencyCode()

		// URL url = new URL("http://example.com");
		// HttpURLConnection con = (HttpURLConnection) url.openConnection();
		// con.setRequestMethod("GET");

        return new CurrencyAmount(-100 * source.getUnitAmount(), target);
    }

}
