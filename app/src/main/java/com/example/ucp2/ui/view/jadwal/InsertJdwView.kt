package com.example.ucp2.ui.view.jadwal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.ui.costumwidget.DynamicSelectedTextField
import com.example.ucp2.ui.navigation.AlamatNavigasi
import com.example.ucp2.ui.viewmodel.FormErrorJadwalState
import com.example.ucp2.ui.viewmodel.JadwalEvent
import com.example.ucp2.ui.viewmodel.JadwalViewModel
import com.example.ucp2.ui.viewmodel.JdwUiState
import com.example.ucp2.ui.viewmodel.PenyediaViewModel

@Composable
fun InsertBodyJadwal(
    modifier: Modifier = Modifier,
    onValueChange: (JadwalEvent) -> Unit,
    JdwState: JdwUiState,
    onClick: () -> Unit
){
    Column  (
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        FormJadwal(
            jadwalEvent = JdwState.JadwalEvent,
            onValueChange = onValueChange,
            errorJadwalState = JdwState.isEntryValid,
            modifier = modifier.fillMaxWidth()
        )
        Button(
            onClick = onClick,
            modifier = modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF708090)
            )
        ){
            Text(text = "Simpan")
        }
    }
}

object DestinasiInsertJad: AlamatNavigasi {
    override val route : String = "insert_Jad"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormJadwal(
    jadwalEvent: JadwalEvent = JadwalEvent(),
    onValueChange: (JadwalEvent) -> Unit = { },
    errorJadwalState: FormErrorJadwalState,
    viewModel: JadwalViewModel = viewModel(factory = PenyediaViewModel.Factory),
    modifier: Modifier = Modifier
) {
    var chosenDropdown by remember { mutableStateOf("") }

    var PilihDokter by remember { mutableStateOf(listOf<String>()) }

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            modifier = modifier.fillMaxWidth(),
            value = jadwalEvent.id,
            onValueChange = { onValueChange(jadwalEvent.copy(id = it)) },
            label = { Text(text = "ID") },
            isError = errorJadwalState.id != null
        )

        LaunchedEffect (Unit) {
            viewModel.listDokter.collect { dokterList ->
                PilihDokter = dokterList.map { it.nama }
            }
        }

        DynamicSelectedTextField(
            selectedValue = chosenDropdown,
            options = PilihDokter,
            label = "Pilih Nama Dokter",
            onValueChangedEvent = {
                onValueChange(jadwalEvent.copy(NamaDokter = it))
                chosenDropdown = it
            }
        )
        Text(
            text = errorJadwalState.NamaDokter ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            modifier = modifier.fillMaxWidth(),
            value = jadwalEvent.NamaPasien,
            onValueChange = { onValueChange(jadwalEvent.copy(NamaPasien = it)) },
            label = { Text(text = "Nama Pasien") },
            isError = errorJadwalState.NamaPasien != null
        )
        Text(text = errorJadwalState.NamaPasien ?: "", color = Color.Red)

        OutlinedTextField(
            modifier = modifier.fillMaxWidth(),
            value = jadwalEvent.noHp,
            onValueChange = { onValueChange(jadwalEvent.copy(noHp = it)) },
            label = { Text(text = "Nomor Hp") },
            isError = errorJadwalState.noHp != null
        )
        Text(text = errorJadwalState.noHp ?: "", color = Color.Red)

        OutlinedTextField(
            modifier = modifier.fillMaxWidth(),
            value = jadwalEvent.tanggalKonsultasi,
            onValueChange = { onValueChange(jadwalEvent.copy(tanggalKonsultasi = it)) },
            label = { Text(text = "Tanggal Konsultasi") },
            isError = errorJadwalState.tanggalKonsultasi != null
        )
        Text(text = errorJadwalState.tanggalKonsultasi?: "", color = Color.Red)

        OutlinedTextField(
            modifier = modifier.fillMaxWidth(),
            value = jadwalEvent.status,
            onValueChange = { onValueChange(jadwalEvent.copy(status = it)) },
            label = { Text(text = "Status") },
            isError = errorJadwalState.status != null
        )
        Text(text = errorJadwalState.status?: "", color = Color.Red)
    }
}

