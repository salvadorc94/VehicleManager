package com.example.maris.vehiclemanager.Model.Database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.maris.vehiclemanager.Model.Database.TypeConverters.DateConverter;

import java.util.Date;

/**
 * Database Expense entity
 */

@Entity
@TypeConverters(DateConverter.class)
public class Expense implements Parcelable{
    @PrimaryKey(autoGenerate = true)
    private int idExp;

    @ForeignKey(entity = Category.class, parentColumns = "idCat", childColumns = "idCat" , onDelete = ForeignKey.SET_NULL)
    private int idCat;

    @ForeignKey(entity = Vehicle.class, parentColumns = "idCar", childColumns = "idCar", onDelete = ForeignKey.SET_NULL)
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

    @Ignore
    protected Expense(Parcel in) {
        idExp = in.readInt();
        idCat = in.readInt();
        idCar = in.readInt();
        expense = in.readString();
        cost = in.readFloat();
        place = in.readString();
        receipt = in.readString();
        date = DateConverter.toDate(Long.parseLong(in.readString()));
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

    public void setIdExp(int idExp) {
        this.idExp = idExp;
    }

    public void setIdCat(int idCat) {
        this.idCat = idCat;
    }

    public void setIdCar(int idCar) {
        this.idCar = idCar;
    }

    public void setExpense(String expense) {
        this.expense = expense;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(idExp);
        parcel.writeInt(idCat);
        parcel.writeInt(idCar);
        parcel.writeString(expense);
        parcel.writeFloat(cost);
        parcel.writeString(place);
        parcel.writeString(receipt);
        parcel.writeString(DateConverter.fromDate(date).toString());
    }

    @Ignore
    public static final Creator<Expense> CREATOR = new Creator<Expense>() {
        @Override
        public Expense createFromParcel(Parcel in) {
            return new Expense(in);
        }

        @Override
        public Expense[] newArray(int size) {
            return new Expense[size];
        }
    };

}
