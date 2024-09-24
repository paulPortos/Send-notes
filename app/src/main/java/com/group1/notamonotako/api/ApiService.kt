import com.group1.notamonotako.api.requests_responses.ChangePass.ChangePasswordRequest
import com.group1.notamonotako.api.requests_responses.ChangePass.ChangePasswordResponse
import com.group1.notamonotako.api.requests_responses.admin.postToAdmin
import com.group1.notamonotako.api.requests_responses.admin.responseToAdmin
import com.group1.notamonotako.api.requests_responses.flashcards.GetFlashcards
import com.group1.notamonotako.api.requests_responses.notes.Note
import com.group1.notamonotako.api.requests_responses.notes.NoteRequest
import com.group1.notamonotako.api.requests_responses.notes.PostnotesRequest
import com.group1.notamonotako.api.requests_responses.notes.PostnotesResponse
import com.group1.notamonotako.api.requests_responses.notes.UpdateNotes
import com.group1.notamonotako.api.requests_responses.signin.Login
import com.group1.notamonotako.api.requests_responses.signin.LoginResponse
import com.group1.notamonotako.api.requests_responses.signup.RegisterRequests
import com.group1.notamonotako.api.requests_responses.signup.RegistrationResponses
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.DELETE
import retrofit2.http.Path

interface ApiService {

    // User registration
    @POST("register")
    suspend fun register(@Body request: RegisterRequests): Response<RegistrationResponses>

    // User login
    @POST("login")
    suspend fun login(@Body request: Login): Response<LoginResponse>

    // Logout
    @DELETE("logout")
    fun logout(@Header("Authorization") token: String): Call<Void>

    @POST("admin")
    suspend fun toAdmin(@Body request: postToAdmin): Response<responseToAdmin>

    // Get notes (with token-based auth)
    @GET("notes")
    fun getNotes(): Call<List<Note>>

    // Create a new note
    @POST("notes")
    suspend fun createNote(@Body response: PostnotesRequest): Response<PostnotesResponse>

    // Update a note
    @PUT("notes/{id}")
    suspend fun updateNote(
        @Header("Authorization") token: String,
        @Path("id") noteId: Int,
        @Body noteRequest: UpdateNotes // Add this to pass the updated note content
    ): Response<Void>

    @GET("flashcards")
    suspend fun getFlashcards(): Response<List<GetFlashcards>>

    // Delete a note
    @DELETE("notes/{id}")
    suspend fun deleteNote(
        @Header("Authorization") token: String,
        @Path("id") noteId: Int
    ): Response<Void>

    @PUT("ChangePass")
    suspend fun PassChange(@Body changePasswordRequest: ChangePasswordRequest): Response<ChangePasswordResponse>
}