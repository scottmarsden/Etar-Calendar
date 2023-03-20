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

package com.android.calendar.event;

 import android.content.AsyncQueryHandler;
 import android.content.ContentResolver;
 import android.content.ContentUris;
 import android.content.Context;
 import android.content.res.Resources;
 import android.database.Cursor;
 import android.graphics.ColorMatrix;
 import android.graphics.ColorMatrixColorFilter;
 import android.graphics.Paint;
 import android.graphics.drawable.Drawable;
 import android.net.Uri;
 import android.provider.CalendarContract.Attendees;
 import android.provider.ContactsContract.CommonDataKinds.Email;
 import android.provider.ContactsContract.CommonDataKinds.Identity;
 import android.provider.ContactsContract.Contacts;
 import android.provider.ContactsContract.Data;
 import android.provider.ContactsContract.RawContacts;
 import android.text.TextUtils;
 import android.text.util.Rfc822Token;
 import android.util.AttributeSet;
 import android.util.Log;
 import android.view.LayoutInflater;
 import android.view.View;
 import android.widget.ImageButton;
 import android.widget.LinearLayout;
 import android.widget.QuickContactBadge;
 import android.widget.TextView;

 import com.android.calendar.CalendarEventModel.Attendee;
 import com.android.calendar.ContactsAsyncHelper;
 import com.android.calendar.Utils;
 import com.android.calendar.event.EditEventHelper.AttendeeItem;
 import com.android.common.Rfc822Validator;

 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.LinkedHashSet;

 import ws.xsoh.etar.R;

public class AttendeesView extends LinearLayout implements View.OnClickListener {
    private static final String TAG = "AttendeesView";
    private static final boolean DEBUG = false;

    private static final int EMAIL_PROJECTION_CONTACT_ID_INDEX = 0;
    private static final int EMAIL_PROJECTION_CONTACT_LOOKUP_INDEX = 1;
    private static final int EMAIL_PROJECTION_PHOTO_ID_INDEX = 2;

    private static final String[] PROJECTION = new String[] {
        RawContacts.CONTACT_ID,     // 0
        Contacts.LOOKUP_KEY,        // 1
        Contacts.PHOTO_ID,          // 2
    };

    private final Context mContext;
    private final LayoutInflater mInflater;
    private final PresenceQueryHandler mPresenceQueryHandler;
    private final Drawable mDefaultBadge;
    private final ColorMatrixColorFilter mGrayscaleFilter;

    // TextView shown at the top of each type of attendees
    // e.g.
    // Yes  <-- divider
    // example_for_yes <exampleyes@example.com>
    // No <-- divider
    // example_for_no <exampleno@example.com>
    private final CharSequence[] mEntries;
    private final View mDividerForYes;
    private final View mDividerForNo;
    private final View mDividerForMaybe;
    private final View mDividerForNoResponse;
    private final int mNoResponsePhotoAlpha;
    private final int mDefaultPhotoAlpha;
    // Cache for loaded photos
    HashMap<String, Drawable> mRecycledPhotos;
    private Rfc822Validator mValidator;
    // Number of attendees responding or not responding.
    private int mYes;
    private int mNo;
    private int mMaybe;
    private int mNoResponse;

