package com.example.citysearch.ui.city

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citysearch.data.model.City
import com.example.citysearch.data.repository.CityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityViewModel @Inject constructor(private val repository: CityRepository) : ViewModel() {

    private val _cities = MutableLiveData<List<City>>()
    val cities: LiveData<List<City>> get() = _cities

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    init {
        _cities.value = emptyList()
        _isLoading.value = false
    }

    fun loadCities(json: String) {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateCities(json)
            _cities.postValue(repository.filterCities("").sortedBy { it.name })
            _isLoading.postValue(false)
        }
    }

    fun filterCities(prefix: String) {
        _cities.value = repository.filterCities(prefix).sortedBy { it.name }
    }
}
