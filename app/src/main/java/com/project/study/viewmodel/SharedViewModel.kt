package com.project.study.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.study.data.database.tables.PhotosTable
import com.project.study.data.repository.PhotosRepository
import com.project.study.data.client.ResponseEvent
import com.project.study.data.client.ResponseService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val photosRepository: PhotosRepository
) : ViewModel(), ResponseService {
    private val TAG = "SharedViewModel"

    val loading: ObservableField<Boolean> by lazy { ObservableField<Boolean>(false) }
    private val _photosData = MutableLiveData<ResponseEvent<List<PhotosTable>>>()
    private val photosList: ArrayList<PhotosTable> = ArrayList()
    var pageNo = 1

//    init {
//        if (_photosData.value == null)
//            insertPhotos(pageNo)
//    }

    fun insertPhotos(key: String, pageNo: Int) = viewModelScope.launch {
        photosRepository.getPhotos(
            key,
            pageNo,
            this@SharedViewModel
        )
    }

    fun getPhotos(): LiveData<ResponseEvent<List<PhotosTable>>> {
        viewModelScope.launch {
            photosRepository.getAllPhotos().catch {
                _photosData.postValue(ResponseEvent.error("Something went wrong!", null))
            }.collect { list ->
                photosList.addAll(list)
                if (photosList.isEmpty()) {
                    loading.set(true)
                } else {
                    loading.set(false)
                    _photosData.postValue(ResponseEvent.success(photosList.distinct()))
                }
            }
        }
        return _photosData
    }

    override fun <D> success(data: D) {
        viewModelScope.launch {
            photosRepository.deleteAllPhotos()
            photosRepository.insertAllPhotos(data)
        }
    }

    override fun loading(isLoading: Boolean) {
        loading.set(isLoading)
    }

    override fun <E, D> error(error: E, data: D) {
        viewModelScope.launch {
            _photosData.postValue(ResponseEvent.error(error as String, null))
        }
    }

}