package com.silverorange.videoplayer.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.silverorange.videoplayer.databinding.ActivityMainBinding
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
//    private var viewModel: MainViewModel by viewModels()
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initObservers()
    }

    private fun initObservers() {
        handleProgress()
        handleError()
    }

    private fun handleProgress() {
        lifecycleScope.launch {
            viewModel.loadingStateFlow.collect { isShow ->
            }
        }
    }

    private fun handleError() {
        lifecycleScope.launch {
            viewModel.errorStateFlow.collect { errorMessage ->
                if (!errorMessage.isNullOrEmpty()){
                    Toast.makeText(this@MainActivity, errorMessage.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
