package com.project.study.data.database

import androidx.room.*
import com.project.study.data.database.tables.PhotosTable
import com.project.study.data.model.PhotosDataClass
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotosDao {

    @Query("SELECT * FROM PhotosTable")
    fun getAllPhotos(): Flow<List<PhotosTable>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPhotos(photos: MutableList<PhotosTable>)

    @Query("DELETE FROM PhotosTable")
    suspend fun deleteAllPhotos()
}