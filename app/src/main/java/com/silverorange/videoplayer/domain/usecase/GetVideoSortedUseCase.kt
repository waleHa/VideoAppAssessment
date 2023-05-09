package com.silverorange.videoplayer.domain.usecase

import com.silverorange.videoplayer.domain.repository.VideoRepository
import javax.inject.Inject


class GetVideoSortedUseCase @Inject constructor(private val repository: VideoRepository) {
    suspend operator fun invoke() = repository.getVideos().sortedBy { it.publishedAt }
}
