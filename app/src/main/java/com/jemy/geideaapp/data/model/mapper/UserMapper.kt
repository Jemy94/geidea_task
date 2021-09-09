package com.jemy.geideaapp.data.model.mapper

import com.jemy.geideaapp.data.model.entities.UserEntity
import com.jemy.geideaapp.data.model.reposnose.UsersResponse.User

fun List<User>.mapToEntity(): List<UserEntity> =
    map { user ->
        UserEntity(
            id = user.id,
            email = user.email,
            firstName = user.firstName,
            lastName = user.lastName,
            avatar = user.avatar,
        )
    }