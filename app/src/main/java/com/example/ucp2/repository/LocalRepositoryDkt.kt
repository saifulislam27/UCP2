package com.example.ucp2.repository

import com.example.ucp2.data.dao.DokterDao
import com.example.ucp2.data.entity.Dokter
import kotlinx.coroutines.flow.Flow

class LocalRepositoryDkt(
    private val dokterDao: DokterDao
):RepositoryDkt {

    override suspend fun insertDkt(dokter: Dokter) {
        dokterDao.insertDokter(dokter)
    }
    override fun getAllDkt(): Flow<List<Dokter>> {
        return dokterDao.getAllDokter()
    }
}