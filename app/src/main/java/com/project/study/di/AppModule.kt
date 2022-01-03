package com.project.study.di

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
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
    private val TAG = "AppModule"

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
        .addMigrations(MIGRATION_1_2)
        .addMigrations(MIGRATION_2_3)
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

    private val MIGRATION_1_2 = object : Migration(1, 3) {
        override fun migrate(database: SupportSQLiteDatabase) {
            Log.d(TAG, "migrate: 1 to 3 called")
            database.execSQL("ALTER TABLE PhotosTable ADD COLUMN created_at TEXT default 'null' NOT NULL")
            database.execSQL("ALTER TABLE PhotosTable ADD COLUMN updated_at TEXT default 'null' NOT NULL")
        }
    }

    private val MIGRATION_2_3 = object : Migration(2, 3) {
        override fun migrate(database: SupportSQLiteDatabase) {
            Log.d(TAG, "migrate: 2 to 3 called")
            database.execSQL("ALTER TABLE PhotosTable ADD COLUMN updated_at TEXT default 'null' NOT NULL")
        }
    }

}