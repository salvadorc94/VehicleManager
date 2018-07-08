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
import com.example.maris.vehiclemanager.R;

import java.util.ArrayList;

/**
 * Global Database
 */

@Database(entities = {Category.class, Vehicle.class, Expense.class}, version = 1)
public abstract class AppRoomDatabase extends RoomDatabase{

    private static AppRoomDatabase INSTANCE;
    private static String DATABASE_NAME = "vehicle_manager_database";
    private static String[] defaultCategories;

    public static AppRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppRoomDatabase.class) {
                if (INSTANCE == null) {
                    defaultCategories = context.getResources().getStringArray(R.array.default_category_list);

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

            ArrayList<Category> defaults = new ArrayList<>();
            for (String defaultCategory : defaultCategories) {
                defaults.add(new Category(0,defaultCategory, ""));
            }
            //Default data on database create
            mDao.insertOrUpdateCategories(defaults);

            /*mDao.insertOrUpdateCategories(
                    (new Category(1,"Fuel", "")),
                    (new Category(2,"Service", "")),
                    (new Category(3,"Miscellaneous", "")),
                    (new Category(4,"Oil", "")),
                    (new Category(5,"Insurance", "")),
                    (new Category(6,"Tires", "")),
                    (new Category(7,"Wheel balancing", "")),
                    (new Category(8,"Aligning", "")),
                    (new Category(9,"Engine", "")),
                    (new Category(10,"Legal", "")),
                    (new Category(11,"Maintenance and Repairs", ""))
            );*/


            return null;
        }
    }

    public abstract AppDAO appDao();
}
