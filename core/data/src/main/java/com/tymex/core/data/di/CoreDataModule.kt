package com.tymex.core.data.di


import com.tymex.core.data.HttpClientFactory
import org.koin.dsl.module

val coreDataModule = module {
    single {
        HttpClientFactory().build()
    }
}