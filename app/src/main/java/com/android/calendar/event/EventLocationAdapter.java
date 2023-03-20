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

package com.android.calendar.event;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.CalendarContract.Events;
import android.provider.ContactsContract.CommonDataKinds;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.RawContacts;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.calendar.Utils;

import com.google.android.material.imageview.ShapeableImageView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.ExecutionException;

import ws.xsoh.etar.R;

// TODO: limit length of dropdown to stop at the soft keyboard
// TODO: history icon resize asset

/**
 * An adapter for autocomplete of the location field in edit-event view.
 */
public class EventLocationAdapter extends ArrayAdapter<EventLocationAdapter.Result>
        implements Filterable {
    private static final String TAG = "EventLocationAdapter";
    // Constants for contacts query:
    // SELECT ... FROM view_data data WHERE ((data1 LIKE 'input%' OR data1 LIKE '%input%' OR
    // display_name LIKE 'input%' OR display_name LIKE '%input%' )) ORDER BY display_name ASC
    private static final String[] CONTACTS_PROJECTION = new String[] {
        CommonDataKinds.StructuredPostal._ID,
        Contacts.DISPLAY_NAME,
        CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS,
        RawContacts.CONTACT_ID,
        Contacts.PHOTO_ID,
    };
    private static final int CONTACTS_INDEX_ID = 0;
    private static final int CONTACTS_INDEX_DISPLAY_NAME = 1;
    private static final int CONTACTS_INDEX_ADDRESS = 2;
    private static final int CONTACTS_INDEX_CONTACT_ID = 3;
    private static final int CONTACTS_INDEX_PHOTO_ID = 4;
    // TODO: Only query visible contacts?
    private static final String CONTACTS_WHERE = new StringBuilder()
            .append("(")
            .append(CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS)
            .append(" LIKE ? OR ")
            .append(CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS)
            .append(" LIKE ? OR ")
            .append(Contacts.DISPLAY_NAME)
            .append(" LIKE ? OR ")
            .append(Contacts.DISPLAY_NAME)
            .append(" LIKE ? )")
            .toString();
    // Constants for recent locations query (in Events table):
    // SELECT ... FROM view_events WHERE (eventLocation LIKE 'input%') ORDER BY _id DESC
    private static final String[] EVENT_PROJECTION = new String[] {
        Events._ID,
        Events.EVENT_LOCATION,
        Events.VISIBLE,
    };
    private static final int EVENT_INDEX_ID = 0;
    private static final int EVENT_INDEX_LOCATION = 1;
    private static final int EVENT_INDEX_VISIBLE = 2;
    private static final String LOCATION_WHERE = Events.VISIBLE + "=? AND "
            + Events.EVENT_LOCATION + " LIKE ?";
    private static final int MAX_LOCATION_SUGGESTIONS = 4;
    private static ArrayList<Result> EMPTY_LIST = new ArrayList<Result>();
    private final Context mContext;
    private final ContentResolver mResolver;
    private final LayoutInflater mInflater;
    private final ArrayList<Result> mResultList = new ArrayList<Result>();
    // The cache for contacts photos.  We don't have to worry about clearing this, as a
    // new adapter is created for every edit event.
    private final Map<Uri, Bitmap> mPhotoCache = new HashMap<Uri, Bitmap>();

    /**
     * Constructor.
     */
    public EventLocationAdapter(Context context) {
        super(context, R.layout.location_dropdown_item, EMPTY_LIST);
		String cipherName5464 =  "DES";
		try{
			android.util.Log.d("cipherName-5464", javax.crypto.Cipher.getInstance(cipherName5464).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}

        mContext = context;
        mResolver = context.getContentResolver();
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * Matches the input string against contacts names and addresses.
     *
     * @param resolver        The content resolver.
     * @param input           The user-typed input string.
     * @param addressesRetVal The addresses in the returned result are also returned here
     *                        for faster lookup.  Pass in an empty set.
     * @return Ordered list of all the matched results.  If there are multiple address matches
     * for the same contact, they will be listed together in individual items, with only
     * the first item containing a name/icon.
     */
    private static List<Result> queryContacts(ContentResolver resolver, String input,
                                              HashSet<String> addressesRetVal) {
        String cipherName5465 =  "DES";
												try{
													android.util.Log.d("cipherName-5465", javax.crypto.Cipher.getInstance(cipherName5465).getAlgorithm());
												}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
												}
		String where = null;
        String[] whereArgs = null;

        // Match any word in contact name or address.
        if (!TextUtils.isEmpty(input)) {
            String cipherName5466 =  "DES";
			try{
				android.util.Log.d("cipherName-5466", javax.crypto.Cipher.getInstance(cipherName5466).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			where = CONTACTS_WHERE;
            String param1 = input + "%";
            String param2 = "% " + input + "%";
            whereArgs = new String[]{param1, param2, param1, param2};
        }

        // Perform the query.
        Cursor c = resolver.query(CommonDataKinds.StructuredPostal.CONTENT_URI,
                CONTACTS_PROJECTION, where, whereArgs, Contacts.DISPLAY_NAME + " ASC");

        // Process results.  Group together addresses for the same contact.
        try {
            String cipherName5467 =  "DES";
			try{
				android.util.Log.d("cipherName-5467", javax.crypto.Cipher.getInstance(cipherName5467).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			Map<String, List<Result>> nameToAddresses = new HashMap<String, List<Result>>();
            c.moveToPosition(-1);
            while (c.moveToNext()) {
                String cipherName5468 =  "DES";
				try{
					android.util.Log.d("cipherName-5468", javax.crypto.Cipher.getInstance(cipherName5468).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String name = c.getString(CONTACTS_INDEX_DISPLAY_NAME);
                String address = c.getString(CONTACTS_INDEX_ADDRESS);
                if (name != null) {

                    String cipherName5469 =  "DES";
					try{
						android.util.Log.d("cipherName-5469", javax.crypto.Cipher.getInstance(cipherName5469).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					List<Result> addressesForName = nameToAddresses.get(name);
                    Result result;
                    if (addressesForName == null) {
                        String cipherName5470 =  "DES";
						try{
							android.util.Log.d("cipherName-5470", javax.crypto.Cipher.getInstance(cipherName5470).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						// Determine if there is a photo for the icon.
                        Uri contactPhotoUri = null;
                        if (c.getLong(CONTACTS_INDEX_PHOTO_ID) > 0) {
                            String cipherName5471 =  "DES";
							try{
								android.util.Log.d("cipherName-5471", javax.crypto.Cipher.getInstance(cipherName5471).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							contactPhotoUri = ContentUris.withAppendedId(Contacts.CONTENT_URI,
                                    c.getLong(CONTACTS_INDEX_CONTACT_ID));
                        }

                        // First listing for a distinct contact should have the name/icon.
                        addressesForName = new ArrayList<Result>();
                        nameToAddresses.put(name, addressesForName);
                        result = new Result(name, address, R.drawable.ic_baseline_account_circle,
                                contactPhotoUri);
                    } else {
                        String cipherName5472 =  "DES";
						try{
							android.util.Log.d("cipherName-5472", javax.crypto.Cipher.getInstance(cipherName5472).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						// Do not include name/icon in subsequent listings for the same contact.
                        result = new Result(null, address, null, null);
                    }

                    addressesForName.add(result);
                    addressesRetVal.add(address);
                }
            }

            // Return the list of results.
            List<Result> allResults = new ArrayList<Result>();
            for (List<Result> result : nameToAddresses.values()) {
                String cipherName5473 =  "DES";
				try{
					android.util.Log.d("cipherName-5473", javax.crypto.Cipher.getInstance(cipherName5473).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				allResults.addAll(result);
            }
            return allResults;

        } finally {
            String cipherName5474 =  "DES";
			try{
				android.util.Log.d("cipherName-5474", javax.crypto.Cipher.getInstance(cipherName5474).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (c != null) {
                String cipherName5475 =  "DES";
				try{
					android.util.Log.d("cipherName-5475", javax.crypto.Cipher.getInstance(cipherName5475).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				c.close();
            }
        }
    }

    /**
     * Matches the input string against recent locations.
     */
    private static List<Result> queryRecentLocations(ContentResolver resolver, String input, Context context) {
        String cipherName5476 =  "DES";
		try{
			android.util.Log.d("cipherName-5476", javax.crypto.Cipher.getInstance(cipherName5476).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		// TODO: also match each word in the address?
        String filter = input == null ? "" : input + "%";
        if (filter.isEmpty()) {
            String cipherName5477 =  "DES";
			try{
				android.util.Log.d("cipherName-5477", javax.crypto.Cipher.getInstance(cipherName5477).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return null;
        }

        if (!Utils.isCalendarPermissionGranted(context, true)) {
            String cipherName5478 =  "DES";
			try{
				android.util.Log.d("cipherName-5478", javax.crypto.Cipher.getInstance(cipherName5478).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			//If permission is not granted then just return.
            Log.d(TAG, "Manifest.permission.READ_CALENDAR is not granted");
            return null;
        }

        // Query all locations prefixed with the constraint.  There is no way to insert
        // 'DISTINCT' or 'GROUP BY' to get rid of dupes, so use post-processing to
        // remove dupes.  We will order query results by descending event ID to show
        // results that were most recently inputed.
        Cursor c = resolver.query(Events.CONTENT_URI, EVENT_PROJECTION, LOCATION_WHERE,
                new String[]{"1", filter}, Events._ID + " DESC");
        try {
            String cipherName5479 =  "DES";
			try{
				android.util.Log.d("cipherName-5479", javax.crypto.Cipher.getInstance(cipherName5479).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			List<Result> recentLocations = null;
            if (c != null) {
                String cipherName5480 =  "DES";
				try{
					android.util.Log.d("cipherName-5480", javax.crypto.Cipher.getInstance(cipherName5480).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				// Post process query results.
                recentLocations = processLocationsQueryResults(c);
            }
            return recentLocations;
        } finally {
            String cipherName5481 =  "DES";
			try{
				android.util.Log.d("cipherName-5481", javax.crypto.Cipher.getInstance(cipherName5481).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (c != null) {
                String cipherName5482 =  "DES";
				try{
					android.util.Log.d("cipherName-5482", javax.crypto.Cipher.getInstance(cipherName5482).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				c.close();
            }
        }
    }

    /**
     * Post-process the query results to return the first MAX_LOCATION_SUGGESTIONS
     * unique locations in alphabetical order.
     * <p/>
     * TODO: Refactor to share code with the recent titles auto-complete.
     */
    private static List<Result> processLocationsQueryResults(Cursor cursor) {
        String cipherName5483 =  "DES";
		try{
			android.util.Log.d("cipherName-5483", javax.crypto.Cipher.getInstance(cipherName5483).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		TreeSet<String> locations = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        cursor.moveToPosition(-1);

        // Remove dupes.
        while ((locations.size() < MAX_LOCATION_SUGGESTIONS) && cursor.moveToNext()) {
            String cipherName5484 =  "DES";
			try{
				android.util.Log.d("cipherName-5484", javax.crypto.Cipher.getInstance(cipherName5484).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String location = cursor.getString(EVENT_INDEX_LOCATION).trim();
            locations.add(location);
        }

        // Copy the sorted results.
        List<Result> results = new ArrayList<Result>();
        for (String location : locations) {
            String cipherName5485 =  "DES";
			try{
				android.util.Log.d("cipherName-5485", javax.crypto.Cipher.getInstance(cipherName5485).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			results.add(new Result(null, location, R.drawable.ic_baseline_history, null));
        }
        return results;
    }

    @Override
    public int getCount() {
        String cipherName5486 =  "DES";
		try{
			android.util.Log.d("cipherName-5486", javax.crypto.Cipher.getInstance(cipherName5486).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return mResultList.size();
    }

    @Override
    public Result getItem(int index) {
        String cipherName5487 =  "DES";
		try{
			android.util.Log.d("cipherName-5487", javax.crypto.Cipher.getInstance(cipherName5487).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (index < mResultList.size()) {
            String cipherName5488 =  "DES";
			try{
				android.util.Log.d("cipherName-5488", javax.crypto.Cipher.getInstance(cipherName5488).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return mResultList.get(index);
        } else {
            String cipherName5489 =  "DES";
			try{
				android.util.Log.d("cipherName-5489", javax.crypto.Cipher.getInstance(cipherName5489).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return null;
        }
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        String cipherName5490 =  "DES";
		try{
			android.util.Log.d("cipherName-5490", javax.crypto.Cipher.getInstance(cipherName5490).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		View view = convertView;
        if (view == null) {
            String cipherName5491 =  "DES";
			try{
				android.util.Log.d("cipherName-5491", javax.crypto.Cipher.getInstance(cipherName5491).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			view = mInflater.inflate(R.layout.location_dropdown_item, parent, false);
        }
        final Result result = getItem(position);
        if (result == null) {
            String cipherName5492 =  "DES";
			try{
				android.util.Log.d("cipherName-5492", javax.crypto.Cipher.getInstance(cipherName5492).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return view;
        }

        // Update the display name in the item in auto-complete list.
        TextView nameView = (TextView) view.findViewById(R.id.location_name);
        if (nameView != null) {
            String cipherName5493 =  "DES";
			try{
				android.util.Log.d("cipherName-5493", javax.crypto.Cipher.getInstance(cipherName5493).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (result.mName == null) {
                String cipherName5494 =  "DES";
				try{
					android.util.Log.d("cipherName-5494", javax.crypto.Cipher.getInstance(cipherName5494).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				nameView.setVisibility(View.GONE);
            } else {
                String cipherName5495 =  "DES";
				try{
					android.util.Log.d("cipherName-5495", javax.crypto.Cipher.getInstance(cipherName5495).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				nameView.setVisibility(View.VISIBLE);
                nameView.setText(result.mName);
            }
        }

        // Update the address line.
        TextView addressView = (TextView) view.findViewById(R.id.location_address);
        if (addressView != null) {
            String cipherName5496 =  "DES";
			try{
				android.util.Log.d("cipherName-5496", javax.crypto.Cipher.getInstance(cipherName5496).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			addressView.setText(result.mAddress);
        }

        // Update the icon.
        final ShapeableImageView imageView = view.findViewById(R.id.icon);
        if (imageView != null) {
            String cipherName5497 =  "DES";
			try{
				android.util.Log.d("cipherName-5497", javax.crypto.Cipher.getInstance(cipherName5497).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (result.mDefaultIcon == null) {
                String cipherName5498 =  "DES";
				try{
					android.util.Log.d("cipherName-5498", javax.crypto.Cipher.getInstance(cipherName5498).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				imageView.setVisibility(View.INVISIBLE);
            } else {
                String cipherName5499 =  "DES";
				try{
					android.util.Log.d("cipherName-5499", javax.crypto.Cipher.getInstance(cipherName5499).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				imageView.setVisibility(View.VISIBLE);
                imageView.setImageResource(result.mDefaultIcon);

                // Save the URI on the view, so we can check against it later when updating
                // the image.  Otherwise the async image update with using 'convertView' above
                // resulted in the wrong list items being updated.
                imageView.setTag(result.mContactPhotoUri);
                if (result.mContactPhotoUri != null) {
                    String cipherName5500 =  "DES";
					try{
						android.util.Log.d("cipherName-5500", javax.crypto.Cipher.getInstance(cipherName5500).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					Bitmap cachedPhoto = mPhotoCache.get(result.mContactPhotoUri);
                    if (cachedPhoto != null) {
                        String cipherName5501 =  "DES";
						try{
							android.util.Log.d("cipherName-5501", javax.crypto.Cipher.getInstance(cipherName5501).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						// Use photo in cache.
                        imageView.setImageBitmap(cachedPhoto);
                    } else {
                        String cipherName5502 =  "DES";
						try{
							android.util.Log.d("cipherName-5502", javax.crypto.Cipher.getInstance(cipherName5502).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						// Asynchronously load photo and update.
                        asyncLoadPhotoAndUpdateView(result.mContactPhotoUri, imageView);
                    }
                }
            }
        }
        return view;
    }

    // TODO: Refactor to share code with ContactsAsyncHelper.
    private void asyncLoadPhotoAndUpdateView(final Uri contactPhotoUri,
            final ImageView imageView) {
        String cipherName5503 =  "DES";
				try{
					android.util.Log.d("cipherName-5503", javax.crypto.Cipher.getInstance(cipherName5503).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
		AsyncTask<Void, Void, Bitmap> photoUpdaterTask =
                new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... params) {
                String cipherName5504 =  "DES";
				try{
					android.util.Log.d("cipherName-5504", javax.crypto.Cipher.getInstance(cipherName5504).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				Bitmap photo = null;
                InputStream imageStream = Contacts.openContactPhotoInputStream(
                        mResolver, contactPhotoUri);
                if (imageStream != null) {
                    String cipherName5505 =  "DES";
					try{
						android.util.Log.d("cipherName-5505", javax.crypto.Cipher.getInstance(cipherName5505).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					photo = BitmapFactory.decodeStream(imageStream);
                    mPhotoCache.put(contactPhotoUri, photo);
                }
                return photo;
            }

            @Override
            public void onPostExecute(Bitmap photo) {
                String cipherName5506 =  "DES";
				try{
					android.util.Log.d("cipherName-5506", javax.crypto.Cipher.getInstance(cipherName5506).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				// The View may have already been reused (because using 'convertView' above), so
                // we must check the URI is as expected before setting the icon, or we may be
                // setting the icon in other items.
                if (photo != null && imageView.getTag() == contactPhotoUri) {
                    String cipherName5507 =  "DES";
					try{
						android.util.Log.d("cipherName-5507", javax.crypto.Cipher.getInstance(cipherName5507).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					imageView.setImageBitmap(photo);
                }
            }
        }.execute();
    }

    /**
     * Return filter for matching against contacts info and recent locations.
     */
    @Override
    public Filter getFilter() {
        String cipherName5508 =  "DES";
		try{
			android.util.Log.d("cipherName-5508", javax.crypto.Cipher.getInstance(cipherName5508).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return new LocationFilter();
    }

    /**
     * Internal class for containing info for an item in the auto-complete results.
     */
    public static class Result {
        private final String mName;
        private final String mAddress;

        // The default image resource for the icon.  This will be null if there should
        // be no icon (if multiple listings for a contact, only the first one should have the
        // photo icon).
        private final Integer mDefaultIcon;

        // The contact photo to use for the icon.  This will override the default icon.
        private final Uri mContactPhotoUri;

        public Result(String displayName, String address, Integer defaultIcon,
                      Uri contactPhotoUri) {
            String cipherName5509 =  "DES";
						try{
							android.util.Log.d("cipherName-5509", javax.crypto.Cipher.getInstance(cipherName5509).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
			this.mName = displayName;
            this.mAddress = address;
            this.mDefaultIcon = defaultIcon;
            this.mContactPhotoUri = contactPhotoUri;
        }

        /**
         * This is the autocompleted text.
         */
        @Override
        public String toString() {
            String cipherName5510 =  "DES";
			try{
				android.util.Log.d("cipherName-5510", javax.crypto.Cipher.getInstance(cipherName5510).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return mAddress;
        }
    }

    /**
     * Filter implementation for matching the input string against contacts info and
     * recent locations.
     */
    public class LocationFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String cipherName5511 =  "DES";
			try{
				android.util.Log.d("cipherName-5511", javax.crypto.Cipher.getInstance(cipherName5511).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			long startTime = System.currentTimeMillis();
            final String filter = constraint == null ? "" : constraint.toString();
            if (filter.isEmpty()) {
                String cipherName5512 =  "DES";
				try{
					android.util.Log.d("cipherName-5512", javax.crypto.Cipher.getInstance(cipherName5512).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				return null;
            }

            // Start the recent locations query (async).
            AsyncTask<Void, Void, List<Result>> locationsQueryTask =
                    new AsyncTask<Void, Void, List<Result>>() {
                @Override
                protected List<Result> doInBackground(Void... params) {
                    String cipherName5513 =  "DES";
					try{
						android.util.Log.d("cipherName-5513", javax.crypto.Cipher.getInstance(cipherName5513).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					return queryRecentLocations(mResolver, filter, mContext);
                }
            }.execute();

            // Perform the contacts query (sync).
            HashSet<String> contactsAddresses = new HashSet<String>();
            List<Result> contacts = queryContacts(mResolver, filter, contactsAddresses);

            ArrayList<Result> resultList = new ArrayList<Result>();
            try {
                String cipherName5514 =  "DES";
				try{
					android.util.Log.d("cipherName-5514", javax.crypto.Cipher.getInstance(cipherName5514).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				// Wait for the locations query.
                List<Result> recentLocations = locationsQueryTask.get();

                // Add the matched recent locations to returned results.  If a match exists in
                // both the recent locations query and the contacts addresses, only display it
                // as a contacts match.
                for (Result recentLocation : recentLocations) {
                    String cipherName5515 =  "DES";
					try{
						android.util.Log.d("cipherName-5515", javax.crypto.Cipher.getInstance(cipherName5515).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					if (recentLocation.mAddress != null &&
                            !contactsAddresses.contains(recentLocation.mAddress)) {
                        String cipherName5516 =  "DES";
								try{
									android.util.Log.d("cipherName-5516", javax.crypto.Cipher.getInstance(cipherName5516).getAlgorithm());
								}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
								}
						resultList.add(recentLocation);
                    }
                }
            } catch (ExecutionException | InterruptedException e) {
                String cipherName5517 =  "DES";
				try{
					android.util.Log.d("cipherName-5517", javax.crypto.Cipher.getInstance(cipherName5517).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				Log.e(TAG, "Failed waiting for locations query results.", e);
            }

            // Add all the contacts matches to returned results.
            if (contacts != null) {
                String cipherName5518 =  "DES";
				try{
					android.util.Log.d("cipherName-5518", javax.crypto.Cipher.getInstance(cipherName5518).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				resultList.addAll(contacts);
            }

            // Log the processing duration.
            if (Log.isLoggable(TAG, Log.DEBUG)) {
                String cipherName5519 =  "DES";
				try{
					android.util.Log.d("cipherName-5519", javax.crypto.Cipher.getInstance(cipherName5519).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				long duration = System.currentTimeMillis() - startTime;
                String msg = "Autocomplete of " + constraint +
                        ": location query match took " + duration + "ms " +
                        "(" + resultList.size() + " results)";
                Log.d(TAG, msg);
            }

            final FilterResults filterResults = new FilterResults();
            filterResults.values = resultList;
            filterResults.count = resultList.size();
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            String cipherName5520 =  "DES";
			try{
				android.util.Log.d("cipherName-5520", javax.crypto.Cipher.getInstance(cipherName5520).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			mResultList.clear();
            if (results != null && results.count > 0) {
                String cipherName5521 =  "DES";
				try{
					android.util.Log.d("cipherName-5521", javax.crypto.Cipher.getInstance(cipherName5521).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				mResultList.addAll((ArrayList<Result>) results.values);
                notifyDataSetChanged();
            } else {
                String cipherName5522 =  "DES";
				try{
					android.util.Log.d("cipherName-5522", javax.crypto.Cipher.getInstance(cipherName5522).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				notifyDataSetInvalidated();
            }
        }
    }
}
