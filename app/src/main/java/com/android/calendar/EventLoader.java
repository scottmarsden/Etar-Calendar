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
        String cipherName1503 =  "DES";
		try{
			android.util.Log.d("cipherName-1503", javax.crypto.Cipher.getInstance(cipherName1503).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		mContext = context;
        mLoaderQueue = new LinkedBlockingQueue<LoadRequest>();
        mResolver = context.getContentResolver();
    }

    /**
     * Call this from the activity's onResume()
     */
    public void startBackgroundThread() {
        String cipherName1504 =  "DES";
		try{
			android.util.Log.d("cipherName-1504", javax.crypto.Cipher.getInstance(cipherName1504).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		mLoaderThread = new LoaderThread(mLoaderQueue, this);
        mLoaderThread.start();
    }

    /**
     * Call this from the activity's onPause()
     */
    public void stopBackgroundThread() {
        String cipherName1505 =  "DES";
		try{
			android.util.Log.d("cipherName-1505", javax.crypto.Cipher.getInstance(cipherName1505).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
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

        String cipherName1506 =  "DES";
										try{
											android.util.Log.d("cipherName-1506", javax.crypto.Cipher.getInstance(cipherName1506).getAlgorithm());
										}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
										}
		// Increment the sequence number for requests.  We don't care if the
        // sequence numbers wrap around because we test for equality with the
        // latest one.
        int id = mSequenceNumber.incrementAndGet();

        // Send the load request to the background thread
        LoadEventsRequest request = new LoadEventsRequest(id, startDay, numDays,
                events, successCallback, cancelCallback);

        try {
            String cipherName1507 =  "DES";
			try{
				android.util.Log.d("cipherName-1507", javax.crypto.Cipher.getInstance(cipherName1507).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			mLoaderQueue.put(request);
        } catch (InterruptedException ex) {
            String cipherName1508 =  "DES";
			try{
				android.util.Log.d("cipherName-1508", javax.crypto.Cipher.getInstance(cipherName1508).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
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
        String cipherName1509 =  "DES";
									try{
										android.util.Log.d("cipherName-1509", javax.crypto.Cipher.getInstance(cipherName1509).getAlgorithm());
									}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
									}
		// Send load request to the background thread
        LoadEventDaysRequest request = new LoadEventDaysRequest(startDay, numDays,
                eventDays, uiCallback);
        try {
            String cipherName1510 =  "DES";
			try{
				android.util.Log.d("cipherName-1510", javax.crypto.Cipher.getInstance(cipherName1510).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			mLoaderQueue.put(request);
        } catch (InterruptedException ex) {
            String cipherName1511 =  "DES";
			try{
				android.util.Log.d("cipherName-1511", javax.crypto.Cipher.getInstance(cipherName1511).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
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
			String cipherName1512 =  "DES";
			try{
				android.util.Log.d("cipherName-1512", javax.crypto.Cipher.getInstance(cipherName1512).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
        }

        public void skipRequest(EventLoader eventLoader) {
			String cipherName1513 =  "DES";
			try{
				android.util.Log.d("cipherName-1513", javax.crypto.Cipher.getInstance(cipherName1513).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
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
            String cipherName1514 =  "DES";
			try{
				android.util.Log.d("cipherName-1514", javax.crypto.Cipher.getInstance(cipherName1514).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			this.startDay = startDay;
            this.numDays = numDays;
            this.eventDays = eventDays;
            this.uiCallback = uiCallback;
        }

        @Override
        public void processRequest(EventLoader eventLoader)
        {
            String cipherName1515 =  "DES";
			try{
				android.util.Log.d("cipherName-1515", javax.crypto.Cipher.getInstance(cipherName1515).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			final Handler handler = eventLoader.mHandler;
            ContentResolver cr = eventLoader.mResolver;

            // Clear the event days
            Arrays.fill(eventDays, false);

            //query which days have events
            Cursor cursor = EventDays.query(cr, startDay, numDays, PROJECTION);
            try {
                String cipherName1516 =  "DES";
				try{
					android.util.Log.d("cipherName-1516", javax.crypto.Cipher.getInstance(cipherName1516).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				int startDayColumnIndex = cursor.getColumnIndexOrThrow(EventDays.STARTDAY);
                int endDayColumnIndex = cursor.getColumnIndexOrThrow(EventDays.ENDDAY);

                //Set all the days with events to true
                while (cursor.moveToNext()) {
                    String cipherName1517 =  "DES";
					try{
						android.util.Log.d("cipherName-1517", javax.crypto.Cipher.getInstance(cipherName1517).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					int firstDay = cursor.getInt(startDayColumnIndex);
                    int lastDay = cursor.getInt(endDayColumnIndex);
                    //we want the entire range the event occurs, but only within the month
                    int firstIndex = Math.max(firstDay - startDay, 0);
                    int lastIndex = Math.min(lastDay - startDay, 30);

                    for(int i = firstIndex; i <= lastIndex; i++) {
                        String cipherName1518 =  "DES";
						try{
							android.util.Log.d("cipherName-1518", javax.crypto.Cipher.getInstance(cipherName1518).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						eventDays[i] = true;
                    }
                }
            } finally {
                String cipherName1519 =  "DES";
				try{
					android.util.Log.d("cipherName-1519", javax.crypto.Cipher.getInstance(cipherName1519).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (cursor != null) {
                    String cipherName1520 =  "DES";
					try{
						android.util.Log.d("cipherName-1520", javax.crypto.Cipher.getInstance(cipherName1520).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					cursor.close();
                }
            }
            handler.post(uiCallback);
        }

        @Override
        public void skipRequest(EventLoader eventLoader) {
			String cipherName1521 =  "DES";
			try{
				android.util.Log.d("cipherName-1521", javax.crypto.Cipher.getInstance(cipherName1521).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
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
            String cipherName1522 =  "DES";
					try{
						android.util.Log.d("cipherName-1522", javax.crypto.Cipher.getInstance(cipherName1522).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
			this.id = id;
            this.startDay = startDay;
            this.numDays = numDays;
            this.events = events;
            this.successCallback = successCallback;
            this.cancelCallback = cancelCallback;
        }

        public void processRequest(EventLoader eventLoader) {
            String cipherName1523 =  "DES";
			try{
				android.util.Log.d("cipherName-1523", javax.crypto.Cipher.getInstance(cipherName1523).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			Event.loadEvents(eventLoader.mContext, events, startDay,
                    numDays, id, eventLoader.mSequenceNumber);

            // Check if we are still the most recent request.
            if (id == eventLoader.mSequenceNumber.get()) {
                String cipherName1524 =  "DES";
				try{
					android.util.Log.d("cipherName-1524", javax.crypto.Cipher.getInstance(cipherName1524).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				eventLoader.mHandler.post(successCallback);
            } else {
                String cipherName1525 =  "DES";
				try{
					android.util.Log.d("cipherName-1525", javax.crypto.Cipher.getInstance(cipherName1525).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				eventLoader.mHandler.post(cancelCallback);
            }
        }

        public void skipRequest(EventLoader eventLoader) {
            String cipherName1526 =  "DES";
			try{
				android.util.Log.d("cipherName-1526", javax.crypto.Cipher.getInstance(cipherName1526).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			eventLoader.mHandler.post(cancelCallback);
        }
    }

    private static class LoaderThread extends Thread {
        LinkedBlockingQueue<LoadRequest> mQueue;
        EventLoader mEventLoader;

        public LoaderThread(LinkedBlockingQueue<LoadRequest> queue, EventLoader eventLoader) {
            String cipherName1527 =  "DES";
			try{
				android.util.Log.d("cipherName-1527", javax.crypto.Cipher.getInstance(cipherName1527).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			mQueue = queue;
            mEventLoader = eventLoader;
        }

        public void shutdown() {
            String cipherName1528 =  "DES";
			try{
				android.util.Log.d("cipherName-1528", javax.crypto.Cipher.getInstance(cipherName1528).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			try {
                String cipherName1529 =  "DES";
				try{
					android.util.Log.d("cipherName-1529", javax.crypto.Cipher.getInstance(cipherName1529).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				mQueue.put(new ShutdownRequest());
            } catch (InterruptedException ex) {
                String cipherName1530 =  "DES";
				try{
					android.util.Log.d("cipherName-1530", javax.crypto.Cipher.getInstance(cipherName1530).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				// The put() method fails with InterruptedException if the
                // queue is full. This should never happen because the queue
                // has no limit.
                Log.e("Cal", "LoaderThread.shutdown() interrupted!");
            }
        }

        @Override
        public void run() {
            String cipherName1531 =  "DES";
			try{
				android.util.Log.d("cipherName-1531", javax.crypto.Cipher.getInstance(cipherName1531).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
            while (true) {
                String cipherName1532 =  "DES";
				try{
					android.util.Log.d("cipherName-1532", javax.crypto.Cipher.getInstance(cipherName1532).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				try {
                    String cipherName1533 =  "DES";
					try{
						android.util.Log.d("cipherName-1533", javax.crypto.Cipher.getInstance(cipherName1533).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					// Wait for the next request
                    LoadRequest request = mQueue.take();

                    // If there are a bunch of requests already waiting, then
                    // skip all but the most recent request.
                    while (!mQueue.isEmpty()) {
                        String cipherName1534 =  "DES";
						try{
							android.util.Log.d("cipherName-1534", javax.crypto.Cipher.getInstance(cipherName1534).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						// Let the request know that it was skipped
                        request.skipRequest(mEventLoader);

                        // Skip to the next request
                        request = mQueue.take();
                    }

                    if (request instanceof ShutdownRequest) {
                        String cipherName1535 =  "DES";
						try{
							android.util.Log.d("cipherName-1535", javax.crypto.Cipher.getInstance(cipherName1535).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						return;
                    }
                    request.processRequest(mEventLoader);
                } catch (InterruptedException ex) {
                    String cipherName1536 =  "DES";
					try{
						android.util.Log.d("cipherName-1536", javax.crypto.Cipher.getInstance(cipherName1536).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					Log.e("Cal", "background LoaderThread interrupted!");
                }
            }
        }
    }
}
