package com.yazhamit.izmirharita

import android.content.Context
import com.google.auth.oauth2.GoogleCredentials
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FcmAccessTokenHelper {
    companion object {
        suspend fun getAccessToken(context: Context): String? {
            return withContext(Dispatchers.IO) {
                try {
                    val stream = context.assets.open("service_account.json")
                    val credentials = GoogleCredentials.fromStream(stream)
                        .createScoped(listOf("https://www.googleapis.com/auth/firebase.messaging"))
                    credentials.refreshIfExpired()
                    credentials.accessToken.tokenValue
                } catch (e: Exception) {
                    e.printStackTrace()
                    null
                }
            }
        }
    }
}
