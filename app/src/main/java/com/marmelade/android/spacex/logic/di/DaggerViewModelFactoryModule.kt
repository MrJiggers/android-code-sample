package com.marmelade.android.spacex.logic.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module


/**
 * @author Petr Tykal <tykal.pete@gmail.com>
 */
@Module
abstract class DaggerViewModelFactoryModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory
}