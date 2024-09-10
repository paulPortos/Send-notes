package com.group1.notamonotako.api.requests_responses.notes




data class Note(
    val id: Int,
    val title: String,
    val contents: String,
)

data class NoteRequest(
    val title: String,
    val contents: String,
    val public: Boolean,
    val to_public: Boolean
)