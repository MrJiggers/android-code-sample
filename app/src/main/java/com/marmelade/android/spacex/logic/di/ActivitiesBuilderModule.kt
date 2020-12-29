package com.marmelade.android.spacex.logic.di

import com.marmelade.android.spacex.ui.detail.DetailActivity
import com.marmelade.android.spacex.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


/**
 * @author Petr Tykal <tykal.pete@gmail.com>
 */
@Module
abstract class ActivitiesBuilderModule {

    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [DetailModule::class])
    abstract fun bindDetailActivity(): DetailActivity
}