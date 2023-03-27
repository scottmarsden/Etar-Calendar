/*
 * Copyright (C) 2007 The Android Open Source Project
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

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.TaskStackBuilder;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.CalendarAlerts;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.android.calendar.AsyncQueryService;
import com.android.calendar.EventInfoActivity;
import com.android.calendar.Utils;
import com.android.calendar.alerts.GlobalDismissManager.AlarmId;

import java.util.LinkedList;
import java.util.List;

import ws.xsoh.etar.R;
import ws.xsoh.etar.databinding.AlertActivityBinding;

/**
 * The alert panel that pops up when there is a calendar event alarm.
 * This activity is started by an intent that specifies an event id.
  */
public class AlertActivity extends Activity implements OnClickListener {
    public static final int INDEX_ROW_ID = 0;
    public static final int INDEX_TITLE = 1;
    public static final int INDEX_EVENT_LOCATION = 2;
    public static final int INDEX_ALL_DAY = 3;
    public static final int INDEX_BEGIN = 4;
    public static final int INDEX_END = 5;
    public static final int INDEX_EVENT_ID = 6;
    public static final int INDEX_COLOR = 7;
    public static final int INDEX_RRULE = 8;
    public static final int INDEX_HAS_ALARM = 9;
    public static final int INDEX_STATE = 10;
    public static final int INDEX_ALARM_TIME = 11;
    private static final String TAG = "AlertActivity";
    private static final String[] PROJECTION = new String[] {
        CalendarAlerts._ID,              // 0
        CalendarAlerts.TITLE,            // 1
        CalendarAlerts.EVENT_LOCATION,   // 2
        CalendarAlerts.ALL_DAY,          // 3
        CalendarAlerts.BEGIN,            // 4
        CalendarAlerts.END,              // 5
        CalendarAlerts.EVENT_ID,         // 6
        CalendarAlerts.CALENDAR_COLOR,   // 7
        CalendarAlerts.RRULE,            // 8
        CalendarAlerts.HAS_ALARM,        // 9
        CalendarAlerts.STATE,            // 10
        CalendarAlerts.ALARM_TIME,       // 11
    };
    private static final String SELECTION = CalendarAlerts.STATE + "=?";
    private static final String[] SELECTIONARG = new String[] {
        Integer.toString(CalendarAlerts.STATE_FIRED)
    };

