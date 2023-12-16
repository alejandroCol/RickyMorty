package vass.rickymorty.data.remote.network

sealed class ApiResponse<out T : Any> {
    data class Success<out T : Any>(val data: T) : ApiResponse<T>()
    data class ErrorResponse(val errorMessage: String) : ApiResponse<Nothing>()
    object ResourceNotFound : ApiResponse<Nothing>()
    object EmptyResponse : ApiResponse<Nothing>()
    data class Error(val exception: Exception) : ApiResponse<Nothing>()
}
