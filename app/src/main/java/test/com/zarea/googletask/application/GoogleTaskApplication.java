package test.com.zarea.googletask.application;

import android.app.Application;

import com.activeandroid.ActiveAndroid;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.services.tasks.TasksScopes;

/**
 * Created by zarea on 2/22/16.
 */
public class GoogleTaskApplication extends Application {
    private static final String PREF_ACCOUNT_NAME = "accountName";
    private static final String[] SCOPES = {TasksScopes.TASKS_READONLY};
    public GoogleAccountCredential mCredential;

    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }
}
