package com.example.maris.vehiclemanager.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.maris.vehiclemanager.Model.Database.Category;
import com.example.maris.vehiclemanager.Model.Database.Expense;
import com.example.maris.vehiclemanager.Model.Database.Notification;
import com.example.maris.vehiclemanager.Model.Database.Vehicle;

/**
 * Global Database
 */

@Database(entities = {Category.class, Vehicle.class, Expense.class, Notification.class}, version = 1)
public abstract class AppRoomDatabase extends RoomDatabase{

    private static AppRoomDatabase INSTANCE;
    private static String DATABASE_NAME = "vehicle_manager_database";

    public static AppRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppRoomDatabase.class, DATABASE_NAME)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract AppDAO appDao();
}
