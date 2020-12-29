package com.marmelade.android.spacex.logic.web_api

import com.marmelade.android.spacex.data.entities.company.Company
import com.marmelade.android.spacex.data.entities.rocket.Rocket
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


/**
 * @author Petr Tykal <tykal.pete@gmail.com>
 */
interface WebApi {

    @GET("company")
    fun getCompanyInfo(): Single<Response<Company>>

    @GET("rockets")
    fun getRockets(): Single<Response<List<Rocket>>>

    @GET("rockets/{id}")
    fun getRocketById(@Path("id") id: String): Single<Response<Rocket>>
}