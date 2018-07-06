package com.example.maris.vehiclemanager.Model.Database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Database Vehicle entity
 */

@Entity
public class Vehicle implements Parcelable{
    @PrimaryKey(autoGenerate = true)
    private int idCar;
    private String carPic;
    private String name;
    private String model;
    private int year;
    private long odometer;
    private String plate;
    private String gasoline;
    private boolean isCar;

    public Vehicle(int idCar, String carPic, String name, String model, int year, long odometer, String plate, String gasoline, boolean isCar) {
        this.idCar = idCar;
        this.carPic = carPic;
        this.name = name;
        this.model = model;
        this.year = year;
        this.odometer = odometer;
        this.plate = plate;
        this.gasoline = gasoline;
        this.isCar = isCar;
    }

    @Ignore
    protected Vehicle(Parcel in) {
        idCar = in.readInt();
        carPic = in.readString();
        name = in.readString();
        model = in.readString();
        year = in.readInt();
        odometer = in.readLong();
        plate = in.readString();
        gasoline = in.readString();
        isCar = in.readByte() != 0;
    }

    @Ignore
    public static final Creator<Vehicle> CREATOR = new Creator<Vehicle>() {
        @Override
        public Vehicle createFromParcel(Parcel in) {
            return new Vehicle(in);
        }

        @Override
        public Vehicle[] newArray(int size) {
            return new Vehicle[size];
        }
    };

    public int getIdCar() {
        return idCar;
    }

    public String getCarPic() {
        return carPic;
    }

    public String getName() {
        return name;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public long getOdometer() {
        return odometer;
    }

    public String getPlate() {
        return plate;
    }

    public String getGasoline() {
        return gasoline;
    }

    public boolean isCar() {
        return isCar;
    }

    public void setIdCar(int idCar) {
        this.idCar = idCar;
    }

    public void setCarPic(String carPic) {
        this.carPic = carPic;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setOdometer(long odometer) {
        this.odometer = odometer;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public void setGasoline(String gasoline) {
        this.gasoline = gasoline;
    }

    public void setCar(boolean car) {
        isCar = car;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeInt(idCar);
        parcel.writeString(carPic);
        parcel.writeString(name);
        parcel.writeString(model);
        parcel.writeInt(year);
        parcel.writeLong(odometer);
        parcel.writeString(plate);
        parcel.writeString(gasoline);
        parcel.writeByte((byte) (isCar ? 1 : 0));
    }
}
