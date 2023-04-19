package com.example.sosapplication

import android.app.Application
import com.example.sosapplication.database.AppDatabase

class ApplicationClass:Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}