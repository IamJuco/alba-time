package com.juco.workplaceedit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juco.domain.model.WorkPlace
import com.juco.domain.repository.WorkPlaceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkPlaceEditViewModel @Inject constructor(
    private val repository: WorkPlaceRepository
) : ViewModel() {
    private val _updateEvent = MutableSharedFlow<Unit>()
    val updateEvent: SharedFlow<Unit> = _updateEvent.asSharedFlow()

    private val _deleteEvent = MutableSharedFlow<Unit>()
    val deleteEvent: SharedFlow<Unit> = _deleteEvent.asSharedFlow()

    private val _workPlace = MutableStateFlow<WorkPlace?>(null)
    val workPlace: StateFlow<WorkPlace?> = _workPlace.asStateFlow()

    fun loadWorkPlaceById(workPlaceId: Int) {
        viewModelScope.launch {
            _workPlace.value = repository.getWorkPlaceById(workPlaceId)
        }
    }

    fun updateWorkPlace(updatedWorkPlace: WorkPlace) {
        viewModelScope.launch {
            repository.updateWorkPlace(updatedWorkPlace)
            _updateEvent.emit(Unit)
        }
    }

    fun deleteWorkPlace(workPlace: WorkPlace) {
        viewModelScope.launch {
            repository.deleteWorkPlace(workPlace)
            _deleteEvent.emit(Unit)
        }
    }
}
