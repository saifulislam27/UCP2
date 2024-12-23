package com.example.ucp2.dependenciesinjection

import android.content.Context
import com.example.ucp2.data.database.PelayananDokterDatabase
import com.example.ucp2.repository.LocalRepositoryDkt
import com.example.ucp2.repository.RepositoryDkt

interface InterfaceDokter {
    val repositoryDr : RepositoryDkt
}

class ContainerAppDkt(private val context: Context) : InterfaceDokter {
    override val repositoryDr: RepositoryDkt by lazy {
        LocalRepositoryDkt(PelayananDokterDatabase.getDatabase(context).DokterDao())
    }
}