package test.com.zarea.googletask.googleTaskApis;

import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.util.List;

/**
 * An asynchronous task that handles the Google Tasks API call.
 * Placing the API calls in their own task ensures the UI stays responsive.
 */
public class MakeRequestTask extends AsyncTask<Void, Void, List<String>> {
    private com.google.api.services.tasks.Tasks mService = null;
    private Exception mLastError = null;
    GoogleAccountCredential googleAccountCredential;

    public MakeRequestTask(GoogleAccountCredential credential) {
        googleAccountCredential = credential;
        HttpTransport transport = AndroidHttp.newCompatibleTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        mService = new com.google.api.services.tasks.Tasks.Builder(
                transport, jsonFactory, credential)
                .setApplicationName("Google Tasks API Android Quickstart")
                .build();
    }

    /**
     * Background task to call Google Tasks API.
     * @param params no parameters needed for this task.
     */
    @Override
    protected List<String> doInBackground(Void... params) {
        try {
            //return getDataFromApi();
        } catch (Exception e) {
            mLastError = e;
            cancel(true);
            return null;
        }
        return null;
    }
    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onPostExecute(List<String> output) {

        if (output == null || output.size() == 0) {

        } else {
            output.add(0, "Data retrieved using the Google Tasks API:");

        }
    }
//
//    @Override
//    protected void onCancelled() {
//        if (mLastError != null) {
//            if (mLastError instanceof GooglePlayServicesAvailabilityIOException) {
//                showGooglePlayServicesAvailabilityErrorDialog(
//                        ((GooglePlayServicesAvailabilityIOException) mLastError)
//                                .getConnectionStatusCode());
//            } else if (mLastError instanceof UserRecoverableAuthIOException) {
//                startActivityForResult(
//                        ((UserRecoverableAuthIOException) mLastError).getIntent(),
//                        TestTask.REQUEST_AUTHORIZATION);
//            } else {
//                mOutputText.setText("The following error occurred:\n"
//                        + mLastError.getMessage());
//            }
//        } else {
//            mOutputText.setText("Request cancelled.");
//        }
//    }
}