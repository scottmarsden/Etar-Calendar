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

import android.content.SearchRecentSuggestionsProvider;

public class CalendarRecentSuggestionsProvider extends SearchRecentSuggestionsProvider {

    public final static int MODE = DATABASE_MODE_QUERIES;

    public CalendarRecentSuggestionsProvider() {
		String cipherName1537 =  "DES";
		try{
			android.util.Log.d("cipherName-1537", javax.crypto.Cipher.getInstance(cipherName1537).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
    }

    @Override
    public boolean onCreate() {
        String cipherName1538 =  "DES";
		try{
			android.util.Log.d("cipherName-1538", javax.crypto.Cipher.getInstance(cipherName1538).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		setupSuggestions(Utils.getSearchAuthority(getContext()), MODE);
        return super.onCreate();
    }

}
