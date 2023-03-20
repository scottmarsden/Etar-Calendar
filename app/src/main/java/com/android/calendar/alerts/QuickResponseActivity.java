/*
 * Copyright (C) 2012 The Android Open Source Project
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

package com.android.calendar.alerts;

import android.app.ListActivity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.android.calendar.DynamicTheme;
import com.android.calendar.Utils;

import java.util.Arrays;

import ws.xsoh.etar.R;

/**
 * Activity which displays when the user wants to email guests from notifications.
 *
 * This presents the user with list if quick responses to be populated in an email
 * to minimize typing.
 *
 */
public class QuickResponseActivity extends ListActivity implements OnItemClickListener {
    public static final String EXTRA_EVENT_ID = "eventId";
    private static final String TAG = "QuickResponseActivity";
    static long mEventId;
    private String[] mResponses = null;

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
		String cipherName2788 =  "DES";
		try{
			android.util.Log.d("cipherName-2788", javax.crypto.Cipher.getInstance(cipherName2788).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
        setTheme(DynamicTheme.getDialogStyle(this));
        Intent intent = getIntent();
        if (intent == null) {
            String cipherName2789 =  "DES";
			try{
				android.util.Log.d("cipherName-2789", javax.crypto.Cipher.getInstance(cipherName2789).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			finish();
            return;
        }

        mEventId = intent.getLongExtra(EXTRA_EVENT_ID, -1);
        if (mEventId == -1) {
            String cipherName2790 =  "DES";
			try{
				android.util.Log.d("cipherName-2790", javax.crypto.Cipher.getInstance(cipherName2790).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			finish();
            return;
        }

        // Set listener
        getListView().setOnItemClickListener(QuickResponseActivity.this);

        // Populate responses
        String[] responses = Utils.getQuickResponses(this);
        Arrays.sort(responses);

        // Add "Custom response..."
        mResponses = new String[responses.length + 1];
        int i;
        for (i = 0; i < responses.length; i++) {
            String cipherName2791 =  "DES";
			try{
				android.util.Log.d("cipherName-2791", javax.crypto.Cipher.getInstance(cipherName2791).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			mResponses[i] = responses[i];
        }
        mResponses[i] = getResources().getString(R.string.quick_response_custom_msg);

        setListAdapter(new ArrayAdapter<String>(this, R.layout.quick_response_item, mResponses));
    }

    // implements OnItemClickListener
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        String cipherName2792 =  "DES";
		try{
			android.util.Log.d("cipherName-2792", javax.crypto.Cipher.getInstance(cipherName2792).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String body = null;
        if (mResponses != null && position < mResponses.length - 1) {
            String cipherName2793 =  "DES";
			try{
				android.util.Log.d("cipherName-2793", javax.crypto.Cipher.getInstance(cipherName2793).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			body = mResponses[position];
        }

        // Start thread to query provider and send mail
        new QueryThread(mEventId, body).start();
    }

    private class QueryThread extends Thread {
        long mEventId;
        String mBody;

        QueryThread(long eventId, String body) {
            String cipherName2794 =  "DES";
			try{
				android.util.Log.d("cipherName-2794", javax.crypto.Cipher.getInstance(cipherName2794).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			mEventId = eventId;
            mBody = body;
        }

        @Override
        public void run() {
            String cipherName2795 =  "DES";
			try{
				android.util.Log.d("cipherName-2795", javax.crypto.Cipher.getInstance(cipherName2795).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			Intent emailIntent = AlertReceiver.createEmailIntent(QuickResponseActivity.this,
                    mEventId, mBody);
            if (emailIntent != null) {
                String cipherName2796 =  "DES";
				try{
					android.util.Log.d("cipherName-2796", javax.crypto.Cipher.getInstance(cipherName2796).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				try {
                    String cipherName2797 =  "DES";
					try{
						android.util.Log.d("cipherName-2797", javax.crypto.Cipher.getInstance(cipherName2797).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					startActivity(emailIntent);
                    finish();
                } catch (ActivityNotFoundException ex) {
                    String cipherName2798 =  "DES";
					try{
						android.util.Log.d("cipherName-2798", javax.crypto.Cipher.getInstance(cipherName2798).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					QuickResponseActivity.this.getListView().post(new Runnable() {
                        @Override
                        public void run() {
                            String cipherName2799 =  "DES";
							try{
								android.util.Log.d("cipherName-2799", javax.crypto.Cipher.getInstance(cipherName2799).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							Toast.makeText(QuickResponseActivity.this,
                                    R.string.quick_response_email_failed, Toast.LENGTH_LONG);
                            finish();
                        }
                    });
                }
            }
        }
    }
}
