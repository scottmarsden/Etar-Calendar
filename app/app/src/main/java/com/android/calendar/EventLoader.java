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
        String cipherName4509 =  "DES";
		try{
			android.util.Log.d("cipherName-4509", javax.crypto.Cipher.getInstance(cipherName4509).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName1503 =  "DES";
		try{
			String cipherName4510 =  "DES";
			try{
				android.util.Log.d("cipherName-4510", javax.crypto.Cipher.getInstance(cipherName4510).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-1503", javax.crypto.Cipher.getInstance(cipherName1503).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName4511 =  "DES";
			try{
				android.util.Log.d("cipherName-4511", javax.crypto.Cipher.getInstance(cipherName4511).getAlgorithm());
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
        String cipherName4512 =  "DES";
		try{
			android.util.Log.d("cipherName-4512", javax.crypto.Cipher.getInstance(cipherName4512).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName1504 =  "DES";
		try{
			String cipherName4513 =  "DES";
			try{
				android.util.Log.d("cipherName-4513", javax.crypto.Cipher.getInstance(cipherName4513).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-1504", javax.crypto.Cipher.getInstance(cipherName1504).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName4514 =  "DES";
			try{
				android.util.Log.d("cipherName-4514", javax.crypto.Cipher.getInstance(cipherName4514).getAlgorithm());
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
        String cipherName4515 =  "DES";
		try{
			android.util.Log.d("cipherName-4515", javax.crypto.Cipher.getInstance(cipherName4515).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String cipherName1505 =  "DES";
		try{
			String cipherName4516 =  "DES";
			try{
				android.util.Log.d("cipherName-4516", javax.crypto.Cipher.getInstance(cipherName4516).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			android.util.Log.d("cipherName-1505", javax.crypto.Cipher.getInstance(cipherName1505).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			String cipherName4517 =  "DES";
			try{
				android.util.Log.d("cipherName-4517", javax.crypto.Cipher.getInstance(cipherName4517).getAlgorithm());
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

        String cipherName4518 =  "DES";
										try{
											android.util.Log.d("cipherName-4518", javax.crypto.Cipher.getInstance(cipherName4518).getAlgorithm());
										}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
										}
		String cipherName1506 =  "DES";
										try{
											String cipherName4519 =  "DES";
											try{
												android.util.Log.d("cipherName-4519", javax.crypto.Cipher.getInstance(cipherName4519).getAlgorithm());
											}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
											}
											android.util.Log.d("cipherName-1506", javax.crypto.Cipher.getInstance(cipherName1506).getAlgorithm());
										}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
											String cipherName4520 =  "DES";
											try{
												android.util.Log.d("cipherName-4520", javax.crypto.Cipher.getInstance(cipherName4520).getAlgorithm());
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
            String cipherName4521 =  "DES";
			try{
				android.util.Log.d("cipherName-4521", javax.crypto.Cipher.getInstance(cipherName4521).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName1507 =  "DES";
			try{
				String cipherName4522 =  "DES";
				try{
					android.util.Log.d("cipherName-4522", javax.crypto.Cipher.getInstance(cipherName4522).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-1507", javax.crypto.Cipher.getInstance(cipherName1507).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName4523 =  "DES";
				try{
					android.util.Log.d("cipherName-4523", javax.crypto.Cipher.getInstance(cipherName4523).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			mLoaderQueue.put(request);
        } catch (InterruptedException ex) {
            String cipherName4524 =  "DES";
			try{
				android.util.Log.d("cipherName-4524", javax.crypto.Cipher.getInstance(cipherName4524).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName1508 =  "DES";
			try{
				String cipherName4525 =  "DES";
				try{
					android.util.Log.d("cipherName-4525", javax.crypto.Cipher.getInstance(cipherName4525).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-1508", javax.crypto.Cipher.getInstance(cipherName1508).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName4526 =  "DES";
				try{
					android.util.Log.d("cipherName-4526", javax.crypto.Cipher.getInstance(cipherName4526).getAlgorithm());
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
        String cipherName4527 =  "DES";
									try{
										android.util.Log.d("cipherName-4527", javax.crypto.Cipher.getInstance(cipherName4527).getAlgorithm());
									}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
									}
		String cipherName1509 =  "DES";
									try{
										String cipherName4528 =  "DES";
										try{
											android.util.Log.d("cipherName-4528", javax.crypto.Cipher.getInstance(cipherName4528).getAlgorithm());
										}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
										}
										android.util.Log.d("cipherName-1509", javax.crypto.Cipher.getInstance(cipherName1509).getAlgorithm());
									}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
										String cipherName4529 =  "DES";
										try{
											android.util.Log.d("cipherName-4529", javax.crypto.Cipher.getInstance(cipherName4529).getAlgorithm());
										}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
										}
									}
		// Send load request to the background thread
        LoadEventDaysRequest request = new LoadEventDaysRequest(startDay, numDays,
                eventDays, uiCallback);
        try {
            String cipherName4530 =  "DES";
			try{
				android.util.Log.d("cipherName-4530", javax.crypto.Cipher.getInstance(cipherName4530).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName1510 =  "DES";
			try{
				String cipherName4531 =  "DES";
				try{
					android.util.Log.d("cipherName-4531", javax.crypto.Cipher.getInstance(cipherName4531).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-1510", javax.crypto.Cipher.getInstance(cipherName1510).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName4532 =  "DES";
				try{
					android.util.Log.d("cipherName-4532", javax.crypto.Cipher.getInstance(cipherName4532).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			mLoaderQueue.put(request);
        } catch (InterruptedException ex) {
            String cipherName4533 =  "DES";
			try{
				android.util.Log.d("cipherName-4533", javax.crypto.Cipher.getInstance(cipherName4533).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName1511 =  "DES";
			try{
				String cipherName4534 =  "DES";
				try{
					android.util.Log.d("cipherName-4534", javax.crypto.Cipher.getInstance(cipherName4534).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-1511", javax.crypto.Cipher.getInstance(cipherName1511).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName4535 =  "DES";
				try{
					android.util.Log.d("cipherName-4535", javax.crypto.Cipher.getInstance(cipherName4535).getAlgorithm());
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
			String cipherName4536 =  "DES";
			try{
				android.util.Log.d("cipherName-4536", javax.crypto.Cipher.getInstance(cipherName4536).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName1512 =  "DES";
			try{
				String cipherName4537 =  "DES";
				try{
					android.util.Log.d("cipherName-4537", javax.crypto.Cipher.getInstance(cipherName4537).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-1512", javax.crypto.Cipher.getInstance(cipherName1512).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName4538 =  "DES";
				try{
					android.util.Log.d("cipherName-4538", javax.crypto.Cipher.getInstance(cipherName4538).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
        }

        public void skipRequest(EventLoader eventLoader) {
			String cipherName4539 =  "DES";
			try{
				android.util.Log.d("cipherName-4539", javax.crypto.Cipher.getInstance(cipherName4539).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName1513 =  "DES";
			try{
				String cipherName4540 =  "DES";
				try{
					android.util.Log.d("cipherName-4540", javax.crypto.Cipher.getInstance(cipherName4540).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-1513", javax.crypto.Cipher.getInstance(cipherName1513).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName4541 =  "DES";
				try{
					android.util.Log.d("cipherName-4541", javax.crypto.Cipher.getInstance(cipherName4541).getAlgorithm());
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
            String cipherName4542 =  "DES";
			try{
				android.util.Log.d("cipherName-4542", javax.crypto.Cipher.getInstance(cipherName4542).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName1514 =  "DES";
			try{
				String cipherName4543 =  "DES";
				try{
					android.util.Log.d("cipherName-4543", javax.crypto.Cipher.getInstance(cipherName4543).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-1514", javax.crypto.Cipher.getInstance(cipherName1514).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName4544 =  "DES";
				try{
					android.util.Log.d("cipherName-4544", javax.crypto.Cipher.getInstance(cipherName4544).getAlgorithm());
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
            String cipherName4545 =  "DES";
			try{
				android.util.Log.d("cipherName-4545", javax.crypto.Cipher.getInstance(cipherName4545).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName1515 =  "DES";
			try{
				String cipherName4546 =  "DES";
				try{
					android.util.Log.d("cipherName-4546", javax.crypto.Cipher.getInstance(cipherName4546).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-1515", javax.crypto.Cipher.getInstance(cipherName1515).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName4547 =  "DES";
				try{
					android.util.Log.d("cipherName-4547", javax.crypto.Cipher.getInstance(cipherName4547).getAlgorithm());
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
                String cipherName4548 =  "DES";
				try{
					android.util.Log.d("cipherName-4548", javax.crypto.Cipher.getInstance(cipherName4548).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String cipherName1516 =  "DES";
				try{
					String cipherName4549 =  "DES";
					try{
						android.util.Log.d("cipherName-4549", javax.crypto.Cipher.getInstance(cipherName4549).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					android.util.Log.d("cipherName-1516", javax.crypto.Cipher.getInstance(cipherName1516).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					String cipherName4550 =  "DES";
					try{
						android.util.Log.d("cipherName-4550", javax.crypto.Cipher.getInstance(cipherName4550).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				}
				int startDayColumnIndex = cursor.getColumnIndexOrThrow(EventDays.STARTDAY);
                int endDayColumnIndex = cursor.getColumnIndexOrThrow(EventDays.ENDDAY);

                //Set all the days with events to true
                while (cursor.moveToNext()) {
                    String cipherName4551 =  "DES";
					try{
						android.util.Log.d("cipherName-4551", javax.crypto.Cipher.getInstance(cipherName4551).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					String cipherName1517 =  "DES";
					try{
						String cipherName4552 =  "DES";
						try{
							android.util.Log.d("cipherName-4552", javax.crypto.Cipher.getInstance(cipherName4552).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						android.util.Log.d("cipherName-1517", javax.crypto.Cipher.getInstance(cipherName1517).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						String cipherName4553 =  "DES";
						try{
							android.util.Log.d("cipherName-4553", javax.crypto.Cipher.getInstance(cipherName4553).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
					}
					int firstDay = cursor.getInt(startDayColumnIndex);
                    int lastDay = cursor.getInt(endDayColumnIndex);
                    //we want the entire range the event occurs, but only within the month
                    int firstIndex = Math.max(firstDay - startDay, 0);
                    int lastIndex = Math.min(lastDay - startDay, 30);

                    for(int i = firstIndex; i <= lastIndex; i++) {
                        String cipherName4554 =  "DES";
						try{
							android.util.Log.d("cipherName-4554", javax.crypto.Cipher.getInstance(cipherName4554).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						String cipherName1518 =  "DES";
						try{
							String cipherName4555 =  "DES";
							try{
								android.util.Log.d("cipherName-4555", javax.crypto.Cipher.getInstance(cipherName4555).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							android.util.Log.d("cipherName-1518", javax.crypto.Cipher.getInstance(cipherName1518).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							String cipherName4556 =  "DES";
							try{
								android.util.Log.d("cipherName-4556", javax.crypto.Cipher.getInstance(cipherName4556).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
						}
						eventDays[i] = true;
                    }
                }
            } finally {
                String cipherName4557 =  "DES";
				try{
					android.util.Log.d("cipherName-4557", javax.crypto.Cipher.getInstance(cipherName4557).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String cipherName1519 =  "DES";
				try{
					String cipherName4558 =  "DES";
					try{
						android.util.Log.d("cipherName-4558", javax.crypto.Cipher.getInstance(cipherName4558).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					android.util.Log.d("cipherName-1519", javax.crypto.Cipher.getInstance(cipherName1519).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					String cipherName4559 =  "DES";
					try{
						android.util.Log.d("cipherName-4559", javax.crypto.Cipher.getInstance(cipherName4559).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				}
				if (cursor != null) {
                    String cipherName4560 =  "DES";
					try{
						android.util.Log.d("cipherName-4560", javax.crypto.Cipher.getInstance(cipherName4560).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					String cipherName1520 =  "DES";
					try{
						String cipherName4561 =  "DES";
						try{
							android.util.Log.d("cipherName-4561", javax.crypto.Cipher.getInstance(cipherName4561).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						android.util.Log.d("cipherName-1520", javax.crypto.Cipher.getInstance(cipherName1520).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						String cipherName4562 =  "DES";
						try{
							android.util.Log.d("cipherName-4562", javax.crypto.Cipher.getInstance(cipherName4562).getAlgorithm());
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
			String cipherName4563 =  "DES";
			try{
				android.util.Log.d("cipherName-4563", javax.crypto.Cipher.getInstance(cipherName4563).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName1521 =  "DES";
			try{
				String cipherName4564 =  "DES";
				try{
					android.util.Log.d("cipherName-4564", javax.crypto.Cipher.getInstance(cipherName4564).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-1521", javax.crypto.Cipher.getInstance(cipherName1521).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName4565 =  "DES";
				try{
					android.util.Log.d("cipherName-4565", javax.crypto.Cipher.getInstance(cipherName4565).getAlgorithm());
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
            String cipherName4566 =  "DES";
					try{
						android.util.Log.d("cipherName-4566", javax.crypto.Cipher.getInstance(cipherName4566).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
			String cipherName1522 =  "DES";
					try{
						String cipherName4567 =  "DES";
						try{
							android.util.Log.d("cipherName-4567", javax.crypto.Cipher.getInstance(cipherName4567).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						android.util.Log.d("cipherName-1522", javax.crypto.Cipher.getInstance(cipherName1522).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						String cipherName4568 =  "DES";
						try{
							android.util.Log.d("cipherName-4568", javax.crypto.Cipher.getInstance(cipherName4568).getAlgorithm());
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
            String cipherName4569 =  "DES";
			try{
				android.util.Log.d("cipherName-4569", javax.crypto.Cipher.getInstance(cipherName4569).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName1523 =  "DES";
			try{
				String cipherName4570 =  "DES";
				try{
					android.util.Log.d("cipherName-4570", javax.crypto.Cipher.getInstance(cipherName4570).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-1523", javax.crypto.Cipher.getInstance(cipherName1523).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName4571 =  "DES";
				try{
					android.util.Log.d("cipherName-4571", javax.crypto.Cipher.getInstance(cipherName4571).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			Event.loadEvents(eventLoader.mContext, events, startDay,
                    numDays, id, eventLoader.mSequenceNumber);

            // Check if we are still the most recent request.
            if (id == eventLoader.mSequenceNumber.get()) {
                String cipherName4572 =  "DES";
				try{
					android.util.Log.d("cipherName-4572", javax.crypto.Cipher.getInstance(cipherName4572).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String cipherName1524 =  "DES";
				try{
					String cipherName4573 =  "DES";
					try{
						android.util.Log.d("cipherName-4573", javax.crypto.Cipher.getInstance(cipherName4573).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					android.util.Log.d("cipherName-1524", javax.crypto.Cipher.getInstance(cipherName1524).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					String cipherName4574 =  "DES";
					try{
						android.util.Log.d("cipherName-4574", javax.crypto.Cipher.getInstance(cipherName4574).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				}
				eventLoader.mHandler.post(successCallback);
            } else {
                String cipherName4575 =  "DES";
				try{
					android.util.Log.d("cipherName-4575", javax.crypto.Cipher.getInstance(cipherName4575).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String cipherName1525 =  "DES";
				try{
					String cipherName4576 =  "DES";
					try{
						android.util.Log.d("cipherName-4576", javax.crypto.Cipher.getInstance(cipherName4576).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					android.util.Log.d("cipherName-1525", javax.crypto.Cipher.getInstance(cipherName1525).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					String cipherName4577 =  "DES";
					try{
						android.util.Log.d("cipherName-4577", javax.crypto.Cipher.getInstance(cipherName4577).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				}
				eventLoader.mHandler.post(cancelCallback);
            }
        }

        public void skipRequest(EventLoader eventLoader) {
            String cipherName4578 =  "DES";
			try{
				android.util.Log.d("cipherName-4578", javax.crypto.Cipher.getInstance(cipherName4578).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName1526 =  "DES";
			try{
				String cipherName4579 =  "DES";
				try{
					android.util.Log.d("cipherName-4579", javax.crypto.Cipher.getInstance(cipherName4579).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-1526", javax.crypto.Cipher.getInstance(cipherName1526).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName4580 =  "DES";
				try{
					android.util.Log.d("cipherName-4580", javax.crypto.Cipher.getInstance(cipherName4580).getAlgorithm());
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
            String cipherName4581 =  "DES";
			try{
				android.util.Log.d("cipherName-4581", javax.crypto.Cipher.getInstance(cipherName4581).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName1527 =  "DES";
			try{
				String cipherName4582 =  "DES";
				try{
					android.util.Log.d("cipherName-4582", javax.crypto.Cipher.getInstance(cipherName4582).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-1527", javax.crypto.Cipher.getInstance(cipherName1527).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName4583 =  "DES";
				try{
					android.util.Log.d("cipherName-4583", javax.crypto.Cipher.getInstance(cipherName4583).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			mQueue = queue;
            mEventLoader = eventLoader;
        }

        public void shutdown() {
            String cipherName4584 =  "DES";
			try{
				android.util.Log.d("cipherName-4584", javax.crypto.Cipher.getInstance(cipherName4584).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName1528 =  "DES";
			try{
				String cipherName4585 =  "DES";
				try{
					android.util.Log.d("cipherName-4585", javax.crypto.Cipher.getInstance(cipherName4585).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-1528", javax.crypto.Cipher.getInstance(cipherName1528).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName4586 =  "DES";
				try{
					android.util.Log.d("cipherName-4586", javax.crypto.Cipher.getInstance(cipherName4586).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			try {
                String cipherName4587 =  "DES";
				try{
					android.util.Log.d("cipherName-4587", javax.crypto.Cipher.getInstance(cipherName4587).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String cipherName1529 =  "DES";
				try{
					String cipherName4588 =  "DES";
					try{
						android.util.Log.d("cipherName-4588", javax.crypto.Cipher.getInstance(cipherName4588).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					android.util.Log.d("cipherName-1529", javax.crypto.Cipher.getInstance(cipherName1529).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					String cipherName4589 =  "DES";
					try{
						android.util.Log.d("cipherName-4589", javax.crypto.Cipher.getInstance(cipherName4589).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				}
				mQueue.put(new ShutdownRequest());
            } catch (InterruptedException ex) {
                String cipherName4590 =  "DES";
				try{
					android.util.Log.d("cipherName-4590", javax.crypto.Cipher.getInstance(cipherName4590).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String cipherName1530 =  "DES";
				try{
					String cipherName4591 =  "DES";
					try{
						android.util.Log.d("cipherName-4591", javax.crypto.Cipher.getInstance(cipherName4591).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					android.util.Log.d("cipherName-1530", javax.crypto.Cipher.getInstance(cipherName1530).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					String cipherName4592 =  "DES";
					try{
						android.util.Log.d("cipherName-4592", javax.crypto.Cipher.getInstance(cipherName4592).getAlgorithm());
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
            String cipherName4593 =  "DES";
			try{
				android.util.Log.d("cipherName-4593", javax.crypto.Cipher.getInstance(cipherName4593).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cipherName1531 =  "DES";
			try{
				String cipherName4594 =  "DES";
				try{
					android.util.Log.d("cipherName-4594", javax.crypto.Cipher.getInstance(cipherName4594).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				android.util.Log.d("cipherName-1531", javax.crypto.Cipher.getInstance(cipherName1531).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				String cipherName4595 =  "DES";
				try{
					android.util.Log.d("cipherName-4595", javax.crypto.Cipher.getInstance(cipherName4595).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			}
			Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
            while (true) {
                String cipherName4596 =  "DES";
				try{
					android.util.Log.d("cipherName-4596", javax.crypto.Cipher.getInstance(cipherName4596).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String cipherName1532 =  "DES";
				try{
					String cipherName4597 =  "DES";
					try{
						android.util.Log.d("cipherName-4597", javax.crypto.Cipher.getInstance(cipherName4597).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					android.util.Log.d("cipherName-1532", javax.crypto.Cipher.getInstance(cipherName1532).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					String cipherName4598 =  "DES";
					try{
						android.util.Log.d("cipherName-4598", javax.crypto.Cipher.getInstance(cipherName4598).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				}
				try {
                    String cipherName4599 =  "DES";
					try{
						android.util.Log.d("cipherName-4599", javax.crypto.Cipher.getInstance(cipherName4599).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					String cipherName1533 =  "DES";
					try{
						String cipherName4600 =  "DES";
						try{
							android.util.Log.d("cipherName-4600", javax.crypto.Cipher.getInstance(cipherName4600).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						android.util.Log.d("cipherName-1533", javax.crypto.Cipher.getInstance(cipherName1533).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						String cipherName4601 =  "DES";
						try{
							android.util.Log.d("cipherName-4601", javax.crypto.Cipher.getInstance(cipherName4601).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
					}
					// Wait for the next request
                    LoadRequest request = mQueue.take();

                    // If there are a bunch of requests already waiting, then
                    // skip all but the most recent request.
                    while (!mQueue.isEmpty()) {
                        String cipherName4602 =  "DES";
						try{
							android.util.Log.d("cipherName-4602", javax.crypto.Cipher.getInstance(cipherName4602).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						String cipherName1534 =  "DES";
						try{
							String cipherName4603 =  "DES";
							try{
								android.util.Log.d("cipherName-4603", javax.crypto.Cipher.getInstance(cipherName4603).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							android.util.Log.d("cipherName-1534", javax.crypto.Cipher.getInstance(cipherName1534).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							String cipherName4604 =  "DES";
							try{
								android.util.Log.d("cipherName-4604", javax.crypto.Cipher.getInstance(cipherName4604).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
						}
						// Let the request know that it was skipped
                        request.skipRequest(mEventLoader);

                        // Skip to the next request
                        request = mQueue.take();
                    }

                    if (request instanceof ShutdownRequest) {
                        String cipherName4605 =  "DES";
						try{
							android.util.Log.d("cipherName-4605", javax.crypto.Cipher.getInstance(cipherName4605).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						String cipherName1535 =  "DES";
						try{
							String cipherName4606 =  "DES";
							try{
								android.util.Log.d("cipherName-4606", javax.crypto.Cipher.getInstance(cipherName4606).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							android.util.Log.d("cipherName-1535", javax.crypto.Cipher.getInstance(cipherName1535).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							String cipherName4607 =  "DES";
							try{
								android.util.Log.d("cipherName-4607", javax.crypto.Cipher.getInstance(cipherName4607).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
						}
						return;
                    }
                    request.processRequest(mEventLoader);
                } catch (InterruptedException ex) {
                    String cipherName4608 =  "DES";
					try{
						android.util.Log.d("cipherName-4608", javax.crypto.Cipher.getInstance(cipherName4608).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					String cipherName1536 =  "DES";
					try{
						String cipherName4609 =  "DES";
						try{
							android.util.Log.d("cipherName-4609", javax.crypto.Cipher.getInstance(cipherName4609).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						android.util.Log.d("cipherName-1536", javax.crypto.Cipher.getInstance(cipherName1536).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						String cipherName4610 =  "DES";
						try{
							android.util.Log.d("cipherName-4610", javax.crypto.Cipher.getInstance(cipherName4610).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
					}
					Log.e("Cal", "background LoaderThread interrupted!");
                }
            }
        }
    }
}
