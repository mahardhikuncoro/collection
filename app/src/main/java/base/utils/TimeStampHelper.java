package base.utils;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import id.collection.mobile.application.R;


public class TimeStampHelper {

    private static String dateFormat;
    private static SimpleDateFormat simpleDateFormat;
    private static String language;
    private static Context context;
    private Locale id;

    public TimeStampHelper(Context context){
        super();
        this.context = context;
    }

    public String ddMMyyyy(Long milliSeconds){
        dateFormat = "dd MMM yyyy";
        language = context.getResources().getString(R.string.language);
            if (language.equals("EN")) {
                id = Locale.ENGLISH;
            } else if (language.equals("ID")){
                id = new Locale("in", "ID");
            } else {
                id = Locale.ENGLISH;
            }
        simpleDateFormat = new SimpleDateFormat(dateFormat, id);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return simpleDateFormat.format(calendar.getTime());
    }

    public String ddMMyyyyhhmm(Long milliSeconds){
        dateFormat = "dd MMM yyyy HH:mm:ss";
        language = context.getResources().getString(R.string.language);
            if (language.equals("EN")) {
                id = Locale.ENGLISH;
            } else if (language.equals("ID")){
                id = new Locale("in", "ID");
            } else {
                id = Locale.ENGLISH;
            }
        simpleDateFormat = new SimpleDateFormat(dateFormat, id);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return simpleDateFormat.format(calendar.getTime());
    }


    public String MMMddyyyy(Long milliSeconds){
        dateFormat = "MMM dd,yyyy";
        language = context.getResources().getString(R.string.language);
            if (language.equals("EN")) {
                id = Locale.ENGLISH;
            } else if (language.equals("ID")){
                id = new Locale("in", "ID");
            } else {
                id = Locale.ENGLISH;
            }
        simpleDateFormat = new SimpleDateFormat(dateFormat, id);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return simpleDateFormat.format(calendar.getTime());
    }

    public String MMMddyyyyhhmm(Long milliSeconds){
        dateFormat = "MMM dd,yyyy HH:mm";
        language = context.getResources().getString(R.string.language);
            if (language.equals("EN")) {
                id = Locale.ENGLISH;
            } else if (language.equals("ID")){
                id = new Locale("in", "ID");
            } else {
                id = Locale.ENGLISH;
            }
        simpleDateFormat = new SimpleDateFormat(dateFormat, id);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return simpleDateFormat.format(calendar.getTime());
    }

    public static Date getEndDateTimeMillis(){
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);

        return calendar.getTime();
    }

    public static Date getStartDateTimeMillis(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    public static Date get7DaysFuture(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_YEAR, -7);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    public String getLanguage(){
        return language = context.getString(R.string.language);
    }
}
