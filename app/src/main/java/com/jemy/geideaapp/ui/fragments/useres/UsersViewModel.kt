package com.jemy.geideaapp.ui.fragments.useres

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.jemy.geideaapp.data.model.mapper.mapToEntity
import com.jemy.geideaapp.data.repository.UsersRepository
import com.jemy.geideaapp.data.room.UsersDao
import com.jemy.geideaapp.utils.Resource
import com.jemy.geideaapp.utils.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject
constructor(
    private val repository: UsersRepository,
    private val dao: UsersDao
) : ViewModel() {

    fun getUsers() = liveData(Dispatchers.IO) {
        delay(500)
        emit(Resource(ResourceState.LOADING, data = null))
        try {
            val networkList = repository.getUsers()
            val listEntity = networkList.data?.mapToEntity()
            listEntity?.let { dao.insertUser(it) }
            emit(Resource(ResourceState.SUCCESS, data = listEntity))
        } catch (exception: Exception) {
            if (exception.message == "Network is not available") {
                emit(
                    Resource(
                        ResourceState.SUCCESS,
                        data = dao.getUsers()
                    )
                )
            } else {
                emit(
                    Resource(
                        ResourceState.ERROR,
                        data = null,
                        message = exception.message ?: "Unknown error"
                    )
                )
            }

        } catch (exception: IOException) {
            Resource(
                ResourceState.ERROR,
                data = null,
                message = exception.message ?: "Unknown error"
            )
        }

    }
}