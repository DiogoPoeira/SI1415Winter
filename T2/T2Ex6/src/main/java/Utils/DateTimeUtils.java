package Utils;

import org.joda.time.DateTime;

public class DateTimeUtils {

	public static DateTime getDateTimeFromString(String date){
		DateTime dateOfCompletion = null;

		if (date != null){
			String[] days, secs;
			String milis="0";
			
			days = date.substring(0, date.indexOf("T")).split("-");
			secs = date.substring(date.indexOf("T")+1 , date.length() - 1).split(":");
			if(secs[2].contains(".")){
				milis = secs[2].substring(secs[2].indexOf(".")+1);
				secs[2]=secs[2].substring(0,secs[2].indexOf("."));
			}
			dateOfCompletion = new DateTime(Integer.parseInt(days[0]), 
					Integer.parseInt(days[1]),
					Integer.parseInt(days[2]),
					Integer.parseInt(secs[0]),
					Integer.parseInt(secs[1]),
					Integer.parseInt(secs[2]), 
					Integer.parseInt(milis));

		}

		return dateOfCompletion;
	}
}
