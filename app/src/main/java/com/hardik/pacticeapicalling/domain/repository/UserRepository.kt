package com.hardik.pacticeapicalling.domain.repository

import android.content.Context
import com.hardik.pacticeapicalling.data.remote.dto.UserDto
import com.hardik.pacticeapicalling.domain.model.UserModel

interface UserRepository {
    suspend fun getUsers(): List<UserDto>

    suspend fun insertUser(appContext: Context, user: UserModel)//todo: insertUser in database
}