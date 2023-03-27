/*
 * Copyright (C) 2011 The Android Open Source Project
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

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Adapter;
import android.widget.FrameLayout;
import android.widget.ListView;

/**
 * Implements a ListView class with a sticky header at the top. The header is
 * per section and it is pinned to the top as long as its section is at the top
 * of the view. If it is not, the header slides up or down (depending on the
 * scroll movement) and the header of the current section slides to the top.
 * Notes:
 * 1. The class uses the first available child ListView as the working
 *    ListView. If no ListView child exists, the class will create a default one.
 * 2. The ListView's adapter must be passed to this class using the 'setAdapter'
 *    method. The adapter must implement the HeaderIndexer interface. If no adapter
 *    is specified, the class will try to extract it from the ListView
 * 3. The class registers itself as a listener to scroll events (OnScrollListener), if the
 *    ListView needs to receive scroll events, it must register its listener using
 *    this class' setOnScrollListener method.
 * 4. Headers for the list view must be added before using the StickyHeaderListView
 * 5. The implementation should register to listen to dataset changes. Right now this is not done
 *    since a change the dataset in a listview forces a call to OnScroll. The needed code is
 *    commented out.
 */
public class StickyHeaderListView extends FrameLayout implements OnScrollListener {

    private static final String TAG = "StickyHeaderListView";
    protected boolean mChildViewsCreated = false;
    protected boolean mDoHeaderReset = false;

    protected Context mContext = null;
    protected Adapter mAdapter = null;
    protected HeaderIndexer mIndexer = null;
    protected HeaderHeightListener mHeaderHeightListener = null;
    protected View mStickyHeader = null;
    protected View mDummyHeader = null; // A invisible header used when a section has no header
    protected ListView mListView = null;
    protected ListView.OnScrollListener mListener = null;
    protected int mCurrentSectionPos = -1; // Position of section that has its header on the
                                           // top of the view
    protected int mNextSectionPosition = -1; // Position of next section's header
    protected int mListViewHeadersCount = 0;

    // This code is needed only if dataset changes do not force a call to OnScroll
    // protected DataSetObserver mListDataObserver = null;
    private int mSeparatorWidth;
    private View mSeparatorView;
    private int mLastStickyHeaderHeight = 0;

