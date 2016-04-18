package pcpnru.utilities;

import java.text.NumberFormat;
import java.util.Locale;

public class ThaiNumber {

	public String CenverT_To_ThaiNumberic(String number) {

		Locale thaiLocale = new Locale("th", "TH", "TH");

		String returnString = "";

		NumberFormat nf = NumberFormat.getNumberInstance(thaiLocale);

		returnString = String.valueOf(nf.format(Integer.parseInt(number)));

		return returnString;
	}

}
