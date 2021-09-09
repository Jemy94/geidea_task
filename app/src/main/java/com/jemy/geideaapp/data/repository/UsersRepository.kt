package com.jemy.geideaapp.data.repository

import com.jemy.geideaapp.data.network.Api
import javax.inject.Inject

class UsersRepository @Inject constructor(
    private val api: Api
) {

    suspend fun getUsers() = api.getUsers()
}