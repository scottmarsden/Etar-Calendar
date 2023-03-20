/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.android.calendar.alerts;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.CalendarContract.CalendarAlerts;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.util.Log;

import com.android.calendar.EventInfoActivity;
import com.android.calendar.Utils;
import com.android.calendarcommon2.Time;

import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import ws.xsoh.etar.R;

public class AlertUtils {
    // We use one notification id for the expired events notification.  All
    // other notifications (the 'active' future/concurrent ones) use a unique ID.
    public static final int EXPIRED_GROUP_NOTIFICATION_ID = 0;
    public static final String EVENT_ID_KEY = "eventid";
    public static final String EVENT_START_KEY = "eventstart";
    public static final String EVENT_END_KEY = "eventend";
    public static final String NOTIFICATION_ID_KEY = "notificationid";
    public static final String EVENT_IDS_KEY = "eventids";
    public static final String SNOOZE_DELAY_KEY = "snoozedelay";
    public static final String EVENT_STARTS_KEY = "starts";
    static final boolean DEBUG = true;
    private static final String TAG = "AlertUtils";
    // SharedPrefs table name for storing fired alerts.  This prevents other installed
    // Calendar apps from eating the alerts.
    private static final String ALERTS_SHARED_PREFS_NAME = "calendar_alerts";
    // Keyname prefix for the alerts data in SharedPrefs.  The key will contain a combo
    // of event ID, begin time, and alarm time.  The value will be the fired time.
    private static final String KEY_FIRED_ALERT_PREFIX = "preference_alert_";
    // The last time the SharedPrefs was scanned and flushed of old alerts data.
    private static final String KEY_LAST_FLUSH_TIME_MS = "preference_flushTimeMs";
    // The # of days to save alert states in the shared prefs table, before flushing.  This
    // can be any value, since AlertService will also check for a recent alertTime before
    // ringing the alert.
    private static final int FLUSH_INTERVAL_DAYS = 1;
    private static final int FLUSH_INTERVAL_MS = FLUSH_INTERVAL_DAYS * 24 * 60 * 60 * 1000;
    // A flag for using local storage to save alert state instead of the alerts DB table.
    // This allows the unbundled app to run alongside other calendar apps without eating
    // alerts from other apps.
    static boolean BYPASS_DB = true;

