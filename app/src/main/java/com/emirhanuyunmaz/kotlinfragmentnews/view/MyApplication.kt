package com.emirhanuyunmaz.kotlinfragmentnews.view

import android.app.Application
import com.emirhanuyunmaz.kotlinfragmentnews.view.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class MyApplication:Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(appModule)
        }
    }
}