package com.group1.notamonotako.api.requests_responses.comments

data class Comments(
    val username: String,
    val message: String
)

data class CommentPostRequest(
    val notes_id: Int,
    val username: String,
    val comment : String,


)

data class CommentPostResponse(
    val user_id: Int,
    val username: String,
    val comment: String,
    val id :Int

)


