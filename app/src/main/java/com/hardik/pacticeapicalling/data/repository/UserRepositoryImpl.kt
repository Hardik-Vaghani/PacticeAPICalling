package com.hardik.pacticeapicalling.data.repository

import android.content.Context
import android.util.Log
import com.hardik.pacticeapicalling.common.Constants.BASE_TAG
import com.hardik.pacticeapicalling.data.database.AppDatabase
import com.hardik.pacticeapicalling.data.database.dao.UserDao
import com.hardik.pacticeapicalling.data.remote.api.ApiInterface
import com.hardik.pacticeapicalling.data.remote.dto.UserDto
import com.hardik.pacticeapicalling.data.remote.dto.toUserModel
import com.hardik.pacticeapicalling.domain.model.UserModel
import com.hardik.pacticeapicalling.domain.model.toUserDto
import com.hardik.pacticeapicalling.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class UserRepositoryImpl(private val appContext : Context, private val apiInterface: ApiInterface): UserRepository {
    private val TAG = BASE_TAG + UserRepositoryImpl::class.java.simpleName

    override suspend fun getUsers(): List<UserDto> {
//        return apiInterface.getUsers()

        // Check if the database has users
        val usersFromDb = AppDatabase.getDatabase(appContext).userDao().getAllUsers()
        return if (usersFromDb.isNotEmpty()) {
            Log.d(TAG, "getUsers: From the database")
            // If there are users in the database, return them
            usersFromDb.map { it.toUserDto() }
        } else {
            Log.d(TAG, "getUsers: calling api for get user data")
            // If the database is empty, fetch from API
            val usersFromApi = apiInterface.getUsers()
            if (usersFromApi.isNotEmpty()) {
                Log.d(TAG, "getUsers: insert data in user table")

                // Store the fetched users in the database
                AppDatabase.getDatabase(appContext).userDao().insertUsers(usersFromApi.map { it.toUserModel() })
            }
            // Return users from API
            usersFromApi
        }

    }

    override suspend fun insertUser(appContext: Context, user: UserModel) {  AppDatabase.getDatabase(appContext).userDao().insertUser(user) }
}