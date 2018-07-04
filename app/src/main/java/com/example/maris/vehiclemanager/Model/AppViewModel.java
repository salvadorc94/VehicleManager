package com.example.maris.vehiclemanager.Model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

/**
 * Global ViewModel
 */

public class AppViewModel extends AndroidViewModel{
    private AppRepository mRespository;

    public AppViewModel(@NonNull Application application) {
        super(application);

        mRespository = AppRepository.getInstance(application);
    }
}
