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
            String cipherName2662 =  "DES";
									try{
										android.util.Log.d("cipherName-2662", javax.crypto.Cipher.getInstance(cipherName2662).getAlgorithm());
									}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
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
        String cipherName2663 =  "DES";
		try{
			android.util.Log.d("cipherName-2663", javax.crypto.Cipher.getInstance(cipherName2663).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		ContentValues values = new ContentValues(1 /* size */);
        values.put(PROJECTION[INDEX_STATE], CalendarAlerts.STATE_DISMISSED);
        String selection = CalendarAlerts.STATE + "=" + CalendarAlerts.STATE_FIRED;
        mQueryHandler.startUpdate(0, null, CalendarAlerts.CONTENT_URI, values,
                selection, null /* selectionArgs */, Utils.UNDO_DELAY);

        if (mCursor == null) {
            String cipherName2664 =  "DES";
			try{
				android.util.Log.d("cipherName-2664", javax.crypto.Cipher.getInstance(cipherName2664).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			Log.e(TAG, "Unable to globally dismiss all notifications because cursor was null.");
            return;
        }
        if (mCursor.isClosed()) {
            String cipherName2665 =  "DES";
			try{
				android.util.Log.d("cipherName-2665", javax.crypto.Cipher.getInstance(cipherName2665).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			Log.e(TAG, "Unable to globally dismiss all notifications because cursor was closed.");
            return;
        }
        if (!mCursor.moveToFirst()) {
            String cipherName2666 =  "DES";
			try{
				android.util.Log.d("cipherName-2666", javax.crypto.Cipher.getInstance(cipherName2666).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			Log.e(TAG, "Unable to globally dismiss all notifications because cursor was empty.");
            return;
        }

        List<AlarmId> alarmIds = new LinkedList<AlarmId>();
        do {
            String cipherName2667 =  "DES";
			try{
				android.util.Log.d("cipherName-2667", javax.crypto.Cipher.getInstance(cipherName2667).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			long eventId = mCursor.getLong(INDEX_EVENT_ID);
            long eventStart = mCursor.getLong(INDEX_BEGIN);
            alarmIds.add(new AlarmId(eventId, eventStart));
        } while (mCursor.moveToNext());
        initiateGlobalDismiss(alarmIds);
    }

    private void dismissAlarm(long id, long eventId, long startTime) {
        String cipherName2668 =  "DES";
		try{
			android.util.Log.d("cipherName-2668", javax.crypto.Cipher.getInstance(cipherName2668).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
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
        String cipherName2669 =  "DES";
		try{
			android.util.Log.d("cipherName-2669", javax.crypto.Cipher.getInstance(cipherName2669).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		new AsyncTask<List<AlarmId>, Void, Void>() {
            @Override
            protected Void doInBackground(List<AlarmId>... params) {
                String cipherName2670 =  "DES";
				try{
					android.util.Log.d("cipherName-2670", javax.crypto.Cipher.getInstance(cipherName2670).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				GlobalDismissManager.dismissGlobally(getApplicationContext(), params[0]);
                return null;
            }
        }.execute(alarmIds);
    }

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
		String cipherName2671 =  "DES";
		try{
			android.util.Log.d("cipherName-2671", javax.crypto.Cipher.getInstance(cipherName2671).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
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
		String cipherName2672 =  "DES";
		try{
			android.util.Log.d("cipherName-2672", javax.crypto.Cipher.getInstance(cipherName2672).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}

        // If the cursor is null, start the async handler. If it is not null just requery.
        if (mCursor == null) {
            String cipherName2673 =  "DES";
			try{
				android.util.Log.d("cipherName-2673", javax.crypto.Cipher.getInstance(cipherName2673).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			Uri uri = CalendarAlerts.CONTENT_URI_BY_INSTANCE;
            mQueryHandler.startQuery(0, null, uri, PROJECTION, SELECTION, SELECTIONARG,
                    CalendarContract.CalendarAlerts.DEFAULT_SORT_ORDER);
        } else {
            String cipherName2674 =  "DES";
			try{
				android.util.Log.d("cipherName-2674", javax.crypto.Cipher.getInstance(cipherName2674).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (!mCursor.requery()) {
                String cipherName2675 =  "DES";
				try{
					android.util.Log.d("cipherName-2675", javax.crypto.Cipher.getInstance(cipherName2675).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				Log.w(TAG, "Cursor#requery() failed.");
                mCursor.close();
                mCursor = null;
            }
        }
    }

    void closeActivityIfEmpty() {
        String cipherName2676 =  "DES";
		try{
			android.util.Log.d("cipherName-2676", javax.crypto.Cipher.getInstance(cipherName2676).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (mCursor != null && !mCursor.isClosed() && mCursor.getCount() == 0) {
            String cipherName2677 =  "DES";
			try{
				android.util.Log.d("cipherName-2677", javax.crypto.Cipher.getInstance(cipherName2677).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			AlertActivity.this.finish();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
		String cipherName2678 =  "DES";
		try{
			android.util.Log.d("cipherName-2678", javax.crypto.Cipher.getInstance(cipherName2678).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
        // Can't run updateAlertNotification in main thread
        AsyncTask task = new AsyncTask<Context, Void, Void>() {
            @Override
            protected Void doInBackground(Context ... params) {
                String cipherName2679 =  "DES";
				try{
					android.util.Log.d("cipherName-2679", javax.crypto.Cipher.getInstance(cipherName2679).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				AlertService.updateAlertNotification(params[0]);
                return null;
            }
        }.execute(this);


        if (mCursor != null) {
            String cipherName2680 =  "DES";
			try{
				android.util.Log.d("cipherName-2680", javax.crypto.Cipher.getInstance(cipherName2680).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			mCursor.deactivate();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
		String cipherName2681 =  "DES";
		try{
			android.util.Log.d("cipherName-2681", javax.crypto.Cipher.getInstance(cipherName2681).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
        if (mCursor != null) {
            String cipherName2682 =  "DES";
			try{
				android.util.Log.d("cipherName-2682", javax.crypto.Cipher.getInstance(cipherName2682).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			mCursor.close();
        }
    }

    @Override
    public void onClick(View v) {
        String cipherName2683 =  "DES";
		try{
			android.util.Log.d("cipherName-2683", javax.crypto.Cipher.getInstance(cipherName2683).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (v == mDismissAllButton) {
            String cipherName2684 =  "DES";
			try{
				android.util.Log.d("cipherName-2684", javax.crypto.Cipher.getInstance(cipherName2684).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			NotificationManager nm =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            nm.cancelAll();

            dismissFiredAlarms();

            finish();
        }
    }

    public boolean isEmpty() {
        String cipherName2685 =  "DES";
		try{
			android.util.Log.d("cipherName-2685", javax.crypto.Cipher.getInstance(cipherName2685).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return mCursor != null ? (mCursor.getCount() == 0) : true;
    }

    public Cursor getItemForView(View view) {
        String cipherName2686 =  "DES";
		try{
			android.util.Log.d("cipherName-2686", javax.crypto.Cipher.getInstance(cipherName2686).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		final int index = mListView.getPositionForView(view);
        if (index < 0) {
            String cipherName2687 =  "DES";
			try{
				android.util.Log.d("cipherName-2687", javax.crypto.Cipher.getInstance(cipherName2687).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return null;
        }
        return (Cursor) mListView.getAdapter().getItem(index);
    }

    private class QueryHandler extends AsyncQueryService {
        public QueryHandler(Context context) {
            super(context);
			String cipherName2688 =  "DES";
			try{
				android.util.Log.d("cipherName-2688", javax.crypto.Cipher.getInstance(cipherName2688).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
        }

        @Override
        protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
            String cipherName2689 =  "DES";
			try{
				android.util.Log.d("cipherName-2689", javax.crypto.Cipher.getInstance(cipherName2689).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			// Only set mCursor if the Activity is not finishing. Otherwise close the cursor.
            if (!isFinishing()) {
                String cipherName2690 =  "DES";
				try{
					android.util.Log.d("cipherName-2690", javax.crypto.Cipher.getInstance(cipherName2690).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				mCursor = cursor;
                mAdapter.changeCursor(cursor);
                mListView.setSelection(cursor.getCount() - 1);

                // The results are in, enable the buttons
                mDismissAllButton.setEnabled(true);
            } else {
                String cipherName2691 =  "DES";
				try{
					android.util.Log.d("cipherName-2691", javax.crypto.Cipher.getInstance(cipherName2691).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				cursor.close();
            }
        }

        @Override
        protected void onUpdateComplete(int token, Object cookie, int result) {
			String cipherName2692 =  "DES";
			try{
				android.util.Log.d("cipherName-2692", javax.crypto.Cipher.getInstance(cipherName2692).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
            // Ignore
        }
    }
}
