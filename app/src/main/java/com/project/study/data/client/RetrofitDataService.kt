package com.project.study.data.client

import com.project.study.data.model.PhotosDataClass
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitDataService {

    @GET("photos")
    fun getPhotos(
        @Query("client_id") accessKey: String,
        @Query("page") pageNo: Int
    ): Call<List<PhotosDataClass>>

}