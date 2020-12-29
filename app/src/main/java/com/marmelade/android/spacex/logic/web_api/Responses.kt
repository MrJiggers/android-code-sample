package com.marmelade.android.spacex.logic.web_api

import android.content.Context
import com.marmelade.android.spacex.R
import com.marmelade.android.spacex.data.entities.ErrorIdentification


/**
 * @author Petr Tykal <tykal.pete@gmail.com>
 */
abstract class BaseResponse(val status: Int, val error: String?) {
    companion object {
        const val CODE_SUCCESS = 200
        const val CODE_SUCCESS_CREATE = 201

        const val CODE_UNAUTHORIZED = 401
        const val CODE_NOT_FOUND = 404
        const val CODE_INTERNAL_SERVER_ERROR = 500
    }

    open fun isSuccessful(): Boolean = status == CODE_SUCCESS || status == CODE_SUCCESS_CREATE

    fun mapToError(appContext: Context): ErrorIdentification = when {
        isSuccessful() -> ErrorIdentification.None()
        status == CODE_UNAUTHORIZED -> ErrorIdentification.Authentication(appContext.getString(R.string.common_error_unauthorized_access))
        status == CODE_NOT_FOUND -> ErrorIdentification.NotFound(appContext.getString(R.string.common_error_not_found))
        status == CODE_INTERNAL_SERVER_ERROR -> ErrorIdentification.InternalServerError(appContext.getString(R.string.common_error_server_error))
        else -> ErrorIdentification.Unknown(appContext.getString(R.string.common_error_unknown_error))
    }
}

class ErrorResponse(status: Int, error: String?) : BaseResponse(status, error) {
    override fun isSuccessful(): Boolean = false
}