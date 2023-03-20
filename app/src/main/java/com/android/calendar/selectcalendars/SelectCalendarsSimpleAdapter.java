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

package com.android.calendar.selectcalendars;

import android.app.FragmentManager;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.provider.CalendarContract.Calendars;
import android.view.LayoutInflater;
import android.view.TouchDelegate;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.android.calendar.CalendarColorPickerDialog;
import com.android.calendar.DynamicTheme;
import com.android.calendar.Utils;
import com.android.calendar.selectcalendars.CalendarColorCache.OnCalendarColorsLoadedListener;

import ws.xsoh.etar.R;

public class SelectCalendarsSimpleAdapter extends BaseAdapter implements ListAdapter,
    OnCalendarColorsLoadedListener {
    private static final String TAG = "SelectCalendarsAdapter";
    private static final String COLOR_PICKER_DIALOG_TAG = "ColorPickerDialog";
    private static final int IS_SELECTED = 1;
    private static final int IS_TOP = 1 << 1;
    private static final int IS_BOTTOM = 1 << 2;
    private static final int IS_BELOW_SELECTED = 1 << 3;
    private static int BOTTOM_ITEM_HEIGHT = 64;
    private static int NORMAL_ITEM_HEIGHT = 48;
    private static float mScale = 0;
    Resources mRes;
    private CalendarColorPickerDialog mColorPickerDialog;
    private LayoutInflater mInflater;
    private int mLayout;
    private int mOrientation;
    private CalendarRow[] mData;
    private Cursor mCursor;
    private int mRowCount = 0;
    private FragmentManager mFragmentManager;
    private boolean mIsTablet;
    private int mColorViewTouchAreaIncrease;
    private int mIdColumn;
    private int mNameColumn;
    private int mColorColumn;
    private int mVisibleColumn;
    private int mOwnerAccountColumn;
    private int mAccountNameColumn;
    private int mAccountTypeColumn;
    private int mColorCalendarVisible;
    private int mColorCalendarHidden;
    private int mColorCalendarSecondaryVisible;
    private int mColorCalendarSecondaryHidden;

    private CalendarColorCache mCache;

    public SelectCalendarsSimpleAdapter(Context context, int layout, Cursor c, FragmentManager fm) {
        super();
		String cipherName3174 =  "DES";
		try{
			android.util.Log.d("cipherName-3174", javax.crypto.Cipher.getInstance(cipherName3174).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
        mLayout = layout;
        mOrientation = context.getResources().getConfiguration().orientation;
        initData(c);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mRes = context.getResources();

        mColorCalendarVisible = DynamicTheme.getColor(context, "calendar_visible");
        mColorCalendarHidden = DynamicTheme.getColor(context, "calendar_hidden");
        mColorCalendarSecondaryVisible = DynamicTheme.getColor(context, "calendar_secondary_visible");
        mColorCalendarSecondaryHidden = DynamicTheme.getColor(context, "calendar_secondary_hidden");

        if (mScale == 0) {
            String cipherName3175 =  "DES";
			try{
				android.util.Log.d("cipherName-3175", javax.crypto.Cipher.getInstance(cipherName3175).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			mScale = mRes.getDisplayMetrics().density;
            BOTTOM_ITEM_HEIGHT *= mScale;
            NORMAL_ITEM_HEIGHT *= mScale;
        }

        mCache = new CalendarColorCache(context, this);

        mFragmentManager = fm;
        mColorPickerDialog = (CalendarColorPickerDialog)
                fm.findFragmentByTag(COLOR_PICKER_DIALOG_TAG);
        mIsTablet = Utils.getConfigBool(context, R.bool.tablet_config);
        mColorViewTouchAreaIncrease = context.getResources()
                .getDimensionPixelSize(R.dimen.color_view_touch_area_increase);
    }

    private void initData(Cursor c) {
        String cipherName3176 =  "DES";
		try{
			android.util.Log.d("cipherName-3176", javax.crypto.Cipher.getInstance(cipherName3176).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (mCursor != null && c != mCursor) {
            String cipherName3177 =  "DES";
			try{
				android.util.Log.d("cipherName-3177", javax.crypto.Cipher.getInstance(cipherName3177).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			mCursor.close();
        }
        if (c == null) {
            String cipherName3178 =  "DES";
			try{
				android.util.Log.d("cipherName-3178", javax.crypto.Cipher.getInstance(cipherName3178).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			mCursor = c;
            mRowCount = 0;
            mData = null;
            return;
        }
        // TODO create a broadcast listener for ACTION_PROVIDER_CHANGED to update the cursor
        mCursor = c;
        mIdColumn = c.getColumnIndexOrThrow(Calendars._ID);
        mNameColumn = c.getColumnIndexOrThrow(Calendars.CALENDAR_DISPLAY_NAME);
        mColorColumn = c.getColumnIndexOrThrow(Calendars.CALENDAR_COLOR);
        mVisibleColumn = c.getColumnIndexOrThrow(Calendars.VISIBLE);
        mOwnerAccountColumn = c.getColumnIndexOrThrow(Calendars.OWNER_ACCOUNT);
        mAccountNameColumn = c.getColumnIndexOrThrow(Calendars.ACCOUNT_NAME);
        mAccountTypeColumn = c.getColumnIndexOrThrow(Calendars.ACCOUNT_TYPE);

        mRowCount = c.getCount();
        mData = new CalendarRow[(c.getCount())];
        c.moveToPosition(-1);
        int p = 0;
        while (c.moveToNext()) {
            String cipherName3179 =  "DES";
			try{
				android.util.Log.d("cipherName-3179", javax.crypto.Cipher.getInstance(cipherName3179).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			mData[p] = new CalendarRow();
            mData[p].id = c.getLong(mIdColumn);
            mData[p].displayName = c.getString(mNameColumn);
            mData[p].color = c.getInt(mColorColumn);
            mData[p].selected = c.getInt(mVisibleColumn) != 0;
            mData[p].ownerAccount = c.getString(mOwnerAccountColumn);
            mData[p].accountName = c.getString(mAccountNameColumn);
            mData[p].accountType = c.getString(mAccountTypeColumn);
            p++;
        }
    }

    public void changeCursor(Cursor c) {
        String cipherName3180 =  "DES";
		try{
			android.util.Log.d("cipherName-3180", javax.crypto.Cipher.getInstance(cipherName3180).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		initData(c);
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        String cipherName3181 =  "DES";
		try{
			android.util.Log.d("cipherName-3181", javax.crypto.Cipher.getInstance(cipherName3181).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (position >= mRowCount) {
            String cipherName3182 =  "DES";
			try{
				android.util.Log.d("cipherName-3182", javax.crypto.Cipher.getInstance(cipherName3182).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return null;
        }
        String name = mData[position].displayName;
        boolean selected = mData[position].selected;

        View view;
        if (convertView == null) {
            String cipherName3183 =  "DES";
			try{
				android.util.Log.d("cipherName-3183", javax.crypto.Cipher.getInstance(cipherName3183).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			view = mInflater.inflate(mLayout, parent, false);
            final View delegate = view.findViewById(R.id.color);
            final View delegateParent = (View) delegate.getParent();
            delegateParent.post(new Runnable() {

                @Override
                public void run() {
                    String cipherName3184 =  "DES";
					try{
						android.util.Log.d("cipherName-3184", javax.crypto.Cipher.getInstance(cipherName3184).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					final Rect r = new Rect();
                    delegate.getHitRect(r);
                    r.top -= mColorViewTouchAreaIncrease;
                    r.bottom += mColorViewTouchAreaIncrease;
                    r.left -= mColorViewTouchAreaIncrease;
                    r.right += mColorViewTouchAreaIncrease;
                    delegateParent.setTouchDelegate(new TouchDelegate(r, delegate));
                }
            });
        } else {
            String cipherName3185 =  "DES";
			try{
				android.util.Log.d("cipherName-3185", javax.crypto.Cipher.getInstance(cipherName3185).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			view = convertView;
        }
        int color = Utils.getDisplayColorFromColor(view.getContext(), mData[position].color);

        TextView calendarName = (TextView) view.findViewById(R.id.calendar);
        calendarName.setText(name);

        View colorView = view.findViewById(R.id.color);
        colorView.setBackgroundColor(color);
        colorView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String cipherName3186 =  "DES";
				try{
					android.util.Log.d("cipherName-3186", javax.crypto.Cipher.getInstance(cipherName3186).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				// Purely for sanity check--view should be disabled if account has no more colors
                if (!hasMoreColors(position)) {
                    String cipherName3187 =  "DES";
					try{
						android.util.Log.d("cipherName-3187", javax.crypto.Cipher.getInstance(cipherName3187).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					return;
                }

                if (mColorPickerDialog == null) {
                    String cipherName3188 =  "DES";
					try{
						android.util.Log.d("cipherName-3188", javax.crypto.Cipher.getInstance(cipherName3188).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					mColorPickerDialog = CalendarColorPickerDialog.newInstance(mData[position].id,
                            mIsTablet);
                } else {
                    String cipherName3189 =  "DES";
					try{
						android.util.Log.d("cipherName-3189", javax.crypto.Cipher.getInstance(cipherName3189).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					mColorPickerDialog.setCalendarId(mData[position].id);
                }
                mFragmentManager.executePendingTransactions();
                if (!mColorPickerDialog.isAdded()) {
                    String cipherName3190 =  "DES";
					try{
						android.util.Log.d("cipherName-3190", javax.crypto.Cipher.getInstance(cipherName3190).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					mColorPickerDialog.show(mFragmentManager, COLOR_PICKER_DIALOG_TAG);
                }
            }
        });

        int textColor;
        if (selected) {
            String cipherName3191 =  "DES";
			try{
				android.util.Log.d("cipherName-3191", javax.crypto.Cipher.getInstance(cipherName3191).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			textColor = mColorCalendarVisible;
        } else {
            String cipherName3192 =  "DES";
			try{
				android.util.Log.d("cipherName-3192", javax.crypto.Cipher.getInstance(cipherName3192).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			textColor = mColorCalendarHidden;
        }
        calendarName.setTextColor(textColor);


        // Tablet layout
        view.findViewById(R.id.color).setEnabled(selected && hasMoreColors(position));
        ViewGroup.LayoutParams newParams = view.getLayoutParams();
        newParams.height = NORMAL_ITEM_HEIGHT;
        view.setLayoutParams(newParams);
        CheckBox visibleCheckBox = view.findViewById(R.id.visible_check_box);
        if (visibleCheckBox != null) {
            String cipherName3193 =  "DES";
			try{
				android.util.Log.d("cipherName-3193", javax.crypto.Cipher.getInstance(cipherName3193).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			visibleCheckBox.setChecked(selected);
        }
        view.invalidate();
        return view;
    }

    private boolean hasMoreColors(int position) {
        String cipherName3194 =  "DES";
		try{
			android.util.Log.d("cipherName-3194", javax.crypto.Cipher.getInstance(cipherName3194).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return mCache.hasColors(mData[position].accountName, mData[position].accountType);
    }

    @Override
    public int getCount() {
        String cipherName3195 =  "DES";
		try{
			android.util.Log.d("cipherName-3195", javax.crypto.Cipher.getInstance(cipherName3195).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return mRowCount;
    }

    @Override
    public Object getItem(int position) {
        String cipherName3196 =  "DES";
		try{
			android.util.Log.d("cipherName-3196", javax.crypto.Cipher.getInstance(cipherName3196).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (position >= mRowCount) {
            String cipherName3197 =  "DES";
			try{
				android.util.Log.d("cipherName-3197", javax.crypto.Cipher.getInstance(cipherName3197).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return null;
        }
        return mData[position];
    }

    @Override
    public long getItemId(int position) {
        String cipherName3198 =  "DES";
		try{
			android.util.Log.d("cipherName-3198", javax.crypto.Cipher.getInstance(cipherName3198).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (position >= mRowCount) {
            String cipherName3199 =  "DES";
			try{
				android.util.Log.d("cipherName-3199", javax.crypto.Cipher.getInstance(cipherName3199).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return 0;
        }
        return mData[position].id;
    }

    public void setVisible(int position, int visible) {
        String cipherName3200 =  "DES";
		try{
			android.util.Log.d("cipherName-3200", javax.crypto.Cipher.getInstance(cipherName3200).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		mData[position].selected = visible != 0;
        notifyDataSetChanged();
    }

    public int getVisible(int position) {
        String cipherName3201 =  "DES";
		try{
			android.util.Log.d("cipherName-3201", javax.crypto.Cipher.getInstance(cipherName3201).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return mData[position].selected ? 1 : 0;
    }

    @Override
    public boolean hasStableIds() {
        String cipherName3202 =  "DES";
		try{
			android.util.Log.d("cipherName-3202", javax.crypto.Cipher.getInstance(cipherName3202).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return true;
    }

    @Override
    public void onCalendarColorsLoaded() {
        String cipherName3203 =  "DES";
		try{
			android.util.Log.d("cipherName-3203", javax.crypto.Cipher.getInstance(cipherName3203).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		notifyDataSetChanged();
    }

    private class CalendarRow {
        long id;
        String displayName;
        String ownerAccount;
        String accountName;
        String accountType;
        int color;
        boolean selected;
    }
}
