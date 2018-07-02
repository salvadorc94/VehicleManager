package com.example.maris.vehiclemanager.Model.Database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Database Vehicle entity
 */

@Entity
public class Vehicle {
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
}
