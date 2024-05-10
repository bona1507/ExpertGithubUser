package com.bona.expertgithubuser

import android.app.Application
import com.bona.core.di.databaseModule
import com.bona.core.di.networkModule
import com.bona.core.di.repositoryModule
import com.bona.expertgithubuser.di.useCaseModule
import com.bona.expertgithubuser.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                databaseModule,
                networkModule,
                repositoryModule,
                useCaseModule,
                viewModelModule
            )
        }
    }
}