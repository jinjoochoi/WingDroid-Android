package com.example.choijinjoo.wingdroid.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.parceler.Parcel;


/**
 * Created by choijinjoo on 2017. 8. 4..
 */

@DatabaseTable(tableName = "category")
@Parcel(value = Parcel.Serialization.BEAN)
public class Category {
    public final static String ID_FIELD_NAME = "id";

    @DatabaseField(unique = true, id = true, columnName = ID_FIELD_NAME)
    String id;
    @DatabaseField
    String name;
    @DatabaseField
    boolean selected ;

    public Category() {}

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getSelected() { return selected; }

    public void setSelected(boolean selected) { this.selected = selected; }


    /*
     * Custom method
     */


    public void selected() {
        this.selected = !this.selected;
    }

}
