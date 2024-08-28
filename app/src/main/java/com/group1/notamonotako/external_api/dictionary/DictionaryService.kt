package com.group1.notamonotako.external_api.dictionary

import com.group1.notamonotako.external_api.dictionary.requests_response.DictionaryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryService {
    @GET("{word}")
    suspend fun getWordDefinition(@Path("word") word: String): Response<List<DictionaryResponse>>
}
