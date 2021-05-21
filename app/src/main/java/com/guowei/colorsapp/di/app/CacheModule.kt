package com.guowei.colorsapp.di.app

import com.guowei.colorsapp.cache.SessionCache
import com.guowei.colorsapp.cache.InMemorySessionCache
import dagger.Binds
import dagger.Module

@Module
abstract class CacheModule {

    @AppScope
    @Binds
    abstract fun tokenCache(inMemoryTokenCache: InMemorySessionCache): SessionCache
}
