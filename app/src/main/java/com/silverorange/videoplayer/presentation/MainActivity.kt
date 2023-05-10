package com.silverorange.videoplayer.presentation

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.silverorange.videoplayer.R
import com.silverorange.videoplayer.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import io.noties.markwon.Markwon
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

    private var exoPlayer: ExoPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initObservers()
        handleCurrentVideo()
        setClickListeners()
    }

    private fun setClickListeners() {
        binding.buttonNext.setOnClickListener {
            viewModel.playNextVideo(true)
        }

        binding.buttonPrevious.setOnClickListener {
            viewModel.playNextVideo(false)
        }

        binding.buttonPlayPause.setOnClickListener {
            if (exoPlayer?.isPlaying == true) {
                exoPlayer?.pause()
                binding.buttonPlayPause.setImageResource(R.drawable.ic_play)
            } else {
                exoPlayer?.play()
                binding.buttonPlayPause.setImageResource(R.drawable.ic_pause)
            }
        }
    }

    private fun initObservers() {
        handleProgress()
        handleError()
        handleCurrentVideo()
    }

    private fun preparePlayer(url: String) {
        exoPlayer = ExoPlayer.Builder(this).build().apply {
            playWhenReady = false
            binding.playerView.player = this
            setMediaItem(MediaItem.fromUri(url))
            prepare()
        }
    }

    private fun releasePlayer() {
        exoPlayer?.release()
        exoPlayer = null
    }

    private fun handleCurrentVideo() {
        lifecycleScope.launch {
            viewModel.currentVideoFlow.collect { video ->

                binding.item = video
                binding.firstItem = video?.index == 0
                binding.lastItem = video?.index == viewModel.size - 1

                video?.description?.let { markdownInit(it) }
                video?.let {
                    exoPlayer?.playWhenReady = false
                    preparePlayer(it.hlsURL)
                }
            }
        }
    }

    private fun markdownInit(markdownText: String) {
        val markdown = Markwon.create(this)
        markdown.setMarkdown(binding.videoDescription, markdownText)
    }

    private fun handleProgress() {
        lifecycleScope.launch {
            viewModel.loadingStateFlow.collect { isShow ->
                binding.progressBar.isVisible = isShow
            }
        }
    }

    private fun handleError() {
        lifecycleScope.launch {
            viewModel.errorStateFlow.collect { errorMessage ->
                if (!errorMessage.isNullOrEmpty())
                    Toast.makeText(this@MainActivity, errorMessage.toString(), Toast.LENGTH_LONG)
                        .show()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    override fun onPause() {
        super.onPause()
        releasePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        releasePlayer()
    }
}
