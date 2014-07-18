package com.dd.sample;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;

public class TaskFragment extends Fragment {

    /**
     * Callback interface through which the fragment can report the task's
     * progress and results back to the Activity.
     */
    static interface TaskCallbacks {
        void onPreExecute();

        void onProgressUpdate(int percent);

        void onCancelled();

        void onPostExecute();
    }

    private TaskCallbacks mCallbacks;
    private DummyTask mTask;
    private boolean mRunning;

    /**
     * Hold a reference to the parent Activity so we can report the task's current
     * progress and results. The Android framework will pass us a reference to the
     * newly created Activity after each configuration change.
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!(activity instanceof TaskCallbacks)) {
            throw new IllegalStateException("Activity must implement the TaskCallbacks interface.");
        }

        // Hold a reference to the parent Activity so we can report back the task's
        // current progress and results.
        mCallbacks = (TaskCallbacks) activity;
    }

    /**
     * This method is called once when the Fragment is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    /**
     * Note that this method is <em>not</em> called when the Fragment is being
     * retained across Activity instances. It will, however, be called when its
     * parent Activity is being destroyed for good (such as when the user clicks
     * the back button, etc.).
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        cancel();
    }

    /*****************************/
    /***** TASK FRAGMENT API *****/
    /*****************************/

    /**
     * Start the background task.
     */
    public void start() {
        if (!mRunning) {
            mTask = new DummyTask();
            mTask.execute();
            mRunning = true;
        }
    }

    /**
     * Cancel the background task.
     */
    public void cancel() {
        if (mRunning) {
            mTask.cancel(false);
            mTask = null;
            mRunning = false;
        }
    }

    /**
     * Returns the current state of the background task.
     */
    public boolean isRunning() {
        return mRunning;
    }

    /***************************/
    /***** BACKGROUND TASK *****/
    /***************************/

    /**
     * A dummy task that performs some (dumb) background work and proxies progress
     * updates and results back to the Activity.
     */
    private class DummyTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected void onPreExecute() {
            // Proxy the call to the Activity.
            mCallbacks.onPreExecute();
            mRunning = true;
        }

        /**
         * Note that we do NOT call the callback object's methods directly from the
         * background thread, as this could result in a race condition.
         */
        @Override
        protected Void doInBackground(Void... ignore) {
            for (int i = 0; !isCancelled() && i < 100; i++) {
                SystemClock.sleep(100);
                publishProgress(i);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... percent) {
            // Proxy the call to the Activity.
            mCallbacks.onProgressUpdate(percent[0]);
        }

        @Override
        protected void onCancelled() {
            // Proxy the call to the Activity.
            mCallbacks.onCancelled();
            mRunning = false;
        }

        @Override
        protected void onPostExecute(Void ignore) {
            // Proxy the call to the Activity.
            mCallbacks.onPostExecute();
            mRunning = false;
        }
    }
}