package com.example.choijinjoo.wingdroid.model.converter;

import android.os.Parcel;

import com.example.choijinjoo.wingdroid.model.Gif;
import com.example.choijinjoo.wingdroid.model.Image;

import org.parceler.Parcels;

/**
 * Created by choijinjoo on 2017. 8. 10..
 */

public class GifListParcelConverter extends RealmListParcelConverter<Gif> {
    @Override
    public void itemToParcel(Gif input, Parcel parcel) {
        parcel.writeParcelable(Parcels.wrap(input), 0);
    }

    @Override
    public Gif itemFromParcel(Parcel parcel) {
        return Parcels.unwrap(parcel.readParcelable(Image.class.getClassLoader()));
    }
}
