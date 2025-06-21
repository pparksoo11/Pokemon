package com.soo.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

open class BaseViewModel: ViewModel() {

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent

    protected suspend fun sendUiEvent(event: UiEvent) {
        _uiEvent.emit(event)
    }

    /**
     * 토스트 메시지 노출
     * */
    fun showToast(message: String) {
        viewModelScope.launch {
            sendUiEvent(UiEvent.ShowToast(message))
        }
    }

    sealed class UiEvent {
        data class ShowToast(val message: String) : UiEvent()
    }
}