package test.com.zarea.googletask.googleTaskApis;

import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.tasks.model.TaskList;

import java.io.IOException;

/**
 * Created by zarea on 2/23/16.
 */
public class GetTaskListWebService extends AsyncTask<String, Void, TaskList> {
    private com.google.api.services.tasks.Tasks mService = null;
    private Exception mLastError = null;

    public GetTaskListWebService(GoogleAccountCredential credential) {
        HttpTransport transport = AndroidHttp.newCompatibleTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        mService = new com.google.api.services.tasks.Tasks.Builder(
                transport, jsonFactory, credential)
                .setApplicationName("Google Tasks API Android Quickstart")
                .build();
    }

    /**
     * Background task to call Google Tasks API.
     *
     * @param params no parameters needed for this task.
     */
    @Override
    protected TaskList doInBackground(String... params) {
        try {
            return mService.tasklists().get(params[0]).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void onPostExecute(TaskList taskList) {
        Log.d("test", taskList.getTitle());
    }

}