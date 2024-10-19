package com.hardik.pacticeapicalling.data.remote.api

import com.hardik.pacticeapicalling.data.remote.dto.UserDto
import retrofit2.http.GET

interface ApiInterface {

    @GET("/users")
    suspend fun getUsers(): List<UserDto>

    /**
     * Without suspend keyword,it is necessary to use Call<Post>,
     * With suspend keyword, It isn't necessary to use Call<Post>, You have two option Call<Post> or Post.
     * */


}