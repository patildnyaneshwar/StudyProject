package com.project.study.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.project.study.data.database.tables.PhotosTable

@Database(entities = [PhotosTable::class], version = 1, exportSchema = false)
abstract class PhotosDatabase : RoomDatabase() {
    abstract fun photosDao(): PhotosDao
}