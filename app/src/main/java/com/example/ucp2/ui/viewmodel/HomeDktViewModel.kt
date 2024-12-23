package com.example.ucp2.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.Dokter
import com.example.ucp2.repository.RepositoryDkt
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class HomeDktViewModel(
    private val repositoryDr: RepositoryDkt
) : ViewModel() {

    val homeDktUiState: StateFlow<HomeDktUiState> = repositoryDr.getAllDkt()
        .filterNotNull()
        .map {
            HomeDktUiState(
                listDr = it.toList(),
                isLoading = false,
            )
        }
        .onStart {
            emit(HomeDktUiState(isLoading = true))
            delay(900)
        }
        .catch {
            emit(
                HomeDktUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message ?: "Terjadi Kesalahan"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeDktUiState(
                isLoading = true
            )
        )

}
data class HomeDktUiState(
    val listDr: List<Dokter> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)