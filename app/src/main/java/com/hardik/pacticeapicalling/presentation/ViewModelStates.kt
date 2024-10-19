package com.hardik.pacticeapicalling.presentation

data class UserListState<T>(
    val isLoading : Boolean = false,
    val users : List<T> = emptyList(),
    val error : String = "",
    )
