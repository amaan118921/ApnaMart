package com.example.apnamart.data.room.dao

import com.example.apnamart.data.room.enitities.RepositoryModelEntity

class FakeAppDao : AppDao {
    private val database: MutableList<RepositoryModelEntity> = mutableListOf()
    override suspend fun insertRepos(list: List<RepositoryModelEntity>) {
        database.addAll(list)
    }

    override fun getAllRepos(): List<RepositoryModelEntity> {
        return database
    }

    override suspend fun deleteAllRepos() {
        database.clear()
    }

    fun setByDefault(list: List<RepositoryModelEntity>) {
        database.addAll(list)
    }
}