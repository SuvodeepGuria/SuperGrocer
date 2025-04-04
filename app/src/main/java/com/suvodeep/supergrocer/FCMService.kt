//package com.suvodeep.supergrocer
//
//import okhttp3.MediaType.Companion.toMediaTypeOrNull
//import okhttp3.OkHttpClient
//import okhttp3.Request
//import okhttp3.RequestBody
//import okhttp3.RequestBody.Companion.toRequestBody
//
//object FCMService {
//    private const val SERVER_KEY = "BBj43MkSbLwlFniBxP633XTwFDGXsf-E0iIBsYIOKFWEI3r29jLGP6xJrTDtFlNul6Cf4poqKwmQwVSM6FthSIk"
//    private const val FCM_URL = "https://fcm.googleapis.com/fcm/send"
//
//    fun sendNotification(topic: String, title: String, body: String) {
//        val client = OkHttpClient()
//        val json = """
//            {
//                "to": "/topics/$topic",
//                "notification": {
//                    "title": "$title",
//                    "body": "$body"
//                }
//            }
//        """.trimIndent()
//
//        val requestBody = json.toRequestBody("application/json".toMediaTypeOrNull())
//        val request = Request.Builder()
//            .url(FCM_URL)
//            .post(requestBody)
//            .addHeader("Authorization", "key=$SERVER_KEY")
//            .addHeader("Content-Type", "application/json")
//            .build()
//
//        val response = client.newCall(request).execute()
//        println(response.body?.string()) // Log response
//    }
//}
