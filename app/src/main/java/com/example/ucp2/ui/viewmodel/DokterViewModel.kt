package com.example.ucp2.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.ucp2.data.entity.Dokter
import com.example.ucp2.repository.RepositoryDkt

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
