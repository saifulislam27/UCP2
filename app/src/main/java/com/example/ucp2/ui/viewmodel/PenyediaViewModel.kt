package com.example.ucp2.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ucp2.KrsApp

object PenyediaViewModel{
    val Factory = viewModelFactory {

        initializer {
            DokterViewModel(
                IRMApp().containerAppDr.repositoryDr
            )
        }

        initializer {
            HomeDktViewModel(
                IRMApp().containerAppDr.repositoryDr
            )
        }

        initializer {
            JadwalViewModel(
                IRMApp().containerAppJadwal.repositoryJadwal
            )
        }

        initializer {
            HomeJdwViewModel(
                IRMApp().containerAppJadwal.repositoryJadwal
            )
        }

        initializer {
            DetailJdwViewModel(
                createSavedStateHandle(),
                IRMApp().containerAppJadwal.repositoryJadwal
            )
        }

        initializer {
            UpdateJdwViewModel(
                createSavedStateHandle(),
                IRMApp().containerAppJadwal.repositoryJadwal
            )
        }
    }
}
fun CreationExtras.IRMApp(): KrsApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as KrsApp)