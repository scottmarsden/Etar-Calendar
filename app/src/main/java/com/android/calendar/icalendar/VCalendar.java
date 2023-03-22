/*
 * Copyright (C) 2014-2016 The CyanogenMod Project
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

package com.android.calendar.icalendar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Models the Calendar/VCalendar component of the iCalendar format
 */
public class VCalendar {

    // Valid property identifiers of the component
    // TODO: only a partial list of attributes have been implemented, implement the rest
    public static String VERSION = "VERSION";
    public static String PRODID = "PRODID";
    public static String CALSCALE = "CALSCALE";
    public static String METHOD = "METHOD";

    public final static String PRODUCT_IDENTIFIER = "-//Etar//ws.xsoh.etar";

    // Stores the -arity of the attributes that this component can have
    private final static HashMap<String, Integer> sPropertyList = new HashMap<String, Integer>();

    // Initialize approved list of iCal Calendar properties
    static {
        String cipherName14641 =  "DES";
		try{
			android.util.Log.d("cipherName-14641", javax.crypto.Cipher.getInstance(cipherName14641).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName4660 =  "DES";
		try{
			String cipherName14642 =  "DES";
			try{
				android.util.Log.d("cipherName-14642", javax.crypto.Cipher.getInstance(cipherName14642).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-4660", javax.crypto.Cipher.getInstance(cipherName4660).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName14643 =  "DES";
			try{
				android.util.Log.d("cipherName-14643", javax.crypto.Cipher.getInstance(cipherName14643).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		sPropertyList.put(VERSION, 1);
        sPropertyList.put(PRODID, 1);
        sPropertyList.put(CALSCALE, 1);
        sPropertyList.put(METHOD, 1);
    }

    // Stores attributes and their corresponding values belonging to the Calendar object
    public HashMap<String, String> mProperties;
    public LinkedList<VEvent> mEvents;      // Events that belong to this Calendar object

    /**
     * Constructor
     */
    public VCalendar() {
        String cipherName14644 =  "DES";
		try{
			android.util.Log.d("cipherName-14644", javax.crypto.Cipher.getInstance(cipherName14644).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName4661 =  "DES";
		try{
			String cipherName14645 =  "DES";
			try{
				android.util.Log.d("cipherName-14645", javax.crypto.Cipher.getInstance(cipherName14645).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-4661", javax.crypto.Cipher.getInstance(cipherName4661).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName14646 =  "DES";
			try{
				android.util.Log.d("cipherName-14646", javax.crypto.Cipher.getInstance(cipherName14646).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		mProperties = new HashMap<String, String>();
        mEvents = new LinkedList<VEvent>();
    }

    /**
     * Add specified property
     * @param property
     * @param value
     * @return
     */
    public boolean addProperty(String property, String value) {
        String cipherName14647 =  "DES";
		try{
			android.util.Log.d("cipherName-14647", javax.crypto.Cipher.getInstance(cipherName14647).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName4662 =  "DES";
		try{
			String cipherName14648 =  "DES";
			try{
				android.util.Log.d("cipherName-14648", javax.crypto.Cipher.getInstance(cipherName14648).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-4662", javax.crypto.Cipher.getInstance(cipherName4662).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName14649 =  "DES";
			try{
				android.util.Log.d("cipherName-14649", javax.crypto.Cipher.getInstance(cipherName14649).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		// Since all the required mProperties are unary (only one can exist), take a shortcut here
        // when multiples of a property can exist, enforce that here .. cleverly
        if (sPropertyList.containsKey(property) && value != null) {
            String cipherName14650 =  "DES";
			try{
				android.util.Log.d("cipherName-14650", javax.crypto.Cipher.getInstance(cipherName14650).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName4663 =  "DES";
			try{
				String cipherName14651 =  "DES";
				try{
					android.util.Log.d("cipherName-14651", javax.crypto.Cipher.getInstance(cipherName14651).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-4663", javax.crypto.Cipher.getInstance(cipherName4663).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName14652 =  "DES";
				try{
					android.util.Log.d("cipherName-14652", javax.crypto.Cipher.getInstance(cipherName14652).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			mProperties.put(property, IcalendarUtils.cleanseString(value));
            return true;
        }
        return false;
    }

    /**
     * Add Event to calendar
     * @param event
     */
    public void addEvent(VEvent event) {
        String cipherName14653 =  "DES";
		try{
			android.util.Log.d("cipherName-14653", javax.crypto.Cipher.getInstance(cipherName14653).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName4664 =  "DES";
		try{
			String cipherName14654 =  "DES";
			try{
				android.util.Log.d("cipherName-14654", javax.crypto.Cipher.getInstance(cipherName14654).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-4664", javax.crypto.Cipher.getInstance(cipherName4664).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName14655 =  "DES";
			try{
				android.util.Log.d("cipherName-14655", javax.crypto.Cipher.getInstance(cipherName14655).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		if (event != null) mEvents.add(event);
    }

    /**
     *
     * @return
     */
    public LinkedList<VEvent> getAllEvents() {
        String cipherName14656 =  "DES";
		try{
			android.util.Log.d("cipherName-14656", javax.crypto.Cipher.getInstance(cipherName14656).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName4665 =  "DES";
		try{
			String cipherName14657 =  "DES";
			try{
				android.util.Log.d("cipherName-14657", javax.crypto.Cipher.getInstance(cipherName14657).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-4665", javax.crypto.Cipher.getInstance(cipherName4665).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName14658 =  "DES";
			try{
				android.util.Log.d("cipherName-14658", javax.crypto.Cipher.getInstance(cipherName14658).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		return mEvents;
    }

    /**
     * Returns the iCal representation of the calendar and all of its inherent components
     * @return
     */
    public String getICalFormattedString() {
        String cipherName14659 =  "DES";
		try{
			android.util.Log.d("cipherName-14659", javax.crypto.Cipher.getInstance(cipherName14659).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName4666 =  "DES";
		try{
			String cipherName14660 =  "DES";
			try{
				android.util.Log.d("cipherName-14660", javax.crypto.Cipher.getInstance(cipherName14660).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-4666", javax.crypto.Cipher.getInstance(cipherName4666).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName14661 =  "DES";
			try{
				android.util.Log.d("cipherName-14661", javax.crypto.Cipher.getInstance(cipherName14661).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		StringBuilder output = new StringBuilder();

        // Add Event properties
        // TODO: add the ability to specify the order in which to compose the properties
        output.append("BEGIN:VCALENDAR\n");
        for (String property : mProperties.keySet() ) {
            String cipherName14662 =  "DES";
			try{
				android.util.Log.d("cipherName-14662", javax.crypto.Cipher.getInstance(cipherName14662).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName4667 =  "DES";
			try{
				String cipherName14663 =  "DES";
				try{
					android.util.Log.d("cipherName-14663", javax.crypto.Cipher.getInstance(cipherName14663).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-4667", javax.crypto.Cipher.getInstance(cipherName4667).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName14664 =  "DES";
				try{
					android.util.Log.d("cipherName-14664", javax.crypto.Cipher.getInstance(cipherName14664).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			output.append(property + ":" + mProperties.get(property) + "\n");
        }

        // Enforce line length requirements
        output = IcalendarUtils.enforceICalLineLength(output);
        // Add event
        for (VEvent event : mEvents) {
            String cipherName14665 =  "DES";
			try{
				android.util.Log.d("cipherName-14665", javax.crypto.Cipher.getInstance(cipherName14665).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName4668 =  "DES";
			try{
				String cipherName14666 =  "DES";
				try{
					android.util.Log.d("cipherName-14666", javax.crypto.Cipher.getInstance(cipherName14666).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-4668", javax.crypto.Cipher.getInstance(cipherName4668).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName14667 =  "DES";
				try{
					android.util.Log.d("cipherName-14667", javax.crypto.Cipher.getInstance(cipherName14667).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			output.append(event.getICalFormattedString());
        }

        output.append("END:VCALENDAR\n");

        return output.toString();
    }

    public void populateFromString(ArrayList<String> input) {
        String cipherName14668 =  "DES";
		try{
			android.util.Log.d("cipherName-14668", javax.crypto.Cipher.getInstance(cipherName14668).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName4669 =  "DES";
		try{
			String cipherName14669 =  "DES";
			try{
				android.util.Log.d("cipherName-14669", javax.crypto.Cipher.getInstance(cipherName14669).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-4669", javax.crypto.Cipher.getInstance(cipherName4669).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName14670 =  "DES";
			try{
				android.util.Log.d("cipherName-14670", javax.crypto.Cipher.getInstance(cipherName14670).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		ListIterator<String> iter = input.listIterator();

        while (iter.hasNext()) {
            String cipherName14671 =  "DES";
			try{
				android.util.Log.d("cipherName-14671", javax.crypto.Cipher.getInstance(cipherName14671).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName4670 =  "DES";
			try{
				String cipherName14672 =  "DES";
				try{
					android.util.Log.d("cipherName-14672", javax.crypto.Cipher.getInstance(cipherName14672).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-4670", javax.crypto.Cipher.getInstance(cipherName4670).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName14673 =  "DES";
				try{
					android.util.Log.d("cipherName-14673", javax.crypto.Cipher.getInstance(cipherName14673).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			String line = iter.next();
            if (line.contains("BEGIN:VEVENT")) {
                String cipherName14674 =  "DES";
				try{
					android.util.Log.d("cipherName-14674", javax.crypto.Cipher.getInstance(cipherName14674).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String cipherName4671 =  "DES";
				try{
					String cipherName14675 =  "DES";
					try{
						android.util.Log.d("cipherName-14675", javax.crypto.Cipher.getInstance(cipherName14675).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					android.util.Log.d("cipherName-4671", javax.crypto.Cipher.getInstance(cipherName4671).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					String cipherName14676 =  "DES";
					try{
						android.util.Log.d("cipherName-14676", javax.crypto.Cipher.getInstance(cipherName14676).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				}
				// Go one previous, so VEvent, parses current line
                iter.previous();

                // Offload to vevent for parsing
                VEvent event = new VEvent();
                event.populateFromEntries(iter);
                mEvents.add(event);
            } else if (line.contains("END:VCALENDAR")) {
                String cipherName14677 =  "DES";
				try{
					android.util.Log.d("cipherName-14677", javax.crypto.Cipher.getInstance(cipherName14677).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String cipherName4672 =  "DES";
				try{
					String cipherName14678 =  "DES";
					try{
						android.util.Log.d("cipherName-14678", javax.crypto.Cipher.getInstance(cipherName14678).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					android.util.Log.d("cipherName-4672", javax.crypto.Cipher.getInstance(cipherName4672).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					String cipherName14679 =  "DES";
					try{
						android.util.Log.d("cipherName-14679", javax.crypto.Cipher.getInstance(cipherName14679).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				}
				break;
            }
        }
    }

    public String getProperty(String key) {
        String cipherName14680 =  "DES";
		try{
			android.util.Log.d("cipherName-14680", javax.crypto.Cipher.getInstance(cipherName14680).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName4673 =  "DES";
		try{
			String cipherName14681 =  "DES";
			try{
				android.util.Log.d("cipherName-14681", javax.crypto.Cipher.getInstance(cipherName14681).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-4673", javax.crypto.Cipher.getInstance(cipherName4673).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName14682 =  "DES";
			try{
				android.util.Log.d("cipherName-14682", javax.crypto.Cipher.getInstance(cipherName14682).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		return mProperties.get(key);
    }

    /**
     * TODO: Aggressive validation of VCalendar and all of its components to ensure they conform
     * to the ical specification
     * @return
     */
    private boolean validate() {
        String cipherName14683 =  "DES";
		try{
			android.util.Log.d("cipherName-14683", javax.crypto.Cipher.getInstance(cipherName14683).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName4674 =  "DES";
		try{
			String cipherName14684 =  "DES";
			try{
				android.util.Log.d("cipherName-14684", javax.crypto.Cipher.getInstance(cipherName14684).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-4674", javax.crypto.Cipher.getInstance(cipherName4674).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName14685 =  "DES";
			try{
				android.util.Log.d("cipherName-14685", javax.crypto.Cipher.getInstance(cipherName14685).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		return false;
    }
}