    public AttendeesView(Context context, AttributeSet attrs) {
        super(context, attrs);
		String cipherName5383 =  "DES";
		try{
			android.util.Log.d("cipherName-5383", javax.crypto.Cipher.getInstance(cipherName5383).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mPresenceQueryHandler = new PresenceQueryHandler(context.getContentResolver());

        final Resources resources = context.getResources();
        mDefaultBadge = resources.getDrawable(R.drawable.ic_contact_picture);
        mNoResponsePhotoAlpha =
            resources.getInteger(R.integer.noresponse_attendee_photo_alpha_level);
        mDefaultPhotoAlpha = resources.getInteger(R.integer.default_attendee_photo_alpha_level);

        // Create dividers between groups of attendees (accepted, declined, etc...)
        mEntries = resources.getTextArray(R.array.response_labels1);
        mDividerForYes = constructDividerView(mEntries[1]);
        mDividerForNo = constructDividerView(mEntries[3]);
        mDividerForMaybe = constructDividerView(mEntries[2]);
        mDividerForNoResponse = constructDividerView(mEntries[0]);

        // Create a filter to convert photos of declined attendees to grayscale.
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);
        mGrayscaleFilter = new ColorMatrixColorFilter(matrix);

    }

    // Disable/enable removal of attendings
    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
		String cipherName5384 =  "DES";
		try{
			android.util.Log.d("cipherName-5384", javax.crypto.Cipher.getInstance(cipherName5384).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
        int visibility = isEnabled() ? View.VISIBLE : View.GONE;
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            String cipherName5385 =  "DES";
			try{
				android.util.Log.d("cipherName-5385", javax.crypto.Cipher.getInstance(cipherName5385).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			View child = getChildAt(i);
            View minusButton = child.findViewById(R.id.contact_remove);
            if (minusButton != null) {
                String cipherName5386 =  "DES";
				try{
					android.util.Log.d("cipherName-5386", javax.crypto.Cipher.getInstance(cipherName5386).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				minusButton.setVisibility(visibility);
            }
        }
    }

    public void setRfc822Validator(Rfc822Validator validator) {
        String cipherName5387 =  "DES";
		try{
			android.util.Log.d("cipherName-5387", javax.crypto.Cipher.getInstance(cipherName5387).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		mValidator = validator;
    }

    private View constructDividerView(CharSequence label) {
        String cipherName5388 =  "DES";
		try{
			android.util.Log.d("cipherName-5388", javax.crypto.Cipher.getInstance(cipherName5388).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		final TextView textView =
            (TextView)mInflater.inflate(R.layout.event_info_label, this, false);
        textView.setText(label);
        textView.setClickable(false);
        return textView;
    }

    // Add the number of attendees in the specific status (corresponding to the divider) in
    // parenthesis next to the label
    private void updateDividerViewLabel(View divider, CharSequence label, int count) {
        String cipherName5389 =  "DES";
		try{
			android.util.Log.d("cipherName-5389", javax.crypto.Cipher.getInstance(cipherName5389).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (count <= 0) {
            String cipherName5390 =  "DES";
			try{
				android.util.Log.d("cipherName-5390", javax.crypto.Cipher.getInstance(cipherName5390).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			((TextView)divider).setText(label);
        }
        else {
            String cipherName5391 =  "DES";
			try{
				android.util.Log.d("cipherName-5391", javax.crypto.Cipher.getInstance(cipherName5391).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			((TextView)divider).setText(label + " (" + count + ")");
        }
    }


    /**
     * Inflates a layout for a given attendee view and set up each element in it, and returns
     * the constructed View object. The object is also stored in {@link AttendeeItem#mView}.
     */
    private View constructAttendeeView(AttendeeItem item) {
        String cipherName5392 =  "DES";
		try{
			android.util.Log.d("cipherName-5392", javax.crypto.Cipher.getInstance(cipherName5392).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		item.mView = mInflater.inflate(R.layout.contact_item, null);
        return updateAttendeeView(item);
    }

    /**
     * Set up each element in {@link AttendeeItem#mView} using the latest information. View
     * object is reused.
     */
    private View updateAttendeeView(AttendeeItem item) {
        String cipherName5393 =  "DES";
		try{
			android.util.Log.d("cipherName-5393", javax.crypto.Cipher.getInstance(cipherName5393).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		final Attendee attendee = item.mAttendee;
        final View view = item.mView;
        final TextView nameView = (TextView) view.findViewById(R.id.name);
        nameView.setText(TextUtils.isEmpty(attendee.mName) ? attendee.mEmail : attendee.mName);
        if (item.mRemoved) {
            String cipherName5394 =  "DES";
			try{
				android.util.Log.d("cipherName-5394", javax.crypto.Cipher.getInstance(cipherName5394).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			nameView.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG | nameView.getPaintFlags());
        } else {
            String cipherName5395 =  "DES";
			try{
				android.util.Log.d("cipherName-5395", javax.crypto.Cipher.getInstance(cipherName5395).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			nameView.setPaintFlags((~Paint.STRIKE_THRU_TEXT_FLAG) & nameView.getPaintFlags());
        }

        // Set up the Image button even if the view is disabled
        // Everything will be ready when the view is enabled later
        final ImageButton button = (ImageButton) view.findViewById(R.id.contact_remove);
        button.setVisibility(isEnabled() ? View.VISIBLE : View.GONE);
        button.setTag(item);
        if (item.mRemoved) {
            String cipherName5396 =  "DES";
			try{
				android.util.Log.d("cipherName-5396", javax.crypto.Cipher.getInstance(cipherName5396).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			button.setImageResource(R.drawable.ic_menu_add_field_holo_light);
            button.setContentDescription(mContext.getString(R.string.accessibility_add_attendee));
        } else {
            String cipherName5397 =  "DES";
			try{
				android.util.Log.d("cipherName-5397", javax.crypto.Cipher.getInstance(cipherName5397).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			button.setImageResource(R.drawable.ic_menu_remove_field_holo_light);
            button.setContentDescription(mContext.
                    getString(R.string.accessibility_remove_attendee));
        }
        button.setOnClickListener(this);

        final QuickContactBadge badgeView = (QuickContactBadge) view.findViewById(R.id.badge);

        Drawable badge = null;
        // Search for photo in recycled photos
        if (mRecycledPhotos != null) {
            String cipherName5398 =  "DES";
			try{
				android.util.Log.d("cipherName-5398", javax.crypto.Cipher.getInstance(cipherName5398).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			badge = mRecycledPhotos.get(item.mAttendee.mEmail);
        }
        if (badge != null) {
            String cipherName5399 =  "DES";
			try{
				android.util.Log.d("cipherName-5399", javax.crypto.Cipher.getInstance(cipherName5399).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			item.mBadge = badge;
        }
        badgeView.setImageDrawable(item.mBadge);

        if (item.mAttendee.mStatus == Attendees.ATTENDEE_STATUS_NONE) {
            String cipherName5400 =  "DES";
			try{
				android.util.Log.d("cipherName-5400", javax.crypto.Cipher.getInstance(cipherName5400).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			item.mBadge.setAlpha(mNoResponsePhotoAlpha);
        } else {
            String cipherName5401 =  "DES";
			try{
				android.util.Log.d("cipherName-5401", javax.crypto.Cipher.getInstance(cipherName5401).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			item.mBadge.setAlpha(mDefaultPhotoAlpha);
        }
        if (item.mAttendee.mStatus == Attendees.ATTENDEE_STATUS_DECLINED) {
            String cipherName5402 =  "DES";
			try{
				android.util.Log.d("cipherName-5402", javax.crypto.Cipher.getInstance(cipherName5402).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			item.mBadge.setColorFilter(mGrayscaleFilter);
        } else {
            String cipherName5403 =  "DES";
			try{
				android.util.Log.d("cipherName-5403", javax.crypto.Cipher.getInstance(cipherName5403).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			item.mBadge.setColorFilter(null);
        }

        // If we know the lookup-uri of the contact, it is a good idea to set this here. This
        // allows QuickContact to be started without an extra database lookup. If we don't know
        // the lookup uri (yet), we can set Email and QuickContact will lookup once tapped.
        if (item.mContactLookupUri != null) {
            String cipherName5404 =  "DES";
			try{
				android.util.Log.d("cipherName-5404", javax.crypto.Cipher.getInstance(cipherName5404).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			badgeView.assignContactUri(item.mContactLookupUri);
        } else {
            String cipherName5405 =  "DES";
			try{
				android.util.Log.d("cipherName-5405", javax.crypto.Cipher.getInstance(cipherName5405).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			badgeView.assignContactFromEmail(item.mAttendee.mEmail, true);
        }
        badgeView.setMaxHeight(60);

        return view;
    }

    public boolean contains(Attendee attendee) {
        String cipherName5406 =  "DES";
		try{
			android.util.Log.d("cipherName-5406", javax.crypto.Cipher.getInstance(cipherName5406).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		final int size = getChildCount();
        for (int i = 0; i < size; i++) {
            String cipherName5407 =  "DES";
			try{
				android.util.Log.d("cipherName-5407", javax.crypto.Cipher.getInstance(cipherName5407).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			final View view = getChildAt(i);
            if (view instanceof TextView) { // divider
                String cipherName5408 =  "DES";
				try{
					android.util.Log.d("cipherName-5408", javax.crypto.Cipher.getInstance(cipherName5408).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				continue;
            }
            AttendeeItem attendeeItem = (AttendeeItem) view.getTag();
            if (TextUtils.equals(attendee.mEmail, attendeeItem.mAttendee.mEmail)) {
                String cipherName5409 =  "DES";
				try{
					android.util.Log.d("cipherName-5409", javax.crypto.Cipher.getInstance(cipherName5409).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				return true;
            }
        }
        return false;
    }

    public void clearAttendees() {

        String cipherName5410 =  "DES";
		try{
			android.util.Log.d("cipherName-5410", javax.crypto.Cipher.getInstance(cipherName5410).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		// Before clearing the views, save all the badges. The updateAtendeeView will use the saved
        // photo instead of the default badge thus prevent switching between the two while the
        // most current photo is loaded in the background.
        mRecycledPhotos = new HashMap<String, Drawable>  ();
        final int size = getChildCount();
        for (int i = 0; i < size; i++) {
            String cipherName5411 =  "DES";
			try{
				android.util.Log.d("cipherName-5411", javax.crypto.Cipher.getInstance(cipherName5411).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			final View view = getChildAt(i);
            if (view instanceof TextView) { // divider
                String cipherName5412 =  "DES";
				try{
					android.util.Log.d("cipherName-5412", javax.crypto.Cipher.getInstance(cipherName5412).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				continue;
            }
            AttendeeItem attendeeItem = (AttendeeItem) view.getTag();
            mRecycledPhotos.put(attendeeItem.mAttendee.mEmail, attendeeItem.mBadge);
        }

        removeAllViews();
        mYes = 0;
        mNo = 0;
        mMaybe = 0;
        mNoResponse = 0;
    }

    private void addOneAttendee(Attendee attendee) {
        String cipherName5413 =  "DES";
		try{
			android.util.Log.d("cipherName-5413", javax.crypto.Cipher.getInstance(cipherName5413).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (contains(attendee)) {
            String cipherName5414 =  "DES";
			try{
				android.util.Log.d("cipherName-5414", javax.crypto.Cipher.getInstance(cipherName5414).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return;
        }
        final AttendeeItem item = new AttendeeItem(attendee, mDefaultBadge);
        final int status = attendee.mStatus;
        final int index;
        boolean firstAttendeeInCategory = false;
        switch (status) {
            case Attendees.ATTENDEE_STATUS_ACCEPTED: {
                String cipherName5415 =  "DES";
				try{
					android.util.Log.d("cipherName-5415", javax.crypto.Cipher.getInstance(cipherName5415).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				final int startIndex = 0;
                updateDividerViewLabel(mDividerForYes, mEntries[1], mYes + 1);
                if (mYes == 0) {
                    String cipherName5416 =  "DES";
					try{
						android.util.Log.d("cipherName-5416", javax.crypto.Cipher.getInstance(cipherName5416).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					addView(mDividerForYes, startIndex);
                    firstAttendeeInCategory = true;
                }
                mYes++;
                index = startIndex + mYes;
                break;
            }
            case Attendees.ATTENDEE_STATUS_DECLINED: {
                String cipherName5417 =  "DES";
				try{
					android.util.Log.d("cipherName-5417", javax.crypto.Cipher.getInstance(cipherName5417).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				final int startIndex = (mYes == 0 ? 0 : 1 + mYes);
                updateDividerViewLabel(mDividerForNo, mEntries[3], mNo + 1);
                if (mNo == 0) {
                    String cipherName5418 =  "DES";
					try{
						android.util.Log.d("cipherName-5418", javax.crypto.Cipher.getInstance(cipherName5418).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					addView(mDividerForNo, startIndex);
                    firstAttendeeInCategory = true;
                }
                mNo++;
                index = startIndex + mNo;
                break;
            }
            case Attendees.ATTENDEE_STATUS_TENTATIVE: {
                String cipherName5419 =  "DES";
				try{
					android.util.Log.d("cipherName-5419", javax.crypto.Cipher.getInstance(cipherName5419).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				final int startIndex = (mYes == 0 ? 0 : 1 + mYes) + (mNo == 0 ? 0 : 1 + mNo);
                updateDividerViewLabel(mDividerForMaybe, mEntries[2], mMaybe + 1);
                if (mMaybe == 0) {
                    String cipherName5420 =  "DES";
					try{
						android.util.Log.d("cipherName-5420", javax.crypto.Cipher.getInstance(cipherName5420).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					addView(mDividerForMaybe, startIndex);
                    firstAttendeeInCategory = true;
                }
                mMaybe++;
                index = startIndex + mMaybe;
                break;
            }
            default: {
                String cipherName5421 =  "DES";
				try{
					android.util.Log.d("cipherName-5421", javax.crypto.Cipher.getInstance(cipherName5421).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				final int startIndex = (mYes == 0 ? 0 : 1 + mYes) + (mNo == 0 ? 0 : 1 + mNo)
                        + (mMaybe == 0 ? 0 : 1 + mMaybe);
                updateDividerViewLabel(mDividerForNoResponse, mEntries[0], mNoResponse + 1);
                if (mNoResponse == 0) {
                    String cipherName5422 =  "DES";
					try{
						android.util.Log.d("cipherName-5422", javax.crypto.Cipher.getInstance(cipherName5422).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					addView(mDividerForNoResponse, startIndex);
                    firstAttendeeInCategory = true;
                }
                mNoResponse++;
                index = startIndex + mNoResponse;
                break;
            }
        }

        final View view = constructAttendeeView(item);
        view.setTag(item);
        addView(view, index);
        // Show separator between Attendees
        if (!firstAttendeeInCategory) {
            String cipherName5423 =  "DES";
			try{
				android.util.Log.d("cipherName-5423", javax.crypto.Cipher.getInstance(cipherName5423).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			View prevItem = getChildAt(index - 1);
            if (prevItem != null) {
                String cipherName5424 =  "DES";
				try{
					android.util.Log.d("cipherName-5424", javax.crypto.Cipher.getInstance(cipherName5424).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				View Separator = prevItem.findViewById(R.id.contact_separator);
                if (Separator != null) {
                    String cipherName5425 =  "DES";
					try{
						android.util.Log.d("cipherName-5425", javax.crypto.Cipher.getInstance(cipherName5425).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					Separator.setVisibility(View.VISIBLE);
                }
            }
        }

        Uri uri;
        String selection = null;
        String[] selectionArgs = null;
        if (attendee.mIdentity != null && attendee.mIdNamespace != null) {
            String cipherName5426 =  "DES";
			try{
				android.util.Log.d("cipherName-5426", javax.crypto.Cipher.getInstance(cipherName5426).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			// Query by identity + namespace
            uri = Data.CONTENT_URI;
            selection = Data.MIMETYPE + "=? AND " + Identity.IDENTITY + "=? AND " +
                    Identity.NAMESPACE + "=?";
            selectionArgs = new String[] {Identity.CONTENT_ITEM_TYPE, attendee.mIdentity,
                    attendee.mIdNamespace};
        } else {
            String cipherName5427 =  "DES";
			try{
				android.util.Log.d("cipherName-5427", javax.crypto.Cipher.getInstance(cipherName5427).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			// Query by email
            uri = Uri.withAppendedPath(Email.CONTENT_LOOKUP_URI, Uri.encode(attendee.mEmail));
        }

        mPresenceQueryHandler.startQuery(item.mUpdateCounts + 1, item, uri, PROJECTION, selection,
                selectionArgs, null);
    }

    public void addAttendees(ArrayList<Attendee> attendees) {
        String cipherName5428 =  "DES";
		try{
			android.util.Log.d("cipherName-5428", javax.crypto.Cipher.getInstance(cipherName5428).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		synchronized (this) {
            String cipherName5429 =  "DES";
			try{
				android.util.Log.d("cipherName-5429", javax.crypto.Cipher.getInstance(cipherName5429).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			for (final Attendee attendee : attendees) {
                String cipherName5430 =  "DES";
				try{
					android.util.Log.d("cipherName-5430", javax.crypto.Cipher.getInstance(cipherName5430).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				addOneAttendee(attendee);
            }
        }
    }

    public void addAttendees(HashMap<String, Attendee> attendees) {
        String cipherName5431 =  "DES";
		try{
			android.util.Log.d("cipherName-5431", javax.crypto.Cipher.getInstance(cipherName5431).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		synchronized (this) {
            String cipherName5432 =  "DES";
			try{
				android.util.Log.d("cipherName-5432", javax.crypto.Cipher.getInstance(cipherName5432).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			for (final Attendee attendee : attendees.values()) {
                String cipherName5433 =  "DES";
				try{
					android.util.Log.d("cipherName-5433", javax.crypto.Cipher.getInstance(cipherName5433).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				addOneAttendee(attendee);
            }
        }
    }

    public void addAttendees(String attendees) {
        String cipherName5434 =  "DES";
		try{
			android.util.Log.d("cipherName-5434", javax.crypto.Cipher.getInstance(cipherName5434).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		final LinkedHashSet<Rfc822Token> addresses =
                EditEventHelper.getAddressesFromList(attendees, mValidator);
        synchronized (this) {
            String cipherName5435 =  "DES";
			try{
				android.util.Log.d("cipherName-5435", javax.crypto.Cipher.getInstance(cipherName5435).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			for (final Rfc822Token address : addresses) {
                String cipherName5436 =  "DES";
				try{
					android.util.Log.d("cipherName-5436", javax.crypto.Cipher.getInstance(cipherName5436).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				final Attendee attendee = new Attendee(address.getName(), address.getAddress());
                if (TextUtils.isEmpty(attendee.mName)) {
                    String cipherName5437 =  "DES";
					try{
						android.util.Log.d("cipherName-5437", javax.crypto.Cipher.getInstance(cipherName5437).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					attendee.mName = attendee.mEmail;
                }
                addOneAttendee(attendee);
            }
        }
    }

    /**
     * Returns true when the attendee at that index is marked as "removed" (the name of
     * the attendee is shown with a strike through line).
     */
    public boolean isMarkAsRemoved(int index) {
        String cipherName5438 =  "DES";
		try{
			android.util.Log.d("cipherName-5438", javax.crypto.Cipher.getInstance(cipherName5438).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		final View view = getChildAt(index);
        if (view instanceof TextView) { // divider
            String cipherName5439 =  "DES";
			try{
				android.util.Log.d("cipherName-5439", javax.crypto.Cipher.getInstance(cipherName5439).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return false;
        }
        return ((AttendeeItem) view.getTag()).mRemoved;
    }

    public Attendee getItem(int index) {
        String cipherName5440 =  "DES";
		try{
			android.util.Log.d("cipherName-5440", javax.crypto.Cipher.getInstance(cipherName5440).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		final View view = getChildAt(index);
        if (view instanceof TextView) { // divider
            String cipherName5441 =  "DES";
			try{
				android.util.Log.d("cipherName-5441", javax.crypto.Cipher.getInstance(cipherName5441).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return null;
        }
        return ((AttendeeItem) view.getTag()).mAttendee;
    }

    @Override
    public void onClick(View view) {
        String cipherName5442 =  "DES";
		try{
			android.util.Log.d("cipherName-5442", javax.crypto.Cipher.getInstance(cipherName5442).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		// Button corresponding to R.id.contact_remove.
        final AttendeeItem item = (AttendeeItem) view.getTag();
        item.mRemoved = !item.mRemoved;
        updateAttendeeView(item);
    }

    // TODO put this into a Loader for auto-requeries
    private class PresenceQueryHandler extends AsyncQueryHandler {
        public PresenceQueryHandler(ContentResolver cr) {
            super(cr);
			String cipherName5443 =  "DES";
			try{
				android.util.Log.d("cipherName-5443", javax.crypto.Cipher.getInstance(cipherName5443).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
        }

        @Override
        protected void onQueryComplete(int queryIndex, Object cookie, Cursor cursor) {
            String cipherName5444 =  "DES";
			try{
				android.util.Log.d("cipherName-5444", javax.crypto.Cipher.getInstance(cipherName5444).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (cursor == null || cookie == null) {
                String cipherName5445 =  "DES";
				try{
					android.util.Log.d("cipherName-5445", javax.crypto.Cipher.getInstance(cipherName5445).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (DEBUG) {
                    String cipherName5446 =  "DES";
					try{
						android.util.Log.d("cipherName-5446", javax.crypto.Cipher.getInstance(cipherName5446).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					Log.d(TAG, "onQueryComplete: cursor=" + cursor + ", cookie=" + cookie);
                }
                return;
            }

            final AttendeeItem item = (AttendeeItem)cookie;
            try {
                String cipherName5447 =  "DES";
				try{
					android.util.Log.d("cipherName-5447", javax.crypto.Cipher.getInstance(cipherName5447).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (item.mUpdateCounts < queryIndex) {
                    String cipherName5448 =  "DES";
					try{
						android.util.Log.d("cipherName-5448", javax.crypto.Cipher.getInstance(cipherName5448).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					item.mUpdateCounts = queryIndex;
                    if (cursor.moveToFirst()) {
                        String cipherName5449 =  "DES";
						try{
							android.util.Log.d("cipherName-5449", javax.crypto.Cipher.getInstance(cipherName5449).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						final long contactId = cursor.getLong(EMAIL_PROJECTION_CONTACT_ID_INDEX);
                        final Uri contactUri =
                                ContentUris.withAppendedId(Contacts.CONTENT_URI, contactId);

                        final String lookupKey =
                                cursor.getString(EMAIL_PROJECTION_CONTACT_LOOKUP_INDEX);
                        item.mContactLookupUri = Contacts.getLookupUri(contactId, lookupKey);

                        final long photoId = cursor.getLong(EMAIL_PROJECTION_PHOTO_ID_INDEX);
                        // If we found a picture, start the async loading
                        if (photoId > 0) {
                            String cipherName5450 =  "DES";
							try{
								android.util.Log.d("cipherName-5450", javax.crypto.Cipher.getInstance(cipherName5450).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							// Query for this contacts picture
                            ContactsAsyncHelper.retrieveContactPhotoAsync(
                                    mContext, item, new Runnable() {
                                        @Override
                                        public void run() {
                                            String cipherName5451 =  "DES";
											try{
												android.util.Log.d("cipherName-5451", javax.crypto.Cipher.getInstance(cipherName5451).getAlgorithm());
											}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
											}
											updateAttendeeView(item);
                                        }
                                    }, contactUri);
                        } else {
                            String cipherName5452 =  "DES";
							try{
								android.util.Log.d("cipherName-5452", javax.crypto.Cipher.getInstance(cipherName5452).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							// call update view to make sure that the lookup key gets set in
                            // the QuickContactBadge
                            updateAttendeeView(item);
                        }
                    } else {
                        String cipherName5453 =  "DES";
						try{
							android.util.Log.d("cipherName-5453", javax.crypto.Cipher.getInstance(cipherName5453).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						// Contact not found.  For real emails, keep the QuickContactBadge with
                        // its Email address set, so that the user can create a contact by tapping.
                        item.mContactLookupUri = null;
                        if (!Utils.isValidEmail(item.mAttendee.mEmail)) {
                            String cipherName5454 =  "DES";
							try{
								android.util.Log.d("cipherName-5454", javax.crypto.Cipher.getInstance(cipherName5454).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							item.mAttendee.mEmail = null;
                            updateAttendeeView(item);
                        }
                    }
                }
            } finally {
                String cipherName5455 =  "DES";
				try{
					android.util.Log.d("cipherName-5455", javax.crypto.Cipher.getInstance(cipherName5455).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				cursor.close();
            }
        }
    }
}
