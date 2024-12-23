package com.example.ucp2.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.Jadwal
import com.example.ucp2.repository.RepositoryJdw
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class HomeJdwViewModel(
    private val repositoryJadwal: RepositoryJdw
) : ViewModel() {

    val homeJdwUiState: StateFlow<HomeJdwUiState> = repositoryJadwal.getAllJadwal()
        .filterNotNull()
        .map {
            HomeJdwUiState(
                listJdw = it.toList(),
                isLoading = false,
            )
        }
        .onStart {
            emit(HomeJdwUiState(isLoading = true))
            delay(900)
        }
        .catch {
            emit(
                HomeJdwUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message ?: "Terjadi Kesalahan"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeJdwUiState(
                isLoading = true
            )
        )

}
data class HomeJdwUiState(
    val listJdw: List<Jadwal> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)