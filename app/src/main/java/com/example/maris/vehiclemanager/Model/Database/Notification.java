package com.example.maris.vehiclemanager.Model.Database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

/**
 * Database Notification entity
 */

@Entity
public class Notification {
    @PrimaryKey(autoGenerate = true)
    private int idNot;

    @ForeignKey(entity = Expense.class, parentColumns = "idExp", childColumns = "idExp")
    private int idExp;

    private String time;

    public Notification(int idNot, int idExp, String time) {
        this.idNot = idNot;
        this.idExp = idExp;
        this.time = time;
    }

    public int getIdNot() {
        return idNot;
    }

    public int getIdExp() {
        return idExp;
    }

    public String getTime() {
        return time;
    }
}
