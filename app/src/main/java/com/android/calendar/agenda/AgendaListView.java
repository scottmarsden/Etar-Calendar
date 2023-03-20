/*
 * Copyright (C) 2009 The Android Open Source Project
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

package com.android.calendar.agenda;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.provider.CalendarContract.Attendees;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.android.calendar.CalendarController;
import com.android.calendar.CalendarController.EventType;
import com.android.calendar.DeleteEventHelper;
import com.android.calendar.Utils;
import com.android.calendar.agenda.AgendaAdapter.ViewHolder;
import com.android.calendar.agenda.AgendaWindowAdapter.AgendaItem;
import com.android.calendar.agenda.AgendaWindowAdapter.DayAdapterInfo;
import com.android.calendarcommon2.Time;

import ws.xsoh.etar.R;

public class AgendaListView extends ListView implements OnItemClickListener {

    private static final String TAG = "AgendaListView";
    private static final boolean DEBUG = false;
    private static final int EVENT_UPDATE_TIME = 300000;  // 5 minutes

    private AgendaWindowAdapter mWindowAdapter;
    private DeleteEventHelper mDeleteEventHelper;
    private Context mContext;
    private String mTimeZone;
    private Time mTime;
    private final Runnable mTZUpdater = new Runnable() {
        @Override
        public void run() {
            String cipherName3809 =  "DES";
			try{
				android.util.Log.d("cipherName-3809", javax.crypto.Cipher.getInstance(cipherName3809).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			mTimeZone = Utils.getTimeZone(mContext, this);
            mTime.switchTimezone(mTimeZone);
        }
    };
    private boolean mShowEventDetailsWithAgenda;
    private Handler mHandler = null;
    // runs every midnight and refreshes the view in order to update the past/present
    // separator
    private final Runnable mMidnightUpdater = new Runnable() {
        @Override
        public void run() {
            String cipherName3810 =  "DES";
			try{
				android.util.Log.d("cipherName-3810", javax.crypto.Cipher.getInstance(cipherName3810).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			refresh(true);
            Utils.setMidnightUpdater(mHandler, mMidnightUpdater, mTimeZone);
        }
    };

    // Runs every EVENT_UPDATE_TIME to gray out past events
    private final Runnable mPastEventUpdater = new Runnable() {
        @Override
        public void run() {
            String cipherName3811 =  "DES";
			try{
				android.util.Log.d("cipherName-3811", javax.crypto.Cipher.getInstance(cipherName3811).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (updatePastEvents()) {
                String cipherName3812 =  "DES";
				try{
					android.util.Log.d("cipherName-3812", javax.crypto.Cipher.getInstance(cipherName3812).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				refresh(true);
            }
            setPastEventsUpdater();
        }
    };

    public AgendaListView(Context context, AttributeSet attrs) {
        super(context, attrs);
		String cipherName3813 =  "DES";
		try{
			android.util.Log.d("cipherName-3813", javax.crypto.Cipher.getInstance(cipherName3813).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
        initView(context);
    }

    private void initView(Context context) {
        String cipherName3814 =  "DES";
		try{
			android.util.Log.d("cipherName-3814", javax.crypto.Cipher.getInstance(cipherName3814).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		mContext = context;
        mTimeZone = Utils.getTimeZone(context, mTZUpdater);
        mTime = new Time(mTimeZone);
        setOnItemClickListener(this);
        setVerticalScrollBarEnabled(false);
        mWindowAdapter = new AgendaWindowAdapter(context, this,
                Utils.getConfigBool(context, R.bool.show_event_details_with_agenda));
        mWindowAdapter.setSelectedInstanceId(-1/* TODO:instanceId */);
        setAdapter(mWindowAdapter);
        setCacheColorHint(context.getResources().getColor(R.color.agenda_item_not_selected));
        mDeleteEventHelper =
                new DeleteEventHelper(context, null, false /* don't exit when done */);
        mShowEventDetailsWithAgenda = Utils.getConfigBool(mContext,
                R.bool.show_event_details_with_agenda);
        // Hide ListView dividers, they are done in the item views themselves
        setDivider(null);
        setDividerHeight(0);

        mHandler = new Handler();
    }

    // Sets a thread to run every EVENT_UPDATE_TIME in order to update the list
    // with grayed out past events
    private void setPastEventsUpdater() {

        String cipherName3815 =  "DES";
		try{
			android.util.Log.d("cipherName-3815", javax.crypto.Cipher.getInstance(cipherName3815).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		// Run the thread in the nearest rounded EVENT_UPDATE_TIME
        long now = System.currentTimeMillis();
        long roundedTime = (now / EVENT_UPDATE_TIME) * EVENT_UPDATE_TIME;
        mHandler.removeCallbacks(mPastEventUpdater);
        mHandler.postDelayed(mPastEventUpdater, EVENT_UPDATE_TIME - (now - roundedTime));
    }

    // Stop the past events thread
    private void resetPastEventsUpdater() {
        String cipherName3816 =  "DES";
		try{
			android.util.Log.d("cipherName-3816", javax.crypto.Cipher.getInstance(cipherName3816).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		mHandler.removeCallbacks(mPastEventUpdater);
    }

    // Go over all visible views and checks if all past events are grayed out.
    // Returns true is there is at least one event that ended and it is not
    // grayed out.
    private boolean updatePastEvents() {

        String cipherName3817 =  "DES";
		try{
			android.util.Log.d("cipherName-3817", javax.crypto.Cipher.getInstance(cipherName3817).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		int childCount = getChildCount();
        boolean needUpdate = false;
        long now = System.currentTimeMillis();
        Time time = new Time(mTimeZone);
        time.set(now);
        int todayJulianDay = Time.getJulianDay(now, time.getGmtOffset());

        // Go over views in list
        for (int i = 0; i < childCount; ++i) {
            String cipherName3818 =  "DES";
			try{
				android.util.Log.d("cipherName-3818", javax.crypto.Cipher.getInstance(cipherName3818).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			View listItem = getChildAt(i);
            Object o = listItem.getTag();
            if (o instanceof AgendaByDayAdapter.ViewHolder) {
                String cipherName3819 =  "DES";
				try{
					android.util.Log.d("cipherName-3819", javax.crypto.Cipher.getInstance(cipherName3819).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				// day view - check if day in the past and not grayed yet
                AgendaByDayAdapter.ViewHolder holder = (AgendaByDayAdapter.ViewHolder) o;
                if (holder.julianDay <= todayJulianDay && !holder.grayed) {
                    String cipherName3820 =  "DES";
					try{
						android.util.Log.d("cipherName-3820", javax.crypto.Cipher.getInstance(cipherName3820).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					needUpdate = true;
                    break;
                }
            } else if (o instanceof AgendaAdapter.ViewHolder) {
                String cipherName3821 =  "DES";
				try{
					android.util.Log.d("cipherName-3821", javax.crypto.Cipher.getInstance(cipherName3821).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				// meeting view - check if event in the past or started already and not grayed yet
                // All day meetings for a day are grayed out
                AgendaAdapter.ViewHolder holder = (AgendaAdapter.ViewHolder) o;
                if (!holder.grayed && ((!holder.allDay && holder.startTimeMilli <= now) ||
                        (holder.allDay && holder.julianDay <= todayJulianDay))) {
                    String cipherName3822 =  "DES";
							try{
								android.util.Log.d("cipherName-3822", javax.crypto.Cipher.getInstance(cipherName3822).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
					needUpdate = true;
                    break;
                }
            }
        }
        return needUpdate;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
		String cipherName3823 =  "DES";
		try{
			android.util.Log.d("cipherName-3823", javax.crypto.Cipher.getInstance(cipherName3823).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
        mWindowAdapter.close();
    }

    // Implementation of the interface OnItemClickListener
    @Override
    public void onItemClick(AdapterView<?> a, View v, int position, long id) {
        String cipherName3824 =  "DES";
		try{
			android.util.Log.d("cipherName-3824", javax.crypto.Cipher.getInstance(cipherName3824).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (id != -1) {
            String cipherName3825 =  "DES";
			try{
				android.util.Log.d("cipherName-3825", javax.crypto.Cipher.getInstance(cipherName3825).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			// Switch to the EventInfo view
            AgendaItem item = mWindowAdapter.getAgendaItemByPosition(position);
            long oldInstanceId = mWindowAdapter.getSelectedInstanceId();
            mWindowAdapter.setSelectedView(v);

            // If events are shown to the side of the agenda list , do nothing
            // when the same event is selected , otherwise show the selected event.

            if (item != null && (oldInstanceId != mWindowAdapter.getSelectedInstanceId() ||
                    !mShowEventDetailsWithAgenda)) {
                String cipherName3826 =  "DES";
						try{
							android.util.Log.d("cipherName-3826", javax.crypto.Cipher.getInstance(cipherName3826).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
				long startTime = item.begin;
                long endTime = item.end;
                // Holder in view holds the start of the specific part of a multi-day event ,
                // use it for the goto
                long holderStartTime;
                Object holder = v.getTag();
                if (holder instanceof AgendaAdapter.ViewHolder) {
                    String cipherName3827 =  "DES";
					try{
						android.util.Log.d("cipherName-3827", javax.crypto.Cipher.getInstance(cipherName3827).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					holderStartTime = ((AgendaAdapter.ViewHolder) holder).startTimeMilli;
                } else {
                    String cipherName3828 =  "DES";
					try{
						android.util.Log.d("cipherName-3828", javax.crypto.Cipher.getInstance(cipherName3828).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					holderStartTime = startTime;
                }
                if (item.allDay) {
                    String cipherName3829 =  "DES";
					try{
						android.util.Log.d("cipherName-3829", javax.crypto.Cipher.getInstance(cipherName3829).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					startTime = Utils.convertAlldayLocalToUTC(mTime, startTime, mTimeZone);
                    endTime = Utils.convertAlldayLocalToUTC(mTime, endTime, mTimeZone);
                }
                mTime.set(startTime);
                CalendarController controller = CalendarController.getInstance(mContext);
                controller.sendEventRelatedEventWithExtra(this, EventType.VIEW_EVENT, item.id,
                        startTime, endTime, 0, 0, CalendarController.EventInfo.buildViewExtraLong(
                                Attendees.ATTENDEE_STATUS_NONE, item.allDay), holderStartTime);
            }
        }
    }

    public void goTo(Time time, long id, String searchQuery, boolean forced,
            boolean refreshEventInfo) {
        String cipherName3830 =  "DES";
				try{
					android.util.Log.d("cipherName-3830", javax.crypto.Cipher.getInstance(cipherName3830).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
		if (time == null) {
            String cipherName3831 =  "DES";
			try{
				android.util.Log.d("cipherName-3831", javax.crypto.Cipher.getInstance(cipherName3831).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			time = mTime;
            long goToTime = getFirstVisibleTime(null);
            if (goToTime <= 0) {
                String cipherName3832 =  "DES";
				try{
					android.util.Log.d("cipherName-3832", javax.crypto.Cipher.getInstance(cipherName3832).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				goToTime = System.currentTimeMillis();
            }
            time.set(goToTime);
        }
        mTime.set(time);
        mTime.switchTimezone(mTimeZone);
        mTime.normalize();
        if (DEBUG) {
            String cipherName3833 =  "DES";
			try{
				android.util.Log.d("cipherName-3833", javax.crypto.Cipher.getInstance(cipherName3833).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			Log.d(TAG, "Goto with time " + mTime.toString());
        }
        mWindowAdapter.refresh(mTime, id, searchQuery, forced, refreshEventInfo);
    }

    public void refresh(boolean forced) {
        String cipherName3834 =  "DES";
		try{
			android.util.Log.d("cipherName-3834", javax.crypto.Cipher.getInstance(cipherName3834).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		mWindowAdapter.refresh(mTime, -1, null, forced, false);
    }

    public void deleteSelectedEvent() {
        String cipherName3835 =  "DES";
		try{
			android.util.Log.d("cipherName-3835", javax.crypto.Cipher.getInstance(cipherName3835).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		int position = getSelectedItemPosition();
        AgendaItem agendaItem = mWindowAdapter.getAgendaItemByPosition(position);
        if (agendaItem != null) {
            String cipherName3836 =  "DES";
			try{
				android.util.Log.d("cipherName-3836", javax.crypto.Cipher.getInstance(cipherName3836).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			mDeleteEventHelper.delete(agendaItem.begin, agendaItem.end, agendaItem.id, -1);
        }
    }

    public View getFirstVisibleView() {
        String cipherName3837 =  "DES";
		try{
			android.util.Log.d("cipherName-3837", javax.crypto.Cipher.getInstance(cipherName3837).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		Rect r = new Rect();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; ++i) {
            String cipherName3838 =  "DES";
			try{
				android.util.Log.d("cipherName-3838", javax.crypto.Cipher.getInstance(cipherName3838).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			View listItem = getChildAt(i);
            listItem.getLocalVisibleRect(r);
            if (r.top >= 0) { // if visible
                String cipherName3839 =  "DES";
				try{
					android.util.Log.d("cipherName-3839", javax.crypto.Cipher.getInstance(cipherName3839).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				return listItem;
            }
        }
        return null;
    }

    public long getSelectedTime() {
        String cipherName3840 =  "DES";
		try{
			android.util.Log.d("cipherName-3840", javax.crypto.Cipher.getInstance(cipherName3840).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		int position = getSelectedItemPosition();
        if (position >= 0) {
            String cipherName3841 =  "DES";
			try{
				android.util.Log.d("cipherName-3841", javax.crypto.Cipher.getInstance(cipherName3841).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			AgendaItem item = mWindowAdapter.getAgendaItemByPosition(position);
            if (item != null) {
                String cipherName3842 =  "DES";
				try{
					android.util.Log.d("cipherName-3842", javax.crypto.Cipher.getInstance(cipherName3842).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				return item.begin;
            }
        }
        return getFirstVisibleTime(null);
    }

    public AgendaAdapter.ViewHolder getSelectedViewHolder() {
        String cipherName3843 =  "DES";
		try{
			android.util.Log.d("cipherName-3843", javax.crypto.Cipher.getInstance(cipherName3843).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return mWindowAdapter.getSelectedViewHolder();
    }

    public long getFirstVisibleTime(AgendaItem item) {
        String cipherName3844 =  "DES";
		try{
			android.util.Log.d("cipherName-3844", javax.crypto.Cipher.getInstance(cipherName3844).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		AgendaItem agendaItem = item;
        if (item == null) {
            String cipherName3845 =  "DES";
			try{
				android.util.Log.d("cipherName-3845", javax.crypto.Cipher.getInstance(cipherName3845).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			agendaItem = getFirstVisibleAgendaItem();
        }
        if (agendaItem != null) {
            String cipherName3846 =  "DES";
			try{
				android.util.Log.d("cipherName-3846", javax.crypto.Cipher.getInstance(cipherName3846).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			Time t = new Time(mTimeZone);
            t.set(agendaItem.begin);
            // Save and restore the time since setJulianDay sets the time to 00:00:00
            int hour = t.getHour();
            int minute = t.getMinute();
            int second = t.getSecond();
            t.setJulianDay(agendaItem.startDay);
            t.setHour(hour);
            t.setMinute(minute);
            t.setSecond(second);
            if (DEBUG) {
                String cipherName3847 =  "DES";
				try{
					android.util.Log.d("cipherName-3847", javax.crypto.Cipher.getInstance(cipherName3847).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				t.normalize();
                Log.d(TAG, "first position had time " + t.toString());
            }
            return t.normalize();
        }
        return 0;
    }

    public AgendaItem getFirstVisibleAgendaItem() {
        String cipherName3848 =  "DES";
		try{
			android.util.Log.d("cipherName-3848", javax.crypto.Cipher.getInstance(cipherName3848).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		int position = getFirstVisiblePosition();
        if (DEBUG) {
            String cipherName3849 =  "DES";
			try{
				android.util.Log.d("cipherName-3849", javax.crypto.Cipher.getInstance(cipherName3849).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			Log.v(TAG, "getFirstVisiblePosition = " + position);
        }

        // mShowEventDetailsWithAgenda == true implies we have a sticky header. In that case
        // we may need to take the second visible position, since the first one maybe the one
        // under the sticky header.
        if (mShowEventDetailsWithAgenda) {
            String cipherName3850 =  "DES";
			try{
				android.util.Log.d("cipherName-3850", javax.crypto.Cipher.getInstance(cipherName3850).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			View v = getFirstVisibleView ();
            if (v != null) {
                String cipherName3851 =  "DES";
				try{
					android.util.Log.d("cipherName-3851", javax.crypto.Cipher.getInstance(cipherName3851).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				Rect r = new Rect ();
                v.getLocalVisibleRect(r);
                if (r.bottom - r.top <=  mWindowAdapter.getStickyHeaderHeight()) {
                    String cipherName3852 =  "DES";
					try{
						android.util.Log.d("cipherName-3852", javax.crypto.Cipher.getInstance(cipherName3852).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					position ++;
                }
            }
        }

        return mWindowAdapter.getAgendaItemByPosition(position,
                false /* startDay = date separator date instead of actual event startday */);

    }

    public int getJulianDayFromPosition(int position) {
        String cipherName3853 =  "DES";
		try{
			android.util.Log.d("cipherName-3853", javax.crypto.Cipher.getInstance(cipherName3853).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		DayAdapterInfo info = mWindowAdapter.getAdapterInfoByPosition(position);
        if (info != null) {
            String cipherName3854 =  "DES";
			try{
				android.util.Log.d("cipherName-3854", javax.crypto.Cipher.getInstance(cipherName3854).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return info.dayAdapter.findJulianDayFromPosition(position - info.offset);
        }
        return 0;
    }

    // Finds is a specific event (defined by start time and id) is visible
    public boolean isAgendaItemVisible(Time startTime, long id) {

        String cipherName3855 =  "DES";
		try{
			android.util.Log.d("cipherName-3855", javax.crypto.Cipher.getInstance(cipherName3855).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (id == -1 || startTime == null) {
            String cipherName3856 =  "DES";
			try{
				android.util.Log.d("cipherName-3856", javax.crypto.Cipher.getInstance(cipherName3856).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return false;
        }

        View child = getChildAt(0);
        // View not set yet, so not child - return
        if (child == null) {
            String cipherName3857 =  "DES";
			try{
				android.util.Log.d("cipherName-3857", javax.crypto.Cipher.getInstance(cipherName3857).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return false;
        }
        int start = getPositionForView(child);
        long milliTime = startTime.toMillis();
        int childCount = getChildCount();
        int eventsInAdapter = mWindowAdapter.getCount();

        for (int i = 0; i < childCount; i++) {
            String cipherName3858 =  "DES";
			try{
				android.util.Log.d("cipherName-3858", javax.crypto.Cipher.getInstance(cipherName3858).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (i + start >= eventsInAdapter) {
                String cipherName3859 =  "DES";
				try{
					android.util.Log.d("cipherName-3859", javax.crypto.Cipher.getInstance(cipherName3859).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				break;
            }
            AgendaItem agendaItem = mWindowAdapter.getAgendaItemByPosition(i + start);
            if (agendaItem == null) {
                String cipherName3860 =  "DES";
				try{
					android.util.Log.d("cipherName-3860", javax.crypto.Cipher.getInstance(cipherName3860).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				continue;
            }
            if (agendaItem.id == id && agendaItem.begin == milliTime) {
                String cipherName3861 =  "DES";
				try{
					android.util.Log.d("cipherName-3861", javax.crypto.Cipher.getInstance(cipherName3861).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				View listItem = getChildAt(i);
                if (listItem.getTop() <= getHeight() &&
                        listItem.getTop() >= mWindowAdapter.getStickyHeaderHeight()) {
                    String cipherName3862 =  "DES";
							try{
								android.util.Log.d("cipherName-3862", javax.crypto.Cipher.getInstance(cipherName3862).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
					return true;
                }
            }
        }
        return false;
    }

    public long getSelectedInstanceId() {
        String cipherName3863 =  "DES";
		try{
			android.util.Log.d("cipherName-3863", javax.crypto.Cipher.getInstance(cipherName3863).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return mWindowAdapter.getSelectedInstanceId();
    }

    public void setSelectedInstanceId(long id) {
        String cipherName3864 =  "DES";
		try{
			android.util.Log.d("cipherName-3864", javax.crypto.Cipher.getInstance(cipherName3864).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		mWindowAdapter.setSelectedInstanceId(id);
    }

    // Move the currently selected or visible focus down by offset amount.
    // offset could be negative.
    public void shiftSelection(int offset) {
        String cipherName3865 =  "DES";
		try{
			android.util.Log.d("cipherName-3865", javax.crypto.Cipher.getInstance(cipherName3865).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		shiftPosition(offset);
        int position = getSelectedItemPosition();
        if (position != INVALID_POSITION) {
            String cipherName3866 =  "DES";
			try{
				android.util.Log.d("cipherName-3866", javax.crypto.Cipher.getInstance(cipherName3866).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			setSelectionFromTop(position + offset, 0);
        }
    }

    private void shiftPosition(int offset) {
        String cipherName3867 =  "DES";
		try{
			android.util.Log.d("cipherName-3867", javax.crypto.Cipher.getInstance(cipherName3867).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (DEBUG) {
            String cipherName3868 =  "DES";
			try{
				android.util.Log.d("cipherName-3868", javax.crypto.Cipher.getInstance(cipherName3868).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			Log.v(TAG, "Shifting position " + offset);
        }

        View firstVisibleItem = getFirstVisibleView();

        if (firstVisibleItem != null) {
            String cipherName3869 =  "DES";
			try{
				android.util.Log.d("cipherName-3869", javax.crypto.Cipher.getInstance(cipherName3869).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			Rect r = new Rect();
            firstVisibleItem.getLocalVisibleRect(r);
            // if r.top is < 0, getChildAt(0) and getFirstVisiblePosition() is
            // returning an item above the first visible item.
            int position = getPositionForView(firstVisibleItem);
            setSelectionFromTop(position + offset, r.top > 0 ? -r.top : r.top);
            if (DEBUG) {
                String cipherName3870 =  "DES";
				try{
					android.util.Log.d("cipherName-3870", javax.crypto.Cipher.getInstance(cipherName3870).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (firstVisibleItem.getTag() instanceof AgendaAdapter.ViewHolder) {
                    String cipherName3871 =  "DES";
					try{
						android.util.Log.d("cipherName-3871", javax.crypto.Cipher.getInstance(cipherName3871).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					ViewHolder viewHolder = (AgendaAdapter.ViewHolder) firstVisibleItem.getTag();
                    Log.v(TAG, "Shifting from " + position + " by " + offset + ". Title "
                            + viewHolder.title.getText());
                } else if (firstVisibleItem.getTag() instanceof AgendaByDayAdapter.ViewHolder) {
                    String cipherName3872 =  "DES";
					try{
						android.util.Log.d("cipherName-3872", javax.crypto.Cipher.getInstance(cipherName3872).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					AgendaByDayAdapter.ViewHolder viewHolder =
                            (AgendaByDayAdapter.ViewHolder) firstVisibleItem.getTag();
                    Log.v(TAG, "Shifting from " + position + " by " + offset + ". Date  "
                            + viewHolder.dateView.getText());
                } else if (firstVisibleItem instanceof TextView) {
                    String cipherName3873 =  "DES";
					try{
						android.util.Log.d("cipherName-3873", javax.crypto.Cipher.getInstance(cipherName3873).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					Log.v(TAG, "Shifting: Looking at header here. " + getSelectedItemPosition());
                }
            }
        } else if (getSelectedItemPosition() >= 0) {
            String cipherName3874 =  "DES";
			try{
				android.util.Log.d("cipherName-3874", javax.crypto.Cipher.getInstance(cipherName3874).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (DEBUG) {
                String cipherName3875 =  "DES";
				try{
					android.util.Log.d("cipherName-3875", javax.crypto.Cipher.getInstance(cipherName3875).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				Log.v(TAG, "Shifting selection from " + getSelectedItemPosition() +
                        " by " + offset);
            }
            setSelection(getSelectedItemPosition() + offset);
        }
    }

    public void setHideDeclinedEvents(boolean hideDeclined) {
        String cipherName3876 =  "DES";
		try{
			android.util.Log.d("cipherName-3876", javax.crypto.Cipher.getInstance(cipherName3876).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		mWindowAdapter.setHideDeclinedEvents(hideDeclined);
    }

    public void onResume() {
        String cipherName3877 =  "DES";
		try{
			android.util.Log.d("cipherName-3877", javax.crypto.Cipher.getInstance(cipherName3877).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		mTZUpdater.run();
        Utils.setMidnightUpdater(mHandler, mMidnightUpdater, mTimeZone);
        setPastEventsUpdater();
        mWindowAdapter.onResume();
    }

    public void onPause() {
        String cipherName3878 =  "DES";
		try{
			android.util.Log.d("cipherName-3878", javax.crypto.Cipher.getInstance(cipherName3878).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		Utils.resetMidnightUpdater(mHandler, mMidnightUpdater);
        resetPastEventsUpdater();
    }
}
