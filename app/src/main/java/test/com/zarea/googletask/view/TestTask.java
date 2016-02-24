package test.com.zarea.googletask.view;

import android.accounts.AccountManager;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.tasks.TasksScopes;
import com.google.api.services.tasks.model.Task;
import com.google.api.services.tasks.model.Tasks;

import java.util.Arrays;
import java.util.List;

import test.com.zarea.googletask.application.GoogleTaskApplication;
import test.com.zarea.googletask.database.ItemsDb;
import test.com.zarea.googletask.database.PendingDB;
import test.com.zarea.googletask.googleTaskApis.GetTasksWebService;
import test.com.zarea.googletask.googleTaskApis.OnFinishAction;
import test.com.zarea.googletask.googleTaskApis.OnFinishGetTasks;
import test.com.zarea.googletask.googleTaskApis.TaskController;
import test.com.zarea.googletask.util.Misc;

import static test.com.zarea.googletask.util.Misc.isDeviceOnline;

/**
 * Created by zarea on 2/23/16.
 */
public class TestTask  extends Activity {
    private TextView mOutputText;
    ProgressDialog mProgress;
    private Button reload;
    private  GoogleTaskApplication globalVariable;

    static final int REQUEST_ACCOUNT_PICKER = 1000;
    static final int REQUEST_AUTHORIZATION = 1001;
    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    private static final String PREF_ACCOUNT_NAME = "accountName";
    private static final String[] SCOPES = { TasksScopes.TASKS_READONLY };

