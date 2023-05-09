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
class MainViewModel @Inject constructor(private val useCase: GetVideoSortedUseCase)
: ViewModel() {

    private val _successStateFlow = MutableStateFlow<List<VideoPlayerWrapper>?>(null)
    val successStateFlow : StateFlow<List<VideoPlayerWrapper>?> = _successStateFlow

    private val _errorStateFlow = MutableStateFlow<String?> (null)
    val errorStateFlow : StateFlow<String?> = _errorStateFlow

    private val _loadingStateFlow = MutableStateFlow(true)
    val loadingStateFlow : StateFlow<Boolean> = _loadingStateFlow

    init {
        getVideos()
    }

    private fun getVideos(){
        viewModelScope.launch {
            _loadingStateFlow.emit(true)
            try {
                _successStateFlow.emit(useCase())
            }catch (e:Exception){
                _errorStateFlow.emit(e.message)
            }
            _loadingStateFlow.emit(false)
        }
    }
}