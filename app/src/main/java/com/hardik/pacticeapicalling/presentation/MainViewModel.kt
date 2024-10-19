package com.hardik.pacticeapicalling.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hardik.pacticeapicalling.common.Resource
import com.hardik.pacticeapicalling.domain.model.UserModel
import com.hardik.pacticeapicalling.domain.use_case.GetUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainViewModel(private val getUserUseCase: GetUserUseCase) : ViewModel() {
    private val _state = MutableLiveData<UserListState<UserModel>>()
    val state: LiveData<UserListState<UserModel>> = _state

    private val _state1 = MutableStateFlow<UserListState<UserModel>>(UserListState(isLoading = true))
    val state1: StateFlow<UserListState<UserModel>> = _state1

    init {
        getUsers()
    }

    private fun getUsers() {
        getUserUseCase().onEach { result: Resource<List<UserModel>> ->
            when(result){
                is Resource.Success -> { _state.value = UserListState(users = result.data ?: emptyList()) }
                is Resource.Error -> { _state.value = UserListState(error = result.message ?: "An unexpected error occurred") }
                is Resource.Loading -> { _state.value = UserListState(isLoading = true) }
            }

        }.launchIn(viewModelScope)
    }

    // Function to fetch users using flow
    fun fetchUsers() {
        viewModelScope.launch {
            getUserUseCase().onEach { result: Resource<List<UserModel>> ->
                when(result){
                    is Resource.Success -> { _state1.value = UserListState(users = result.data ?: emptyList()) }
                    is Resource.Error -> { _state1.value = UserListState(error = result.message ?: "An unexpected error occurred") }
                    is Resource.Loading -> { _state1.value = UserListState(isLoading = true) }
                }

            }.launchIn(viewModelScope)
        }
    }
}

//class MainViewModelFactory: ViewModelProvider.Factory{
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return MainViewModel() as T
//    }
//}

// use like this or MainViewModelFactoryHelper class
