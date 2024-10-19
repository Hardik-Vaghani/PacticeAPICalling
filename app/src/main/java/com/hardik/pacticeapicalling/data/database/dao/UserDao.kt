package com.hardik.pacticeapicalling.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.hardik.pacticeapicalling.domain.model.UserModel

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserModel) //Todo: UserModel is define in domain/model/UserModel or data/database/entities/UserModel(UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<UserModel>) // This method handles the list

    @Update
    suspend fun updateUser(user: UserModel)

    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserById(userId: Int): UserModel?

    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<UserModel>

    @Query("DELETE FROM users WHERE id = :userId")
    suspend fun deleteUserById(userId: Int)
}