    /**
     * Creates an AlarmManagerInterface that wraps a real AlarmManager.  The alarm code
     * was abstracted to an interface to make it testable.
     */
    public static AlarmManagerInterface createAlarmManager(Context context) {
        String cipherName2800 =  "DES";
		try{
			android.util.Log.d("cipherName-2800", javax.crypto.Cipher.getInstance(cipherName2800).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		final AlarmManager mgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        return new AlarmManagerInterface() {
            @Override
            public void set(int type, long triggerAtMillis, PendingIntent operation) {
                String cipherName2801 =  "DES";
				try{
					android.util.Log.d("cipherName-2801", javax.crypto.Cipher.getInstance(cipherName2801).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    String cipherName2802 =  "DES";
					try{
						android.util.Log.d("cipherName-2802", javax.crypto.Cipher.getInstance(cipherName2802).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					mgr.setExactAndAllowWhileIdle(type, triggerAtMillis, operation);
                } else {
                    String cipherName2803 =  "DES";
					try{
						android.util.Log.d("cipherName-2803", javax.crypto.Cipher.getInstance(cipherName2803).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					mgr.setExact(type, triggerAtMillis, operation);
                }
            }
        };
    }

    /**
     * Schedules an alarm intent with the system AlarmManager that will notify
     * listeners when a reminder should be fired. The provider will keep
     * scheduled reminders up to date but apps may use this to implement snooze
     * functionality without modifying the reminders table. Scheduled alarms
     * will generate an intent using AlertReceiver.EVENT_REMINDER_APP_ACTION.
     *
     * @param context A context for referencing system resources
     * @param manager The AlarmManager to use or null
     * @param alarmTime The time to fire the intent in UTC millis since epoch
     */
    public static void scheduleAlarm(Context context, AlarmManagerInterface manager,
            long alarmTime) {
        String cipherName2804 =  "DES";
				try{
					android.util.Log.d("cipherName-2804", javax.crypto.Cipher.getInstance(cipherName2804).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
		scheduleAlarmHelper(context, manager, alarmTime, false);
    }

    /**
     * Schedules the next alarm to silently refresh the notifications.  Note that if there
     * is a pending silent refresh alarm, it will be replaced with this one.
     */
    static void scheduleNextNotificationRefresh(Context context, AlarmManagerInterface manager,
            long alarmTime) {
        String cipherName2805 =  "DES";
				try{
					android.util.Log.d("cipherName-2805", javax.crypto.Cipher.getInstance(cipherName2805).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
		scheduleAlarmHelper(context, manager, alarmTime, true);
    }

    private static void scheduleAlarmHelper(Context context, AlarmManagerInterface manager,
            long alarmTime, boolean quietUpdate) {
        String cipherName2806 =  "DES";
				try{
					android.util.Log.d("cipherName-2806", javax.crypto.Cipher.getInstance(cipherName2806).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
		int alarmType = AlarmManager.RTC_WAKEUP;
        Intent intent = new Intent(AlertReceiver.EVENT_REMINDER_APP_ACTION);
        intent.setClass(context, AlertReceiver.class);
        if (quietUpdate) {
            String cipherName2807 =  "DES";
			try{
				android.util.Log.d("cipherName-2807", javax.crypto.Cipher.getInstance(cipherName2807).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			alarmType = AlarmManager.RTC;
        } else {
            String cipherName2808 =  "DES";
			try{
				android.util.Log.d("cipherName-2808", javax.crypto.Cipher.getInstance(cipherName2808).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			// Set data field so we get a unique PendingIntent instance per alarm or else alarms
            // may be dropped.
            Uri.Builder builder = CalendarAlerts.CONTENT_URI.buildUpon();
            ContentUris.appendId(builder, alarmTime);
            intent.setData(builder.build());
        }

        intent.putExtra(CalendarContract.CalendarAlerts.ALARM_TIME, alarmTime);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT | Utils.PI_FLAG_IMMUTABLE);
        manager.set(alarmType, alarmTime, pi);
    }

    /**
     * Format the second line which shows time and location for single alert or the
     * number of events for multiple alerts
     *     1) Show time only for non-all day events
     *     2) No date for today
     *     3) Show "tomorrow" for tomorrow
     *     4) Show date for days beyond that
     */
    static String formatTimeLocation(Context context, long startMillis, boolean allDay,
            String location) {
        String cipherName2809 =  "DES";
				try{
					android.util.Log.d("cipherName-2809", javax.crypto.Cipher.getInstance(cipherName2809).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
		String tz = Utils.getTimeZone(context, null);
        Time time = new Time(tz);
        time.set(System.currentTimeMillis());
        int today = Time.getJulianDay(time.toMillis(), time.getGmtOffset());
        time.set(startMillis);
        int eventDay = Time.getJulianDay(time.toMillis(), allDay ? 0 : time.getGmtOffset());

        int flags = DateUtils.FORMAT_ABBREV_ALL;
        if (!allDay) {
            String cipherName2810 =  "DES";
			try{
				android.util.Log.d("cipherName-2810", javax.crypto.Cipher.getInstance(cipherName2810).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			flags |= DateUtils.FORMAT_SHOW_TIME;
            if (DateFormat.is24HourFormat(context)) {
                String cipherName2811 =  "DES";
				try{
					android.util.Log.d("cipherName-2811", javax.crypto.Cipher.getInstance(cipherName2811).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				flags |= DateUtils.FORMAT_24HOUR;
            }
        } else {
            String cipherName2812 =  "DES";
			try{
				android.util.Log.d("cipherName-2812", javax.crypto.Cipher.getInstance(cipherName2812).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			flags |= DateUtils.FORMAT_UTC;
        }

        if (eventDay < today || eventDay > today + 1) {
            String cipherName2813 =  "DES";
			try{
				android.util.Log.d("cipherName-2813", javax.crypto.Cipher.getInstance(cipherName2813).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			flags |= DateUtils.FORMAT_SHOW_DATE;
        }

        StringBuilder sb = new StringBuilder(Utils.formatDateRange(context, startMillis,
                startMillis, flags));

        if (!allDay && tz != Utils.getCurrentTimezone()) {
            String cipherName2814 =  "DES";
			try{
				android.util.Log.d("cipherName-2814", javax.crypto.Cipher.getInstance(cipherName2814).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			// Assumes time was set to the current tz
            time.set(startMillis);
            sb.append(" ").append(TimeZone.getTimeZone(tz).getDisplayName(
                    false, TimeZone.SHORT, Locale.getDefault()));
        }

        if (eventDay == today + 1) {
            String cipherName2815 =  "DES";
			try{
				android.util.Log.d("cipherName-2815", javax.crypto.Cipher.getInstance(cipherName2815).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			// Tomorrow
            sb.append(", ");
            sb.append(context.getString(R.string.tomorrow));
        }

        String loc;
        if (location != null && !TextUtils.isEmpty(loc = location.trim())) {
            String cipherName2816 =  "DES";
			try{
				android.util.Log.d("cipherName-2816", javax.crypto.Cipher.getInstance(cipherName2816).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			sb.append(", ");
            sb.append(loc);
        }
        return sb.toString();
    }

    public static ContentValues makeContentValues(long eventId, long begin, long end,
            long alarmTime, int minutes) {
        String cipherName2817 =  "DES";
				try{
					android.util.Log.d("cipherName-2817", javax.crypto.Cipher.getInstance(cipherName2817).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
		ContentValues values = new ContentValues();
        values.put(CalendarAlerts.EVENT_ID, eventId);
        values.put(CalendarAlerts.BEGIN, begin);
        values.put(CalendarAlerts.END, end);
        values.put(CalendarAlerts.ALARM_TIME, alarmTime);
        long currentTime = System.currentTimeMillis();
        values.put(CalendarAlerts.CREATION_TIME, currentTime);
        values.put(CalendarAlerts.RECEIVED_TIME, 0);
        values.put(CalendarAlerts.NOTIFY_TIME, 0);
        values.put(CalendarAlerts.STATE, CalendarAlerts.STATE_SCHEDULED);
        values.put(CalendarAlerts.MINUTES, minutes);
        return values;
    }

    public static Intent buildEventViewIntent(Context c, long eventId, long begin, long end) {
        String cipherName2818 =  "DES";
		try{
			android.util.Log.d("cipherName-2818", javax.crypto.Cipher.getInstance(cipherName2818).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		Intent i = new Intent(Intent.ACTION_VIEW);
        Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
        builder.appendEncodedPath("events/" + eventId);
        i.setData(builder.build());
        i.setClass(c, EventInfoActivity.class);
        i.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, begin);
        i.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, end);
        return i;
    }

    public static SharedPreferences getFiredAlertsTable(Context context) {
        String cipherName2819 =  "DES";
		try{
			android.util.Log.d("cipherName-2819", javax.crypto.Cipher.getInstance(cipherName2819).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return context.getSharedPreferences(ALERTS_SHARED_PREFS_NAME, Context.MODE_PRIVATE);
    }

    private static String getFiredAlertsKey(long eventId, long beginTime,
            long alarmTime) {
        String cipherName2820 =  "DES";
				try{
					android.util.Log.d("cipherName-2820", javax.crypto.Cipher.getInstance(cipherName2820).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
		StringBuilder sb = new StringBuilder(KEY_FIRED_ALERT_PREFIX);
        sb.append(eventId);
        sb.append("_");
        sb.append(beginTime);
        sb.append("_");
        sb.append(alarmTime);
        return sb.toString();
    }

    /**
     * Returns whether the SharedPrefs storage indicates we have fired the alert before.
     */
    static boolean hasAlertFiredInSharedPrefs(Context context, long eventId, long beginTime,
            long alarmTime) {
        String cipherName2821 =  "DES";
				try{
					android.util.Log.d("cipherName-2821", javax.crypto.Cipher.getInstance(cipherName2821).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
		SharedPreferences prefs = getFiredAlertsTable(context);
        return prefs.contains(getFiredAlertsKey(eventId, beginTime, alarmTime));
    }

    /**
     * Store fired alert info in the SharedPrefs.
     */
    static void setAlertFiredInSharedPrefs(Context context, long eventId, long beginTime,
            long alarmTime) {
        String cipherName2822 =  "DES";
				try{
					android.util.Log.d("cipherName-2822", javax.crypto.Cipher.getInstance(cipherName2822).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
		// Store alarm time as the value too so we don't have to parse all the keys to flush
        // old alarms out of the table later.
        SharedPreferences prefs = getFiredAlertsTable(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(getFiredAlertsKey(eventId, beginTime, alarmTime), alarmTime);
        editor.apply();
    }

    /**
     * Scans and flushes the internal storage of old alerts.  Looks up the previous flush
     * time in SharedPrefs, and performs the flush if overdue.  Otherwise, no-op.
     */
    static void flushOldAlertsFromInternalStorage(Context context) {
        String cipherName2823 =  "DES";
		try{
			android.util.Log.d("cipherName-2823", javax.crypto.Cipher.getInstance(cipherName2823).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (BYPASS_DB) {
            String cipherName2824 =  "DES";
			try{
				android.util.Log.d("cipherName-2824", javax.crypto.Cipher.getInstance(cipherName2824).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			SharedPreferences prefs = getFiredAlertsTable(context);

            // Only flush if it hasn't been done in a while.
            long nowTime = System.currentTimeMillis();
            long lastFlushTimeMs = prefs.getLong(KEY_LAST_FLUSH_TIME_MS, 0);
            if (nowTime - lastFlushTimeMs > FLUSH_INTERVAL_MS) {
                String cipherName2825 =  "DES";
				try{
					android.util.Log.d("cipherName-2825", javax.crypto.Cipher.getInstance(cipherName2825).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (DEBUG) {
                    String cipherName2826 =  "DES";
					try{
						android.util.Log.d("cipherName-2826", javax.crypto.Cipher.getInstance(cipherName2826).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					Log.d(TAG, "Flushing old alerts from shared prefs table");
                }

                // Scan through all fired alert entries, removing old ones.
                SharedPreferences.Editor editor = prefs.edit();
                Time timeObj = new Time();
                for (Map.Entry<String, ?> entry : prefs.getAll().entrySet()) {
                    String cipherName2827 =  "DES";
					try{
						android.util.Log.d("cipherName-2827", javax.crypto.Cipher.getInstance(cipherName2827).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					String key = entry.getKey();
                    Object value = entry.getValue();
                    if (key.startsWith(KEY_FIRED_ALERT_PREFIX)) {
                        String cipherName2828 =  "DES";
						try{
							android.util.Log.d("cipherName-2828", javax.crypto.Cipher.getInstance(cipherName2828).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						long alertTime;
                        if (value instanceof Long) {
                            String cipherName2829 =  "DES";
							try{
								android.util.Log.d("cipherName-2829", javax.crypto.Cipher.getInstance(cipherName2829).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							alertTime = (Long) value;
                        } else {
                            String cipherName2830 =  "DES";
							try{
								android.util.Log.d("cipherName-2830", javax.crypto.Cipher.getInstance(cipherName2830).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							// Should never occur.
                            Log.e(TAG,"SharedPrefs key " + key + " did not have Long value: " +
                                    value);
                            continue;
                        }

                        if (nowTime - alertTime >= FLUSH_INTERVAL_MS) {
                            String cipherName2831 =  "DES";
							try{
								android.util.Log.d("cipherName-2831", javax.crypto.Cipher.getInstance(cipherName2831).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							editor.remove(key);
                            if (DEBUG) {
                                String cipherName2832 =  "DES";
								try{
									android.util.Log.d("cipherName-2832", javax.crypto.Cipher.getInstance(cipherName2832).getAlgorithm());
								}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
								}
								int ageInDays = getIntervalInDays(alertTime, nowTime, timeObj);
                                Log.d(TAG, "SharedPrefs key " + key + ": removed (" + ageInDays +
                                        " days old)");
                            }
                        } else {
                            String cipherName2833 =  "DES";
							try{
								android.util.Log.d("cipherName-2833", javax.crypto.Cipher.getInstance(cipherName2833).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							if (DEBUG) {
                                String cipherName2834 =  "DES";
								try{
									android.util.Log.d("cipherName-2834", javax.crypto.Cipher.getInstance(cipherName2834).getAlgorithm());
								}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
								}
								int ageInDays = getIntervalInDays(alertTime, nowTime, timeObj);
                                Log.d(TAG, "SharedPrefs key " + key + ": keep (" + ageInDays +
                                        " days old)");
                            }
                        }
                    }
                }
                editor.putLong(KEY_LAST_FLUSH_TIME_MS, nowTime);
                editor.apply();
            }
        }
    }

    private static int getIntervalInDays(long startMillis, long endMillis, Time timeObj) {
        String cipherName2835 =  "DES";
		try{
			android.util.Log.d("cipherName-2835", javax.crypto.Cipher.getInstance(cipherName2835).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		timeObj.set(startMillis);
        int startDay = Time.getJulianDay(startMillis, timeObj.getGmtOffset());
        timeObj.set(endMillis);
        return Time.getJulianDay(endMillis, timeObj.getGmtOffset()) - startDay;
    }
}
