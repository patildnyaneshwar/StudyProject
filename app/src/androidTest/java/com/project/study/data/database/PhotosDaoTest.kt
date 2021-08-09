package com.project.study.data.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.project.study.data.database.tables.PhotosTable
import com.project.study.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PhotosDaoTest {
    // executes each test task synchronously
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var photosDatabase: PhotosDatabase
    private lateinit var photosDao: PhotosDao

    // @Before is responsible to run the setUp() each time before each test task
    @Before
    fun setUp() {
        // Room.inMemoryDatabaseBuilder will store data in RAM
        // Once app destroy, data will also destroy
        photosDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            PhotosDatabase::class.java
        ).allowMainThreadQueries().build()
        photosDao = photosDatabase.photosDao()
    }

    // After completing all test task @After is responsible to remove some instances
    // teardown() is as same as destroy() from activity lifecycle
    @After
    fun teardown() {
        photosDatabase.close()
    }

    @Test
    fun insertAllPhotos() = runBlocking(Dispatchers.Main) {
        val photosList = ArrayList<PhotosTable>()
        val photos1 = PhotosTable(
            "1",
            System.currentTimeMillis().toString(),
            System.currentTimeMillis().toString(),
            "Fashion",
            100,
            "https://google.com"
        )
        val photos2 = photos1.copy(id = "2", likes = 200)
        val photos3 = photos1.copy(id = "3", likes = 300)
        photosList.add(photos1)
        photosList.add(photos2)
        photosList.add(photos3)

        photosDao.insertAllPhotos(photosList)

        // LiveData executes asynchronously, so to run it synchronously we use {@link LiveData.getOrAwaitValue()}
        val getAllPhotos = photosDao.getAllPhotos().asLiveData().getOrAwaitValue()

        assertThat(getAllPhotos).isEqualTo(photosList)
    }

    @Test
    fun deleteAllPhotos() = runBlocking {
        photosDao.deleteAllPhotos()
        // LiveData executes asynchronously, so to run it synchronously we use {@link LiveData.getOrAwaitValue()}
        val getAllPhotos = photosDao.getAllPhotos().asLiveData().getOrAwaitValue()

        assertThat(getAllPhotos).isEmpty()
    }
}