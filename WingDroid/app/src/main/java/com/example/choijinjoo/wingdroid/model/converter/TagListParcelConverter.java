package com.example.choijinjoo.wingdroid.model.converter;

import android.os.Parcel;

import com.example.choijinjoo.wingdroid.model.Image;
import com.example.choijinjoo.wingdroid.model.Tag;

import org.parceler.Parcels;

/**
 * Created by choijinjoo on 2017. 8. 10..
 */

public class TagListParcelConverter extends RealmListParcelConverter<Tag> {
    @Override
    public void itemToParcel(Tag input, Parcel parcel) {
        parcel.writeParcelable(Parcels.wrap(input), 0);
    }

    @Override
    public Tag itemFromParcel(Parcel parcel) {
        return Parcels.unwrap(parcel.readParcelable(Image.class.getClassLoader()));
    }
}
