package com.example.viewpager2_thumbindicator.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.viewpager2_thumbindicator.databinding.ActivityTopBinding;

public class TopViewModel extends AndroidViewModel {

    private static final String TAG = new Object(){}.getClass().getEnclosingClass().getName();


    public ActivityTopBinding binding;
    private Application _application;


    public TopViewModel(Application application) {

        super(application);

        _application = application;


    }


}
