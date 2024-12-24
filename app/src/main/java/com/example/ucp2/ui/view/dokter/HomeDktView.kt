package com.example.ucp2.ui.view.dokter

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.R
import com.example.ucp2.data.entity.Dokter
import com.example.ucp2.ui.costumwidget.TopAppBar
import com.example.ucp2.ui.viewmodel.HomeDktUiState
import com.example.ucp2.ui.viewmodel.HomeDktViewModel
import com.example.ucp2.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

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
                    namaApp = "Kesehatan MUH",
                    ID = R.drawable.ferrari

                )
                TopAppBar(
                    judul = "Daftar Dokter",
                    showBackButton = false,
                    onBack = { },
                )
            }
        }
    ) { innerPadding ->
        val homeDrUiState by viewModel.homeDktUiState.collectAsState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                        .clickable { onAddDr() }
                        .shadow(4.dp, shape = RoundedCornerShape(8.dp))
                        .background(Color(0xFF708090), shape = RoundedCornerShape(8.dp))
                ) {
                    Row(
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountBox,
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

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                        .clickable { onAddJad() }
                        .shadow(4.dp, shape = RoundedCornerShape(8.dp))
                        .background(Color(0xFF708090), shape = RoundedCornerShape(8.dp))
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

            BodyHomeDkt(
                homeDktUiState = homeDrUiState,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun BodyHomeDkt(
    homeDktUiState: HomeDktUiState,
    modifier: Modifier = Modifier,
) {
    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    // Common table background modifier without border
    val tableBackgroundModifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
        .background(
            color = Color(0xFF708090), // Light grey background color to resemble a table
            shape = RoundedCornerShape(8.dp)
        )

    when {
        homeDktUiState.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        homeDktUiState.isError -> {
            LaunchedEffect(homeDktUiState.errorMessage) {
                homeDktUiState.errorMessage?.let { message ->
                    coroutineScope.launch {
                        snackBarHostState.showSnackbar(message)
                    }
                }
            }
        }

        homeDktUiState.listDr.isEmpty() -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    modifier = tableBackgroundModifier,
                    color = Color.Transparent // No extra background color
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Tidak Ada Data Dokter",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }

        else -> {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Surface(
                    modifier = tableBackgroundModifier,
                    color = Color.Transparent // Transparent background inside table
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        // Add "List Dokter" text
                        Text(
                            text = "List Dokter",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        // List of doctors
                        ListDokter(
                            listDkt = homeDktUiState.listDr,
                            modifier = modifier
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun Header(
    namaApp: String,
    ID: Int
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFFA23E48),
                        Color(0xFFCCCCCC)
                    )
                ),
                shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
            )
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = namaApp,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Filled.ThumbUp,
                    contentDescription = "ikon",
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Mari Hidup Sehat",
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.8f)
            )

            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "search",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )

                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "customer service",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(40.dp))
                .background(Color.White.copy(alpha = 0.3f))
                .align(Alignment.TopEnd)
        ) {
            Image(
                painter = painterResource(ID),
                contentDescription = "Company Profile",
                modifier = Modifier
                    .size(70.dp)
                    .clip(RoundedCornerShape(35.dp))
                    .align(Alignment.Center),
                contentScale = ContentScale.Crop
            )
        }
    }
}


@Composable
fun ListDokter(
    listDkt: List<Dokter>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier) {
        items(
            items = listDkt,
            key = { it.id }
        ) { dr ->
            CardDokter(
                dr = dr
            )
        }
    }
}

@Composable
fun CardDokter(
    dr: Dokter,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { }
) {
    Card(
        modifier = modifier
            .fillMaxWidth(1F)
            .padding(10.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Icon Person",
                    modifier = Modifier.padding(end = 8.dp),
                    tint = Color.Black
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = dr.nama,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.DateRange,
                    contentDescription = "Icon Date",
                    modifier = Modifier.padding(end = 8.dp),
                    tint = Color.Black
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = dr.jamKerja,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = "Icon Home",
                    modifier = Modifier.padding(end = 8.dp),
                    tint = Color.Black
                )
                Spacer(modifier = Modifier.width(4.dp))
                val textColor = when (dr.spesialis.trim()) {
                    "Spesialis Anak" -> Color.Blue
                    "Spesialis Gigi" -> Color.Red
                    "Spesialis Kulit" -> Color.Green
                    "Spesialis Bedah" -> Color.Magenta
                    else -> Color.Black
                }
                Text(
                    text = dr.spesialis,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )
            }
        }
    }
}
