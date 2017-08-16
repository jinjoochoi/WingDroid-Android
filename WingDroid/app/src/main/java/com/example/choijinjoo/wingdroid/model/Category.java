package com.example.choijinjoo.wingdroid.model;

import org.parceler.Parcel;


/**
 * Created by choijinjoo on 2017. 8. 4..
 */

@Parcel(value = Parcel.Serialization.BEAN)
public class Category extends FBModel{
    String name;
    boolean selected;

    public Category() {}

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


    public void selected() { this.selected = !this.selected; }

}
