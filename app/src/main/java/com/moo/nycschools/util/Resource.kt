package com.moo.nycschools.util

//This is a resource class that I use in almost all my project. Taken from Philipp Lackner on Youtube.
sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T?): Resource<T>(data)
    class Error<T>(message: String, data: T? = null): Resource<T>(data, message)
}