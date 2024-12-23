package com.example.ucp2.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.Jadwal
import com.example.ucp2.repository.RepositoryJdw
import com.example.ucp2.ui.navigation.AlamatNavigasi
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UpdateJdwViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryJadwal: RepositoryJdw
) : ViewModel() {

    var updateJdwUIState by mutableStateOf(JdwUiState())
        private set

    private val _id: String = checkNotNull(savedStateHandle[AlamatNavigasi.DestinasiEditJdw.id])

    init {
        viewModelScope.launch {
            updateJdwUIState = repositoryJadwal.getJadwal(_id)
                .filterNotNull()
                .first()
                .toUIStateMhs()
        }
    }

    fun updateJdwState(jadwalEvent: JadwalEvent) {
        updateJdwUIState = updateJdwUIState.copy(
            JadwalEvent = jadwalEvent,
        )
    }

    fun validateFields(): Boolean {
        val event = updateJdwUIState.JadwalEvent
        val errorJadState = FormErrorJadwalState(
            id = if (event.id.isNotEmpty()) null else "Id Tidak Boleh Kosong",
            NamaDokter = if (event.NamaDokter.isNotEmpty()) null else "Nama Dokter Tidak Boleh Kosong",
            NamaPasien = if (event.NamaPasien.isNotEmpty()) null else "Nama Pasien Tidak Boleh Kosong",
            noHp = if (event.noHp.isNotEmpty()) null else "Nomor Hp Tidak Boleh Kosong",
            tanggalKonsultasi = if (event.tanggalKonsultasi.isNotEmpty()) null else "Tanggal Tidak Boleh Kosong",
            status = if (event.status.isNotEmpty()) null else "Status Tidak Boleh Kosong",
        )

        updateJdwUIState = updateJdwUIState.copy(isEntryValid = errorJadState)
        return errorJadState.isValid()
    }

    fun updateData() {
        val currentEvent = updateJdwUIState.JadwalEvent

        if (validateFields()) {
            viewModelScope.launch {
                try {
                    repositoryJadwal.updateJadwal(currentEvent.toJadwalEntity())
                    updateJdwUIState = updateJdwUIState.copy(
                        snackbarMessage = "Data Berhasil Diupdate",
                        JadwalEvent = JadwalEvent(),
                        isEntryValid = FormErrorJadwalState(),
                    )
                    println("snackBarMessage Diatur: ${updateJdwUIState.snackbarMessage}")
                } catch (e: Exception) {
                    updateJdwUIState = updateJdwUIState.copy(
                        snackbarMessage = "Data Gagal Diupdate"
                    )
                }
            }
        } else  {
            updateJdwUIState = updateJdwUIState.copy(
                snackbarMessage = "Data Gagal Diupdate"
            )
        }
    }

    fun resetSnackBarMessage() {
        updateJdwUIState = updateJdwUIState.copy(snackbarMessage = null)
    }
}

fun Jadwal.toUIStateMhs(): JdwUiState = JdwUiState(
    JadwalEvent = this.toDetailUiEvent(),
)