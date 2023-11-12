package com.example.apnamart.domain.usecases

import android.annotation.SuppressLint
import com.example.apnamart.data.repositories.FakeAppRepositoryImpl
import com.example.apnamart.data.room.dao.FakeAppDao
import com.example.apnamart.data.room.enitities.RepositoryModelEntity
import com.example.apnamart.data.usecases.FetchReposUseCaseImpl
import com.example.apnamart.ui.helper.ResultState
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest


import org.junit.Before
import org.junit.Test

class FetchReposUseCaseImplTest {


    private lateinit var fakeAppRepository: FakeAppRepositoryImpl
    private lateinit var fakeAppDao: FakeAppDao

    @Before
    fun setUp() {
        fakeAppDao = FakeAppDao()
        fakeAppRepository = FakeAppRepositoryImpl()
    }

    @SuppressLint("CheckResult")
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `remote data fetch success_local data update success`() = runTest {
        fakeAppRepository.shouldBeSuccessful(true)
        fakeAppRepository.responseModelNull(false)
        val fetchReposUseCaseImpl = FetchReposUseCaseImpl(fakeAppRepository, fakeAppDao)

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

        fakeAppDao.setByDefault(list)

        val result = fetchReposUseCaseImpl("stars")

        assertThat(result is ResultState.Success)

        assertThat(result.data?.size).isEqualTo(0)

    }

    @SuppressLint("CheckResult")
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `remote data fetch unsuccessful_local data should persist`() = runTest {
        fakeAppRepository.shouldBeSuccessful(false)
        fakeAppRepository.responseModelNull(false)
        val fetchReposUseCaseImpl = FetchReposUseCaseImpl(fakeAppRepository, fakeAppDao)

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

        fakeAppDao.setByDefault(list)

        val result = fetchReposUseCaseImpl("stars")

        assertThat(result is ResultState.Error)

        assertThat(result.data?.size).isEqualTo(2)
        assertThat(result.data!![0].watchers).isEqualTo(0)
        assertThat(result.data!![1].watchers).isEqualTo(78)

    }


    @SuppressLint("CheckResult")
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `remote data fetch success_ but fetched data is null_ local data should persist`() =
        runTest {
            fakeAppRepository.shouldBeSuccessful(true)
            fakeAppRepository.responseModelNull(true)
            val fetchReposUseCaseImpl = FetchReposUseCaseImpl(fakeAppRepository, fakeAppDao)

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

            fakeAppDao.setByDefault(list)

            val result = fetchReposUseCaseImpl("stars")

            assertThat(result is ResultState.Success)

            assertThat(result.data?.size).isEqualTo(2)
            assertThat(result.data!![1].forks).isEqualTo(110)

        }

    @SuppressLint("CheckResult")
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `remote data fetch fails_throws exception_local data should persist`() =
        runTest {
            val fetchReposUseCaseImpl = FetchReposUseCaseImpl(fakeAppRepository, fakeAppDao)

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
            fakeAppRepository.setException(true)
            fakeAppDao.setByDefault(list)

            val result = fetchReposUseCaseImpl("stars")

            assertThat(result is ResultState.Error)

            assertThat(result.data?.size).isEqualTo(2)
            assertThat(result.data!![1].forks).isEqualTo(110)

        }


}