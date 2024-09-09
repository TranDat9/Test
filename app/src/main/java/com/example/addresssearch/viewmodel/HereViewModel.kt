package com.example.addresssearch.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.addresssearch.data.HereRepository
import com.example.addresssearch.data.Place
import kotlinx.coroutines.launch

class HereViewModel(private val repository: HereRepository) : ViewModel() {

    private val _cities = MutableLiveData<List<Place>>()
    val cities: LiveData<List<Place>> = _cities

    fun searchCities(query: String,apiKey: String) {
        viewModelScope.launch {
            val result = repository.getVietnamCities(query,apiKey)
            _cities.postValue(result)
        }
    }

    fun clearPlaces() {
        _cities.postValue(emptyList())
    }
}

