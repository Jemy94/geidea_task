package com.jemy.geideaapp.ui.fragments.userdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.jemy.geideaapp.data.repository.UserDetailsRepository
import com.jemy.geideaapp.data.repository.UsersRepository
import com.jemy.geideaapp.data.room.UsersDao
import com.jemy.geideaapp.utils.Resource
import com.jemy.geideaapp.utils.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel @Inject
constructor(
    private val repository: UserDetailsRepository
) : ViewModel() {

    fun getUserDetails(id: Int) = liveData(Dispatchers.IO) {
        emit(Resource(state = ResourceState.LOADING, data = null))
        try {
            val details = repository.getUser(id)
            emit(Resource(ResourceState.SUCCESS, data = details))
        } catch (e: Exception) {
            emit(
                Resource(
                    state = ResourceState.ERROR,
                    data = null,
                    message = e.message ?: "unknown error"
                )
            )
        } catch (e: HttpException) {
            emit(
                Resource(
                    state = ResourceState.ERROR,
                    data = null,
                    message = e.message ?: "unknown error"
                )
            )

        } catch (e: IOException) {
            emit(
                Resource(
                    state = ResourceState.ERROR,
                    data = null,
                    message = e.message ?: "unknown error"
                )
            )

        }
    }
}