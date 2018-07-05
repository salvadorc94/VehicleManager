package com.example.maris.vehiclemanager.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.maris.vehiclemanager.Model.Database.Category;
import com.example.maris.vehiclemanager.Model.Database.Expense;
import com.example.maris.vehiclemanager.Model.Database.Notification;
import com.example.maris.vehiclemanager.Model.Database.Vehicle;

import java.util.List;
import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * Global database DAO.
 */

@Dao
public interface AppDAO  {
    //Expenses
    @Query("SELECT * FROM expense")
    Flowable<List<Expense>> getAllExpenses();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrUpdateExpenses(Expense... expenses);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrUpdateExpenses(List<Expense> expenses);

    @Delete
    void deleteExpenses(Expense... expenses);

    @Delete
    void deleteExpenses(List<Expense> expenses);

    @Query("SELECT * FROM expense WHERE idExp = :idExpense")
    Single<Expense> getExpense(int idExpense);


    //Vehicles
    @Query("SELECT * FROM vehicle")
    Flowable<List<Vehicle>> getAllVehicles();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrUpdateVehicles(Vehicle... vehicles);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrUpdateVehicles(List<Vehicle> vehicles);

    @Delete
    void deleteVehicles(Vehicle... vehicles);

    @Delete
    void deleteVehicles(List<Vehicle> vehicles);

    @Query("SELECT * FROM vehicle WHERE idCar = :idVehicle")
    Single<Vehicle> getVehicle(int idVehicle);


    //Categories
    @Query("SELECT * FROM category")
    Flowable<List<Category>> getAllCategories();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrUpdateCategories(Category... categories);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrUpdateCategories(List<Category> categories);

    @Delete
    void deleteCategories(Category... categories);

    @Delete
    void deleteCategories(List<Category> categories);

    @Query("SELECT * FROM category WHERE idCat = :idCategory")
    Single<Category> getCategory(int idCategory);


    //Notifications
    @Query("SELECT * FROM notification")
    Flowable<List<Notification>> getAllNotifications();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrUpdateNotifications(Notification... notifications);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrUpdateNotifications(List<Notification> notifications);

    @Delete
    void deleteNotifications(Notification... notifications);

    @Delete
    void deleteNotifications(List<Notification> notifications);

    @Query("SELECT * FROM notification WHERE idNot = :idNotification")
    Single<Notification> getNotification(int idNotification);


}
