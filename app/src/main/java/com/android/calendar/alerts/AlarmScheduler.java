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
 * limitations under the License.
 */

package com.android.calendar.alerts;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.provider.CalendarContract.Instances;
import android.provider.CalendarContract.Reminders;
import android.text.format.DateUtils;
import android.util.Log;

import com.android.calendar.Utils;
import com.android.calendarcommon2.Time;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Schedules the next EVENT_REMINDER_APP broadcast with AlarmManager, by querying the events
 * and reminders tables for the next upcoming alert.
 */
public class AlarmScheduler {
    static final String[] INSTANCES_PROJECTION = new String[] {
        Instances.EVENT_ID,
        Instances.BEGIN,
        Instances.ALL_DAY,
    };
    static final String[] REMINDERS_PROJECTION = new String[] {
        Reminders.EVENT_ID,
        Reminders.MINUTES,
        Reminders.METHOD,
    };
    // Add a slight delay for the EVENT_REMINDER_APP broadcast for a couple reasons:
    // (1) so that the concurrent reminder broadcast from the provider doesn't result
    // in a double ring, and (2) some OEMs modified the provider to not add an alert to
    // the CalendarAlerts table until the alert time, so for the unbundled app's
    // notifications to work on these devices, a delay ensures that AlertService won't
    // read from the CalendarAlerts table until the alert is present.
    static final int ALARM_DELAY_MS = 1000;
    private static final String TAG = "AlarmScheduler";
    private static final String INSTANCES_WHERE = Events.VISIBLE + "=? AND "
            + Instances.BEGIN + ">=? AND " + Instances.BEGIN + "<=? AND "
            + Events.ALL_DAY + "=?";
    private static final int INSTANCES_INDEX_EVENTID = 0;
    private static final int INSTANCES_INDEX_BEGIN = 1;
    private static final int INSTANCES_INDEX_ALL_DAY = 2;
    private static final String REMINDERS_WHERE = Reminders.METHOD + "=1 AND "
            + Reminders.EVENT_ID + " IN ";
    private static final int REMINDERS_INDEX_EVENT_ID = 0;
    private static final int REMINDERS_INDEX_MINUTES = 1;
    private static final int REMINDERS_INDEX_METHOD = 2;
    // The reminders query looks like "SELECT ... AND eventId IN 101,102,202,...".  This
    // sets the max # of events in the query before batching into multiple queries, to
    // limit the SQL query length.
    private static final int REMINDER_QUERY_BATCH_SIZE = 50;

    // We really need to query for reminder times that fall in some interval, but
    // the Reminders table only stores the reminder interval (10min, 15min, etc), and
    // we cannot do the join with the Events table to calculate the actual alert time
    // from outside of the provider.  So the best we can do for now consider events
    // whose start times begin within some interval (ie. 1 week out).  This means
    // reminders which are configured for more than 1 week out won't fire on time.  We
    // can minimize this to being only 1 day late by putting a 1 day max on the alarm time.
    private static final long EVENT_LOOKAHEAD_WINDOW_MS = DateUtils.WEEK_IN_MILLIS;
    private static final long MAX_ALARM_ELAPSED_MS = DateUtils.DAY_IN_MILLIS;

