package vass.rickymorty.domain.repository

class ResultRM<out T>(
    val data: T? = null,
    val error: String? = null,
) {
    fun isSuccess(): Boolean = data != null

    fun isError(): Boolean = error != null

    companion object {
        fun <T> success(data: T): ResultRM<T> {
            return ResultRM(data = data)
        }

        fun error(message: String): ResultRM<Nothing> {
            return ResultRM(error = message)
        }
    }
}
