package com.marmelade.android.spacex.logic.di


import com.marmelade.android.spacex.ui.company.CompanyFragment
import com.marmelade.android.spacex.ui.rockets.RocketsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


/**
 * @author Petr Tykal <tykal.pete@gmail.com>
 */
@Module
abstract class FragmentBuilderModule {

    @ContributesAndroidInjector(modules = [RocketsModule::class])
    abstract fun contributeRocketsFragment(): RocketsFragment

    @ContributesAndroidInjector(modules = [CompanyModule::class])
    abstract fun contributeCompanyFragment(): CompanyFragment
}