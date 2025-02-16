package com.juco.workplacesetting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juco.domain.repository.WorkPlaceRepository
import com.juco.workplacesetting.model.WorkDayType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class WorkPlaceViewModel @Inject constructor(
    private val repository: WorkPlaceRepository
) : ViewModel() {

    var workPlaceName = MutableStateFlow("")
    var wage = MutableStateFlow("")
    var selectedWorkDayType = MutableStateFlow<WorkDayType?>(null)
    var selectedWorkDays = MutableStateFlow<List<LocalDate>>(emptyList())

    fun setWorkDays(dayOfWeeks: Set<DayOfWeek>) {
        val today = LocalDate.now()
        val days = generateSequence(today) { it.plusDays(1) }
            .take(365)
            .filter { it.dayOfWeek in dayOfWeeks }
            .toList()

        selectedWorkDays.value = days
    }

    fun setCustomWorkDays(dates: List<LocalDate>) {
        selectedWorkDayType.value = WorkDayType.CUSTOM
        selectedWorkDays.value = dates
    }

    fun saveWorkPlace() {
        val name = workPlaceName.value
        val wageValue = wage.value.toIntOrNull() ?: return
        val workDays = selectedWorkDays.value

        viewModelScope.launch {
            repository.saveWorkPlace(name, wageValue, workDays)
            workPlaceName.value = ""
            wage.value = ""
            selectedWorkDays.value = emptyList()
            selectedWorkDayType.value = null
        }
    }
}