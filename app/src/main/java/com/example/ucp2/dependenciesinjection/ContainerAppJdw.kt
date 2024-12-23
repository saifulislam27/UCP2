package com.example.ucp2.dependenciesinjection

import android.content.Context
import com.example.ucp2.data.database.PelayananDokterDatabase
import com.example.ucp2.repository.LocalRepositoryJdw
import com.example.ucp2.repository.RepositoryJdw

interface InterfaceJadwal{
    val repositoryJadwal: RepositoryJdw
}

class ContainerAppJdw(private val context: Context) : InterfaceJadwal{
    override val repositoryJadwal: RepositoryJdw by lazy {
        LocalRepositoryJdw(PelayananDokterDatabase.getDatabase(context).JadwalDao())
    }
}