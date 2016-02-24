package test.com.zarea.googletask.database;

import android.util.Log;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by zarea on 2/24/16.
 */
@Table(name = "Items")
public class ItemsDb extends Model {

    @Column(name = "ItemId")
    private String itemId;

    @Column(name = "Title")
    private String title;

    @Column(name = "Notes")
    private String notes;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    private String state;

    public ItemsDb(){
        super();
    }


    public String getItemId() {
        return itemId;
    }

    public String getTitle() {
        return title;
    }

    public String getNotes() {
        return notes;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public ItemsDb(String id, String title, String notes) {
        this.itemId = id;
        this.title = title;
        this.notes = notes;
    }

    public static ItemsDb getItem() {
        return new Select().from(ItemsDb.class).orderBy("Id DESC").executeSingle();
    }

    public void deleteItem() {
        try {
            new Delete().from(ItemsDb.class).where("Id = ?", getId()).execute();
        } catch (Exception e) {
            Log.e("deleteAuthFromAuth", e.toString());
        }
    }
    public static void deleteItemById(String itemId) {
        try {
            new Delete().from(ItemsDb.class).where("ItemId = ?", itemId).execute();
        } catch (Exception e) {
            Log.e("deleteAuthFromAuth", e.toString());
        }
    }
    public static Long insert(String id, String title, String notes){
        return new ItemsDb(id,title,notes).save();
    }

    public static ItemsDb update(ItemsDb itemsDb){
        ItemsDb itemsDb1 = new Select().from(ItemsDb.class).where("ItemId = ?", itemsDb.getItemId()).executeSingle();
        itemsDb1.title = itemsDb.title;
        itemsDb1.notes = itemsDb.notes;
        itemsDb1.save();
        return itemsDb1;
    }
    public static List<ItemsDb> getAll(){
        return new Select()
                .from(ItemsDb.class)
                .orderBy("Id ASC")
                .execute();
    }
}
