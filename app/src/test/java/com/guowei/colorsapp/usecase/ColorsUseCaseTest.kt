package com.guowei.colorsapp.usecase

import com.guowei.colorsapp.cache.SessionCache
import com.guowei.colorsapp.networking.api.StorageApi
import com.guowei.colorsapp.networking.schema.StorageResponse
import io.mockk.*
import io.reactivex.Single
import io.reactivex.plugins.RxJavaPlugins
import org.junit.After
import org.junit.Before
import org.junit.Test

class ColorsUseCaseTest {
    private val storageApi = mockk<StorageApi>()
    private val sessionCache = mockk<SessionCache>()

    private lateinit var useCase: ColorsUseCase

    @Before
    fun setup() {
        RxJavaPlugins.reset()
        useCase = ColorsUseCase(storageApi, sessionCache)
    }

    @After
    fun tearDown() = RxJavaPlugins.reset()

    @Test
    fun getOrCreate_noStorageId_shouldCreate() {
        every { sessionCache.getStorageId() }.returns(null)
        every { storageApi.create(any()) }.returns(Single.just(StorageResponse("id", "color")))
        every { sessionCache.saveStorageId("id") } just Runs

        useCase.getOrCreate().test().assertValue("color")

        verify {
            sessionCache.saveStorageId("id")
            storageApi.get("id") wasNot Called
        }
    }

    @Test
    fun getOrCreate_hasStorageId_shouldGet() {
        every { sessionCache.getStorageId() }.returns("storageId")
        every { storageApi.get("storageId") }.returns(
            Single.just(
                StorageResponse(
                    "storageId",
                    "color"
                )
            )
        )

        useCase.getOrCreate().test().assertValue("color")

        verify {
            storageApi.get("storageId")
            storageApi.create(any()) wasNot Called
        }
    }

}