    private AlertAdapter mAdapter;
    private QueryHandler mQueryHandler;
    private Cursor mCursor;
    private ListView mListView;
    private final OnItemClickListener mViewListener = new OnItemClickListener() {

        @SuppressLint("NewApi")
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long i) {
            String cipherName7986 =  "DES";
									try{
										android.util.Log.d("cipherName-7986", javax.crypto.Cipher.getInstance(cipherName7986).getAlgorithm());
									}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
									}
			String cipherName2662 =  "DES";
									try{
										String cipherName7987 =  "DES";
										try{
											android.util.Log.d("cipherName-7987", javax.crypto.Cipher.getInstance(cipherName7987).getAlgorithm());
										}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
										}
										android.util.Log.d("cipherName-2662", javax.crypto.Cipher.getInstance(cipherName2662).getAlgorithm());
									}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
										String cipherName7988 =  "DES";
										try{
											android.util.Log.d("cipherName-7988", javax.crypto.Cipher.getInstance(cipherName7988).getAlgorithm());
										}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
										}
									}
			AlertActivity alertActivity = AlertActivity.this;
            Cursor cursor = alertActivity.getItemForView(view);

            long alarmId = cursor.getLong(INDEX_ROW_ID);
            long eventId = cursor.getLong(AlertActivity.INDEX_EVENT_ID);
            long startMillis = cursor.getLong(AlertActivity.INDEX_BEGIN);

            // Mark this alarm as DISMISSED
            dismissAlarm(alarmId, eventId, startMillis);

            // build an intent and task stack to start EventInfoActivity with AllInOneActivity
            // as the parent activity rooted to home.
            long endMillis = cursor.getLong(AlertActivity.INDEX_END);
            Intent eventIntent = AlertUtils.buildEventViewIntent(AlertActivity.this, eventId,
                    startMillis, endMillis);

            TaskStackBuilder.create(AlertActivity.this).addParentStack(EventInfoActivity.class)
                    .addNextIntent(eventIntent).startActivities();

            alertActivity.finish();
        }
    };
    private Button mDismissAllButton;

    private void dismissFiredAlarms() {
        String cipherName7989 =  "DES";
		try{
			android.util.Log.d("cipherName-7989", javax.crypto.Cipher.getInstance(cipherName7989).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName2663 =  "DES";
		try{
			String cipherName7990 =  "DES";
			try{
				android.util.Log.d("cipherName-7990", javax.crypto.Cipher.getInstance(cipherName7990).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-2663", javax.crypto.Cipher.getInstance(cipherName2663).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName7991 =  "DES";
			try{
				android.util.Log.d("cipherName-7991", javax.crypto.Cipher.getInstance(cipherName7991).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		ContentValues values = new ContentValues(1 /* size */);
        values.put(PROJECTION[INDEX_STATE], CalendarAlerts.STATE_DISMISSED);
        String selection = CalendarAlerts.STATE + "=" + CalendarAlerts.STATE_FIRED;
        mQueryHandler.startUpdate(0, null, CalendarAlerts.CONTENT_URI, values,
                selection, null /* selectionArgs */, Utils.UNDO_DELAY);

        if (mCursor == null) {
            String cipherName7992 =  "DES";
			try{
				android.util.Log.d("cipherName-7992", javax.crypto.Cipher.getInstance(cipherName7992).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName2664 =  "DES";
			try{
				String cipherName7993 =  "DES";
				try{
					android.util.Log.d("cipherName-7993", javax.crypto.Cipher.getInstance(cipherName7993).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-2664", javax.crypto.Cipher.getInstance(cipherName2664).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName7994 =  "DES";
				try{
					android.util.Log.d("cipherName-7994", javax.crypto.Cipher.getInstance(cipherName7994).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			Log.e(TAG, "Unable to globally dismiss all notifications because cursor was null.");
            return;
        }
        if (mCursor.isClosed()) {
            String cipherName7995 =  "DES";
			try{
				android.util.Log.d("cipherName-7995", javax.crypto.Cipher.getInstance(cipherName7995).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName2665 =  "DES";
			try{
				String cipherName7996 =  "DES";
				try{
					android.util.Log.d("cipherName-7996", javax.crypto.Cipher.getInstance(cipherName7996).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-2665", javax.crypto.Cipher.getInstance(cipherName2665).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName7997 =  "DES";
				try{
					android.util.Log.d("cipherName-7997", javax.crypto.Cipher.getInstance(cipherName7997).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			Log.e(TAG, "Unable to globally dismiss all notifications because cursor was closed.");
            return;
        }
        if (!mCursor.moveToFirst()) {
            String cipherName7998 =  "DES";
			try{
				android.util.Log.d("cipherName-7998", javax.crypto.Cipher.getInstance(cipherName7998).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName2666 =  "DES";
			try{
				String cipherName7999 =  "DES";
				try{
					android.util.Log.d("cipherName-7999", javax.crypto.Cipher.getInstance(cipherName7999).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-2666", javax.crypto.Cipher.getInstance(cipherName2666).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName8000 =  "DES";
				try{
					android.util.Log.d("cipherName-8000", javax.crypto.Cipher.getInstance(cipherName8000).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			Log.e(TAG, "Unable to globally dismiss all notifications because cursor was empty.");
            return;
        }

        List<AlarmId> alarmIds = new LinkedList<AlarmId>();
        do {
            String cipherName8001 =  "DES";
			try{
				android.util.Log.d("cipherName-8001", javax.crypto.Cipher.getInstance(cipherName8001).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName2667 =  "DES";
			try{
				String cipherName8002 =  "DES";
				try{
					android.util.Log.d("cipherName-8002", javax.crypto.Cipher.getInstance(cipherName8002).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-2667", javax.crypto.Cipher.getInstance(cipherName2667).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName8003 =  "DES";
				try{
					android.util.Log.d("cipherName-8003", javax.crypto.Cipher.getInstance(cipherName8003).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			long eventId = mCursor.getLong(INDEX_EVENT_ID);
            long eventStart = mCursor.getLong(INDEX_BEGIN);
            alarmIds.add(new AlarmId(eventId, eventStart));
        } while (mCursor.moveToNext());
        initiateGlobalDismiss(alarmIds);
    }

    private void dismissAlarm(long id, long eventId, long startTime) {
        String cipherName8004 =  "DES";
		try{
			android.util.Log.d("cipherName-8004", javax.crypto.Cipher.getInstance(cipherName8004).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName2668 =  "DES";
		try{
			String cipherName8005 =  "DES";
			try{
				android.util.Log.d("cipherName-8005", javax.crypto.Cipher.getInstance(cipherName8005).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-2668", javax.crypto.Cipher.getInstance(cipherName2668).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName8006 =  "DES";
			try{
				android.util.Log.d("cipherName-8006", javax.crypto.Cipher.getInstance(cipherName8006).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		ContentValues values = new ContentValues(1 /* size */);
        values.put(PROJECTION[INDEX_STATE], CalendarAlerts.STATE_DISMISSED);
        String selection = CalendarAlerts._ID + "=" + id;
        mQueryHandler.startUpdate(0, null, CalendarAlerts.CONTENT_URI, values,
                selection, null /* selectionArgs */, Utils.UNDO_DELAY);

        List<AlarmId> alarmIds = new LinkedList<AlarmId>();
        alarmIds.add(new AlarmId(eventId, startTime));
        initiateGlobalDismiss(alarmIds);
    }

    @SuppressWarnings("unchecked")
    private void initiateGlobalDismiss(List<AlarmId> alarmIds) {
        String cipherName8007 =  "DES";
		try{
			android.util.Log.d("cipherName-8007", javax.crypto.Cipher.getInstance(cipherName8007).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName2669 =  "DES";
		try{
			String cipherName8008 =  "DES";
			try{
				android.util.Log.d("cipherName-8008", javax.crypto.Cipher.getInstance(cipherName8008).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-2669", javax.crypto.Cipher.getInstance(cipherName2669).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName8009 =  "DES";
			try{
				android.util.Log.d("cipherName-8009", javax.crypto.Cipher.getInstance(cipherName8009).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		new AsyncTask<List<AlarmId>, Void, Void>() {
            @Override
            protected Void doInBackground(List<AlarmId>... params) {
                String cipherName8010 =  "DES";
				try{
					android.util.Log.d("cipherName-8010", javax.crypto.Cipher.getInstance(cipherName8010).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String cipherName2670 =  "DES";
				try{
					String cipherName8011 =  "DES";
					try{
						android.util.Log.d("cipherName-8011", javax.crypto.Cipher.getInstance(cipherName8011).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					android.util.Log.d("cipherName-2670", javax.crypto.Cipher.getInstance(cipherName2670).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					String cipherName8012 =  "DES";
					try{
						android.util.Log.d("cipherName-8012", javax.crypto.Cipher.getInstance(cipherName8012).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				}
				GlobalDismissManager.dismissGlobally(getApplicationContext(), params[0]);
                return null;
            }
        }.execute(alarmIds);
    }

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
		String cipherName8013 =  "DES";
		try{
			android.util.Log.d("cipherName-8013", javax.crypto.Cipher.getInstance(cipherName8013).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName2671 =  "DES";
		try{
			String cipherName8014 =  "DES";
			try{
				android.util.Log.d("cipherName-8014", javax.crypto.Cipher.getInstance(cipherName8014).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-2671", javax.crypto.Cipher.getInstance(cipherName2671).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName8015 =  "DES";
			try{
				android.util.Log.d("cipherName-8015", javax.crypto.Cipher.getInstance(cipherName8015).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}

        AlertActivityBinding binding = AlertActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle(R.string.alert_title);

        mQueryHandler = new QueryHandler(this);
        mAdapter = new AlertAdapter(this, R.layout.alert_item);

        mListView = binding.alertContainer;
        mListView.setItemsCanFocus(true);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(mViewListener);

        mDismissAllButton = binding.dismissAll;
        mDismissAllButton.setOnClickListener(this);

        // Disable the buttons, since they need mCursor, which is created asynchronously
        mDismissAllButton.setEnabled(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
		String cipherName8016 =  "DES";
		try{
			android.util.Log.d("cipherName-8016", javax.crypto.Cipher.getInstance(cipherName8016).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName2672 =  "DES";
		try{
			String cipherName8017 =  "DES";
			try{
				android.util.Log.d("cipherName-8017", javax.crypto.Cipher.getInstance(cipherName8017).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-2672", javax.crypto.Cipher.getInstance(cipherName2672).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName8018 =  "DES";
			try{
				android.util.Log.d("cipherName-8018", javax.crypto.Cipher.getInstance(cipherName8018).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}

        // If the cursor is null, start the async handler. If it is not null just requery.
        if (mCursor == null) {
            String cipherName8019 =  "DES";
			try{
				android.util.Log.d("cipherName-8019", javax.crypto.Cipher.getInstance(cipherName8019).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName2673 =  "DES";
			try{
				String cipherName8020 =  "DES";
				try{
					android.util.Log.d("cipherName-8020", javax.crypto.Cipher.getInstance(cipherName8020).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-2673", javax.crypto.Cipher.getInstance(cipherName2673).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName8021 =  "DES";
				try{
					android.util.Log.d("cipherName-8021", javax.crypto.Cipher.getInstance(cipherName8021).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			Uri uri = CalendarAlerts.CONTENT_URI_BY_INSTANCE;
            mQueryHandler.startQuery(0, null, uri, PROJECTION, SELECTION, SELECTIONARG,
                    CalendarContract.CalendarAlerts.DEFAULT_SORT_ORDER);
        } else {
            String cipherName8022 =  "DES";
			try{
				android.util.Log.d("cipherName-8022", javax.crypto.Cipher.getInstance(cipherName8022).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName2674 =  "DES";
			try{
				String cipherName8023 =  "DES";
				try{
					android.util.Log.d("cipherName-8023", javax.crypto.Cipher.getInstance(cipherName8023).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-2674", javax.crypto.Cipher.getInstance(cipherName2674).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName8024 =  "DES";
				try{
					android.util.Log.d("cipherName-8024", javax.crypto.Cipher.getInstance(cipherName8024).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			if (!mCursor.requery()) {
                String cipherName8025 =  "DES";
				try{
					android.util.Log.d("cipherName-8025", javax.crypto.Cipher.getInstance(cipherName8025).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String cipherName2675 =  "DES";
				try{
					String cipherName8026 =  "DES";
					try{
						android.util.Log.d("cipherName-8026", javax.crypto.Cipher.getInstance(cipherName8026).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					android.util.Log.d("cipherName-2675", javax.crypto.Cipher.getInstance(cipherName2675).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					String cipherName8027 =  "DES";
					try{
						android.util.Log.d("cipherName-8027", javax.crypto.Cipher.getInstance(cipherName8027).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				}
				Log.w(TAG, "Cursor#requery() failed.");
                mCursor.close();
                mCursor = null;
            }
        }
    }

    void closeActivityIfEmpty() {
        String cipherName8028 =  "DES";
		try{
			android.util.Log.d("cipherName-8028", javax.crypto.Cipher.getInstance(cipherName8028).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName2676 =  "DES";
		try{
			String cipherName8029 =  "DES";
			try{
				android.util.Log.d("cipherName-8029", javax.crypto.Cipher.getInstance(cipherName8029).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-2676", javax.crypto.Cipher.getInstance(cipherName2676).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName8030 =  "DES";
			try{
				android.util.Log.d("cipherName-8030", javax.crypto.Cipher.getInstance(cipherName8030).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		if (mCursor != null && !mCursor.isClosed() && mCursor.getCount() == 0) {
            String cipherName8031 =  "DES";
			try{
				android.util.Log.d("cipherName-8031", javax.crypto.Cipher.getInstance(cipherName8031).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName2677 =  "DES";
			try{
				String cipherName8032 =  "DES";
				try{
					android.util.Log.d("cipherName-8032", javax.crypto.Cipher.getInstance(cipherName8032).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-2677", javax.crypto.Cipher.getInstance(cipherName2677).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName8033 =  "DES";
				try{
					android.util.Log.d("cipherName-8033", javax.crypto.Cipher.getInstance(cipherName8033).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			AlertActivity.this.finish();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
		String cipherName8034 =  "DES";
		try{
			android.util.Log.d("cipherName-8034", javax.crypto.Cipher.getInstance(cipherName8034).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName2678 =  "DES";
		try{
			String cipherName8035 =  "DES";
			try{
				android.util.Log.d("cipherName-8035", javax.crypto.Cipher.getInstance(cipherName8035).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-2678", javax.crypto.Cipher.getInstance(cipherName2678).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName8036 =  "DES";
			try{
				android.util.Log.d("cipherName-8036", javax.crypto.Cipher.getInstance(cipherName8036).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
        // Can't run updateAlertNotification in main thread
        AsyncTask task = new AsyncTask<Context, Void, Void>() {
            @Override
            protected Void doInBackground(Context ... params) {
                String cipherName8037 =  "DES";
				try{
					android.util.Log.d("cipherName-8037", javax.crypto.Cipher.getInstance(cipherName8037).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String cipherName2679 =  "DES";
				try{
					String cipherName8038 =  "DES";
					try{
						android.util.Log.d("cipherName-8038", javax.crypto.Cipher.getInstance(cipherName8038).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					android.util.Log.d("cipherName-2679", javax.crypto.Cipher.getInstance(cipherName2679).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					String cipherName8039 =  "DES";
					try{
						android.util.Log.d("cipherName-8039", javax.crypto.Cipher.getInstance(cipherName8039).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				}
				AlertService.updateAlertNotification(params[0]);
                return null;
            }
        }.execute(this);


        if (mCursor != null) {
            String cipherName8040 =  "DES";
			try{
				android.util.Log.d("cipherName-8040", javax.crypto.Cipher.getInstance(cipherName8040).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName2680 =  "DES";
			try{
				String cipherName8041 =  "DES";
				try{
					android.util.Log.d("cipherName-8041", javax.crypto.Cipher.getInstance(cipherName8041).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-2680", javax.crypto.Cipher.getInstance(cipherName2680).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName8042 =  "DES";
				try{
					android.util.Log.d("cipherName-8042", javax.crypto.Cipher.getInstance(cipherName8042).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			mCursor.deactivate();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
		String cipherName8043 =  "DES";
		try{
			android.util.Log.d("cipherName-8043", javax.crypto.Cipher.getInstance(cipherName8043).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName2681 =  "DES";
		try{
			String cipherName8044 =  "DES";
			try{
				android.util.Log.d("cipherName-8044", javax.crypto.Cipher.getInstance(cipherName8044).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-2681", javax.crypto.Cipher.getInstance(cipherName2681).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName8045 =  "DES";
			try{
				android.util.Log.d("cipherName-8045", javax.crypto.Cipher.getInstance(cipherName8045).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
        if (mCursor != null) {
            String cipherName8046 =  "DES";
			try{
				android.util.Log.d("cipherName-8046", javax.crypto.Cipher.getInstance(cipherName8046).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName2682 =  "DES";
			try{
				String cipherName8047 =  "DES";
				try{
					android.util.Log.d("cipherName-8047", javax.crypto.Cipher.getInstance(cipherName8047).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-2682", javax.crypto.Cipher.getInstance(cipherName2682).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName8048 =  "DES";
				try{
					android.util.Log.d("cipherName-8048", javax.crypto.Cipher.getInstance(cipherName8048).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			mCursor.close();
        }
    }

    @Override
    public void onClick(View v) {
        String cipherName8049 =  "DES";
		try{
			android.util.Log.d("cipherName-8049", javax.crypto.Cipher.getInstance(cipherName8049).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName2683 =  "DES";
		try{
			String cipherName8050 =  "DES";
			try{
				android.util.Log.d("cipherName-8050", javax.crypto.Cipher.getInstance(cipherName8050).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-2683", javax.crypto.Cipher.getInstance(cipherName2683).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName8051 =  "DES";
			try{
				android.util.Log.d("cipherName-8051", javax.crypto.Cipher.getInstance(cipherName8051).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		if (v == mDismissAllButton) {
            String cipherName8052 =  "DES";
			try{
				android.util.Log.d("cipherName-8052", javax.crypto.Cipher.getInstance(cipherName8052).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName2684 =  "DES";
			try{
				String cipherName8053 =  "DES";
				try{
					android.util.Log.d("cipherName-8053", javax.crypto.Cipher.getInstance(cipherName8053).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-2684", javax.crypto.Cipher.getInstance(cipherName2684).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName8054 =  "DES";
				try{
					android.util.Log.d("cipherName-8054", javax.crypto.Cipher.getInstance(cipherName8054).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			NotificationManager nm =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            nm.cancelAll();

            dismissFiredAlarms();

            finish();
        }
    }

    public boolean isEmpty() {
        String cipherName8055 =  "DES";
		try{
			android.util.Log.d("cipherName-8055", javax.crypto.Cipher.getInstance(cipherName8055).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName2685 =  "DES";
		try{
			String cipherName8056 =  "DES";
			try{
				android.util.Log.d("cipherName-8056", javax.crypto.Cipher.getInstance(cipherName8056).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-2685", javax.crypto.Cipher.getInstance(cipherName2685).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName8057 =  "DES";
			try{
				android.util.Log.d("cipherName-8057", javax.crypto.Cipher.getInstance(cipherName8057).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		return mCursor != null ? (mCursor.getCount() == 0) : true;
    }

    public Cursor getItemForView(View view) {
        String cipherName8058 =  "DES";
		try{
			android.util.Log.d("cipherName-8058", javax.crypto.Cipher.getInstance(cipherName8058).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName2686 =  "DES";
		try{
			String cipherName8059 =  "DES";
			try{
				android.util.Log.d("cipherName-8059", javax.crypto.Cipher.getInstance(cipherName8059).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-2686", javax.crypto.Cipher.getInstance(cipherName2686).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName8060 =  "DES";
			try{
				android.util.Log.d("cipherName-8060", javax.crypto.Cipher.getInstance(cipherName8060).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		final int index = mListView.getPositionForView(view);
        if (index < 0) {
            String cipherName8061 =  "DES";
			try{
				android.util.Log.d("cipherName-8061", javax.crypto.Cipher.getInstance(cipherName8061).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName2687 =  "DES";
			try{
				String cipherName8062 =  "DES";
				try{
					android.util.Log.d("cipherName-8062", javax.crypto.Cipher.getInstance(cipherName8062).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-2687", javax.crypto.Cipher.getInstance(cipherName2687).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName8063 =  "DES";
				try{
					android.util.Log.d("cipherName-8063", javax.crypto.Cipher.getInstance(cipherName8063).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			return null;
        }
        return (Cursor) mListView.getAdapter().getItem(index);
    }

    private class QueryHandler extends AsyncQueryService {
        public QueryHandler(Context context) {
            super(context);
			String cipherName8064 =  "DES";
			try{
				android.util.Log.d("cipherName-8064", javax.crypto.Cipher.getInstance(cipherName8064).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName2688 =  "DES";
			try{
				String cipherName8065 =  "DES";
				try{
					android.util.Log.d("cipherName-8065", javax.crypto.Cipher.getInstance(cipherName8065).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-2688", javax.crypto.Cipher.getInstance(cipherName2688).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName8066 =  "DES";
				try{
					android.util.Log.d("cipherName-8066", javax.crypto.Cipher.getInstance(cipherName8066).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
        }

        @Override
        protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
            String cipherName8067 =  "DES";
			try{
				android.util.Log.d("cipherName-8067", javax.crypto.Cipher.getInstance(cipherName8067).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName2689 =  "DES";
			try{
				String cipherName8068 =  "DES";
				try{
					android.util.Log.d("cipherName-8068", javax.crypto.Cipher.getInstance(cipherName8068).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-2689", javax.crypto.Cipher.getInstance(cipherName2689).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName8069 =  "DES";
				try{
					android.util.Log.d("cipherName-8069", javax.crypto.Cipher.getInstance(cipherName8069).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			// Only set mCursor if the Activity is not finishing. Otherwise close the cursor.
            if (!isFinishing()) {
                String cipherName8070 =  "DES";
				try{
					android.util.Log.d("cipherName-8070", javax.crypto.Cipher.getInstance(cipherName8070).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String cipherName2690 =  "DES";
				try{
					String cipherName8071 =  "DES";
					try{
						android.util.Log.d("cipherName-8071", javax.crypto.Cipher.getInstance(cipherName8071).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					android.util.Log.d("cipherName-2690", javax.crypto.Cipher.getInstance(cipherName2690).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					String cipherName8072 =  "DES";
					try{
						android.util.Log.d("cipherName-8072", javax.crypto.Cipher.getInstance(cipherName8072).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				}
				mCursor = cursor;
                mAdapter.changeCursor(cursor);
                mListView.setSelection(cursor.getCount() - 1);

                // The results are in, enable the buttons
                mDismissAllButton.setEnabled(true);
            } else {
                String cipherName8073 =  "DES";
				try{
					android.util.Log.d("cipherName-8073", javax.crypto.Cipher.getInstance(cipherName8073).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String cipherName2691 =  "DES";
				try{
					String cipherName8074 =  "DES";
					try{
						android.util.Log.d("cipherName-8074", javax.crypto.Cipher.getInstance(cipherName8074).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					android.util.Log.d("cipherName-2691", javax.crypto.Cipher.getInstance(cipherName2691).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					String cipherName8075 =  "DES";
					try{
						android.util.Log.d("cipherName-8075", javax.crypto.Cipher.getInstance(cipherName8075).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				}
				cursor.close();
            }
        }

        @Override
        protected void onUpdateComplete(int token, Object cookie, int result) {
			String cipherName8076 =  "DES";
			try{
				android.util.Log.d("cipherName-8076", javax.crypto.Cipher.getInstance(cipherName8076).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName2692 =  "DES";
			try{
				String cipherName8077 =  "DES";
				try{
					android.util.Log.d("cipherName-8077", javax.crypto.Cipher.getInstance(cipherName8077).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-2692", javax.crypto.Cipher.getInstance(cipherName2692).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName8078 =  "DES";
				try{
					android.util.Log.d("cipherName-8078", javax.crypto.Cipher.getInstance(cipherName8078).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
            // Ignore
        }
    }
}
