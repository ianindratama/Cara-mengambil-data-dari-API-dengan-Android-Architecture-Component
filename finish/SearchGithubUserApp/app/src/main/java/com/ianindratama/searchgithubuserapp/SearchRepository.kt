package com.ianindratama.searchgithubuserapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData


class SearchRepository {

    fun findSearchUser(searchInput: String): LiveData<Result<SearchResponse?>> = liveData {

        emit(Result.Loading)
        val returnValue = MutableLiveData<Result<SearchResponse?>>()
        val response = RetrofitInstance.API_OBJECT.getSearchData(searchInput)

        returnValue.value = if (response.isSuccessful){
            Result.Success(response.body())
        }else{
            Result.Error("Telah terjadi error, mohon coba lagi nanti")
        }

        emitSource(returnValue)

    }

    companion object{

        @Volatile
        private var instance: SearchRepository? = null

        fun getInstance(): SearchRepository {
            return instance ?: synchronized(this) {
                if (instance == null){
                    instance = SearchRepository()
                }
                return instance as SearchRepository
            }
        }

    }

}