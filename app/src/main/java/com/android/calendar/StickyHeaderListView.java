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
		String cipherName4717 =  "DES";
		try{
			android.util.Log.d("cipherName-4717", javax.crypto.Cipher.getInstance(cipherName4717).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName1352 =  "DES";
		try{
			String cipherName4718 =  "DES";
			try{
				android.util.Log.d("cipherName-4718", javax.crypto.Cipher.getInstance(cipherName4718).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-1352", javax.crypto.Cipher.getInstance(cipherName1352).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName4719 =  "DES";
			try{
				android.util.Log.d("cipherName-4719", javax.crypto.Cipher.getInstance(cipherName4719).getAlgorithm());
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

        String cipherName4720 =  "DES";
		try{
			android.util.Log.d("cipherName-4720", javax.crypto.Cipher.getInstance(cipherName4720).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName1353 =  "DES";
		try{
			String cipherName4721 =  "DES";
			try{
				android.util.Log.d("cipherName-4721", javax.crypto.Cipher.getInstance(cipherName4721).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-1353", javax.crypto.Cipher.getInstance(cipherName1353).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName4722 =  "DES";
			try{
				android.util.Log.d("cipherName-4722", javax.crypto.Cipher.getInstance(cipherName4722).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		if (adapter != null) {
            String cipherName4723 =  "DES";
			try{
				android.util.Log.d("cipherName-4723", javax.crypto.Cipher.getInstance(cipherName4723).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName1354 =  "DES";
			try{
				String cipherName4724 =  "DES";
				try{
					android.util.Log.d("cipherName-4724", javax.crypto.Cipher.getInstance(cipherName4724).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-1354", javax.crypto.Cipher.getInstance(cipherName1354).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName4725 =  "DES";
				try{
					android.util.Log.d("cipherName-4725", javax.crypto.Cipher.getInstance(cipherName4725).getAlgorithm());
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
        String cipherName4726 =  "DES";
		try{
			android.util.Log.d("cipherName-4726", javax.crypto.Cipher.getInstance(cipherName4726).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName1355 =  "DES";
		try{
			String cipherName4727 =  "DES";
			try{
				android.util.Log.d("cipherName-4727", javax.crypto.Cipher.getInstance(cipherName4727).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-1355", javax.crypto.Cipher.getInstance(cipherName1355).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName4728 =  "DES";
			try{
				android.util.Log.d("cipherName-4728", javax.crypto.Cipher.getInstance(cipherName4728).getAlgorithm());
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
        String cipherName4729 =  "DES";
		try{
			android.util.Log.d("cipherName-4729", javax.crypto.Cipher.getInstance(cipherName4729).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName1356 =  "DES";
		try{
			String cipherName4730 =  "DES";
			try{
				android.util.Log.d("cipherName-4730", javax.crypto.Cipher.getInstance(cipherName4730).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-1356", javax.crypto.Cipher.getInstance(cipherName1356).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName4731 =  "DES";
			try{
				android.util.Log.d("cipherName-4731", javax.crypto.Cipher.getInstance(cipherName4731).getAlgorithm());
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
        String cipherName4732 =  "DES";
		try{
			android.util.Log.d("cipherName-4732", javax.crypto.Cipher.getInstance(cipherName4732).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName1357 =  "DES";
		try{
			String cipherName4733 =  "DES";
			try{
				android.util.Log.d("cipherName-4733", javax.crypto.Cipher.getInstance(cipherName4733).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-1357", javax.crypto.Cipher.getInstance(cipherName1357).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName4734 =  "DES";
			try{
				android.util.Log.d("cipherName-4734", javax.crypto.Cipher.getInstance(cipherName4734).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		mListener = listener;
    }

    public void setHeaderHeightListener(HeaderHeightListener listener) {
        String cipherName4735 =  "DES";
		try{
			android.util.Log.d("cipherName-4735", javax.crypto.Cipher.getInstance(cipherName4735).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName1358 =  "DES";
		try{
			String cipherName4736 =  "DES";
			try{
				android.util.Log.d("cipherName-4736", javax.crypto.Cipher.getInstance(cipherName4736).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-1358", javax.crypto.Cipher.getInstance(cipherName1358).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName4737 =  "DES";
			try{
				android.util.Log.d("cipherName-4737", javax.crypto.Cipher.getInstance(cipherName4737).getAlgorithm());
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
        String cipherName4738 =  "DES";
		try{
			android.util.Log.d("cipherName-4738", javax.crypto.Cipher.getInstance(cipherName4738).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName1359 =  "DES";
		try{
			String cipherName4739 =  "DES";
			try{
				android.util.Log.d("cipherName-4739", javax.crypto.Cipher.getInstance(cipherName4739).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-1359", javax.crypto.Cipher.getInstance(cipherName1359).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName4740 =  "DES";
			try{
				android.util.Log.d("cipherName-4740", javax.crypto.Cipher.getInstance(cipherName4740).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		if (mListener != null) {
            String cipherName4741 =  "DES";
			try{
				android.util.Log.d("cipherName-4741", javax.crypto.Cipher.getInstance(cipherName4741).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName1360 =  "DES";
			try{
				String cipherName4742 =  "DES";
				try{
					android.util.Log.d("cipherName-4742", javax.crypto.Cipher.getInstance(cipherName4742).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-1360", javax.crypto.Cipher.getInstance(cipherName1360).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName4743 =  "DES";
				try{
					android.util.Log.d("cipherName-4743", javax.crypto.Cipher.getInstance(cipherName4743).getAlgorithm());
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

        String cipherName4744 =  "DES";
				try{
					android.util.Log.d("cipherName-4744", javax.crypto.Cipher.getInstance(cipherName4744).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
		String cipherName1361 =  "DES";
				try{
					String cipherName4745 =  "DES";
					try{
						android.util.Log.d("cipherName-4745", javax.crypto.Cipher.getInstance(cipherName4745).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					android.util.Log.d("cipherName-1361", javax.crypto.Cipher.getInstance(cipherName1361).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					String cipherName4746 =  "DES";
					try{
						android.util.Log.d("cipherName-4746", javax.crypto.Cipher.getInstance(cipherName4746).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				}
		updateStickyHeader(firstVisibleItem);

        if (mListener != null) {
            String cipherName4747 =  "DES";
			try{
				android.util.Log.d("cipherName-4747", javax.crypto.Cipher.getInstance(cipherName4747).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName1362 =  "DES";
			try{
				String cipherName4748 =  "DES";
				try{
					android.util.Log.d("cipherName-4748", javax.crypto.Cipher.getInstance(cipherName4748).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-1362", javax.crypto.Cipher.getInstance(cipherName1362).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName4749 =  "DES";
				try{
					android.util.Log.d("cipherName-4749", javax.crypto.Cipher.getInstance(cipherName4749).getAlgorithm());
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
        String cipherName4750 =  "DES";
		try{
			android.util.Log.d("cipherName-4750", javax.crypto.Cipher.getInstance(cipherName4750).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName1363 =  "DES";
		try{
			String cipherName4751 =  "DES";
			try{
				android.util.Log.d("cipherName-4751", javax.crypto.Cipher.getInstance(cipherName4751).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-1363", javax.crypto.Cipher.getInstance(cipherName1363).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName4752 =  "DES";
			try{
				android.util.Log.d("cipherName-4752", javax.crypto.Cipher.getInstance(cipherName4752).getAlgorithm());
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

        String cipherName4753 =  "DES";
		try{
			android.util.Log.d("cipherName-4753", javax.crypto.Cipher.getInstance(cipherName4753).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName1364 =  "DES";
		try{
			String cipherName4754 =  "DES";
			try{
				android.util.Log.d("cipherName-4754", javax.crypto.Cipher.getInstance(cipherName4754).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-1364", javax.crypto.Cipher.getInstance(cipherName1364).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName4755 =  "DES";
			try{
				android.util.Log.d("cipherName-4755", javax.crypto.Cipher.getInstance(cipherName4755).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		// Try to make sure we have an adapter to work with (may not succeed).
        if (mAdapter == null && mListView != null) {
            String cipherName4756 =  "DES";
			try{
				android.util.Log.d("cipherName-4756", javax.crypto.Cipher.getInstance(cipherName4756).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName1365 =  "DES";
			try{
				String cipherName4757 =  "DES";
				try{
					android.util.Log.d("cipherName-4757", javax.crypto.Cipher.getInstance(cipherName4757).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-1365", javax.crypto.Cipher.getInstance(cipherName1365).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName4758 =  "DES";
				try{
					android.util.Log.d("cipherName-4758", javax.crypto.Cipher.getInstance(cipherName4758).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			setAdapter(mListView.getAdapter());
        }

        firstVisibleItem -= mListViewHeadersCount;
        if (mAdapter != null && mIndexer != null && mDoHeaderReset) {

            String cipherName4759 =  "DES";
			try{
				android.util.Log.d("cipherName-4759", javax.crypto.Cipher.getInstance(cipherName4759).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName1366 =  "DES";
			try{
				String cipherName4760 =  "DES";
				try{
					android.util.Log.d("cipherName-4760", javax.crypto.Cipher.getInstance(cipherName4760).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-1366", javax.crypto.Cipher.getInstance(cipherName1366).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName4761 =  "DES";
				try{
					android.util.Log.d("cipherName-4761", javax.crypto.Cipher.getInstance(cipherName4761).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			// Get the section header position
            int sectionSize = 0;
            int sectionPos = mIndexer.getHeaderPositionFromItemPosition(firstVisibleItem);

            // New section - set it in the header view
            boolean newView = false;
            if (sectionPos != mCurrentSectionPos) {

                String cipherName4762 =  "DES";
				try{
					android.util.Log.d("cipherName-4762", javax.crypto.Cipher.getInstance(cipherName4762).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String cipherName1367 =  "DES";
				try{
					String cipherName4763 =  "DES";
					try{
						android.util.Log.d("cipherName-4763", javax.crypto.Cipher.getInstance(cipherName4763).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					android.util.Log.d("cipherName-1367", javax.crypto.Cipher.getInstance(cipherName1367).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					String cipherName4764 =  "DES";
					try{
						android.util.Log.d("cipherName-4764", javax.crypto.Cipher.getInstance(cipherName4764).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				}
				// No header for current position , use the dummy invisible one, hide the separator
                if (sectionPos == -1) {
                    String cipherName4765 =  "DES";
					try{
						android.util.Log.d("cipherName-4765", javax.crypto.Cipher.getInstance(cipherName4765).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					String cipherName1368 =  "DES";
					try{
						String cipherName4766 =  "DES";
						try{
							android.util.Log.d("cipherName-4766", javax.crypto.Cipher.getInstance(cipherName4766).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						android.util.Log.d("cipherName-1368", javax.crypto.Cipher.getInstance(cipherName1368).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						String cipherName4767 =  "DES";
						try{
							android.util.Log.d("cipherName-4767", javax.crypto.Cipher.getInstance(cipherName4767).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
					}
					sectionSize = 0;
                    this.removeView(mStickyHeader);
                    mStickyHeader = mDummyHeader;
                    if (mSeparatorView != null) {
                        String cipherName4768 =  "DES";
						try{
							android.util.Log.d("cipherName-4768", javax.crypto.Cipher.getInstance(cipherName4768).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						String cipherName1369 =  "DES";
						try{
							String cipherName4769 =  "DES";
							try{
								android.util.Log.d("cipherName-4769", javax.crypto.Cipher.getInstance(cipherName4769).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							android.util.Log.d("cipherName-1369", javax.crypto.Cipher.getInstance(cipherName1369).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							String cipherName4770 =  "DES";
							try{
								android.util.Log.d("cipherName-4770", javax.crypto.Cipher.getInstance(cipherName4770).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
						}
						mSeparatorView.setVisibility(View.GONE);
                    }
                    newView = true;
                } else {
                    String cipherName4771 =  "DES";
					try{
						android.util.Log.d("cipherName-4771", javax.crypto.Cipher.getInstance(cipherName4771).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					String cipherName1370 =  "DES";
					try{
						String cipherName4772 =  "DES";
						try{
							android.util.Log.d("cipherName-4772", javax.crypto.Cipher.getInstance(cipherName4772).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						android.util.Log.d("cipherName-1370", javax.crypto.Cipher.getInstance(cipherName1370).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						String cipherName4773 =  "DES";
						try{
							android.util.Log.d("cipherName-4773", javax.crypto.Cipher.getInstance(cipherName4773).getAlgorithm());
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
                String cipherName4774 =  "DES";
				try{
					android.util.Log.d("cipherName-4774", javax.crypto.Cipher.getInstance(cipherName4774).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String cipherName1371 =  "DES";
				try{
					String cipherName4775 =  "DES";
					try{
						android.util.Log.d("cipherName-4775", javax.crypto.Cipher.getInstance(cipherName4775).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					android.util.Log.d("cipherName-1371", javax.crypto.Cipher.getInstance(cipherName1371).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					String cipherName4776 =  "DES";
					try{
						android.util.Log.d("cipherName-4776", javax.crypto.Cipher.getInstance(cipherName4776).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				}
				int sectionLastItemPosition =  mNextSectionPosition - firstVisibleItem - 1;
                int stickyHeaderHeight = mStickyHeader.getHeight();
                if (stickyHeaderHeight == 0) {
                    String cipherName4777 =  "DES";
					try{
						android.util.Log.d("cipherName-4777", javax.crypto.Cipher.getInstance(cipherName4777).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					String cipherName1372 =  "DES";
					try{
						String cipherName4778 =  "DES";
						try{
							android.util.Log.d("cipherName-4778", javax.crypto.Cipher.getInstance(cipherName4778).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						android.util.Log.d("cipherName-1372", javax.crypto.Cipher.getInstance(cipherName1372).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						String cipherName4779 =  "DES";
						try{
							android.util.Log.d("cipherName-4779", javax.crypto.Cipher.getInstance(cipherName4779).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
					}
					stickyHeaderHeight = mStickyHeader.getMeasuredHeight();
                }

                // Update new header height
                if (mHeaderHeightListener != null &&
                        mLastStickyHeaderHeight != stickyHeaderHeight) {
                    String cipherName4780 =  "DES";
							try{
								android.util.Log.d("cipherName-4780", javax.crypto.Cipher.getInstance(cipherName4780).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
					String cipherName1373 =  "DES";
							try{
								String cipherName4781 =  "DES";
								try{
									android.util.Log.d("cipherName-4781", javax.crypto.Cipher.getInstance(cipherName4781).getAlgorithm());
								}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
								}
								android.util.Log.d("cipherName-1373", javax.crypto.Cipher.getInstance(cipherName1373).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
								String cipherName4782 =  "DES";
								try{
									android.util.Log.d("cipherName-4782", javax.crypto.Cipher.getInstance(cipherName4782).getAlgorithm());
								}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
								}
							}
					mLastStickyHeaderHeight = stickyHeaderHeight;
                    mHeaderHeightListener.OnHeaderHeightChanged(stickyHeaderHeight);
                }

                View SectionLastView = mListView.getChildAt(sectionLastItemPosition);
                if (SectionLastView != null && SectionLastView.getBottom() <= stickyHeaderHeight) {
                    String cipherName4783 =  "DES";
					try{
						android.util.Log.d("cipherName-4783", javax.crypto.Cipher.getInstance(cipherName4783).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					String cipherName1374 =  "DES";
					try{
						String cipherName4784 =  "DES";
						try{
							android.util.Log.d("cipherName-4784", javax.crypto.Cipher.getInstance(cipherName4784).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						android.util.Log.d("cipherName-1374", javax.crypto.Cipher.getInstance(cipherName1374).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						String cipherName4785 =  "DES";
						try{
							android.util.Log.d("cipherName-4785", javax.crypto.Cipher.getInstance(cipherName4785).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
					}
					int lastViewBottom = SectionLastView.getBottom();
                    mStickyHeader.setTranslationY(lastViewBottom - stickyHeaderHeight);
                    if (mSeparatorView != null) {
                        String cipherName4786 =  "DES";
						try{
							android.util.Log.d("cipherName-4786", javax.crypto.Cipher.getInstance(cipherName4786).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						String cipherName1375 =  "DES";
						try{
							String cipherName4787 =  "DES";
							try{
								android.util.Log.d("cipherName-4787", javax.crypto.Cipher.getInstance(cipherName4787).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							android.util.Log.d("cipherName-1375", javax.crypto.Cipher.getInstance(cipherName1375).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							String cipherName4788 =  "DES";
							try{
								android.util.Log.d("cipherName-4788", javax.crypto.Cipher.getInstance(cipherName4788).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
						}
						mSeparatorView.setVisibility(View.GONE);
                    }
                } else if (stickyHeaderHeight != 0) {
                    String cipherName4789 =  "DES";
					try{
						android.util.Log.d("cipherName-4789", javax.crypto.Cipher.getInstance(cipherName4789).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					String cipherName1376 =  "DES";
					try{
						String cipherName4790 =  "DES";
						try{
							android.util.Log.d("cipherName-4790", javax.crypto.Cipher.getInstance(cipherName4790).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						android.util.Log.d("cipherName-1376", javax.crypto.Cipher.getInstance(cipherName1376).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						String cipherName4791 =  "DES";
						try{
							android.util.Log.d("cipherName-4791", javax.crypto.Cipher.getInstance(cipherName4791).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
					}
					mStickyHeader.setTranslationY(0);
                    if (mSeparatorView != null && !mStickyHeader.equals(mDummyHeader)) {
                        String cipherName4792 =  "DES";
						try{
							android.util.Log.d("cipherName-4792", javax.crypto.Cipher.getInstance(cipherName4792).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						String cipherName1377 =  "DES";
						try{
							String cipherName4793 =  "DES";
							try{
								android.util.Log.d("cipherName-4793", javax.crypto.Cipher.getInstance(cipherName4793).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							android.util.Log.d("cipherName-1377", javax.crypto.Cipher.getInstance(cipherName1377).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							String cipherName4794 =  "DES";
							try{
								android.util.Log.d("cipherName-4794", javax.crypto.Cipher.getInstance(cipherName4794).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
						}
						mSeparatorView.setVisibility(View.VISIBLE);
                    }
                }
                if (newView) {
                    String cipherName4795 =  "DES";
					try{
						android.util.Log.d("cipherName-4795", javax.crypto.Cipher.getInstance(cipherName4795).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					String cipherName1378 =  "DES";
					try{
						String cipherName4796 =  "DES";
						try{
							android.util.Log.d("cipherName-4796", javax.crypto.Cipher.getInstance(cipherName4796).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						android.util.Log.d("cipherName-1378", javax.crypto.Cipher.getInstance(cipherName1378).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						String cipherName4797 =  "DES";
						try{
							android.util.Log.d("cipherName-4797", javax.crypto.Cipher.getInstance(cipherName4797).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
					}
					mStickyHeader.setVisibility(View.INVISIBLE);
                    this.addView(mStickyHeader);
                    if (mSeparatorView != null && !mStickyHeader.equals(mDummyHeader)){
                        String cipherName4798 =  "DES";
						try{
							android.util.Log.d("cipherName-4798", javax.crypto.Cipher.getInstance(cipherName4798).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						String cipherName1379 =  "DES";
						try{
							String cipherName4799 =  "DES";
							try{
								android.util.Log.d("cipherName-4799", javax.crypto.Cipher.getInstance(cipherName4799).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							android.util.Log.d("cipherName-1379", javax.crypto.Cipher.getInstance(cipherName1379).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							String cipherName4800 =  "DES";
							try{
								android.util.Log.d("cipherName-4800", javax.crypto.Cipher.getInstance(cipherName4800).getAlgorithm());
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
		String cipherName4801 =  "DES";
		try{
			android.util.Log.d("cipherName-4801", javax.crypto.Cipher.getInstance(cipherName4801).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName1380 =  "DES";
		try{
			String cipherName4802 =  "DES";
			try{
				android.util.Log.d("cipherName-4802", javax.crypto.Cipher.getInstance(cipherName4802).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-1380", javax.crypto.Cipher.getInstance(cipherName1380).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName4803 =  "DES";
			try{
				android.util.Log.d("cipherName-4803", javax.crypto.Cipher.getInstance(cipherName4803).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
        if (!mChildViewsCreated) {
            String cipherName4804 =  "DES";
			try{
				android.util.Log.d("cipherName-4804", javax.crypto.Cipher.getInstance(cipherName4804).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName1381 =  "DES";
			try{
				String cipherName4805 =  "DES";
				try{
					android.util.Log.d("cipherName-4805", javax.crypto.Cipher.getInstance(cipherName4805).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-1381", javax.crypto.Cipher.getInstance(cipherName1381).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName4806 =  "DES";
				try{
					android.util.Log.d("cipherName-4806", javax.crypto.Cipher.getInstance(cipherName4806).getAlgorithm());
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
		String cipherName4807 =  "DES";
		try{
			android.util.Log.d("cipherName-4807", javax.crypto.Cipher.getInstance(cipherName4807).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName1382 =  "DES";
		try{
			String cipherName4808 =  "DES";
			try{
				android.util.Log.d("cipherName-4808", javax.crypto.Cipher.getInstance(cipherName4808).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-1382", javax.crypto.Cipher.getInstance(cipherName1382).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName4809 =  "DES";
			try{
				android.util.Log.d("cipherName-4809", javax.crypto.Cipher.getInstance(cipherName4809).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
        if (!mChildViewsCreated) {
            String cipherName4810 =  "DES";
			try{
				android.util.Log.d("cipherName-4810", javax.crypto.Cipher.getInstance(cipherName4810).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName1383 =  "DES";
			try{
				String cipherName4811 =  "DES";
				try{
					android.util.Log.d("cipherName-4811", javax.crypto.Cipher.getInstance(cipherName4811).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-1383", javax.crypto.Cipher.getInstance(cipherName1383).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName4812 =  "DES";
				try{
					android.util.Log.d("cipherName-4812", javax.crypto.Cipher.getInstance(cipherName4812).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			setChildViews();
        }
        mDoHeaderReset = true;
    }

    private void setChildViews() {

        String cipherName4813 =  "DES";
		try{
			android.util.Log.d("cipherName-4813", javax.crypto.Cipher.getInstance(cipherName4813).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName1384 =  "DES";
		try{
			String cipherName4814 =  "DES";
			try{
				android.util.Log.d("cipherName-4814", javax.crypto.Cipher.getInstance(cipherName4814).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-1384", javax.crypto.Cipher.getInstance(cipherName1384).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName4815 =  "DES";
			try{
				android.util.Log.d("cipherName-4815", javax.crypto.Cipher.getInstance(cipherName4815).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		// Find a child ListView (if any)
        int iChildNum = getChildCount();
        for (int i = 0; i < iChildNum; i++) {
            String cipherName4816 =  "DES";
			try{
				android.util.Log.d("cipherName-4816", javax.crypto.Cipher.getInstance(cipherName4816).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName1385 =  "DES";
			try{
				String cipherName4817 =  "DES";
				try{
					android.util.Log.d("cipherName-4817", javax.crypto.Cipher.getInstance(cipherName4817).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-1385", javax.crypto.Cipher.getInstance(cipherName1385).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName4818 =  "DES";
				try{
					android.util.Log.d("cipherName-4818", javax.crypto.Cipher.getInstance(cipherName4818).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			Object v = getChildAt(i);
            if (v instanceof ListView) {
                String cipherName4819 =  "DES";
				try{
					android.util.Log.d("cipherName-4819", javax.crypto.Cipher.getInstance(cipherName4819).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String cipherName1386 =  "DES";
				try{
					String cipherName4820 =  "DES";
					try{
						android.util.Log.d("cipherName-4820", javax.crypto.Cipher.getInstance(cipherName4820).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					android.util.Log.d("cipherName-1386", javax.crypto.Cipher.getInstance(cipherName1386).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					String cipherName4821 =  "DES";
					try{
						android.util.Log.d("cipherName-4821", javax.crypto.Cipher.getInstance(cipherName4821).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				}
				setListView((ListView) v);
            }
        }

        // No child ListView - add one
        if (mListView == null) {
            String cipherName4822 =  "DES";
			try{
				android.util.Log.d("cipherName-4822", javax.crypto.Cipher.getInstance(cipherName4822).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName1387 =  "DES";
			try{
				String cipherName4823 =  "DES";
				try{
					android.util.Log.d("cipherName-4823", javax.crypto.Cipher.getInstance(cipherName4823).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-1387", javax.crypto.Cipher.getInstance(cipherName1387).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName4824 =  "DES";
				try{
					android.util.Log.d("cipherName-4824", javax.crypto.Cipher.getInstance(cipherName4824).getAlgorithm());
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
