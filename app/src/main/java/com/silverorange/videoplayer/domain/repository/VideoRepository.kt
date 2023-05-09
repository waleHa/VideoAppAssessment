package com.silverorange.videoplayer.domain.repository

import com.silverorange.videoplayer.domain.model.VideoPlayerWrapper

interface VideoRepository {
    suspend fun getVideos(): List<VideoPlayerWrapper>
}