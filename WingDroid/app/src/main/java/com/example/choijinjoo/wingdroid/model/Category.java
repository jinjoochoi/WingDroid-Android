package com.example.choijinjoo.wingdroid.model;

import org.parceler.Parcel;


/**
 * Created by choijinjoo on 2017. 8. 4..
 */

@Parcel(value = Parcel.Serialization.BEAN)
public class Category {
    Integer id;
    String name;
    boolean selected;
    boolean simulatable;

    public Category() {}

    public Category(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getSelected() { return selected; }

    public void setSelected(boolean selected) { this.selected = selected; }

    public boolean isSimulatable() { return simulatable; }

    public void setSimulatable(boolean simulatable) { this.simulatable = simulatable; }

    /*
     * Custom method
     */


    public void selected() { this.selected = !this.selected; }

}
