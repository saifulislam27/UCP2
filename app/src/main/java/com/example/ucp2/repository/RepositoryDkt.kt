package com.example.ucp2.repository

import com.example.ucp2.data.entity.Dokter
import kotlinx.coroutines.flow.Flow

interface RepositoryDkt {

    suspend fun insertDkt(dokter: Dokter)

    fun getAllDkt(): Flow<List<Dokter>>
}