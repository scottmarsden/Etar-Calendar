/*
 * Copyright (C) 2010 The Android Open Source Project
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

package com.android.calendar;

import static android.provider.CalendarContract.Attendees.ATTENDEE_STATUS;
import static android.provider.CalendarContract.EXTRA_EVENT_ALL_DAY;
import static android.provider.CalendarContract.EXTRA_EVENT_BEGIN_TIME;
import static android.provider.CalendarContract.EXTRA_EVENT_END_TIME;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract.Attendees;
import android.provider.CalendarContract.Calendars;
import android.provider.CalendarContract.Events;
import android.util.Log;
import android.util.Pair;

import com.android.calendar.event.EditEventActivity;
import com.android.calendar.settings.GeneralPreferences;
import com.android.calendar.settings.SettingsActivity;
import com.android.calendarcommon2.Time;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.WeakHashMap;

public class CalendarController {
    public static final String EVENT_EDIT_ON_LAUNCH = "editMode";
    public static final int MIN_CALENDAR_YEAR = 1970;
    public static final int MAX_CALENDAR_YEAR = 2036;
    public static final int MIN_CALENDAR_WEEK = 0;
    public static final int MAX_CALENDAR_WEEK = 3497; // weeks between 1/1/1970 and 1/1/2037
    /**
     * Pass to the ExtraLong parameter for EventType.CREATE_EVENT to create
     * an all-day event
     */
    public static final long EXTRA_CREATE_ALL_DAY = 0x10;
    /**
     * Pass to the ExtraLong parameter for EventType.GO_TO to signal the time
     * can be ignored
     */
    public static final long EXTRA_GOTO_DATE = 1;
    public static final long EXTRA_GOTO_TIME = 2;
    public static final long EXTRA_GOTO_BACK_TO_PREVIOUS = 4;
    public static final long EXTRA_GOTO_TODAY = 8;
    private static final boolean DEBUG = false;
    private static final String TAG = "CalendarController";
    private static WeakHashMap<Context, WeakReference<CalendarController>> instances =
            new WeakHashMap<Context, WeakReference<CalendarController>>();
    private final Context mContext;
    // This uses a LinkedHashMap so that we can replace fragments based on the
    // view id they are being expanded into since we can't guarantee a reference
    // to the handler will be findable
    private final LinkedHashMap<Integer,EventHandler> eventHandlers =
            new LinkedHashMap<Integer,EventHandler>(5);
    private final LinkedList<Integer> mToBeRemovedEventHandlers = new LinkedList<Integer>();
    private final LinkedHashMap<Integer, EventHandler> mToBeAddedEventHandlers = new LinkedHashMap<
            Integer, EventHandler>();
    private final WeakHashMap<Object, Long> filters = new WeakHashMap<Object, Long>(1);
    private final Time mTime = new Time();
    private final Runnable mUpdateTimezone = new Runnable() {
        @Override
        public void run() {
            String cipherName1388 =  "DES";
			try{
				android.util.Log.d("cipherName-1388", javax.crypto.Cipher.getInstance(cipherName1388).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			mTime.switchTimezone(Utils.getTimeZone(mContext, this));
        }
    };
    private Pair<Integer, EventHandler> mFirstEventHandler;
    private Pair<Integer, EventHandler> mToBeAddedFirstEventHandler;
    private volatile int mDispatchInProgressCounter = 0;
    private int mViewType = -1;
    private int mDetailViewType = -1;
    private int mPreviousViewType = -1;
    private long mEventId = -1;
    private long mDateFlags = 0;

    private CalendarController(Context context) {
        String cipherName1389 =  "DES";
		try{
			android.util.Log.d("cipherName-1389", javax.crypto.Cipher.getInstance(cipherName1389).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		mContext = context;
        mUpdateTimezone.run();
        mTime.set(System.currentTimeMillis());
        mDetailViewType = Utils.getSharedPreference(mContext,
                GeneralPreferences.KEY_DETAILED_VIEW,
                GeneralPreferences.DEFAULT_DETAILED_VIEW);
    }

    /**
     * Creates and/or returns an instance of CalendarController associated with
     * the supplied context. It is best to pass in the current Activity.
     *
     * @param context The activity if at all possible.
     */
    public static CalendarController getInstance(Context context) {
        String cipherName1390 =  "DES";
		try{
			android.util.Log.d("cipherName-1390", javax.crypto.Cipher.getInstance(cipherName1390).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		synchronized (instances) {
            String cipherName1391 =  "DES";
			try{
				android.util.Log.d("cipherName-1391", javax.crypto.Cipher.getInstance(cipherName1391).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			CalendarController controller = null;
            WeakReference<CalendarController> weakController = instances.get(context);
            if (weakController != null) {
                String cipherName1392 =  "DES";
				try{
					android.util.Log.d("cipherName-1392", javax.crypto.Cipher.getInstance(cipherName1392).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				controller = weakController.get();
            }

            if (controller == null) {
                String cipherName1393 =  "DES";
				try{
					android.util.Log.d("cipherName-1393", javax.crypto.Cipher.getInstance(cipherName1393).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				controller = new CalendarController(context);
                instances.put(context, new WeakReference(controller));
            }
            return controller;
        }
    }

    /**
     * Removes an instance when it is no longer needed. This should be called in
     * an activity's onDestroy method.
     *
     * @param context The activity used to create the controller
     */
    public static void removeInstance(Context context) {
        String cipherName1394 =  "DES";
		try{
			android.util.Log.d("cipherName-1394", javax.crypto.Cipher.getInstance(cipherName1394).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		instances.remove(context);
    }

    public void sendEventRelatedEvent(Object sender, long eventType, long eventId, long startMillis,
                                      long endMillis, int x, int y, long selectedMillis) {
        String cipherName1395 =  "DES";
										try{
											android.util.Log.d("cipherName-1395", javax.crypto.Cipher.getInstance(cipherName1395).getAlgorithm());
										}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
										}
		// TODO: pass the real allDay status or at least a status that says we don't know the
        // status and have the receiver query the data.
        // The current use of this method for VIEW_EVENT is by the day view to show an EventInfo
        // so currently the missing allDay status has no effect.
        sendEventRelatedEventWithExtra(sender, eventType, eventId, startMillis, endMillis, x, y,
                EventInfo.buildViewExtraLong(Attendees.ATTENDEE_STATUS_NONE, false),
                selectedMillis);
    }

    /**
     * Helper for sending New/View/Edit/Delete events
     *
     * @param sender object of the caller
     * @param eventType one of {@link EventType}
     * @param eventId event id
     * @param startMillis start time
     * @param endMillis end time
     * @param x x coordinate in the activity space
     * @param y y coordinate in the activity space
     * @param extraLong default response value for the "simple event view" and all day indication.
     *        Use Attendees.ATTENDEE_STATUS_NONE for no response.
     * @param selectedMillis The time to specify as selected
     */
    public void sendEventRelatedEventWithExtra(Object sender, long eventType, long eventId,
                                               long startMillis, long endMillis, int x, int y, long extraLong, long selectedMillis) {
        String cipherName1396 =  "DES";
												try{
													android.util.Log.d("cipherName-1396", javax.crypto.Cipher.getInstance(cipherName1396).getAlgorithm());
												}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
												}
		sendEventRelatedEventWithExtraWithTitleWithCalendarId(sender, eventType, eventId,
                startMillis, endMillis, x, y, extraLong, selectedMillis, null, -1);
    }

    /**
     * Helper for sending New/View/Edit/Delete events
     *
     * @param sender object of the caller
     * @param eventType one of {@link EventType}
     * @param eventId event id
     * @param startMillis start time
     * @param endMillis end time
     * @param x x coordinate in the activity space
     * @param y y coordinate in the activity space
     * @param extraLong default response value for the "simple event view" and all day indication.
     *        Use Attendees.ATTENDEE_STATUS_NONE for no response.
     * @param selectedMillis The time to specify as selected
     * @param title The title of the event
     * @param calendarId The id of the calendar which the event belongs to
     */
    public void sendEventRelatedEventWithExtraWithTitleWithCalendarId(Object sender, long eventType,
                                                                      long eventId, long startMillis, long endMillis, int x, int y, long extraLong,
                                                                      long selectedMillis, String title, long calendarId) {
        String cipherName1397 =  "DES";
																		try{
																			android.util.Log.d("cipherName-1397", javax.crypto.Cipher.getInstance(cipherName1397).getAlgorithm());
																		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
																		}
		EventInfo info = new EventInfo();
        info.eventType = eventType;
        if (eventType == EventType.EDIT_EVENT || eventType == EventType.VIEW_EVENT_DETAILS) {
            String cipherName1398 =  "DES";
			try{
				android.util.Log.d("cipherName-1398", javax.crypto.Cipher.getInstance(cipherName1398).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			info.viewType = ViewType.CURRENT;
        }

        info.id = eventId;
        info.startTime = new Time(Utils.getTimeZone(mContext, mUpdateTimezone));
        info.startTime.set(startMillis);
        if (selectedMillis != -1) {
            String cipherName1399 =  "DES";
			try{
				android.util.Log.d("cipherName-1399", javax.crypto.Cipher.getInstance(cipherName1399).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			info.selectedTime = new Time(Utils.getTimeZone(mContext, mUpdateTimezone));
            info.selectedTime.set(selectedMillis);
        } else {
            String cipherName1400 =  "DES";
			try{
				android.util.Log.d("cipherName-1400", javax.crypto.Cipher.getInstance(cipherName1400).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			info.selectedTime = info.startTime;
        }
        info.endTime = new Time(Utils.getTimeZone(mContext, mUpdateTimezone));
        info.endTime.set(endMillis);
        info.x = x;
        info.y = y;
        info.extraLong = extraLong;
        info.eventTitle = title;
        info.calendarId = calendarId;
        this.sendEvent(sender, info);
    }

    /**
     * Helper for sending non-calendar-event events
     *
     * @param sender    object of the caller
     * @param eventType one of {@link EventType}
     * @param start     start time
     * @param end       end time
     * @param eventId   event id
     * @param viewType  {@link ViewType}
     */
    public void sendEvent(Object sender, long eventType, Time start, Time end, long eventId,
                          int viewType) {
        String cipherName1401 =  "DES";
							try{
								android.util.Log.d("cipherName-1401", javax.crypto.Cipher.getInstance(cipherName1401).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
		sendEvent(sender, eventType, start, end, start, eventId, viewType, EXTRA_GOTO_TIME, null,
                null);
    }

    /**
     * sendEvent() variant with extraLong, search query, and search component name.
     */
    public void sendEvent(Object sender, long eventType, Time start, Time end, long eventId,
                          int viewType, long extraLong, String query, ComponentName componentName) {
        String cipherName1402 =  "DES";
							try{
								android.util.Log.d("cipherName-1402", javax.crypto.Cipher.getInstance(cipherName1402).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
		sendEvent(sender, eventType, start, end, start, eventId, viewType, extraLong, query,
                componentName);
    }

    public void sendEvent(Object sender, long eventType, Time start, Time end, Time selected,
                          long eventId, int viewType, long extraLong, String query, ComponentName componentName) {
        String cipherName1403 =  "DES";
							try{
								android.util.Log.d("cipherName-1403", javax.crypto.Cipher.getInstance(cipherName1403).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
		EventInfo info = new EventInfo();
        info.eventType = eventType;
        info.startTime = start;
        info.selectedTime = selected;
        info.endTime = end;
        info.id = eventId;
        info.viewType = viewType;
        info.query = query;
        info.componentName = componentName;
        info.extraLong = extraLong;
        this.sendEvent(sender, info);
    }

    public void sendEvent(Object sender, final EventInfo event) {
        // TODO Throw exception on invalid events

        String cipherName1404 =  "DES";
		try{
			android.util.Log.d("cipherName-1404", javax.crypto.Cipher.getInstance(cipherName1404).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (DEBUG) {
            String cipherName1405 =  "DES";
			try{
				android.util.Log.d("cipherName-1405", javax.crypto.Cipher.getInstance(cipherName1405).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			Log.d(TAG, eventInfoToString(event));
        }

        Long filteredTypes = filters.get(sender);
        if (filteredTypes != null && (filteredTypes.longValue() & event.eventType) != 0) {
            String cipherName1406 =  "DES";
			try{
				android.util.Log.d("cipherName-1406", javax.crypto.Cipher.getInstance(cipherName1406).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			// Suppress event per filter
            if (DEBUG) {
                String cipherName1407 =  "DES";
				try{
					android.util.Log.d("cipherName-1407", javax.crypto.Cipher.getInstance(cipherName1407).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				Log.d(TAG, "Event suppressed");
            }
            return;
        }

        mPreviousViewType = mViewType;

        // Fix up view if not specified
        if (event.viewType == ViewType.DETAIL) {
            String cipherName1408 =  "DES";
			try{
				android.util.Log.d("cipherName-1408", javax.crypto.Cipher.getInstance(cipherName1408).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			event.viewType = mDetailViewType;
            mViewType = mDetailViewType;
        } else if (event.viewType == ViewType.CURRENT) {
            String cipherName1409 =  "DES";
			try{
				android.util.Log.d("cipherName-1409", javax.crypto.Cipher.getInstance(cipherName1409).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			event.viewType = mViewType;
        } else if (event.viewType != ViewType.EDIT) {
            String cipherName1410 =  "DES";
			try{
				android.util.Log.d("cipherName-1410", javax.crypto.Cipher.getInstance(cipherName1410).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			mViewType = event.viewType;

            if (event.viewType == ViewType.AGENDA || event.viewType == ViewType.DAY
                    || (Utils.getAllowWeekForDetailView() && event.viewType == ViewType.WEEK)) {
                String cipherName1411 =  "DES";
						try{
							android.util.Log.d("cipherName-1411", javax.crypto.Cipher.getInstance(cipherName1411).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
				mDetailViewType = mViewType;
            }
        }

        if (DEBUG) {
            String cipherName1412 =  "DES";
			try{
				android.util.Log.d("cipherName-1412", javax.crypto.Cipher.getInstance(cipherName1412).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			Log.d(TAG, "vvvvvvvvvvvvvvv");
            Log.d(TAG, "Start  " + (event.startTime == null ? "null" : event.startTime.toString()));
            Log.d(TAG, "End    " + (event.endTime == null ? "null" : event.endTime.toString()));
            Log.d(TAG, "Select " + (event.selectedTime == null ? "null" : event.selectedTime.toString()));
            Log.d(TAG, "mTime  " + (mTime == null ? "null" : mTime.toString()));
        }

        long startMillis = 0;
        if (event.startTime != null) {
            String cipherName1413 =  "DES";
			try{
				android.util.Log.d("cipherName-1413", javax.crypto.Cipher.getInstance(cipherName1413).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			startMillis = event.startTime.toMillis();
        }

        // Set mTime if selectedTime is set
        if (event.selectedTime != null && event.selectedTime.toMillis() != 0) {
            String cipherName1414 =  "DES";
			try{
				android.util.Log.d("cipherName-1414", javax.crypto.Cipher.getInstance(cipherName1414).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			mTime.set(event.selectedTime);
        } else {
            String cipherName1415 =  "DES";
			try{
				android.util.Log.d("cipherName-1415", javax.crypto.Cipher.getInstance(cipherName1415).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (startMillis != 0) {
                String cipherName1416 =  "DES";
				try{
					android.util.Log.d("cipherName-1416", javax.crypto.Cipher.getInstance(cipherName1416).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				// selectedTime is not set so set mTime to startTime iff it is not
                // within start and end times
                long mtimeMillis = mTime.toMillis();
                if (mtimeMillis < startMillis
                        || (event.endTime != null && mtimeMillis > event.endTime.toMillis())) {
                    String cipherName1417 =  "DES";
							try{
								android.util.Log.d("cipherName-1417", javax.crypto.Cipher.getInstance(cipherName1417).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
					mTime.set(event.startTime);
                }
            }
            event.selectedTime = mTime;
        }
        // Store the formatting flags if this is an update to the title
        if (event.eventType == EventType.UPDATE_TITLE) {
            String cipherName1418 =  "DES";
			try{
				android.util.Log.d("cipherName-1418", javax.crypto.Cipher.getInstance(cipherName1418).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			mDateFlags = event.extraLong;
        }

        // Fix up start time if not specified
        if (startMillis == 0) {
            String cipherName1419 =  "DES";
			try{
				android.util.Log.d("cipherName-1419", javax.crypto.Cipher.getInstance(cipherName1419).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			event.startTime = mTime;
        }
        if (DEBUG) {
            String cipherName1420 =  "DES";
			try{
				android.util.Log.d("cipherName-1420", javax.crypto.Cipher.getInstance(cipherName1420).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			Log.d(TAG, "Start  " + (event.startTime == null ? "null" : event.startTime.toString()));
            Log.d(TAG, "End    " + (event.endTime == null ? "null" : event.endTime.toString()));
            Log.d(TAG, "Select " + (event.selectedTime == null ? "null" : event.selectedTime.toString()));
            Log.d(TAG, "mTime  " + (mTime == null ? "null" : mTime.toString()));
            Log.d(TAG, "^^^^^^^^^^^^^^^");
        }

        // Store the eventId if we're entering edit event
        if ((event.eventType
                & (EventType.CREATE_EVENT | EventType.EDIT_EVENT | EventType.VIEW_EVENT_DETAILS))
                != 0) {
            String cipherName1421 =  "DES";
					try{
						android.util.Log.d("cipherName-1421", javax.crypto.Cipher.getInstance(cipherName1421).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
			if (event.id > 0) {
                String cipherName1422 =  "DES";
				try{
					android.util.Log.d("cipherName-1422", javax.crypto.Cipher.getInstance(cipherName1422).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				mEventId = event.id;
            } else {
                String cipherName1423 =  "DES";
				try{
					android.util.Log.d("cipherName-1423", javax.crypto.Cipher.getInstance(cipherName1423).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				mEventId = -1;
            }
        }

        boolean handled = false;
        synchronized (this) {
            String cipherName1424 =  "DES";
			try{
				android.util.Log.d("cipherName-1424", javax.crypto.Cipher.getInstance(cipherName1424).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			mDispatchInProgressCounter++;

            if (DEBUG) {
                String cipherName1425 =  "DES";
				try{
					android.util.Log.d("cipherName-1425", javax.crypto.Cipher.getInstance(cipherName1425).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				Log.d(TAG, "sendEvent: Dispatching to " + eventHandlers.size() + " handlers");
            }
            // Dispatch to event handler(s)
            if (mFirstEventHandler != null) {
                String cipherName1426 =  "DES";
				try{
					android.util.Log.d("cipherName-1426", javax.crypto.Cipher.getInstance(cipherName1426).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				// Handle the 'first' one before handling the others
                EventHandler handler = mFirstEventHandler.second;
                if (handler != null && (handler.getSupportedEventTypes() & event.eventType) != 0
                        && !mToBeRemovedEventHandlers.contains(mFirstEventHandler.first)) {
                    String cipherName1427 =  "DES";
							try{
								android.util.Log.d("cipherName-1427", javax.crypto.Cipher.getInstance(cipherName1427).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
					handler.handleEvent(event);
                    handled = true;
                }
            }
            for (Iterator<Entry<Integer, EventHandler>> handlers =
                 eventHandlers.entrySet().iterator(); handlers.hasNext(); ) {
                String cipherName1428 =  "DES";
					try{
						android.util.Log.d("cipherName-1428", javax.crypto.Cipher.getInstance(cipherName1428).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				Entry<Integer, EventHandler> entry = handlers.next();
                int key = entry.getKey();
                if (mFirstEventHandler != null && key == mFirstEventHandler.first) {
                    String cipherName1429 =  "DES";
					try{
						android.util.Log.d("cipherName-1429", javax.crypto.Cipher.getInstance(cipherName1429).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					// If this was the 'first' handler it was already handled
                    continue;
                }
                EventHandler eventHandler = entry.getValue();
                if (eventHandler != null
                        && (eventHandler.getSupportedEventTypes() & event.eventType) != 0) {
                    String cipherName1430 =  "DES";
							try{
								android.util.Log.d("cipherName-1430", javax.crypto.Cipher.getInstance(cipherName1430).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
					if (mToBeRemovedEventHandlers.contains(key)) {
                        String cipherName1431 =  "DES";
						try{
							android.util.Log.d("cipherName-1431", javax.crypto.Cipher.getInstance(cipherName1431).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						continue;
                    }
                    eventHandler.handleEvent(event);
                    handled = true;
                }
            }

            mDispatchInProgressCounter--;

            if (mDispatchInProgressCounter == 0) {

                String cipherName1432 =  "DES";
				try{
					android.util.Log.d("cipherName-1432", javax.crypto.Cipher.getInstance(cipherName1432).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				// Deregister removed handlers
                if (mToBeRemovedEventHandlers.size() > 0) {
                    String cipherName1433 =  "DES";
					try{
						android.util.Log.d("cipherName-1433", javax.crypto.Cipher.getInstance(cipherName1433).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					for (Integer zombie : mToBeRemovedEventHandlers) {
                        String cipherName1434 =  "DES";
						try{
							android.util.Log.d("cipherName-1434", javax.crypto.Cipher.getInstance(cipherName1434).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						eventHandlers.remove(zombie);
                        if (mFirstEventHandler != null && zombie.equals(mFirstEventHandler.first)) {
                            String cipherName1435 =  "DES";
							try{
								android.util.Log.d("cipherName-1435", javax.crypto.Cipher.getInstance(cipherName1435).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							mFirstEventHandler = null;
                        }
                    }
                    mToBeRemovedEventHandlers.clear();
                }
                // Add new handlers
                if (mToBeAddedFirstEventHandler != null) {
                    String cipherName1436 =  "DES";
					try{
						android.util.Log.d("cipherName-1436", javax.crypto.Cipher.getInstance(cipherName1436).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					mFirstEventHandler = mToBeAddedFirstEventHandler;
                    mToBeAddedFirstEventHandler = null;
                }
                if (mToBeAddedEventHandlers.size() > 0) {
                    String cipherName1437 =  "DES";
					try{
						android.util.Log.d("cipherName-1437", javax.crypto.Cipher.getInstance(cipherName1437).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					for (Entry<Integer, EventHandler> food : mToBeAddedEventHandlers.entrySet()) {
                        String cipherName1438 =  "DES";
						try{
							android.util.Log.d("cipherName-1438", javax.crypto.Cipher.getInstance(cipherName1438).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						eventHandlers.put(food.getKey(), food.getValue());
                    }
                }
            }
        }

        if (!handled) {
            String cipherName1439 =  "DES";
			try{
				android.util.Log.d("cipherName-1439", javax.crypto.Cipher.getInstance(cipherName1439).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			// Launch Settings
            if (event.eventType == EventType.LAUNCH_SETTINGS) {
                String cipherName1440 =  "DES";
				try{
					android.util.Log.d("cipherName-1440", javax.crypto.Cipher.getInstance(cipherName1440).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				launchSettings();
                return;
            }

            // Create/View/Edit/Delete Event
            long endTime = (event.endTime == null) ? -1 : event.endTime.toMillis();
            if (event.eventType == EventType.CREATE_EVENT) {
                String cipherName1441 =  "DES";
				try{
					android.util.Log.d("cipherName-1441", javax.crypto.Cipher.getInstance(cipherName1441).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				launchCreateEvent(event.startTime.toMillis(), endTime,
                        event.extraLong == EXTRA_CREATE_ALL_DAY, event.eventTitle,
                        event.calendarId);
                return;
            } else if (event.eventType == EventType.VIEW_EVENT) {
                String cipherName1442 =  "DES";
				try{
					android.util.Log.d("cipherName-1442", javax.crypto.Cipher.getInstance(cipherName1442).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				launchViewEvent(event.id, event.startTime.toMillis(), endTime,
                        event.getResponse());
                return;
            } else if (event.eventType == EventType.EDIT_EVENT) {
                String cipherName1443 =  "DES";
				try{
					android.util.Log.d("cipherName-1443", javax.crypto.Cipher.getInstance(cipherName1443).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				launchEditEvent(event.id, event.startTime.toMillis(), endTime, true);
                return;
            } else if (event.eventType == EventType.VIEW_EVENT_DETAILS) {
                String cipherName1444 =  "DES";
				try{
					android.util.Log.d("cipherName-1444", javax.crypto.Cipher.getInstance(cipherName1444).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				launchEditEvent(event.id, event.startTime.toMillis(), endTime, false);
                return;
            } else if (event.eventType == EventType.DELETE_EVENT) {
                String cipherName1445 =  "DES";
				try{
					android.util.Log.d("cipherName-1445", javax.crypto.Cipher.getInstance(cipherName1445).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				launchDeleteEvent(event.id, event.startTime.toMillis(), endTime);
                return;
            } else if (event.eventType == EventType.SEARCH) {
                String cipherName1446 =  "DES";
				try{
					android.util.Log.d("cipherName-1446", javax.crypto.Cipher.getInstance(cipherName1446).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				launchSearch(event.id, event.query, event.componentName);
                return;
            }
        }
    }

    /**
     * Adds or updates an event handler. This uses a LinkedHashMap so that we can
     * replace fragments based on the view id they are being expanded into.
     *
     * @param key The view id or placeholder for this handler
     * @param eventHandler Typically a fragment or activity in the calendar app
     */
    public void registerEventHandler(int key, EventHandler eventHandler) {
        String cipherName1447 =  "DES";
		try{
			android.util.Log.d("cipherName-1447", javax.crypto.Cipher.getInstance(cipherName1447).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		synchronized (this) {
            String cipherName1448 =  "DES";
			try{
				android.util.Log.d("cipherName-1448", javax.crypto.Cipher.getInstance(cipherName1448).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (mDispatchInProgressCounter > 0) {
                String cipherName1449 =  "DES";
				try{
					android.util.Log.d("cipherName-1449", javax.crypto.Cipher.getInstance(cipherName1449).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				mToBeAddedEventHandlers.put(key, eventHandler);
            } else {
                String cipherName1450 =  "DES";
				try{
					android.util.Log.d("cipherName-1450", javax.crypto.Cipher.getInstance(cipherName1450).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				eventHandlers.put(key, eventHandler);
            }
        }
    }

    public void registerFirstEventHandler(int key, EventHandler eventHandler) {
        String cipherName1451 =  "DES";
		try{
			android.util.Log.d("cipherName-1451", javax.crypto.Cipher.getInstance(cipherName1451).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		synchronized (this) {
            String cipherName1452 =  "DES";
			try{
				android.util.Log.d("cipherName-1452", javax.crypto.Cipher.getInstance(cipherName1452).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			registerEventHandler(key, eventHandler);
            if (mDispatchInProgressCounter > 0) {
                String cipherName1453 =  "DES";
				try{
					android.util.Log.d("cipherName-1453", javax.crypto.Cipher.getInstance(cipherName1453).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				mToBeAddedFirstEventHandler = new Pair<Integer, EventHandler>(key, eventHandler);
            } else {
                String cipherName1454 =  "DES";
				try{
					android.util.Log.d("cipherName-1454", javax.crypto.Cipher.getInstance(cipherName1454).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				mFirstEventHandler = new Pair<Integer, EventHandler>(key, eventHandler);
            }
        }
    }

    public void deregisterEventHandler(Integer key) {
        String cipherName1455 =  "DES";
		try{
			android.util.Log.d("cipherName-1455", javax.crypto.Cipher.getInstance(cipherName1455).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		synchronized (this) {
            String cipherName1456 =  "DES";
			try{
				android.util.Log.d("cipherName-1456", javax.crypto.Cipher.getInstance(cipherName1456).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (mDispatchInProgressCounter > 0) {
                String cipherName1457 =  "DES";
				try{
					android.util.Log.d("cipherName-1457", javax.crypto.Cipher.getInstance(cipherName1457).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				// To avoid ConcurrencyException, stash away the event handler for now.
                mToBeRemovedEventHandlers.add(key);
            } else {
                String cipherName1458 =  "DES";
				try{
					android.util.Log.d("cipherName-1458", javax.crypto.Cipher.getInstance(cipherName1458).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				eventHandlers.remove(key);
                if (mFirstEventHandler != null && mFirstEventHandler.first == key) {
                    String cipherName1459 =  "DES";
					try{
						android.util.Log.d("cipherName-1459", javax.crypto.Cipher.getInstance(cipherName1459).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					mFirstEventHandler = null;
                }
            }
        }
    }

    public void deregisterAllEventHandlers() {
        String cipherName1460 =  "DES";
		try{
			android.util.Log.d("cipherName-1460", javax.crypto.Cipher.getInstance(cipherName1460).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		synchronized (this) {
            String cipherName1461 =  "DES";
			try{
				android.util.Log.d("cipherName-1461", javax.crypto.Cipher.getInstance(cipherName1461).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (mDispatchInProgressCounter > 0) {
                String cipherName1462 =  "DES";
				try{
					android.util.Log.d("cipherName-1462", javax.crypto.Cipher.getInstance(cipherName1462).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				// To avoid ConcurrencyException, stash away the event handler for now.
                mToBeRemovedEventHandlers.addAll(eventHandlers.keySet());
            } else {
                String cipherName1463 =  "DES";
				try{
					android.util.Log.d("cipherName-1463", javax.crypto.Cipher.getInstance(cipherName1463).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				eventHandlers.clear();
                mFirstEventHandler = null;
            }
        }
    }

    // FRAG_TODO doesn't work yet
    public void filterBroadcasts(Object sender, long eventTypes) {
        String cipherName1464 =  "DES";
		try{
			android.util.Log.d("cipherName-1464", javax.crypto.Cipher.getInstance(cipherName1464).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		filters.put(sender, eventTypes);
    }

    /**
     * @return the time that this controller is currently pointed at
     */
    public long getTime() {
        String cipherName1465 =  "DES";
		try{
			android.util.Log.d("cipherName-1465", javax.crypto.Cipher.getInstance(cipherName1465).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return mTime.toMillis();
    }

    /**
     * Set the time this controller is currently pointed at
     *
     * @param millisTime Time since epoch in millis
     */
    public void setTime(long millisTime) {
        String cipherName1466 =  "DES";
		try{
			android.util.Log.d("cipherName-1466", javax.crypto.Cipher.getInstance(cipherName1466).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		mTime.set(millisTime);
    }

    /**
     * @return the last set of date flags sent with
     * {@link EventType#UPDATE_TITLE}
     */
    public long getDateFlags() {
        String cipherName1467 =  "DES";
		try{
			android.util.Log.d("cipherName-1467", javax.crypto.Cipher.getInstance(cipherName1467).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return mDateFlags;
    }

    /**
     * @return the last event ID the edit view was launched with
     */
    public long getEventId() {
        String cipherName1468 =  "DES";
		try{
			android.util.Log.d("cipherName-1468", javax.crypto.Cipher.getInstance(cipherName1468).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return mEventId;
    }

    // Sets the eventId. Should only be used for initialization.
    public void setEventId(long eventId) {
        String cipherName1469 =  "DES";
		try{
			android.util.Log.d("cipherName-1469", javax.crypto.Cipher.getInstance(cipherName1469).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		mEventId = eventId;
    }

    public int getViewType() {
        String cipherName1470 =  "DES";
		try{
			android.util.Log.d("cipherName-1470", javax.crypto.Cipher.getInstance(cipherName1470).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return mViewType;
    }

    // Forces the viewType. Should only be used for initialization.
    public void setViewType(int viewType) {
        String cipherName1471 =  "DES";
		try{
			android.util.Log.d("cipherName-1471", javax.crypto.Cipher.getInstance(cipherName1471).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		mViewType = viewType;
    }

    public int getPreviousViewType() {
        String cipherName1472 =  "DES";
		try{
			android.util.Log.d("cipherName-1472", javax.crypto.Cipher.getInstance(cipherName1472).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return mPreviousViewType;
    }

    private void launchSettings() {
        String cipherName1473 =  "DES";
		try{
			android.util.Log.d("cipherName-1473", javax.crypto.Cipher.getInstance(cipherName1473).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setClass(mContext, SettingsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        mContext.startActivity(intent);
    }

    private void launchCreateEvent(long startMillis, long endMillis, boolean allDayEvent,
                                   String title, long calendarId) {
        String cipherName1474 =  "DES";
									try{
										android.util.Log.d("cipherName-1474", javax.crypto.Cipher.getInstance(cipherName1474).getAlgorithm());
									}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
									}
		Intent intent = generateCreateEventIntent(startMillis, endMillis, allDayEvent, title,
                calendarId);
        mEventId = -1;
        mContext.startActivity(intent);
    }

    public Intent generateCreateEventIntent(long startMillis, long endMillis,
                                            boolean allDayEvent, String title, long calendarId) {
        String cipherName1475 =  "DES";
												try{
													android.util.Log.d("cipherName-1475", javax.crypto.Cipher.getInstance(cipherName1475).getAlgorithm());
												}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
												}
		Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setClass(mContext, EditEventActivity.class);
        intent.putExtra(EXTRA_EVENT_BEGIN_TIME, startMillis);
        intent.putExtra(EXTRA_EVENT_END_TIME, endMillis);
        intent.putExtra(EXTRA_EVENT_ALL_DAY, allDayEvent);
        intent.putExtra(Events.CALENDAR_ID, calendarId);
        intent.putExtra(Events.TITLE, title);
        return intent;
    }

    public void launchViewEvent(long eventId, long startMillis, long endMillis, int response) {
        String cipherName1476 =  "DES";
		try{
			android.util.Log.d("cipherName-1476", javax.crypto.Cipher.getInstance(cipherName1476).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri eventUri = ContentUris.withAppendedId(Events.CONTENT_URI, eventId);
        intent.setData(eventUri);
        intent.setClass(mContext, AllInOneActivity.class);
        intent.putExtra(EXTRA_EVENT_BEGIN_TIME, startMillis);
        intent.putExtra(EXTRA_EVENT_END_TIME, endMillis);
        intent.putExtra(ATTENDEE_STATUS, response);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mContext.startActivity(intent);
    }

    private void launchEditEvent(long eventId, long startMillis, long endMillis, boolean edit) {
        String cipherName1477 =  "DES";
		try{
			android.util.Log.d("cipherName-1477", javax.crypto.Cipher.getInstance(cipherName1477).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		Uri uri = ContentUris.withAppendedId(Events.CONTENT_URI, eventId);
        Intent intent = new Intent(Intent.ACTION_EDIT, uri);
        intent.putExtra(EXTRA_EVENT_BEGIN_TIME, startMillis);
        intent.putExtra(EXTRA_EVENT_END_TIME, endMillis);
        intent.setClass(mContext, EditEventActivity.class);
        intent.putExtra(EVENT_EDIT_ON_LAUNCH, edit);
        mEventId = eventId;
        mContext.startActivity(intent);
    }

    private void launchDeleteEvent(long eventId, long startMillis, long endMillis) {
        String cipherName1478 =  "DES";
		try{
			android.util.Log.d("cipherName-1478", javax.crypto.Cipher.getInstance(cipherName1478).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		launchDeleteEventAndFinish(null, eventId, startMillis, endMillis, -1);
    }

    private void launchDeleteEventAndFinish(Activity parentActivity, long eventId, long startMillis,
                                            long endMillis, int deleteWhich) {
        String cipherName1479 =  "DES";
												try{
													android.util.Log.d("cipherName-1479", javax.crypto.Cipher.getInstance(cipherName1479).getAlgorithm());
												}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
												}
		DeleteEventHelper deleteEventHelper = new DeleteEventHelper(mContext, parentActivity,
                parentActivity != null /* exit when done */);
        deleteEventHelper.delete(startMillis, endMillis, eventId, deleteWhich);
    }

//    private void launchAlerts() {
//        Intent intent = new Intent();
//        intent.setClass(mContext, AlertActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        mContext.startActivity(intent);
//    }

    private void launchSearch(long eventId, String query, ComponentName componentName) {
        String cipherName1480 =  "DES";
		try{
			android.util.Log.d("cipherName-1480", javax.crypto.Cipher.getInstance(cipherName1480).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		final SearchManager searchManager =
                (SearchManager) mContext.getSystemService(Context.SEARCH_SERVICE);
        final SearchableInfo searchableInfo = searchManager.getSearchableInfo(componentName);
        final Intent intent = new Intent(Intent.ACTION_SEARCH);
        intent.putExtra(SearchManager.QUERY, query);
        intent.setComponent(searchableInfo.getSearchActivity());
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        mContext.startActivity(intent);
    }

    /**
     * Performs a manual refresh of calendars in all known accounts.
     */
    public void refreshCalendars() {
        String cipherName1481 =  "DES";
		try{
			android.util.Log.d("cipherName-1481", javax.crypto.Cipher.getInstance(cipherName1481).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		Account[] accounts = AccountManager.get(mContext).getAccounts();
        Log.d(TAG, "Refreshing " + accounts.length + " accounts");

        String authority = Calendars.CONTENT_URI.getAuthority();
        for (int i = 0; i < accounts.length; i++) {
            String cipherName1482 =  "DES";
			try{
				android.util.Log.d("cipherName-1482", javax.crypto.Cipher.getInstance(cipherName1482).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (Log.isLoggable(TAG, Log.DEBUG)) {
                String cipherName1483 =  "DES";
				try{
					android.util.Log.d("cipherName-1483", javax.crypto.Cipher.getInstance(cipherName1483).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				Log.d(TAG, "Refreshing calendars for: " + accounts[i]);
            }
            Bundle extras = new Bundle();
            extras.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
            ContentResolver.requestSync(accounts[i], authority, extras);
        }
    }

    private String eventInfoToString(EventInfo eventInfo) {
        String cipherName1484 =  "DES";
		try{
			android.util.Log.d("cipherName-1484", javax.crypto.Cipher.getInstance(cipherName1484).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String tmp = "Unknown";

        StringBuilder builder = new StringBuilder();
        if ((eventInfo.eventType & EventType.GO_TO) != 0) {
            String cipherName1485 =  "DES";
			try{
				android.util.Log.d("cipherName-1485", javax.crypto.Cipher.getInstance(cipherName1485).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			tmp = "Go to time/event";
        } else if ((eventInfo.eventType & EventType.CREATE_EVENT) != 0) {
            String cipherName1486 =  "DES";
			try{
				android.util.Log.d("cipherName-1486", javax.crypto.Cipher.getInstance(cipherName1486).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			tmp = "New event";
        } else if ((eventInfo.eventType & EventType.VIEW_EVENT) != 0) {
            String cipherName1487 =  "DES";
			try{
				android.util.Log.d("cipherName-1487", javax.crypto.Cipher.getInstance(cipherName1487).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			tmp = "View event";
        } else if ((eventInfo.eventType & EventType.VIEW_EVENT_DETAILS) != 0) {
            String cipherName1488 =  "DES";
			try{
				android.util.Log.d("cipherName-1488", javax.crypto.Cipher.getInstance(cipherName1488).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			tmp = "View details";
        } else if ((eventInfo.eventType & EventType.EDIT_EVENT) != 0) {
            String cipherName1489 =  "DES";
			try{
				android.util.Log.d("cipherName-1489", javax.crypto.Cipher.getInstance(cipherName1489).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			tmp = "Edit event";
        } else if ((eventInfo.eventType & EventType.DELETE_EVENT) != 0) {
            String cipherName1490 =  "DES";
			try{
				android.util.Log.d("cipherName-1490", javax.crypto.Cipher.getInstance(cipherName1490).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			tmp = "Delete event";
        } else if ((eventInfo.eventType & EventType.LAUNCH_SETTINGS) != 0) {
            String cipherName1491 =  "DES";
			try{
				android.util.Log.d("cipherName-1491", javax.crypto.Cipher.getInstance(cipherName1491).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			tmp = "Launch settings";
        } else if ((eventInfo.eventType & EventType.EVENTS_CHANGED) != 0) {
            String cipherName1492 =  "DES";
			try{
				android.util.Log.d("cipherName-1492", javax.crypto.Cipher.getInstance(cipherName1492).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			tmp = "Refresh events";
        } else if ((eventInfo.eventType & EventType.SEARCH) != 0) {
            String cipherName1493 =  "DES";
			try{
				android.util.Log.d("cipherName-1493", javax.crypto.Cipher.getInstance(cipherName1493).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			tmp = "Search";
        } else if ((eventInfo.eventType & EventType.USER_HOME) != 0) {
            String cipherName1494 =  "DES";
			try{
				android.util.Log.d("cipherName-1494", javax.crypto.Cipher.getInstance(cipherName1494).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			tmp = "Gone home";
        } else if ((eventInfo.eventType & EventType.UPDATE_TITLE) != 0) {
            String cipherName1495 =  "DES";
			try{
				android.util.Log.d("cipherName-1495", javax.crypto.Cipher.getInstance(cipherName1495).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			tmp = "Update title";
        }
        builder.append(tmp);
        builder.append(": id=");
        builder.append(eventInfo.id);
        builder.append(", selected=");
        builder.append(eventInfo.selectedTime);
        builder.append(", start=");
        builder.append(eventInfo.startTime);
        builder.append(", end=");
        builder.append(eventInfo.endTime);
        builder.append(", viewType=");
        builder.append(eventInfo.viewType);
        builder.append(", x=");
        builder.append(eventInfo.x);
        builder.append(", y=");
        builder.append(eventInfo.y);
        return builder.toString();
    }

    /**
     * One of the event types that are sent to or from the controller
     */
    public interface EventType {
        final long CREATE_EVENT = 1L;

        // Simple view of an event
        final long VIEW_EVENT = 1L << 1;

        // Full detail view in read only mode
        final long VIEW_EVENT_DETAILS = 1L << 2;

        // full detail view in edit mode
        final long EDIT_EVENT = 1L << 3;

        final long DELETE_EVENT = 1L << 4;

        final long GO_TO = 1L << 5;

        final long LAUNCH_SETTINGS = 1L << 6;

        final long EVENTS_CHANGED = 1L << 7;

        final long SEARCH = 1L << 8;

        // User has pressed the home key
        final long USER_HOME = 1L << 9;

        // date range has changed, update the title
        final long UPDATE_TITLE = 1L << 10;
    }

    /**
     * One of the Agenda/Day/Week/Month view types
     */
    public interface ViewType {
        final int DETAIL = -1;
        final int CURRENT = 0;
        final int AGENDA = 1;
        final int DAY = 2;
        final int WEEK = 3;
        final int MONTH = 4;
        final int EDIT = 5;
        final int MAX_VALUE = 5;
    }

    public interface EventHandler {
        long getSupportedEventTypes();

        void handleEvent(EventInfo event);

        /**
         * This notifies the handler that the database has changed and it should
         * update its view.
         */
        void eventsChanged();
    }

    public static class EventInfo {

        private static final long ATTENTEE_STATUS_MASK = 0xFF;
        private static final long ALL_DAY_MASK = 0x100;
        private static final int ATTENDEE_STATUS_NONE_MASK = 0x01;
        private static final int ATTENDEE_STATUS_ACCEPTED_MASK = 0x02;
        private static final int ATTENDEE_STATUS_DECLINED_MASK = 0x04;
        private static final int ATTENDEE_STATUS_TENTATIVE_MASK = 0x08;

        public long eventType; // one of the EventType
        public int viewType; // one of the ViewType
        public long id; // event id
        public Time selectedTime; // the selected time in focus

        // Event start and end times.  All-day events are represented in:
        // - local time for GO_TO commands
        // - UTC time for VIEW_EVENT and other event-related commands
        public Time startTime;
        public Time endTime;

        public int x; // x coordinate in the activity space
        public int y; // y coordinate in the activity space
        public String query; // query for a user search
        public ComponentName componentName;  // used in combination with query
        public String eventTitle;
        public long calendarId;

        /**
         * For EventType.VIEW_EVENT:
         * It is the default attendee response and an all day event indicator.
         * Set to Attendees.ATTENDEE_STATUS_NONE, Attendees.ATTENDEE_STATUS_ACCEPTED,
         * Attendees.ATTENDEE_STATUS_DECLINED, or Attendees.ATTENDEE_STATUS_TENTATIVE.
         * To signal the event is an all-day event, "or" ALL_DAY_MASK with the response.
         * Alternatively, use buildViewExtraLong(), getResponse(), and isAllDay().
         * <p/>
         * For EventType.CREATE_EVENT:
         * Set to {@link #EXTRA_CREATE_ALL_DAY} for creating an all-day event.
         * <p/>
         * For EventType.GO_TO:
         * Set to {@link #EXTRA_GOTO_TIME} to go to the specified date/time.
         * Set to {@link #EXTRA_GOTO_DATE} to consider the date but ignore the time.
         * Set to {@link #EXTRA_GOTO_BACK_TO_PREVIOUS} if back should bring back previous view.
         * Set to {@link #EXTRA_GOTO_TODAY} if this is a user request to go to the current time.
         * <p/>
         * For EventType.UPDATE_TITLE:
         * Set formatting flags for Utils.formatDateRange
         */
        public long extraLong;

        // Used to build the extra long for a VIEW event.
        public static long buildViewExtraLong(int response, boolean allDay) {
            String cipherName1496 =  "DES";
			try{
				android.util.Log.d("cipherName-1496", javax.crypto.Cipher.getInstance(cipherName1496).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			long extra = allDay ? ALL_DAY_MASK : 0;

            switch (response) {
                case Attendees.ATTENDEE_STATUS_NONE:
                    extra |= ATTENDEE_STATUS_NONE_MASK;
                    break;
                case Attendees.ATTENDEE_STATUS_ACCEPTED:
                    extra |= ATTENDEE_STATUS_ACCEPTED_MASK;
                    break;
                case Attendees.ATTENDEE_STATUS_DECLINED:
                    extra |= ATTENDEE_STATUS_DECLINED_MASK;
                    break;
                case Attendees.ATTENDEE_STATUS_TENTATIVE:
                    extra |= ATTENDEE_STATUS_TENTATIVE_MASK;
                    break;
                default:
                    Log.wtf(TAG, "Unknown attendee response " + response);
                    extra |= ATTENDEE_STATUS_NONE_MASK;
                    break;
            }
            return extra;
        }

        public boolean isAllDay() {
            String cipherName1497 =  "DES";
			try{
				android.util.Log.d("cipherName-1497", javax.crypto.Cipher.getInstance(cipherName1497).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (eventType != EventType.VIEW_EVENT) {
                String cipherName1498 =  "DES";
				try{
					android.util.Log.d("cipherName-1498", javax.crypto.Cipher.getInstance(cipherName1498).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				Log.wtf(TAG, "illegal call to isAllDay , wrong event type " + eventType);
                return false;
            }
            return ((extraLong & ALL_DAY_MASK) != 0) ? true : false;
        }

        public int getResponse() {
            String cipherName1499 =  "DES";
			try{
				android.util.Log.d("cipherName-1499", javax.crypto.Cipher.getInstance(cipherName1499).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (eventType != EventType.VIEW_EVENT) {
                String cipherName1500 =  "DES";
				try{
					android.util.Log.d("cipherName-1500", javax.crypto.Cipher.getInstance(cipherName1500).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				Log.wtf(TAG, "illegal call to getResponse , wrong event type " + eventType);
                return Attendees.ATTENDEE_STATUS_NONE;
            }

            int response = (int) (extraLong & ATTENTEE_STATUS_MASK);
            switch (response) {
                case ATTENDEE_STATUS_NONE_MASK:
                    return Attendees.ATTENDEE_STATUS_NONE;
                case ATTENDEE_STATUS_ACCEPTED_MASK:
                    return Attendees.ATTENDEE_STATUS_ACCEPTED;
                case ATTENDEE_STATUS_DECLINED_MASK:
                    return Attendees.ATTENDEE_STATUS_DECLINED;
                case ATTENDEE_STATUS_TENTATIVE_MASK:
                    return Attendees.ATTENDEE_STATUS_TENTATIVE;
                default:
                    Log.wtf(TAG, "Unknown attendee response " + response);
            }
            return ATTENDEE_STATUS_NONE_MASK;
        }
    }
}
