package test.com.zarea.googletask.googleTaskApis;

import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.tasks.model.Tasks;

import java.io.IOException;

/**
 * Created by zarea on 2/23/16.
 */
public class GetTasksWebService  extends AsyncTask<String, Void, Tasks> {
    private com.google.api.services.tasks.Tasks mService = null;
    private Exception mLastError = null;
    GoogleAccountCredential googleAccountCredential;
    public OnFinishGetTasks delegate;

    public GetTasksWebService(GoogleAccountCredential credential) {
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
    protected Tasks doInBackground(String... params) {
        try {
            return mService.tasks().list("@default").execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void onPostExecute(Tasks tasks) {
        delegate.onSuccess(tasks);
    }

}
