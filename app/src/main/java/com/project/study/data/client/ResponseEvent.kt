package com.project.study.data.client

class ResponseEvent<out T>(
    val status: Status,
    val data: T?,
    val message: String?
) {
    companion object {

        fun <T> success(data: T?): ResponseEvent<T> {
            return ResponseEvent(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): ResponseEvent<T> {
            return ResponseEvent(Status.ERROR, data, msg)
        }

//        fun <T> loading(data:T?): ResponseEvent<T>{
//            return ResponseEvent(Status.LOADING, data, null)
//        }

    }
}

enum class Status {
    SUCCESS,
    ERROR
//    LOADING
}