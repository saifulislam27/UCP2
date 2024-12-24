package com.example.ucp2.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.Dokter
import com.example.ucp2.repository.RepositoryDkt
import kotlinx.coroutines.launch

class DokterViewModel(
    private val repositoryDr: RepositoryDkt
) : ViewModel() {
    var DktUiState by mutableStateOf(DktUiState())

    fun updateUiState(dokterEvent: DokterEvent) {
        DktUiState = DktUiState.copy(
            DokterEvent = dokterEvent,
        )
    }

    private fun validateFields(): Boolean {
        val event = DktUiState.DokterEvent
        val errorState = FormErrorState(
            id = if (event.id.isEmpty()) "Id tidak boleh kosong" else null,
            nama = if (event.nama.isEmpty()) "Nama tidak boleh kosong" else null,
            spesialis = if (event.spesialis.isEmpty()) "Spesialis tidak boleh kosong" else null,
            klinik = if (event.klinik.isEmpty()) "Klinik tidak boleh kosong" else null,
            noHp = if (event.noHp.isEmpty()) "Nomor Hp tidak boleh kosong" else null,
            jamKerja = if (event.jamKerja.isEmpty()) "Jam Praktik tidak boleh kosong" else null
        )
        DktUiState = DktUiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }
    fun saveData() {

        val currentEvent = DktUiState.DokterEvent

        if (validateFields()) {
            viewModelScope.launch {
                try {
                    repositoryDr.insertDkt(currentEvent.toDokterEntity())
                    DktUiState = DktUiState.copy(
                        snackbarMessage = "Data berhasil disimpan",
                        DokterEvent = DokterEvent(),
                        isEntryValid = FormErrorState()
                    )
                } catch (e: Exception) {
                    DktUiState = DktUiState.copy(
                        snackbarMessage = "Data gagal disimpan"
                    )
                }
            }
        }else {
            DktUiState = DktUiState.copy(
                snackbarMessage = "Data tidak valid. Periksa kembali data Anda."
            )
        }
    }

    fun resetSnackBarMessage() {
        DktUiState = DktUiState.copy(
            snackbarMessage = null
        )
    }
}

data class DokterEvent(
    var id: String = "",
    var nama: String = "",
    var spesialis: String = "",
    var klinik: String = "",
    var noHp: String = "",
    var jamKerja: String = ""
)
fun DokterEvent.toDokterEntity(): Dokter = Dokter(
    id = id,
    nama = nama,
    spesialis = spesialis,
    klinik = klinik,
    noHp = noHp,
    jamKerja = jamKerja
)
data class FormErrorState(
    val id : String? = null,
    val nama : String? = null,
    val spesialis : String? = null,
    val klinik : String? = null,
    val noHp : String? = null,
    val jamKerja : String? = null
)
{
    fun isValid(): Boolean {
        return id == null &&
                nama == null &&
                spesialis == null &&
                klinik == null &&
                noHp == null &&
                jamKerja == null
    }
}
data class DktUiState(
    val DokterEvent: DokterEvent = DokterEvent(),
    val isEntryValid: FormErrorState = FormErrorState(),
    val snackbarMessage: String? = null
)