    /**
     * Create the main activity.
     * @param savedInstanceState previously saved instance data.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        globalVariable = (GoogleTaskApplication) getApplicationContext();
        SharedPreferences settings = getPreferences(Context.MODE_PRIVATE);
        globalVariable.mCredential = GoogleAccountCredential.usingOAuth2(
                getApplicationContext(), Arrays.asList(SCOPES))
                .setBackOff(new ExponentialBackOff())
                .setSelectedAccountName(settings.getString(PREF_ACCOUNT_NAME, null));
        LinearLayout activityLayout = new LinearLayout(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        activityLayout.setLayoutParams(lp);
        activityLayout.setOrientation(LinearLayout.VERTICAL);
        activityLayout.setPadding(16, 16, 16, 16);

        ViewGroup.LayoutParams tlp = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        reload = new Button(this);
        mOutputText = new TextView(this);
        mOutputText.setLayoutParams(tlp);
        mOutputText.setPadding(16, 16, 16, 16);
        mOutputText.setVerticalScrollBarEnabled(true);
        mOutputText.setMovementMethod(new ScrollingMovementMethod());
        activityLayout.addView(reload);
        activityLayout.addView(mOutputText);

        mProgress = new ProgressDialog(this);
        mProgress.setMessage("Calling Google Tasks API ...");

        setContentView(activityLayout);
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Task task = new Task();
                task.setTitle("test");
                task.setNotes("asdfjh");
                task.setDue(new DateTime(System.currentTimeMillis() + 3600000));
                TaskController taskController = new TaskController(globalVariable.mCredential, Misc.actionType.insert.toString());
                taskController.onfinish = new OnFinishAction() {
                    @Override
                    public void onSuccess(Task task) {
                        Log.d("tasktest", task.getTitle());
                        mOutputText.setText("");
                        GetTasksWebService getTasksWebService = new GetTasksWebService(globalVariable.mCredential);
                        getTasksWebService.delegate = new OnFinishGetTasks() {
                            @Override
                            public void onSuccess(Tasks tasks) {
                                String s = "";
                                for(Task task:tasks.getItems()){
                                    s+="\n"+task.getTitle();
                                }
                                mOutputText.setText(s);

                            }
                        };
                    }
                };
                taskController.execute(task);

            }
        });

    }


    /**
     * Called whenever this activity is pushed to the foreground, such as after
     * a call to onCreate().
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (isGooglePlayServicesAvailable()) {
            refreshResults();
        } else {
            mOutputText.setText("Google Play Services required: " +
                    "after installing, close and relaunch this app.");
        }
    }

    /**
     * Called when an activity launched here (specifically, AccountPicker
     * and authorization) exits, giving you the requestCode you started it with,
     * the resultCode it returned, and any additional data from it.
     * @param requestCode code indicating which activity result is incoming.
     * @param resultCode code indicating the result of the incoming
     *     activity result.
     * @param data Intent (containing result data) returned by incoming
     *     activity result.
     */
    @Override
    protected void onActivityResult(
            int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case REQUEST_GOOGLE_PLAY_SERVICES:
                if (resultCode != RESULT_OK) {
                    isGooglePlayServicesAvailable();
                }
                break;
            case REQUEST_ACCOUNT_PICKER:
                if (resultCode == RESULT_OK && data != null &&
                        data.getExtras() != null) {
                    String accountName =
                            data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null) {
                        globalVariable.mCredential.setSelectedAccountName(accountName);
                        SharedPreferences settings =
                                getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString(PREF_ACCOUNT_NAME, accountName);
                        editor.apply();
                    }
                } else if (resultCode == RESULT_CANCELED) {
                    mOutputText.setText("Account unspecified.");
                }
                break;
            case REQUEST_AUTHORIZATION:
                if (resultCode != RESULT_OK) {
                    chooseAccount();
                }
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Attempt to get a set of data from the Google Tasks API to display. If the
     * email address isn't known yet, then call chooseAccount() method so the
     * user can pick an account.
     */
    private void refreshResults() {
        if (globalVariable.mCredential.getSelectedAccountName() == null) {
            chooseAccount();
        } else {
            if (isDeviceOnline(TestTask.this)) {
                final List<PendingDB> pendingDBs = PendingDB.getAll();
                for(int i = 0 ;i<pendingDBs.size();i++){
                    TaskController taskController = new TaskController(globalVariable.mCredential,pendingDBs.get(i).getActionType());
                    final int finalI = i;
                    taskController.onfinish = new OnFinishAction() {
                        @Override
                        public void onSuccess(Task task) {
                            pendingDBs.get(finalI).deleteItem();
                        }
                    };
                    taskController.execute(pendingDBs.get(i).getTask());
                }

                GetTasksWebService getTasksWebService = new GetTasksWebService(globalVariable.mCredential);
                getTasksWebService.delegate = new OnFinishGetTasks() {
                    @Override
                    public void onSuccess(Tasks tasks) {
                        List<ItemsDb>itemsDbs = ItemsDb.getAll();
                       for (int i=0;i<itemsDbs.size();i++){
                           itemsDbs.get(i).deleteItem();
                       }
                        for(Task task:tasks.getItems()){
                            ItemsDb.insert(task.getId(),task.getTitle(),task.getNotes());
                        }
                        Intent intent = new Intent(TestTask.this,TaskListActivity.class);
                        startActivity(intent);

                    }
                };
                getTasksWebService.execute();
            } else {
                Intent intent = new Intent(TestTask.this,TaskListActivity.class);
                startActivity(intent);
            }
        }
    }

    /**
     * Starts an activity in Google Play Services so the user can pick an
     * account.
     */
    private void chooseAccount() {
        startActivityForResult(
                globalVariable.mCredential.newChooseAccountIntent(), REQUEST_ACCOUNT_PICKER);
    }



    /**
     * Check that Google Play services APK is installed and up to date. Will
     * launch an error dialog for the user to update Google Play Services if
     * possible.
     * @return true if Google Play Services is available and up to
     *     date on this device; false otherwise.
     */
    private boolean isGooglePlayServicesAvailable() {
        final int connectionStatusCode =
                GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (GooglePlayServicesUtil.isUserRecoverableError(connectionStatusCode)) {
            showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode);
            return false;
        } else if (connectionStatusCode != ConnectionResult.SUCCESS ) {
            return false;
        }
        return true;
    }

    /**
     * Display an error dialog showing that Google Play Services is missing
     * or out of date.
     * @param connectionStatusCode code describing the presence (or lack of)
     *     Google Play Services on this device.
     */
    void showGooglePlayServicesAvailabilityErrorDialog(
            final int connectionStatusCode) {
        Dialog dialog = GooglePlayServicesUtil.getErrorDialog(
                connectionStatusCode,
                TestTask.this,
                REQUEST_GOOGLE_PLAY_SERVICES);
        dialog.show();
    }


}
