package com.example.lab6_2

import android.app.Application
import android.graphics.Bitmap
import android.util.Log
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MyApplication : Application() {
    val executor: ExecutorService = Executors.newFixedThreadPool(1)
    var imgBitmap: Bitmap? = null

    override fun onCreate() {
        super.onCreate()
        Log.d("Debug", "Application created")
    }
}