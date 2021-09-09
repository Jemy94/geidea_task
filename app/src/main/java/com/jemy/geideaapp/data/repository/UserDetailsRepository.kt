package com.jemy.geideaapp.data.repository

import com.jemy.geideaapp.data.network.Api
import javax.inject.Inject

class UserDetailsRepository @Inject constructor(
    private val api: Api
) {

    suspend fun getUser(id: Int) = api.getUser(id)
}