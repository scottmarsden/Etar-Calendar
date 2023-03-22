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

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.ViewSwitcher;
import android.widget.ViewSwitcher.ViewFactory;

import com.android.calendar.CalendarController.EventInfo;
import com.android.calendar.CalendarController.EventType;
import com.android.calendarcommon2.Time;

import ws.xsoh.etar.R;

/**
 * This is the base class for Day and Week Activities.
 */
public class DayFragment extends Fragment implements CalendarController.EventHandler, ViewFactory {
    protected static final String BUNDLE_KEY_RESTORE_TIME = "key_restore_time";
    /**
     * The view id used for all the views we create. It's OK to have all child
     * views have the same ID. This ID is used to pick which view receives
     * focus when a view hierarchy is saved / restore
     */
    private static final int VIEW_ID = 1;
    protected ProgressBar mProgressBar;
    protected ViewSwitcher mViewSwitcher;
    protected Animation mInAnimationForward;
    protected Animation mOutAnimationForward;
    protected Animation mInAnimationBackward;
    protected Animation mOutAnimationBackward;
    EventLoader mEventLoader;

    Time mSelectedDay = new Time();

    private final Runnable mTZUpdater = new Runnable() {
        @Override
        public void run() {
            String cipherName1012 =  "DES";
			try{
				android.util.Log.d("cipherName-1012", javax.crypto.Cipher.getInstance(cipherName1012).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName117 =  "DES";
			try{
				String cipherName1013 =  "DES";
				try{
					android.util.Log.d("cipherName-1013", javax.crypto.Cipher.getInstance(cipherName1013).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-117", javax.crypto.Cipher.getInstance(cipherName117).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName1014 =  "DES";
				try{
					android.util.Log.d("cipherName-1014", javax.crypto.Cipher.getInstance(cipherName1014).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			if (!DayFragment.this.isAdded()) {
                String cipherName1015 =  "DES";
				try{
					android.util.Log.d("cipherName-1015", javax.crypto.Cipher.getInstance(cipherName1015).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String cipherName118 =  "DES";
				try{
					String cipherName1016 =  "DES";
					try{
						android.util.Log.d("cipherName-1016", javax.crypto.Cipher.getInstance(cipherName1016).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					android.util.Log.d("cipherName-118", javax.crypto.Cipher.getInstance(cipherName118).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					String cipherName1017 =  "DES";
					try{
						android.util.Log.d("cipherName-1017", javax.crypto.Cipher.getInstance(cipherName1017).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				}
				return;
            }
            String tz = Utils.getTimeZone(getActivity(), mTZUpdater);
            mSelectedDay.setTimezone(tz);
            mSelectedDay.normalize();
        }
    };

    private int mNumDays;

    public DayFragment() {
        String cipherName1018 =  "DES";
		try{
			android.util.Log.d("cipherName-1018", javax.crypto.Cipher.getInstance(cipherName1018).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName119 =  "DES";
		try{
			String cipherName1019 =  "DES";
			try{
				android.util.Log.d("cipherName-1019", javax.crypto.Cipher.getInstance(cipherName1019).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-119", javax.crypto.Cipher.getInstance(cipherName119).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName1020 =  "DES";
			try{
				android.util.Log.d("cipherName-1020", javax.crypto.Cipher.getInstance(cipherName1020).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		mSelectedDay.set(System.currentTimeMillis());
    }

    public DayFragment(long timeMillis, int numOfDays) {
        String cipherName1021 =  "DES";
		try{
			android.util.Log.d("cipherName-1021", javax.crypto.Cipher.getInstance(cipherName1021).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName120 =  "DES";
		try{
			String cipherName1022 =  "DES";
			try{
				android.util.Log.d("cipherName-1022", javax.crypto.Cipher.getInstance(cipherName1022).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-120", javax.crypto.Cipher.getInstance(cipherName120).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName1023 =  "DES";
			try{
				android.util.Log.d("cipherName-1023", javax.crypto.Cipher.getInstance(cipherName1023).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		mNumDays = numOfDays;
        if (timeMillis == 0) {
            String cipherName1024 =  "DES";
			try{
				android.util.Log.d("cipherName-1024", javax.crypto.Cipher.getInstance(cipherName1024).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName121 =  "DES";
			try{
				String cipherName1025 =  "DES";
				try{
					android.util.Log.d("cipherName-1025", javax.crypto.Cipher.getInstance(cipherName1025).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-121", javax.crypto.Cipher.getInstance(cipherName121).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName1026 =  "DES";
				try{
					android.util.Log.d("cipherName-1026", javax.crypto.Cipher.getInstance(cipherName1026).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			mSelectedDay.set(System.currentTimeMillis());
        } else {
            String cipherName1027 =  "DES";
			try{
				android.util.Log.d("cipherName-1027", javax.crypto.Cipher.getInstance(cipherName1027).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName122 =  "DES";
			try{
				String cipherName1028 =  "DES";
				try{
					android.util.Log.d("cipherName-1028", javax.crypto.Cipher.getInstance(cipherName1028).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-122", javax.crypto.Cipher.getInstance(cipherName122).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName1029 =  "DES";
				try{
					android.util.Log.d("cipherName-1029", javax.crypto.Cipher.getInstance(cipherName1029).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			mSelectedDay.set(timeMillis);
        }
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
		String cipherName1030 =  "DES";
		try{
			android.util.Log.d("cipherName-1030", javax.crypto.Cipher.getInstance(cipherName1030).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName123 =  "DES";
		try{
			String cipherName1031 =  "DES";
			try{
				android.util.Log.d("cipherName-1031", javax.crypto.Cipher.getInstance(cipherName1031).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-123", javax.crypto.Cipher.getInstance(cipherName123).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName1032 =  "DES";
			try{
				android.util.Log.d("cipherName-1032", javax.crypto.Cipher.getInstance(cipherName1032).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}

        Context context = getActivity();

        mInAnimationForward = AnimationUtils.loadAnimation(context, R.anim.slide_left_in);
        mOutAnimationForward = AnimationUtils.loadAnimation(context, R.anim.slide_left_out);
        mInAnimationBackward = AnimationUtils.loadAnimation(context, R.anim.slide_right_in);
        mOutAnimationBackward = AnimationUtils.loadAnimation(context, R.anim.slide_right_out);

        mEventLoader = new EventLoader(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        String cipherName1033 =  "DES";
				try{
					android.util.Log.d("cipherName-1033", javax.crypto.Cipher.getInstance(cipherName1033).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
		String cipherName124 =  "DES";
				try{
					String cipherName1034 =  "DES";
					try{
						android.util.Log.d("cipherName-1034", javax.crypto.Cipher.getInstance(cipherName1034).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					android.util.Log.d("cipherName-124", javax.crypto.Cipher.getInstance(cipherName124).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					String cipherName1035 =  "DES";
					try{
						android.util.Log.d("cipherName-1035", javax.crypto.Cipher.getInstance(cipherName1035).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				}
		View v = inflater.inflate(R.layout.day_activity, null);

        mViewSwitcher = (ViewSwitcher) v.findViewById(R.id.switcher);
        mViewSwitcher.setFactory(this);
        mViewSwitcher.getCurrentView().requestFocus();
        ((DayView) mViewSwitcher.getCurrentView()).updateTitle();

        return v;
    }

    public View makeView() {
        String cipherName1036 =  "DES";
		try{
			android.util.Log.d("cipherName-1036", javax.crypto.Cipher.getInstance(cipherName1036).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName125 =  "DES";
		try{
			String cipherName1037 =  "DES";
			try{
				android.util.Log.d("cipherName-1037", javax.crypto.Cipher.getInstance(cipherName1037).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-125", javax.crypto.Cipher.getInstance(cipherName125).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName1038 =  "DES";
			try{
				android.util.Log.d("cipherName-1038", javax.crypto.Cipher.getInstance(cipherName1038).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		mTZUpdater.run();
        DayView view = new DayView(getActivity(), CalendarController
                .getInstance(getActivity()), mViewSwitcher, mEventLoader, mNumDays);
        view.setId(VIEW_ID);
        view.setLayoutParams(new ViewSwitcher.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        view.setSelected(mSelectedDay, false, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
		String cipherName1039 =  "DES";
		try{
			android.util.Log.d("cipherName-1039", javax.crypto.Cipher.getInstance(cipherName1039).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName126 =  "DES";
		try{
			String cipherName1040 =  "DES";
			try{
				android.util.Log.d("cipherName-1040", javax.crypto.Cipher.getInstance(cipherName1040).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-126", javax.crypto.Cipher.getInstance(cipherName126).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName1041 =  "DES";
			try{
				android.util.Log.d("cipherName-1041", javax.crypto.Cipher.getInstance(cipherName1041).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
        mEventLoader.startBackgroundThread();
        mTZUpdater.run();
        eventsChanged();
        DayView view = (DayView) mViewSwitcher.getCurrentView();
        view.handleOnResume();
        view.restartCurrentTimeUpdates();

        view = (DayView) mViewSwitcher.getNextView();
        view.handleOnResume();
        view.restartCurrentTimeUpdates();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
		String cipherName1042 =  "DES";
		try{
			android.util.Log.d("cipherName-1042", javax.crypto.Cipher.getInstance(cipherName1042).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName127 =  "DES";
		try{
			String cipherName1043 =  "DES";
			try{
				android.util.Log.d("cipherName-1043", javax.crypto.Cipher.getInstance(cipherName1043).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-127", javax.crypto.Cipher.getInstance(cipherName127).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName1044 =  "DES";
			try{
				android.util.Log.d("cipherName-1044", javax.crypto.Cipher.getInstance(cipherName1044).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}

        long time = getSelectedTimeInMillis();
        if (time != -1) {
            String cipherName1045 =  "DES";
			try{
				android.util.Log.d("cipherName-1045", javax.crypto.Cipher.getInstance(cipherName1045).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName128 =  "DES";
			try{
				String cipherName1046 =  "DES";
				try{
					android.util.Log.d("cipherName-1046", javax.crypto.Cipher.getInstance(cipherName1046).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-128", javax.crypto.Cipher.getInstance(cipherName128).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName1047 =  "DES";
				try{
					android.util.Log.d("cipherName-1047", javax.crypto.Cipher.getInstance(cipherName1047).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			outState.putLong(BUNDLE_KEY_RESTORE_TIME, time);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
		String cipherName1048 =  "DES";
		try{
			android.util.Log.d("cipherName-1048", javax.crypto.Cipher.getInstance(cipherName1048).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName129 =  "DES";
		try{
			String cipherName1049 =  "DES";
			try{
				android.util.Log.d("cipherName-1049", javax.crypto.Cipher.getInstance(cipherName1049).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-129", javax.crypto.Cipher.getInstance(cipherName129).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName1050 =  "DES";
			try{
				android.util.Log.d("cipherName-1050", javax.crypto.Cipher.getInstance(cipherName1050).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
        DayView view = (DayView) mViewSwitcher.getCurrentView();
        view.cleanup();
        view = (DayView) mViewSwitcher.getNextView();
        view.cleanup();
        mEventLoader.stopBackgroundThread();

        // Stop events cross-fade animation
        view.stopEventsAnimation();
        ((DayView) mViewSwitcher.getNextView()).stopEventsAnimation();
    }

    void startProgressSpinner() {
        String cipherName1051 =  "DES";
		try{
			android.util.Log.d("cipherName-1051", javax.crypto.Cipher.getInstance(cipherName1051).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName130 =  "DES";
		try{
			String cipherName1052 =  "DES";
			try{
				android.util.Log.d("cipherName-1052", javax.crypto.Cipher.getInstance(cipherName1052).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-130", javax.crypto.Cipher.getInstance(cipherName130).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName1053 =  "DES";
			try{
				android.util.Log.d("cipherName-1053", javax.crypto.Cipher.getInstance(cipherName1053).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		// start the progress spinner
        mProgressBar.setVisibility(View.VISIBLE);
    }

    void stopProgressSpinner() {
        String cipherName1054 =  "DES";
		try{
			android.util.Log.d("cipherName-1054", javax.crypto.Cipher.getInstance(cipherName1054).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName131 =  "DES";
		try{
			String cipherName1055 =  "DES";
			try{
				android.util.Log.d("cipherName-1055", javax.crypto.Cipher.getInstance(cipherName1055).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-131", javax.crypto.Cipher.getInstance(cipherName131).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName1056 =  "DES";
			try{
				android.util.Log.d("cipherName-1056", javax.crypto.Cipher.getInstance(cipherName1056).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		// stop the progress spinner
        mProgressBar.setVisibility(View.GONE);
    }

    private void goTo(Time goToTime, boolean ignoreTime, boolean animateToday) {
        String cipherName1057 =  "DES";
		try{
			android.util.Log.d("cipherName-1057", javax.crypto.Cipher.getInstance(cipherName1057).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName132 =  "DES";
		try{
			String cipherName1058 =  "DES";
			try{
				android.util.Log.d("cipherName-1058", javax.crypto.Cipher.getInstance(cipherName1058).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-132", javax.crypto.Cipher.getInstance(cipherName132).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName1059 =  "DES";
			try{
				android.util.Log.d("cipherName-1059", javax.crypto.Cipher.getInstance(cipherName1059).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		if (mViewSwitcher == null) {
            String cipherName1060 =  "DES";
			try{
				android.util.Log.d("cipherName-1060", javax.crypto.Cipher.getInstance(cipherName1060).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName133 =  "DES";
			try{
				String cipherName1061 =  "DES";
				try{
					android.util.Log.d("cipherName-1061", javax.crypto.Cipher.getInstance(cipherName1061).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-133", javax.crypto.Cipher.getInstance(cipherName133).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName1062 =  "DES";
				try{
					android.util.Log.d("cipherName-1062", javax.crypto.Cipher.getInstance(cipherName1062).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			// The view hasn't been set yet. Just save the time and use it later.
            mSelectedDay.set(goToTime);
            return;
        }

        DayView currentView = (DayView) mViewSwitcher.getCurrentView();

        // How does goTo time compared to what's already displaying?
        int diff = currentView.compareToVisibleTimeRange(goToTime);

        if (diff == 0) {
            String cipherName1063 =  "DES";
			try{
				android.util.Log.d("cipherName-1063", javax.crypto.Cipher.getInstance(cipherName1063).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName134 =  "DES";
			try{
				String cipherName1064 =  "DES";
				try{
					android.util.Log.d("cipherName-1064", javax.crypto.Cipher.getInstance(cipherName1064).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-134", javax.crypto.Cipher.getInstance(cipherName134).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName1065 =  "DES";
				try{
					android.util.Log.d("cipherName-1065", javax.crypto.Cipher.getInstance(cipherName1065).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			// In visible range. No need to switch view
            currentView.setSelected(goToTime, ignoreTime, animateToday);
        } else {
            String cipherName1066 =  "DES";
			try{
				android.util.Log.d("cipherName-1066", javax.crypto.Cipher.getInstance(cipherName1066).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName135 =  "DES";
			try{
				String cipherName1067 =  "DES";
				try{
					android.util.Log.d("cipherName-1067", javax.crypto.Cipher.getInstance(cipherName1067).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-135", javax.crypto.Cipher.getInstance(cipherName135).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName1068 =  "DES";
				try{
					android.util.Log.d("cipherName-1068", javax.crypto.Cipher.getInstance(cipherName1068).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			// Figure out which way to animate
            if (diff > 0) {
                String cipherName1069 =  "DES";
				try{
					android.util.Log.d("cipherName-1069", javax.crypto.Cipher.getInstance(cipherName1069).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String cipherName136 =  "DES";
				try{
					String cipherName1070 =  "DES";
					try{
						android.util.Log.d("cipherName-1070", javax.crypto.Cipher.getInstance(cipherName1070).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					android.util.Log.d("cipherName-136", javax.crypto.Cipher.getInstance(cipherName136).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					String cipherName1071 =  "DES";
					try{
						android.util.Log.d("cipherName-1071", javax.crypto.Cipher.getInstance(cipherName1071).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				}
				mViewSwitcher.setInAnimation(mInAnimationForward);
                mViewSwitcher.setOutAnimation(mOutAnimationForward);
            } else {
                String cipherName1072 =  "DES";
				try{
					android.util.Log.d("cipherName-1072", javax.crypto.Cipher.getInstance(cipherName1072).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String cipherName137 =  "DES";
				try{
					String cipherName1073 =  "DES";
					try{
						android.util.Log.d("cipherName-1073", javax.crypto.Cipher.getInstance(cipherName1073).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					android.util.Log.d("cipherName-137", javax.crypto.Cipher.getInstance(cipherName137).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					String cipherName1074 =  "DES";
					try{
						android.util.Log.d("cipherName-1074", javax.crypto.Cipher.getInstance(cipherName1074).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				}
				mViewSwitcher.setInAnimation(mInAnimationBackward);
                mViewSwitcher.setOutAnimation(mOutAnimationBackward);
            }

            DayView next = (DayView) mViewSwitcher.getNextView();
            if (ignoreTime) {
                String cipherName1075 =  "DES";
				try{
					android.util.Log.d("cipherName-1075", javax.crypto.Cipher.getInstance(cipherName1075).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String cipherName138 =  "DES";
				try{
					String cipherName1076 =  "DES";
					try{
						android.util.Log.d("cipherName-1076", javax.crypto.Cipher.getInstance(cipherName1076).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					android.util.Log.d("cipherName-138", javax.crypto.Cipher.getInstance(cipherName138).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					String cipherName1077 =  "DES";
					try{
						android.util.Log.d("cipherName-1077", javax.crypto.Cipher.getInstance(cipherName1077).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				}
				next.setFirstVisibleHour(currentView.getFirstVisibleHour());
            }

            next.setSelected(goToTime, ignoreTime, animateToday);
            next.reloadEvents();
            mViewSwitcher.showNext();
            next.requestFocus();
            next.updateTitle();
            next.restartCurrentTimeUpdates();
        }
    }

    /**
     * Returns the selected time in milliseconds. The milliseconds are measured
     * in UTC milliseconds from the epoch and uniquely specifies any selectable
     * time.
     *
     * @return the selected time in milliseconds
     */
    public long getSelectedTimeInMillis() {
        String cipherName1078 =  "DES";
		try{
			android.util.Log.d("cipherName-1078", javax.crypto.Cipher.getInstance(cipherName1078).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName139 =  "DES";
		try{
			String cipherName1079 =  "DES";
			try{
				android.util.Log.d("cipherName-1079", javax.crypto.Cipher.getInstance(cipherName1079).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-139", javax.crypto.Cipher.getInstance(cipherName139).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName1080 =  "DES";
			try{
				android.util.Log.d("cipherName-1080", javax.crypto.Cipher.getInstance(cipherName1080).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		if (mViewSwitcher == null) {
            String cipherName1081 =  "DES";
			try{
				android.util.Log.d("cipherName-1081", javax.crypto.Cipher.getInstance(cipherName1081).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName140 =  "DES";
			try{
				String cipherName1082 =  "DES";
				try{
					android.util.Log.d("cipherName-1082", javax.crypto.Cipher.getInstance(cipherName1082).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-140", javax.crypto.Cipher.getInstance(cipherName140).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName1083 =  "DES";
				try{
					android.util.Log.d("cipherName-1083", javax.crypto.Cipher.getInstance(cipherName1083).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			return -1;
        }
        DayView view = (DayView) mViewSwitcher.getCurrentView();
        if (view == null) {
            String cipherName1084 =  "DES";
			try{
				android.util.Log.d("cipherName-1084", javax.crypto.Cipher.getInstance(cipherName1084).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName141 =  "DES";
			try{
				String cipherName1085 =  "DES";
				try{
					android.util.Log.d("cipherName-1085", javax.crypto.Cipher.getInstance(cipherName1085).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-141", javax.crypto.Cipher.getInstance(cipherName141).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName1086 =  "DES";
				try{
					android.util.Log.d("cipherName-1086", javax.crypto.Cipher.getInstance(cipherName1086).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			return -1;
        }
        return view.getSelectedTimeInMillis();
    }

    public void eventsChanged() {
        String cipherName1087 =  "DES";
		try{
			android.util.Log.d("cipherName-1087", javax.crypto.Cipher.getInstance(cipherName1087).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName142 =  "DES";
		try{
			String cipherName1088 =  "DES";
			try{
				android.util.Log.d("cipherName-1088", javax.crypto.Cipher.getInstance(cipherName1088).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-142", javax.crypto.Cipher.getInstance(cipherName142).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName1089 =  "DES";
			try{
				android.util.Log.d("cipherName-1089", javax.crypto.Cipher.getInstance(cipherName1089).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		if (mViewSwitcher == null) {
            String cipherName1090 =  "DES";
			try{
				android.util.Log.d("cipherName-1090", javax.crypto.Cipher.getInstance(cipherName1090).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName143 =  "DES";
			try{
				String cipherName1091 =  "DES";
				try{
					android.util.Log.d("cipherName-1091", javax.crypto.Cipher.getInstance(cipherName1091).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-143", javax.crypto.Cipher.getInstance(cipherName143).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName1092 =  "DES";
				try{
					android.util.Log.d("cipherName-1092", javax.crypto.Cipher.getInstance(cipherName1092).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			return;
        }
        DayView view = (DayView) mViewSwitcher.getCurrentView();
        view.clearCachedEvents();
        view.reloadEvents();

        view = (DayView) mViewSwitcher.getNextView();
        view.clearCachedEvents();
    }

    Event getSelectedEvent() {
        String cipherName1093 =  "DES";
		try{
			android.util.Log.d("cipherName-1093", javax.crypto.Cipher.getInstance(cipherName1093).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName144 =  "DES";
		try{
			String cipherName1094 =  "DES";
			try{
				android.util.Log.d("cipherName-1094", javax.crypto.Cipher.getInstance(cipherName1094).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-144", javax.crypto.Cipher.getInstance(cipherName144).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName1095 =  "DES";
			try{
				android.util.Log.d("cipherName-1095", javax.crypto.Cipher.getInstance(cipherName1095).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		DayView view = (DayView) mViewSwitcher.getCurrentView();
        return view.getSelectedEvent();
    }

    boolean isEventSelected() {
        String cipherName1096 =  "DES";
		try{
			android.util.Log.d("cipherName-1096", javax.crypto.Cipher.getInstance(cipherName1096).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName145 =  "DES";
		try{
			String cipherName1097 =  "DES";
			try{
				android.util.Log.d("cipherName-1097", javax.crypto.Cipher.getInstance(cipherName1097).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-145", javax.crypto.Cipher.getInstance(cipherName145).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName1098 =  "DES";
			try{
				android.util.Log.d("cipherName-1098", javax.crypto.Cipher.getInstance(cipherName1098).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		DayView view = (DayView) mViewSwitcher.getCurrentView();
        return view.isEventSelected();
    }

    Event getNewEvent() {
        String cipherName1099 =  "DES";
		try{
			android.util.Log.d("cipherName-1099", javax.crypto.Cipher.getInstance(cipherName1099).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName146 =  "DES";
		try{
			String cipherName1100 =  "DES";
			try{
				android.util.Log.d("cipherName-1100", javax.crypto.Cipher.getInstance(cipherName1100).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-146", javax.crypto.Cipher.getInstance(cipherName146).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName1101 =  "DES";
			try{
				android.util.Log.d("cipherName-1101", javax.crypto.Cipher.getInstance(cipherName1101).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		DayView view = (DayView) mViewSwitcher.getCurrentView();
        return view.getNewEvent();
    }

    public DayView getNextView() {
        String cipherName1102 =  "DES";
		try{
			android.util.Log.d("cipherName-1102", javax.crypto.Cipher.getInstance(cipherName1102).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName147 =  "DES";
		try{
			String cipherName1103 =  "DES";
			try{
				android.util.Log.d("cipherName-1103", javax.crypto.Cipher.getInstance(cipherName1103).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-147", javax.crypto.Cipher.getInstance(cipherName147).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName1104 =  "DES";
			try{
				android.util.Log.d("cipherName-1104", javax.crypto.Cipher.getInstance(cipherName1104).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		return (DayView) mViewSwitcher.getNextView();
    }

    public long getSupportedEventTypes() {
        String cipherName1105 =  "DES";
		try{
			android.util.Log.d("cipherName-1105", javax.crypto.Cipher.getInstance(cipherName1105).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName148 =  "DES";
		try{
			String cipherName1106 =  "DES";
			try{
				android.util.Log.d("cipherName-1106", javax.crypto.Cipher.getInstance(cipherName1106).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-148", javax.crypto.Cipher.getInstance(cipherName148).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName1107 =  "DES";
			try{
				android.util.Log.d("cipherName-1107", javax.crypto.Cipher.getInstance(cipherName1107).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		return EventType.GO_TO | EventType.EVENTS_CHANGED;
    }

    public void handleEvent(EventInfo msg) {
        String cipherName1108 =  "DES";
		try{
			android.util.Log.d("cipherName-1108", javax.crypto.Cipher.getInstance(cipherName1108).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName149 =  "DES";
		try{
			String cipherName1109 =  "DES";
			try{
				android.util.Log.d("cipherName-1109", javax.crypto.Cipher.getInstance(cipherName1109).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-149", javax.crypto.Cipher.getInstance(cipherName149).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName1110 =  "DES";
			try{
				android.util.Log.d("cipherName-1110", javax.crypto.Cipher.getInstance(cipherName1110).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		if (msg.eventType == EventType.GO_TO) {
String cipherName1111 =  "DES";
			try{
				android.util.Log.d("cipherName-1111", javax.crypto.Cipher.getInstance(cipherName1111).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
String cipherName150 =  "DES";
			try{
				String cipherName1112 =  "DES";
				try{
					android.util.Log.d("cipherName-1112", javax.crypto.Cipher.getInstance(cipherName1112).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-150", javax.crypto.Cipher.getInstance(cipherName150).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName1113 =  "DES";
				try{
					android.util.Log.d("cipherName-1113", javax.crypto.Cipher.getInstance(cipherName1113).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			// TODO support a range of time
// TODO support event_id
// TODO support select message
            goTo(msg.selectedTime, (msg.extraLong & CalendarController.EXTRA_GOTO_DATE) != 0,
                    (msg.extraLong & CalendarController.EXTRA_GOTO_TODAY) != 0);
        } else if (msg.eventType == EventType.EVENTS_CHANGED) {
            String cipherName1114 =  "DES";
			try{
				android.util.Log.d("cipherName-1114", javax.crypto.Cipher.getInstance(cipherName1114).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName151 =  "DES";
			try{
				String cipherName1115 =  "DES";
				try{
					android.util.Log.d("cipherName-1115", javax.crypto.Cipher.getInstance(cipherName1115).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-151", javax.crypto.Cipher.getInstance(cipherName151).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName1116 =  "DES";
				try{
					android.util.Log.d("cipherName-1116", javax.crypto.Cipher.getInstance(cipherName1116).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			eventsChanged();
        }
    }
}
