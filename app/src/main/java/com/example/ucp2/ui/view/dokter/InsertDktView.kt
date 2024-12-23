package com.example.ucp2.ui.view.dokter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.ui.costumwidget.TopAppBar
import com.example.ucp2.ui.navigation.AlamatNavigasi
import com.example.ucp2.ui.viewmodel.DktUiState
import com.example.ucp2.ui.viewmodel.DokterEvent
import com.example.ucp2.ui.viewmodel.DokterViewModel
import com.example.ucp2.ui.viewmodel.FormErrorState
import com.example.ucp2.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

@Composable
fun InsertDktView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DokterViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val uiState = viewModel.DktUiState
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(uiState.snackbarMessage) {
        uiState.snackbarMessage?.let { message ->
            coroutineScope.launch {
                snackBarHostState.showSnackbar(message)
                viewModel.resetSnackBarMessage()
            }
        }
    }
    Scaffold (
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) }

    ){padding ->
        Column (
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ){
            TopAppBar(
                onBack = onBack,
                showBackButton = true,
                judul = "Tambah Dokter"
            )
            InsertBodyDkt(
                uiState = uiState,
                onValueChange = {updateEvent ->
                    viewModel.updateUiState(updateEvent)
                },onClick = {
                    coroutineScope.launch {
                        viewModel.saveData()
                    }
                    onNavigate()
                }
            )
        }
    }
}

@Composable
fun InsertBodyDkt(
    modifier: Modifier = Modifier,
    onValueChange: (DokterEvent) -> Unit,
    uiState: DktUiState,
    onClick: () -> Unit
){
    Column  (
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        FormDokter(
            dokterEvent = uiState.DokterEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid,
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

object DestinasiInsertDr: AlamatNavigasi {
    override val route : String = "insert_dr"
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun FormDokter(
    dokterEvent: DokterEvent = DokterEvent(),
    onValueChange: (DokterEvent) -> Unit = { },
    errorState: FormErrorState = FormErrorState(),
    modifier: Modifier = Modifier
) {
    val listSpesialis = listOf("Spesialis Anak", "Spesialis Gigi", "Spesialis Kulit", "Spesialis Bedah")
    var expanded by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            modifier = modifier.fillMaxWidth(),
            value = dokterEvent.id,
            onValueChange = { onValueChange(dokterEvent.copy(id = it)) },
            label = { Text(text = "ID Dokter") },
            isError = errorState.id != null
        )
        Text(text = errorState.id ?: "", color = Color.Red)


        OutlinedTextField(
            modifier = modifier.fillMaxWidth(),
            value = dokterEvent.nama,
            onValueChange = { onValueChange(dokterEvent.copy(nama = it)) },
            label = { Text(text = "Nama") },
            isError = errorState.nama != null
        )
        Text(text = errorState.nama ?: "", color = Color.Red)


        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                value = dokterEvent.spesialis,
                onValueChange = { },
                readOnly = true,
                label = { Text("Spesialis") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                isError = errorState.spesialis != null
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                listSpesialis.forEach { spesialis ->
                    DropdownMenuItem(
                        text = { Text(text = spesialis) },
                        onClick = {
                            onValueChange(dokterEvent.copy(spesialis = spesialis))
                            expanded = false
                        }
                    )
                }
            }
        }
        Text(text = errorState.spesialis ?: "", color = Color.Red)

        OutlinedTextField(
            modifier = modifier.fillMaxWidth(),
            value = dokterEvent.klinik,
            onValueChange = { onValueChange(dokterEvent.copy(klinik = it)) },
            label = { Text(text = "Klinik") },
            isError = errorState.klinik != null
        )
        Text(text = errorState.klinik ?: "", color = Color.Red)

        OutlinedTextField(
            modifier = modifier.fillMaxWidth(),
            value = dokterEvent.noHp,
            onValueChange = { onValueChange(dokterEvent.copy(noHp = it)) },
            label = { Text(text = "Nomor Hp") },
            isError = errorState.noHp != null
        )
        Text(text = errorState.noHp ?: "", color = Color.Red)

        OutlinedTextField(
            modifier = modifier.fillMaxWidth(),
            value = dokterEvent.jamKerja,
            onValueChange = { onValueChange(dokterEvent.copy(jamKerja = it)) },
            label = { Text(text = "Jam Praktik") },
            isError = errorState.jamKerja != null
        )
        Text(text = errorState.jamKerja ?: "", color = Color.Red)
    }
}
