package com.silverorange.videoplayer.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.silverorange.videoplayer.domain.model.VideoPlayerWrapper
import com.silverorange.videoplayer.domain.usecase.GetVideoSortedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val useCase: GetVideoSortedUseCase) : ViewModel() {

    private val _successStateFlow = MutableStateFlow<List<VideoPlayerWrapper>?>(null)
    val successStateFlow: StateFlow<List<VideoPlayerWrapper>?> = _successStateFlow

    private var _currentVideoFlow = MutableStateFlow<VideoPlayerWrapper?>(null)
    var currentVideoFlow: StateFlow<VideoPlayerWrapper?> = _currentVideoFlow

    private val _errorStateFlow = MutableStateFlow<String?>(null)
    val errorStateFlow: StateFlow<String?> = _errorStateFlow

    private val _loadingStateFlow = MutableStateFlow(true)
    val loadingStateFlow: StateFlow<Boolean> = _loadingStateFlow

    var size: Int = 0

    init {
        getVideos()
    }

    private fun getVideos() {
        viewModelScope.launch {
            _loadingStateFlow.emit(true)
            try {
                val data = useCase()
                data.forEachIndexed { index, it ->
                    it.index = index
                }
                size = data.size
                _successStateFlow.emit(data)
                _currentVideoFlow.emit(data.firstOrNull())
            } catch (e: Exception) {
                _errorStateFlow.emit(e.message)
            }
            _loadingStateFlow.emit(false)
        }
    }

    fun playNextVideo(next: Boolean) {
        val currentIndex = _successStateFlow.value?.indexOf(_currentVideoFlow.value)
        currentIndex?.let {
            val nextIndex = if (next) it + 1 else it - 1
            if (nextIndex >= 0 && nextIndex < _successStateFlow.value?.size ?: 0) {
                _currentVideoFlow.value = _successStateFlow.value?.get(nextIndex)
            }
        }
    }


}
