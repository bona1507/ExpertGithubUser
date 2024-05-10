package com.bona.core.di

import androidx.room.Room
import com.bona.core.BuildConfig
import com.bona.core.data.local.ItemUserDatabase
import com.bona.core.data.local.LocalDataSource
import com.bona.core.data.remote.RemoteDataSource
import com.bona.core.data.remote.retrofit.ApiService
import com.bona.core.data.remote.ItemUserQuery
import com.bona.core.utils.ItemUserRepository
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<ItemUserDatabase>().itemUserDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            ItemUserDatabase::class.java,
            "Users.db"
        ).fallbackToDestructiveMigration().build()
    }
}

val networkModule = module {
    single {
        val authInterceptor = Interceptor { chain ->
            val req = chain.request()
            val requestHeaders = req.newBuilder()
                .header("Authorization", BuildConfig.API_KEY)
                .build()
            chain.proceed(requestHeaders)
        }

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }

        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
            .create(ApiService::class.java)
    }
}


val repositoryModule = module {
    single<LocalDataSource> { LocalDataSource(get()) }
    single<RemoteDataSource> { RemoteDataSource(get()) }
    single<ItemUserRepository> { ItemUserQuery(get(), get()) }
}
