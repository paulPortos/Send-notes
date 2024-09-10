import com.group1.notamonotako.api.requests_responses.notes.Note
import com.group1.notamonotako.api.requests_responses.notes.NoteRequest
import com.group1.notamonotako.api.requests_responses.signin.Login
import com.group1.notamonotako.api.requests_responses.signin.LoginResponse
import com.group1.notamonotako.api.requests_responses.signup.RegisterRequests
import com.group1.notamonotako.api.requests_responses.signup.RegistrationResponses
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.DELETE

interface ApiService {

    // User registration
    @POST("register")
    fun register(@Body registrationRequest: RegisterRequests): Call<RegistrationResponses>

    // User login
    @POST("login")
    fun login(@Body loginRequest: Login): Call<LoginResponse>

    // Logout
    @POST("logout")
    fun logout(@Header("Authorization") token: String): Call<Void>

    // Get notes (with token-based auth)
    @GET("notes")
    fun getNotes(): Call<List<Note>>

    // Create a new note
    @POST("notes")
    fun createNote(@Body noteRequest: NoteRequest): Call<Note>

    // Update a note
    @PUT("notes/{id}")
    fun updateNote(@Body noteRequest: NoteRequest): Call<Note>

    // Delete a note
    @DELETE("notes/{id}")
    fun deleteNote(@Body noteRequest: NoteRequest): Call<Void>
    abstract fun signUpUser(registrationRequest: RegisterRequests): Any
}