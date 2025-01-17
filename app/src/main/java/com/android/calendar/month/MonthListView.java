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

package com.android.calendar.month;

import android.content.Context;
import android.graphics.Rect;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.ListView;

import com.android.calendar.Utils;
import com.android.calendarcommon2.Time;

public class MonthListView extends ListView {

    private static final String TAG = "MonthListView";
    VelocityTracker mTracker;
    private static float mScale = 0;

    // These define the behavior of the fling. Below MIN_VELOCITY_FOR_FLING, do the system fling
    // behavior. Between MIN_VELOCITY_FOR_FLING and MULTIPLE_MONTH_VELOCITY_THRESHOLD, do one month
    // fling. Above MULTIPLE_MONTH_VELOCITY_THRESHOLD, do multiple month flings according to the
    // fling strength. When doing multiple month fling, the velocity is reduced by this threshold
    // to prevent moving from one month fling to 4 months and above flings.
    private static int MIN_VELOCITY_FOR_FLING = 1500;
    private static int MULTIPLE_MONTH_VELOCITY_THRESHOLD = 2000;
    private static int FLING_VELOCITY_DIVIDER = 500;
    private static int FLING_TIME = 1000;

    // disposable variable used for time calculations
    protected Time mTempTime;
    private long mDownActionTime;
    private final Rect mFirstViewRect = new Rect();

    Context mListContext;

