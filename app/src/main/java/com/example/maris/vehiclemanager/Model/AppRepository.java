package com.example.maris.vehiclemanager.Model;

import android.app.Application;

import com.example.maris.vehiclemanager.Database.AppDAO;
import com.example.maris.vehiclemanager.Database.AppRoomDatabase;

/**
 * Global repository
 */

public class AppRepository {
    private static AppRepository INSTANCE;

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
}
