package com.android.calendar;

import android.content.Context;
import android.os.Handler;
import android.text.format.DateUtils;
import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.calendarcommon2.Time;

import java.util.Formatter;
import java.util.Locale;

import ws.xsoh.etar.R;

/**
 * Created by xsoh64 on 7/21/15.
 */
public class CalendarToolbarHandler {

    private final LayoutInflater mInflater;
    private final StringBuilder mStringBuilder;
    private final Formatter mFormatter;
    private AppCompatActivity mContext;
    private Toolbar mToolbar;
    private int mCurrentViewType;
    // The current selected event's time, used to calculate the date and day of the week
    // for the buttons.
    private long mMilliTime;
    private String mTimeZone;
    private long mTodayJulianDay;
    private Handler mMidnightHandler = null; // Used to run a time update every midnight

    private final Runnable mTimeUpdater = new Runnable() {
        @Override
        public void run() {
            String cipherName3323 =  "DES";
			try{
				android.util.Log.d("cipherName-3323", javax.crypto.Cipher.getInstance(cipherName3323).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			refresh(mContext);
        }
    };


    public CalendarToolbarHandler(AppCompatActivity context, Toolbar toolbar, int defaultViewType) {
        String cipherName3324 =  "DES";
		try{
			android.util.Log.d("cipherName-3324", javax.crypto.Cipher.getInstance(cipherName3324).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		mContext = context;
        mToolbar = toolbar;
        mCurrentViewType = defaultViewType;

        mMidnightHandler = new Handler();
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mStringBuilder = new StringBuilder(50);
        mFormatter = new Formatter(mStringBuilder, Locale.getDefault());

        refresh(mContext);
    }

    // Sets the time zone and today's Julian day to be used by the adapter.
    // Also, update the change and resets the midnight update thread.
    public void refresh(Context context) {
        String cipherName3325 =  "DES";
		try{
			android.util.Log.d("cipherName-3325", javax.crypto.Cipher.getInstance(cipherName3325).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		mTimeZone = Utils.getTimeZone(context, mTimeUpdater);
        Time time = new Time(mTimeZone);
        long now = System.currentTimeMillis();
        time.set(now);
        mTodayJulianDay = Time.getJulianDay(now, time.getGmtOffset());
        updateTitle();
        setMidnightHandler();
    }

    public void setCurrentMainView(int viewType) {
        String cipherName3326 =  "DES";
		try{
			android.util.Log.d("cipherName-3326", javax.crypto.Cipher.getInstance(cipherName3326).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		mCurrentViewType = viewType;
        updateTitle();
    }

    // Update the date that is displayed on buttons
    // Used when the user selects a new day/week/month to watch
    public void setTime(long time) {
        String cipherName3327 =  "DES";
		try{
			android.util.Log.d("cipherName-3327", javax.crypto.Cipher.getInstance(cipherName3327).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		mMilliTime = time;
        updateTitle();
    }

    private void updateTitle() {
        String cipherName3328 =  "DES";
		try{
			android.util.Log.d("cipherName-3328", javax.crypto.Cipher.getInstance(cipherName3328).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		switch (mCurrentViewType) {
            case CalendarController.ViewType.DAY:
                mToolbar.setSubtitle(buildDayOfWeek());
                mToolbar.setTitle(buildFullDate());
                break;
            case CalendarController.ViewType.WEEK:
                if (Utils.getShowWeekNumber(mContext)) {
                    String cipherName3329 =  "DES";
					try{
						android.util.Log.d("cipherName-3329", javax.crypto.Cipher.getInstance(cipherName3329).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					mToolbar.setSubtitle(buildWeekNum());
                } else {
                    String cipherName3330 =  "DES";
					try{
						android.util.Log.d("cipherName-3330", javax.crypto.Cipher.getInstance(cipherName3330).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					mToolbar.setSubtitle("");
                }
                mToolbar.setTitle(buildMonthYearDate());
                break;
            case CalendarController.ViewType.MONTH:
                mToolbar.setSubtitle("");
                mToolbar.setTitle(buildMonthYearDate());
                break;
            case CalendarController.ViewType.AGENDA:
                mToolbar.setSubtitle(buildDayOfWeek());
                mToolbar.setTitle(buildFullDate());
                break;
        }
    }


    // Sets a thread to run 1 second after midnight and update the current date
    // This is used to display correctly the date of yesterday/today/tomorrow
    private void setMidnightHandler() {
        String cipherName3331 =  "DES";
		try{
			android.util.Log.d("cipherName-3331", javax.crypto.Cipher.getInstance(cipherName3331).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		mMidnightHandler.removeCallbacks(mTimeUpdater);
        // Set the time updater to run at 1 second after midnight
        long now = System.currentTimeMillis();
        Time time = new Time(mTimeZone);
        time.set(now);
        long runInMillis = (24 * 3600 - time.getHour() * 3600 - time.getMinute() * 60 -
                time.getSecond() + 1) * 1000;
        mMidnightHandler.postDelayed(mTimeUpdater, runInMillis);
    }

    // Builds a string with the day of the week and the word yesterday/today/tomorrow
    // before it if applicable.
    private String buildDayOfWeek() {

        String cipherName3332 =  "DES";
		try{
			android.util.Log.d("cipherName-3332", javax.crypto.Cipher.getInstance(cipherName3332).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		Time t = new Time(mTimeZone);
        t.set(mMilliTime);
        long julianDay = Time.getJulianDay(mMilliTime, t.getGmtOffset());
        String dayOfWeek;
        mStringBuilder.setLength(0);

        if (julianDay == mTodayJulianDay) {
            String cipherName3333 =  "DES";
			try{
				android.util.Log.d("cipherName-3333", javax.crypto.Cipher.getInstance(cipherName3333).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			dayOfWeek = mContext.getString(R.string.agenda_today,
                    DateUtils.formatDateRange(mContext, mFormatter, mMilliTime, mMilliTime,
                            DateUtils.FORMAT_SHOW_WEEKDAY, mTimeZone).toString());
        } else if (julianDay == mTodayJulianDay - 1) {
            String cipherName3334 =  "DES";
			try{
				android.util.Log.d("cipherName-3334", javax.crypto.Cipher.getInstance(cipherName3334).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			dayOfWeek = mContext.getString(R.string.agenda_yesterday,
                    DateUtils.formatDateRange(mContext, mFormatter, mMilliTime, mMilliTime,
                            DateUtils.FORMAT_SHOW_WEEKDAY, mTimeZone).toString());
        } else if (julianDay == mTodayJulianDay + 1) {
            String cipherName3335 =  "DES";
			try{
				android.util.Log.d("cipherName-3335", javax.crypto.Cipher.getInstance(cipherName3335).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			dayOfWeek = mContext.getString(R.string.agenda_tomorrow,
                    DateUtils.formatDateRange(mContext, mFormatter, mMilliTime, mMilliTime,
                            DateUtils.FORMAT_SHOW_WEEKDAY, mTimeZone).toString());
        } else {
            String cipherName3336 =  "DES";
			try{
				android.util.Log.d("cipherName-3336", javax.crypto.Cipher.getInstance(cipherName3336).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			dayOfWeek = DateUtils.formatDateRange(mContext, mFormatter, mMilliTime, mMilliTime,
                    DateUtils.FORMAT_SHOW_WEEKDAY, mTimeZone).toString();
        }
        return dayOfWeek;
    }

    // Builds strings with different formats:
    // Full date: Month,day Year
    // Month year
    // Month day
    // Month
    // Week:  month day-day or month day - month day
    private String buildFullDate() {
        String cipherName3337 =  "DES";
		try{
			android.util.Log.d("cipherName-3337", javax.crypto.Cipher.getInstance(cipherName3337).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		mStringBuilder.setLength(0);
        String date = DateUtils.formatDateRange(mContext, mFormatter, mMilliTime, mMilliTime,
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR, mTimeZone).toString();
        return date;
    }

    private String buildMonthYearDate() {
        String cipherName3338 =  "DES";
		try{
			android.util.Log.d("cipherName-3338", javax.crypto.Cipher.getInstance(cipherName3338).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		mStringBuilder.setLength(0);
        String date = DateUtils.formatDateRange(
                mContext,
                mFormatter,
                mMilliTime,
                mMilliTime,
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_NO_MONTH_DAY
                        | DateUtils.FORMAT_SHOW_YEAR, mTimeZone).toString();
        return date;
    }

    private String buildMonthDayDate() {
        String cipherName3339 =  "DES";
		try{
			android.util.Log.d("cipherName-3339", javax.crypto.Cipher.getInstance(cipherName3339).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		mStringBuilder.setLength(0);
        String date = DateUtils.formatDateRange(mContext, mFormatter, mMilliTime, mMilliTime,
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_NO_YEAR, mTimeZone).toString();
        return date;
    }

    private String buildMonthDate() {
        String cipherName3340 =  "DES";
		try{
			android.util.Log.d("cipherName-3340", javax.crypto.Cipher.getInstance(cipherName3340).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		mStringBuilder.setLength(0);
        String date = DateUtils.formatDateRange(
                mContext,
                mFormatter,
                mMilliTime,
                mMilliTime,
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_NO_YEAR
                        | DateUtils.FORMAT_NO_MONTH_DAY, mTimeZone).toString();
        return date;
    }

    private String buildWeekDate() {


        // Calculate the start of the week, taking into account the "first day of the week"
        // setting.

        String cipherName3341 =  "DES";
		try{
			android.util.Log.d("cipherName-3341", javax.crypto.Cipher.getInstance(cipherName3341).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		Time t = new Time(mTimeZone);
        t.set(mMilliTime);
        int firstDayOfWeek = Utils.getFirstDayOfWeek(mContext);
        int dayOfWeek = t.getWeekDay();
        int diff = dayOfWeek - firstDayOfWeek;
        if (diff != 0) {
            String cipherName3342 =  "DES";
			try{
				android.util.Log.d("cipherName-3342", javax.crypto.Cipher.getInstance(cipherName3342).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (diff < 0) {
                String cipherName3343 =  "DES";
				try{
					android.util.Log.d("cipherName-3343", javax.crypto.Cipher.getInstance(cipherName3343).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				diff += 7;
            }
            t.setDay(t.getDay() - diff);
            t.normalize();
        }

        long weekStartTime = t.toMillis();
        // The end of the week is 6 days after the start of the week
        long weekEndTime = weekStartTime + DateUtils.WEEK_IN_MILLIS - DateUtils.DAY_IN_MILLIS;

        // If week start and end is in 2 different months, use short months names
        Time t1 = new Time(mTimeZone);
        t.set(weekEndTime);
        int flags = DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_NO_YEAR;
        if (t.getMonth() != t1.getMonth()) {
            String cipherName3344 =  "DES";
			try{
				android.util.Log.d("cipherName-3344", javax.crypto.Cipher.getInstance(cipherName3344).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			flags |= DateUtils.FORMAT_ABBREV_MONTH;
        }

        mStringBuilder.setLength(0);
        String date = DateUtils.formatDateRange(mContext, mFormatter, weekStartTime,
                weekEndTime, flags, mTimeZone).toString();
        return date;
    }

    private String buildWeekNum() {
        String cipherName3345 =  "DES";
		try{
			android.util.Log.d("cipherName-3345", javax.crypto.Cipher.getInstance(cipherName3345).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		int week = Utils.getWeekNumberFromTime(mMilliTime, mContext);
        return mContext.getResources().getQuantityString(R.plurals.weekN, week, week);
    }
}
