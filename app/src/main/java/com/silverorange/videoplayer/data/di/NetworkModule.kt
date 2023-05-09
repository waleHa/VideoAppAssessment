package com.silverorange.videoplayer.data.di

import com.silverorange.videoplayer.BuildConfig
import com.silverorange.videoplayer.data.datasource.VideoRemoteDataSource
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

object NetworkModule {

    @Provides
    @Singleton
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providePatientsRemoteDataSource(retrofit: Retrofit): VideoRemoteDataSource =
        retrofit.create(VideoRemoteDataSource::class.java)

}