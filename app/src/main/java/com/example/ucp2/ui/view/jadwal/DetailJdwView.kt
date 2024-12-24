package com.example.ucp2.ui.view.jadwal

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.data.entity.Jadwal
import com.example.ucp2.ui.costumwidget.TopAppBar
import com.example.ucp2.ui.viewmodel.DetailJdwUiState
import com.example.ucp2.ui.viewmodel.DetailJdwViewModel
import com.example.ucp2.ui.viewmodel.PenyediaViewModel
import com.example.ucp2.ui.viewmodel.toJadwalEntity

@Composable
fun DetailJadView(
    modifier: Modifier = Modifier,
    viewModel: DetailJdwViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onBack: () -> Unit = { },
    onEditClick: (String) -> Unit = { },
    onDeleteClick: () -> Unit = { }
) {
    Scaffold (
        modifier = Modifier,
        topBar = {
            TopAppBar(
                judul = "Detail Jadwal",
                showBackButton = true,
                onBack = onBack,
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEditClick(viewModel.detailUiState.value.detailUiEvent.id) },
                shape = MaterialTheme.shapes.medium,
                containerColor = Color(0xFF708090),
                modifier = Modifier.padding(16.dp)

            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Jadwal",
                    tint = Color.White,
                )
            }
        }
    ) {
            innerPadding ->
        val detailUiState by viewModel.detailUiState.collectAsState()

        BodyDetailJdw(
            modifier = modifier.padding(innerPadding),
            detailUiState = detailUiState,
            onDeleteClick = {
                viewModel.deleteJadwal()
                onDeleteClick()
            }
        )
    }
}

@Composable
fun BodyDetailJdw(
    modifier: Modifier = Modifier,
    detailUiState: DetailJdwUiState = DetailJdwUiState(),
    onDeleteClick: () -> Unit = { }
) {
    var deleteConfirmationReuired by rememberSaveable { mutableStateOf(false) }
    when {
        detailUiState.isLoading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        detailUiState.isUiEventNotEmpty -> {
            Column (
                modifier = modifier.fillMaxWidth()
                    .padding(16.dp)
            ){
                ItemDetailJdw(
                    jadwal = detailUiState.detailUiEvent.toJadwalEntity(),
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Button(
                    onClick = { deleteConfirmationReuired = true },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF708090)
                    )
                ) {
                    Text(text = "Delete")
                }

                if (deleteConfirmationReuired) {
                    DeleteConfirmationDialog(
                        onDeleteConfirm = {
                            deleteConfirmationReuired = false
                            onDeleteClick()
                        },
                        onDeleteCancel = { deleteConfirmationReuired = false },
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }

        detailUiState.isUiEventEmpty -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Data Tidak Ada",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun ItemDetailJdw(
    modifier: Modifier = Modifier,
    jadwal: Jadwal
) {
    Card (
        modifier = modifier
            .fillMaxWidth(),
    ) {
        Column (
            modifier = Modifier.padding(16.dp),
        ){
            ComponentDetailJdw(judul = "Id", isinya = jadwal.id)
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailJdw(judul = "Nama Dokter", isinya = jadwal.namaDokter)
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailJdw(judul = "Nama Pasien", isinya = jadwal.namaPasien)
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailJdw(judul = "Nomor hp", isinya = jadwal.noHp)
            Spacer(modifier = Modifier.padding(4.dp))


            ComponentDetailJdw(judul = "Tanggal Konsultasi", isinya = jadwal.tanggalKonsultasi)
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailJdw(judul = "Status", isinya = jadwal.status)
            Spacer(modifier = Modifier.padding(4.dp))

        }
    }
}

@Composable
fun ComponentDetailJdw(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String
) {
    Column (
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ){
        Text(
            text = "$judul : ",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray
        )

        Text(
            text = isinya,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}


@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = { },
        title = { Text("Delete Data") },
        text = { Text("Anda yakin ingin menghapus data ini?") },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = "Batal")
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = "Ya")
            }
        }
    )
}