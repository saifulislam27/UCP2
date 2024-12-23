package com.example.ucp2.ui.viewmodel

import com.example.ucp2.data.entity.Jadwal

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