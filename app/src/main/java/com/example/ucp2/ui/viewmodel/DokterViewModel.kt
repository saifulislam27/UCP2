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
