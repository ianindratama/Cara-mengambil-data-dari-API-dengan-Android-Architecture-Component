package com.ianindratama.searchgithubuserapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    private val searchRepository = SearchRepository.getInstance()

    private val _listOfUser = MutableLiveData<List<GithubUserData>?>()
    val listOfUser: LiveData<List<GithubUserData>?> = _listOfUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage =  MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    fun findSearchUser(searchInput: String){

        viewModelScope.launch(Dispatchers.IO) {

            searchRepository.findSearchUser(searchInput).asFlow().collect{
                when (it){
                    is Result.Loading -> {
                        _isLoading.postValue(true)
                    }
                    is Result.Success -> {
                        _isLoading.postValue(false)
                        _listOfUser.postValue(it.data?.items)
                    }
                    is Result.Error -> {
                        _isLoading.postValue(false)
                        _errorMessage.postValue(it.error)
                    }
                }
            }

        }
    }

}