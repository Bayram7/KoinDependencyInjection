package com.example.android.devbyteviewer.di

import android.content.Context
import androidx.room.Room
import com.example.android.devbyteviewer.database.VideosDatabase
import com.example.android.devbyteviewer.network.DevbyteService
import com.example.android.devbyteviewer.repository.VideosRepository
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


val netModule = module {
    fun provideMoshi(): Moshi {
        return  Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
            }
    fun provideRetrofit(moshi: Moshi): Retrofit {
        return Retrofit.Builder()
                .baseUrl("https://devbytes.udacity.com/")
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
    }

    single { provideMoshi() }
    single { provideRetrofit(get()) }
}

val apiModule = module {
    fun provideUserApi(retrofit: Retrofit): DevbyteService {
        return retrofit.create(DevbyteService::class.java)
    }

    single { provideUserApi(get()) }
}

val databaseModule = module {
    fun getDatabase(context: Context): VideosDatabase {
        return Room.databaseBuilder(context.applicationContext,
                    VideosDatabase::class.java,
                    "videos").build()
        }

    single { getDatabase(get()) }

}

val repositoryModule = module {
//        single<VideosDatabase> { getDatabase(get()) }

    fun provideVideosRepository(api: DevbyteService, database: VideosDatabase): VideosRepository {
        return VideosRepository(database,api)
    }
    single { provideVideosRepository(get(),get() ) }
}

