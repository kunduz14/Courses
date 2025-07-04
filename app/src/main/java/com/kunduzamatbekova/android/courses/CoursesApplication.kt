package com.kunduzamatbekova.android.courses

import android.app.Application
import com.kunduzamatbekova.android.courses.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class CoursesApplication : Application() {
    
    override fun onCreate() {
        super.onCreate()
        
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@CoursesApplication)
            modules(appModule)
        }
    }
} 