package com.example.tiktokandroid

import android.app.Application
import com.google.firebase.FirebaseApp

class TikTokAndroidApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Initialize Firebase here to make it available everywhere
        FirebaseApp.initializeApp(this)
    }
}