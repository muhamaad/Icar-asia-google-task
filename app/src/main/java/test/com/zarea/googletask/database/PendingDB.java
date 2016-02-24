package test.com.zarea.googletask.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.google.api.client.util.DateTime;
import com.google.api.services.tasks.model.Task;

import java.util.List;

/**
 * Created by zarea on 2/24/16.
 */
@Table(name = "PendingTable")
public class PendingDB extends Model {

    @Column(name = "ActionType")
    String actionType;
    @Column(name = "Due")
    DateTime due;
    @Column(name = "ItemId")
    private String itemId;
    @Column(name = "Title")
    private String title;
    @Column(name = "Notes")
    private String notes;

    public PendingDB() {
        super();
    }

    public PendingDB(String itemId, String title, String notes, String actionType) {
        this.itemId = itemId;
        this.title = title;
        this.notes = notes;
        this.actionType = actionType;
        due = new DateTime(System.currentTimeMillis() + 3600000);
    }

    public static Long insertNewTask(String title, String notes, String actionType) {
        return new PendingDB(null, title, notes, actionType).save();
    }

    public static Long insertUpdate(String id,String title, String notes, String actionType) {
        return new PendingDB(id, title, notes, actionType).save();
    }


    public static Long insert(String itemId, String title, String notes, String actionType) {
        return new PendingDB(itemId, title, notes, actionType).save();
    }

    public static PendingDB getItem() {
        return new Select().from(PendingDB.class).orderBy("Id DESC").executeSingle();
    }

    public static List<PendingDB> getAll() {
        return new Select()
                .from(PendingDB.class)
                .orderBy("Id ASC")
                .execute();
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public DateTime getDue() {
        return due;
    }

    public void setDue(DateTime due) {
        this.due = due;
    }

    public Task getTask() {
        Task task = new Task();
        task.setId(itemId);
        task.setTitle(title);
        task.setNotes(notes);
        task.setDue(due);
        return task;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public void deleteItem() {
        try {
            new Delete().from(PendingDB.class).where("Id = ?", getId()).execute();
        } catch (Exception e) {
        }
    }
}
