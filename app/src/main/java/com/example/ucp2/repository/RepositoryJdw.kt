package com.example.ucp2.repository

import androidx.room.Query
import com.example.ucp2.data.entity.Dokter
import com.example.ucp2.data.entity.Jadwal
import kotlinx.coroutines.flow.Flow

interface RepositoryJdw {

    suspend fun insertJadwal(jadwal: Jadwal)

    fun getAllJadwal(): Flow<List<Jadwal>>

    fun getJadwal(id: String): Flow<Jadwal>

    suspend fun deleteJadwal(jadwal: Jadwal)

    suspend fun updateJadwal(jadwal: Jadwal)

    @Query("SELECT * FROM Dokter ORDER BY nama DESC")
    fun getAllNamaDokter(): Flow<List<Dokter>>
}