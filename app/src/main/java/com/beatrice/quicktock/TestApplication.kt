package com.beatrice.quicktock

import android.app.Application
import com.beatrice.quicktock.di.appModule
import com.beatrice.quicktock.di.test.testDataModule
import org.koin.core.context.startKoin

class TestApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(appModule + testDataModule)
        }
    }
}
