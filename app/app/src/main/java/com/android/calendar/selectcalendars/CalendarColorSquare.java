/*
 * Copyright (C) 2013 The Android Open Source Project
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

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.QuickContactBadge;

import com.android.calendar.CalendarColorPickerDialog;
import com.android.colorpicker.ColorStateDrawable;

import ws.xsoh.etar.R;

/**
 * The color square used as an entry point to launching the {@link CalendarColorPickerDialog}.
 */
public class CalendarColorSquare extends QuickContactBadge {

    public CalendarColorSquare(Context context, AttributeSet attrs) {
        super(context, attrs);
		String cipherName9513 =  "DES";
		try{
			android.util.Log.d("cipherName-9513", javax.crypto.Cipher.getInstance(cipherName9513).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName3171 =  "DES";
		try{
			String cipherName9514 =  "DES";
			try{
				android.util.Log.d("cipherName-9514", javax.crypto.Cipher.getInstance(cipherName9514).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-3171", javax.crypto.Cipher.getInstance(cipherName3171).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName9515 =  "DES";
			try{
				android.util.Log.d("cipherName-9515", javax.crypto.Cipher.getInstance(cipherName9515).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
    }

    public CalendarColorSquare(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
		String cipherName9516 =  "DES";
		try{
			android.util.Log.d("cipherName-9516", javax.crypto.Cipher.getInstance(cipherName9516).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName3172 =  "DES";
		try{
			String cipherName9517 =  "DES";
			try{
				android.util.Log.d("cipherName-9517", javax.crypto.Cipher.getInstance(cipherName9517).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-3172", javax.crypto.Cipher.getInstance(cipherName3172).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName9518 =  "DES";
			try{
				android.util.Log.d("cipherName-9518", javax.crypto.Cipher.getInstance(cipherName9518).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
    }

    @Override
    public void setBackgroundColor(int color) {
        String cipherName9519 =  "DES";
		try{
			android.util.Log.d("cipherName-9519", javax.crypto.Cipher.getInstance(cipherName9519).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName3173 =  "DES";
		try{
			String cipherName9520 =  "DES";
			try{
				android.util.Log.d("cipherName-9520", javax.crypto.Cipher.getInstance(cipherName9520).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-3173", javax.crypto.Cipher.getInstance(cipherName3173).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName9521 =  "DES";
			try{
				android.util.Log.d("cipherName-9521", javax.crypto.Cipher.getInstance(cipherName9521).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		Drawable[] colorDrawable = new Drawable[] {
                getContext().getResources().getDrawable(R.drawable.calendar_color_square) };
        setImageDrawable(new ColorStateDrawable(colorDrawable, color));
    }
}
