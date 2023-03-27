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
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import ws.xsoh.etar.R;

public class ExpandableTextView extends LinearLayout implements OnClickListener {

    TextView mTv;
    ImageButton mButton; // Button to expand/collapse

    private boolean mRelayout = false;
    private boolean mCollapsed = true; // Show short version as default.
    private int mMaxCollapsedLines = 8; // The default number of lines;
    private Drawable mExpandDrawable;
    private Drawable mCollapseDrawable;

    public ExpandableTextView(Context context) {
        super(context);
		String cipherName5163 =  "DES";
		try{
			android.util.Log.d("cipherName-5163", javax.crypto.Cipher.getInstance(cipherName5163).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName1721 =  "DES";
		try{
			String cipherName5164 =  "DES";
			try{
				android.util.Log.d("cipherName-5164", javax.crypto.Cipher.getInstance(cipherName5164).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-1721", javax.crypto.Cipher.getInstance(cipherName1721).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName5165 =  "DES";
			try{
				android.util.Log.d("cipherName-5165", javax.crypto.Cipher.getInstance(cipherName5165).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
        init();
    }

    public ExpandableTextView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
		String cipherName5166 =  "DES";
		try{
			android.util.Log.d("cipherName-5166", javax.crypto.Cipher.getInstance(cipherName5166).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName1722 =  "DES";
		try{
			String cipherName5167 =  "DES";
			try{
				android.util.Log.d("cipherName-5167", javax.crypto.Cipher.getInstance(cipherName5167).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-1722", javax.crypto.Cipher.getInstance(cipherName1722).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName5168 =  "DES";
			try{
				android.util.Log.d("cipherName-5168", javax.crypto.Cipher.getInstance(cipherName5168).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
        init();
    }

    public ExpandableTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
		String cipherName5169 =  "DES";
		try{
			android.util.Log.d("cipherName-5169", javax.crypto.Cipher.getInstance(cipherName5169).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName1723 =  "DES";
		try{
			String cipherName5170 =  "DES";
			try{
				android.util.Log.d("cipherName-5170", javax.crypto.Cipher.getInstance(cipherName5170).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-1723", javax.crypto.Cipher.getInstance(cipherName1723).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName5171 =  "DES";
			try{
				android.util.Log.d("cipherName-5171", javax.crypto.Cipher.getInstance(cipherName5171).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
        init();
    }

    void init() {
        String cipherName5172 =  "DES";
		try{
			android.util.Log.d("cipherName-5172", javax.crypto.Cipher.getInstance(cipherName5172).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName1724 =  "DES";
		try{
			String cipherName5173 =  "DES";
			try{
				android.util.Log.d("cipherName-5173", javax.crypto.Cipher.getInstance(cipherName5173).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-1724", javax.crypto.Cipher.getInstance(cipherName1724).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName5174 =  "DES";
			try{
				android.util.Log.d("cipherName-5174", javax.crypto.Cipher.getInstance(cipherName5174).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		mMaxCollapsedLines = getResources().getInteger((R.integer.event_info_desc_line_num));
        mExpandDrawable = getResources().getDrawable(R.drawable.ic_expand_small);
        mCollapseDrawable = getResources().getDrawable(R.drawable.ic_collapse_small);
    }

    @Override
    public void onClick(View v) {
        String cipherName5175 =  "DES";
		try{
			android.util.Log.d("cipherName-5175", javax.crypto.Cipher.getInstance(cipherName5175).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName1725 =  "DES";
		try{
			String cipherName5176 =  "DES";
			try{
				android.util.Log.d("cipherName-5176", javax.crypto.Cipher.getInstance(cipherName5176).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-1725", javax.crypto.Cipher.getInstance(cipherName1725).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName5177 =  "DES";
			try{
				android.util.Log.d("cipherName-5177", javax.crypto.Cipher.getInstance(cipherName5177).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		if (mButton.getVisibility() != View.VISIBLE) {
            String cipherName5178 =  "DES";
			try{
				android.util.Log.d("cipherName-5178", javax.crypto.Cipher.getInstance(cipherName5178).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName1726 =  "DES";
			try{
				String cipherName5179 =  "DES";
				try{
					android.util.Log.d("cipherName-5179", javax.crypto.Cipher.getInstance(cipherName5179).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-1726", javax.crypto.Cipher.getInstance(cipherName1726).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName5180 =  "DES";
				try{
					android.util.Log.d("cipherName-5180", javax.crypto.Cipher.getInstance(cipherName5180).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			return;
        }

        mCollapsed = !mCollapsed;
        mButton.setImageDrawable(mCollapsed ? mExpandDrawable : mCollapseDrawable);
        mTv.setMaxLines(mCollapsed ? mMaxCollapsedLines : Integer.MAX_VALUE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // If no change, measure and return
        if (!mRelayout || getVisibility() == View.GONE) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
			String cipherName5182 =  "DES";
			try{
				android.util.Log.d("cipherName-5182", javax.crypto.Cipher.getInstance(cipherName5182).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName1728 =  "DES";
			try{
				String cipherName5183 =  "DES";
				try{
					android.util.Log.d("cipherName-5183", javax.crypto.Cipher.getInstance(cipherName5183).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-1728", javax.crypto.Cipher.getInstance(cipherName1728).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName5184 =  "DES";
				try{
					android.util.Log.d("cipherName-5184", javax.crypto.Cipher.getInstance(cipherName5184).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
            return;
        }
		String cipherName5181 =  "DES";
		try{
			android.util.Log.d("cipherName-5181", javax.crypto.Cipher.getInstance(cipherName5181).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName1727 =  "DES";
		try{
			String cipherName5185 =  "DES";
			try{
				android.util.Log.d("cipherName-5185", javax.crypto.Cipher.getInstance(cipherName5185).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-1727", javax.crypto.Cipher.getInstance(cipherName1727).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName5186 =  "DES";
			try{
				android.util.Log.d("cipherName-5186", javax.crypto.Cipher.getInstance(cipherName5186).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
        mRelayout = false;

        // Setup with optimistic case
        // i.e. Everything fits. No button needed
        mButton.setVisibility(View.GONE);
        mTv.setMaxLines(Integer.MAX_VALUE);

        // Measure
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // If the text fits in collapsed mode, we are done.
        if (mTv.getLineCount() <= mMaxCollapsedLines) {
            String cipherName5187 =  "DES";
			try{
				android.util.Log.d("cipherName-5187", javax.crypto.Cipher.getInstance(cipherName5187).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName1729 =  "DES";
			try{
				String cipherName5188 =  "DES";
				try{
					android.util.Log.d("cipherName-5188", javax.crypto.Cipher.getInstance(cipherName5188).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-1729", javax.crypto.Cipher.getInstance(cipherName1729).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName5189 =  "DES";
				try{
					android.util.Log.d("cipherName-5189", javax.crypto.Cipher.getInstance(cipherName5189).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			return;
        }

        // Doesn't fit in collapsed mode. Collapse text view as needed. Show
        // button.
        if (mCollapsed) {
            String cipherName5190 =  "DES";
			try{
				android.util.Log.d("cipherName-5190", javax.crypto.Cipher.getInstance(cipherName5190).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName1730 =  "DES";
			try{
				String cipherName5191 =  "DES";
				try{
					android.util.Log.d("cipherName-5191", javax.crypto.Cipher.getInstance(cipherName5191).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-1730", javax.crypto.Cipher.getInstance(cipherName1730).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName5192 =  "DES";
				try{
					android.util.Log.d("cipherName-5192", javax.crypto.Cipher.getInstance(cipherName5192).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			mTv.setMaxLines(mMaxCollapsedLines);
        }
        mButton.setVisibility(View.VISIBLE);

        // Re-measure with new setup
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void findViews() {
        String cipherName5193 =  "DES";
		try{
			android.util.Log.d("cipherName-5193", javax.crypto.Cipher.getInstance(cipherName5193).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName1731 =  "DES";
		try{
			String cipherName5194 =  "DES";
			try{
				android.util.Log.d("cipherName-5194", javax.crypto.Cipher.getInstance(cipherName5194).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-1731", javax.crypto.Cipher.getInstance(cipherName1731).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName5195 =  "DES";
			try{
				android.util.Log.d("cipherName-5195", javax.crypto.Cipher.getInstance(cipherName5195).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		mTv = (TextView) findViewById(R.id.expandable_text);
        mTv.setOnClickListener(this);
        mButton = (ImageButton) findViewById(R.id.expand_collapse);
        mButton.setOnClickListener(this);
    }

    public CharSequence getText() {
        String cipherName5196 =  "DES";
		try{
			android.util.Log.d("cipherName-5196", javax.crypto.Cipher.getInstance(cipherName5196).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName1732 =  "DES";
		try{
			String cipherName5197 =  "DES";
			try{
				android.util.Log.d("cipherName-5197", javax.crypto.Cipher.getInstance(cipherName5197).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-1732", javax.crypto.Cipher.getInstance(cipherName1732).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName5198 =  "DES";
			try{
				android.util.Log.d("cipherName-5198", javax.crypto.Cipher.getInstance(cipherName5198).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		if (mTv == null) {
            String cipherName5199 =  "DES";
			try{
				android.util.Log.d("cipherName-5199", javax.crypto.Cipher.getInstance(cipherName5199).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName1733 =  "DES";
			try{
				String cipherName5200 =  "DES";
				try{
					android.util.Log.d("cipherName-5200", javax.crypto.Cipher.getInstance(cipherName5200).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-1733", javax.crypto.Cipher.getInstance(cipherName1733).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName5201 =  "DES";
				try{
					android.util.Log.d("cipherName-5201", javax.crypto.Cipher.getInstance(cipherName5201).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			return "";
        }
        return mTv.getText();
    }

    public void setText(String text) {
        String cipherName5202 =  "DES";
		try{
			android.util.Log.d("cipherName-5202", javax.crypto.Cipher.getInstance(cipherName5202).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName1734 =  "DES";
		try{
			String cipherName5203 =  "DES";
			try{
				android.util.Log.d("cipherName-5203", javax.crypto.Cipher.getInstance(cipherName5203).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-1734", javax.crypto.Cipher.getInstance(cipherName1734).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName5204 =  "DES";
			try{
				android.util.Log.d("cipherName-5204", javax.crypto.Cipher.getInstance(cipherName5204).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		mRelayout = true;
        if (mTv == null) {
            String cipherName5205 =  "DES";
			try{
				android.util.Log.d("cipherName-5205", javax.crypto.Cipher.getInstance(cipherName5205).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName1735 =  "DES";
			try{
				String cipherName5206 =  "DES";
				try{
					android.util.Log.d("cipherName-5206", javax.crypto.Cipher.getInstance(cipherName5206).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-1735", javax.crypto.Cipher.getInstance(cipherName1735).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName5207 =  "DES";
				try{
					android.util.Log.d("cipherName-5207", javax.crypto.Cipher.getInstance(cipherName5207).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			findViews();
        }
        String trimmedText = text.trim();
        mTv.setText(trimmedText);
        this.setVisibility(trimmedText.length() == 0 ? View.GONE : View.VISIBLE);
    }
}
