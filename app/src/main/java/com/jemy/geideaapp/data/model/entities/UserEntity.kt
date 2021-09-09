package com.jemy.geideaapp.data.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "users")
data class UserEntity(
    @SerializedName("id")
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int? = 0,
    @SerializedName("email")
    @ColumnInfo(name = "email")
    val email: String? = "",
    @SerializedName("first_name")
    @ColumnInfo(name = "first_name")
    val firstName: String? = "",
    @SerializedName("last_name")
    @ColumnInfo(name = "last_name")
    val lastName: String? = "",
    @SerializedName("avatar")
    @ColumnInfo(name = "avatar")
    val avatar: String? = ""
)