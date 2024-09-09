package com.example.addresssearch.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.addresssearch.viewmodel.HereViewModel

class HereViewModelFactory(private val repository: HereRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HereViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HereViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
