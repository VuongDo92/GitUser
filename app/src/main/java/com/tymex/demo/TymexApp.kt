package com.tymex.demo

import android.app.Application
import com.tymex.core.data.di.coreDataModule
import com.tymex.core.database.di.databaseModule
import com.tymex.demo.di.appModule
import com.tymex.gituser.data.di.gitUserDataModule
import com.tymex.gituser.network.di.networkModule
import com.tymex.gituser.presentation.di.tymexGitUserPresentationModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class TymexApp: Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidLogger()
            androidContext(this@TymexApp)
            modules(
                tymexGitUserPresentationModule,
                appModule,
                databaseModule,
                networkModule,
                coreDataModule,
                gitUserDataModule
            )
        }
    }
}