package dtu.library.acceptance_tests;

import static org.mockito.Mockito.*;

import dtu.library.app.servers.DateServer;
import dtu.library.app.LibraryApp;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class MockDateHolder {

    DateServer date_server = mock(DateServer.class);

    public MockDateHolder(LibraryApp library_app){
        GregorianCalendar cal = new GregorianCalendar();
        setDate(cal);
        library_app.setDateServer(date_server);
   }

   public void setDate(Calendar cal){
        Calendar c = new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        when(this.date_server.getDate()).thenReturn(c);
   }

   public void advancedDateByDates(int days){
       Calendar currentDate = date_server.getDate();
       // Important: we need to create a new object,
       // otherwise, the old calendar object gets changed,
       // which suddenly changes the date for objects
       // using that old calendar object
       Calendar nextDate = new GregorianCalendar();
       nextDate.setTime(currentDate.getTime());
       nextDate.add(Calendar.DAY_OF_YEAR, days);
       setDate(nextDate);
   }
}
