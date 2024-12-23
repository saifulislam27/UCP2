package com.example.ucp2.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.Jadwal
import com.example.ucp2.repository.RepositoryJdw
import com.example.ucp2.ui.navigation.AlamatNavigasi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetailJdwViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryJadwal: RepositoryJdw,

    ) : ViewModel() {
    private val _id: String = checkNotNull(savedStateHandle[AlamatNavigasi.DestinasiDetailJdw.id])

    val detailUiState: StateFlow<DetailJdwUiState> = repositoryJadwal.getJadwal(_id)
        .filterNotNull()
        .map {
            DetailJdwUiState(
                detailUiEvent = it.toDetailUiEvent(),
                isLoading = false,
            )
        }
        .onStart {
            emit(DetailJdwUiState(isLoading = true))
            delay(600)
        }
        .catch {
            emit(
                DetailJdwUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message ?: "Terjadi Kesalahan"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(2000),
            initialValue = DetailJdwUiState(
                isLoading = true
            ),
        )
    fun deleteJadwal() {
        detailUiState.value.detailUiEvent.toJadwalEntity().let {
            viewModelScope.launch {
                repositoryJadwal.deleteJadwal(it)
            }
        }
    }
}
data class DetailJdwUiState(
    val detailUiEvent: JadwalEvent = JadwalEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
){
    val isUiEventEmpty: Boolean
        get()= detailUiEvent == JadwalEvent()
    val isUiEventNotEmpty: Boolean
        get() = detailUiEvent != JadwalEvent()
}
fun Jadwal.toDetailUiEvent(): JadwalEvent {
    return JadwalEvent(
        id = id,
        NamaDokter = namaDokter,
        NamaPasien = namaPasien,
        noHp = noHp,
        tanggalKonsultasi = tanggalKonsultasi,
        status = status
    )
}