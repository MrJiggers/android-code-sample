package com.marmelade.android.spacex.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.marmelade.android.spacex.data.database.SpaceXDatabase
import com.marmelade.android.spacex.data.entities.ErrorIdentification
import com.marmelade.android.spacex.data.entities.Resource
import com.marmelade.android.spacex.data.entities.rocket.Rocket
import com.marmelade.android.spacex.logic.repository.SpaceXRepository
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject


/**
 * @author Petr Tykal <tykal.pete@gmail.com>
 */
class DetailViewModel @Inject constructor(
        private val spaceXRepository: SpaceXRepository,
        private val spaceXDatabase: SpaceXDatabase
) : ViewModel() {

    private val disposables: CompositeDisposable = CompositeDisposable()
    val refreshState = MutableLiveData<Resource<Rocket>>()


    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }

    fun rocketById(rocketId: String) = spaceXDatabase.rocketsDao().getRocketById(rocketId)

    fun getRocketById(rocketId: String) {
        disposables.add(
                spaceXRepository.getRocketById(rocketId)
                        .subscribe({
                            refreshState.postValue(it)
                        }, {
                            refreshState.postValue(Resource.error(ErrorIdentification.Unknown()))
                            Timber.e(it)
                        })
        )
    }
}