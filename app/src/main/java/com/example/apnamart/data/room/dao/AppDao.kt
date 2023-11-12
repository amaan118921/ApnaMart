package com.example.apnamart.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.apnamart.data.room.enitities.RepositoryModelEntity

@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepos(list: List<RepositoryModelEntity>)

    @Query("SELECT * FROM repository_table")
    fun getAllRepos(): List<RepositoryModelEntity>

    @Query("DELETE FROM repository_table")
    suspend fun deleteAllRepos()

}