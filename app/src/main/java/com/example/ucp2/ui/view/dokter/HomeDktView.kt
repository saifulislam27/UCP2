package com.example.ucp2.ui.view.dokter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
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
import com.example.ucp2.data.entity.Dokter

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
