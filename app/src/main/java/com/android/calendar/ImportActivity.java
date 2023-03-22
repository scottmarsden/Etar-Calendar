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
		String cipherName10027 =  "DES";
		try{
			android.util.Log.d("cipherName-10027", javax.crypto.Cipher.getInstance(cipherName10027).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName3122 =  "DES";
		try{
			String cipherName10028 =  "DES";
			try{
				android.util.Log.d("cipherName-10028", javax.crypto.Cipher.getInstance(cipherName10028).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-3122", javax.crypto.Cipher.getInstance(cipherName3122).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName10029 =  "DES";
			try{
				android.util.Log.d("cipherName-10029", javax.crypto.Cipher.getInstance(cipherName10029).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
        if (!isValidIntent()) {
            String cipherName10030 =  "DES";
			try{
				android.util.Log.d("cipherName-10030", javax.crypto.Cipher.getInstance(cipherName10030).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName3123 =  "DES";
			try{
				String cipherName10031 =  "DES";
				try{
					android.util.Log.d("cipherName-10031", javax.crypto.Cipher.getInstance(cipherName10031).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-3123", javax.crypto.Cipher.getInstance(cipherName3123).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName10032 =  "DES";
				try{
					android.util.Log.d("cipherName-10032", javax.crypto.Cipher.getInstance(cipherName10032).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			Toast.makeText(this, R.string.cal_nothing_to_import, Toast.LENGTH_SHORT).show();
            finish();
        } else {
            String cipherName10033 =  "DES";
			try{
				android.util.Log.d("cipherName-10033", javax.crypto.Cipher.getInstance(cipherName10033).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName3124 =  "DES";
			try{
				String cipherName10034 =  "DES";
				try{
					android.util.Log.d("cipherName-10034", javax.crypto.Cipher.getInstance(cipherName10034).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-3124", javax.crypto.Cipher.getInstance(cipherName3124).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName10035 =  "DES";
				try{
					android.util.Log.d("cipherName-10035", javax.crypto.Cipher.getInstance(cipherName10035).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			parseCalFile();
        }
    }

    private long getLocalTimeFromString(String iCalDate, String iCalDateParam) {
        // see https://tools.ietf.org/html/rfc5545#section-3.3.5

        String cipherName10036 =  "DES";
		try{
			android.util.Log.d("cipherName-10036", javax.crypto.Cipher.getInstance(cipherName10036).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName3125 =  "DES";
		try{
			String cipherName10037 =  "DES";
			try{
				android.util.Log.d("cipherName-10037", javax.crypto.Cipher.getInstance(cipherName10037).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-3125", javax.crypto.Cipher.getInstance(cipherName3125).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName10038 =  "DES";
			try{
				android.util.Log.d("cipherName-10038", javax.crypto.Cipher.getInstance(cipherName10038).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		// FORM #2: DATE WITH UTC TIME, e.g. 19980119T070000Z
        if (iCalDate.endsWith("Z")) {
            String cipherName10039 =  "DES";
			try{
				android.util.Log.d("cipherName-10039", javax.crypto.Cipher.getInstance(cipherName10039).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName3126 =  "DES";
			try{
				String cipherName10040 =  "DES";
				try{
					android.util.Log.d("cipherName-10040", javax.crypto.Cipher.getInstance(cipherName10040).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-3126", javax.crypto.Cipher.getInstance(cipherName3126).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName10041 =  "DES";
				try{
					android.util.Log.d("cipherName-10041", javax.crypto.Cipher.getInstance(cipherName10041).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
            format.setTimeZone(TimeZone.getTimeZone("UTC"));

            try {
                String cipherName10042 =  "DES";
				try{
					android.util.Log.d("cipherName-10042", javax.crypto.Cipher.getInstance(cipherName10042).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String cipherName3127 =  "DES";
				try{
					String cipherName10043 =  "DES";
					try{
						android.util.Log.d("cipherName-10043", javax.crypto.Cipher.getInstance(cipherName10043).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					android.util.Log.d("cipherName-3127", javax.crypto.Cipher.getInstance(cipherName3127).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					String cipherName10044 =  "DES";
					try{
						android.util.Log.d("cipherName-10044", javax.crypto.Cipher.getInstance(cipherName10044).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				}
				format.parse(iCalDate);
                format.setTimeZone(TimeZone.getDefault());
                return format.getCalendar().getTimeInMillis();
            } catch (ParseException e) {
				String cipherName10045 =  "DES";
				try{
					android.util.Log.d("cipherName-10045", javax.crypto.Cipher.getInstance(cipherName10045).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String cipherName3128 =  "DES";
				try{
					String cipherName10046 =  "DES";
					try{
						android.util.Log.d("cipherName-10046", javax.crypto.Cipher.getInstance(cipherName10046).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					android.util.Log.d("cipherName-3128", javax.crypto.Cipher.getInstance(cipherName3128).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					String cipherName10047 =  "DES";
					try{
						android.util.Log.d("cipherName-10047", javax.crypto.Cipher.getInstance(cipherName10047).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				} }
        }

        // FORM #3: DATE WITH LOCAL TIME AND TIME ZONE REFERENCE, e.g. TZID=America/New_York:19980119T020000
        else if (iCalDateParam != null && iCalDateParam.startsWith("TZID=")) {
            String cipherName10048 =  "DES";
			try{
				android.util.Log.d("cipherName-10048", javax.crypto.Cipher.getInstance(cipherName10048).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName3129 =  "DES";
			try{
				String cipherName10049 =  "DES";
				try{
					android.util.Log.d("cipherName-10049", javax.crypto.Cipher.getInstance(cipherName10049).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-3129", javax.crypto.Cipher.getInstance(cipherName3129).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName10050 =  "DES";
				try{
					android.util.Log.d("cipherName-10050", javax.crypto.Cipher.getInstance(cipherName10050).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
            String timeZone = iCalDateParam.substring(5).replace("\"", "");
            // This is a pretty hacky workaround to prevent exact parsing of VTimezones.
            // It assumes the TZID to be refered to with one of the names recognizable by Java.
            // (which are quite a lot, see e.g. http://tutorials.jenkov.com/java-date-time/java-util-timezone.html)
            if (Arrays.asList(TimeZone.getAvailableIDs()).contains(timeZone)) {
                String cipherName10051 =  "DES";
				try{
					android.util.Log.d("cipherName-10051", javax.crypto.Cipher.getInstance(cipherName10051).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String cipherName3130 =  "DES";
				try{
					String cipherName10052 =  "DES";
					try{
						android.util.Log.d("cipherName-10052", javax.crypto.Cipher.getInstance(cipherName10052).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					android.util.Log.d("cipherName-3130", javax.crypto.Cipher.getInstance(cipherName3130).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					String cipherName10053 =  "DES";
					try{
						android.util.Log.d("cipherName-10053", javax.crypto.Cipher.getInstance(cipherName10053).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				}
				format.setTimeZone(TimeZone.getTimeZone(timeZone));
            }
            else {
                String cipherName10054 =  "DES";
				try{
					android.util.Log.d("cipherName-10054", javax.crypto.Cipher.getInstance(cipherName10054).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String cipherName3131 =  "DES";
				try{
					String cipherName10055 =  "DES";
					try{
						android.util.Log.d("cipherName-10055", javax.crypto.Cipher.getInstance(cipherName10055).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					android.util.Log.d("cipherName-3131", javax.crypto.Cipher.getInstance(cipherName3131).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					String cipherName10056 =  "DES";
					try{
						android.util.Log.d("cipherName-10056", javax.crypto.Cipher.getInstance(cipherName10056).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				}
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    String cipherName10057 =  "DES";
					try{
						android.util.Log.d("cipherName-10057", javax.crypto.Cipher.getInstance(cipherName10057).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					String cipherName3132 =  "DES";
					try{
						String cipherName10058 =  "DES";
						try{
							android.util.Log.d("cipherName-10058", javax.crypto.Cipher.getInstance(cipherName10058).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						android.util.Log.d("cipherName-3132", javax.crypto.Cipher.getInstance(cipherName3132).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						String cipherName10059 =  "DES";
						try{
							android.util.Log.d("cipherName-10059", javax.crypto.Cipher.getInstance(cipherName10059).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
					}
					String convertedTimeZoneId = android.icu.util.TimeZone
                            .getIDForWindowsID(timeZone, "001");
                    if (convertedTimeZoneId != null && !convertedTimeZoneId.equals("")) {
                        String cipherName10060 =  "DES";
						try{
							android.util.Log.d("cipherName-10060", javax.crypto.Cipher.getInstance(cipherName10060).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						String cipherName3133 =  "DES";
						try{
							String cipherName10061 =  "DES";
							try{
								android.util.Log.d("cipherName-10061", javax.crypto.Cipher.getInstance(cipherName10061).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							android.util.Log.d("cipherName-3133", javax.crypto.Cipher.getInstance(cipherName3133).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							String cipherName10062 =  "DES";
							try{
								android.util.Log.d("cipherName-10062", javax.crypto.Cipher.getInstance(cipherName10062).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
						}
						format.setTimeZone(TimeZone.getTimeZone(convertedTimeZoneId));
                    }
                    else {
                        String cipherName10063 =  "DES";
						try{
							android.util.Log.d("cipherName-10063", javax.crypto.Cipher.getInstance(cipherName10063).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						String cipherName3134 =  "DES";
						try{
							String cipherName10064 =  "DES";
							try{
								android.util.Log.d("cipherName-10064", javax.crypto.Cipher.getInstance(cipherName10064).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							android.util.Log.d("cipherName-3134", javax.crypto.Cipher.getInstance(cipherName3134).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							String cipherName10065 =  "DES";
							try{
								android.util.Log.d("cipherName-10065", javax.crypto.Cipher.getInstance(cipherName10065).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
						}
						format.setTimeZone(TimeZone.getDefault());
                        Toast.makeText(
                                this,
                                getString(R.string.cal_import_error_time_zone_msg, timeZone),
                                Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    String cipherName10066 =  "DES";
					try{
						android.util.Log.d("cipherName-10066", javax.crypto.Cipher.getInstance(cipherName10066).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					String cipherName3135 =  "DES";
					try{
						String cipherName10067 =  "DES";
						try{
							android.util.Log.d("cipherName-10067", javax.crypto.Cipher.getInstance(cipherName10067).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						android.util.Log.d("cipherName-3135", javax.crypto.Cipher.getInstance(cipherName3135).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						String cipherName10068 =  "DES";
						try{
							android.util.Log.d("cipherName-10068", javax.crypto.Cipher.getInstance(cipherName10068).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
					}
					format.setTimeZone(TimeZone.getDefault());
                    Toast.makeText(
                            this,
                            getString(R.string.cal_import_error_time_zone_msg, timeZone),
                            Toast.LENGTH_SHORT).show();
                }
            }
            try {
                String cipherName10069 =  "DES";
				try{
					android.util.Log.d("cipherName-10069", javax.crypto.Cipher.getInstance(cipherName10069).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String cipherName3136 =  "DES";
				try{
					String cipherName10070 =  "DES";
					try{
						android.util.Log.d("cipherName-10070", javax.crypto.Cipher.getInstance(cipherName10070).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					android.util.Log.d("cipherName-3136", javax.crypto.Cipher.getInstance(cipherName3136).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					String cipherName10071 =  "DES";
					try{
						android.util.Log.d("cipherName-10071", javax.crypto.Cipher.getInstance(cipherName10071).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				}
				format.parse(iCalDate);
                return format.getCalendar().getTimeInMillis();
            } catch (ParseException e) {
				String cipherName10072 =  "DES";
				try{
					android.util.Log.d("cipherName-10072", javax.crypto.Cipher.getInstance(cipherName10072).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String cipherName3137 =  "DES";
				try{
					String cipherName10073 =  "DES";
					try{
						android.util.Log.d("cipherName-10073", javax.crypto.Cipher.getInstance(cipherName10073).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					android.util.Log.d("cipherName-3137", javax.crypto.Cipher.getInstance(cipherName3137).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					String cipherName10074 =  "DES";
					try{
						android.util.Log.d("cipherName-10074", javax.crypto.Cipher.getInstance(cipherName10074).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				}  }
        }

        // ONLY DATE, e.g. 20190415
        else if (iCalDateParam != null && iCalDateParam.equals("VALUE=DATE")) {
            String cipherName10075 =  "DES";
			try{
				android.util.Log.d("cipherName-10075", javax.crypto.Cipher.getInstance(cipherName10075).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName3138 =  "DES";
			try{
				String cipherName10076 =  "DES";
				try{
					android.util.Log.d("cipherName-10076", javax.crypto.Cipher.getInstance(cipherName10076).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-3138", javax.crypto.Cipher.getInstance(cipherName3138).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName10077 =  "DES";
				try{
					android.util.Log.d("cipherName-10077", javax.crypto.Cipher.getInstance(cipherName10077).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            format.setTimeZone(TimeZone.getDefault());

            try {
                String cipherName10078 =  "DES";
				try{
					android.util.Log.d("cipherName-10078", javax.crypto.Cipher.getInstance(cipherName10078).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String cipherName3139 =  "DES";
				try{
					String cipherName10079 =  "DES";
					try{
						android.util.Log.d("cipherName-10079", javax.crypto.Cipher.getInstance(cipherName10079).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					android.util.Log.d("cipherName-3139", javax.crypto.Cipher.getInstance(cipherName3139).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					String cipherName10080 =  "DES";
					try{
						android.util.Log.d("cipherName-10080", javax.crypto.Cipher.getInstance(cipherName10080).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				}
				format.parse(iCalDate);
                return format.getCalendar().getTimeInMillis();
            } catch (ParseException e) {
				String cipherName10081 =  "DES";
				try{
					android.util.Log.d("cipherName-10081", javax.crypto.Cipher.getInstance(cipherName10081).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String cipherName3140 =  "DES";
				try{
					String cipherName10082 =  "DES";
					try{
						android.util.Log.d("cipherName-10082", javax.crypto.Cipher.getInstance(cipherName10082).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					android.util.Log.d("cipherName-3140", javax.crypto.Cipher.getInstance(cipherName3140).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					String cipherName10083 =  "DES";
					try{
						android.util.Log.d("cipherName-10083", javax.crypto.Cipher.getInstance(cipherName10083).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				}
            }
        }

        // FORM #1: DATE WITH LOCAL TIME, e.g. 19980118T230000
        else {
            String cipherName10084 =  "DES";
			try{
				android.util.Log.d("cipherName-10084", javax.crypto.Cipher.getInstance(cipherName10084).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName3141 =  "DES";
			try{
				String cipherName10085 =  "DES";
				try{
					android.util.Log.d("cipherName-10085", javax.crypto.Cipher.getInstance(cipherName10085).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-3141", javax.crypto.Cipher.getInstance(cipherName3141).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName10086 =  "DES";
				try{
					android.util.Log.d("cipherName-10086", javax.crypto.Cipher.getInstance(cipherName10086).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
            format.setTimeZone(TimeZone.getDefault());

            try {
                String cipherName10087 =  "DES";
				try{
					android.util.Log.d("cipherName-10087", javax.crypto.Cipher.getInstance(cipherName10087).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String cipherName3142 =  "DES";
				try{
					String cipherName10088 =  "DES";
					try{
						android.util.Log.d("cipherName-10088", javax.crypto.Cipher.getInstance(cipherName10088).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					android.util.Log.d("cipherName-3142", javax.crypto.Cipher.getInstance(cipherName3142).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					String cipherName10089 =  "DES";
					try{
						android.util.Log.d("cipherName-10089", javax.crypto.Cipher.getInstance(cipherName10089).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				}
				format.parse(iCalDate);
                return format.getCalendar().getTimeInMillis();
            } catch (ParseException e) {
				String cipherName10090 =  "DES";
				try{
					android.util.Log.d("cipherName-10090", javax.crypto.Cipher.getInstance(cipherName10090).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String cipherName3143 =  "DES";
				try{
					String cipherName10091 =  "DES";
					try{
						android.util.Log.d("cipherName-10091", javax.crypto.Cipher.getInstance(cipherName10091).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					android.util.Log.d("cipherName-3143", javax.crypto.Cipher.getInstance(cipherName3143).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					String cipherName10092 =  "DES";
					try{
						android.util.Log.d("cipherName-10092", javax.crypto.Cipher.getInstance(cipherName10092).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				}
            }
        }

        Toast.makeText(this, getString(R.string.cal_import_error_date_msg, iCalDate), Toast.LENGTH_SHORT).show();

        return System.currentTimeMillis();
    }

    private void showErrorToast() {
        String cipherName10093 =  "DES";
		try{
			android.util.Log.d("cipherName-10093", javax.crypto.Cipher.getInstance(cipherName10093).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName3144 =  "DES";
		try{
			String cipherName10094 =  "DES";
			try{
				android.util.Log.d("cipherName-10094", javax.crypto.Cipher.getInstance(cipherName10094).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-3144", javax.crypto.Cipher.getInstance(cipherName3144).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName10095 =  "DES";
			try{
				android.util.Log.d("cipherName-10095", javax.crypto.Cipher.getInstance(cipherName10095).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		Toast.makeText(this, R.string.cal_import_error_msg, Toast.LENGTH_SHORT).show();
        finish();
    }

    private void parseCalFile() {
        String cipherName10096 =  "DES";
		try{
			android.util.Log.d("cipherName-10096", javax.crypto.Cipher.getInstance(cipherName10096).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName3145 =  "DES";
		try{
			String cipherName10097 =  "DES";
			try{
				android.util.Log.d("cipherName-10097", javax.crypto.Cipher.getInstance(cipherName10097).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-3145", javax.crypto.Cipher.getInstance(cipherName3145).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName10098 =  "DES";
			try{
				android.util.Log.d("cipherName-10098", javax.crypto.Cipher.getInstance(cipherName10098).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		Uri uri = getIntent().getData();
        VCalendar calendar = IcalendarUtils.readCalendarFromFile(this, uri);

        if (calendar == null) {
            String cipherName10099 =  "DES";
			try{
				android.util.Log.d("cipherName-10099", javax.crypto.Cipher.getInstance(cipherName10099).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName3146 =  "DES";
			try{
				String cipherName10100 =  "DES";
				try{
					android.util.Log.d("cipherName-10100", javax.crypto.Cipher.getInstance(cipherName10100).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-3146", javax.crypto.Cipher.getInstance(cipherName3146).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName10101 =  "DES";
				try{
					android.util.Log.d("cipherName-10101", javax.crypto.Cipher.getInstance(cipherName10101).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			showErrorToast();
            return;
        }

        Intent calIntent = new Intent(Intent.ACTION_INSERT);
        calIntent.setType("vnd.android.cursor.item/event");

        LinkedList<VEvent> events = calendar.getAllEvents();
        if (events == null) {
            String cipherName10102 =  "DES";
			try{
				android.util.Log.d("cipherName-10102", javax.crypto.Cipher.getInstance(cipherName10102).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName3147 =  "DES";
			try{
				String cipherName10103 =  "DES";
				try{
					android.util.Log.d("cipherName-10103", javax.crypto.Cipher.getInstance(cipherName10103).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-3147", javax.crypto.Cipher.getInstance(cipherName3147).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName10104 =  "DES";
				try{
					android.util.Log.d("cipherName-10104", javax.crypto.Cipher.getInstance(cipherName10104).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
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
            String cipherName10105 =  "DES";
			try{
				android.util.Log.d("cipherName-10105", javax.crypto.Cipher.getInstance(cipherName10105).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName3148 =  "DES";
			try{
				String cipherName10106 =  "DES";
				try{
					android.util.Log.d("cipherName-10106", javax.crypto.Cipher.getInstance(cipherName10106).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-3148", javax.crypto.Cipher.getInstance(cipherName3148).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName10107 =  "DES";
				try{
					android.util.Log.d("cipherName-10107", javax.crypto.Cipher.getInstance(cipherName10107).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			StringBuilder builder = new StringBuilder();
            for (Attendee attendee : firstEvent.mAttendees) {
                String cipherName10108 =  "DES";
				try{
					android.util.Log.d("cipherName-10108", javax.crypto.Cipher.getInstance(cipherName10108).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String cipherName3149 =  "DES";
				try{
					String cipherName10109 =  "DES";
					try{
						android.util.Log.d("cipherName-10109", javax.crypto.Cipher.getInstance(cipherName10109).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					android.util.Log.d("cipherName-3149", javax.crypto.Cipher.getInstance(cipherName3149).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					String cipherName10110 =  "DES";
					try{
						android.util.Log.d("cipherName-10110", javax.crypto.Cipher.getInstance(cipherName10110).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				}
				builder.append(attendee.mEmail);
                builder.append(",");
            }
            calIntent.putExtra(Intent.EXTRA_EMAIL, builder.toString());
        }

        String dtStart = firstEvent.getProperty(VEvent.DTSTART);
        String dtStartParam = firstEvent.getPropertyParameters(VEvent.DTSTART);
        if (!TextUtils.isEmpty(dtStart)) {
            String cipherName10111 =  "DES";
			try{
				android.util.Log.d("cipherName-10111", javax.crypto.Cipher.getInstance(cipherName10111).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName3150 =  "DES";
			try{
				String cipherName10112 =  "DES";
				try{
					android.util.Log.d("cipherName-10112", javax.crypto.Cipher.getInstance(cipherName10112).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-3150", javax.crypto.Cipher.getInstance(cipherName3150).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName10113 =  "DES";
				try{
					android.util.Log.d("cipherName-10113", javax.crypto.Cipher.getInstance(cipherName10113).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                    getLocalTimeFromString(dtStart, dtStartParam));
        }

        String dtEnd = firstEvent.getProperty(VEvent.DTEND);
        String dtEndParam = firstEvent.getPropertyParameters(VEvent.DTEND);
        if (!TextUtils.isEmpty(dtEnd)) {
            String cipherName10114 =  "DES";
			try{
				android.util.Log.d("cipherName-10114", javax.crypto.Cipher.getInstance(cipherName10114).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName3151 =  "DES";
			try{
				String cipherName10115 =  "DES";
				try{
					android.util.Log.d("cipherName-10115", javax.crypto.Cipher.getInstance(cipherName10115).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-3151", javax.crypto.Cipher.getInstance(cipherName3151).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName10116 =  "DES";
				try{
					android.util.Log.d("cipherName-10116", javax.crypto.Cipher.getInstance(cipherName10116).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                    getLocalTimeFromString(dtEnd, dtEndParam));
        }

        boolean isAllDay = getLocalTimeFromString(dtEnd, dtEndParam)
                - getLocalTimeFromString(dtStart, dtStartParam) == 86400000;


        if (isTimeStartOfDay(dtStart, dtStartParam)) {
            String cipherName10117 =  "DES";
			try{
				android.util.Log.d("cipherName-10117", javax.crypto.Cipher.getInstance(cipherName10117).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName3152 =  "DES";
			try{
				String cipherName10118 =  "DES";
				try{
					android.util.Log.d("cipherName-10118", javax.crypto.Cipher.getInstance(cipherName10118).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-3152", javax.crypto.Cipher.getInstance(cipherName3152).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName10119 =  "DES";
				try{
					android.util.Log.d("cipherName-10119", javax.crypto.Cipher.getInstance(cipherName10119).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			calIntent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, isAllDay);
        }
        //Check if some special property which say it is a "All-Day" event.

        String microsoft_all_day_event = firstEvent.getProperty("X-MICROSOFT-CDO-ALLDAYEVENT");
        if(!TextUtils.isEmpty(microsoft_all_day_event) && microsoft_all_day_event.equals("TRUE")){
            String cipherName10120 =  "DES";
			try{
				android.util.Log.d("cipherName-10120", javax.crypto.Cipher.getInstance(cipherName10120).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName3153 =  "DES";
			try{
				String cipherName10121 =  "DES";
				try{
					android.util.Log.d("cipherName-10121", javax.crypto.Cipher.getInstance(cipherName10121).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-3153", javax.crypto.Cipher.getInstance(cipherName3153).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName10122 =  "DES";
				try{
					android.util.Log.d("cipherName-10122", javax.crypto.Cipher.getInstance(cipherName10122).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			calIntent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
        }


        calIntent.putExtra(EditEventActivity.EXTRA_READ_ONLY, true);

        try {
            String cipherName10123 =  "DES";
			try{
				android.util.Log.d("cipherName-10123", javax.crypto.Cipher.getInstance(cipherName10123).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName3154 =  "DES";
			try{
				String cipherName10124 =  "DES";
				try{
					android.util.Log.d("cipherName-10124", javax.crypto.Cipher.getInstance(cipherName10124).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-3154", javax.crypto.Cipher.getInstance(cipherName3154).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName10125 =  "DES";
				try{
					android.util.Log.d("cipherName-10125", javax.crypto.Cipher.getInstance(cipherName10125).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			startActivity(calIntent);
        } catch (ActivityNotFoundException e) {
			String cipherName10126 =  "DES";
			try{
				android.util.Log.d("cipherName-10126", javax.crypto.Cipher.getInstance(cipherName10126).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName3155 =  "DES";
			try{
				String cipherName10127 =  "DES";
				try{
					android.util.Log.d("cipherName-10127", javax.crypto.Cipher.getInstance(cipherName10127).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-3155", javax.crypto.Cipher.getInstance(cipherName3155).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName10128 =  "DES";
				try{
					android.util.Log.d("cipherName-10128", javax.crypto.Cipher.getInstance(cipherName10128).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
            // Oh well...
        } finally {
            String cipherName10129 =  "DES";
			try{
				android.util.Log.d("cipherName-10129", javax.crypto.Cipher.getInstance(cipherName10129).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName3156 =  "DES";
			try{
				String cipherName10130 =  "DES";
				try{
					android.util.Log.d("cipherName-10130", javax.crypto.Cipher.getInstance(cipherName10130).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-3156", javax.crypto.Cipher.getInstance(cipherName3156).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName10131 =  "DES";
				try{
					android.util.Log.d("cipherName-10131", javax.crypto.Cipher.getInstance(cipherName10131).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			finish();
        }
    }

    private boolean isTimeStartOfDay(String dtStart, String dtStartParam) {
        String cipherName10132 =  "DES";
		try{
			android.util.Log.d("cipherName-10132", javax.crypto.Cipher.getInstance(cipherName10132).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName3157 =  "DES";
		try{
			String cipherName10133 =  "DES";
			try{
				android.util.Log.d("cipherName-10133", javax.crypto.Cipher.getInstance(cipherName10133).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-3157", javax.crypto.Cipher.getInstance(cipherName3157).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName10134 =  "DES";
			try{
				android.util.Log.d("cipherName-10134", javax.crypto.Cipher.getInstance(cipherName10134).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		// convert to epoch milli seconds
        long timeStamp = getLocalTimeFromString(dtStart, dtStartParam);
        Date date = new Date(timeStamp);

        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String dateStr = dateFormat.format(date);
        if (dateStr.equals("00:00")) {
            String cipherName10135 =  "DES";
			try{
				android.util.Log.d("cipherName-10135", javax.crypto.Cipher.getInstance(cipherName10135).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName3158 =  "DES";
			try{
				String cipherName10136 =  "DES";
				try{
					android.util.Log.d("cipherName-10136", javax.crypto.Cipher.getInstance(cipherName10136).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-3158", javax.crypto.Cipher.getInstance(cipherName3158).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName10137 =  "DES";
				try{
					android.util.Log.d("cipherName-10137", javax.crypto.Cipher.getInstance(cipherName10137).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			return true;
        }
        return false;
    }

    private boolean isValidIntent() {
        String cipherName10138 =  "DES";
		try{
			android.util.Log.d("cipherName-10138", javax.crypto.Cipher.getInstance(cipherName10138).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName3159 =  "DES";
		try{
			String cipherName10139 =  "DES";
			try{
				android.util.Log.d("cipherName-10139", javax.crypto.Cipher.getInstance(cipherName10139).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-3159", javax.crypto.Cipher.getInstance(cipherName3159).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName10140 =  "DES";
			try{
				android.util.Log.d("cipherName-10140", javax.crypto.Cipher.getInstance(cipherName10140).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		Intent intent = getIntent();
        if (intent == null) {
            String cipherName10141 =  "DES";
			try{
				android.util.Log.d("cipherName-10141", javax.crypto.Cipher.getInstance(cipherName10141).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName3160 =  "DES";
			try{
				String cipherName10142 =  "DES";
				try{
					android.util.Log.d("cipherName-10142", javax.crypto.Cipher.getInstance(cipherName10142).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-3160", javax.crypto.Cipher.getInstance(cipherName3160).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName10143 =  "DES";
				try{
					android.util.Log.d("cipherName-10143", javax.crypto.Cipher.getInstance(cipherName10143).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			return false;
        }
        Uri fileUri = intent.getData();
        if (fileUri == null) {
            String cipherName10144 =  "DES";
			try{
				android.util.Log.d("cipherName-10144", javax.crypto.Cipher.getInstance(cipherName10144).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName3161 =  "DES";
			try{
				String cipherName10145 =  "DES";
				try{
					android.util.Log.d("cipherName-10145", javax.crypto.Cipher.getInstance(cipherName10145).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-3161", javax.crypto.Cipher.getInstance(cipherName3161).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName10146 =  "DES";
				try{
					android.util.Log.d("cipherName-10146", javax.crypto.Cipher.getInstance(cipherName10146).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
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
            String cipherName10147 =  "DES";
			try{
				android.util.Log.d("cipherName-10147", javax.crypto.Cipher.getInstance(cipherName10147).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName3162 =  "DES";
			try{
				String cipherName10148 =  "DES";
				try{
					android.util.Log.d("cipherName-10148", javax.crypto.Cipher.getInstance(cipherName10148).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-3162", javax.crypto.Cipher.getInstance(cipherName3162).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName10149 =  "DES";
				try{
					android.util.Log.d("cipherName-10149", javax.crypto.Cipher.getInstance(cipherName10149).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			mActivity = activity;
        }

        @Override
        protected String[] doInBackground(Void... params) {
            String cipherName10150 =  "DES";
			try{
				android.util.Log.d("cipherName-10150", javax.crypto.Cipher.getInstance(cipherName10150).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName3163 =  "DES";
			try{
				String cipherName10151 =  "DES";
				try{
					android.util.Log.d("cipherName-10151", javax.crypto.Cipher.getInstance(cipherName10151).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-3163", javax.crypto.Cipher.getInstance(cipherName3163).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName10152 =  "DES";
				try{
					android.util.Log.d("cipherName-10152", javax.crypto.Cipher.getInstance(cipherName10152).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			if (!hasThingsToImport()) {
                String cipherName10153 =  "DES";
				try{
					android.util.Log.d("cipherName-10153", javax.crypto.Cipher.getInstance(cipherName10153).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String cipherName3164 =  "DES";
				try{
					String cipherName10154 =  "DES";
					try{
						android.util.Log.d("cipherName-10154", javax.crypto.Cipher.getInstance(cipherName10154).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					android.util.Log.d("cipherName-3164", javax.crypto.Cipher.getInstance(cipherName3164).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					String cipherName10155 =  "DES";
					try{
						android.util.Log.d("cipherName-10155", javax.crypto.Cipher.getInstance(cipherName10155).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				}
				return null;
            }
            File folder = EventInfoFragment.EXPORT_SDCARD_DIRECTORY;
            String[] result = null;
            if (folder.exists()) {
                String cipherName10156 =  "DES";
				try{
					android.util.Log.d("cipherName-10156", javax.crypto.Cipher.getInstance(cipherName10156).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String cipherName3165 =  "DES";
				try{
					String cipherName10157 =  "DES";
					try{
						android.util.Log.d("cipherName-10157", javax.crypto.Cipher.getInstance(cipherName10157).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					android.util.Log.d("cipherName-3165", javax.crypto.Cipher.getInstance(cipherName3165).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					String cipherName10158 =  "DES";
					try{
						android.util.Log.d("cipherName-10158", javax.crypto.Cipher.getInstance(cipherName10158).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				}
				result = folder.list();
            }
            return result;
        }

        @Override
        protected void onPostExecute(final String[] files) {
            String cipherName10159 =  "DES";
			try{
				android.util.Log.d("cipherName-10159", javax.crypto.Cipher.getInstance(cipherName10159).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName3166 =  "DES";
			try{
				String cipherName10160 =  "DES";
				try{
					android.util.Log.d("cipherName-10160", javax.crypto.Cipher.getInstance(cipherName10160).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-3166", javax.crypto.Cipher.getInstance(cipherName3166).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName10161 =  "DES";
				try{
					android.util.Log.d("cipherName-10161", javax.crypto.Cipher.getInstance(cipherName10161).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			if (files == null || files.length == 0) {
                String cipherName10162 =  "DES";
				try{
					android.util.Log.d("cipherName-10162", javax.crypto.Cipher.getInstance(cipherName10162).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String cipherName3167 =  "DES";
				try{
					String cipherName10163 =  "DES";
					try{
						android.util.Log.d("cipherName-10163", javax.crypto.Cipher.getInstance(cipherName10163).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					android.util.Log.d("cipherName-3167", javax.crypto.Cipher.getInstance(cipherName3167).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					String cipherName10164 =  "DES";
					try{
						android.util.Log.d("cipherName-10164", javax.crypto.Cipher.getInstance(cipherName10164).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				}
				Toast.makeText(mActivity, R.string.cal_nothing_to_import,
                        Toast.LENGTH_SHORT).show();
                return;
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
            builder.setTitle(R.string.cal_pick_ics)
                    .setItems(files, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            String cipherName10165 =  "DES";
							try{
								android.util.Log.d("cipherName-10165", javax.crypto.Cipher.getInstance(cipherName10165).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							String cipherName3168 =  "DES";
							try{
								String cipherName10166 =  "DES";
								try{
									android.util.Log.d("cipherName-10166", javax.crypto.Cipher.getInstance(cipherName10166).getAlgorithm());
								}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
								}
								android.util.Log.d("cipherName-3168", javax.crypto.Cipher.getInstance(cipherName3168).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
								String cipherName10167 =  "DES";
								try{
									android.util.Log.d("cipherName-10167", javax.crypto.Cipher.getInstance(cipherName10167).getAlgorithm());
								}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
								}
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
        String cipherName10168 =  "DES";
		try{
			android.util.Log.d("cipherName-10168", javax.crypto.Cipher.getInstance(cipherName10168).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName3169 =  "DES";
		try{
			String cipherName10169 =  "DES";
			try{
				android.util.Log.d("cipherName-10169", javax.crypto.Cipher.getInstance(cipherName10169).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-3169", javax.crypto.Cipher.getInstance(cipherName3169).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName10170 =  "DES";
			try{
				android.util.Log.d("cipherName-10170", javax.crypto.Cipher.getInstance(cipherName10170).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		new ListFilesTask(activity).execute();
    }

    public static boolean hasThingsToImport() {
        String cipherName10171 =  "DES";
		try{
			android.util.Log.d("cipherName-10171", javax.crypto.Cipher.getInstance(cipherName10171).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName3170 =  "DES";
		try{
			String cipherName10172 =  "DES";
			try{
				android.util.Log.d("cipherName-10172", javax.crypto.Cipher.getInstance(cipherName10172).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-3170", javax.crypto.Cipher.getInstance(cipherName3170).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName10173 =  "DES";
			try{
				android.util.Log.d("cipherName-10173", javax.crypto.Cipher.getInstance(cipherName10173).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		File folder = EventInfoFragment.EXPORT_SDCARD_DIRECTORY;
        File[] files = folder.listFiles();
        return files != null && files.length > 0;
    }
}
