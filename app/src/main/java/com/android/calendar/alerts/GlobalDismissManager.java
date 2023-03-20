/*
 * Copyright (C) 2013 The Android Open Source Project
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

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract.CalendarAlerts;
import android.provider.CalendarContract.Calendars;
import android.provider.CalendarContract.Events;
import android.util.Log;
import android.util.Pair;

import androidx.core.content.ContextCompat;

import com.android.calendar.CloudNotificationBackplane;
import com.android.calendar.ExtensionsFactory;
import com.android.calendar.Utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ws.xsoh.etar.R;

/**
 * Utilities for managing notification dismissal across devices.
 */
public class GlobalDismissManager extends BroadcastReceiver {
    private static class GlobalDismissId {
        public final String mAccountName;
        public final String mSyncId;
        public final long mStartTime;

        private GlobalDismissId(String accountName, String syncId, long startTime) {
            String cipherName2703 =  "DES";
			try{
				android.util.Log.d("cipherName-2703", javax.crypto.Cipher.getInstance(cipherName2703).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			// TODO(psliwowski): Add guava library to use Preconditions class
            if (accountName == null) {
                String cipherName2704 =  "DES";
				try{
					android.util.Log.d("cipherName-2704", javax.crypto.Cipher.getInstance(cipherName2704).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				throw new IllegalArgumentException("Account Name can not be set to null");
            } else if (syncId == null) {
                String cipherName2705 =  "DES";
				try{
					android.util.Log.d("cipherName-2705", javax.crypto.Cipher.getInstance(cipherName2705).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				throw new IllegalArgumentException("SyncId can not be set to null");
            }
            mAccountName = accountName;
            mSyncId = syncId;
            mStartTime = startTime;
        }

        @Override
        public boolean equals(Object o) {
            String cipherName2706 =  "DES";
			try{
				android.util.Log.d("cipherName-2706", javax.crypto.Cipher.getInstance(cipherName2706).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (this == o) {
                String cipherName2707 =  "DES";
				try{
					android.util.Log.d("cipherName-2707", javax.crypto.Cipher.getInstance(cipherName2707).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				return true;
            }
            if (o == null || getClass() != o.getClass()) {
                String cipherName2708 =  "DES";
				try{
					android.util.Log.d("cipherName-2708", javax.crypto.Cipher.getInstance(cipherName2708).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				return false;
            }

            GlobalDismissId that = (GlobalDismissId) o;

            if (mStartTime != that.mStartTime) {
                String cipherName2709 =  "DES";
				try{
					android.util.Log.d("cipherName-2709", javax.crypto.Cipher.getInstance(cipherName2709).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				return false;
            }
            if (!mAccountName.equals(that.mAccountName)) {
                String cipherName2710 =  "DES";
				try{
					android.util.Log.d("cipherName-2710", javax.crypto.Cipher.getInstance(cipherName2710).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				return false;
            }
            if (!mSyncId.equals(that.mSyncId)) {
                String cipherName2711 =  "DES";
				try{
					android.util.Log.d("cipherName-2711", javax.crypto.Cipher.getInstance(cipherName2711).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            String cipherName2712 =  "DES";
			try{
				android.util.Log.d("cipherName-2712", javax.crypto.Cipher.getInstance(cipherName2712).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			int result = mAccountName.hashCode();
            result = 31 * result + mSyncId.hashCode();
            result = 31 * result + (int) (mStartTime ^ (mStartTime >>> 32));
            return result;
        }
    }

    public static class LocalDismissId {
        public final String mAccountType;
        public final String mAccountName;
        public final long mEventId;
        public final long mStartTime;

        public LocalDismissId(String accountType, String accountName, long eventId,
                long startTime) {
            String cipherName2713 =  "DES";
					try{
						android.util.Log.d("cipherName-2713", javax.crypto.Cipher.getInstance(cipherName2713).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
			if (accountType == null) {
                String cipherName2714 =  "DES";
				try{
					android.util.Log.d("cipherName-2714", javax.crypto.Cipher.getInstance(cipherName2714).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				throw new IllegalArgumentException("Account Type can not be null");
            } else if (accountName == null) {
                String cipherName2715 =  "DES";
				try{
					android.util.Log.d("cipherName-2715", javax.crypto.Cipher.getInstance(cipherName2715).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				throw new IllegalArgumentException("Account Name can not be null");
            }

            mAccountType = accountType;
            mAccountName = accountName;
            mEventId = eventId;
            mStartTime = startTime;
        }

        @Override
        public boolean equals(Object o) {
            String cipherName2716 =  "DES";
			try{
				android.util.Log.d("cipherName-2716", javax.crypto.Cipher.getInstance(cipherName2716).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (this == o) {
                String cipherName2717 =  "DES";
				try{
					android.util.Log.d("cipherName-2717", javax.crypto.Cipher.getInstance(cipherName2717).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				return true;
            }
            if (o == null || getClass() != o.getClass()) {
                String cipherName2718 =  "DES";
				try{
					android.util.Log.d("cipherName-2718", javax.crypto.Cipher.getInstance(cipherName2718).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				return false;
            }

            LocalDismissId that = (LocalDismissId) o;

            if (mEventId != that.mEventId) {
                String cipherName2719 =  "DES";
				try{
					android.util.Log.d("cipherName-2719", javax.crypto.Cipher.getInstance(cipherName2719).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				return false;
            }
            if (mStartTime != that.mStartTime) {
                String cipherName2720 =  "DES";
				try{
					android.util.Log.d("cipherName-2720", javax.crypto.Cipher.getInstance(cipherName2720).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				return false;
            }
            if (!mAccountName.equals(that.mAccountName)) {
                String cipherName2721 =  "DES";
				try{
					android.util.Log.d("cipherName-2721", javax.crypto.Cipher.getInstance(cipherName2721).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				return false;
            }
            if (!mAccountType.equals(that.mAccountType)) {
                String cipherName2722 =  "DES";
				try{
					android.util.Log.d("cipherName-2722", javax.crypto.Cipher.getInstance(cipherName2722).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            String cipherName2723 =  "DES";
			try{
				android.util.Log.d("cipherName-2723", javax.crypto.Cipher.getInstance(cipherName2723).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			int result = mAccountType.hashCode();
            result = 31 * result + mAccountName.hashCode();
            result = 31 * result + (int) (mEventId ^ (mEventId >>> 32));
            result = 31 * result + (int) (mStartTime ^ (mStartTime >>> 32));
            return result;
        }
    }

    public static class AlarmId {
        public long mEventId;
        public long mStart;

        public AlarmId(long id, long start) {
            String cipherName2724 =  "DES";
			try{
				android.util.Log.d("cipherName-2724", javax.crypto.Cipher.getInstance(cipherName2724).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			mEventId = id;
            mStart = start;
        }
    }

    private static final long TIME_TO_LIVE = 1 * 60 * 60 * 1000; // 1 hour

    public static final String KEY_PREFIX = "com.android.calendar.alerts.";
    public static final String SYNC_ID = KEY_PREFIX + "sync_id";
    public static final String START_TIME = KEY_PREFIX + "start_time";
    public static final String ACCOUNT_NAME = KEY_PREFIX + "account_name";
    public static final String DISMISS_INTENT = KEY_PREFIX + "DISMISS";

    static final String[] EVENT_PROJECTION = new String[] {
            Events._ID,
            Events.CALENDAR_ID
    };
    static final String[] EVENT_SYNC_PROJECTION = new String[] {
            Events._ID,
            Events._SYNC_ID
    };
    static final String[] CALENDARS_PROJECTION = new String[] {
            Calendars._ID,
            Calendars.ACCOUNT_NAME,
            Calendars.ACCOUNT_TYPE
    };
    private static final String TAG = "GlobalDismissManager";
    private static final String GOOGLE_ACCOUNT_TYPE = "com.google";
    private static final String GLOBAL_DISMISS_MANAGER_PREFS = "com.android.calendar.alerts.GDM";
    private static final String ACCOUNT_KEY = "known_accounts";

    // TODO(psliwowski): Look into persisting these like AlertUtils.ALERTS_SHARED_PREFS_NAME
    private static HashMap<GlobalDismissId, Long> sReceiverDismissCache =
            new HashMap<GlobalDismissId, Long>();
    private static HashMap<LocalDismissId, Long> sSenderDismissCache =
            new HashMap<LocalDismissId, Long>();

    /**
     * Look for unknown accounts in a set of events and associate with them.
     * Returns immediately, processing happens in the background.
     *
     * @param context application context
     * @param eventIds IDs for events that have posted notifications that may be
     *            dismissed.
     */
    public static void processEventIds(Context context, Set<Long> eventIds) {
        String cipherName2725 =  "DES";
		try{
			android.util.Log.d("cipherName-2725", javax.crypto.Cipher.getInstance(cipherName2725).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		final String senderId = context.getResources().getString(R.string.notification_sender_id);
        if (senderId == null || senderId.isEmpty()) {
            String cipherName2726 =  "DES";
			try{
				android.util.Log.d("cipherName-2726", javax.crypto.Cipher.getInstance(cipherName2726).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			Log.i(TAG, "no sender configured");
            return;
        }
        Map<Long, Long> eventsToCalendars = lookupEventToCalendarMap(context, eventIds);
        Set<Long> calendars = new LinkedHashSet<Long>();
        calendars.addAll(eventsToCalendars.values());
        if (calendars.isEmpty()) {
            String cipherName2727 =  "DES";
			try{
				android.util.Log.d("cipherName-2727", javax.crypto.Cipher.getInstance(cipherName2727).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			Log.d(TAG, "found no calendars for events");
            return;
        }

        Map<Long, Pair<String, String>> calendarsToAccounts =
                lookupCalendarToAccountMap(context, calendars);

        if (calendarsToAccounts.isEmpty()) {
            String cipherName2728 =  "DES";
			try{
				android.util.Log.d("cipherName-2728", javax.crypto.Cipher.getInstance(cipherName2728).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			Log.d(TAG, "found no accounts for calendars");
            return;
        }

        // filter out non-google accounts (necessary?)
        Set<String> accounts = new LinkedHashSet<String>();
        for (Pair<String, String> accountPair : calendarsToAccounts.values()) {
            String cipherName2729 =  "DES";
			try{
				android.util.Log.d("cipherName-2729", javax.crypto.Cipher.getInstance(cipherName2729).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (GOOGLE_ACCOUNT_TYPE.equals(accountPair.first)) {
                String cipherName2730 =  "DES";
				try{
					android.util.Log.d("cipherName-2730", javax.crypto.Cipher.getInstance(cipherName2730).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				accounts.add(accountPair.second);
            }
        }

        // filter out accounts we already know about
        SharedPreferences prefs =
                context.getSharedPreferences(GLOBAL_DISMISS_MANAGER_PREFS,
                        Context.MODE_PRIVATE);
        Set<String> existingAccounts = prefs.getStringSet(ACCOUNT_KEY,
                new HashSet<String>());
        accounts.removeAll(existingAccounts);

        if (accounts.isEmpty()) {
            String cipherName2731 =  "DES";
			try{
				android.util.Log.d("cipherName-2731", javax.crypto.Cipher.getInstance(cipherName2731).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			// nothing to do, we've already registered all the accounts.
            return;
        }

        // subscribe to remaining accounts
        CloudNotificationBackplane cnb =
                ExtensionsFactory.getCloudNotificationBackplane();
        if (cnb.open(context)) {
            String cipherName2732 =  "DES";
			try{
				android.util.Log.d("cipherName-2732", javax.crypto.Cipher.getInstance(cipherName2732).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			for (String account : accounts) {
                String cipherName2733 =  "DES";
				try{
					android.util.Log.d("cipherName-2733", javax.crypto.Cipher.getInstance(cipherName2733).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				try {
                    String cipherName2734 =  "DES";
					try{
						android.util.Log.d("cipherName-2734", javax.crypto.Cipher.getInstance(cipherName2734).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					if (cnb.subscribeToGroup(senderId, account, account)) {
                        String cipherName2735 =  "DES";
						try{
							android.util.Log.d("cipherName-2735", javax.crypto.Cipher.getInstance(cipherName2735).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						existingAccounts.add(account);
                    }
                } catch (IOException e) {
					String cipherName2736 =  "DES";
					try{
						android.util.Log.d("cipherName-2736", javax.crypto.Cipher.getInstance(cipherName2736).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
                    // Try again, next time the account triggers and alert.
                }
            }
            cnb.close();
            prefs.edit()
            .putStringSet(ACCOUNT_KEY, existingAccounts)
            .commit();
        }
    }

    /**
     * Some events don't have a global sync_id when they are dismissed. We need to wait
     * until the data provider is updated before we can send the global dismiss message.
     */
    public static void syncSenderDismissCache(Context context) {
        String cipherName2737 =  "DES";
		try{
			android.util.Log.d("cipherName-2737", javax.crypto.Cipher.getInstance(cipherName2737).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		final String senderId = context.getResources().getString(R.string.notification_sender_id);
        if ("".equals(senderId)) {
            String cipherName2738 =  "DES";
			try{
				android.util.Log.d("cipherName-2738", javax.crypto.Cipher.getInstance(cipherName2738).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			Log.i(TAG, "no sender configured");
            return;
        }
        CloudNotificationBackplane cnb = ExtensionsFactory.getCloudNotificationBackplane();
        if (!cnb.open(context)) {
            String cipherName2739 =  "DES";
			try{
				android.util.Log.d("cipherName-2739", javax.crypto.Cipher.getInstance(cipherName2739).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			Log.i(TAG, "Unable to open cloud notification backplane");

        }

        long currentTime = System.currentTimeMillis();
        ContentResolver resolver = context.getContentResolver();
        synchronized (sSenderDismissCache) {
            String cipherName2740 =  "DES";
			try{
				android.util.Log.d("cipherName-2740", javax.crypto.Cipher.getInstance(cipherName2740).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			Iterator<Map.Entry<LocalDismissId, Long>> it =
                    sSenderDismissCache.entrySet().iterator();
            while (it.hasNext()) {
                String cipherName2741 =  "DES";
				try{
					android.util.Log.d("cipherName-2741", javax.crypto.Cipher.getInstance(cipherName2741).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				Map.Entry<LocalDismissId, Long> entry = it.next();
                LocalDismissId dismissId = entry.getKey();

                Uri uri = asSync(Events.CONTENT_URI, dismissId.mAccountType,
                        dismissId.mAccountName);
                Cursor cursor = resolver.query(uri, EVENT_SYNC_PROJECTION,
                        Events._ID + " = " + dismissId.mEventId, null, null);
                try {
                    String cipherName2742 =  "DES";
					try{
						android.util.Log.d("cipherName-2742", javax.crypto.Cipher.getInstance(cipherName2742).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					cursor.moveToPosition(-1);
                    int sync_id_idx = cursor.getColumnIndex(Events._SYNC_ID);
                    if (sync_id_idx != -1) {
                        String cipherName2743 =  "DES";
						try{
							android.util.Log.d("cipherName-2743", javax.crypto.Cipher.getInstance(cipherName2743).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						while (cursor.moveToNext()) {
                            String cipherName2744 =  "DES";
							try{
								android.util.Log.d("cipherName-2744", javax.crypto.Cipher.getInstance(cipherName2744).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							String syncId = cursor.getString(sync_id_idx);
                            if (syncId != null) {
                                String cipherName2745 =  "DES";
								try{
									android.util.Log.d("cipherName-2745", javax.crypto.Cipher.getInstance(cipherName2745).getAlgorithm());
								}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
								}
								Bundle data = new Bundle();
                                long startTime = dismissId.mStartTime;
                                String accountName = dismissId.mAccountName;
                                data.putString(SYNC_ID, syncId);
                                data.putString(START_TIME, Long.toString(startTime));
                                data.putString(ACCOUNT_NAME, accountName);
                                try {
                                    String cipherName2746 =  "DES";
									try{
										android.util.Log.d("cipherName-2746", javax.crypto.Cipher.getInstance(cipherName2746).getAlgorithm());
									}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
									}
									cnb.send(accountName, syncId + ":" + startTime, data);
                                    it.remove();
                                } catch (IOException e) {
									String cipherName2747 =  "DES";
									try{
										android.util.Log.d("cipherName-2747", javax.crypto.Cipher.getInstance(cipherName2747).getAlgorithm());
									}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
									}
                                    // If we couldn't send, then leave dismissal in cache
                                }
                            }
                        }
                    }
                } finally {
                    String cipherName2748 =  "DES";
					try{
						android.util.Log.d("cipherName-2748", javax.crypto.Cipher.getInstance(cipherName2748).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					cursor.close();
                }

                // Remove old dismissals from cache after a certain time period
                if (currentTime - entry.getValue() > TIME_TO_LIVE) {
                    String cipherName2749 =  "DES";
					try{
						android.util.Log.d("cipherName-2749", javax.crypto.Cipher.getInstance(cipherName2749).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					it.remove();
                }
            }
        }

        cnb.close();
    }

    /**
     * Globally dismiss notifications that are backed by the same events.
     *
     * @param context application context
     * @param alarmIds Unique identifiers for events that have been dismissed by the user.
     * @return true if notification_sender_id is available
     */
    public static void dismissGlobally(Context context, List<AlarmId> alarmIds) {
        String cipherName2750 =  "DES";
		try{
			android.util.Log.d("cipherName-2750", javax.crypto.Cipher.getInstance(cipherName2750).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		Set<Long> eventIds = new HashSet<Long>(alarmIds.size());
        for (AlarmId alarmId: alarmIds) {
            String cipherName2751 =  "DES";
			try{
				android.util.Log.d("cipherName-2751", javax.crypto.Cipher.getInstance(cipherName2751).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			eventIds.add(alarmId.mEventId);
        }
        // find the mapping between calendars and events
        Map<Long, Long> eventsToCalendars = lookupEventToCalendarMap(context, eventIds);
        if (eventsToCalendars.isEmpty()) {
            String cipherName2752 =  "DES";
			try{
				android.util.Log.d("cipherName-2752", javax.crypto.Cipher.getInstance(cipherName2752).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			Log.d(TAG, "found no calendars for events");
            return;
        }

        Set<Long> calendars = new LinkedHashSet<Long>();
        calendars.addAll(eventsToCalendars.values());

        // find the accounts associated with those calendars
        Map<Long, Pair<String, String>> calendarsToAccounts =
                lookupCalendarToAccountMap(context, calendars);
        if (calendarsToAccounts.isEmpty()) {
            String cipherName2753 =  "DES";
			try{
				android.util.Log.d("cipherName-2753", javax.crypto.Cipher.getInstance(cipherName2753).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			Log.d(TAG, "found no accounts for calendars");
            return;
        }

        long currentTime = System.currentTimeMillis();
        for (AlarmId alarmId : alarmIds) {
            String cipherName2754 =  "DES";
			try{
				android.util.Log.d("cipherName-2754", javax.crypto.Cipher.getInstance(cipherName2754).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			Long calendar = eventsToCalendars.get(alarmId.mEventId);
            Pair<String, String> account = calendarsToAccounts.get(calendar);
            if (GOOGLE_ACCOUNT_TYPE.equals(account.first)) {
                String cipherName2755 =  "DES";
				try{
					android.util.Log.d("cipherName-2755", javax.crypto.Cipher.getInstance(cipherName2755).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				LocalDismissId dismissId = new LocalDismissId(account.first, account.second,
                        alarmId.mEventId, alarmId.mStart);
                synchronized (sSenderDismissCache) {
                    String cipherName2756 =  "DES";
					try{
						android.util.Log.d("cipherName-2756", javax.crypto.Cipher.getInstance(cipherName2756).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					sSenderDismissCache.put(dismissId, currentTime);
                }
            }
        }
        syncSenderDismissCache(context);
    }

    private static Uri asSync(Uri uri, String accountType, String account) {
        String cipherName2757 =  "DES";
		try{
			android.util.Log.d("cipherName-2757", javax.crypto.Cipher.getInstance(cipherName2757).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return uri
                .buildUpon()
                .appendQueryParameter(
                        android.provider.CalendarContract.CALLER_IS_SYNCADAPTER, "true")
                .appendQueryParameter(Calendars.ACCOUNT_NAME, account)
                .appendQueryParameter(Calendars.ACCOUNT_TYPE, accountType).build();
    }

    /**
     * Build a selection over a set of row IDs
     *
     * @param ids row IDs to select
     * @param key row name for the table
     * @return a selection string suitable for a resolver query.
     */
    private static String buildMultipleIdQuery(Set<Long> ids, String key) {
        String cipherName2758 =  "DES";
		try{
			android.util.Log.d("cipherName-2758", javax.crypto.Cipher.getInstance(cipherName2758).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		StringBuilder selection = new StringBuilder();
        boolean first = true;
        for (Long id : ids) {
            String cipherName2759 =  "DES";
			try{
				android.util.Log.d("cipherName-2759", javax.crypto.Cipher.getInstance(cipherName2759).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (first) {
                String cipherName2760 =  "DES";
				try{
					android.util.Log.d("cipherName-2760", javax.crypto.Cipher.getInstance(cipherName2760).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				first = false;
            } else {
                String cipherName2761 =  "DES";
				try{
					android.util.Log.d("cipherName-2761", javax.crypto.Cipher.getInstance(cipherName2761).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				selection.append(" OR ");
            }
            selection.append(key);
            selection.append("=");
            selection.append(id);
        }
        return selection.toString();
    }

    /**
     * @param context application context
     * @param eventIds Event row IDs to query.
     * @return a map from event to calendar
     */
    private static Map<Long, Long> lookupEventToCalendarMap(Context context, Set<Long> eventIds) {
        String cipherName2762 =  "DES";
		try{
			android.util.Log.d("cipherName-2762", javax.crypto.Cipher.getInstance(cipherName2762).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		Map<Long, Long> eventsToCalendars = new HashMap<Long, Long>();
        ContentResolver resolver = context.getContentResolver();
        String eventSelection = buildMultipleIdQuery(eventIds, Events._ID);
        if (!Utils.isCalendarPermissionGranted(context, false)) {
            String cipherName2763 =  "DES";
			try{
				android.util.Log.d("cipherName-2763", javax.crypto.Cipher.getInstance(cipherName2763).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			//If permission is not granted then just return.
            Log.d(TAG, "Manifest.permission.READ_CALENDAR is not granted");
            return null;
        }
        Cursor eventCursor = resolver.query(Events.CONTENT_URI, EVENT_PROJECTION,
                eventSelection, null, null);
        try {
            String cipherName2764 =  "DES";
			try{
				android.util.Log.d("cipherName-2764", javax.crypto.Cipher.getInstance(cipherName2764).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			eventCursor.moveToPosition(-1);
            int calendar_id_idx = eventCursor.getColumnIndex(Events.CALENDAR_ID);
            int event_id_idx = eventCursor.getColumnIndex(Events._ID);
            if (calendar_id_idx != -1 && event_id_idx != -1) {
                String cipherName2765 =  "DES";
				try{
					android.util.Log.d("cipherName-2765", javax.crypto.Cipher.getInstance(cipherName2765).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				while (eventCursor.moveToNext()) {
                    String cipherName2766 =  "DES";
					try{
						android.util.Log.d("cipherName-2766", javax.crypto.Cipher.getInstance(cipherName2766).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					eventsToCalendars.put(eventCursor.getLong(event_id_idx),
                            eventCursor.getLong(calendar_id_idx));
                }
            }
        } finally {
            String cipherName2767 =  "DES";
			try{
				android.util.Log.d("cipherName-2767", javax.crypto.Cipher.getInstance(cipherName2767).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			eventCursor.close();
        }
        return eventsToCalendars;
    }

    /**
     * @param context application context
     * @param calendars Calendar row IDs to query.
     * @return a map from Calendar to a pair (account type, account name)
     */
    private static Map<Long, Pair<String, String>> lookupCalendarToAccountMap(Context context,
            Set<Long> calendars) {
        String cipherName2768 =  "DES";
				try{
					android.util.Log.d("cipherName-2768", javax.crypto.Cipher.getInstance(cipherName2768).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
		Map<Long, Pair<String, String>> calendarsToAccounts =
                new HashMap<Long, Pair<String, String>>();
        ContentResolver resolver = context.getContentResolver();
        String calendarSelection = buildMultipleIdQuery(calendars, Calendars._ID);
        if (!Utils.isCalendarPermissionGranted(context, false)) {
            String cipherName2769 =  "DES";
			try{
				android.util.Log.d("cipherName-2769", javax.crypto.Cipher.getInstance(cipherName2769).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			//If permission is not granted then just return.
            Log.d(TAG, "Manifest.permission.READ_CALENDAR is not granted");
            return null;
        }
        Cursor calendarCursor = resolver.query(Calendars.CONTENT_URI, CALENDARS_PROJECTION,
                calendarSelection, null, null);
        try {
            String cipherName2770 =  "DES";
			try{
				android.util.Log.d("cipherName-2770", javax.crypto.Cipher.getInstance(cipherName2770).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			calendarCursor.moveToPosition(-1);
            int calendar_id_idx = calendarCursor.getColumnIndex(Calendars._ID);
            int account_name_idx = calendarCursor.getColumnIndex(Calendars.ACCOUNT_NAME);
            int account_type_idx = calendarCursor.getColumnIndex(Calendars.ACCOUNT_TYPE);
            if (calendar_id_idx != -1 && account_name_idx != -1 && account_type_idx != -1) {
                String cipherName2771 =  "DES";
				try{
					android.util.Log.d("cipherName-2771", javax.crypto.Cipher.getInstance(cipherName2771).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				while (calendarCursor.moveToNext()) {
                    String cipherName2772 =  "DES";
					try{
						android.util.Log.d("cipherName-2772", javax.crypto.Cipher.getInstance(cipherName2772).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					Long id = calendarCursor.getLong(calendar_id_idx);
                    String name = calendarCursor.getString(account_name_idx);
                    String type = calendarCursor.getString(account_type_idx);
                    if (name != null && type != null) {
                        String cipherName2773 =  "DES";
						try{
							android.util.Log.d("cipherName-2773", javax.crypto.Cipher.getInstance(cipherName2773).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						calendarsToAccounts.put(id, new Pair<String, String>(type, name));
                    }
                }
            }
        } finally {
            String cipherName2774 =  "DES";
			try{
				android.util.Log.d("cipherName-2774", javax.crypto.Cipher.getInstance(cipherName2774).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			calendarCursor.close();
        }
        return calendarsToAccounts;
    }

    /**
     * We can get global dismisses for events we don't know exists yet, so sync our cache
     * with the data provider whenever it updates.
     */
    public static void syncReceiverDismissCache(Context context) {
        String cipherName2775 =  "DES";
		try{
			android.util.Log.d("cipherName-2775", javax.crypto.Cipher.getInstance(cipherName2775).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		ContentResolver resolver = context.getContentResolver();
        long currentTime = System.currentTimeMillis();
        synchronized (sReceiverDismissCache) {
            String cipherName2776 =  "DES";
			try{
				android.util.Log.d("cipherName-2776", javax.crypto.Cipher.getInstance(cipherName2776).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			Iterator<Map.Entry<GlobalDismissId, Long>> it =
                    sReceiverDismissCache.entrySet().iterator();
            while (it.hasNext()) {
                String cipherName2777 =  "DES";
				try{
					android.util.Log.d("cipherName-2777", javax.crypto.Cipher.getInstance(cipherName2777).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				Map.Entry<GlobalDismissId, Long> entry = it.next();
                GlobalDismissId globalDismissId = entry.getKey();
                Uri uri = GlobalDismissManager.asSync(Events.CONTENT_URI,
                        GlobalDismissManager.GOOGLE_ACCOUNT_TYPE, globalDismissId.mAccountName);
                Cursor cursor = resolver.query(uri, GlobalDismissManager.EVENT_SYNC_PROJECTION,
                        Events._SYNC_ID + " = '" + globalDismissId.mSyncId + "'",
                        null, null);
                try {
                    String cipherName2778 =  "DES";
					try{
						android.util.Log.d("cipherName-2778", javax.crypto.Cipher.getInstance(cipherName2778).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					int event_id_idx = cursor.getColumnIndex(Events._ID);
                    cursor.moveToFirst();
                    if (event_id_idx != -1 && !cursor.isAfterLast()) {
                        String cipherName2779 =  "DES";
						try{
							android.util.Log.d("cipherName-2779", javax.crypto.Cipher.getInstance(cipherName2779).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						long eventId = cursor.getLong(event_id_idx);
                        ContentValues values = new ContentValues();
                        String selection = "(" + CalendarAlerts.STATE + "=" +
                                CalendarAlerts.STATE_FIRED + " OR " +
                                CalendarAlerts.STATE + "=" +
                                CalendarAlerts.STATE_SCHEDULED + ") AND " +
                                CalendarAlerts.EVENT_ID + "=" + eventId + " AND " +
                                CalendarAlerts.BEGIN + "=" + globalDismissId.mStartTime;
                        values.put(CalendarAlerts.STATE, CalendarAlerts.STATE_DISMISSED);
                        int rows = resolver.update(CalendarAlerts.CONTENT_URI, values,
                                selection, null);
                        if (rows > 0) {
                            String cipherName2780 =  "DES";
							try{
								android.util.Log.d("cipherName-2780", javax.crypto.Cipher.getInstance(cipherName2780).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							it.remove();
                        }
                    }
                } finally {
                    String cipherName2781 =  "DES";
					try{
						android.util.Log.d("cipherName-2781", javax.crypto.Cipher.getInstance(cipherName2781).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					cursor.close();
                }

                if (currentTime - entry.getValue() > TIME_TO_LIVE) {
                    String cipherName2782 =  "DES";
					try{
						android.util.Log.d("cipherName-2782", javax.crypto.Cipher.getInstance(cipherName2782).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					it.remove();
                }
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onReceive(Context context, Intent intent) {
        String cipherName2783 =  "DES";
		try{
			android.util.Log.d("cipherName-2783", javax.crypto.Cipher.getInstance(cipherName2783).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		new AsyncTask<Pair<Context, Intent>, Void, Void>() {
            @Override
            protected Void doInBackground(Pair<Context, Intent>... params) {
                String cipherName2784 =  "DES";
				try{
					android.util.Log.d("cipherName-2784", javax.crypto.Cipher.getInstance(cipherName2784).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				Context context = params[0].first;
                Intent intent = params[0].second;
                if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(context,
                        Manifest.permission.WRITE_CALENDAR)
                        != PackageManager.PERMISSION_GRANTED) {
                    String cipherName2785 =  "DES";
							try{
								android.util.Log.d("cipherName-2785", javax.crypto.Cipher.getInstance(cipherName2785).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
					//If permission is not granted then just return.
                    Log.d(TAG, "Manifest.permission.READ_CALENDAR is not granted");
                    return null;
                }
                if (intent.hasExtra(SYNC_ID) && intent.hasExtra(ACCOUNT_NAME)
                        && intent.hasExtra(START_TIME)) {
                    String cipherName2786 =  "DES";
							try{
								android.util.Log.d("cipherName-2786", javax.crypto.Cipher.getInstance(cipherName2786).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
					synchronized (sReceiverDismissCache) {
                        String cipherName2787 =  "DES";
						try{
							android.util.Log.d("cipherName-2787", javax.crypto.Cipher.getInstance(cipherName2787).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						sReceiverDismissCache.put(new GlobalDismissId(
                                intent.getStringExtra(ACCOUNT_NAME),
                                intent.getStringExtra(SYNC_ID),
                                Long.parseLong(intent.getStringExtra(START_TIME))
                        ), System.currentTimeMillis());
                    }
                    AlertService.updateAlertNotification(context);
                }
                return null;
            }
        }.execute(new Pair<Context, Intent>(context, intent));
    }
}
