package com.jemy.geideaapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jemy.geideaapp.data.model.entities.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class UsersDatabase : RoomDatabase() {

    abstract fun dao(): UsersDao

    companion object {
        const val DATABASE_NAME = "users_db"
    }
}