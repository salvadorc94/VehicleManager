package com.example.maris.vehiclemanager.Model.Database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.example.maris.vehiclemanager.Model.Database.TypeConverters.DateConverter;

import java.util.Date;

/**
 * Database Notification entity
 */

@Entity
@TypeConverters(DateConverter.class)
public class Notification {
    @PrimaryKey(autoGenerate = true)
    private int idNot;

    @ForeignKey(entity = Expense.class, parentColumns = "idExp", childColumns = "idExp")
    private int idExp;

    private Date date;

    public Notification(int idNot, int idExp, Date date) {
        this.idNot = idNot;
        this.idExp = idExp;
        this.date = date;
    }

    public int getIdNot() {
        return idNot;
    }

    public int getIdExp() {
        return idExp;
    }

    public Date getDate() {
        return date;
    }
}
