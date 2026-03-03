package com.example.myapp

import android.app.Application
import com.example.myapp.di.appModule
import com.example.myapp.di.sharedModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MyApplication)
            modules(
                appModule,    // Android固有（Settings等）
                sharedModule  // 共通（Repository・ApiClient等）
            )
        }
    }
}
