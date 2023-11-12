package com.example.apnamart.data.room.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.apnamart.data.room.database.AppDatabase
import com.example.apnamart.data.room.enitities.RepositoryModelEntity
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test


class AppDaoTest {

    private lateinit var dao: AppDao
    private lateinit var appDatabase: AppDatabase

    @Before
    fun setup() {
        appDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        )
            .allowMainThreadQueries().build()
        dao = appDatabase.getAppDao()
    }

    @After
    fun tearDown() {
        appDatabase.close()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun insertEmptyList_Expected_EmptyList() = runTest {
        val list = listOf<RepositoryModelEntity>()

        dao.insertRepos(list)

        val result = dao.getAllRepos()

        assertThat(result.size).isEqualTo(0)

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun singleItemList_Expected_SingleItemList() = runTest {
        val list = listOf(
            RepositoryModelEntity(
                name = "xyz",
                login = "abc",
                description = "prq",
                score = 0f,
                watchers = 0,
                forks = 0,
                language = "abc"
            )
        )

        dao.insertRepos(list)

        val result = dao.getAllRepos()

        assertThat(result.size).isEqualTo(1)
        assertThat(result[0].login).isEqualTo("abc")
        assertThat(result[0].name).isEqualTo("xyz")
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun deleteItem_Expected_ListSize_Zero() = runTest {
        val list = listOf(
            RepositoryModelEntity(
                name = "xyz",
                login = "abc",
                description = "prq",
                score = 0f,
                watchers = 0,
                forks = 0,
                language = "abc"
            ),
            RepositoryModelEntity(
                name = "xayz",
                login = "abtc",
                description = "prvvq",
                score = 10f,
                watchers = 78,
                forks = 110,
                language = "abcdsds"
            )
        )

        dao.insertRepos(list)

        dao.deleteAllRepos()

        val result = dao.getAllRepos()

        assertThat(result.size).isEqualTo(0)

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun insertList_Validates_FirstIndex_Element() = runTest {
        val list = listOf(
            RepositoryModelEntity(
                name = "xyz",
                login = "abc",
                description = "prq",
                score = 0f,
                watchers = 0,
                forks = 0,
                language = "abc"
            ),
            RepositoryModelEntity(
                name = "xayz",
                login = "abtc",
                description = "prvvq",
                score = 10f,
                watchers = 78,
                forks = 110,
                language = "abcdsds"
            )
        )

        dao.insertRepos(list)

        val result = dao.getAllRepos()

        assertThat(result.size).isEqualTo(2)
        assertThat(result[1].forks).isEqualTo(110)

    }


}