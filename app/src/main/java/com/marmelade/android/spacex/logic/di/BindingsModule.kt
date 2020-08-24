package com.marmelade.android.spacex.logic.di

import androidx.lifecycle.ViewModel
import com.marmelade.android.spacex.ui.company.CompanyViewModel
import com.marmelade.android.spacex.ui.rockets.RocketsViewModel
import com.marmelade.android.spacex.ui.detail.DetailViewModel
import com.marmelade.android.spacex.ui.main.MainActivityViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class MainModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun bindsMainActivityViewModel(viewModel: MainActivityViewModel): ViewModel
}

@Module
abstract class DetailModule {
    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel::class)
    abstract fun bindsDetailViewModel(viewModel: DetailViewModel): ViewModel
}

@Module
abstract class RocketsModule {
    @Binds
    @IntoMap
    @ViewModelKey(RocketsViewModel::class)
    abstract fun bindsRocketsViewModel(viewModel: RocketsViewModel): ViewModel
}

@Module
abstract class CompanyModule {
    @Binds
    @IntoMap
    @ViewModelKey(CompanyViewModel::class)
    abstract fun bindsCompanyViewModel(viewModel: CompanyViewModel): ViewModel
}