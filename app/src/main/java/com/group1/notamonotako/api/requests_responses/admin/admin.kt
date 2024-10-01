package com.group1.notamonotako.api.requests_responses.admin

import com.google.gson.annotations.SerializedName

class postToAdmin(
    val title: String,
    @SerializedName("creator_username")
    val creatorUsername: String,
    @SerializedName("creator_email")
    val creatorEmail: String,
    val contents: String,
    val public: Boolean,
)

class responseToAdmin(
    val title: String,
    val creator_username: String,
    val creator_email: String,
    val contents: String,
    val public: Boolean,
)
