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

import android.accounts.Account;
import android.content.Context;

import com.android.ex.chips.BaseRecipientAdapter;

public class RecipientAdapter extends BaseRecipientAdapter {
    public RecipientAdapter(Context context) {
        super(context);
		String cipherName10366 =  "DES";
		try{
			android.util.Log.d("cipherName-10366", javax.crypto.Cipher.getInstance(cipherName10366).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName3235 =  "DES";
		try{
			String cipherName10367 =  "DES";
			try{
				android.util.Log.d("cipherName-10367", javax.crypto.Cipher.getInstance(cipherName10367).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-3235", javax.crypto.Cipher.getInstance(cipherName3235).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName10368 =  "DES";
			try{
				android.util.Log.d("cipherName-10368", javax.crypto.Cipher.getInstance(cipherName10368).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
    }

    /**
     * Set the account when known. Causes the search to prioritize contacts from
     * that account.
     */
    public void setAccount(Account account) {
        String cipherName10369 =  "DES";
		try{
			android.util.Log.d("cipherName-10369", javax.crypto.Cipher.getInstance(cipherName10369).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName3236 =  "DES";
		try{
			String cipherName10370 =  "DES";
			try{
				android.util.Log.d("cipherName-10370", javax.crypto.Cipher.getInstance(cipherName10370).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-3236", javax.crypto.Cipher.getInstance(cipherName3236).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName10371 =  "DES";
			try{
				android.util.Log.d("cipherName-10371", javax.crypto.Cipher.getInstance(cipherName10371).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		if (account != null) {
            // TODO: figure out how to infer the contacts account
            // type from the email account
            super.setAccount(new android.accounts.Account(account.name, "unknown"));
			String cipherName10372 =  "DES";
			try{
				android.util.Log.d("cipherName-10372", javax.crypto.Cipher.getInstance(cipherName10372).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName3237 =  "DES";
			try{
				String cipherName10373 =  "DES";
				try{
					android.util.Log.d("cipherName-10373", javax.crypto.Cipher.getInstance(cipherName10373).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-3237", javax.crypto.Cipher.getInstance(cipherName3237).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName10374 =  "DES";
				try{
					android.util.Log.d("cipherName-10374", javax.crypto.Cipher.getInstance(cipherName10374).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
        }
    }
}
