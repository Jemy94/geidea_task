package com.jemy.geideaapp.data.model.reposnose


import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("data")
    val `data`: Data? = Data(),
    @SerializedName("support")
    val support: Support? = Support()
) {
    data class Data(
        @SerializedName("id")
        val id: Int? = 0,
        @SerializedName("email")
        val email: String? = "",
        @SerializedName("first_name")
        val firstName: String? = "",
        @SerializedName("last_name")
        val lastName: String? = "",
        @SerializedName("avatar")
        val avatar: String? = ""
    )

    data class Support(
        @SerializedName("url")
        val url: String? = "",
        @SerializedName("text")
        val text: String? = ""
    )
}