package dtu.library.app;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateServer {
    public Calendar getDate(){
        Calendar cal = new GregorianCalendar();
        return new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
    }
}
