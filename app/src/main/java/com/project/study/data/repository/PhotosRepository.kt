package com.project.study.data.repository

import android.util.Log
import com.project.study.data.client.RetrofitDataService
import com.project.study.data.database.tables.PhotosTable
import com.project.study.data.model.PhotosDataClass
import com.project.study.data.client.ResponseService
import com.project.study.utils.objectToString
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class PhotosRepository @Inject constructor(
    private val retrofitDataService: RetrofitDataService
) {
    private val TAG = "PhotosRepository"

    fun getPhotos(accessKey: String, pageNo: Int, responseService: ResponseService) {
        responseService.loading(true)
        retrofitDataService.getPhotos(accessKey, pageNo)
            .enqueue(object : Callback<List<PhotosDataClass>> {
                override fun onResponse(
                    call: Call<List<PhotosDataClass>>,
                    response: Response<List<PhotosDataClass>>
                ) {
                    Log.d(TAG, "onResponse: $response")
                    responseService.loading(false)
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            val photosList: MutableList<PhotosTable> = ArrayList()
                            for (photos in response.body()!!.iterator()) {
                                val urls = photos.urls.objectToString()
                                val categories = photos.categories.objectToString()
                                photosList.add(
                                    PhotosTable(
                                        photos.id,
                                        photos.created_at,
                                        photos.updated_at,
                                        categories,
                                        photos.likes,
                                        urls
                                    )
                                )
                            }

                            responseService.success(photosList)
                        } else {
                            responseService.error("Photos response is null", null)
                        }
                    } else {
                        responseService.error(response.errorBody().toString(), null)
                    }
                }

                override fun onFailure(call: Call<List<PhotosDataClass>>, t: Throwable) {
                    responseService.loading(false)
                    responseService.error(t.toString(), null)
                }
            })
    }
}