package com.group1.notamonotako.api.requests_responses.flashcards

data class GetFlashcards (
    val title: String ,
    val cards: List<String>,
)
data class PostFlashcards(
    val title: String,
    val cards: List<String>,
    val public: Boolean,
    val to_public: Boolean
)

data class FlashcardsResponse(
    val id: Int,
    val title: String,
    val cards: List<String>,
    val public: Boolean,
    val to_public: Boolean,
    val updated_at: String
)