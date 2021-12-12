package ninja.lukasfend.ProgressionMMO.handlers;

import java.text.NumberFormat;
import java.util.Collections;

public class StringHelper {

	/**
	 * Returns a string x times
	 * @param text The string to be returned
	 * @param times The amount of times
	 * @return The new string
	 */
	public static String times(String text, int times) {
		return String.join("", Collections.nCopies(times, text));
	}

	/**
	 * Returns a progressbar consisting of "|" characters
	 * @param charcount The amount of bars
	 * @param percent How many percent the bar should display
	 * @return The string of the progress bar (with colors Red & Green)
	 */
	public static String progressBar(int charcount, double percent) { 
		if(percent > 1f) {
			percent = 1f;
		}
		int spacesActive = (int) Math.round(percent* charcount);
		return "§2" + StringHelper.times("|", spacesActive) + "§4" + StringHelper.times("|", charcount-spacesActive);
	}


	/**
	 * Adds 1000. spacers (e.g. 1000000 -> 1.000.000)
	 * @param number The number
	 * @return The string of the number
	 */
	public static String thousandSpacers(int number) {
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		return formatter.format(number).replace(",00", "").substring(2);
		//return String.format("$,d", number).replace(",", ".");	
	}
}
