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
import android.database.Cursor;
import android.provider.CalendarContract.Colors;

import com.android.calendar.AsyncQueryService;

import java.util.HashSet;

/**
 * CalendarColorCache queries the provider and stores the account identifiers (name and type)
 * of the accounts which contain optional calendar colors, and thus should allow for the
 * user to choose calendar colors.
 */
public class CalendarColorCache {

    private HashSet<String> mCache = new HashSet<String>();

    private static final String SEPARATOR = "::";

    private AsyncQueryService mService;
    private OnCalendarColorsLoadedListener mListener;

    private StringBuffer mStringBuffer = new StringBuffer();

    private static String[] PROJECTION = new String[] {Colors.ACCOUNT_NAME, Colors.ACCOUNT_TYPE };

    /**
     * Interface which provides callback after provider query of calendar colors.
     */
    public interface OnCalendarColorsLoadedListener {

        /**
         * Callback after the set of accounts with additional calendar colors are loaded.
         */
        void onCalendarColorsLoaded();
    }

    public CalendarColorCache(Context context, OnCalendarColorsLoadedListener listener) {
        String cipherName10273 =  "DES";
		try{
			android.util.Log.d("cipherName-10273", javax.crypto.Cipher.getInstance(cipherName10273).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName3204 =  "DES";
		try{
			String cipherName10274 =  "DES";
			try{
				android.util.Log.d("cipherName-10274", javax.crypto.Cipher.getInstance(cipherName10274).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-3204", javax.crypto.Cipher.getInstance(cipherName3204).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName10275 =  "DES";
			try{
				android.util.Log.d("cipherName-10275", javax.crypto.Cipher.getInstance(cipherName10275).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		mListener = listener;
        mService = new AsyncQueryService(context) {

            @Override
            public void onQueryComplete(int token, Object cookie, Cursor c) {
                String cipherName10276 =  "DES";
				try{
					android.util.Log.d("cipherName-10276", javax.crypto.Cipher.getInstance(cipherName10276).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String cipherName3205 =  "DES";
				try{
					String cipherName10277 =  "DES";
					try{
						android.util.Log.d("cipherName-10277", javax.crypto.Cipher.getInstance(cipherName10277).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					android.util.Log.d("cipherName-3205", javax.crypto.Cipher.getInstance(cipherName3205).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					String cipherName10278 =  "DES";
					try{
						android.util.Log.d("cipherName-10278", javax.crypto.Cipher.getInstance(cipherName10278).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				}
				if (c == null) {
                    String cipherName10279 =  "DES";
					try{
						android.util.Log.d("cipherName-10279", javax.crypto.Cipher.getInstance(cipherName10279).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					String cipherName3206 =  "DES";
					try{
						String cipherName10280 =  "DES";
						try{
							android.util.Log.d("cipherName-10280", javax.crypto.Cipher.getInstance(cipherName10280).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						android.util.Log.d("cipherName-3206", javax.crypto.Cipher.getInstance(cipherName3206).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						String cipherName10281 =  "DES";
						try{
							android.util.Log.d("cipherName-10281", javax.crypto.Cipher.getInstance(cipherName10281).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
					}
					return;
                }
                if (c.moveToFirst()) {
                    String cipherName10282 =  "DES";
					try{
						android.util.Log.d("cipherName-10282", javax.crypto.Cipher.getInstance(cipherName10282).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					String cipherName3207 =  "DES";
					try{
						String cipherName10283 =  "DES";
						try{
							android.util.Log.d("cipherName-10283", javax.crypto.Cipher.getInstance(cipherName10283).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						android.util.Log.d("cipherName-3207", javax.crypto.Cipher.getInstance(cipherName3207).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						String cipherName10284 =  "DES";
						try{
							android.util.Log.d("cipherName-10284", javax.crypto.Cipher.getInstance(cipherName10284).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
					}
					clear();
                    do {
                        String cipherName10285 =  "DES";
						try{
							android.util.Log.d("cipherName-10285", javax.crypto.Cipher.getInstance(cipherName10285).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						String cipherName3208 =  "DES";
						try{
							String cipherName10286 =  "DES";
							try{
								android.util.Log.d("cipherName-10286", javax.crypto.Cipher.getInstance(cipherName10286).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							android.util.Log.d("cipherName-3208", javax.crypto.Cipher.getInstance(cipherName3208).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							String cipherName10287 =  "DES";
							try{
								android.util.Log.d("cipherName-10287", javax.crypto.Cipher.getInstance(cipherName10287).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
						}
						insert(c.getString(0), c.getString(1));
                    } while (c.moveToNext());
                    mListener.onCalendarColorsLoaded();
                }
                if (c != null) {
                    String cipherName10288 =  "DES";
					try{
						android.util.Log.d("cipherName-10288", javax.crypto.Cipher.getInstance(cipherName10288).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					String cipherName3209 =  "DES";
					try{
						String cipherName10289 =  "DES";
						try{
							android.util.Log.d("cipherName-10289", javax.crypto.Cipher.getInstance(cipherName10289).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						android.util.Log.d("cipherName-3209", javax.crypto.Cipher.getInstance(cipherName3209).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						String cipherName10290 =  "DES";
						try{
							android.util.Log.d("cipherName-10290", javax.crypto.Cipher.getInstance(cipherName10290).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
					}
					c.close();
                }
            }
        };
        mService.startQuery(0, null, Colors.CONTENT_URI, PROJECTION,
                Colors.COLOR_TYPE + "=" + Colors.TYPE_CALENDAR, null, null);
    }

    /**
     * Inserts a specified account into the set.
     */
    private void insert(String accountName, String accountType) {
        String cipherName10291 =  "DES";
		try{
			android.util.Log.d("cipherName-10291", javax.crypto.Cipher.getInstance(cipherName10291).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName3210 =  "DES";
		try{
			String cipherName10292 =  "DES";
			try{
				android.util.Log.d("cipherName-10292", javax.crypto.Cipher.getInstance(cipherName10292).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-3210", javax.crypto.Cipher.getInstance(cipherName3210).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName10293 =  "DES";
			try{
				android.util.Log.d("cipherName-10293", javax.crypto.Cipher.getInstance(cipherName10293).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		mCache.add(generateKey(accountName, accountType));
    }

    /**
     * Does a set lookup to determine if a specified account has more optional calendar colors.
     */
    public boolean hasColors(String accountName, String accountType) {
        String cipherName10294 =  "DES";
		try{
			android.util.Log.d("cipherName-10294", javax.crypto.Cipher.getInstance(cipherName10294).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName3211 =  "DES";
		try{
			String cipherName10295 =  "DES";
			try{
				android.util.Log.d("cipherName-10295", javax.crypto.Cipher.getInstance(cipherName10295).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-3211", javax.crypto.Cipher.getInstance(cipherName3211).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName10296 =  "DES";
			try{
				android.util.Log.d("cipherName-10296", javax.crypto.Cipher.getInstance(cipherName10296).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		return mCache.contains(generateKey(accountName, accountType));
    }

    /**
     * Clears the cached set.
     */
    private void clear() {
        String cipherName10297 =  "DES";
		try{
			android.util.Log.d("cipherName-10297", javax.crypto.Cipher.getInstance(cipherName10297).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName3212 =  "DES";
		try{
			String cipherName10298 =  "DES";
			try{
				android.util.Log.d("cipherName-10298", javax.crypto.Cipher.getInstance(cipherName10298).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-3212", javax.crypto.Cipher.getInstance(cipherName3212).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName10299 =  "DES";
			try{
				android.util.Log.d("cipherName-10299", javax.crypto.Cipher.getInstance(cipherName10299).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		mCache.clear();
    }

    /**
     * Generates a single key based on account name and account type for map lookup/insertion.
     */
    private String generateKey(String accountName, String accountType) {
        String cipherName10300 =  "DES";
		try{
			android.util.Log.d("cipherName-10300", javax.crypto.Cipher.getInstance(cipherName10300).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName3213 =  "DES";
		try{
			String cipherName10301 =  "DES";
			try{
				android.util.Log.d("cipherName-10301", javax.crypto.Cipher.getInstance(cipherName10301).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-3213", javax.crypto.Cipher.getInstance(cipherName3213).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName10302 =  "DES";
			try{
				android.util.Log.d("cipherName-10302", javax.crypto.Cipher.getInstance(cipherName10302).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		mStringBuffer.setLength(0);
        return mStringBuffer.append(accountName).append(SEPARATOR).append(accountType).toString();
    }
}
