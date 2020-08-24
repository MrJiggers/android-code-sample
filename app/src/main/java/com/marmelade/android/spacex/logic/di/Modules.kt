package com.marmelade.android.spacex.logic.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.marmelade.android.spacex.BuildConfig
import com.marmelade.android.spacex.data.database.SpaceXDatabase
import com.marmelade.android.spacex.logic.repository.SpaceXRepository
import com.marmelade.android.spacex.logic.web_api.WebApi
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
class AppModule(private val application: Application) {

    @Provides
    @Singleton
    internal fun provideContext(): Context = application

    @Provides
    @Singleton
    internal fun provideSpaceXRepository(
        appContext: Context,
        webApi: WebApi,
        spaceXDatabase: SpaceXDatabase,
        retrofit: Retrofit
    ): SpaceXRepository = SpaceXRepository(appContext, webApi, spaceXDatabase, retrofit)

    @Provides
    @Singleton
    internal fun provideRoomDatabase(): SpaceXDatabase {
        return Room.databaseBuilder(
            application,
            SpaceXDatabase::class.java,
            "${BuildConfig.APPLICATION_ID}_database"
        ).build()
    }
}

@Module
class RestModule {
    companion object {
        private const val READ_TIMEOUT = 300L
        private const val HEADER_ACCEPT = "Accept"
        private const val HEADER_ACCEPT_VALUE = "application/json"
        private const val HEADER_CONTENT_TYPE = "Content-Type"
        private const val HEADER_CONTENT_TYPE_VALUE = "application/json"
    }

    @Provides
    internal fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(interceptor)
        }

        builder.addInterceptor { chain ->
            chain.proceed(
                chain.request()
                    .newBuilder()
                    .addHeader(HEADER_CONTENT_TYPE, HEADER_CONTENT_TYPE_VALUE)
                    .addHeader(HEADER_ACCEPT, HEADER_ACCEPT_VALUE)
                    .build()
            )
        }
        return builder.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    internal fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(okHttpClient)
            .build()
    }

    @Provides
    internal fun provideMoshi(): Moshi = Moshi.Builder().build()

    @Provides
    internal fun provideWebApi(retrofit: Retrofit): WebApi = retrofit.create(WebApi::class.java)
}