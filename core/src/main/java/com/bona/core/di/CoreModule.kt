package com.bona.core.di

import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import androidx.room.Room
import com.bona.core.BuildConfig
import com.bona.core.data.local.ItemUserDatabase
import com.bona.core.data.local.LocalDataSource
import com.bona.core.data.remote.RemoteDataSource
import com.bona.core.data.remote.retrofit.ApiService
import com.bona.core.data.remote.ItemUserQuery
import com.bona.core.utils.ItemUserRepository
import okhttp3.CertificatePinner
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
        val passphrase: ByteArray = SQLiteDatabase.getBytes("bonabona".toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
            androidContext(),
            ItemUserDatabase::class.java,
            "Users.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
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
        val hostname = "api.github.com"
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/jFaeVpA8UQuidlJkkpIdq3MPwD0m8XbuCRbJlezysBE=")
            .add(hostname, "sha256/lmo8/KPXoMsxI+J9hY+ibNm2r0IYChmOsF9BxD74PVc=")
            .build()
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
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
