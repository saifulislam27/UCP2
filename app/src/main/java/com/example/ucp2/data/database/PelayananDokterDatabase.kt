package com.example.ucp2.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ucp2.data.dao.DokterDao
import com.example.ucp2.data.dao.JadwalDao
import com.example.ucp2.data.entity.Dokter
import com.example.ucp2.data.entity.Jadwal

@Database(entities = [Dokter::class, Jadwal::class], version = 1, exportSchema = false)
abstract class PelayananDokterDatabase : RoomDatabase() {

    abstract fun DokterDao(): DokterDao

    abstract fun JadwalDao(): JadwalDao
    companion object {
        @Volatile
        private var Instance: PelayananDokterDatabase? = null

        fun getDatabase(context: Context): PelayananDokterDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    PelayananDokterDatabase::class.java,
                    "PelayananDokterDatabase"
                )
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
