package com.example.maris.vehiclemanager.Model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.example.maris.vehiclemanager.Model.Database.Category;
import com.example.maris.vehiclemanager.Model.Database.Expense;
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
                .observeOn(AndroidSchedulers.mainThread());
    }
    
    public Completable insertOrUpdateExpenses(Expense... expenses) {
        return mRepository.insertOrUpdateExpenses(expenses)
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Completable insertOrUpdateExpenses(List<Expense> expenses) {
        return mRepository.insertOrUpdateExpenses(expenses)
                .observeOn(AndroidSchedulers.mainThread());
    }
    
    public Completable deleteExpenses(Expense... expenses) {
        return mRepository.deleteExpenses(expenses)
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Completable deleteExpenses(List<Expense> expenses) {
        return mRepository.deleteExpenses(expenses)
                .observeOn(AndroidSchedulers.mainThread());
    }
    
    public Single<Expense> getExpense(int idExpense) {
        return mRepository.getExpense(idExpense)
                .observeOn(AndroidSchedulers.mainThread());
    }
    
    

//    --------------Vehicles--------------
    public Flowable<List<Vehicle>> getAllVehicles() {
        return this.mRepository.getVehicles()
                .observeOn(AndroidSchedulers.mainThread());
    }
    
    public Completable insertOrUpdateVehicles(Vehicle... vehicles) {
        return mRepository.insertOrUpdateVehicles(vehicles)
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Completable insertOrUpdateVehicles(List<Vehicle> vehicles) {
        return mRepository.insertOrUpdateVehicles(vehicles)
                .observeOn(AndroidSchedulers.mainThread());
    }
    
    public Completable deleteVehicles(Vehicle... vehicles) {
        return mRepository.deleteVehicles(vehicles)
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Completable deleteVehicles(List<Vehicle> vehicles) {
        return mRepository.deleteVehicles(vehicles)
                .observeOn(AndroidSchedulers.mainThread());
    }
    
    public Single<Vehicle> getVehicle(int idVehicle) {
        return mRepository.getVehicle(idVehicle)
                .observeOn(AndroidSchedulers.mainThread());
    }
    
    
    
//    --------------Categories--------------
    public Flowable<List<Category>> getAllCategories() {
        return this.mRepository.getCategories()
                .observeOn(AndroidSchedulers.mainThread());
    }
    
    public Completable insertOrUpdateCategories(Category... categories) {
        return mRepository.insertOrUpdateCategories(categories)
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Completable insertOrUpdateCategories(List<Category> categories) {
        return mRepository.insertOrUpdateCategories(categories)
                .observeOn(AndroidSchedulers.mainThread());
    }
    
    public Completable deleteCategories(Category... categories) {
        return mRepository.deleteCategories(categories)
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Completable deleteCategories(List<Category> categories) {
        return mRepository.deleteCategories(categories)
                .observeOn(AndroidSchedulers.mainThread());
    }
    
    public Single<Category> getCategory(int idCategory) {
        return mRepository.getCategory(idCategory)
                .observeOn(AndroidSchedulers.mainThread());
    }
}
