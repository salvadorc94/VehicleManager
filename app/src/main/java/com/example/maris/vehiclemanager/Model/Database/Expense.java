package com.example.maris.vehiclemanager.Model.Database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

/**
 * Database Expense entity
 */

@Entity
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

    private String date;

    public Expense(int idExp, int idCat, int idCar, String expense, float cost, String place, String receipt, String date) {
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

    public String getDate() {
        return date;
    }
}
