package com.group1.notamonotako.api.requests_responses.notification

data class GetNotification(
    val notification_type: String,
    val email: String,
    val message: String
)
