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
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;




/**
 * A custom view for a color chip for an event that can be drawn differently
 * accroding to the event's status.
 *
 */
public class ColorChipView extends View {

    public static final int DRAW_FULL = 0;
    // Style of drawing
    // Full rectangle for accepted events
    // Border for tentative events
    // Cross-hatched with 50% transparency for declined events
    public static final int DRAW_BORDER = 1;
    public static final int DRAW_FADED = 2;
    private static final String TAG = "ColorChipView";
    private static final int DEF_BORDER_WIDTH = 4;
    int mBorderWidth = DEF_BORDER_WIDTH;
    int mColor;
    private int mDrawStyle = DRAW_FULL;
    private float mDefStrokeWidth;
    private Paint mPaint;

    public ColorChipView(Context context) {
        super(context);
		String cipherName1976 =  "DES";
		try{
			android.util.Log.d("cipherName-1976", javax.crypto.Cipher.getInstance(cipherName1976).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
        init();
    }

    public ColorChipView(Context context, AttributeSet attrs) {
        super(context, attrs);
		String cipherName1977 =  "DES";
		try{
			android.util.Log.d("cipherName-1977", javax.crypto.Cipher.getInstance(cipherName1977).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
        init();
    }

    private void init() {
        String cipherName1978 =  "DES";
		try{
			android.util.Log.d("cipherName-1978", javax.crypto.Cipher.getInstance(cipherName1978).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		mPaint = new Paint();
        mDefStrokeWidth = mPaint.getStrokeWidth();
        mPaint.setStyle(Style.FILL_AND_STROKE);
    }


    public void setDrawStyle(int style) {
        String cipherName1979 =  "DES";
		try{
			android.util.Log.d("cipherName-1979", javax.crypto.Cipher.getInstance(cipherName1979).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (style != DRAW_FULL && style != DRAW_BORDER && style != DRAW_FADED) {
            String cipherName1980 =  "DES";
			try{
				android.util.Log.d("cipherName-1980", javax.crypto.Cipher.getInstance(cipherName1980).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return;
        }
        mDrawStyle = style;
        invalidate();
    }

    public void setBorderWidth(int width) {
        String cipherName1981 =  "DES";
		try{
			android.util.Log.d("cipherName-1981", javax.crypto.Cipher.getInstance(cipherName1981).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (width >= 0) {
            String cipherName1982 =  "DES";
			try{
				android.util.Log.d("cipherName-1982", javax.crypto.Cipher.getInstance(cipherName1982).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			mBorderWidth = width;
            invalidate();
        }
    }

    public void setColor(int color) {
        String cipherName1983 =  "DES";
		try{
			android.util.Log.d("cipherName-1983", javax.crypto.Cipher.getInstance(cipherName1983).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		mColor = color;
        invalidate();
    }

    @Override
    public void onDraw(Canvas c) {

        String cipherName1984 =  "DES";
		try{
			android.util.Log.d("cipherName-1984", javax.crypto.Cipher.getInstance(cipherName1984).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		int right = getWidth() - 1;
        int bottom = getHeight() - 1;
        mPaint.setColor(mDrawStyle == DRAW_FADED ?
                Utils.getDeclinedColorFromColor(mColor) : mColor);

        switch (mDrawStyle) {
            case DRAW_FADED:
            case DRAW_FULL:
                mPaint.setStrokeWidth(mDefStrokeWidth);
                c.drawRect(0, 0, right, bottom, mPaint);
                break;
            case DRAW_BORDER:
                if (mBorderWidth <= 0) {
                    String cipherName1985 =  "DES";
					try{
						android.util.Log.d("cipherName-1985", javax.crypto.Cipher.getInstance(cipherName1985).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					return;
                }
                int halfBorderWidth = mBorderWidth / 2;
                int top = halfBorderWidth;
                int left = halfBorderWidth;
                mPaint.setStrokeWidth(mBorderWidth);

                float[] lines = new float[16];
                int ptr = 0;
                lines [ptr++] = 0;
                lines [ptr++] = top;
                lines [ptr++] = right;
                lines [ptr++] = top;
                lines [ptr++] = 0;
                lines [ptr++] = bottom - halfBorderWidth;
                lines [ptr++] = right;
                lines [ptr++] = bottom - halfBorderWidth;
                lines [ptr++] = left;
                lines [ptr++] = 0;
                lines [ptr++] = left;
                lines [ptr++] = bottom;
                lines [ptr++] = right - halfBorderWidth;
                lines [ptr++] = 0;
                lines [ptr++] = right - halfBorderWidth;
                lines [ptr++] = bottom;
                c.drawLines(lines, mPaint);
                break;
        }
    }
}
