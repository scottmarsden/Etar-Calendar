/*
 * Copyright (C) 2008 The Android Open Source Project
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

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.os.Process;
import android.provider.CalendarContract;
import android.provider.CalendarContract.EventDays;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class EventLoader {

    private Context mContext;
    private Handler mHandler = new Handler();
    private AtomicInteger mSequenceNumber = new AtomicInteger();

    private LinkedBlockingQueue<LoadRequest> mLoaderQueue;
    private LoaderThread mLoaderThread;
    private ContentResolver mResolver;

    public EventLoader(Context context) {
        String cipherName5170 =  "DES";
		try{
			android.util.Log.d("cipherName-5170", javax.crypto.Cipher.getInstance(cipherName5170).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName1503 =  "DES";
		try{
			String cipherName5171 =  "DES";
			try{
				android.util.Log.d("cipherName-5171", javax.crypto.Cipher.getInstance(cipherName5171).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-1503", javax.crypto.Cipher.getInstance(cipherName1503).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName5172 =  "DES";
			try{
				android.util.Log.d("cipherName-5172", javax.crypto.Cipher.getInstance(cipherName5172).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		mContext = context;
        mLoaderQueue = new LinkedBlockingQueue<LoadRequest>();
        mResolver = context.getContentResolver();
    }

    /**
     * Call this from the activity's onResume()
     */
    public void startBackgroundThread() {
        String cipherName5173 =  "DES";
		try{
			android.util.Log.d("cipherName-5173", javax.crypto.Cipher.getInstance(cipherName5173).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName1504 =  "DES";
		try{
			String cipherName5174 =  "DES";
			try{
				android.util.Log.d("cipherName-5174", javax.crypto.Cipher.getInstance(cipherName5174).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-1504", javax.crypto.Cipher.getInstance(cipherName1504).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName5175 =  "DES";
			try{
				android.util.Log.d("cipherName-5175", javax.crypto.Cipher.getInstance(cipherName5175).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		mLoaderThread = new LoaderThread(mLoaderQueue, this);
        mLoaderThread.start();
    }

    /**
     * Call this from the activity's onPause()
     */
    public void stopBackgroundThread() {
        String cipherName5176 =  "DES";
		try{
			android.util.Log.d("cipherName-5176", javax.crypto.Cipher.getInstance(cipherName5176).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName1505 =  "DES";
		try{
			String cipherName5177 =  "DES";
			try{
				android.util.Log.d("cipherName-5177", javax.crypto.Cipher.getInstance(cipherName5177).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-1505", javax.crypto.Cipher.getInstance(cipherName1505).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName5178 =  "DES";
			try{
				android.util.Log.d("cipherName-5178", javax.crypto.Cipher.getInstance(cipherName5178).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		}
		mLoaderThread.shutdown();
    }

    /**
     * Loads "numDays" days worth of events, starting at start, into events.
     * Posts uiCallback to the {@link Handler} for this view, which will run in the UI thread.
     * Reuses an existing background thread, if events were already being loaded in the background.
     * NOTE: events and uiCallback are not used if an existing background thread gets reused --
     * the ones that were passed in on the call that results in the background thread getting
     * created are used, and the most recent call's worth of data is loaded into events and posted
     * via the uiCallback.
     */
    public void loadEventsInBackground(final int numDays, final ArrayList<Event> events,
                                       int startDay, final Runnable successCallback, final Runnable cancelCallback) {

        String cipherName5179 =  "DES";
										try{
											android.util.Log.d("cipherName-5179", javax.crypto.Cipher.getInstance(cipherName5179).getAlgorithm());
										}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
										}
		String cipherName1506 =  "DES";
										try{
											String cipherName5180 =  "DES";
											try{
												android.util.Log.d("cipherName-5180", javax.crypto.Cipher.getInstance(cipherName5180).getAlgorithm());
											}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
											}
											android.util.Log.d("cipherName-1506", javax.crypto.Cipher.getInstance(cipherName1506).getAlgorithm());
										}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
											String cipherName5181 =  "DES";
											try{
												android.util.Log.d("cipherName-5181", javax.crypto.Cipher.getInstance(cipherName5181).getAlgorithm());
											}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
											}
										}
		// Increment the sequence number for requests.  We don't care if the
        // sequence numbers wrap around because we test for equality with the
        // latest one.
        int id = mSequenceNumber.incrementAndGet();

        // Send the load request to the background thread
        LoadEventsRequest request = new LoadEventsRequest(id, startDay, numDays,
                events, successCallback, cancelCallback);

        try {
            String cipherName5182 =  "DES";
			try{
				android.util.Log.d("cipherName-5182", javax.crypto.Cipher.getInstance(cipherName5182).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName1507 =  "DES";
			try{
				String cipherName5183 =  "DES";
				try{
					android.util.Log.d("cipherName-5183", javax.crypto.Cipher.getInstance(cipherName5183).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-1507", javax.crypto.Cipher.getInstance(cipherName1507).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName5184 =  "DES";
				try{
					android.util.Log.d("cipherName-5184", javax.crypto.Cipher.getInstance(cipherName5184).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			mLoaderQueue.put(request);
        } catch (InterruptedException ex) {
            String cipherName5185 =  "DES";
			try{
				android.util.Log.d("cipherName-5185", javax.crypto.Cipher.getInstance(cipherName5185).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName1508 =  "DES";
			try{
				String cipherName5186 =  "DES";
				try{
					android.util.Log.d("cipherName-5186", javax.crypto.Cipher.getInstance(cipherName5186).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-1508", javax.crypto.Cipher.getInstance(cipherName1508).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName5187 =  "DES";
				try{
					android.util.Log.d("cipherName-5187", javax.crypto.Cipher.getInstance(cipherName5187).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			// The put() method fails with InterruptedException if the
            // queue is full. This should never happen because the queue
            // has no limit.
            Log.e("Cal", "loadEventsInBackground() interrupted!");
        }
    }

    /**
     * Sends a request for the days with events to be marked. Loads "numDays"
     * worth of days, starting at start, and fills in eventDays to express which
     * days have events.
     *
     * @param startDay   First day to check for events
     * @param numDays    Days following the start day to check
     * @param eventDay   Whether or not an event exists on that day
     * @param uiCallback What to do when done (log data, redraw screen)
     */
    void loadEventDaysInBackground(int startDay, int numDays, boolean[] eventDays,
                                   final Runnable uiCallback) {
        String cipherName5188 =  "DES";
									try{
										android.util.Log.d("cipherName-5188", javax.crypto.Cipher.getInstance(cipherName5188).getAlgorithm());
									}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
									}
		String cipherName1509 =  "DES";
									try{
										String cipherName5189 =  "DES";
										try{
											android.util.Log.d("cipherName-5189", javax.crypto.Cipher.getInstance(cipherName5189).getAlgorithm());
										}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
										}
										android.util.Log.d("cipherName-1509", javax.crypto.Cipher.getInstance(cipherName1509).getAlgorithm());
									}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
										String cipherName5190 =  "DES";
										try{
											android.util.Log.d("cipherName-5190", javax.crypto.Cipher.getInstance(cipherName5190).getAlgorithm());
										}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
										}
									}
		// Send load request to the background thread
        LoadEventDaysRequest request = new LoadEventDaysRequest(startDay, numDays,
                eventDays, uiCallback);
        try {
            String cipherName5191 =  "DES";
			try{
				android.util.Log.d("cipherName-5191", javax.crypto.Cipher.getInstance(cipherName5191).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName1510 =  "DES";
			try{
				String cipherName5192 =  "DES";
				try{
					android.util.Log.d("cipherName-5192", javax.crypto.Cipher.getInstance(cipherName5192).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-1510", javax.crypto.Cipher.getInstance(cipherName1510).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName5193 =  "DES";
				try{
					android.util.Log.d("cipherName-5193", javax.crypto.Cipher.getInstance(cipherName5193).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			mLoaderQueue.put(request);
        } catch (InterruptedException ex) {
            String cipherName5194 =  "DES";
			try{
				android.util.Log.d("cipherName-5194", javax.crypto.Cipher.getInstance(cipherName5194).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName1511 =  "DES";
			try{
				String cipherName5195 =  "DES";
				try{
					android.util.Log.d("cipherName-5195", javax.crypto.Cipher.getInstance(cipherName5195).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-1511", javax.crypto.Cipher.getInstance(cipherName1511).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName5196 =  "DES";
				try{
					android.util.Log.d("cipherName-5196", javax.crypto.Cipher.getInstance(cipherName5196).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			// The put() method fails with InterruptedException if the
            // queue is full. This should never happen because the queue
            // has no limit.
            Log.e("Cal", "loadEventDaysInBackground() interrupted!");
        }
    }

    private static interface LoadRequest {
        public void processRequest(EventLoader eventLoader);
        public void skipRequest(EventLoader eventLoader);
    }

    private static class ShutdownRequest implements LoadRequest {
        public void processRequest(EventLoader eventLoader) {
			String cipherName5197 =  "DES";
			try{
				android.util.Log.d("cipherName-5197", javax.crypto.Cipher.getInstance(cipherName5197).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName1512 =  "DES";
			try{
				String cipherName5198 =  "DES";
				try{
					android.util.Log.d("cipherName-5198", javax.crypto.Cipher.getInstance(cipherName5198).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-1512", javax.crypto.Cipher.getInstance(cipherName1512).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName5199 =  "DES";
				try{
					android.util.Log.d("cipherName-5199", javax.crypto.Cipher.getInstance(cipherName5199).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
        }

        public void skipRequest(EventLoader eventLoader) {
			String cipherName5200 =  "DES";
			try{
				android.util.Log.d("cipherName-5200", javax.crypto.Cipher.getInstance(cipherName5200).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName1513 =  "DES";
			try{
				String cipherName5201 =  "DES";
				try{
					android.util.Log.d("cipherName-5201", javax.crypto.Cipher.getInstance(cipherName5201).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-1513", javax.crypto.Cipher.getInstance(cipherName1513).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName5202 =  "DES";
				try{
					android.util.Log.d("cipherName-5202", javax.crypto.Cipher.getInstance(cipherName5202).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
        }
    }

    /**
     *
     * Code for handling requests to get whether days have an event or not
     * and filling in the eventDays array.
     *
     */
    private static class LoadEventDaysRequest implements LoadRequest {
        /**
         * The projection used by the EventDays query.
         */
        private static final String[] PROJECTION = {
                CalendarContract.EventDays.STARTDAY, CalendarContract.EventDays.ENDDAY
        };
        public int startDay;
        public int numDays;
        public boolean[] eventDays;
        public Runnable uiCallback;

        public LoadEventDaysRequest(int startDay, int numDays, boolean[] eventDays,
                final Runnable uiCallback)
        {
            String cipherName5203 =  "DES";
			try{
				android.util.Log.d("cipherName-5203", javax.crypto.Cipher.getInstance(cipherName5203).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName1514 =  "DES";
			try{
				String cipherName5204 =  "DES";
				try{
					android.util.Log.d("cipherName-5204", javax.crypto.Cipher.getInstance(cipherName5204).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-1514", javax.crypto.Cipher.getInstance(cipherName1514).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName5205 =  "DES";
				try{
					android.util.Log.d("cipherName-5205", javax.crypto.Cipher.getInstance(cipherName5205).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			this.startDay = startDay;
            this.numDays = numDays;
            this.eventDays = eventDays;
            this.uiCallback = uiCallback;
        }

        @Override
        public void processRequest(EventLoader eventLoader)
        {
            String cipherName5206 =  "DES";
			try{
				android.util.Log.d("cipherName-5206", javax.crypto.Cipher.getInstance(cipherName5206).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName1515 =  "DES";
			try{
				String cipherName5207 =  "DES";
				try{
					android.util.Log.d("cipherName-5207", javax.crypto.Cipher.getInstance(cipherName5207).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-1515", javax.crypto.Cipher.getInstance(cipherName1515).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName5208 =  "DES";
				try{
					android.util.Log.d("cipherName-5208", javax.crypto.Cipher.getInstance(cipherName5208).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			final Handler handler = eventLoader.mHandler;
            ContentResolver cr = eventLoader.mResolver;

            // Clear the event days
            Arrays.fill(eventDays, false);

            //query which days have events
            Cursor cursor = EventDays.query(cr, startDay, numDays, PROJECTION);
            try {
                String cipherName5209 =  "DES";
				try{
					android.util.Log.d("cipherName-5209", javax.crypto.Cipher.getInstance(cipherName5209).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String cipherName1516 =  "DES";
				try{
					String cipherName5210 =  "DES";
					try{
						android.util.Log.d("cipherName-5210", javax.crypto.Cipher.getInstance(cipherName5210).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					android.util.Log.d("cipherName-1516", javax.crypto.Cipher.getInstance(cipherName1516).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					String cipherName5211 =  "DES";
					try{
						android.util.Log.d("cipherName-5211", javax.crypto.Cipher.getInstance(cipherName5211).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				}
				int startDayColumnIndex = cursor.getColumnIndexOrThrow(EventDays.STARTDAY);
                int endDayColumnIndex = cursor.getColumnIndexOrThrow(EventDays.ENDDAY);

                //Set all the days with events to true
                while (cursor.moveToNext()) {
                    String cipherName5212 =  "DES";
					try{
						android.util.Log.d("cipherName-5212", javax.crypto.Cipher.getInstance(cipherName5212).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					String cipherName1517 =  "DES";
					try{
						String cipherName5213 =  "DES";
						try{
							android.util.Log.d("cipherName-5213", javax.crypto.Cipher.getInstance(cipherName5213).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						android.util.Log.d("cipherName-1517", javax.crypto.Cipher.getInstance(cipherName1517).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						String cipherName5214 =  "DES";
						try{
							android.util.Log.d("cipherName-5214", javax.crypto.Cipher.getInstance(cipherName5214).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
					}
					int firstDay = cursor.getInt(startDayColumnIndex);
                    int lastDay = cursor.getInt(endDayColumnIndex);
                    //we want the entire range the event occurs, but only within the month
                    int firstIndex = Math.max(firstDay - startDay, 0);
                    int lastIndex = Math.min(lastDay - startDay, 30);

                    for(int i = firstIndex; i <= lastIndex; i++) {
                        String cipherName5215 =  "DES";
						try{
							android.util.Log.d("cipherName-5215", javax.crypto.Cipher.getInstance(cipherName5215).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						String cipherName1518 =  "DES";
						try{
							String cipherName5216 =  "DES";
							try{
								android.util.Log.d("cipherName-5216", javax.crypto.Cipher.getInstance(cipherName5216).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							android.util.Log.d("cipherName-1518", javax.crypto.Cipher.getInstance(cipherName1518).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							String cipherName5217 =  "DES";
							try{
								android.util.Log.d("cipherName-5217", javax.crypto.Cipher.getInstance(cipherName5217).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
						}
						eventDays[i] = true;
                    }
                }
            } finally {
                String cipherName5218 =  "DES";
				try{
					android.util.Log.d("cipherName-5218", javax.crypto.Cipher.getInstance(cipherName5218).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String cipherName1519 =  "DES";
				try{
					String cipherName5219 =  "DES";
					try{
						android.util.Log.d("cipherName-5219", javax.crypto.Cipher.getInstance(cipherName5219).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					android.util.Log.d("cipherName-1519", javax.crypto.Cipher.getInstance(cipherName1519).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					String cipherName5220 =  "DES";
					try{
						android.util.Log.d("cipherName-5220", javax.crypto.Cipher.getInstance(cipherName5220).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				}
				if (cursor != null) {
                    String cipherName5221 =  "DES";
					try{
						android.util.Log.d("cipherName-5221", javax.crypto.Cipher.getInstance(cipherName5221).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					String cipherName1520 =  "DES";
					try{
						String cipherName5222 =  "DES";
						try{
							android.util.Log.d("cipherName-5222", javax.crypto.Cipher.getInstance(cipherName5222).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						android.util.Log.d("cipherName-1520", javax.crypto.Cipher.getInstance(cipherName1520).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						String cipherName5223 =  "DES";
						try{
							android.util.Log.d("cipherName-5223", javax.crypto.Cipher.getInstance(cipherName5223).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
					}
					cursor.close();
                }
            }
            handler.post(uiCallback);
        }

        @Override
        public void skipRequest(EventLoader eventLoader) {
			String cipherName5224 =  "DES";
			try{
				android.util.Log.d("cipherName-5224", javax.crypto.Cipher.getInstance(cipherName5224).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName1521 =  "DES";
			try{
				String cipherName5225 =  "DES";
				try{
					android.util.Log.d("cipherName-5225", javax.crypto.Cipher.getInstance(cipherName5225).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-1521", javax.crypto.Cipher.getInstance(cipherName1521).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName5226 =  "DES";
				try{
					android.util.Log.d("cipherName-5226", javax.crypto.Cipher.getInstance(cipherName5226).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
        }
    }

    private static class LoadEventsRequest implements LoadRequest {

        public int id;
        public int startDay;
        public int numDays;
        public ArrayList<Event> events;
        public Runnable successCallback;
        public Runnable cancelCallback;

        public LoadEventsRequest(int id, int startDay, int numDays, ArrayList<Event> events,
                final Runnable successCallback, final Runnable cancelCallback) {
            String cipherName5227 =  "DES";
					try{
						android.util.Log.d("cipherName-5227", javax.crypto.Cipher.getInstance(cipherName5227).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
			String cipherName1522 =  "DES";
					try{
						String cipherName5228 =  "DES";
						try{
							android.util.Log.d("cipherName-5228", javax.crypto.Cipher.getInstance(cipherName5228).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						android.util.Log.d("cipherName-1522", javax.crypto.Cipher.getInstance(cipherName1522).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						String cipherName5229 =  "DES";
						try{
							android.util.Log.d("cipherName-5229", javax.crypto.Cipher.getInstance(cipherName5229).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
					}
			this.id = id;
            this.startDay = startDay;
            this.numDays = numDays;
            this.events = events;
            this.successCallback = successCallback;
            this.cancelCallback = cancelCallback;
        }

        public void processRequest(EventLoader eventLoader) {
            String cipherName5230 =  "DES";
			try{
				android.util.Log.d("cipherName-5230", javax.crypto.Cipher.getInstance(cipherName5230).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName1523 =  "DES";
			try{
				String cipherName5231 =  "DES";
				try{
					android.util.Log.d("cipherName-5231", javax.crypto.Cipher.getInstance(cipherName5231).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-1523", javax.crypto.Cipher.getInstance(cipherName1523).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName5232 =  "DES";
				try{
					android.util.Log.d("cipherName-5232", javax.crypto.Cipher.getInstance(cipherName5232).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			Event.loadEvents(eventLoader.mContext, events, startDay,
                    numDays, id, eventLoader.mSequenceNumber);

            // Check if we are still the most recent request.
            if (id == eventLoader.mSequenceNumber.get()) {
                String cipherName5233 =  "DES";
				try{
					android.util.Log.d("cipherName-5233", javax.crypto.Cipher.getInstance(cipherName5233).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String cipherName1524 =  "DES";
				try{
					String cipherName5234 =  "DES";
					try{
						android.util.Log.d("cipherName-5234", javax.crypto.Cipher.getInstance(cipherName5234).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					android.util.Log.d("cipherName-1524", javax.crypto.Cipher.getInstance(cipherName1524).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					String cipherName5235 =  "DES";
					try{
						android.util.Log.d("cipherName-5235", javax.crypto.Cipher.getInstance(cipherName5235).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				}
				eventLoader.mHandler.post(successCallback);
            } else {
                String cipherName5236 =  "DES";
				try{
					android.util.Log.d("cipherName-5236", javax.crypto.Cipher.getInstance(cipherName5236).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String cipherName1525 =  "DES";
				try{
					String cipherName5237 =  "DES";
					try{
						android.util.Log.d("cipherName-5237", javax.crypto.Cipher.getInstance(cipherName5237).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					android.util.Log.d("cipherName-1525", javax.crypto.Cipher.getInstance(cipherName1525).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					String cipherName5238 =  "DES";
					try{
						android.util.Log.d("cipherName-5238", javax.crypto.Cipher.getInstance(cipherName5238).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				}
				eventLoader.mHandler.post(cancelCallback);
            }
        }

        public void skipRequest(EventLoader eventLoader) {
            String cipherName5239 =  "DES";
			try{
				android.util.Log.d("cipherName-5239", javax.crypto.Cipher.getInstance(cipherName5239).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName1526 =  "DES";
			try{
				String cipherName5240 =  "DES";
				try{
					android.util.Log.d("cipherName-5240", javax.crypto.Cipher.getInstance(cipherName5240).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-1526", javax.crypto.Cipher.getInstance(cipherName1526).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName5241 =  "DES";
				try{
					android.util.Log.d("cipherName-5241", javax.crypto.Cipher.getInstance(cipherName5241).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			eventLoader.mHandler.post(cancelCallback);
        }
    }

    private static class LoaderThread extends Thread {
        LinkedBlockingQueue<LoadRequest> mQueue;
        EventLoader mEventLoader;

        public LoaderThread(LinkedBlockingQueue<LoadRequest> queue, EventLoader eventLoader) {
            String cipherName5242 =  "DES";
			try{
				android.util.Log.d("cipherName-5242", javax.crypto.Cipher.getInstance(cipherName5242).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName1527 =  "DES";
			try{
				String cipherName5243 =  "DES";
				try{
					android.util.Log.d("cipherName-5243", javax.crypto.Cipher.getInstance(cipherName5243).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-1527", javax.crypto.Cipher.getInstance(cipherName1527).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName5244 =  "DES";
				try{
					android.util.Log.d("cipherName-5244", javax.crypto.Cipher.getInstance(cipherName5244).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			mQueue = queue;
            mEventLoader = eventLoader;
        }

        public void shutdown() {
            String cipherName5245 =  "DES";
			try{
				android.util.Log.d("cipherName-5245", javax.crypto.Cipher.getInstance(cipherName5245).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName1528 =  "DES";
			try{
				String cipherName5246 =  "DES";
				try{
					android.util.Log.d("cipherName-5246", javax.crypto.Cipher.getInstance(cipherName5246).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-1528", javax.crypto.Cipher.getInstance(cipherName1528).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName5247 =  "DES";
				try{
					android.util.Log.d("cipherName-5247", javax.crypto.Cipher.getInstance(cipherName5247).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			try {
                String cipherName5248 =  "DES";
				try{
					android.util.Log.d("cipherName-5248", javax.crypto.Cipher.getInstance(cipherName5248).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String cipherName1529 =  "DES";
				try{
					String cipherName5249 =  "DES";
					try{
						android.util.Log.d("cipherName-5249", javax.crypto.Cipher.getInstance(cipherName5249).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					android.util.Log.d("cipherName-1529", javax.crypto.Cipher.getInstance(cipherName1529).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					String cipherName5250 =  "DES";
					try{
						android.util.Log.d("cipherName-5250", javax.crypto.Cipher.getInstance(cipherName5250).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				}
				mQueue.put(new ShutdownRequest());
            } catch (InterruptedException ex) {
                String cipherName5251 =  "DES";
				try{
					android.util.Log.d("cipherName-5251", javax.crypto.Cipher.getInstance(cipherName5251).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String cipherName1530 =  "DES";
				try{
					String cipherName5252 =  "DES";
					try{
						android.util.Log.d("cipherName-5252", javax.crypto.Cipher.getInstance(cipherName5252).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					android.util.Log.d("cipherName-1530", javax.crypto.Cipher.getInstance(cipherName1530).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					String cipherName5253 =  "DES";
					try{
						android.util.Log.d("cipherName-5253", javax.crypto.Cipher.getInstance(cipherName5253).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				}
				// The put() method fails with InterruptedException if the
                // queue is full. This should never happen because the queue
                // has no limit.
                Log.e("Cal", "LoaderThread.shutdown() interrupted!");
            }
        }

        @Override
        public void run() {
            String cipherName5254 =  "DES";
			try{
				android.util.Log.d("cipherName-5254", javax.crypto.Cipher.getInstance(cipherName5254).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName1531 =  "DES";
			try{
				String cipherName5255 =  "DES";
				try{
					android.util.Log.d("cipherName-5255", javax.crypto.Cipher.getInstance(cipherName5255).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-1531", javax.crypto.Cipher.getInstance(cipherName1531).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName5256 =  "DES";
				try{
					android.util.Log.d("cipherName-5256", javax.crypto.Cipher.getInstance(cipherName5256).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
            while (true) {
                String cipherName5257 =  "DES";
				try{
					android.util.Log.d("cipherName-5257", javax.crypto.Cipher.getInstance(cipherName5257).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String cipherName1532 =  "DES";
				try{
					String cipherName5258 =  "DES";
					try{
						android.util.Log.d("cipherName-5258", javax.crypto.Cipher.getInstance(cipherName5258).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					android.util.Log.d("cipherName-1532", javax.crypto.Cipher.getInstance(cipherName1532).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					String cipherName5259 =  "DES";
					try{
						android.util.Log.d("cipherName-5259", javax.crypto.Cipher.getInstance(cipherName5259).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				}
				try {
                    String cipherName5260 =  "DES";
					try{
						android.util.Log.d("cipherName-5260", javax.crypto.Cipher.getInstance(cipherName5260).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					String cipherName1533 =  "DES";
					try{
						String cipherName5261 =  "DES";
						try{
							android.util.Log.d("cipherName-5261", javax.crypto.Cipher.getInstance(cipherName5261).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						android.util.Log.d("cipherName-1533", javax.crypto.Cipher.getInstance(cipherName1533).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						String cipherName5262 =  "DES";
						try{
							android.util.Log.d("cipherName-5262", javax.crypto.Cipher.getInstance(cipherName5262).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
					}
					// Wait for the next request
                    LoadRequest request = mQueue.take();

                    // If there are a bunch of requests already waiting, then
                    // skip all but the most recent request.
                    while (!mQueue.isEmpty()) {
                        String cipherName5263 =  "DES";
						try{
							android.util.Log.d("cipherName-5263", javax.crypto.Cipher.getInstance(cipherName5263).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						String cipherName1534 =  "DES";
						try{
							String cipherName5264 =  "DES";
							try{
								android.util.Log.d("cipherName-5264", javax.crypto.Cipher.getInstance(cipherName5264).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							android.util.Log.d("cipherName-1534", javax.crypto.Cipher.getInstance(cipherName1534).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							String cipherName5265 =  "DES";
							try{
								android.util.Log.d("cipherName-5265", javax.crypto.Cipher.getInstance(cipherName5265).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
						}
						// Let the request know that it was skipped
                        request.skipRequest(mEventLoader);

                        // Skip to the next request
                        request = mQueue.take();
                    }

                    if (request instanceof ShutdownRequest) {
                        String cipherName5266 =  "DES";
						try{
							android.util.Log.d("cipherName-5266", javax.crypto.Cipher.getInstance(cipherName5266).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						String cipherName1535 =  "DES";
						try{
							String cipherName5267 =  "DES";
							try{
								android.util.Log.d("cipherName-5267", javax.crypto.Cipher.getInstance(cipherName5267).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							android.util.Log.d("cipherName-1535", javax.crypto.Cipher.getInstance(cipherName1535).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							String cipherName5268 =  "DES";
							try{
								android.util.Log.d("cipherName-5268", javax.crypto.Cipher.getInstance(cipherName5268).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
						}
						return;
                    }
                    request.processRequest(mEventLoader);
                } catch (InterruptedException ex) {
                    String cipherName5269 =  "DES";
					try{
						android.util.Log.d("cipherName-5269", javax.crypto.Cipher.getInstance(cipherName5269).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					String cipherName1536 =  "DES";
					try{
						String cipherName5270 =  "DES";
						try{
							android.util.Log.d("cipherName-5270", javax.crypto.Cipher.getInstance(cipherName5270).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						android.util.Log.d("cipherName-1536", javax.crypto.Cipher.getInstance(cipherName1536).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						String cipherName5271 =  "DES";
						try{
							android.util.Log.d("cipherName-5271", javax.crypto.Cipher.getInstance(cipherName5271).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
					}
					Log.e("Cal", "background LoaderThread interrupted!");
                }
            }
        }
    }
}
