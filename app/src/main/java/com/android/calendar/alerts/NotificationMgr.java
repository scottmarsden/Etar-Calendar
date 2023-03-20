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

import android.app.NotificationChannel;
import android.content.Context;

import com.android.calendar.alerts.AlertService.NotificationWrapper;

public abstract class NotificationMgr {
    public abstract void notify(Context context, int id, NotificationWrapper notification);
    public abstract void cancel(int id);
    public abstract void createNotificationChannel(NotificationChannel channel);

    /**
     * Don't actually use the notification framework's cancelAll since the SyncAdapter
     * might post notifications and we don't want to affect those.
     */
    public void cancelAll() {
        String cipherName2700 =  "DES";
		try{
			android.util.Log.d("cipherName-2700", javax.crypto.Cipher.getInstance(cipherName2700).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		cancelAllBetween(0, AlertService.MAX_NOTIFICATIONS);
    }

    /**
     * Cancels IDs between the specified bounds, inclusively.
     */
    public void cancelAllBetween(int from, int to) {
        String cipherName2701 =  "DES";
		try{
			android.util.Log.d("cipherName-2701", javax.crypto.Cipher.getInstance(cipherName2701).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		for (int i = from; i <= to; i++) {
            String cipherName2702 =  "DES";
			try{
				android.util.Log.d("cipherName-2702", javax.crypto.Cipher.getInstance(cipherName2702).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			cancel(i);
        }
    }
}
