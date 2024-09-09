package com.group1.notamonotako.api.requests_responses.notes

import com.google.gson.annotations.SerializedName

data class GetNotesResponse(
    @SerializedName("title")
    val title: String,
    @SerializedName("contents")
    val contents: String
)
