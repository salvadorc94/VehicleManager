package com.example.maris.vehiclemanager.Database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;

import com.example.maris.vehiclemanager.Model.Database.Category;
import com.example.maris.vehiclemanager.Model.Database.Expense;
import com.example.maris.vehiclemanager.Model.Database.Vehicle;

import java.util.Date;

/**
 * Global Database
 */

@Database(entities = {Category.class, Vehicle.class, Expense.class}, version = 1)
public abstract class AppRoomDatabase extends RoomDatabase{

    private static AppRoomDatabase INSTANCE;
    private static String DATABASE_NAME = "vehicle_manager_database";

    public static AppRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppRoomDatabase.class, DATABASE_NAME)
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(SupportSQLiteDatabase db) {
            super.onCreate(db);
            new populateDBAsync(INSTANCE).execute();
        }
    };

    private static class populateDBAsync extends AsyncTask<Void, Void, Void> {
        private final AppDAO mDao;

        populateDBAsync(AppRoomDatabase db){
            this.mDao = db.appDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            //Default data on database create
            mDao.insertOrUpdateCategories(
                    (new Category(1,"Fuel", "")),
                    (new Category(2,"Tires", "")),
                    (new Category(3,"Oil", ""))
            );


            mDao.insertOrUpdateVehicles(
                    new Vehicle(1, "","Mazda", "Mazda", "203", 2001, 10989744, "P0008989", "Super", true)
            );

            mDao.insertOrUpdateExpenses(
                    (new Expense(1,1, 1,"Fuel", 20, "Shell", "Super", new Date())),
                    (new Expense(2,2, 1,"Tires", 150, "Shell", "24\"", new Date())),
                    (new Expense(3,3, 1,"Oil", 17, "Shell", "Del reciclado", new Date()))
            );

            return null;
        }
    }

    public abstract AppDAO appDao();
}
