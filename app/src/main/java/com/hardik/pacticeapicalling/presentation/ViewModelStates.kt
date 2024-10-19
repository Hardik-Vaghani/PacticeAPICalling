package com.hardik.pacticeapicalling.presentation

data class UserListState<T>(
    val isLoading : Boolean = false,
    val users : List<T> = emptyList(),
    val error : String = "",
    )
data class CreatePostState<T>(
    val isLoading: Boolean = false,
    val post: T? = null,
    val error: String = ""
)
