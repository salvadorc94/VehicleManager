package com.example.maris.vehiclemanager.Model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.example.maris.vehiclemanager.Model.Database.Category;
import com.example.maris.vehiclemanager.Model.Database.Expense;
import com.example.maris.vehiclemanager.Model.Database.Notification;
import com.example.maris.vehiclemanager.Model.Database.Vehicle;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Global ViewModel
 */

public class AppViewModel extends AndroidViewModel{
    private AppRepository mRepository;

    public AppViewModel(@NonNull Application application) {
        super(application);

        mRepository = AppRepository.getInstance(application);
    }



//    --------------Expenses--------------
    public Flowable<List<Expense>> getAllExpenses() {
        return this.mRepository.getExpenses()
                .subscribeOn(AndroidSchedulers.mainThread());
    }
    
    public Completable insertOrUpdateExpenses(Expense... expenses) {
        return mRepository.insertOrUpdateExpenses(expenses)
                .subscribeOn(AndroidSchedulers.mainThread());
    }
    public Completable insertOrUpdateExpenses(List<Expense> expenses) {
        return mRepository.insertOrUpdateExpenses(expenses)
                .subscribeOn(AndroidSchedulers.mainThread());
    }
    
    public Completable deleteExpenses(Expense... expenses) {
        return mRepository.deleteExpenses(expenses)
                .subscribeOn(AndroidSchedulers.mainThread());
    }
    public Completable deleteExpenses(List<Expense> expenses) {
        return mRepository.deleteExpenses(expenses)
                .subscribeOn(AndroidSchedulers.mainThread());
    }
    
    public Single<Expense> getExpense(int idExpense) {
        return mRepository.getExpense(idExpense)
                .subscribeOn(AndroidSchedulers.mainThread());
    }
    
    

//    --------------Vehicles--------------
    public Flowable<List<Vehicle>> getAllVehicles() {
        return this.mRepository.getVehicles()
                .subscribeOn(AndroidSchedulers.mainThread());
    }
    
    public Completable insertOrUpdateVehicles(Vehicle... vehicles) {
        return mRepository.insertOrUpdateVehicles(vehicles)
                .subscribeOn(AndroidSchedulers.mainThread());
    }
    public Completable insertOrUpdateVehicles(List<Vehicle> vehicles) {
        return mRepository.insertOrUpdateVehicles(vehicles)
                .subscribeOn(AndroidSchedulers.mainThread());
    }
    
    public Completable deleteVehicles(Vehicle... vehicles) {
        return mRepository.deleteVehicles(vehicles)
                .subscribeOn(AndroidSchedulers.mainThread());
    }
    public Completable deleteVehicles(List<Vehicle> vehicles) {
        return mRepository.deleteVehicles(vehicles)
                .subscribeOn(AndroidSchedulers.mainThread());
    }
    
    public Single<Vehicle> getVehicle(int idVehicle) {
        return mRepository.getVehicle(idVehicle)
                .subscribeOn(AndroidSchedulers.mainThread());
    }
    
    
    
//    --------------Categories--------------
    public Flowable<List<Category>> getAllCategories() {
        return this.mRepository.getCategories()
                .subscribeOn(AndroidSchedulers.mainThread());
    }
    
    public Completable insertOrUpdateCategories(Category... categories) {
        return mRepository.insertOrUpdateCategories(categories)
                .subscribeOn(AndroidSchedulers.mainThread());
    }
    public Completable insertOrUpdateCategories(List<Category> categories) {
        return mRepository.insertOrUpdateCategories(categories)
                .subscribeOn(AndroidSchedulers.mainThread());
    }
    
    public Completable deleteCategories(Category... categories) {
        return mRepository.deleteCategories(categories)
                .subscribeOn(AndroidSchedulers.mainThread());
    }
    public Completable deleteCategories(List<Category> categories) {
        return mRepository.deleteCategories(categories)
                .subscribeOn(AndroidSchedulers.mainThread());
    }
    
    public Single<Category> getCategory(int idCategory) {
        return mRepository.getCategory(idCategory)
                .subscribeOn(AndroidSchedulers.mainThread());
    }
    
    
    
//    --------------Notifications--------------
    public Flowable<List<Notification>> getAllNotifications() {
        return this.mRepository.getNotifications()
                .subscribeOn(AndroidSchedulers.mainThread());
    }
    
    public Completable insertOrUpdateNotifications(Notification... notifications) {
        return mRepository.insertOrUpdateNotifications(notifications)
                .subscribeOn(AndroidSchedulers.mainThread());
    }
    public Completable insertOrUpdateNotifications(List<Notification> notifications) {
        return mRepository.insertOrUpdateNotifications(notifications)
                .subscribeOn(AndroidSchedulers.mainThread());
    }
    
    public Completable deleteNotifications(Notification... notifications) {
        return mRepository.deleteNotifications(notifications)
                .subscribeOn(AndroidSchedulers.mainThread());
    }
    public Completable deleteNotifications(List<Notification> notifications) {
        return mRepository.deleteNotifications(notifications)
                .subscribeOn(AndroidSchedulers.mainThread());
    }

    public Single<Notification> getNotification(int idNotification) {
        return mRepository.getNotification(idNotification)
                .subscribeOn(AndroidSchedulers.mainThread());
    }
}
