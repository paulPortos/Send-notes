import android.content.Context
import android.content.SharedPreferences
import android.util.Log

object TokenManager {
    private const val PREFS_NAME = "user_prefs"
    private const val TOKEN_KEY = "token"
    private lateinit var preferences: SharedPreferences

    fun init(context: Context) {
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun saveToken(token: String) {
        Log.d("TokenManager", "Saving token: $token")
        preferences.edit().putString(TOKEN_KEY, token).apply()
    }

    fun getToken(): String? {
        val token = preferences.getString(TOKEN_KEY, null)
        Log.d("TokenManager", "Retrieved token: $token")
        return token
    }

    fun clearToken() {
        preferences.edit().remove(TOKEN_KEY).apply()
    }

    fun isLoggedIn(): Boolean {
        val token = getToken()
        return !token.isNullOrEmpty()  // Checks if a token exists
    }
}