import com.group1.notamonotako.api.requests_responses.ChangePass.ChangePasswordRequest
import com.group1.notamonotako.api.requests_responses.ChangePass.ChangePasswordResponse
import com.group1.notamonotako.api.requests_responses.admin.PostToAdmin
import com.group1.notamonotako.api.requests_responses.admin.ResponseToAdmin
import com.group1.notamonotako.api.requests_responses.comments.CommentPostRequest
import com.group1.notamonotako.api.requests_responses.comments.CommentPostResponse
import com.group1.notamonotako.api.requests_responses.flashcards.FlashcardsResponse
import com.group1.notamonotako.api.requests_responses.flashcards.GetFlashcards
import com.group1.notamonotako.api.requests_responses.flashcards.PostFlashcards
import com.group1.notamonotako.api.requests_responses.flashcards.UpdateFlashcards
import com.group1.notamonotako.api.requests_responses.forgetPassword.ResetPasswordResponse
import com.group1.notamonotako.api.requests_responses.forgetPassword.forgot_Password
import com.group1.notamonotako.api.requests_responses.forgetPassword.forgot_PasswordResponse
import com.group1.notamonotako.api.requests_responses.forgetPassword.reset_Password
import com.group1.notamonotako.api.requests_responses.notes.Note
import com.group1.notamonotako.api.requests_responses.notes.NoteRequest
import com.group1.notamonotako.api.requests_responses.notes.PostnotesRequest
import com.group1.notamonotako.api.requests_responses.notes.PostnotesResponse
import com.group1.notamonotako.api.requests_responses.notes.UpdateNotes
import com.group1.notamonotako.api.requests_responses.notes.UpdateToPublicNotes
import com.group1.notamonotako.api.requests_responses.notification.GetNotification
import com.group1.notamonotako.api.requests_responses.notification.PostPendingNotification
import com.group1.notamonotako.api.requests_responses.public_notes.getPublicNotes
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
    suspend fun toAdmin(@Body request: PostToAdmin): Response<ResponseToAdmin>

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

    // Update a note "To_public{
    @PUT("notes/{id}")
    suspend fun updateNotesToPublic(
        @Header("Authorization") token: String,
        @Path("id") noteId: Int,
        @Body noteRequest: UpdateToPublicNotes // Add this to pass the updated note content
    ): Response<Void>

    @GET("flashcards")
    suspend fun getFlashcards(): Response<List<GetFlashcards>>

    @POST("flashcards")
    suspend fun postFlashcards(@Body request: PostFlashcards): Response<FlashcardsResponse>

    @PUT("flashcards/{id}")
    suspend fun updateFlashcards(
        @Header("Authorization") token: String,
        @Path("id") flashcardsID: Int,
        @Body flashcardRequest: UpdateFlashcards // Add this to pass the updated note content
    ): Response<Void>

    // Delete a note
    @DELETE("notes/{id}")
    suspend fun deleteNote(
        @Header("Authorization") token: String,
        @Path("id") noteId: Int
    ): Response<Void>
    // Delete a flashcard
    @DELETE("flashcards/{id}")
    suspend fun deleteFlashcards(
        @Header("Authorization") token: String,
        @Path("id") flashcardsID: Int
    ): Response<Void>

    @PUT("ChangePass")
    suspend fun PassChange(@Body changePasswordRequest: ChangePasswordRequest): Response<ChangePasswordResponse>

    @GET("public_notes")
    suspend fun getPublicNotes(): Response<List<getPublicNotes>>

    @POST("forgot")
    suspend fun forgotPassword(@Body request: forgot_Password): Response<forgot_PasswordResponse>

    @POST("reset")
    suspend fun resetPassword(@Body request: reset_Password): Response<ResetPasswordResponse>

    @GET("shownotif")
    suspend fun showNotification(): Response<List<GetNotification>>

    @POST("comments")
    suspend fun postComment(
        @Header("Authorization") token: String, // assuming you're using token-based authentication
        @Body commentData: CommentPostRequest
    ): Response<CommentPostResponse>

    @POST("notePending")
    suspend fun postNotePending(@Body request: PostPendingNotification): Response<GetNotification>
}
