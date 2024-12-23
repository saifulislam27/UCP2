package com.example.ucp2.ui.view.dokter

import androidx.compose.runtime.Composable

@Composable
fun HomeDktView(
    viewModel: HomeDktViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onAddDr: () -> Unit = { },
    onAddJad: () -> Unit = { },
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            Column {
                Header(
                    namaApp = "IrMedika",
                    ID = R.drawable.gost2
                )
                TopAppBar(
                    judul = "Daftar Dokter",
                    showBackButton = false,
                    onBack = { },
                )
            }
        }
    ) { innerPadding ->
        val homeDrUiState by viewModel.homeDrUiState.collectAsState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp) // Memberikan jarak antar tombol
            ) {
                // Tombol Tambah Dokter
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                        .clickable { onAddDr() }
                        .shadow(4.dp, shape = RoundedCornerShape(8.dp))
                        .background(Color(0XFF000080), shape = RoundedCornerShape(8.dp))
                ) {
                    Row(
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Tambah Dokter Icon",
                            modifier = Modifier.padding(end = 8.dp),
                            tint = Color.White
                        )
                        Text(
                            text = "Tambah Dokter",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color.White
                        )
                    }
                }

                // Tombol Jadwal
                Box(
                    modifier = Modifier
                        .weight(1f) // Membuat tombol ini mengisi ruang yang tersedia
                        .padding(8.dp)
                        .clickable { onAddJad() }
                        .shadow(4.dp, shape = RoundedCornerShape(8.dp))
                        .background(Color(0XFF000080), shape = RoundedCornerShape(8.dp)) // Ubah warna jika perlu
                ) {
                    Row(
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Jadwal Icon",
                            modifier = Modifier.padding(end = 8.dp),
                            tint = Color.White
                        )
                        Text(
                            text = "Jadwal Pasien",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color.White
                        )
                    }
                }
            }

            // Tampilan BodyHomeDr
            BodyHomeDr(
                homeDrUiState = homeDrUiState,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}