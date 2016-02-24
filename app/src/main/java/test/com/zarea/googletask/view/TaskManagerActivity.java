package test.com.zarea.googletask.view;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.api.client.util.DateTime;
import com.google.api.services.tasks.model.Task;

import test.com.zarea.googletask.R;
import test.com.zarea.googletask.application.GoogleTaskApplication;
import test.com.zarea.googletask.database.PendingDB;
import test.com.zarea.googletask.googleTaskApis.OnFinishAction;
import test.com.zarea.googletask.googleTaskApis.TaskController;
import test.com.zarea.googletask.util.Misc;

public class TaskManagerActivity extends AppCompatActivity implements View.OnClickListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    GoogleApiClient mGoogleApiClient;
    private GoogleTaskApplication globalVariable;
    private Toolbar toolbar;
    private EditText titleEditText;
    private EditText noteEditText;
    private Button doneButton;
    private String actionType;
    private int position = 0;
    private String itemId;
    private double lat, lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_manager);
        initViews();
        init();
        getIntents();

    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        titleEditText = (EditText) findViewById(R.id.title_edit_text);
        noteEditText = (EditText) findViewById(R.id.notes_edit_text);
        doneButton = (Button) findViewById(R.id.done_button);
        setSupportActionBar(toolbar);
    }

    private void init() {
        globalVariable = (GoogleTaskApplication) getApplicationContext();
        doneButton.setOnClickListener(this);
    }


    private void getIntents() {
        Intent intent = getIntent();
        actionType = intent.getStringExtra("actionType");
        if (actionType.equals("insert")) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            mGoogleApiClient.connect();

        } else if (actionType.equals("update")) {
            itemId = intent.getStringExtra("itemId");
            titleEditText.setText(intent.getStringExtra("title"));
            noteEditText.setText(intent.getStringExtra("notes"));
            position = intent.getIntExtra("position", 0);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.done_button:
                if (actionType.equals("insert")) {
                    addNewTask();
                } else if (actionType.equals("update")) {
                    updateTask();
                }
                break;
        }
    }


    private void addNewTask() {
        if (!titleEditText.getText().toString().equals("") && !noteEditText.getText().toString().equals("")) {
            if (Misc.isDeviceOnline(TaskManagerActivity.this)) {
                Task task = new Task();
                task.setTitle(titleEditText.getText().toString());
                task.setNotes(noteEditText.getText().toString());
                task.setDue(new DateTime(System.currentTimeMillis() + 3600000));
                TaskController taskController = new TaskController(globalVariable.mCredential, Misc.actionType.insert.toString());
                taskController.delegate = new OnFinishAction() {
                    @Override
                    public void onSuccess(Task task) {
                        Intent intent = new Intent();
                        intent.putExtra("id", task.getId());
                        intent.putExtra("title", task.getTitle());
                        intent.putExtra("notes", task.getNotes());
                        intent.putExtra("position", position);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                };
                taskController.execute(task);
            } else {
                String id = new DateTime(System.currentTimeMillis()).toString();
                Long aLong = PendingDB.insertNewTask(titleEditText.getText().toString(), noteEditText.getText().toString(), Misc.actionType.insert.toString());
                Intent intent = new Intent();
                intent.putExtra("id", id);
                intent.putExtra("title", titleEditText.getText().toString());
                intent.putExtra("notes", noteEditText.getText().toString());
                intent.putExtra("position", position);
                setResult(RESULT_OK, intent);
                finish();
            }
        } else {
            Toast.makeText(TaskManagerActivity.this, "Please fill all failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateTask() {
        if (!titleEditText.getText().toString().equals("") && !noteEditText.getText().toString().equals("")) {
            if (Misc.isDeviceOnline(TaskManagerActivity.this)) {
                Task task = new Task();
                task.setId(itemId);
                task.setTitle(titleEditText.getText().toString());
                task.setNotes(noteEditText.getText().toString());
                task.setDue(new DateTime(System.currentTimeMillis() + 3600000));
                TaskController taskController = new TaskController(globalVariable.mCredential, Misc.actionType.update.toString());
                taskController.delegate = new OnFinishAction() {
                    @Override
                    public void onSuccess(Task task) {
                        Intent intent = new Intent();
                        intent.putExtra("id", task.getId());
                        intent.putExtra("title", task.getTitle());
                        intent.putExtra("notes", task.getNotes());
                        intent.putExtra("position", position);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                };
                taskController.execute(task);
            } else {
                Long aLong = PendingDB.insertUpdate(itemId, titleEditText.getText().toString(), noteEditText.getText().toString(), Misc.actionType.update.toString());
                Intent intent = new Intent();
                intent.putExtra("id", itemId);
                intent.putExtra("title", titleEditText.getText().toString());
                intent.putExtra("notes", noteEditText.getText().toString());
                intent.putExtra("position", position);
                setResult(RESULT_OK, intent);
                finish();
            }
        } else {
            Toast.makeText(TaskManagerActivity.this, "Please fill all failed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        String location = "Current location:\n" + "Latitude: " + mLastLocation.getLatitude() + "\n" + "Longitude: " + mLastLocation.getLongitude();
        noteEditText.setText(location);
        mGoogleApiClient.disconnect();

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
