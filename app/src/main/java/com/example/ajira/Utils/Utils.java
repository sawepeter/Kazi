package com.example.ajira.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

public class Utils {

    @FunctionalInterface
    public interface Function{
        void apply();
    }

    public static boolean hasNetwork(Context context) {
        boolean isConnected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) isConnected = true;
        return isConnected;
    }

    public static void runAsyncTask(Function method) {
        new AsyncTaskRunner(method).execute();
    }

    public static void runAsyncTask(Function method, Function postExecute) {
        new AsyncTaskRunner(method).setPostExecutor(postExecute).execute();
    }

    private static class AsyncTaskRunner extends AsyncTask<String, Void, String> {
        private Function method;
        private Function postExecute;

        public AsyncTaskRunner(Function method) {
            this.method = method;
        }

        public AsyncTaskRunner setPostExecutor(Function postExecute) {
            this.postExecute = postExecute;
            return this;
        }

        @Override
        protected String doInBackground(String... strings) {
            method.apply();
            return "Data Loaded";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("AsyncTaskRunner", "Task completed: " + s);
            if (null != postExecute)
                postExecute.apply();
        }
    }
}
