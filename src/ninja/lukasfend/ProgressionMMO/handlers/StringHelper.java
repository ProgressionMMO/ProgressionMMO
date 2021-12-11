package ninja.lukasfend.ProgressionMMO.handlers;

import java.text.NumberFormat;
import java.util.Collections;

public class StringHelper {
	public static String times(String text, int times) {
		return String.join("", Collections.nCopies(times, text));
	}
	
	public static String progressBar(int charcount, double percent) { 
		if(percent > 1f) {
			percent = 1f;
		}
		int spacesActive = (int) Math.round(percent* charcount);
		return "§2" + StringHelper.times("|", spacesActive) + "§4" + StringHelper.times("|", charcount-spacesActive);
	}
	
	public static String thousandSpacers(int number) {
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		return formatter.format(number).replace(",00", "").substring(2);
		//return String.format("$,d", number).replace(",", ".");	
	}
}
