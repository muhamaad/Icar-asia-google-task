package test.com.zarea.googletask.googleTaskApis;

import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.tasks.model.Task;

import java.io.IOException;

/**
 * Created by zarea on 2/23/16.
 */
public class TaskController extends AsyncTask<Task, Void, Task> {
    public OnFinishAction delegate;
    private com.google.api.services.tasks.Tasks mService = null;
    private Exception mLastError = null;
    private String actionType = "";

    public TaskController(GoogleAccountCredential credential, String actionType) {
        this.actionType = actionType;
        HttpTransport transport = AndroidHttp.newCompatibleTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        mService = new com.google.api.services.tasks.Tasks.Builder(
                transport, jsonFactory, credential)
                .setApplicationName("Google Tasks API Android Quickstart")
                .build();
    }

    /**
     * @param task
     * @return
     */
    @Override
    protected Task doInBackground(Task... task) {
        try {
            if (actionType.equals("insert")) {
                return insert(task[0]);
            } else if (actionType.equals("update")) {
                return update(task[0]);
            } else if (actionType.equals("delete")) {
                delete(task[0]);
                return task[0];
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void onPostExecute(Task task) {
        delegate.onSuccess(task);

    }

    private Task insert(Task task) throws IOException {
        Task result = mService.tasks().insert("@default", task).execute();
        return result;
    }

    private Task update(Task task) throws IOException {
        Task updatedTask = mService.tasks().get("@default", task.getId()).execute();
        updatedTask.setTitle(task.getTitle());
        updatedTask.setNotes(task.getNotes());
        return mService.tasks().update("@default", task.getId(), updatedTask).execute();
    }

    private void delete(Task task) throws IOException {
        mService.tasks().delete("@default", task.getId()).execute();
    }

}