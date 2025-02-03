package com.juco.workplacesetting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juco.domain.repository.WorkPlaceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkPlaceViewModel @Inject constructor(
    private val repository: WorkPlaceRepository
) : ViewModel() {
    var workPlaceName = MutableStateFlow("")
    var wage = MutableStateFlow("")

    fun saveWorkPlace() {
        val wageValue = wage.value.toIntOrNull() ?: return
        viewModelScope.launch {
            repository.saveWorkPlace(workPlaceName.value, wageValue)
            workPlaceName.value = ""
            wage.value = ""
        }
    }
}