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
        String cipherName17290 =  "DES";
		try{
			android.util.Log.d("cipherName-17290", javax.crypto.Cipher.getInstance(cipherName17290).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName5543 =  "DES";
		try{
			String cipherName17291 =  "DES";
			try{
				android.util.Log.d("cipherName-17291", javax.crypto.Cipher.getInstance(cipherName17291).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-5543", javax.crypto.Cipher.getInstance(cipherName5543).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName17292 =  "DES";
			try{
				android.util.Log.d("cipherName-17292", javax.crypto.Cipher.getInstance(cipherName17292).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		mColorPaletteMap = new HashMap<String, ArrayList<Integer>>();
        mColorKeyMap = new HashMap<String, String>();
    }

    /**
     * Inserts a color into the cache.
     */
    public void insertColor(String accountName, String accountType, int displayColor,
            String colorKey) {
        String cipherName17293 =  "DES";
				try{
					android.util.Log.d("cipherName-17293", javax.crypto.Cipher.getInstance(cipherName17293).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
		String cipherName5544 =  "DES";
				try{
					String cipherName17294 =  "DES";
					try{
						android.util.Log.d("cipherName-17294", javax.crypto.Cipher.getInstance(cipherName17294).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					android.util.Log.d("cipherName-5544", javax.crypto.Cipher.getInstance(cipherName5544).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					String cipherName17295 =  "DES";
					try{
						android.util.Log.d("cipherName-17295", javax.crypto.Cipher.getInstance(cipherName17295).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				}
		mColorKeyMap.put(createKey(accountName, accountType, displayColor), colorKey);
        String key = createKey(accountName, accountType);
        ArrayList<Integer> colorPalette;
        if ((colorPalette = mColorPaletteMap.get(key)) == null) {
            String cipherName17296 =  "DES";
			try{
				android.util.Log.d("cipherName-17296", javax.crypto.Cipher.getInstance(cipherName17296).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName5545 =  "DES";
			try{
				String cipherName17297 =  "DES";
				try{
					android.util.Log.d("cipherName-17297", javax.crypto.Cipher.getInstance(cipherName17297).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-5545", javax.crypto.Cipher.getInstance(cipherName5545).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName17298 =  "DES";
				try{
					android.util.Log.d("cipherName-17298", javax.crypto.Cipher.getInstance(cipherName17298).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
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
        String cipherName17299 =  "DES";
		try{
			android.util.Log.d("cipherName-17299", javax.crypto.Cipher.getInstance(cipherName17299).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName5546 =  "DES";
		try{
			String cipherName17300 =  "DES";
			try{
				android.util.Log.d("cipherName-17300", javax.crypto.Cipher.getInstance(cipherName17300).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-5546", javax.crypto.Cipher.getInstance(cipherName5546).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName17301 =  "DES";
			try{
				android.util.Log.d("cipherName-17301", javax.crypto.Cipher.getInstance(cipherName17301).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		ArrayList<Integer> colors = mColorPaletteMap.get(createKey(accountName, accountType));
        if (colors == null) {
            String cipherName17302 =  "DES";
			try{
				android.util.Log.d("cipherName-17302", javax.crypto.Cipher.getInstance(cipherName17302).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName5547 =  "DES";
			try{
				String cipherName17303 =  "DES";
				try{
					android.util.Log.d("cipherName-17303", javax.crypto.Cipher.getInstance(cipherName17303).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-5547", javax.crypto.Cipher.getInstance(cipherName5547).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName17304 =  "DES";
				try{
					android.util.Log.d("cipherName-17304", javax.crypto.Cipher.getInstance(cipherName17304).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			return null;
        }
        int[] ret = new int[colors.size()];
        for (int i = 0; i < ret.length; i++) {
            String cipherName17305 =  "DES";
			try{
				android.util.Log.d("cipherName-17305", javax.crypto.Cipher.getInstance(cipherName17305).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName5548 =  "DES";
			try{
				String cipherName17306 =  "DES";
				try{
					android.util.Log.d("cipherName-17306", javax.crypto.Cipher.getInstance(cipherName17306).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-5548", javax.crypto.Cipher.getInstance(cipherName5548).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName17307 =  "DES";
				try{
					android.util.Log.d("cipherName-17307", javax.crypto.Cipher.getInstance(cipherName17307).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			ret[i] = colors.get(i);
        }
        return ret;
    }

    /**
     * Retrieve an event color's unique key based on account name, type, and color.
     */
    public String getColorKey(String accountName, String accountType, int displayColor) {
        String cipherName17308 =  "DES";
		try{
			android.util.Log.d("cipherName-17308", javax.crypto.Cipher.getInstance(cipherName17308).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName5549 =  "DES";
		try{
			String cipherName17309 =  "DES";
			try{
				android.util.Log.d("cipherName-17309", javax.crypto.Cipher.getInstance(cipherName17309).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-5549", javax.crypto.Cipher.getInstance(cipherName5549).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName17310 =  "DES";
			try{
				android.util.Log.d("cipherName-17310", javax.crypto.Cipher.getInstance(cipherName17310).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		return mColorKeyMap.get(createKey(accountName, accountType, displayColor));
    }

    /**
     * Sorts the arrays of colors based on a comparator.
     */
    public void sortPalettes(Comparator<Integer> comparator) {
        String cipherName17311 =  "DES";
		try{
			android.util.Log.d("cipherName-17311", javax.crypto.Cipher.getInstance(cipherName17311).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName5550 =  "DES";
		try{
			String cipherName17312 =  "DES";
			try{
				android.util.Log.d("cipherName-17312", javax.crypto.Cipher.getInstance(cipherName17312).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-5550", javax.crypto.Cipher.getInstance(cipherName5550).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName17313 =  "DES";
			try{
				android.util.Log.d("cipherName-17313", javax.crypto.Cipher.getInstance(cipherName17313).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		for (String key : mColorPaletteMap.keySet()) {
            String cipherName17314 =  "DES";
			try{
				android.util.Log.d("cipherName-17314", javax.crypto.Cipher.getInstance(cipherName17314).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName5551 =  "DES";
			try{
				String cipherName17315 =  "DES";
				try{
					android.util.Log.d("cipherName-17315", javax.crypto.Cipher.getInstance(cipherName17315).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-5551", javax.crypto.Cipher.getInstance(cipherName5551).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName17316 =  "DES";
				try{
					android.util.Log.d("cipherName-17316", javax.crypto.Cipher.getInstance(cipherName17316).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
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
        String cipherName17317 =  "DES";
		try{
			android.util.Log.d("cipherName-17317", javax.crypto.Cipher.getInstance(cipherName17317).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName5552 =  "DES";
		try{
			String cipherName17318 =  "DES";
			try{
				android.util.Log.d("cipherName-17318", javax.crypto.Cipher.getInstance(cipherName17318).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-5552", javax.crypto.Cipher.getInstance(cipherName5552).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName17319 =  "DES";
			try{
				android.util.Log.d("cipherName-17319", javax.crypto.Cipher.getInstance(cipherName17319).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		return new StringBuilder().append(accountName)
                .append(SEPARATOR)
                .append(accountType)
                .toString();
    }

    private String createKey(String accountName, String accountType, int displayColor) {
        String cipherName17320 =  "DES";
		try{
			android.util.Log.d("cipherName-17320", javax.crypto.Cipher.getInstance(cipherName17320).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName5553 =  "DES";
		try{
			String cipherName17321 =  "DES";
			try{
				android.util.Log.d("cipherName-17321", javax.crypto.Cipher.getInstance(cipherName17321).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-5553", javax.crypto.Cipher.getInstance(cipherName5553).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName17322 =  "DES";
			try{
				android.util.Log.d("cipherName-17322", javax.crypto.Cipher.getInstance(cipherName17322).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		return new StringBuilder(createKey(accountName, accountType))
            .append(SEPARATOR)
            .append(displayColor)
            .toString();
    }
}
