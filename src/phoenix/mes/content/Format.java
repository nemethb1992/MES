package phoenix.mes.content;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class Format {
	
	/**
	 * Int értéket formáz, másodpercben megadott értékről, hh:mm:ss formátumra.
	 */
	public static String toTime(int timeInSeconds)
	{
	    int hours = timeInSeconds / 3600;
	    int secondsLeft = timeInSeconds - hours * 3600;
	    int minutes = secondsLeft / 60;
	    int seconds = secondsLeft - minutes * 60;

	    String formattedTime = "";
	    if (hours < 10)
	        formattedTime += "";
	    formattedTime += hours + ":";

	    if (minutes < 10)
	        formattedTime += "0";
	    formattedTime += minutes + ":";

	    if (seconds < 10)
	        formattedTime += "0";
	    formattedTime += seconds ;

	    return formattedTime;
	}
	
	/**
	 * BigDecimal értéket formáz, az aktuális régió szerint.
	 */
	public static String byLocale(BigDecimal bdData, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		switch (((Dictionary) session.getAttribute("Dictionary")).getLanguage()) {
		case de:
			NumberFormat nf1 = NumberFormat.getInstance(Locale.GERMAN);
			return nf1.format(bdData.setScale(3, RoundingMode.HALF_UP).stripTrailingZeros());
			
		case en:
			NumberFormat nf2 = NumberFormat.getInstance(Locale.ENGLISH);
			return nf2.format(bdData.setScale(3, RoundingMode.HALF_UP).stripTrailingZeros());
			
		default:
			case hu:
				NumberFormat nf3 = NumberFormat.getInstance(Locale.GERMAN);
				return (nf3.format(bdData.setScale(3, RoundingMode.HALF_UP).stripTrailingZeros())).replace("."," ");
		}
	}
}
