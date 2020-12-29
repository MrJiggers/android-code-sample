package com.marmelade.android.spacex.ui.base

import android.content.Context
import com.marmelade.android.spacex.R
import com.marmelade.android.spacex.data.entities.ErrorIdentification
import com.marmelade.android.spacex.data.entities.Resource
import com.marmelade.android.spacex.logic.web_api.BaseResponse.Companion.CODE_UNAUTHORIZED
import com.marmelade.android.spacex.logic.web_api.ErrorResponse
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import timber.log.Timber
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException


/**
 * Base parent for repositories
 *
 * @author Petr Tykal <tykal.pete@gmail.com>
 */
abstract class BaseRepository(
        private val appContext: Context,
        private val retrofit: Retrofit
) {
    companion object {
        private const val SYNC_TIMEOUT_SECONDS = 5L
    }

    protected fun <U, T : Response<U>> Single<T>.asOperation(): Single<Resource<U>> {
        return this.timeout(SYNC_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .map {
                    if (it.isSuccessful) Resource.success(it.body()!!)
                    else parseFailedResponse(it)
                }
                .onErrorReturn {
                    Timber.e(it)
                    parseErrorReturn(it)
                }
                .doOnError {
                    Timber.e(it)
                }
                .subscribeOn(Schedulers.io())
                .doOnDispose {
                    Timber.d("Call disposed")
                }
    }

    private fun <T> parseFailedResponse(response: Response<T>): Resource<T> =
            try {
                val error = ErrorResponse(response.code(), response.message())
                Resource.error(error.mapToError(appContext), null)
            } catch (e: Exception) {
                try {
                    parseErrorBody(response.errorBody()!!)
                } catch (e: Exception) {
                    Resource.error(
                            ErrorIdentification.Unknown(
                                    appContext.getString(R.string.common_error_unknown_error)
                            ), null
                    )
                }
            }

    private fun <T> parseErrorBody(errorBody: ResponseBody): Resource<T> {
        val response = retrofit.responseBodyConverter<Any>(
                ErrorResponse::class.java, ErrorResponse::class.java.annotations
        ).convert(errorBody) as ErrorResponse
        return Resource.error(response.mapToError(appContext), null)
    }

    private fun <T> parseErrorReturn(throwable: Throwable): Resource<T> {
        when (throwable) {
            is UnknownHostException -> {
                return Resource.error(
                        ErrorIdentification.Connection(
                                appContext.getString(R.string.common_error_connection_error)
                        ), null
                )
            }
            is HttpException -> {
                if (throwable.code() == CODE_UNAUTHORIZED) {
                    return Resource.error(
                            ErrorIdentification.Authentication(
                                    appContext.getString(R.string.common_error_unauthorized_access)
                            ), null
                    )
                }
                val errorBody = throwable.response()?.errorBody()
                if (errorBody != null) {
                    return parseErrorBody(errorBody)
                }
            }
            is TimeoutException -> return Resource.error(
                    ErrorIdentification.Timeout(appContext.getString(R.string.common_error_timeout)),
                    null
            )
        }
        return Resource.error(
                ErrorIdentification.Unknown(appContext.getString(R.string.common_error_unknown_error)),
                null
        )
    }
}