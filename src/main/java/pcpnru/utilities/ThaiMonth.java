package pcpnru.utilities;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class ThaiMonth {
	public String Convert_To_ThaiMonth(String month) {

		Locale thaiLocale = new Locale("th", "TH", "TH");

		SimpleDateFormat df = new SimpleDateFormat("MMMM", thaiLocale);

		month = df.format(Date.valueOf(month));

		return month;
	}

	public String Convert_To_ThaiMonth_AndG(String month) {

		Locale thaiLocale = new Locale("th", "TH", "TH");

		SimpleDateFormat df = new SimpleDateFormat("MMMM G", thaiLocale);

		month = df.format(Date.valueOf(month));

		return month;
	}
}