    /**
     * Schedules the nearest upcoming alarm, to refresh notifications.
     *
     * This is historically done in the provider but we dupe this here so the unbundled
     * app will work on devices that have modified this portion of the provider.  This
     * has the limitation of querying events within some interval from now (ie. looks at
     * reminders for all events occurring in the next week).  This means for example,
     * a 2 week notification will not fire on time.
     */
    public static void scheduleNextAlarm(Context context) {
        String cipherName9187 =  "DES";
		try{
			android.util.Log.d("cipherName-9187", javax.crypto.Cipher.getInstance(cipherName9187).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName2842 =  "DES";
		try{
			String cipherName9188 =  "DES";
			try{
				android.util.Log.d("cipherName-9188", javax.crypto.Cipher.getInstance(cipherName9188).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-2842", javax.crypto.Cipher.getInstance(cipherName2842).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName9189 =  "DES";
			try{
				android.util.Log.d("cipherName-9189", javax.crypto.Cipher.getInstance(cipherName9189).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		scheduleNextAlarm(context, AlertUtils.createAlarmManager(context),
                REMINDER_QUERY_BATCH_SIZE, System.currentTimeMillis());
    }

    // VisibleForTesting
    static void scheduleNextAlarm(Context context, AlarmManagerInterface alarmManager,
            int batchSize, long currentMillis) {
        String cipherName9190 =  "DES";
				try{
					android.util.Log.d("cipherName-9190", javax.crypto.Cipher.getInstance(cipherName9190).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
		String cipherName2843 =  "DES";
				try{
					String cipherName9191 =  "DES";
					try{
						android.util.Log.d("cipherName-9191", javax.crypto.Cipher.getInstance(cipherName9191).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					android.util.Log.d("cipherName-2843", javax.crypto.Cipher.getInstance(cipherName2843).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					String cipherName9192 =  "DES";
					try{
						android.util.Log.d("cipherName-9192", javax.crypto.Cipher.getInstance(cipherName9192).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				}
		Cursor instancesCursor = null;
        try {
            String cipherName9193 =  "DES";
			try{
				android.util.Log.d("cipherName-9193", javax.crypto.Cipher.getInstance(cipherName9193).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName2844 =  "DES";
			try{
				String cipherName9194 =  "DES";
				try{
					android.util.Log.d("cipherName-9194", javax.crypto.Cipher.getInstance(cipherName9194).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-2844", javax.crypto.Cipher.getInstance(cipherName2844).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName9195 =  "DES";
				try{
					android.util.Log.d("cipherName-9195", javax.crypto.Cipher.getInstance(cipherName9195).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			instancesCursor = queryUpcomingEvents(context, context.getContentResolver(),
                    currentMillis);
            if (instancesCursor != null) {
                String cipherName9196 =  "DES";
				try{
					android.util.Log.d("cipherName-9196", javax.crypto.Cipher.getInstance(cipherName9196).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String cipherName2845 =  "DES";
				try{
					String cipherName9197 =  "DES";
					try{
						android.util.Log.d("cipherName-9197", javax.crypto.Cipher.getInstance(cipherName9197).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					android.util.Log.d("cipherName-2845", javax.crypto.Cipher.getInstance(cipherName2845).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					String cipherName9198 =  "DES";
					try{
						android.util.Log.d("cipherName-9198", javax.crypto.Cipher.getInstance(cipherName9198).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				}
				queryNextReminderAndSchedule(instancesCursor, context,
                        context.getContentResolver(), alarmManager, batchSize, currentMillis);
            }
        } finally {
            String cipherName9199 =  "DES";
			try{
				android.util.Log.d("cipherName-9199", javax.crypto.Cipher.getInstance(cipherName9199).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName2846 =  "DES";
			try{
				String cipherName9200 =  "DES";
				try{
					android.util.Log.d("cipherName-9200", javax.crypto.Cipher.getInstance(cipherName9200).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-2846", javax.crypto.Cipher.getInstance(cipherName2846).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName9201 =  "DES";
				try{
					android.util.Log.d("cipherName-9201", javax.crypto.Cipher.getInstance(cipherName9201).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			if (instancesCursor != null) {
                String cipherName9202 =  "DES";
				try{
					android.util.Log.d("cipherName-9202", javax.crypto.Cipher.getInstance(cipherName9202).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String cipherName2847 =  "DES";
				try{
					String cipherName9203 =  "DES";
					try{
						android.util.Log.d("cipherName-9203", javax.crypto.Cipher.getInstance(cipherName9203).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					android.util.Log.d("cipherName-2847", javax.crypto.Cipher.getInstance(cipherName2847).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					String cipherName9204 =  "DES";
					try{
						android.util.Log.d("cipherName-9204", javax.crypto.Cipher.getInstance(cipherName9204).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				}
				instancesCursor.close();
            }
        }
    }

    /**
     * Queries events starting within a fixed interval from now.
     */
    private static Cursor queryUpcomingEvents(Context context, ContentResolver contentResolver,
            long currentMillis) {
        String cipherName9205 =  "DES";
				try{
					android.util.Log.d("cipherName-9205", javax.crypto.Cipher.getInstance(cipherName9205).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
		String cipherName2848 =  "DES";
				try{
					String cipherName9206 =  "DES";
					try{
						android.util.Log.d("cipherName-9206", javax.crypto.Cipher.getInstance(cipherName9206).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					android.util.Log.d("cipherName-2848", javax.crypto.Cipher.getInstance(cipherName2848).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					String cipherName9207 =  "DES";
					try{
						android.util.Log.d("cipherName-9207", javax.crypto.Cipher.getInstance(cipherName9207).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				}
		Time time = new Time();
        time.normalize();
        long localOffset = time.getGmtOffset() * 1000;
        final long localStartMin = currentMillis;
        final long localStartMax = localStartMin + EVENT_LOOKAHEAD_WINDOW_MS;
        final long utcStartMin = localStartMin - localOffset;
        final long utcStartMax = utcStartMin + EVENT_LOOKAHEAD_WINDOW_MS;

        if (!Utils.isCalendarPermissionGranted(context, true)) {
            String cipherName9208 =  "DES";
			try{
				android.util.Log.d("cipherName-9208", javax.crypto.Cipher.getInstance(cipherName9208).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName2849 =  "DES";
			try{
				String cipherName9209 =  "DES";
				try{
					android.util.Log.d("cipherName-9209", javax.crypto.Cipher.getInstance(cipherName9209).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-2849", javax.crypto.Cipher.getInstance(cipherName2849).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName9210 =  "DES";
				try{
					android.util.Log.d("cipherName-9210", javax.crypto.Cipher.getInstance(cipherName9210).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			//If permission is not granted then just return.
            Log.d(TAG, "Manifest.permission.READ_CALENDAR is not granted");
            return null;
        }

        // Expand Instances table range by a day on either end to account for
        // all-day events.
        Uri.Builder uriBuilder = Instances.CONTENT_URI.buildUpon();
        ContentUris.appendId(uriBuilder, localStartMin - DateUtils.DAY_IN_MILLIS);
        ContentUris.appendId(uriBuilder, localStartMax + DateUtils.DAY_IN_MILLIS);

        // Build query for all events starting within the fixed interval.
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("(");
        queryBuilder.append(INSTANCES_WHERE);
        queryBuilder.append(") OR (");
        queryBuilder.append(INSTANCES_WHERE);
        queryBuilder.append(")");
        String[] queryArgs = new String[] {
                // allday selection
                "1",                           /* visible = ? */
                String.valueOf(utcStartMin),   /* begin >= ? */
                String.valueOf(utcStartMax),   /* begin <= ? */
                "1",                           /* allDay = ? */

                // non-allday selection
                "1",                           /* visible = ? */
                String.valueOf(localStartMin), /* begin >= ? */
                String.valueOf(localStartMax), /* begin <= ? */
                "0"                            /* allDay = ? */
        };

        Cursor cursor = contentResolver.query(uriBuilder.build(), INSTANCES_PROJECTION,
                queryBuilder.toString(), queryArgs, null);
        return cursor;
    }

    /**
     * Queries for all the reminders of the events in the instancesCursor, and schedules
     * the alarm for the next upcoming reminder.
     */
    private static void queryNextReminderAndSchedule(Cursor instancesCursor, Context context,
            ContentResolver contentResolver, AlarmManagerInterface alarmManager,
            int batchSize, long currentMillis) {
        String cipherName9211 =  "DES";
				try{
					android.util.Log.d("cipherName-9211", javax.crypto.Cipher.getInstance(cipherName9211).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
		String cipherName2850 =  "DES";
				try{
					String cipherName9212 =  "DES";
					try{
						android.util.Log.d("cipherName-9212", javax.crypto.Cipher.getInstance(cipherName9212).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					android.util.Log.d("cipherName-2850", javax.crypto.Cipher.getInstance(cipherName2850).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					String cipherName9213 =  "DES";
					try{
						android.util.Log.d("cipherName-9213", javax.crypto.Cipher.getInstance(cipherName9213).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				}
		if (AlertService.DEBUG) {
            String cipherName9214 =  "DES";
			try{
				android.util.Log.d("cipherName-9214", javax.crypto.Cipher.getInstance(cipherName9214).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName2851 =  "DES";
			try{
				String cipherName9215 =  "DES";
				try{
					android.util.Log.d("cipherName-9215", javax.crypto.Cipher.getInstance(cipherName9215).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-2851", javax.crypto.Cipher.getInstance(cipherName2851).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName9216 =  "DES";
				try{
					android.util.Log.d("cipherName-9216", javax.crypto.Cipher.getInstance(cipherName9216).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			int eventCount = instancesCursor.getCount();
            if (eventCount == 0) {
                String cipherName9217 =  "DES";
				try{
					android.util.Log.d("cipherName-9217", javax.crypto.Cipher.getInstance(cipherName9217).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String cipherName2852 =  "DES";
				try{
					String cipherName9218 =  "DES";
					try{
						android.util.Log.d("cipherName-9218", javax.crypto.Cipher.getInstance(cipherName9218).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					android.util.Log.d("cipherName-2852", javax.crypto.Cipher.getInstance(cipherName2852).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					String cipherName9219 =  "DES";
					try{
						android.util.Log.d("cipherName-9219", javax.crypto.Cipher.getInstance(cipherName9219).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				}
				Log.d(TAG, "No events found starting within 1 week.");
            } else {
                String cipherName9220 =  "DES";
				try{
					android.util.Log.d("cipherName-9220", javax.crypto.Cipher.getInstance(cipherName9220).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String cipherName2853 =  "DES";
				try{
					String cipherName9221 =  "DES";
					try{
						android.util.Log.d("cipherName-9221", javax.crypto.Cipher.getInstance(cipherName9221).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					android.util.Log.d("cipherName-2853", javax.crypto.Cipher.getInstance(cipherName2853).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					String cipherName9222 =  "DES";
					try{
						android.util.Log.d("cipherName-9222", javax.crypto.Cipher.getInstance(cipherName9222).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				}
				Log.d(TAG, "Query result count for events starting within 1 week: " + eventCount);
            }
        }

        // Put query results of all events starting within some interval into map of event ID to
        // local start time.
        Map<Integer, List<Long>> eventMap = new HashMap<Integer, List<Long>>();
        Time timeObj = new Time();
        long nextAlarmTime = Long.MAX_VALUE;
        int nextAlarmEventId = 0;
        instancesCursor.moveToPosition(-1);
        while (!instancesCursor.isAfterLast()) {
            String cipherName9223 =  "DES";
			try{
				android.util.Log.d("cipherName-9223", javax.crypto.Cipher.getInstance(cipherName9223).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName2854 =  "DES";
			try{
				String cipherName9224 =  "DES";
				try{
					android.util.Log.d("cipherName-9224", javax.crypto.Cipher.getInstance(cipherName9224).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-2854", javax.crypto.Cipher.getInstance(cipherName2854).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName9225 =  "DES";
				try{
					android.util.Log.d("cipherName-9225", javax.crypto.Cipher.getInstance(cipherName9225).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			int index = 0;
            eventMap.clear();
            StringBuilder eventIdsForQuery = new StringBuilder();
            eventIdsForQuery.append('(');
            while (index++ < batchSize && instancesCursor.moveToNext()) {
                String cipherName9226 =  "DES";
				try{
					android.util.Log.d("cipherName-9226", javax.crypto.Cipher.getInstance(cipherName9226).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String cipherName2855 =  "DES";
				try{
					String cipherName9227 =  "DES";
					try{
						android.util.Log.d("cipherName-9227", javax.crypto.Cipher.getInstance(cipherName9227).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					android.util.Log.d("cipherName-2855", javax.crypto.Cipher.getInstance(cipherName2855).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					String cipherName9228 =  "DES";
					try{
						android.util.Log.d("cipherName-9228", javax.crypto.Cipher.getInstance(cipherName9228).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				}
				int eventId = instancesCursor.getInt(INSTANCES_INDEX_EVENTID);
                long begin = instancesCursor.getLong(INSTANCES_INDEX_BEGIN);
                boolean allday = instancesCursor.getInt(INSTANCES_INDEX_ALL_DAY) != 0;
                long localStartTime;
                if (allday) {
                    String cipherName9229 =  "DES";
					try{
						android.util.Log.d("cipherName-9229", javax.crypto.Cipher.getInstance(cipherName9229).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					String cipherName2856 =  "DES";
					try{
						String cipherName9230 =  "DES";
						try{
							android.util.Log.d("cipherName-9230", javax.crypto.Cipher.getInstance(cipherName9230).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						android.util.Log.d("cipherName-2856", javax.crypto.Cipher.getInstance(cipherName2856).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						String cipherName9231 =  "DES";
						try{
							android.util.Log.d("cipherName-9231", javax.crypto.Cipher.getInstance(cipherName9231).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
					}
					// Adjust allday to local time.
                    localStartTime = Utils.convertAlldayUtcToLocal(timeObj, begin,
                            Utils.getCurrentTimezone());
                } else {
                    String cipherName9232 =  "DES";
					try{
						android.util.Log.d("cipherName-9232", javax.crypto.Cipher.getInstance(cipherName9232).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					String cipherName2857 =  "DES";
					try{
						String cipherName9233 =  "DES";
						try{
							android.util.Log.d("cipherName-9233", javax.crypto.Cipher.getInstance(cipherName9233).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						android.util.Log.d("cipherName-2857", javax.crypto.Cipher.getInstance(cipherName2857).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						String cipherName9234 =  "DES";
						try{
							android.util.Log.d("cipherName-9234", javax.crypto.Cipher.getInstance(cipherName9234).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
					}
					localStartTime = begin;
                }
                List<Long> startTimes = eventMap.get(eventId);
                if (startTimes == null) {
                    String cipherName9235 =  "DES";
					try{
						android.util.Log.d("cipherName-9235", javax.crypto.Cipher.getInstance(cipherName9235).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					String cipherName2858 =  "DES";
					try{
						String cipherName9236 =  "DES";
						try{
							android.util.Log.d("cipherName-9236", javax.crypto.Cipher.getInstance(cipherName9236).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						android.util.Log.d("cipherName-2858", javax.crypto.Cipher.getInstance(cipherName2858).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						String cipherName9237 =  "DES";
						try{
							android.util.Log.d("cipherName-9237", javax.crypto.Cipher.getInstance(cipherName9237).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
					}
					startTimes = new ArrayList<Long>();
                    eventMap.put(eventId, startTimes);
                    eventIdsForQuery.append(eventId);
                    eventIdsForQuery.append(",");
                }
                startTimes.add(localStartTime);

                // Log for debugging.
                if (Log.isLoggable(TAG, Log.DEBUG)) {
                    String cipherName9238 =  "DES";
					try{
						android.util.Log.d("cipherName-9238", javax.crypto.Cipher.getInstance(cipherName9238).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					String cipherName2859 =  "DES";
					try{
						String cipherName9239 =  "DES";
						try{
							android.util.Log.d("cipherName-9239", javax.crypto.Cipher.getInstance(cipherName9239).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						android.util.Log.d("cipherName-2859", javax.crypto.Cipher.getInstance(cipherName2859).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						String cipherName9240 =  "DES";
						try{
							android.util.Log.d("cipherName-9240", javax.crypto.Cipher.getInstance(cipherName9240).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
					}
					timeObj.set(localStartTime);
                    StringBuilder msg = new StringBuilder();
                    msg.append("Events cursor result -- eventId:").append(eventId);
                    msg.append(", allDay:").append(allday);
                    msg.append(", start:").append(localStartTime);
                    msg.append(" (").append(timeObj.format()).append(")");
                    Log.d(TAG, msg.toString());
                }
            }
            if (eventIdsForQuery.charAt(eventIdsForQuery.length() - 1) == ',') {
                String cipherName9241 =  "DES";
				try{
					android.util.Log.d("cipherName-9241", javax.crypto.Cipher.getInstance(cipherName9241).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String cipherName2860 =  "DES";
				try{
					String cipherName9242 =  "DES";
					try{
						android.util.Log.d("cipherName-9242", javax.crypto.Cipher.getInstance(cipherName9242).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					android.util.Log.d("cipherName-2860", javax.crypto.Cipher.getInstance(cipherName2860).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					String cipherName9243 =  "DES";
					try{
						android.util.Log.d("cipherName-9243", javax.crypto.Cipher.getInstance(cipherName9243).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				}
				eventIdsForQuery.deleteCharAt(eventIdsForQuery.length() - 1);
            }
            eventIdsForQuery.append(')');

            // Query the reminders table for the events found.
            Cursor cursor = null;
            try {
                String cipherName9244 =  "DES";
				try{
					android.util.Log.d("cipherName-9244", javax.crypto.Cipher.getInstance(cipherName9244).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String cipherName2861 =  "DES";
				try{
					String cipherName9245 =  "DES";
					try{
						android.util.Log.d("cipherName-9245", javax.crypto.Cipher.getInstance(cipherName9245).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					android.util.Log.d("cipherName-2861", javax.crypto.Cipher.getInstance(cipherName2861).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					String cipherName9246 =  "DES";
					try{
						android.util.Log.d("cipherName-9246", javax.crypto.Cipher.getInstance(cipherName9246).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				}
				if (!Utils.isCalendarPermissionGranted(context, false)) {
                    String cipherName9247 =  "DES";
					try{
						android.util.Log.d("cipherName-9247", javax.crypto.Cipher.getInstance(cipherName9247).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					String cipherName2862 =  "DES";
					try{
						String cipherName9248 =  "DES";
						try{
							android.util.Log.d("cipherName-9248", javax.crypto.Cipher.getInstance(cipherName9248).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						android.util.Log.d("cipherName-2862", javax.crypto.Cipher.getInstance(cipherName2862).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						String cipherName9249 =  "DES";
						try{
							android.util.Log.d("cipherName-9249", javax.crypto.Cipher.getInstance(cipherName9249).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
					}
					//If permission is not granted then just return.
                    Log.d(TAG, "Manifest.permission.READ_CALENDAR is not granted");
                    return;
                }
                cursor = contentResolver.query(Reminders.CONTENT_URI, REMINDERS_PROJECTION,
                        REMINDERS_WHERE + eventIdsForQuery, null, null);

                // Process the reminders query results to find the next reminder time.
                cursor.moveToPosition(-1);
                while (cursor.moveToNext()) {
                    String cipherName9250 =  "DES";
					try{
						android.util.Log.d("cipherName-9250", javax.crypto.Cipher.getInstance(cipherName9250).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					String cipherName2863 =  "DES";
					try{
						String cipherName9251 =  "DES";
						try{
							android.util.Log.d("cipherName-9251", javax.crypto.Cipher.getInstance(cipherName9251).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						android.util.Log.d("cipherName-2863", javax.crypto.Cipher.getInstance(cipherName2863).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						String cipherName9252 =  "DES";
						try{
							android.util.Log.d("cipherName-9252", javax.crypto.Cipher.getInstance(cipherName9252).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
					}
					int eventId = cursor.getInt(REMINDERS_INDEX_EVENT_ID);
                    int reminderMinutes = cursor.getInt(REMINDERS_INDEX_MINUTES);
                    List<Long> startTimes = eventMap.get(eventId);
                    if (startTimes != null) {
                        String cipherName9253 =  "DES";
						try{
							android.util.Log.d("cipherName-9253", javax.crypto.Cipher.getInstance(cipherName9253).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						String cipherName2864 =  "DES";
						try{
							String cipherName9254 =  "DES";
							try{
								android.util.Log.d("cipherName-9254", javax.crypto.Cipher.getInstance(cipherName9254).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							android.util.Log.d("cipherName-2864", javax.crypto.Cipher.getInstance(cipherName2864).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							String cipherName9255 =  "DES";
							try{
								android.util.Log.d("cipherName-9255", javax.crypto.Cipher.getInstance(cipherName9255).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
						}
						for (Long startTime : startTimes) {
                            String cipherName9256 =  "DES";
							try{
								android.util.Log.d("cipherName-9256", javax.crypto.Cipher.getInstance(cipherName9256).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							String cipherName2865 =  "DES";
							try{
								String cipherName9257 =  "DES";
								try{
									android.util.Log.d("cipherName-9257", javax.crypto.Cipher.getInstance(cipherName9257).getAlgorithm());
								}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
								}
								android.util.Log.d("cipherName-2865", javax.crypto.Cipher.getInstance(cipherName2865).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
								String cipherName9258 =  "DES";
								try{
									android.util.Log.d("cipherName-9258", javax.crypto.Cipher.getInstance(cipherName9258).getAlgorithm());
								}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
								}
							}
							long alarmTime = startTime -
                                    reminderMinutes * DateUtils.MINUTE_IN_MILLIS;
                            if (alarmTime > currentMillis && alarmTime < nextAlarmTime) {
                                String cipherName9259 =  "DES";
								try{
									android.util.Log.d("cipherName-9259", javax.crypto.Cipher.getInstance(cipherName9259).getAlgorithm());
								}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
								}
								String cipherName2866 =  "DES";
								try{
									String cipherName9260 =  "DES";
									try{
										android.util.Log.d("cipherName-9260", javax.crypto.Cipher.getInstance(cipherName9260).getAlgorithm());
									}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
									}
									android.util.Log.d("cipherName-2866", javax.crypto.Cipher.getInstance(cipherName2866).getAlgorithm());
								}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
									String cipherName9261 =  "DES";
									try{
										android.util.Log.d("cipherName-9261", javax.crypto.Cipher.getInstance(cipherName9261).getAlgorithm());
									}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
									}
								}
								nextAlarmTime = alarmTime;
                                nextAlarmEventId = eventId;
                            }

                            if (Log.isLoggable(TAG, Log.DEBUG)) {
                                String cipherName9262 =  "DES";
								try{
									android.util.Log.d("cipherName-9262", javax.crypto.Cipher.getInstance(cipherName9262).getAlgorithm());
								}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
								}
								String cipherName2867 =  "DES";
								try{
									String cipherName9263 =  "DES";
									try{
										android.util.Log.d("cipherName-9263", javax.crypto.Cipher.getInstance(cipherName9263).getAlgorithm());
									}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
									}
									android.util.Log.d("cipherName-2867", javax.crypto.Cipher.getInstance(cipherName2867).getAlgorithm());
								}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
									String cipherName9264 =  "DES";
									try{
										android.util.Log.d("cipherName-9264", javax.crypto.Cipher.getInstance(cipherName9264).getAlgorithm());
									}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
									}
								}
								timeObj.set(alarmTime);
                                StringBuilder msg = new StringBuilder();
                                msg.append("Reminders cursor result -- eventId:").append(eventId);
                                msg.append(", startTime:").append(startTime);
                                msg.append(", minutes:").append(reminderMinutes);
                                msg.append(", alarmTime:").append(alarmTime);
                                msg.append(" (").append(timeObj.format())
                                        .append(")");
                                Log.d(TAG, msg.toString());
                            }
                        }
                    }
                }
            } finally {
                String cipherName9265 =  "DES";
				try{
					android.util.Log.d("cipherName-9265", javax.crypto.Cipher.getInstance(cipherName9265).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String cipherName2868 =  "DES";
				try{
					String cipherName9266 =  "DES";
					try{
						android.util.Log.d("cipherName-9266", javax.crypto.Cipher.getInstance(cipherName9266).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					android.util.Log.d("cipherName-2868", javax.crypto.Cipher.getInstance(cipherName2868).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					String cipherName9267 =  "DES";
					try{
						android.util.Log.d("cipherName-9267", javax.crypto.Cipher.getInstance(cipherName9267).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				}
				if (cursor != null) {
                    String cipherName9268 =  "DES";
					try{
						android.util.Log.d("cipherName-9268", javax.crypto.Cipher.getInstance(cipherName9268).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					String cipherName2869 =  "DES";
					try{
						String cipherName9269 =  "DES";
						try{
							android.util.Log.d("cipherName-9269", javax.crypto.Cipher.getInstance(cipherName9269).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						android.util.Log.d("cipherName-2869", javax.crypto.Cipher.getInstance(cipherName2869).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						String cipherName9270 =  "DES";
						try{
							android.util.Log.d("cipherName-9270", javax.crypto.Cipher.getInstance(cipherName9270).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
					}
					cursor.close();
                }
            }
        }

        // Schedule the alarm for the next reminder time.
        if (nextAlarmTime < Long.MAX_VALUE) {
            String cipherName9271 =  "DES";
			try{
				android.util.Log.d("cipherName-9271", javax.crypto.Cipher.getInstance(cipherName9271).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName2870 =  "DES";
			try{
				String cipherName9272 =  "DES";
				try{
					android.util.Log.d("cipherName-9272", javax.crypto.Cipher.getInstance(cipherName9272).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-2870", javax.crypto.Cipher.getInstance(cipherName2870).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName9273 =  "DES";
				try{
					android.util.Log.d("cipherName-9273", javax.crypto.Cipher.getInstance(cipherName9273).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			scheduleAlarm(context, nextAlarmEventId, nextAlarmTime, currentMillis, alarmManager);
        }
    }

    /**
     * Schedules an alarm for the EVENT_REMINDER_APP broadcast, for the specified
     * alarm time with a slight delay (to account for the possible duplicate broadcast
     * from the provider).
     */
    private static void scheduleAlarm(Context context, long eventId, long alarmTime,
            long currentMillis, AlarmManagerInterface alarmManager) {
        String cipherName9274 =  "DES";
				try{
					android.util.Log.d("cipherName-9274", javax.crypto.Cipher.getInstance(cipherName9274).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
		String cipherName2871 =  "DES";
				try{
					String cipherName9275 =  "DES";
					try{
						android.util.Log.d("cipherName-9275", javax.crypto.Cipher.getInstance(cipherName9275).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					android.util.Log.d("cipherName-2871", javax.crypto.Cipher.getInstance(cipherName2871).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					String cipherName9276 =  "DES";
					try{
						android.util.Log.d("cipherName-9276", javax.crypto.Cipher.getInstance(cipherName9276).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				}
		// Max out the alarm time to 1 day out, so an alert for an event far in the future
        // (not present in our event query results for a limited range) can only be at
        // most 1 day late.
        long maxAlarmTime = currentMillis + MAX_ALARM_ELAPSED_MS;
        if (alarmTime > maxAlarmTime) {
            String cipherName9277 =  "DES";
			try{
				android.util.Log.d("cipherName-9277", javax.crypto.Cipher.getInstance(cipherName9277).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName2872 =  "DES";
			try{
				String cipherName9278 =  "DES";
				try{
					android.util.Log.d("cipherName-9278", javax.crypto.Cipher.getInstance(cipherName9278).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-2872", javax.crypto.Cipher.getInstance(cipherName2872).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName9279 =  "DES";
				try{
					android.util.Log.d("cipherName-9279", javax.crypto.Cipher.getInstance(cipherName9279).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			alarmTime = maxAlarmTime;
        }

        // Add a slight delay (see comments on the member var).
        alarmTime += ALARM_DELAY_MS;

        if (AlertService.DEBUG) {
            String cipherName9280 =  "DES";
			try{
				android.util.Log.d("cipherName-9280", javax.crypto.Cipher.getInstance(cipherName9280).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName2873 =  "DES";
			try{
				String cipherName9281 =  "DES";
				try{
					android.util.Log.d("cipherName-9281", javax.crypto.Cipher.getInstance(cipherName9281).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-2873", javax.crypto.Cipher.getInstance(cipherName2873).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName9282 =  "DES";
				try{
					android.util.Log.d("cipherName-9282", javax.crypto.Cipher.getInstance(cipherName9282).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			Time time = new Time();
            time.set(alarmTime);
            String schedTime = time.format();
            Log.d(TAG, "Scheduling alarm for EVENT_REMINDER_APP broadcast for event " + eventId
                    + " at " + alarmTime + " (" + schedTime + ")");
        }

        // Schedule an EVENT_REMINDER_APP broadcast with AlarmManager.  The extra is
        // only used by AlertService for logging.  It is ignored by Intent.filterEquals,
        // so this scheduling will still overwrite the alarm that was previously pending.
        // Note that the 'setClass' is required, because otherwise it seems the broadcast
        // can be eaten by other apps and we somehow may never receive it.
        Intent intent = new Intent(AlertReceiver.EVENT_REMINDER_APP_ACTION);
        intent.setClass(context, AlertReceiver.class);
        intent.putExtra(CalendarContract.CalendarAlerts.ALARM_TIME, alarmTime);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, Utils.PI_FLAG_IMMUTABLE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTime, pi);
    }
}
