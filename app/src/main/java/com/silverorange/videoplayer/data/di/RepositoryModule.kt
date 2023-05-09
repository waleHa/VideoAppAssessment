package com.silverorange.videoplayer.data.di

import com.silverorange.videoplayer.data.datasource.VideoRemoteDataSource
import com.silverorange.videoplayer.data.repository.VideoRepositoryImpl
import com.silverorange.videoplayer.domain.repository.VideoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideVideoRepository(remoteDataSource: VideoRemoteDataSource): VideoRepository {
        return VideoRepositoryImpl(remoteDataSource)
    }
}
