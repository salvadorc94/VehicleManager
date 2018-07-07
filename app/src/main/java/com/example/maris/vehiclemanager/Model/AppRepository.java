package com.example.maris.vehiclemanager.Model;

import android.app.Application;

import com.example.maris.vehiclemanager.Database.AppDAO;
import com.example.maris.vehiclemanager.Database.AppRoomDatabase;
import com.example.maris.vehiclemanager.Model.Database.Category;
import com.example.maris.vehiclemanager.Model.Database.Expense;
import com.example.maris.vehiclemanager.Model.Database.Vehicle;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

/**
 * Global repository
 */

public class AppRepository {
    private static AppRepository INSTANCE;

    private Flowable<List<Expense>> expensesListFlowable;
    private Flowable<List<Category>> categoriesListFlowable;
    private Flowable<List<Vehicle>> vehiclesListFlowable;

    private AppDAO mDao;
    public static AppRepository getInstance(Application application) {
        if (INSTANCE == null) {
            INSTANCE = new AppRepository(application);
        }
        return INSTANCE;
    }

    private AppRepository(Application application) {
        AppRoomDatabase db = AppRoomDatabase.getDatabase(application);
        mDao = db.appDao();

    }


//---------Expenses---------
    public Flowable<List<Expense>> getExpenses() {
        if(this.expensesListFlowable == null)
            expensesListFlowable = mDao.getAllExpenses()
                    .subscribeOn(Schedulers.io());
        return expensesListFlowable;
    }

    public Completable insertOrUpdateExpenses(Expense... expenses) {
        return Completable.fromCallable(() -> {
                mDao.insertOrUpdateExpenses(expenses);
                return null;
        }).subscribeOn(Schedulers.io());
    }
    public Completable insertOrUpdateExpenses(List<Expense> expenses) {
        return Completable.fromCallable(() -> {
                mDao.insertOrUpdateExpenses(expenses);
                return null;
        }).subscribeOn(Schedulers.io());
    }
    
    public Completable deleteExpenses(Expense... expenses) {
        return Completable.fromCallable(() -> {
                mDao.deleteExpenses(expenses);
                return null;
        }).subscribeOn(Schedulers.io());
    }
    public Completable deleteExpenses(List<Expense> expenses) {
        return Completable.fromCallable(() -> {
                mDao.deleteExpenses(expenses);
                return null;
        }).subscribeOn(Schedulers.io());
    }
    
    public Single<Expense> getExpense(int idExpense) {
        return mDao.getExpense(idExpense)
                .subscribeOn(Schedulers.io());
    }
    


//---------Vehicles---------
    public Flowable<List<Vehicle>> getVehicles() {
        if(this.vehiclesListFlowable == null)
            vehiclesListFlowable = mDao.getAllVehicles()
                    .subscribeOn(Schedulers.io());
        return vehiclesListFlowable;
    }
    
    public Completable insertOrUpdateVehicles(Vehicle... vehicles) {
        return Completable.fromCallable(() -> {
                mDao.insertOrUpdateVehicles(vehicles);
                return null;
        }).subscribeOn(Schedulers.io());
    }
    public Completable insertOrUpdateVehicles(List<Vehicle> vehicles) {
        return Completable.fromCallable(() -> {
                mDao.insertOrUpdateVehicles(vehicles);
                return null;
        }).subscribeOn(Schedulers.io());
    }
    
    public Completable deleteVehicles(Vehicle... vehicles) {
        return Completable.fromCallable(() -> {
                mDao.deleteVehicles(vehicles);
                return null;
        }).subscribeOn(Schedulers.io());
    }
    public Completable deleteVehicles(List<Vehicle> vehicles) {
        return Completable.fromCallable(() -> {
                mDao.deleteVehicles(vehicles);
                return null;
        }).subscribeOn(Schedulers.io());
    }
    
    public Single<Vehicle> getVehicle(int idVehicle) {
        return mDao.getVehicle(idVehicle)
                .subscribeOn(Schedulers.io());
    }
    
    
    
//---------Categories---------
    public Flowable<List<Category>> getCategories() {
        if(this.categoriesListFlowable == null)
            categoriesListFlowable = mDao.getAllCategories()
                    .subscribeOn(Schedulers.io());
        return categoriesListFlowable;
    }
    
    public Completable insertOrUpdateCategories(Category... categories) {
        return Completable.fromCallable(() -> {
                mDao.insertOrUpdateCategories(categories);
                return null;
        }).subscribeOn(Schedulers.io());
    }
    public Completable insertOrUpdateCategories(List<Category> categories) {
        return Completable.fromCallable(() -> {
                mDao.insertOrUpdateCategories(categories);
                return null;
        }).subscribeOn(Schedulers.io());
    }
    
    public Completable deleteCategories(Category... categories) {
        return Completable.fromCallable(() -> {
                mDao.deleteCategories(categories);
                return null;
        }).subscribeOn(Schedulers.io());
    }
    public Completable deleteCategories(List<Category> categories) {
        return Completable.fromCallable(() -> {
                mDao.deleteCategories(categories);
                return null;
        }).subscribeOn(Schedulers.io());
    }
    
    public Single<Category> getCategory(int idCategory) {
        return mDao.getCategory(idCategory)
                .subscribeOn(Schedulers.io());
    }
}
