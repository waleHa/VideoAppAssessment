package com.silverorange.videoplayer.data.datasource

import com.silverorange.videoplayer.domain.model.VideoPlayerWrapper
import retrofit2.http.GET

interface VideoRemoteDataSource {
    @GET("/videos")
    suspend fun getVideos(): List<VideoPlayerWrapper>

}