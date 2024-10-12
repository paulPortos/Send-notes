package com.group1.notamonotako.api.requests_responses.notification

import com.google.gson.annotations.SerializedName

data class GetNotification(
    @SerializedName("notification_type")
    val notificationType: String,
    val email: String,
    val message: String
)
