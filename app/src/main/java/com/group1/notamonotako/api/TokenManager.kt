import android.content.Context
import android.content.SharedPreferences

object TokenManager {

    private const val PREFS_NAME = "app_prefs"
    private const val KEY_TOKEN = "auth_token"
    private var prefs: SharedPreferences? = null

    fun init(context: Context) {
        if (prefs == null) {
            prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        }
    }

    fun saveToken(token: String) {
        prefs?.edit()?.putString(KEY_TOKEN, token)?.apply()
    }

    fun getToken(): String? {
        return prefs?.getString(KEY_TOKEN, null)
    }

    fun clearToken() {
        prefs?.edit()?.remove(KEY_TOKEN)?.apply()
    }

    fun isLoggedIn(): Boolean {
        return getToken() != null
    }
}
