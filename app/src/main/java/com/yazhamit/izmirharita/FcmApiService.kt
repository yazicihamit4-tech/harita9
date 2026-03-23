package com.yazhamit.izmirharita

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface FcmApiService {
    @POST("v1/projects/{projectId}/messages:send")
    fun sendMessage(
        @Path("projectId") projectId: String,
        @Header("Authorization") authorization: String,
        @Body message: FcmMessageBody
    ): Call<ResponseBody>
}

data class FcmMessageBody(
    val message: FcmMessageContent
)

data class FcmMessageContent(
    val token: String? = null,
    val topic: String? = null,
    val notification: FcmNotification
)

data class FcmNotification(
    val title: String,
    val body: String
)
