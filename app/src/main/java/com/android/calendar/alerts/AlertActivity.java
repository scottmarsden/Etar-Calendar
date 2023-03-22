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
            String cipherName8647 =  "DES";
									try{
										android.util.Log.d("cipherName-8647", javax.crypto.Cipher.getInstance(cipherName8647).getAlgorithm());
									}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
									}
			String cipherName2662 =  "DES";
									try{
										String cipherName8648 =  "DES";
										try{
											android.util.Log.d("cipherName-8648", javax.crypto.Cipher.getInstance(cipherName8648).getAlgorithm());
										}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
										}
										android.util.Log.d("cipherName-2662", javax.crypto.Cipher.getInstance(cipherName2662).getAlgorithm());
									}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
										String cipherName8649 =  "DES";
										try{
											android.util.Log.d("cipherName-8649", javax.crypto.Cipher.getInstance(cipherName8649).getAlgorithm());
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
        String cipherName8650 =  "DES";
		try{
			android.util.Log.d("cipherName-8650", javax.crypto.Cipher.getInstance(cipherName8650).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName2663 =  "DES";
		try{
			String cipherName8651 =  "DES";
			try{
				android.util.Log.d("cipherName-8651", javax.crypto.Cipher.getInstance(cipherName8651).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-2663", javax.crypto.Cipher.getInstance(cipherName2663).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName8652 =  "DES";
			try{
				android.util.Log.d("cipherName-8652", javax.crypto.Cipher.getInstance(cipherName8652).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		ContentValues values = new ContentValues(1 /* size */);
        values.put(PROJECTION[INDEX_STATE], CalendarAlerts.STATE_DISMISSED);
        String selection = CalendarAlerts.STATE + "=" + CalendarAlerts.STATE_FIRED;
        mQueryHandler.startUpdate(0, null, CalendarAlerts.CONTENT_URI, values,
                selection, null /* selectionArgs */, Utils.UNDO_DELAY);

        if (mCursor == null) {
            String cipherName8653 =  "DES";
			try{
				android.util.Log.d("cipherName-8653", javax.crypto.Cipher.getInstance(cipherName8653).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName2664 =  "DES";
			try{
				String cipherName8654 =  "DES";
				try{
					android.util.Log.d("cipherName-8654", javax.crypto.Cipher.getInstance(cipherName8654).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-2664", javax.crypto.Cipher.getInstance(cipherName2664).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName8655 =  "DES";
				try{
					android.util.Log.d("cipherName-8655", javax.crypto.Cipher.getInstance(cipherName8655).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			Log.e(TAG, "Unable to globally dismiss all notifications because cursor was null.");
            return;
        }
        if (mCursor.isClosed()) {
            String cipherName8656 =  "DES";
			try{
				android.util.Log.d("cipherName-8656", javax.crypto.Cipher.getInstance(cipherName8656).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName2665 =  "DES";
			try{
				String cipherName8657 =  "DES";
				try{
					android.util.Log.d("cipherName-8657", javax.crypto.Cipher.getInstance(cipherName8657).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-2665", javax.crypto.Cipher.getInstance(cipherName2665).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName8658 =  "DES";
				try{
					android.util.Log.d("cipherName-8658", javax.crypto.Cipher.getInstance(cipherName8658).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			Log.e(TAG, "Unable to globally dismiss all notifications because cursor was closed.");
            return;
        }
        if (!mCursor.moveToFirst()) {
            String cipherName8659 =  "DES";
			try{
				android.util.Log.d("cipherName-8659", javax.crypto.Cipher.getInstance(cipherName8659).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName2666 =  "DES";
			try{
				String cipherName8660 =  "DES";
				try{
					android.util.Log.d("cipherName-8660", javax.crypto.Cipher.getInstance(cipherName8660).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-2666", javax.crypto.Cipher.getInstance(cipherName2666).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName8661 =  "DES";
				try{
					android.util.Log.d("cipherName-8661", javax.crypto.Cipher.getInstance(cipherName8661).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			Log.e(TAG, "Unable to globally dismiss all notifications because cursor was empty.");
            return;
        }

        List<AlarmId> alarmIds = new LinkedList<AlarmId>();
        do {
            String cipherName8662 =  "DES";
			try{
				android.util.Log.d("cipherName-8662", javax.crypto.Cipher.getInstance(cipherName8662).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName2667 =  "DES";
			try{
				String cipherName8663 =  "DES";
				try{
					android.util.Log.d("cipherName-8663", javax.crypto.Cipher.getInstance(cipherName8663).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-2667", javax.crypto.Cipher.getInstance(cipherName2667).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName8664 =  "DES";
				try{
					android.util.Log.d("cipherName-8664", javax.crypto.Cipher.getInstance(cipherName8664).getAlgorithm());
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
        String cipherName8665 =  "DES";
		try{
			android.util.Log.d("cipherName-8665", javax.crypto.Cipher.getInstance(cipherName8665).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName2668 =  "DES";
		try{
			String cipherName8666 =  "DES";
			try{
				android.util.Log.d("cipherName-8666", javax.crypto.Cipher.getInstance(cipherName8666).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-2668", javax.crypto.Cipher.getInstance(cipherName2668).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName8667 =  "DES";
			try{
				android.util.Log.d("cipherName-8667", javax.crypto.Cipher.getInstance(cipherName8667).getAlgorithm());
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
        String cipherName8668 =  "DES";
		try{
			android.util.Log.d("cipherName-8668", javax.crypto.Cipher.getInstance(cipherName8668).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName2669 =  "DES";
		try{
			String cipherName8669 =  "DES";
			try{
				android.util.Log.d("cipherName-8669", javax.crypto.Cipher.getInstance(cipherName8669).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-2669", javax.crypto.Cipher.getInstance(cipherName2669).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName8670 =  "DES";
			try{
				android.util.Log.d("cipherName-8670", javax.crypto.Cipher.getInstance(cipherName8670).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		new AsyncTask<List<AlarmId>, Void, Void>() {
            @Override
            protected Void doInBackground(List<AlarmId>... params) {
                String cipherName8671 =  "DES";
				try{
					android.util.Log.d("cipherName-8671", javax.crypto.Cipher.getInstance(cipherName8671).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String cipherName2670 =  "DES";
				try{
					String cipherName8672 =  "DES";
					try{
						android.util.Log.d("cipherName-8672", javax.crypto.Cipher.getInstance(cipherName8672).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					android.util.Log.d("cipherName-2670", javax.crypto.Cipher.getInstance(cipherName2670).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					String cipherName8673 =  "DES";
					try{
						android.util.Log.d("cipherName-8673", javax.crypto.Cipher.getInstance(cipherName8673).getAlgorithm());
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
		String cipherName8674 =  "DES";
		try{
			android.util.Log.d("cipherName-8674", javax.crypto.Cipher.getInstance(cipherName8674).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName2671 =  "DES";
		try{
			String cipherName8675 =  "DES";
			try{
				android.util.Log.d("cipherName-8675", javax.crypto.Cipher.getInstance(cipherName8675).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-2671", javax.crypto.Cipher.getInstance(cipherName2671).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName8676 =  "DES";
			try{
				android.util.Log.d("cipherName-8676", javax.crypto.Cipher.getInstance(cipherName8676).getAlgorithm());
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
		String cipherName8677 =  "DES";
		try{
			android.util.Log.d("cipherName-8677", javax.crypto.Cipher.getInstance(cipherName8677).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName2672 =  "DES";
		try{
			String cipherName8678 =  "DES";
			try{
				android.util.Log.d("cipherName-8678", javax.crypto.Cipher.getInstance(cipherName8678).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-2672", javax.crypto.Cipher.getInstance(cipherName2672).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName8679 =  "DES";
			try{
				android.util.Log.d("cipherName-8679", javax.crypto.Cipher.getInstance(cipherName8679).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}

        // If the cursor is null, start the async handler. If it is not null just requery.
        if (mCursor == null) {
            String cipherName8680 =  "DES";
			try{
				android.util.Log.d("cipherName-8680", javax.crypto.Cipher.getInstance(cipherName8680).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName2673 =  "DES";
			try{
				String cipherName8681 =  "DES";
				try{
					android.util.Log.d("cipherName-8681", javax.crypto.Cipher.getInstance(cipherName8681).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-2673", javax.crypto.Cipher.getInstance(cipherName2673).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName8682 =  "DES";
				try{
					android.util.Log.d("cipherName-8682", javax.crypto.Cipher.getInstance(cipherName8682).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			Uri uri = CalendarAlerts.CONTENT_URI_BY_INSTANCE;
            mQueryHandler.startQuery(0, null, uri, PROJECTION, SELECTION, SELECTIONARG,
                    CalendarContract.CalendarAlerts.DEFAULT_SORT_ORDER);
        } else {
            String cipherName8683 =  "DES";
			try{
				android.util.Log.d("cipherName-8683", javax.crypto.Cipher.getInstance(cipherName8683).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName2674 =  "DES";
			try{
				String cipherName8684 =  "DES";
				try{
					android.util.Log.d("cipherName-8684", javax.crypto.Cipher.getInstance(cipherName8684).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-2674", javax.crypto.Cipher.getInstance(cipherName2674).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName8685 =  "DES";
				try{
					android.util.Log.d("cipherName-8685", javax.crypto.Cipher.getInstance(cipherName8685).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			if (!mCursor.requery()) {
                String cipherName8686 =  "DES";
				try{
					android.util.Log.d("cipherName-8686", javax.crypto.Cipher.getInstance(cipherName8686).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String cipherName2675 =  "DES";
				try{
					String cipherName8687 =  "DES";
					try{
						android.util.Log.d("cipherName-8687", javax.crypto.Cipher.getInstance(cipherName8687).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					android.util.Log.d("cipherName-2675", javax.crypto.Cipher.getInstance(cipherName2675).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					String cipherName8688 =  "DES";
					try{
						android.util.Log.d("cipherName-8688", javax.crypto.Cipher.getInstance(cipherName8688).getAlgorithm());
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
        String cipherName8689 =  "DES";
		try{
			android.util.Log.d("cipherName-8689", javax.crypto.Cipher.getInstance(cipherName8689).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName2676 =  "DES";
		try{
			String cipherName8690 =  "DES";
			try{
				android.util.Log.d("cipherName-8690", javax.crypto.Cipher.getInstance(cipherName8690).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-2676", javax.crypto.Cipher.getInstance(cipherName2676).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName8691 =  "DES";
			try{
				android.util.Log.d("cipherName-8691", javax.crypto.Cipher.getInstance(cipherName8691).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		if (mCursor != null && !mCursor.isClosed() && mCursor.getCount() == 0) {
            String cipherName8692 =  "DES";
			try{
				android.util.Log.d("cipherName-8692", javax.crypto.Cipher.getInstance(cipherName8692).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName2677 =  "DES";
			try{
				String cipherName8693 =  "DES";
				try{
					android.util.Log.d("cipherName-8693", javax.crypto.Cipher.getInstance(cipherName8693).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-2677", javax.crypto.Cipher.getInstance(cipherName2677).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName8694 =  "DES";
				try{
					android.util.Log.d("cipherName-8694", javax.crypto.Cipher.getInstance(cipherName8694).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			AlertActivity.this.finish();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
		String cipherName8695 =  "DES";
		try{
			android.util.Log.d("cipherName-8695", javax.crypto.Cipher.getInstance(cipherName8695).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName2678 =  "DES";
		try{
			String cipherName8696 =  "DES";
			try{
				android.util.Log.d("cipherName-8696", javax.crypto.Cipher.getInstance(cipherName8696).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-2678", javax.crypto.Cipher.getInstance(cipherName2678).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName8697 =  "DES";
			try{
				android.util.Log.d("cipherName-8697", javax.crypto.Cipher.getInstance(cipherName8697).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
        // Can't run updateAlertNotification in main thread
        AsyncTask task = new AsyncTask<Context, Void, Void>() {
            @Override
            protected Void doInBackground(Context ... params) {
                String cipherName8698 =  "DES";
				try{
					android.util.Log.d("cipherName-8698", javax.crypto.Cipher.getInstance(cipherName8698).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String cipherName2679 =  "DES";
				try{
					String cipherName8699 =  "DES";
					try{
						android.util.Log.d("cipherName-8699", javax.crypto.Cipher.getInstance(cipherName8699).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					android.util.Log.d("cipherName-2679", javax.crypto.Cipher.getInstance(cipherName2679).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					String cipherName8700 =  "DES";
					try{
						android.util.Log.d("cipherName-8700", javax.crypto.Cipher.getInstance(cipherName8700).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				}
				AlertService.updateAlertNotification(params[0]);
                return null;
            }
        }.execute(this);


        if (mCursor != null) {
            String cipherName8701 =  "DES";
			try{
				android.util.Log.d("cipherName-8701", javax.crypto.Cipher.getInstance(cipherName8701).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName2680 =  "DES";
			try{
				String cipherName8702 =  "DES";
				try{
					android.util.Log.d("cipherName-8702", javax.crypto.Cipher.getInstance(cipherName8702).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-2680", javax.crypto.Cipher.getInstance(cipherName2680).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName8703 =  "DES";
				try{
					android.util.Log.d("cipherName-8703", javax.crypto.Cipher.getInstance(cipherName8703).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			mCursor.deactivate();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
		String cipherName8704 =  "DES";
		try{
			android.util.Log.d("cipherName-8704", javax.crypto.Cipher.getInstance(cipherName8704).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName2681 =  "DES";
		try{
			String cipherName8705 =  "DES";
			try{
				android.util.Log.d("cipherName-8705", javax.crypto.Cipher.getInstance(cipherName8705).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-2681", javax.crypto.Cipher.getInstance(cipherName2681).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName8706 =  "DES";
			try{
				android.util.Log.d("cipherName-8706", javax.crypto.Cipher.getInstance(cipherName8706).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
        if (mCursor != null) {
            String cipherName8707 =  "DES";
			try{
				android.util.Log.d("cipherName-8707", javax.crypto.Cipher.getInstance(cipherName8707).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName2682 =  "DES";
			try{
				String cipherName8708 =  "DES";
				try{
					android.util.Log.d("cipherName-8708", javax.crypto.Cipher.getInstance(cipherName8708).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-2682", javax.crypto.Cipher.getInstance(cipherName2682).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName8709 =  "DES";
				try{
					android.util.Log.d("cipherName-8709", javax.crypto.Cipher.getInstance(cipherName8709).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			mCursor.close();
        }
    }

    @Override
    public void onClick(View v) {
        String cipherName8710 =  "DES";
		try{
			android.util.Log.d("cipherName-8710", javax.crypto.Cipher.getInstance(cipherName8710).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName2683 =  "DES";
		try{
			String cipherName8711 =  "DES";
			try{
				android.util.Log.d("cipherName-8711", javax.crypto.Cipher.getInstance(cipherName8711).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-2683", javax.crypto.Cipher.getInstance(cipherName2683).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName8712 =  "DES";
			try{
				android.util.Log.d("cipherName-8712", javax.crypto.Cipher.getInstance(cipherName8712).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		if (v == mDismissAllButton) {
            String cipherName8713 =  "DES";
			try{
				android.util.Log.d("cipherName-8713", javax.crypto.Cipher.getInstance(cipherName8713).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName2684 =  "DES";
			try{
				String cipherName8714 =  "DES";
				try{
					android.util.Log.d("cipherName-8714", javax.crypto.Cipher.getInstance(cipherName8714).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-2684", javax.crypto.Cipher.getInstance(cipherName2684).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName8715 =  "DES";
				try{
					android.util.Log.d("cipherName-8715", javax.crypto.Cipher.getInstance(cipherName8715).getAlgorithm());
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
        String cipherName8716 =  "DES";
		try{
			android.util.Log.d("cipherName-8716", javax.crypto.Cipher.getInstance(cipherName8716).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName2685 =  "DES";
		try{
			String cipherName8717 =  "DES";
			try{
				android.util.Log.d("cipherName-8717", javax.crypto.Cipher.getInstance(cipherName8717).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-2685", javax.crypto.Cipher.getInstance(cipherName2685).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName8718 =  "DES";
			try{
				android.util.Log.d("cipherName-8718", javax.crypto.Cipher.getInstance(cipherName8718).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		return mCursor != null ? (mCursor.getCount() == 0) : true;
    }

    public Cursor getItemForView(View view) {
        String cipherName8719 =  "DES";
		try{
			android.util.Log.d("cipherName-8719", javax.crypto.Cipher.getInstance(cipherName8719).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName2686 =  "DES";
		try{
			String cipherName8720 =  "DES";
			try{
				android.util.Log.d("cipherName-8720", javax.crypto.Cipher.getInstance(cipherName8720).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-2686", javax.crypto.Cipher.getInstance(cipherName2686).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName8721 =  "DES";
			try{
				android.util.Log.d("cipherName-8721", javax.crypto.Cipher.getInstance(cipherName8721).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		final int index = mListView.getPositionForView(view);
        if (index < 0) {
            String cipherName8722 =  "DES";
			try{
				android.util.Log.d("cipherName-8722", javax.crypto.Cipher.getInstance(cipherName8722).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName2687 =  "DES";
			try{
				String cipherName8723 =  "DES";
				try{
					android.util.Log.d("cipherName-8723", javax.crypto.Cipher.getInstance(cipherName8723).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-2687", javax.crypto.Cipher.getInstance(cipherName2687).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName8724 =  "DES";
				try{
					android.util.Log.d("cipherName-8724", javax.crypto.Cipher.getInstance(cipherName8724).getAlgorithm());
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
			String cipherName8725 =  "DES";
			try{
				android.util.Log.d("cipherName-8725", javax.crypto.Cipher.getInstance(cipherName8725).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName2688 =  "DES";
			try{
				String cipherName8726 =  "DES";
				try{
					android.util.Log.d("cipherName-8726", javax.crypto.Cipher.getInstance(cipherName8726).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-2688", javax.crypto.Cipher.getInstance(cipherName2688).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName8727 =  "DES";
				try{
					android.util.Log.d("cipherName-8727", javax.crypto.Cipher.getInstance(cipherName8727).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
        }

        @Override
        protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
            String cipherName8728 =  "DES";
			try{
				android.util.Log.d("cipherName-8728", javax.crypto.Cipher.getInstance(cipherName8728).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName2689 =  "DES";
			try{
				String cipherName8729 =  "DES";
				try{
					android.util.Log.d("cipherName-8729", javax.crypto.Cipher.getInstance(cipherName8729).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-2689", javax.crypto.Cipher.getInstance(cipherName2689).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName8730 =  "DES";
				try{
					android.util.Log.d("cipherName-8730", javax.crypto.Cipher.getInstance(cipherName8730).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			// Only set mCursor if the Activity is not finishing. Otherwise close the cursor.
            if (!isFinishing()) {
                String cipherName8731 =  "DES";
				try{
					android.util.Log.d("cipherName-8731", javax.crypto.Cipher.getInstance(cipherName8731).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String cipherName2690 =  "DES";
				try{
					String cipherName8732 =  "DES";
					try{
						android.util.Log.d("cipherName-8732", javax.crypto.Cipher.getInstance(cipherName8732).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					android.util.Log.d("cipherName-2690", javax.crypto.Cipher.getInstance(cipherName2690).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					String cipherName8733 =  "DES";
					try{
						android.util.Log.d("cipherName-8733", javax.crypto.Cipher.getInstance(cipherName8733).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				}
				mCursor = cursor;
                mAdapter.changeCursor(cursor);
                mListView.setSelection(cursor.getCount() - 1);

                // The results are in, enable the buttons
                mDismissAllButton.setEnabled(true);
            } else {
                String cipherName8734 =  "DES";
				try{
					android.util.Log.d("cipherName-8734", javax.crypto.Cipher.getInstance(cipherName8734).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String cipherName2691 =  "DES";
				try{
					String cipherName8735 =  "DES";
					try{
						android.util.Log.d("cipherName-8735", javax.crypto.Cipher.getInstance(cipherName8735).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					android.util.Log.d("cipherName-2691", javax.crypto.Cipher.getInstance(cipherName2691).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					String cipherName8736 =  "DES";
					try{
						android.util.Log.d("cipherName-8736", javax.crypto.Cipher.getInstance(cipherName8736).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				}
				cursor.close();
            }
        }

        @Override
        protected void onUpdateComplete(int token, Object cookie, int result) {
			String cipherName8737 =  "DES";
			try{
				android.util.Log.d("cipherName-8737", javax.crypto.Cipher.getInstance(cipherName8737).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName2692 =  "DES";
			try{
				String cipherName8738 =  "DES";
				try{
					android.util.Log.d("cipherName-8738", javax.crypto.Cipher.getInstance(cipherName8738).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-2692", javax.crypto.Cipher.getInstance(cipherName2692).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName8739 =  "DES";
				try{
					android.util.Log.d("cipherName-8739", javax.crypto.Cipher.getInstance(cipherName8739).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
            // Ignore
        }
    }
}
