package Utils;

import org.joda.time.DateTime;

public class DateTimeUtils {

	public static DateTime getDateTimeFromString(String date){
		DateTime dateOfCompletion = null;

		if (date != null){
			String[] days, secs;
			
			days = date.substring(0, date.indexOf("T")).split("-");
			secs = date.substring(date.indexOf("T")+1 , date.length() - 1).split(":");

			dateOfCompletion = new DateTime(Integer.parseInt(days[0]), 
					Integer.parseInt(days[1]),
					Integer.parseInt(days[2]),
					Integer.parseInt(secs[0]),
					Integer.parseInt(secs[1]),
					Integer.parseInt(secs[2]), 
					0);

		}

		return dateOfCompletion;
	}
}
