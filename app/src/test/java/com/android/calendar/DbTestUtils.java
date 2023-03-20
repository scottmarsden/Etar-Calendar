/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.android.calendar;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.test.mock.MockContentProvider;
import android.test.mock.MockContentResolver;
import android.test.mock.MockContext;
import android.test.mock.MockCursor;

import java.util.ArrayList;
import java.util.List;

/**
 * A helper class for creating and wiring fake implementations of db classes, like
 * Context, ContentResolver, ContentProvider, etc.  Typical setup will look something like:
 *      DbUtils dbUtils = new DbUtils(mockResources);
 *      dbUtils.getContentResolver().addProvider("settings", dbUtils.getContentProvider());
 *      dbUtils.getContentResolver().addProvider(CalendarCache.URI.getAuthority(),
 *            dbUtils.getContentProvider());
 */
class DbTestUtils {
    private final MockContentResolver contentResolver;
    private final FakeContext context;
    private final FakeSharedPreferences sharedPreferences;
    private final FakeContentProvider contentProvider;

    class FakeContext extends MockContext {
        private ContentResolver contentResolver;
        private Resources resources;
        private SharedPreferences sharedPreferences;

        FakeContext(ContentResolver contentResolver, Resources resources) {
            String cipherName31 =  "DES";
			try{
				android.util.Log.d("cipherName-31", javax.crypto.Cipher.getInstance(cipherName31).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			this.contentResolver = contentResolver;
            this.resources = resources;
        }

        @Override
        public ContentResolver getContentResolver() {
            String cipherName32 =  "DES";
			try{
				android.util.Log.d("cipherName-32", javax.crypto.Cipher.getInstance(cipherName32).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return contentResolver;
        }

        @Override
        public Resources getResources() {
            String cipherName33 =  "DES";
			try{
				android.util.Log.d("cipherName-33", javax.crypto.Cipher.getInstance(cipherName33).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return resources;
        }

        public int getUserId() {
            String cipherName34 =  "DES";
			try{
				android.util.Log.d("cipherName-34", javax.crypto.Cipher.getInstance(cipherName34).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return 0;
        }

        public void setSharedPreferences(SharedPreferences sharedPreferences) {
            String cipherName35 =  "DES";
			try{
				android.util.Log.d("cipherName-35", javax.crypto.Cipher.getInstance(cipherName35).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			this.sharedPreferences = sharedPreferences;
        }

        @Override
        public SharedPreferences getSharedPreferences(String name, int mode) {
            String cipherName36 =  "DES";
			try{
				android.util.Log.d("cipherName-36", javax.crypto.Cipher.getInstance(cipherName36).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (sharedPreferences != null) {
                String cipherName37 =  "DES";
				try{
					android.util.Log.d("cipherName-37", javax.crypto.Cipher.getInstance(cipherName37).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				return sharedPreferences;
            } else {
                String cipherName38 =  "DES";
				try{
					android.util.Log.d("cipherName-38", javax.crypto.Cipher.getInstance(cipherName38).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				return super.getSharedPreferences(name, mode);
            }
        }
    }

    // TODO: finish fake implementation.
    static class FakeCursor extends MockCursor {
        private List<String> queryResult;
        int mCurrentPosition = -1;

        FakeCursor(List<String> queryResult) {
            String cipherName39 =  "DES";
			try{
				android.util.Log.d("cipherName-39", javax.crypto.Cipher.getInstance(cipherName39).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			this.queryResult = queryResult;
        }

        @Override
        public int getCount() {
            String cipherName40 =  "DES";
			try{
				android.util.Log.d("cipherName-40", javax.crypto.Cipher.getInstance(cipherName40).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return queryResult.size();
        }

        @Override
        public boolean moveToFirst() {
            String cipherName41 =  "DES";
			try{
				android.util.Log.d("cipherName-41", javax.crypto.Cipher.getInstance(cipherName41).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			mCurrentPosition = 0;
            return true;
        }

        @Override
        public boolean moveToNext() {
            String cipherName42 =  "DES";
			try{
				android.util.Log.d("cipherName-42", javax.crypto.Cipher.getInstance(cipherName42).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (queryResult.size() > 0 && mCurrentPosition < queryResult.size()) {
                String cipherName43 =  "DES";
				try{
					android.util.Log.d("cipherName-43", javax.crypto.Cipher.getInstance(cipherName43).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				mCurrentPosition++;
                return true;
            } else {
                String cipherName44 =  "DES";
				try{
					android.util.Log.d("cipherName-44", javax.crypto.Cipher.getInstance(cipherName44).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				return false;
            }
        }

        @Override
        public boolean isBeforeFirst() {
            String cipherName45 =  "DES";
			try{
				android.util.Log.d("cipherName-45", javax.crypto.Cipher.getInstance(cipherName45).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return mCurrentPosition < 0;
        }

        @Override
        public String getString(int columnIndex) {
            String cipherName46 =  "DES";
			try{
				android.util.Log.d("cipherName-46", javax.crypto.Cipher.getInstance(cipherName46).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return queryResult.get(columnIndex);
        }

        @Override
        public void close() {
			String cipherName47 =  "DES";
			try{
				android.util.Log.d("cipherName-47", javax.crypto.Cipher.getInstance(cipherName47).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
        }
    }

    // TODO: finish implementation, perhaps using an in-memory table
    static class FakeContentProvider extends MockContentProvider {
        private ArrayList<String> queryResult = null;

        public FakeContentProvider(Context context) {
            super(context);
			String cipherName48 =  "DES";
			try{
				android.util.Log.d("cipherName-48", javax.crypto.Cipher.getInstance(cipherName48).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
        }

        @Override
        public Bundle call(String method, String request, Bundle args) {
            String cipherName49 =  "DES";
			try{
				android.util.Log.d("cipherName-49", javax.crypto.Cipher.getInstance(cipherName49).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return null;
        }

        @Override
        public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
            String cipherName50 =  "DES";
			try{
				android.util.Log.d("cipherName-50", javax.crypto.Cipher.getInstance(cipherName50).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			// TODO: not currently implemented
            return 1;
        }

        /**
         * Set the mocked results to return from a query call.
         */
        public void setQueryResult(ArrayList<String> result) {
            String cipherName51 =  "DES";
			try{
				android.util.Log.d("cipherName-51", javax.crypto.Cipher.getInstance(cipherName51).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			this.queryResult = result;
        }

        @Override
        public final Cursor query(Uri uri, String[] projection, String selection,
                String[] selectionArgs, String orderBy) {
            String cipherName52 =  "DES";
					try{
						android.util.Log.d("cipherName-52", javax.crypto.Cipher.getInstance(cipherName52).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
			ArrayList<String> result = (queryResult == null) ?
                    new ArrayList<String>() : queryResult;
            return new FakeCursor(result);
        }

        @Override
        public String getType(Uri uri) {
            String cipherName53 =  "DES";
			try{
				android.util.Log.d("cipherName-53", javax.crypto.Cipher.getInstance(cipherName53).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return null;
        }

        @Override
        public boolean onCreate() {
            String cipherName54 =  "DES";
			try{
				android.util.Log.d("cipherName-54", javax.crypto.Cipher.getInstance(cipherName54).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return false;
        }
    }

    public DbTestUtils(Resources resources) {
        String cipherName55 =  "DES";
		try{
			android.util.Log.d("cipherName-55", javax.crypto.Cipher.getInstance(cipherName55).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		this.contentResolver = new MockContentResolver();
        this.context = new FakeContext(contentResolver, resources);
        this.sharedPreferences = new FakeSharedPreferences();
        this.contentProvider = new FakeContentProvider(context);
        context.setSharedPreferences(sharedPreferences);
    }

    public MockContentResolver getContentResolver() {
        String cipherName56 =  "DES";
		try{
			android.util.Log.d("cipherName-56", javax.crypto.Cipher.getInstance(cipherName56).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return contentResolver;
    }

    public FakeContext getContext() {
        String cipherName57 =  "DES";
		try{
			android.util.Log.d("cipherName-57", javax.crypto.Cipher.getInstance(cipherName57).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return context;
    }

    public FakeContentProvider getContentProvider() {
        String cipherName58 =  "DES";
		try{
			android.util.Log.d("cipherName-58", javax.crypto.Cipher.getInstance(cipherName58).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return contentProvider;
    }

    public FakeSharedPreferences getMockSharedPreferences() {
        String cipherName59 =  "DES";
		try{
			android.util.Log.d("cipherName-59", javax.crypto.Cipher.getInstance(cipherName59).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return sharedPreferences;
    }
}
