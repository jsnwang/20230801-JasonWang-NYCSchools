package com.moo.nycschools.util

data class UiState<T>(
    var status: Status,
    var data: T? = null,
    var error: Throwable? = null
)

enum class Status {
    LOADING,
    SUCCESS,
    ERROR
}