package com.example.choijinjoo.wingdroid.model.converter;

import android.os.Parcel;

import com.example.choijinjoo.wingdroid.model.Tag;

import org.parceler.ParcelConverter;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class TagListParcelConverter implements ParcelConverter<List<Tag>> {
    @Override
    public void toParcel(List<Tag> input, Parcel parcel) {
        if (input == null) {
            parcel.writeInt(-1);
        }
        else {
            parcel.writeInt(input.size());
            for (Tag Tag : input) {
                parcel.writeParcelable(Parcels.wrap(Tag), 0);
            }
        }
    }

    @Override
    public List<Tag> fromParcel(Parcel parcel) {
        int size = parcel.readInt();
        if (size < 0) return null;
        List<Tag> Tags = new ArrayList<>();
        for (int i = 0; i < size; ++i) {
            Tags.add(Parcels.unwrap(parcel.readParcelable(Tag.class.getClassLoader())));
        }
        return Tags;
    }
}