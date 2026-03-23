package com.yazhamit.izmirharita

import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FcmSender {
    companion object {
        private const val BASE_URL = "https://fcm.googleapis.com/"
        private const val PROJECT_ID = "izmirharita-d0d08"

        private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        private val fcmApiService = retrofit.create(FcmApiService::class.java)

        suspend fun sendNotificationToTopic(context: Context, topic: String, title: String, body: String) {
            val token = FcmAccessTokenHelper.getAccessToken(context)
            if (token != null) {
                val messageContent = FcmMessageContent(
                    topic = topic,
                    notification = FcmNotification(title, body)
                )
                val messageBody = FcmMessageBody(messageContent)

                withContext(Dispatchers.IO) {
                    try {
                        val response = fcmApiService.sendMessage(PROJECT_ID, "Bearer $token", messageBody).execute()
                        if (response.isSuccessful) {
                            Log.d("FcmSender", "Topic Notification Sent Successfully")
                        } else {
                            Log.e("FcmSender", "Topic Notification Failed: ${response.errorBody()?.string()}")
                        }
                    } catch (e: Exception) {
                        Log.e("FcmSender", "Exception: ${e.message}")
                    }
                }
            } else {
                Log.e("FcmSender", "Failed to get access token")
            }
        }

        suspend fun sendNotificationToToken(context: Context, targetToken: String, title: String, body: String) {
            val accessToken = FcmAccessTokenHelper.getAccessToken(context)
            if (accessToken != null) {
                val messageContent = FcmMessageContent(
                    token = targetToken,
                    notification = FcmNotification(title, body)
                )
                val messageBody = FcmMessageBody(messageContent)

                withContext(Dispatchers.IO) {
                    try {
                        val response = fcmApiService.sendMessage(PROJECT_ID, "Bearer $accessToken", messageBody).execute()
                        if (response.isSuccessful) {
                            Log.d("FcmSender", "Token Notification Sent Successfully")
                        } else {
                            Log.e("FcmSender", "Token Notification Failed: ${response.errorBody()?.string()}")
                        }
                    } catch (e: Exception) {
                        Log.e("FcmSender", "Exception: ${e.message}")
                    }
                }
            } else {
                Log.e("FcmSender", "Failed to get access token")
            }
        }
    }
}
