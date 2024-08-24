package com.group1.notamonotako.model

import com.google.gson.annotations.SerializedName

data class UserX(
    @SerializedName("created_at")
    val createdAt: String,
    val id: Int,
    @SerializedName("updated_at")
    val updatedAt: String,
    val username: String,
)
