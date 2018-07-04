package com.example.maris.vehiclemanager.Model.Database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.example.maris.vehiclemanager.Model.Database.TypeConverters.DateConverter;

import java.util.Date;

/**
 * Database Expense entity
 */

@Entity
@TypeConverters(DateConverter.class)
public class Expense {
    @PrimaryKey(autoGenerate = true)
    private int idExp;

    @ForeignKey(entity = Category.class, parentColumns = "idCat", childColumns = "idCat")
    private int idCat;

    @ForeignKey(entity = Vehicle.class, parentColumns = "idCar", childColumns = "idCar")
    private int idCar;

    private String expense;

    private float cost;

    private String place;

    private String receipt;

    private Date date;

    public Expense(int idExp, int idCat, int idCar, String expense, float cost, String place, String receipt, Date date) {
        this.idExp = idExp;
        this.idCat = idCat;
        this.idCar = idCar;
        this.expense = expense;
        this.cost = cost;
        this.place = place;
        this.receipt = receipt;
        this.date = date;
    }

    public int getIdExp() {
        return idExp;
    }

    public int getIdCat() {
        return idCat;
    }

    public int getIdCar() {
        return idCar;
    }

    public String getExpense() {
        return expense;
    }

    public float getCost() {
        return cost;
    }

    public String getPlace() {
        return place;
    }

    public String getReceipt() {
        return receipt;
    }

    public Date getDate() {
        return date;
    }
}
