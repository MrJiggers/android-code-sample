package com.marmelade.android.spacex.ui.company

import androidx.lifecycle.ViewModel
import com.marmelade.android.spacex.data.database.SpaceXDatabase
import com.marmelade.android.spacex.logic.repository.SpaceXRepository
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject


/**
 * @author Petr Tykal <tykal.pete@gmail.com>
 */
class CompanyViewModel @Inject constructor(
        private val spaceXRepository: SpaceXRepository,
        spaceXDatabase: SpaceXDatabase
) : ViewModel() {

    private val disposables: CompositeDisposable = CompositeDisposable()
    val company = spaceXDatabase.companyDao().getCompany()


    init {
        getCompany()
    }

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }

    private fun getCompany() {
        disposables.add(
                spaceXRepository.getCompanyInfo()
                        .subscribe({
                            Timber.d(it.data.toString())
                        }, {
                            Timber.e(it)
                        })
        )
    }
}