package com.jemy.geideaapp.di

import android.content.Context
import androidx.room.Room
import com.jemy.geideaapp.data.room.UsersDao
import com.jemy.geideaapp.data.room.UsersDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Singleton
    @Provides
    fun provideUsersDb(@ApplicationContext context: Context): UsersDatabase =
        Room.databaseBuilder(
            context,
            UsersDatabase::class.java,
            UsersDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun providePhonesDao(phonesDatabase: UsersDatabase): UsersDao =
        phonesDatabase.dao()


}