package vass.rickymorty.data.remote.network

import retrofit2.Response
import javax.inject.Inject

class ApiResponseHandler @Inject constructor() {
    suspend fun <T : Any> handleApiCall(call: suspend () -> Response<T>): ApiResponse<T> {
        return try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    ApiResponse.Success(body)
                } else {
                    ApiResponse.EmptyResponse
                }
            } else {
                handleErrorResponse(response)
            }
        } catch (e: Exception) {
            ApiResponse.Error(e)
        }
    }

    private fun <T : Any> handleErrorResponse(response: Response<T>): ApiResponse<T> {
        return when (response.code()) {
            404 -> ApiResponse.ResourceNotFound
            // Manage another codes
            else -> ApiResponse.ErrorResponse(response.message())
        }
    }
}
