package com.guowei.colorsapp.cache

interface TokenCache {
    fun save(token: String)
    fun get(): String?
}