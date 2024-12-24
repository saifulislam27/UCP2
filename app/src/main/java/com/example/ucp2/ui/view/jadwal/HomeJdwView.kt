package com.example.ucp2.ui.view.jadwal

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ucp2.data.entity.Jadwal
import com.example.ucp2.repository.RepositoryJdw

@Composable
fun ListJadwal(
    listJdw: List<Jadwal>,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit = { }
) {
    LazyColumn(
        modifier = modifier
    ) {
            items(
                items = listJdw,
                itemContent = { jdw ->
                    CardJdw(
                        Jdw = jdw,
                        onClick = { onClick(jdw.id) }
                )
            }
        )
    }
}

@Composable
fun CardJdw (
    Jdw: Jadwal,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { }
) {
    Card (
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        onClick = onClick
    ) {
        Column (
            modifier = Modifier.padding(8.dp)

        ){
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "pasien",
                    tint = Color.Black
                )
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = Jdw.namaPasien,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(
                    imageVector = Icons.Filled.AccountBox,
                    contentDescription = "dokter",
                    tint = Color.Black
                )
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = Jdw.namaDokter,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(
                    imageVector = Icons.Filled.DateRange,
                    contentDescription = "tanggal",
                    tint = Color.Black
                )
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = Jdw.tanggalKonsultasi,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}
