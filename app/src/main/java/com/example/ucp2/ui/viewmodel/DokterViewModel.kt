package com.example.ucp2.ui.viewmodel

import com.example.ucp2.data.entity.Dokter

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
