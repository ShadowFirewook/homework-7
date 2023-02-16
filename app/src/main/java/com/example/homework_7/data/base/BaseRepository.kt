package com.example.homework_7.data.base

import com.example.homework_7.domain.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException

abstract class BaseRepository {

    protected fun<T> doRequest(request: suspend () -> T) = flow {
        emit(Resource.Loading())
        delay(1000)
        try {
            val data = request()
            emit(Resource.Success(data))
        } catch (ioException: IOException) {
            emit(Resource.Error(ioException.localizedMessage ?: "Unknown error"))
        }
    }.flowOn(Dispatchers.IO)

}