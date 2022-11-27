package com.example.data.util


import com.example.core.CallException
import com.example.core.ActionResult
import retrofit2.Response

suspend fun <R> makeApiCall(
    call: suspend () -> ActionResult<R>,
    errorMessage: Int = 4567
) = try {
    call()
} catch (e: Exception) {
    ActionResult.Error(CallException<Nothing>(errorMessage))
}

fun <Model> analyzeResponse(
    response: Response<Model>
): ActionResult<Model> {
    return when (response.code()) {
        200 -> {
            val responseBody = response.body()
            responseBody?.let {
                ActionResult.Success(it)
            } ?: ActionResult.Error(CallException<Nothing>(response.code()))
        }
        else -> {
            ActionResult.Error(CallException(response.code()))
        }
    }
}
