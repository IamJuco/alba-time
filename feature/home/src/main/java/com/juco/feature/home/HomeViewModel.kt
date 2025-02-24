package com.juco.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juco.domain.model.WorkPlace
import com.juco.domain.repository.WorkPlaceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: WorkPlaceRepository
) : ViewModel() {
    private val _workPlaces = MutableStateFlow<List<WorkPlace>>(emptyList())
    val workPlaces: StateFlow<List<WorkPlace>> = _workPlaces.asStateFlow()

    init {
        viewModelScope.launch {
            repository.observeWorkPlaces().collect {
                _workPlaces.value = it
            }
        }
    }
}