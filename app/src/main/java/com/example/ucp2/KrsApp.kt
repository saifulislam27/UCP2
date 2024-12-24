package com.example.ucp2

import android.app.Application
import com.example.ucp2.dependenciesinjection.ContainerAppDkt
import com.example.ucp2.dependenciesinjection.ContainerAppJdw

class KrsApp : Application() {
    lateinit var containerAppDr: ContainerAppDkt
    lateinit var containerAppJadwal: ContainerAppJdw

    override fun onCreate() {
        super.onCreate()
        containerAppDr = ContainerAppDkt(this)
        containerAppJadwal = ContainerAppJdw(this)
    }

}