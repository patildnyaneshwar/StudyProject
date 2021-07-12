package com.project.study.data.client

interface ResponseService {
    fun <D> success(data: D)
    fun loading(isLoading: Boolean)
    fun <E, D> error(error: E, data: D)
}