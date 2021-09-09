package com.jemy.geideaapp.data.network

import com.jemy.geideaapp.data.model.reposnose.UserResponse
import com.jemy.geideaapp.data.model.reposnose.UsersResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET(Endpoints.USERS)
    suspend fun getUsers(
        @Query("per_page") perPage: Int = 20
    ): UsersResponse

    @GET(Endpoints.USER)
    suspend fun getUser(
        @Path("id") userId: Int
    ): UserResponse
}