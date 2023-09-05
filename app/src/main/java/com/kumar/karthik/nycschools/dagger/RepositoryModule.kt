package com.kumar.karthik.nycschools.dagger

import android.app.Application
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Retofit bindings
 */
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    internal fun provideOkhttpCache(application: Application): Cache =
        Cache(File(application.cacheDir, "http-cache"),  50L * 1024L * 1024L)

    @Provides
    internal fun provideOkhttpClient(cache: Cache): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor())
            .cache(cache)
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .build()

    @Provides
    internal fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .baseUrl("https://data.cityofnewyork.us/resource/")
            .client(okHttpClient)
            .build()
}