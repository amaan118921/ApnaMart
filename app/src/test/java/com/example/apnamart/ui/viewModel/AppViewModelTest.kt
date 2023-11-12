package com.example.apnamart.ui.viewModel

import android.annotation.SuppressLint
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.apnamart.domain.usecases.FakeFetchReposUseCase
import com.example.apnamart.ui.helper.ResultState
import com.example.apnamart.ui.helper.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After


import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.MockitoAnnotations


class AppViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val query = "stars"

    @get:Rule
    var instantTaskExecutor = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        appFetchReposUseCase = FakeFetchReposUseCase()
        Dispatchers.setMain(testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private lateinit var appFetchReposUseCase: FakeFetchReposUseCase


    @SuppressLint("CheckResult")
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `ResultState Success returns Empty List`() = runTest {

        appFetchReposUseCase.failed(false)
        val viewModel = AppViewModel(appFetchReposUseCase, testDispatcher)

        viewModel.fetchRepos(query)
        testDispatcher.scheduler.advanceUntilIdle()
        val result = viewModel.getRepoLiveData().getOrAwaitValue()
        assertThat(result is ResultState.Success)
        assertThat(result?.data?.size).isEqualTo(0)

    }


    @SuppressLint("CheckResult")
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Result State Failed returns null`() = runTest {
        appFetchReposUseCase.failed(true)
        val viewModel = AppViewModel(appFetchReposUseCase, testDispatcher)

        viewModel.fetchRepos(query)
        testDispatcher.scheduler.advanceUntilIdle()
        val result = viewModel.getRepoLiveData().getOrAwaitValue()
        assertThat(result is ResultState.Error)
        assertThat(result?.data).isNull()

    }


}