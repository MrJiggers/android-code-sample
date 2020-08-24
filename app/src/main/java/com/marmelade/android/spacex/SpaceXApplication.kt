package com.marmelade.android.spacex

import android.app.Application
import com.marmelade.android.spacex.logic.di.AppComponent
import com.marmelade.android.spacex.logic.di.AppModule
import com.marmelade.android.spacex.logic.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import timber.log.Timber
import javax.inject.Inject


open class SpaceXApplication : Application(), HasAndroidInjector {

    @Inject lateinit var androidInjector: DispatchingAndroidInjector<Any>
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(object : Timber.DebugTree() {
                override fun createStackElementTag(element: StackTraceElement): String {
                    return super.createStackElementTag(element) + ":" + element.lineNumber
                }
            })
        }

        appComponent = DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .application(this)
                .build()

        appComponent.inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector
}