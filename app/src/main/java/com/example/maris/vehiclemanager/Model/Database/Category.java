package com.example.maris.vehiclemanager.Model.Database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Database Category entity
 */
@Entity
public class Category implements Parcelable{
    @PrimaryKey(autoGenerate = true)
    private int idCat;

    private String category;
    private String type;

    public Category(int idCat, String category, String type) {
        this.idCat = idCat;
        this.category = category;
        this.type = type;
    }

    @Ignore
    protected Category(Parcel in) {
        idCat = in.readInt();
        category = in.readString();
        type = in.readString();
    }

    /**
     * A constructor that sets the idCat to 0.
     * Be sure to fill all other values before trying to insert to database, otherwise unexpected behavior may present.
     */
    @Ignore
    public Category(){
        this.idCat = 0;
    }

    @Ignore
    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    public int getIdCat() {
        return idCat;
    }

    public String getCategory() {
        return category;
    }

    public String getType() {
        return type;
    }

    public void setIdCat(int idCat) {
        this.idCat = idCat;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return category;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(idCat);
        parcel.writeString(category);
        parcel.writeString(type);
    }
}
