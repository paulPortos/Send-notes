package com.group1.notamonotako.api.requests_responses.admin

class postToAdmin(
    val title: String,
    val contents: String,
    val public: Boolean,
)

class responseToAdmin(
    val title: String,
    val contents: String,
    val public: Boolean,
)
