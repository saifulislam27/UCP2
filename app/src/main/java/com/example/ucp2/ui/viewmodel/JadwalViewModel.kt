package com.example.ucp2.ui.viewmodel

import com.example.ucp2.data.entity.Jadwal

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