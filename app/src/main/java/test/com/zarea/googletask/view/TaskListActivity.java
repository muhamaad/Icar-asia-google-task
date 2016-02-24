package test.com.zarea.googletask.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import test.com.zarea.googletask.R;
import test.com.zarea.googletask.adapter.TaskListAdapter;
import test.com.zarea.googletask.database.ItemsDb;

public class TaskListActivity extends AppCompatActivity implements View.OnClickListener{
    private Toolbar toolbar;
    private RecyclerView taskRecyclerView;
    private TaskListAdapter taskListAdapter;
    private Button addNewTaskButton;
    private int ADD_NEW_TASK = 1990;
    ArrayList<ItemsDb>itemArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        initViews();
        init();
    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        taskRecyclerView = (RecyclerView) findViewById(R.id.task_recycler_view);
        addNewTaskButton = (Button) findViewById(R.id.add_new_task_button);
        getItems();
        taskListAdapter = new TaskListAdapter(TaskListActivity.this,itemArrayList);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(TaskListActivity.this));
        taskRecyclerView.setAdapter(taskListAdapter);
        setSupportActionBar(toolbar);
    }
    private void init(){
        addNewTaskButton.setOnClickListener(this);
    }
    private void getItems(){
        itemArrayList = (ArrayList<ItemsDb>) ItemsDb.getAll();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_new_task_button:
                Intent intent = new Intent(TaskListActivity.this,TaskManagerActivity.class);
                intent.putExtra("actionType","insert");
                startActivityForResult(intent,ADD_NEW_TASK);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode == ADD_NEW_TASK){
              addNewTask(data);
            }
            if(requestCode==2000){
                updateTask(data);
            }
        }
    }
    private void addNewTask(Intent data){
        String id = data.getStringExtra("id");
        String title = data.getStringExtra("title");
        String notes = data.getStringExtra("notes");
        ItemsDb.insert(id,title,notes);
        ItemsDb itemsDb = new ItemsDb(id,title,notes);
        itemArrayList.add(0,itemsDb);
        taskListAdapter.notifyDataSetChanged();
    }
    private void updateTask(Intent data){
        String id = data.getStringExtra("id");
        String title = data.getStringExtra("title");
        String notes = data.getStringExtra("notes");
        int position = data.getIntExtra("position",0);
        ItemsDb itemsDb = new ItemsDb(id,title,notes);
        ItemsDb.update(itemsDb);
        itemArrayList.get(position).setItemId(id);
        itemArrayList.get(position).setTitle(title);
        itemArrayList.get(position).setNotes(notes);
        taskListAdapter.notifyDataSetChanged();
    }
}
