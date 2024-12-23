package com.example.ucp2.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.Dokter
import com.example.ucp2.data.entity.Jadwal
import com.example.ucp2.repository.RepositoryJdw
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class JadwalViewModel(
    private val repositoryJadwal: RepositoryJdw,
) : ViewModel() {

    val listDokter: Flow<List<Dokter>> = repositoryJadwal.getAllNamaDokter()
    var JdwUiState by mutableStateOf(JdwUiState())

    fun updateJadState(jadwalEvent: JadwalEvent) {
        JdwUiState = JdwUiState.copy(
            JadwalEvent = jadwalEvent
        )
    }

    private fun validateFields(): Boolean {
        val event = JdwUiState.JadwalEvent
        val errorJState = FormErrorJadwalState(
            id = if (event.id.isEmpty()) "Id tidak boleh kosong" else null,
            NamaDokter = if (event.NamaDokter.isEmpty()) "Nama Dokter tidak boleh kosong" else null,
            NamaPasien = if (event.NamaPasien.isEmpty()) "Nama Pasien tidak boleh kosong" else null,
            noHp = if (event.noHp.isEmpty()) "Nomor Hp tidak boleh kosong" else null,
            tanggalKonsultasi = if (event.tanggalKonsultasi.isEmpty()) "Tanggal tidak boleh kosong" else null,
            status = if (event.status.isEmpty()) "Status tidak boleh kosong" else null
        )
        JdwUiState = JdwUiState.copy(isEntryValid = errorJState)
        return errorJState.isValid()
    }

    fun saveData() {
        val currentEvent = JdwUiState.JadwalEvent

        if (validateFields()) {
            viewModelScope.launch {
                try {
                    repositoryJadwal.insertJadwal(currentEvent.toJadwalEntity())
                    JdwUiState = JdwUiState.copy(
                        snackbarMessage = "Data berhasil disimpan",
                        JadwalEvent = JadwalEvent(),
                        isEntryValid = FormErrorJadwalState()
                    )
                } catch (e: Exception) {
                    JdwUiState = JdwUiState.copy(
                        snackbarMessage = "Data gagal disimpan: ${e.message}"
                    )
                }
            }
        } else {
            JdwUiState = JdwUiState.copy(
                snackbarMessage = "Data tidak valid. Periksa kembali data Anda."
            )
        }
    }

    fun resetSnackbarMessage() {
        JdwUiState = JdwUiState.copy(
            snackbarMessage = null
        )
    }
}

data class JadwalEvent(
    val id: String = "",
    val NamaDokter: String = "",
    val NamaPasien: String = "",
    val noHp: String = "",
    val tanggalKonsultasi: String = "",
    val status: String = ""
)

fun JadwalEvent.toJadwalEntity(): Jadwal = Jadwal(
    id = id,
    namaDokter = NamaDokter,
    namaPasien = NamaPasien,
    noHp = noHp,
    tanggalKonsultasi = tanggalKonsultasi,
    status = status
)

data class FormErrorJadwalState(
    val id: String? = null,
    val NamaDokter: String? = null,
    val NamaPasien: String? = null,
    val noHp: String? = null,
    val tanggalKonsultasi: String? = null,
    val status: String? = null
) {
    fun isValid(): Boolean {
        return id == null &&
                NamaDokter == null &&
                NamaPasien == null &&
                noHp == null &&
                tanggalKonsultasi == null &&
                status == null
    }
}

data class JdwUiState(
    val JadwalEvent: JadwalEvent = JadwalEvent(),
    val isEntryValid: FormErrorJadwalState = FormErrorJadwalState(),
    val snackbarMessage: String? = null
)

