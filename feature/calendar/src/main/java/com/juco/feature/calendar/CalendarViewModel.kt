package com.juco.feature.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juco.domain.model.WorkPlace
import com.juco.domain.repository.WorkPlaceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val repository: WorkPlaceRepository
) : ViewModel() {

    private val _monthlyWorkPlaces = MutableStateFlow<List<WorkPlace>>(emptyList())
    val monthlyWorkPlaces: StateFlow<List<WorkPlace>> = _monthlyWorkPlaces

    fun loadWorkPlacesForMonth(year: Int, month: Int) {
        viewModelScope.launch {
            val workPlaces = repository.observeWorkPlaces()
                .first()
                .filter { workPlace ->
                    workPlace.workDays.any { it.year == year && it.monthValue == month }
                }
            _monthlyWorkPlaces.value = workPlaces
        }
    }
}