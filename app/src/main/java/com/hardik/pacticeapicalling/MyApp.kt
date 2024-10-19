package com.hardik.pacticeapicalling

import android.app.Application
import com.hardik.pacticeapicalling.di.AppModule
import com.hardik.pacticeapicalling.di.AppModuleImpl

class MyApp: Application() {
    companion object {
        lateinit var appModule : AppModule
    }

    override fun onCreate() {
        super.onCreate()
        appModule = AppModuleImpl(this)
    }
}