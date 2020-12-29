package com.marmelade.android.spacex.ui.rockets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.marmelade.android.spacex.data.entities.ErrorIdentification
import com.marmelade.android.spacex.data.entities.Resource
import com.marmelade.android.spacex.data.entities.RocketFilter
import com.marmelade.android.spacex.data.entities.rocket.Rocket
import com.marmelade.android.spacex.logic.repository.SpaceXRepository
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject


/**
 * @author Petr Tykal <tykal.pete@gmail.com>
 */
class RocketsViewModel @Inject constructor(
        private val spaceXRepository: SpaceXRepository
) : ViewModel() {

    private val disposables: CompositeDisposable = CompositeDisposable()

    val filter: MutableLiveData<RocketFilter> = spaceXRepository.filter
    val rockets: LiveData<List<Rocket>> = spaceXRepository.rockets
    val refreshState = MutableLiveData<Resource<List<Rocket>>>()


    init {
        getListOfRockets()
    }

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }

    fun getListOfRockets() {
        disposables.add(
                spaceXRepository.getListOfRockets()
                        .subscribe({
                            refreshState.postValue(it)
                        }, {
                            refreshState.postValue(Resource.error(ErrorIdentification.Unknown()))
                            Timber.e(it)
                        })
        )
    }
}