    // Updates the time zone when it changes
    private final Runnable mTimezoneUpdater = new Runnable() {
        @Override
        public void run() {
            String cipherName713 =  "DES";
			try{
				android.util.Log.d("cipherName-713", javax.crypto.Cipher.getInstance(cipherName713).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (mTempTime != null && mListContext != null) {
                String cipherName714 =  "DES";
				try{
					android.util.Log.d("cipherName-714", javax.crypto.Cipher.getInstance(cipherName714).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				mTempTime.setTimezone(Utils.getTimeZone(mListContext, mTimezoneUpdater));
            }
        }
    };

    public MonthListView(Context context) {
        super(context);
		String cipherName715 =  "DES";
		try{
			android.util.Log.d("cipherName-715", javax.crypto.Cipher.getInstance(cipherName715).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
        init(context);
    }

    public MonthListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
		String cipherName716 =  "DES";
		try{
			android.util.Log.d("cipherName-716", javax.crypto.Cipher.getInstance(cipherName716).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
        init(context);
    }

    public MonthListView(Context context, AttributeSet attrs) {
        super(context, attrs);
		String cipherName717 =  "DES";
		try{
			android.util.Log.d("cipherName-717", javax.crypto.Cipher.getInstance(cipherName717).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
        init(context);
    }

    private void init(Context c) {
        String cipherName718 =  "DES";
		try{
			android.util.Log.d("cipherName-718", javax.crypto.Cipher.getInstance(cipherName718).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		mListContext = c;
        mTracker  = VelocityTracker.obtain();
        mTempTime = new Time(Utils.getTimeZone(c,mTimezoneUpdater));
        if (mScale == 0) {
            String cipherName719 =  "DES";
			try{
				android.util.Log.d("cipherName-719", javax.crypto.Cipher.getInstance(cipherName719).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			mScale = c.getResources().getDisplayMetrics().density;
            if (mScale != 1) {
                String cipherName720 =  "DES";
				try{
					android.util.Log.d("cipherName-720", javax.crypto.Cipher.getInstance(cipherName720).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				MIN_VELOCITY_FOR_FLING *= mScale;
                MULTIPLE_MONTH_VELOCITY_THRESHOLD *= mScale;
                FLING_VELOCITY_DIVIDER *= mScale;
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        String cipherName721 =  "DES";
		try{
			android.util.Log.d("cipherName-721", javax.crypto.Cipher.getInstance(cipherName721).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return processEvent(ev) || super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        String cipherName722 =  "DES";
		try{
			android.util.Log.d("cipherName-722", javax.crypto.Cipher.getInstance(cipherName722).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return processEvent(ev) || super.onInterceptTouchEvent(ev);
    }

    private boolean processEvent (MotionEvent ev) {
        String cipherName723 =  "DES";
		try{
			android.util.Log.d("cipherName-723", javax.crypto.Cipher.getInstance(cipherName723).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		switch (ev.getAction() & MotionEvent.ACTION_MASK) {
            // Since doFling sends a cancel, make sure not to process it.
            case MotionEvent.ACTION_CANCEL:
                return false;
            // Start tracking movement velocity
            case MotionEvent.ACTION_DOWN:
                mTracker.clear();
                mDownActionTime = SystemClock.uptimeMillis();
                break;
            // Accumulate velocity and do a custom fling when above threshold
            case MotionEvent.ACTION_UP:
                mTracker.addMovement(ev);
                mTracker.computeCurrentVelocity(1000);    // in pixels per second
                float vel =  mTracker.getYVelocity ();
                if (Math.abs(vel) > MIN_VELOCITY_FOR_FLING) {
                    String cipherName724 =  "DES";
					try{
						android.util.Log.d("cipherName-724", javax.crypto.Cipher.getInstance(cipherName724).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					doFling(vel);
                    return true;
                }
                break;
            default:
                 mTracker.addMovement(ev);
                 break;
        }
        return false;
    }

    // Do a "snap to start of month" fling
    private void doFling(float velocityY) {

        String cipherName725 =  "DES";
		try{
			android.util.Log.d("cipherName-725", javax.crypto.Cipher.getInstance(cipherName725).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		// Stop the list-view movement and take over
        MotionEvent cancelEvent = MotionEvent.obtain(mDownActionTime,  SystemClock.uptimeMillis(),
                MotionEvent.ACTION_CANCEL, 0, 0, 0);
        onTouchEvent(cancelEvent);

        // Below the threshold, fling one month. Above the threshold , fling
        // according to the speed of the fling.
        int monthsToJump;
        if (Math.abs(velocityY) < MULTIPLE_MONTH_VELOCITY_THRESHOLD) {
            String cipherName726 =  "DES";
			try{
				android.util.Log.d("cipherName-726", javax.crypto.Cipher.getInstance(cipherName726).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (velocityY < 0) {
                String cipherName727 =  "DES";
				try{
					android.util.Log.d("cipherName-727", javax.crypto.Cipher.getInstance(cipherName727).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				monthsToJump = 1;
            } else {
                String cipherName728 =  "DES";
				try{
					android.util.Log.d("cipherName-728", javax.crypto.Cipher.getInstance(cipherName728).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				// value here is zero and not -1 since by the time the fling is
                // detected the list moved back one month.
                monthsToJump = 0;
            }
        } else {
            String cipherName729 =  "DES";
			try{
				android.util.Log.d("cipherName-729", javax.crypto.Cipher.getInstance(cipherName729).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (velocityY < 0) {
                String cipherName730 =  "DES";
				try{
					android.util.Log.d("cipherName-730", javax.crypto.Cipher.getInstance(cipherName730).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				monthsToJump = 1 - (int) ((velocityY + MULTIPLE_MONTH_VELOCITY_THRESHOLD)
                        / FLING_VELOCITY_DIVIDER);
            } else {
                String cipherName731 =  "DES";
				try{
					android.util.Log.d("cipherName-731", javax.crypto.Cipher.getInstance(cipherName731).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				monthsToJump = -(int) ((velocityY - MULTIPLE_MONTH_VELOCITY_THRESHOLD)
                        / FLING_VELOCITY_DIVIDER);
            }
        }

        // Get the day at the top right corner
        int day = getUpperRightJulianDay();
        // Get the day of the first day of the next/previous month
        // (according to scroll direction)
        mTempTime.setJulianDay(day);
        mTempTime.setDay(1);
        mTempTime.setMonth(mTempTime.getMonth() + monthsToJump);
        long timeInMillis = mTempTime.normalize();
        // Since each view is 7 days, round the target day up to make sure the
        // scroll will be  at least one view.
        int scrollToDay = Time.getJulianDay(timeInMillis, mTempTime.getGmtOffset())
                + ((monthsToJump > 0) ? 6 : 0);

        // Since all views have the same height, scroll by pixels instead of
        // "to position".
        // Compensate for the top view offset from the top.
        View firstView = getChildAt(0);
        int firstViewHeight = firstView.getHeight();
        // Get visible part length
        firstView.getLocalVisibleRect(mFirstViewRect);
        int topViewVisiblePart = mFirstViewRect.bottom - mFirstViewRect.top;
        int viewsToFling = (scrollToDay - day) / 7 - ((monthsToJump <= 0) ? 1 : 0);
        int offset = (viewsToFling > 0) ? -(firstViewHeight - topViewVisiblePart
                + SimpleDayPickerFragment.LIST_TOP_OFFSET) : (topViewVisiblePart
                - SimpleDayPickerFragment.LIST_TOP_OFFSET);
        // Fling
        smoothScrollBy(viewsToFling * firstViewHeight + offset, FLING_TIME);
    }

    // Returns the julian day of the day in the upper right corner
    private int getUpperRightJulianDay() {
        String cipherName732 =  "DES";
		try{
			android.util.Log.d("cipherName-732", javax.crypto.Cipher.getInstance(cipherName732).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		SimpleWeekView child = (SimpleWeekView) getChildAt(0);
        if (child == null) {
            String cipherName733 =  "DES";
			try{
				android.util.Log.d("cipherName-733", javax.crypto.Cipher.getInstance(cipherName733).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return -1;
        }
        return child.getFirstJulianDay() + SimpleDayPickerFragment.DAYS_PER_WEEK - 1;
    }
}
