package com.jemy.geideaapp.data.model.reposnose


import com.google.gson.annotations.SerializedName

data class UsersResponse(
    @SerializedName("page")
    val page: Int? = 0,
    @SerializedName("per_page")
    val perPage: Int? = 0,
    @SerializedName("total")
    val total: Int? = 0,
    @SerializedName("total_pages")
    val totalPages: Int? = 0,
    @SerializedName("data")
    val `data`: List<User>? = listOf(),
    @SerializedName("support")
    val support: Support? = Support()
) {
    data class User(
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