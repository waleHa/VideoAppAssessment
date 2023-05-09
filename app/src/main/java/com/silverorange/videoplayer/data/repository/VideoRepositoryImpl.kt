package com.silverorange.videoplayer.data.repository

import com.silverorange.videoplayer.data.datasource.VideoRemoteDataSource
import com.silverorange.videoplayer.domain.model.VideoPlayerWrapper
import com.silverorange.videoplayer.domain.repository.VideoRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VideoRepositoryImpl @Inject constructor(private val dataSource: VideoRemoteDataSource) :
    VideoRepository {
    override suspend fun getVideos(): List<VideoPlayerWrapper> = dataSource.getVideos()
}
