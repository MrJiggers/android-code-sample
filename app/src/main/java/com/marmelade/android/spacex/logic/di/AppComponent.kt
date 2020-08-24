package com.marmelade.android.spacex.logic.di

import android.app.Application
import com.marmelade.android.spacex.SpaceXApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton


@Singleton
@Component(
        modules = [
            AndroidInjectionModule::class,
            ActivitiesBuilderModule::class,
            FragmentBuilderModule::class,
            AppModule::class,
            DaggerViewModelFactoryModule::class,
            RestModule::class,
            MainModule::class,
            DetailModule::class,
            RocketsModule::class,
            CompanyModule::class
        ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun appModule(appModule: AppModule): Builder
        fun build(): AppComponent
    }

    fun inject(app: SpaceXApplication)
}