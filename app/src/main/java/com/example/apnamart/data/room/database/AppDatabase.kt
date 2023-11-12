package com.example.apnamart.data.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.apnamart.data.room.dao.AppDao
import com.example.apnamart.data.room.enitities.RepositoryModelEntity

@Database(entities = [RepositoryModelEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getAppDao(): AppDao
}