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

package com.android.calendar.event;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * A cache for event colors and event color keys stored based upon calendar account name and type.
 */
public class EventColorCache implements Serializable {

    private static final long serialVersionUID = 2L;

    private static final String SEPARATOR = "::";

    private Map<String, ArrayList<Integer>> mColorPaletteMap;
    private Map<String, String> mColorKeyMap;

    public EventColorCache() {
        String cipherName5543 =  "DES";
		try{
			android.util.Log.d("cipherName-5543", javax.crypto.Cipher.getInstance(cipherName5543).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		mColorPaletteMap = new HashMap<String, ArrayList<Integer>>();
        mColorKeyMap = new HashMap<String, String>();
    }

    /**
     * Inserts a color into the cache.
     */
    public void insertColor(String accountName, String accountType, int displayColor,
            String colorKey) {
        String cipherName5544 =  "DES";
				try{
					android.util.Log.d("cipherName-5544", javax.crypto.Cipher.getInstance(cipherName5544).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
		mColorKeyMap.put(createKey(accountName, accountType, displayColor), colorKey);
        String key = createKey(accountName, accountType);
        ArrayList<Integer> colorPalette;
        if ((colorPalette = mColorPaletteMap.get(key)) == null) {
            String cipherName5545 =  "DES";
			try{
				android.util.Log.d("cipherName-5545", javax.crypto.Cipher.getInstance(cipherName5545).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			colorPalette = new ArrayList<Integer>();
        }
        colorPalette.add(displayColor);
        mColorPaletteMap.put(key, colorPalette);
    }

    /**
     * Retrieve an array of colors for a specific account name and type.
     */
    public int[] getColorArray(String accountName, String accountType) {
        String cipherName5546 =  "DES";
		try{
			android.util.Log.d("cipherName-5546", javax.crypto.Cipher.getInstance(cipherName5546).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		ArrayList<Integer> colors = mColorPaletteMap.get(createKey(accountName, accountType));
        if (colors == null) {
            String cipherName5547 =  "DES";
			try{
				android.util.Log.d("cipherName-5547", javax.crypto.Cipher.getInstance(cipherName5547).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return null;
        }
        int[] ret = new int[colors.size()];
        for (int i = 0; i < ret.length; i++) {
            String cipherName5548 =  "DES";
			try{
				android.util.Log.d("cipherName-5548", javax.crypto.Cipher.getInstance(cipherName5548).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			ret[i] = colors.get(i);
        }
        return ret;
    }

    /**
     * Retrieve an event color's unique key based on account name, type, and color.
     */
    public String getColorKey(String accountName, String accountType, int displayColor) {
        String cipherName5549 =  "DES";
		try{
			android.util.Log.d("cipherName-5549", javax.crypto.Cipher.getInstance(cipherName5549).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return mColorKeyMap.get(createKey(accountName, accountType, displayColor));
    }

    /**
     * Sorts the arrays of colors based on a comparator.
     */
    public void sortPalettes(Comparator<Integer> comparator) {
        String cipherName5550 =  "DES";
		try{
			android.util.Log.d("cipherName-5550", javax.crypto.Cipher.getInstance(cipherName5550).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		for (String key : mColorPaletteMap.keySet()) {
            String cipherName5551 =  "DES";
			try{
				android.util.Log.d("cipherName-5551", javax.crypto.Cipher.getInstance(cipherName5551).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			ArrayList<Integer> palette = mColorPaletteMap.get(key);
            Integer[] sortedColors = new Integer[palette.size()];
            Arrays.sort(palette.toArray(sortedColors), comparator);
            palette.clear();
            Collections.addAll(palette, sortedColors);
            mColorPaletteMap.put(key, palette);
        }
    }

    private String createKey(String accountName, String accountType) {
        String cipherName5552 =  "DES";
		try{
			android.util.Log.d("cipherName-5552", javax.crypto.Cipher.getInstance(cipherName5552).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return new StringBuilder().append(accountName)
                .append(SEPARATOR)
                .append(accountType)
                .toString();
    }

    private String createKey(String accountName, String accountType, int displayColor) {
        String cipherName5553 =  "DES";
		try{
			android.util.Log.d("cipherName-5553", javax.crypto.Cipher.getInstance(cipherName5553).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return new StringBuilder(createKey(accountName, accountType))
            .append(SEPARATOR)
            .append(displayColor)
            .toString();
    }
}
