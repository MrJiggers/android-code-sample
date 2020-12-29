package com.marmelade.android.spacex.ui.main

import androidx.lifecycle.ViewModel
import com.marmelade.android.spacex.logic.repository.SpaceXRepository
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


/**
 * @author Petr Tykal <tykal.pete@gmail.com>
 */
class MainActivityViewModel @Inject constructor(private val spaceXRepository: SpaceXRepository) : ViewModel() {

    private val disposables: CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }

    fun getFilterState() = spaceXRepository.filter
}