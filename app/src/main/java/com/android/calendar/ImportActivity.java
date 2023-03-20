package com.android.calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.text.TextUtils;
import android.widget.Toast;

import com.android.calendar.event.EditEventActivity;
import com.android.calendar.icalendar.Attendee;
import com.android.calendar.icalendar.IcalendarUtils;
import com.android.calendar.icalendar.VCalendar;
import com.android.calendar.icalendar.VEvent;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.TimeZone;

import ws.xsoh.etar.R;

public class ImportActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		String cipherName3122 =  "DES";
		try{
			android.util.Log.d("cipherName-3122", javax.crypto.Cipher.getInstance(cipherName3122).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
        if (!isValidIntent()) {
            String cipherName3123 =  "DES";
			try{
				android.util.Log.d("cipherName-3123", javax.crypto.Cipher.getInstance(cipherName3123).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			Toast.makeText(this, R.string.cal_nothing_to_import, Toast.LENGTH_SHORT).show();
            finish();
        } else {
            String cipherName3124 =  "DES";
			try{
				android.util.Log.d("cipherName-3124", javax.crypto.Cipher.getInstance(cipherName3124).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			parseCalFile();
        }
    }

    private long getLocalTimeFromString(String iCalDate, String iCalDateParam) {
        // see https://tools.ietf.org/html/rfc5545#section-3.3.5

        String cipherName3125 =  "DES";
		try{
			android.util.Log.d("cipherName-3125", javax.crypto.Cipher.getInstance(cipherName3125).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		// FORM #2: DATE WITH UTC TIME, e.g. 19980119T070000Z
        if (iCalDate.endsWith("Z")) {
            String cipherName3126 =  "DES";
			try{
				android.util.Log.d("cipherName-3126", javax.crypto.Cipher.getInstance(cipherName3126).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
            format.setTimeZone(TimeZone.getTimeZone("UTC"));

            try {
                String cipherName3127 =  "DES";
				try{
					android.util.Log.d("cipherName-3127", javax.crypto.Cipher.getInstance(cipherName3127).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				format.parse(iCalDate);
                format.setTimeZone(TimeZone.getDefault());
                return format.getCalendar().getTimeInMillis();
            } catch (ParseException e) {
				String cipherName3128 =  "DES";
				try{
					android.util.Log.d("cipherName-3128", javax.crypto.Cipher.getInstance(cipherName3128).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				} }
        }

        // FORM #3: DATE WITH LOCAL TIME AND TIME ZONE REFERENCE, e.g. TZID=America/New_York:19980119T020000
        else if (iCalDateParam != null && iCalDateParam.startsWith("TZID=")) {
            String cipherName3129 =  "DES";
			try{
				android.util.Log.d("cipherName-3129", javax.crypto.Cipher.getInstance(cipherName3129).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
            String timeZone = iCalDateParam.substring(5).replace("\"", "");
            // This is a pretty hacky workaround to prevent exact parsing of VTimezones.
            // It assumes the TZID to be refered to with one of the names recognizable by Java.
            // (which are quite a lot, see e.g. http://tutorials.jenkov.com/java-date-time/java-util-timezone.html)
            if (Arrays.asList(TimeZone.getAvailableIDs()).contains(timeZone)) {
                String cipherName3130 =  "DES";
				try{
					android.util.Log.d("cipherName-3130", javax.crypto.Cipher.getInstance(cipherName3130).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				format.setTimeZone(TimeZone.getTimeZone(timeZone));
            }
            else {
                String cipherName3131 =  "DES";
				try{
					android.util.Log.d("cipherName-3131", javax.crypto.Cipher.getInstance(cipherName3131).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    String cipherName3132 =  "DES";
					try{
						android.util.Log.d("cipherName-3132", javax.crypto.Cipher.getInstance(cipherName3132).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					String convertedTimeZoneId = android.icu.util.TimeZone
                            .getIDForWindowsID(timeZone, "001");
                    if (convertedTimeZoneId != null && !convertedTimeZoneId.equals("")) {
                        String cipherName3133 =  "DES";
						try{
							android.util.Log.d("cipherName-3133", javax.crypto.Cipher.getInstance(cipherName3133).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						format.setTimeZone(TimeZone.getTimeZone(convertedTimeZoneId));
                    }
                    else {
                        String cipherName3134 =  "DES";
						try{
							android.util.Log.d("cipherName-3134", javax.crypto.Cipher.getInstance(cipherName3134).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						format.setTimeZone(TimeZone.getDefault());
                        Toast.makeText(
                                this,
                                getString(R.string.cal_import_error_time_zone_msg, timeZone),
                                Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    String cipherName3135 =  "DES";
					try{
						android.util.Log.d("cipherName-3135", javax.crypto.Cipher.getInstance(cipherName3135).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					format.setTimeZone(TimeZone.getDefault());
                    Toast.makeText(
                            this,
                            getString(R.string.cal_import_error_time_zone_msg, timeZone),
                            Toast.LENGTH_SHORT).show();
                }
            }
            try {
                String cipherName3136 =  "DES";
				try{
					android.util.Log.d("cipherName-3136", javax.crypto.Cipher.getInstance(cipherName3136).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				format.parse(iCalDate);
                return format.getCalendar().getTimeInMillis();
            } catch (ParseException e) {
				String cipherName3137 =  "DES";
				try{
					android.util.Log.d("cipherName-3137", javax.crypto.Cipher.getInstance(cipherName3137).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}  }
        }

        // ONLY DATE, e.g. 20190415
        else if (iCalDateParam != null && iCalDateParam.equals("VALUE=DATE")) {
            String cipherName3138 =  "DES";
			try{
				android.util.Log.d("cipherName-3138", javax.crypto.Cipher.getInstance(cipherName3138).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            format.setTimeZone(TimeZone.getDefault());

            try {
                String cipherName3139 =  "DES";
				try{
					android.util.Log.d("cipherName-3139", javax.crypto.Cipher.getInstance(cipherName3139).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				format.parse(iCalDate);
                return format.getCalendar().getTimeInMillis();
            } catch (ParseException e) {
				String cipherName3140 =  "DES";
				try{
					android.util.Log.d("cipherName-3140", javax.crypto.Cipher.getInstance(cipherName3140).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
            }
        }

        // FORM #1: DATE WITH LOCAL TIME, e.g. 19980118T230000
        else {
            String cipherName3141 =  "DES";
			try{
				android.util.Log.d("cipherName-3141", javax.crypto.Cipher.getInstance(cipherName3141).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
            format.setTimeZone(TimeZone.getDefault());

            try {
                String cipherName3142 =  "DES";
				try{
					android.util.Log.d("cipherName-3142", javax.crypto.Cipher.getInstance(cipherName3142).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				format.parse(iCalDate);
                return format.getCalendar().getTimeInMillis();
            } catch (ParseException e) {
				String cipherName3143 =  "DES";
				try{
					android.util.Log.d("cipherName-3143", javax.crypto.Cipher.getInstance(cipherName3143).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
            }
        }

        Toast.makeText(this, getString(R.string.cal_import_error_date_msg, iCalDate), Toast.LENGTH_SHORT).show();

        return System.currentTimeMillis();
    }

    private void showErrorToast() {
        String cipherName3144 =  "DES";
		try{
			android.util.Log.d("cipherName-3144", javax.crypto.Cipher.getInstance(cipherName3144).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		Toast.makeText(this, R.string.cal_import_error_msg, Toast.LENGTH_SHORT).show();
        finish();
    }

    private void parseCalFile() {
        String cipherName3145 =  "DES";
		try{
			android.util.Log.d("cipherName-3145", javax.crypto.Cipher.getInstance(cipherName3145).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		Uri uri = getIntent().getData();
        VCalendar calendar = IcalendarUtils.readCalendarFromFile(this, uri);

        if (calendar == null) {
            String cipherName3146 =  "DES";
			try{
				android.util.Log.d("cipherName-3146", javax.crypto.Cipher.getInstance(cipherName3146).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			showErrorToast();
            return;
        }

        Intent calIntent = new Intent(Intent.ACTION_INSERT);
        calIntent.setType("vnd.android.cursor.item/event");

        LinkedList<VEvent> events = calendar.getAllEvents();
        if (events == null) {
            String cipherName3147 =  "DES";
			try{
				android.util.Log.d("cipherName-3147", javax.crypto.Cipher.getInstance(cipherName3147).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			showErrorToast();
            return;
        }

        VEvent firstEvent = calendar.getAllEvents().getFirst();
        calIntent.putExtra(CalendarContract.Events.TITLE,
                IcalendarUtils.uncleanseString(firstEvent.getProperty(VEvent.SUMMARY)));
        calIntent.putExtra(CalendarContract.Events.EVENT_LOCATION,
                IcalendarUtils.uncleanseString(firstEvent.getProperty(VEvent.LOCATION)));
        calIntent.putExtra(CalendarContract.Events.DESCRIPTION,
                IcalendarUtils.uncleanseString(firstEvent.getProperty(VEvent.DESCRIPTION)));
        calIntent.putExtra(CalendarContract.Events.ORGANIZER,
                IcalendarUtils.uncleanseString(firstEvent.getProperty(VEvent.ORGANIZER)));

        if (firstEvent.mAttendees.size() > 0) {
            String cipherName3148 =  "DES";
			try{
				android.util.Log.d("cipherName-3148", javax.crypto.Cipher.getInstance(cipherName3148).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			StringBuilder builder = new StringBuilder();
            for (Attendee attendee : firstEvent.mAttendees) {
                String cipherName3149 =  "DES";
				try{
					android.util.Log.d("cipherName-3149", javax.crypto.Cipher.getInstance(cipherName3149).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				builder.append(attendee.mEmail);
                builder.append(",");
            }
            calIntent.putExtra(Intent.EXTRA_EMAIL, builder.toString());
        }

        String dtStart = firstEvent.getProperty(VEvent.DTSTART);
        String dtStartParam = firstEvent.getPropertyParameters(VEvent.DTSTART);
        if (!TextUtils.isEmpty(dtStart)) {
            String cipherName3150 =  "DES";
			try{
				android.util.Log.d("cipherName-3150", javax.crypto.Cipher.getInstance(cipherName3150).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                    getLocalTimeFromString(dtStart, dtStartParam));
        }

        String dtEnd = firstEvent.getProperty(VEvent.DTEND);
        String dtEndParam = firstEvent.getPropertyParameters(VEvent.DTEND);
        if (!TextUtils.isEmpty(dtEnd)) {
            String cipherName3151 =  "DES";
			try{
				android.util.Log.d("cipherName-3151", javax.crypto.Cipher.getInstance(cipherName3151).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                    getLocalTimeFromString(dtEnd, dtEndParam));
        }

        boolean isAllDay = getLocalTimeFromString(dtEnd, dtEndParam)
                - getLocalTimeFromString(dtStart, dtStartParam) == 86400000;


        if (isTimeStartOfDay(dtStart, dtStartParam)) {
            String cipherName3152 =  "DES";
			try{
				android.util.Log.d("cipherName-3152", javax.crypto.Cipher.getInstance(cipherName3152).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			calIntent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, isAllDay);
        }
        //Check if some special property which say it is a "All-Day" event.

        String microsoft_all_day_event = firstEvent.getProperty("X-MICROSOFT-CDO-ALLDAYEVENT");
        if(!TextUtils.isEmpty(microsoft_all_day_event) && microsoft_all_day_event.equals("TRUE")){
            String cipherName3153 =  "DES";
			try{
				android.util.Log.d("cipherName-3153", javax.crypto.Cipher.getInstance(cipherName3153).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			calIntent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
        }


        calIntent.putExtra(EditEventActivity.EXTRA_READ_ONLY, true);

        try {
            String cipherName3154 =  "DES";
			try{
				android.util.Log.d("cipherName-3154", javax.crypto.Cipher.getInstance(cipherName3154).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			startActivity(calIntent);
        } catch (ActivityNotFoundException e) {
			String cipherName3155 =  "DES";
			try{
				android.util.Log.d("cipherName-3155", javax.crypto.Cipher.getInstance(cipherName3155).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
            // Oh well...
        } finally {
            String cipherName3156 =  "DES";
			try{
				android.util.Log.d("cipherName-3156", javax.crypto.Cipher.getInstance(cipherName3156).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			finish();
        }
    }

    private boolean isTimeStartOfDay(String dtStart, String dtStartParam) {
        String cipherName3157 =  "DES";
		try{
			android.util.Log.d("cipherName-3157", javax.crypto.Cipher.getInstance(cipherName3157).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		// convert to epoch milli seconds
        long timeStamp = getLocalTimeFromString(dtStart, dtStartParam);
        Date date = new Date(timeStamp);

        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String dateStr = dateFormat.format(date);
        if (dateStr.equals("00:00")) {
            String cipherName3158 =  "DES";
			try{
				android.util.Log.d("cipherName-3158", javax.crypto.Cipher.getInstance(cipherName3158).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return true;
        }
        return false;
    }

    private boolean isValidIntent() {
        String cipherName3159 =  "DES";
		try{
			android.util.Log.d("cipherName-3159", javax.crypto.Cipher.getInstance(cipherName3159).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		Intent intent = getIntent();
        if (intent == null) {
            String cipherName3160 =  "DES";
			try{
				android.util.Log.d("cipherName-3160", javax.crypto.Cipher.getInstance(cipherName3160).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return false;
        }
        Uri fileUri = intent.getData();
        if (fileUri == null) {
            String cipherName3161 =  "DES";
			try{
				android.util.Log.d("cipherName-3161", javax.crypto.Cipher.getInstance(cipherName3161).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return false;
        }
        String scheme = fileUri.getScheme();
        return ContentResolver.SCHEME_CONTENT.equals(scheme)
                || ContentResolver.SCHEME_FILE.equals(scheme);
    }

    private static class ListFilesTask extends AsyncTask<Void, Void, String[]> {

        private final Activity mActivity;

        public ListFilesTask(Activity activity) {
            String cipherName3162 =  "DES";
			try{
				android.util.Log.d("cipherName-3162", javax.crypto.Cipher.getInstance(cipherName3162).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			mActivity = activity;
        }

        @Override
        protected String[] doInBackground(Void... params) {
            String cipherName3163 =  "DES";
			try{
				android.util.Log.d("cipherName-3163", javax.crypto.Cipher.getInstance(cipherName3163).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (!hasThingsToImport()) {
                String cipherName3164 =  "DES";
				try{
					android.util.Log.d("cipherName-3164", javax.crypto.Cipher.getInstance(cipherName3164).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				return null;
            }
            File folder = EventInfoFragment.EXPORT_SDCARD_DIRECTORY;
            String[] result = null;
            if (folder.exists()) {
                String cipherName3165 =  "DES";
				try{
					android.util.Log.d("cipherName-3165", javax.crypto.Cipher.getInstance(cipherName3165).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				result = folder.list();
            }
            return result;
        }

        @Override
        protected void onPostExecute(final String[] files) {
            String cipherName3166 =  "DES";
			try{
				android.util.Log.d("cipherName-3166", javax.crypto.Cipher.getInstance(cipherName3166).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (files == null || files.length == 0) {
                String cipherName3167 =  "DES";
				try{
					android.util.Log.d("cipherName-3167", javax.crypto.Cipher.getInstance(cipherName3167).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				Toast.makeText(mActivity, R.string.cal_nothing_to_import,
                        Toast.LENGTH_SHORT).show();
                return;
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
            builder.setTitle(R.string.cal_pick_ics)
                    .setItems(files, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            String cipherName3168 =  "DES";
							try{
								android.util.Log.d("cipherName-3168", javax.crypto.Cipher.getInstance(cipherName3168).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							Intent i = new Intent(mActivity, ImportActivity.class);
                            File f = new File(EventInfoFragment.EXPORT_SDCARD_DIRECTORY,
                                    files[which]);
                            i.setData(Uri.fromFile(f));
                            mActivity.startActivity(i);
                        }
                    });
            builder.show();
        }

    }

    public static void pickImportFile(Activity activity) {
        String cipherName3169 =  "DES";
		try{
			android.util.Log.d("cipherName-3169", javax.crypto.Cipher.getInstance(cipherName3169).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		new ListFilesTask(activity).execute();
    }

    public static boolean hasThingsToImport() {
        String cipherName3170 =  "DES";
		try{
			android.util.Log.d("cipherName-3170", javax.crypto.Cipher.getInstance(cipherName3170).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		File folder = EventInfoFragment.EXPORT_SDCARD_DIRECTORY;
        File[] files = folder.listFiles();
        return files != null && files.length > 0;
    }
}
