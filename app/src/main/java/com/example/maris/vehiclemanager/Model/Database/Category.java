package com.example.maris.vehiclemanager.Model.Database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Database Category entity
 */
@Entity
public class Category {
    @PrimaryKey(autoGenerate = true)
    private int idCat;

    private String category;
    private String type;

    public Category(int idCat, String category, String type) {
        this.idCat = idCat;
        this.category = category;
        this.type = type;
    }

    public int getIdCat() {
        return idCat;
    }

    public String getCategory() {
        return category;
    }

    public String getType() {
        return type;
    }
}
