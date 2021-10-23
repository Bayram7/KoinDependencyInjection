package com.example.android.devbyteviewer.di

import com.example.android.devbyteviewer.database.VideosDatabase
import com.example.android.devbyteviewer.repository.VideosRepository
import org.koin.dsl.module.module


val appModule = module {
        single { VideosDatabase() }
        single { VideosRepository(get()) }
    }