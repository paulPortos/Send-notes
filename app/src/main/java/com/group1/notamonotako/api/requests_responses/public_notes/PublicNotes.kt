package com.group1.notamonotako.api.requests_responses.public_notes

data class getPublicNotes(
    val id: Int,
    val title: String,
    val creator_username: String,
    val creator_email: String,
    val contents: String,
    val public: Boolean,
    val updated_at : String
)