package com.marmelade.android.spacex.logic.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.marmelade.android.spacex.ui.base.BaseRepository
import com.marmelade.android.spacex.data.database.SpaceXDatabase
import com.marmelade.android.spacex.data.entities.Resource
import com.marmelade.android.spacex.data.entities.RocketFilter
import com.marmelade.android.spacex.data.entities.company.Company
import com.marmelade.android.spacex.data.entities.rocket.Rocket
import com.marmelade.android.spacex.logic.util.combineLatestLiveData
import com.marmelade.android.spacex.logic.web_api.WebApi
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton


/**
 * @author Petr Tykal <tykal.pete@gmail.com>
 */
@Singleton
class SpaceXRepository @Inject internal constructor(
        appContext: Context,
        private val webApi: WebApi,
        private val spaceXDatabase: SpaceXDatabase,
        retrofit: Retrofit)
    : BaseRepository(appContext, retrofit) {

    val filter: MutableLiveData<RocketFilter> = MutableLiveData<RocketFilter>().apply {
        postValue(RocketFilter())
    }
    val rockets: LiveData<List<Rocket>> = Transformations.switchMap(
            combineLatestLiveData(spaceXDatabase.rocketsDao().getAllRockets(), filter)) {
        if (it.second.enabledFilter) {
            spaceXDatabase.rocketsDao().getAllRocketsFiltered(
                    it.second.active, it.second.successRatio)
        } else {
            spaceXDatabase.rocketsDao().getAllRockets()
        }
    }


    fun getCompanyInfo(): Single<Resource<Company>> {
        return webApi.getCompanyInfo()
                .asOperation()
                .subscribeOn(Schedulers.io())
                .doOnSuccess {
                    it.data?.let {
                        spaceXDatabase.companyDao().insert(it)
                    }
                }
    }

    fun getListOfRockets(): Single<Resource<List<Rocket>>> {
        return webApi.getRockets()
                .asOperation()
                .subscribeOn(Schedulers.io())
                .doOnSuccess {
                    it.data?.let {
                        spaceXDatabase.rocketsDao().insertAll(it)
                    }
                }
    }

    fun getRocketById(id: String): Single<Resource<Rocket>> {
        return webApi.getRocketById(id)
                .asOperation()
                .subscribeOn(Schedulers.io())
                .doOnSuccess {
                    it.data?.let {
                        spaceXDatabase.rocketsDao().insert(it)
                    }
                }
    }
}