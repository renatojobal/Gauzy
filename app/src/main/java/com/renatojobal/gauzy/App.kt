package com.renatojobal.gauzy

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.viewbinding.BuildConfig
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.renatojobal.gauzy.repository.SharedPrefHelper
import com.renatojobal.gauzy.timber.DebugTree
import com.renatojobal.gauzy.timber.ReleaseTree
import timber.log.Timber


class App : Application(){



    /**
     * Main method that we use to set up important things
     */
    override fun onCreate() {
        super.onCreate()


        // Set up timber
        setUpTimber()

        // Set up shared preferences helper
//        setUpSharedPreferences()


    }


    /**
     * Timber is a library to log in a better way
     */
    private fun setUpTimber() {

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
            // Set a key to an int.
            FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(false)
            Timber.i("Timber set up in DEBUG level")
        } else {
            Timber.plant(ReleaseTree())
            // Set a key to an int.
            FirebaseCrashlytics.getInstance().setCustomKey("Build config", "RELEASE")
            Timber.i("Timber set up in RELEASE level")
        }
    }


    /**
     * Initialize the singleton object to get the shared preferences
     */
    private fun setUpSharedPreferences() {
        SharedPrefHelper.sharedPref =
            this.getSharedPreferences(GeneralConstants.SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE)
    }
}