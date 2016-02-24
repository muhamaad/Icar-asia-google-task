package test.com.zarea.googletask.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.api.services.tasks.model.Task;

import java.util.ArrayList;

import test.com.zarea.googletask.R;
import test.com.zarea.googletask.application.GoogleTaskApplication;
import test.com.zarea.googletask.database.ItemsDb;
import test.com.zarea.googletask.database.PendingDB;
import test.com.zarea.googletask.googleTaskApis.OnFinishAction;
import test.com.zarea.googletask.googleTaskApis.TaskController;
import test.com.zarea.googletask.util.Misc;
import test.com.zarea.googletask.view.TaskManagerActivity;

/**
 * Created by zarea on 2/23/16.
 */
public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskListHolder> {
    private ArrayList<ItemsDb> itemArrayList;
    private Context context;
    private GoogleTaskApplication globalVariable;

    public TaskListAdapter(Context context, ArrayList<ItemsDb> itemArrayList) {
        this.context = context;
        this.itemArrayList = itemArrayList;
    }

    @Override
    public TaskListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list_adapter, parent, false);
        return new TaskListHolder(v);
    }

    @Override
    public void onBindViewHolder(final TaskListHolder holder, final int position) {
        holder.titleTextView.setText(itemArrayList.get(position).getTitle());
        if (itemArrayList.get(position).getState().equals("updated") || itemArrayList.get(position).getState().equals("offline")) {
            holder.itemLinearLayout.setBackgroundColor(Color.rgb(250, 96, 0));
        } else if (itemArrayList.get(position).getState().equals("failed")) {
            holder.itemLinearLayout.setBackgroundColor(Color.rgb(249, 15, 0));
        } else {
            holder.itemLinearLayout.setBackgroundColor(Color.rgb(73, 255, 0));
        }
        holder.itemLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TaskManagerActivity.class);
                intent.putExtra("actionType", "update");
                intent.putExtra("title", itemArrayList.get(position).getTitle());
                intent.putExtra("notes", itemArrayList.get(position).getNotes());
                intent.putExtra("itemId", itemArrayList.get(position).getItemId());
                intent.putExtra("position", position);
                ((Activity) context).startActivityForResult(intent, 2000);
            }
        });
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Misc.isDeviceOnline(context)) {
                    deleteOnline(position);
                } else {
                    deleteOffline(position);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }

    private void deleteOnline(final int position) {
        globalVariable = (GoogleTaskApplication) context.getApplicationContext();
        Task task = new Task();
        task.setId(itemArrayList.get(position).getItemId());
        task.setTitle(itemArrayList.get(position).getTitle());
        task.setNotes(itemArrayList.get(position).getNotes());
        TaskController taskController = new TaskController(globalVariable.mCredential, Misc.actionType.delete.toString());
        taskController.delegate = new OnFinishAction() {
            @Override
            public void onSuccess(Task task) {
                ItemsDb.deleteItemById(task.getId());
                itemArrayList.remove(position);
                notifyDataSetChanged();
            }
        };
        taskController.execute(task);
    }

    private void deleteOffline(int position) {
        PendingDB.insert(itemArrayList.get(position).getItemId(), itemArrayList.get(position).getTitle(), itemArrayList.get(position).getNotes(), Misc.actionType.delete.toString());
        ItemsDb.deleteItemById(itemArrayList.get(position).getItemId());
        itemArrayList.remove(position);
        notifyDataSetChanged();
    }

    public class TaskListHolder extends RecyclerView.ViewHolder {
        protected TextView titleTextView;
        protected Button deleteButton;
        protected LinearLayout itemLinearLayout;

        public TaskListHolder(View v) {
            super(v);
            titleTextView = (TextView) v.findViewById(R.id.title_text_view);
            deleteButton = (Button) v.findViewById(R.id.delete_button);
            itemLinearLayout = (LinearLayout) v.findViewById(R.id.item_linear_layout);
        }

    }
}
