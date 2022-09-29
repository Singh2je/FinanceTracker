package home.project.group.financetracker.EntityClass;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "categories")
public class CategoriesModel {

    //Primary Key
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int key;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "type")
    private String type;

    public int getKey() { return key; };

    public void setKey(int key) { this.key = key; };

    public String getName() { return name; };

    public void setName(String name) { this.name = name; }

    public String getType() { return type; };

    public void setType(String type) { this.type = type; };
}
