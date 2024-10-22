package com.group1.notamonotako.coroutines.signup

import ApiService
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.group1.notamonotako.api.requests_responses.signup.RegisterRequests
import com.group1.notamonotako.api.requests_responses.signup.RegistrationResponses
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class SignUpViewModel(private val apiService: ApiService) : ViewModel() {

    // LiveData to hold the registration result
    private val _registrationResult = MutableLiveData<Result<RegistrationResponses>>()
    val registrationResult: LiveData<Result<RegistrationResponses>> = _registrationResult

    // Function to handle user registration
    fun registerUser(email: String, username: String, password: String) {
        viewModelScope.launch {
            try {
                val registrationRequest = RegisterRequests(email = email, username = username, password = password)
                val response = apiService.register(registrationRequest)

                if (response.isSuccessful) {
                    val registrationResponse = response.body()
                    if (registrationResponse != null) {
                        // Post value only if response body is not null
                        _registrationResult.postValue(Result.success(registrationResponse))
                    } else {
                        // Handle null response body
                        _registrationResult.postValue(Result.failure(Exception("Response body is null")))
                    }
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                    // Post error if response is not successful
                    _registrationResult.postValue(Result.failure(Exception(errorMessage)))
                }
            } catch (e: IOException) {
                // Handle network-related errors (e.g., request timeout)
                _registrationResult.postValue(Result.failure(e))
            } catch (e: HttpException) {
                // Handle network-related errors (e.g., request timeout)
                _registrationResult.postValue(Result.failure(e))
            }
        }
    }
}