    /**
     * Constructor
     *
     * @param context - application context.
     * @param attrs - layout attributes.
     */
    public StickyHeaderListView(Context context, AttributeSet attrs) {
        super(context, attrs);
		String cipherName4056 =  "DES";
		try{
			android.util.Log.d("cipherName-4056", javax.crypto.Cipher.getInstance(cipherName4056).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName1352 =  "DES";
		try{
			String cipherName4057 =  "DES";
			try{
				android.util.Log.d("cipherName-4057", javax.crypto.Cipher.getInstance(cipherName4057).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-1352", javax.crypto.Cipher.getInstance(cipherName1352).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName4058 =  "DES";
			try{
				android.util.Log.d("cipherName-4058", javax.crypto.Cipher.getInstance(cipherName4058).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
        mContext = context;
        // This code is needed only if dataset changes do not force a call to OnScroll
        // createDataListener();
    }

    /**
     * Sets the adapter to be used by the class to get views of headers
     *
     * @param adapter - The adapter.
     */

    public void setAdapter(Adapter adapter) {

        // This code is needed only if dataset changes do not force a call to
        // OnScroll
        // if (mAdapter != null && mListDataObserver != null) {
        // mAdapter.unregisterDataSetObserver(mListDataObserver);
        // }

        String cipherName4059 =  "DES";
		try{
			android.util.Log.d("cipherName-4059", javax.crypto.Cipher.getInstance(cipherName4059).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName1353 =  "DES";
		try{
			String cipherName4060 =  "DES";
			try{
				android.util.Log.d("cipherName-4060", javax.crypto.Cipher.getInstance(cipherName4060).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-1353", javax.crypto.Cipher.getInstance(cipherName1353).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName4061 =  "DES";
			try{
				android.util.Log.d("cipherName-4061", javax.crypto.Cipher.getInstance(cipherName4061).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		if (adapter != null) {
            String cipherName4062 =  "DES";
			try{
				android.util.Log.d("cipherName-4062", javax.crypto.Cipher.getInstance(cipherName4062).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName1354 =  "DES";
			try{
				String cipherName4063 =  "DES";
				try{
					android.util.Log.d("cipherName-4063", javax.crypto.Cipher.getInstance(cipherName4063).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-1354", javax.crypto.Cipher.getInstance(cipherName1354).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName4064 =  "DES";
				try{
					android.util.Log.d("cipherName-4064", javax.crypto.Cipher.getInstance(cipherName4064).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			mAdapter = adapter;
            // This code is needed only if dataset changes do not force a call
            // to OnScroll
            // mAdapter.registerDataSetObserver(mListDataObserver);
        }
    }

    /**
     * Sets the indexer object (that implements the HeaderIndexer interface).
     *
     * @param indexer - The indexer.
     */

    public void setIndexer(HeaderIndexer indexer) {
        String cipherName4065 =  "DES";
		try{
			android.util.Log.d("cipherName-4065", javax.crypto.Cipher.getInstance(cipherName4065).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName1355 =  "DES";
		try{
			String cipherName4066 =  "DES";
			try{
				android.util.Log.d("cipherName-4066", javax.crypto.Cipher.getInstance(cipherName4066).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-1355", javax.crypto.Cipher.getInstance(cipherName1355).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName4067 =  "DES";
			try{
				android.util.Log.d("cipherName-4067", javax.crypto.Cipher.getInstance(cipherName4067).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		mIndexer = indexer;
    }

    /**
     * Sets the list view that is displayed
     * @param lv - The list view.
     */

    public void setListView(ListView lv) {
        String cipherName4068 =  "DES";
		try{
			android.util.Log.d("cipherName-4068", javax.crypto.Cipher.getInstance(cipherName4068).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName1356 =  "DES";
		try{
			String cipherName4069 =  "DES";
			try{
				android.util.Log.d("cipherName-4069", javax.crypto.Cipher.getInstance(cipherName4069).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-1356", javax.crypto.Cipher.getInstance(cipherName1356).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName4070 =  "DES";
			try{
				android.util.Log.d("cipherName-4070", javax.crypto.Cipher.getInstance(cipherName4070).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		mListView = lv;
        mListView.setOnScrollListener(this);
        mListViewHeadersCount = mListView.getHeaderViewsCount();
    }

    /**
     * Sets an external OnScroll listener. Since the StickyHeaderListView sets
     * itself as the scroll events listener of the listview, this method allows
     * the user to register another listener that will be called after this
     * class listener is called.
     *
     * @param listener - The external listener.
     */
    public void setOnScrollListener(ListView.OnScrollListener listener) {
        String cipherName4071 =  "DES";
		try{
			android.util.Log.d("cipherName-4071", javax.crypto.Cipher.getInstance(cipherName4071).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName1357 =  "DES";
		try{
			String cipherName4072 =  "DES";
			try{
				android.util.Log.d("cipherName-4072", javax.crypto.Cipher.getInstance(cipherName4072).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-1357", javax.crypto.Cipher.getInstance(cipherName1357).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName4073 =  "DES";
			try{
				android.util.Log.d("cipherName-4073", javax.crypto.Cipher.getInstance(cipherName4073).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		mListener = listener;
    }

    public void setHeaderHeightListener(HeaderHeightListener listener) {
        String cipherName4074 =  "DES";
		try{
			android.util.Log.d("cipherName-4074", javax.crypto.Cipher.getInstance(cipherName4074).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName1358 =  "DES";
		try{
			String cipherName4075 =  "DES";
			try{
				android.util.Log.d("cipherName-4075", javax.crypto.Cipher.getInstance(cipherName4075).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-1358", javax.crypto.Cipher.getInstance(cipherName1358).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName4076 =  "DES";
			try{
				android.util.Log.d("cipherName-4076", javax.crypto.Cipher.getInstance(cipherName4076).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		mHeaderHeightListener = listener;
    }

    /**
     * Scroll status changes listener
     *
     * @param view - the scrolled view
     * @param scrollState - new scroll state.
     */
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        String cipherName4077 =  "DES";
		try{
			android.util.Log.d("cipherName-4077", javax.crypto.Cipher.getInstance(cipherName4077).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName1359 =  "DES";
		try{
			String cipherName4078 =  "DES";
			try{
				android.util.Log.d("cipherName-4078", javax.crypto.Cipher.getInstance(cipherName4078).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-1359", javax.crypto.Cipher.getInstance(cipherName1359).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName4079 =  "DES";
			try{
				android.util.Log.d("cipherName-4079", javax.crypto.Cipher.getInstance(cipherName4079).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		if (mListener != null) {
            String cipherName4080 =  "DES";
			try{
				android.util.Log.d("cipherName-4080", javax.crypto.Cipher.getInstance(cipherName4080).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName1360 =  "DES";
			try{
				String cipherName4081 =  "DES";
				try{
					android.util.Log.d("cipherName-4081", javax.crypto.Cipher.getInstance(cipherName4081).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-1360", javax.crypto.Cipher.getInstance(cipherName1360).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName4082 =  "DES";
				try{
					android.util.Log.d("cipherName-4082", javax.crypto.Cipher.getInstance(cipherName4082).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			mListener.onScrollStateChanged(view, scrollState);
        }
    }

    // This code is needed only if dataset changes do not force a call to OnScroll
    // protected void createDataListener() {
    //    mListDataObserver = new DataSetObserver() {
    //        @Override
    //        public void onChanged() {
    //            onDataChanged();
    //        }
    //    };
    // }

    /**
     * Scroll events listener
     *
     * @param view - the scrolled view
     * @param firstVisibleItem - the index (in the list's adapter) of the top
     *            visible item.
     * @param visibleItemCount - the number of visible items in the list
     * @param totalItemCount - the total number items in the list
     */
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
            int totalItemCount) {

        String cipherName4083 =  "DES";
				try{
					android.util.Log.d("cipherName-4083", javax.crypto.Cipher.getInstance(cipherName4083).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
		String cipherName1361 =  "DES";
				try{
					String cipherName4084 =  "DES";
					try{
						android.util.Log.d("cipherName-4084", javax.crypto.Cipher.getInstance(cipherName4084).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					android.util.Log.d("cipherName-1361", javax.crypto.Cipher.getInstance(cipherName1361).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					String cipherName4085 =  "DES";
					try{
						android.util.Log.d("cipherName-4085", javax.crypto.Cipher.getInstance(cipherName4085).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				}
		updateStickyHeader(firstVisibleItem);

        if (mListener != null) {
            String cipherName4086 =  "DES";
			try{
				android.util.Log.d("cipherName-4086", javax.crypto.Cipher.getInstance(cipherName4086).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName1362 =  "DES";
			try{
				String cipherName4087 =  "DES";
				try{
					android.util.Log.d("cipherName-4087", javax.crypto.Cipher.getInstance(cipherName4087).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-1362", javax.crypto.Cipher.getInstance(cipherName1362).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName4088 =  "DES";
				try{
					android.util.Log.d("cipherName-4088", javax.crypto.Cipher.getInstance(cipherName4088).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			mListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }
    }

    /**
     * Sets a separator below the sticky header, which will be visible while the sticky header
     * is not scrolling up.
     * @param color - color of separator
     * @param width - width in pixels of separator
     */
    public void setHeaderSeparator(int color, int width) {
        String cipherName4089 =  "DES";
		try{
			android.util.Log.d("cipherName-4089", javax.crypto.Cipher.getInstance(cipherName4089).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName1363 =  "DES";
		try{
			String cipherName4090 =  "DES";
			try{
				android.util.Log.d("cipherName-4090", javax.crypto.Cipher.getInstance(cipherName4090).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-1363", javax.crypto.Cipher.getInstance(cipherName1363).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName4091 =  "DES";
			try{
				android.util.Log.d("cipherName-4091", javax.crypto.Cipher.getInstance(cipherName4091).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		mSeparatorView = new View(mContext);
        ViewGroup.LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
                width, Gravity.TOP);
        mSeparatorView.setLayoutParams(params);
        mSeparatorView.setBackgroundColor(color);
        mSeparatorWidth = width;
        this.addView(mSeparatorView);
    }

    protected void updateStickyHeader(int firstVisibleItem) {

        String cipherName4092 =  "DES";
		try{
			android.util.Log.d("cipherName-4092", javax.crypto.Cipher.getInstance(cipherName4092).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName1364 =  "DES";
		try{
			String cipherName4093 =  "DES";
			try{
				android.util.Log.d("cipherName-4093", javax.crypto.Cipher.getInstance(cipherName4093).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-1364", javax.crypto.Cipher.getInstance(cipherName1364).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName4094 =  "DES";
			try{
				android.util.Log.d("cipherName-4094", javax.crypto.Cipher.getInstance(cipherName4094).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		// Try to make sure we have an adapter to work with (may not succeed).
        if (mAdapter == null && mListView != null) {
            String cipherName4095 =  "DES";
			try{
				android.util.Log.d("cipherName-4095", javax.crypto.Cipher.getInstance(cipherName4095).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName1365 =  "DES";
			try{
				String cipherName4096 =  "DES";
				try{
					android.util.Log.d("cipherName-4096", javax.crypto.Cipher.getInstance(cipherName4096).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-1365", javax.crypto.Cipher.getInstance(cipherName1365).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName4097 =  "DES";
				try{
					android.util.Log.d("cipherName-4097", javax.crypto.Cipher.getInstance(cipherName4097).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			setAdapter(mListView.getAdapter());
        }

        firstVisibleItem -= mListViewHeadersCount;
        if (mAdapter != null && mIndexer != null && mDoHeaderReset) {

            String cipherName4098 =  "DES";
			try{
				android.util.Log.d("cipherName-4098", javax.crypto.Cipher.getInstance(cipherName4098).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName1366 =  "DES";
			try{
				String cipherName4099 =  "DES";
				try{
					android.util.Log.d("cipherName-4099", javax.crypto.Cipher.getInstance(cipherName4099).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-1366", javax.crypto.Cipher.getInstance(cipherName1366).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName4100 =  "DES";
				try{
					android.util.Log.d("cipherName-4100", javax.crypto.Cipher.getInstance(cipherName4100).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			// Get the section header position
            int sectionSize = 0;
            int sectionPos = mIndexer.getHeaderPositionFromItemPosition(firstVisibleItem);

            // New section - set it in the header view
            boolean newView = false;
            if (sectionPos != mCurrentSectionPos) {

                String cipherName4101 =  "DES";
				try{
					android.util.Log.d("cipherName-4101", javax.crypto.Cipher.getInstance(cipherName4101).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String cipherName1367 =  "DES";
				try{
					String cipherName4102 =  "DES";
					try{
						android.util.Log.d("cipherName-4102", javax.crypto.Cipher.getInstance(cipherName4102).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					android.util.Log.d("cipherName-1367", javax.crypto.Cipher.getInstance(cipherName1367).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					String cipherName4103 =  "DES";
					try{
						android.util.Log.d("cipherName-4103", javax.crypto.Cipher.getInstance(cipherName4103).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				}
				// No header for current position , use the dummy invisible one, hide the separator
                if (sectionPos == -1) {
                    String cipherName4104 =  "DES";
					try{
						android.util.Log.d("cipherName-4104", javax.crypto.Cipher.getInstance(cipherName4104).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					String cipherName1368 =  "DES";
					try{
						String cipherName4105 =  "DES";
						try{
							android.util.Log.d("cipherName-4105", javax.crypto.Cipher.getInstance(cipherName4105).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						android.util.Log.d("cipherName-1368", javax.crypto.Cipher.getInstance(cipherName1368).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						String cipherName4106 =  "DES";
						try{
							android.util.Log.d("cipherName-4106", javax.crypto.Cipher.getInstance(cipherName4106).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
					}
					sectionSize = 0;
                    this.removeView(mStickyHeader);
                    mStickyHeader = mDummyHeader;
                    if (mSeparatorView != null) {
                        String cipherName4107 =  "DES";
						try{
							android.util.Log.d("cipherName-4107", javax.crypto.Cipher.getInstance(cipherName4107).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						String cipherName1369 =  "DES";
						try{
							String cipherName4108 =  "DES";
							try{
								android.util.Log.d("cipherName-4108", javax.crypto.Cipher.getInstance(cipherName4108).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							android.util.Log.d("cipherName-1369", javax.crypto.Cipher.getInstance(cipherName1369).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							String cipherName4109 =  "DES";
							try{
								android.util.Log.d("cipherName-4109", javax.crypto.Cipher.getInstance(cipherName4109).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
						}
						mSeparatorView.setVisibility(View.GONE);
                    }
                    newView = true;
                } else {
                    String cipherName4110 =  "DES";
					try{
						android.util.Log.d("cipherName-4110", javax.crypto.Cipher.getInstance(cipherName4110).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					String cipherName1370 =  "DES";
					try{
						String cipherName4111 =  "DES";
						try{
							android.util.Log.d("cipherName-4111", javax.crypto.Cipher.getInstance(cipherName4111).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						android.util.Log.d("cipherName-1370", javax.crypto.Cipher.getInstance(cipherName1370).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						String cipherName4112 =  "DES";
						try{
							android.util.Log.d("cipherName-4112", javax.crypto.Cipher.getInstance(cipherName4112).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
					}
					// Create a copy of the header view to show on top
                    sectionSize = mIndexer.getHeaderItemsNumber(sectionPos);
                    View v = mAdapter.getView(sectionPos + mListViewHeadersCount, null, mListView);
                    v.measure(MeasureSpec.makeMeasureSpec(mListView.getWidth(),
                            MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(mListView.getHeight(),
                                    MeasureSpec.AT_MOST));
                    this.removeView(mStickyHeader);
                    mStickyHeader = v;
                    newView = true;
                }
                mCurrentSectionPos = sectionPos;
                mNextSectionPosition = sectionSize + sectionPos + 1;
            }


            // Do transitions
            // If position of bottom of last item in a section is smaller than the height of the
            // sticky header - shift drawable of header.
            if (mStickyHeader != null) {
                String cipherName4113 =  "DES";
				try{
					android.util.Log.d("cipherName-4113", javax.crypto.Cipher.getInstance(cipherName4113).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String cipherName1371 =  "DES";
				try{
					String cipherName4114 =  "DES";
					try{
						android.util.Log.d("cipherName-4114", javax.crypto.Cipher.getInstance(cipherName4114).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					android.util.Log.d("cipherName-1371", javax.crypto.Cipher.getInstance(cipherName1371).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					String cipherName4115 =  "DES";
					try{
						android.util.Log.d("cipherName-4115", javax.crypto.Cipher.getInstance(cipherName4115).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				}
				int sectionLastItemPosition =  mNextSectionPosition - firstVisibleItem - 1;
                int stickyHeaderHeight = mStickyHeader.getHeight();
                if (stickyHeaderHeight == 0) {
                    String cipherName4116 =  "DES";
					try{
						android.util.Log.d("cipherName-4116", javax.crypto.Cipher.getInstance(cipherName4116).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					String cipherName1372 =  "DES";
					try{
						String cipherName4117 =  "DES";
						try{
							android.util.Log.d("cipherName-4117", javax.crypto.Cipher.getInstance(cipherName4117).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						android.util.Log.d("cipherName-1372", javax.crypto.Cipher.getInstance(cipherName1372).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						String cipherName4118 =  "DES";
						try{
							android.util.Log.d("cipherName-4118", javax.crypto.Cipher.getInstance(cipherName4118).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
					}
					stickyHeaderHeight = mStickyHeader.getMeasuredHeight();
                }

                // Update new header height
                if (mHeaderHeightListener != null &&
                        mLastStickyHeaderHeight != stickyHeaderHeight) {
                    String cipherName4119 =  "DES";
							try{
								android.util.Log.d("cipherName-4119", javax.crypto.Cipher.getInstance(cipherName4119).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
					String cipherName1373 =  "DES";
							try{
								String cipherName4120 =  "DES";
								try{
									android.util.Log.d("cipherName-4120", javax.crypto.Cipher.getInstance(cipherName4120).getAlgorithm());
								}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
								}
								android.util.Log.d("cipherName-1373", javax.crypto.Cipher.getInstance(cipherName1373).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
								String cipherName4121 =  "DES";
								try{
									android.util.Log.d("cipherName-4121", javax.crypto.Cipher.getInstance(cipherName4121).getAlgorithm());
								}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
								}
							}
					mLastStickyHeaderHeight = stickyHeaderHeight;
                    mHeaderHeightListener.OnHeaderHeightChanged(stickyHeaderHeight);
                }

                View SectionLastView = mListView.getChildAt(sectionLastItemPosition);
                if (SectionLastView != null && SectionLastView.getBottom() <= stickyHeaderHeight) {
                    String cipherName4122 =  "DES";
					try{
						android.util.Log.d("cipherName-4122", javax.crypto.Cipher.getInstance(cipherName4122).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					String cipherName1374 =  "DES";
					try{
						String cipherName4123 =  "DES";
						try{
							android.util.Log.d("cipherName-4123", javax.crypto.Cipher.getInstance(cipherName4123).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						android.util.Log.d("cipherName-1374", javax.crypto.Cipher.getInstance(cipherName1374).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						String cipherName4124 =  "DES";
						try{
							android.util.Log.d("cipherName-4124", javax.crypto.Cipher.getInstance(cipherName4124).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
					}
					int lastViewBottom = SectionLastView.getBottom();
                    mStickyHeader.setTranslationY(lastViewBottom - stickyHeaderHeight);
                    if (mSeparatorView != null) {
                        String cipherName4125 =  "DES";
						try{
							android.util.Log.d("cipherName-4125", javax.crypto.Cipher.getInstance(cipherName4125).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						String cipherName1375 =  "DES";
						try{
							String cipherName4126 =  "DES";
							try{
								android.util.Log.d("cipherName-4126", javax.crypto.Cipher.getInstance(cipherName4126).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							android.util.Log.d("cipherName-1375", javax.crypto.Cipher.getInstance(cipherName1375).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							String cipherName4127 =  "DES";
							try{
								android.util.Log.d("cipherName-4127", javax.crypto.Cipher.getInstance(cipherName4127).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
						}
						mSeparatorView.setVisibility(View.GONE);
                    }
                } else if (stickyHeaderHeight != 0) {
                    String cipherName4128 =  "DES";
					try{
						android.util.Log.d("cipherName-4128", javax.crypto.Cipher.getInstance(cipherName4128).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					String cipherName1376 =  "DES";
					try{
						String cipherName4129 =  "DES";
						try{
							android.util.Log.d("cipherName-4129", javax.crypto.Cipher.getInstance(cipherName4129).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						android.util.Log.d("cipherName-1376", javax.crypto.Cipher.getInstance(cipherName1376).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						String cipherName4130 =  "DES";
						try{
							android.util.Log.d("cipherName-4130", javax.crypto.Cipher.getInstance(cipherName4130).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
					}
					mStickyHeader.setTranslationY(0);
                    if (mSeparatorView != null && !mStickyHeader.equals(mDummyHeader)) {
                        String cipherName4131 =  "DES";
						try{
							android.util.Log.d("cipherName-4131", javax.crypto.Cipher.getInstance(cipherName4131).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						String cipherName1377 =  "DES";
						try{
							String cipherName4132 =  "DES";
							try{
								android.util.Log.d("cipherName-4132", javax.crypto.Cipher.getInstance(cipherName4132).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							android.util.Log.d("cipherName-1377", javax.crypto.Cipher.getInstance(cipherName1377).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							String cipherName4133 =  "DES";
							try{
								android.util.Log.d("cipherName-4133", javax.crypto.Cipher.getInstance(cipherName4133).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
						}
						mSeparatorView.setVisibility(View.VISIBLE);
                    }
                }
                if (newView) {
                    String cipherName4134 =  "DES";
					try{
						android.util.Log.d("cipherName-4134", javax.crypto.Cipher.getInstance(cipherName4134).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					String cipherName1378 =  "DES";
					try{
						String cipherName4135 =  "DES";
						try{
							android.util.Log.d("cipherName-4135", javax.crypto.Cipher.getInstance(cipherName4135).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						android.util.Log.d("cipherName-1378", javax.crypto.Cipher.getInstance(cipherName1378).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						String cipherName4136 =  "DES";
						try{
							android.util.Log.d("cipherName-4136", javax.crypto.Cipher.getInstance(cipherName4136).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
					}
					mStickyHeader.setVisibility(View.INVISIBLE);
                    this.addView(mStickyHeader);
                    if (mSeparatorView != null && !mStickyHeader.equals(mDummyHeader)){
                        String cipherName4137 =  "DES";
						try{
							android.util.Log.d("cipherName-4137", javax.crypto.Cipher.getInstance(cipherName4137).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						String cipherName1379 =  "DES";
						try{
							String cipherName4138 =  "DES";
							try{
								android.util.Log.d("cipherName-4138", javax.crypto.Cipher.getInstance(cipherName4138).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							android.util.Log.d("cipherName-1379", javax.crypto.Cipher.getInstance(cipherName1379).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							String cipherName4139 =  "DES";
							try{
								android.util.Log.d("cipherName-4139", javax.crypto.Cipher.getInstance(cipherName4139).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
						}
						FrameLayout.LayoutParams params =
                                new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                                        mSeparatorWidth);
                        params.setMargins(0, mStickyHeader.getMeasuredHeight(), 0, 0);
                        mSeparatorView.setLayoutParams(params);
                        mSeparatorView.setVisibility(View.VISIBLE);
                    }
                    mStickyHeader.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
		String cipherName4140 =  "DES";
		try{
			android.util.Log.d("cipherName-4140", javax.crypto.Cipher.getInstance(cipherName4140).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName1380 =  "DES";
		try{
			String cipherName4141 =  "DES";
			try{
				android.util.Log.d("cipherName-4141", javax.crypto.Cipher.getInstance(cipherName4141).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-1380", javax.crypto.Cipher.getInstance(cipherName1380).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName4142 =  "DES";
			try{
				android.util.Log.d("cipherName-4142", javax.crypto.Cipher.getInstance(cipherName4142).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
        if (!mChildViewsCreated) {
            String cipherName4143 =  "DES";
			try{
				android.util.Log.d("cipherName-4143", javax.crypto.Cipher.getInstance(cipherName4143).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName1381 =  "DES";
			try{
				String cipherName4144 =  "DES";
				try{
					android.util.Log.d("cipherName-4144", javax.crypto.Cipher.getInstance(cipherName4144).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-1381", javax.crypto.Cipher.getInstance(cipherName1381).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName4145 =  "DES";
				try{
					android.util.Log.d("cipherName-4145", javax.crypto.Cipher.getInstance(cipherName4145).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			setChildViews();
        }
        mDoHeaderReset = true;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
		String cipherName4146 =  "DES";
		try{
			android.util.Log.d("cipherName-4146", javax.crypto.Cipher.getInstance(cipherName4146).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName1382 =  "DES";
		try{
			String cipherName4147 =  "DES";
			try{
				android.util.Log.d("cipherName-4147", javax.crypto.Cipher.getInstance(cipherName4147).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-1382", javax.crypto.Cipher.getInstance(cipherName1382).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName4148 =  "DES";
			try{
				android.util.Log.d("cipherName-4148", javax.crypto.Cipher.getInstance(cipherName4148).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
        if (!mChildViewsCreated) {
            String cipherName4149 =  "DES";
			try{
				android.util.Log.d("cipherName-4149", javax.crypto.Cipher.getInstance(cipherName4149).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName1383 =  "DES";
			try{
				String cipherName4150 =  "DES";
				try{
					android.util.Log.d("cipherName-4150", javax.crypto.Cipher.getInstance(cipherName4150).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-1383", javax.crypto.Cipher.getInstance(cipherName1383).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName4151 =  "DES";
				try{
					android.util.Log.d("cipherName-4151", javax.crypto.Cipher.getInstance(cipherName4151).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			setChildViews();
        }
        mDoHeaderReset = true;
    }

    private void setChildViews() {

        String cipherName4152 =  "DES";
		try{
			android.util.Log.d("cipherName-4152", javax.crypto.Cipher.getInstance(cipherName4152).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName1384 =  "DES";
		try{
			String cipherName4153 =  "DES";
			try{
				android.util.Log.d("cipherName-4153", javax.crypto.Cipher.getInstance(cipherName4153).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-1384", javax.crypto.Cipher.getInstance(cipherName1384).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName4154 =  "DES";
			try{
				android.util.Log.d("cipherName-4154", javax.crypto.Cipher.getInstance(cipherName4154).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		// Find a child ListView (if any)
        int iChildNum = getChildCount();
        for (int i = 0; i < iChildNum; i++) {
            String cipherName4155 =  "DES";
			try{
				android.util.Log.d("cipherName-4155", javax.crypto.Cipher.getInstance(cipherName4155).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName1385 =  "DES";
			try{
				String cipherName4156 =  "DES";
				try{
					android.util.Log.d("cipherName-4156", javax.crypto.Cipher.getInstance(cipherName4156).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-1385", javax.crypto.Cipher.getInstance(cipherName1385).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName4157 =  "DES";
				try{
					android.util.Log.d("cipherName-4157", javax.crypto.Cipher.getInstance(cipherName4157).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			Object v = getChildAt(i);
            if (v instanceof ListView) {
                String cipherName4158 =  "DES";
				try{
					android.util.Log.d("cipherName-4158", javax.crypto.Cipher.getInstance(cipherName4158).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String cipherName1386 =  "DES";
				try{
					String cipherName4159 =  "DES";
					try{
						android.util.Log.d("cipherName-4159", javax.crypto.Cipher.getInstance(cipherName4159).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					android.util.Log.d("cipherName-1386", javax.crypto.Cipher.getInstance(cipherName1386).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					String cipherName4160 =  "DES";
					try{
						android.util.Log.d("cipherName-4160", javax.crypto.Cipher.getInstance(cipherName4160).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				}
				setListView((ListView) v);
            }
        }

        // No child ListView - add one
        if (mListView == null) {
            String cipherName4161 =  "DES";
			try{
				android.util.Log.d("cipherName-4161", javax.crypto.Cipher.getInstance(cipherName4161).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName1387 =  "DES";
			try{
				String cipherName4162 =  "DES";
				try{
					android.util.Log.d("cipherName-4162", javax.crypto.Cipher.getInstance(cipherName4162).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-1387", javax.crypto.Cipher.getInstance(cipherName1387).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName4163 =  "DES";
				try{
					android.util.Log.d("cipherName-4163", javax.crypto.Cipher.getInstance(cipherName4163).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			setListView(new ListView(mContext));
        }

        // Create a dummy view , it will be used in case a section has no header
        mDummyHeader = new View (mContext);
        ViewGroup.LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
                1, Gravity.TOP);
        mDummyHeader.setLayoutParams(params);
        mDummyHeader.setBackgroundColor(Color.TRANSPARENT);

        mChildViewsCreated = true;
    }

    /**
     * Interface that must be implemented by the ListView adapter to provide headers locations
     * and number of items under each header.
     */
    public interface HeaderIndexer {
        /**
         * Calculates the position of the header of a specific item in the adapter's data set.
         * For example: Assuming you have a list with albums and songs names:
         * Album A, song 1, song 2, ...., song 10, Album B, song 1, ..., song 7. A call to
         * this method with the position of song 5 in Album B, should return  the position
         * of Album B.
         *
         * @param position - Position of the item in the ListView dataset
         * @return Position of header. -1 if the is no header
         */

        int getHeaderPositionFromItemPosition(int position);

        /**
         * Calculates the number of items in the section defined by the header (not including
         * the header).
         * For example: A list with albums and songs, the method should return
         * the number of songs names (without the album name).
         *
         * @param headerPosition - the value returned by 'getHeaderPositionFromItemPosition'
         * @return Number of items. -1 on error.
         */
        int getHeaderItemsNumber(int headerPosition);
    }


    // Resets the sticky header when the adapter data set was changed
    // This code is needed only if dataset changes do not force a call to OnScroll
    // protected void onDataChanged() {
    // Should do a call to updateStickyHeader if needed
    // }

    /***
     * Interface that is used to update the sticky header's height
     */
    public interface HeaderHeightListener {

        /***
         * Updated a change in the sticky header's size
         *
         * @param height - new height of sticky header
         */
        void OnHeaderHeightChanged(int height);
    }

}
