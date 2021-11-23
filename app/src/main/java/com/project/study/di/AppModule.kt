package com.project.study.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.project.study.BuildConfig
import com.project.study.data.client.ResponseService
import com.project.study.data.client.RetrofitDataService
import com.project.study.data.database.PhotosDao
import com.project.study.data.database.PhotosDatabase
import com.project.study.data.repository.PhotosRepository
import com.project.study.utils.ConstantUrls
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

//    @Provides
//    @Singleton
//    fun provideApplicationContext(
//        application: Application
//    ): Context? = application.applicationContext

    @Provides
    @Singleton
    fun provideDatabase(
        application: Application
    ) = Room.databaseBuilder(application, PhotosDatabase::class.java, "photos_database")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun providesPhotoDao(db: PhotosDatabase) = db.photosDao()

    // Dagger doesn't support @Inject into kotlin objects, final/const, static & private field
    @Provides
    fun provideBaseUrl() = ConstantUrls.BASE_URL

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else {
        OkHttpClient
            .Builder()
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient, BASE_URL: String): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    @Provides
    @Singleton
    fun provideRetrofitDataService(retrofit: Retrofit): RetrofitDataService =
        retrofit.create(RetrofitDataService::class.java)

//    @Provides
//    @Singleton
//    fun provideResponseService(sharedViewModel: SharedViewModel): ResponseService = sharedViewModel

//    @Provides
//    @Singleton
//    fun providesPhotosRepository(
//        retrofitDataService: RetrofitDataService,
//        photosDao: PhotosDao
//    ) = PhotosRepository(retrofitDataService, photosDao)

}