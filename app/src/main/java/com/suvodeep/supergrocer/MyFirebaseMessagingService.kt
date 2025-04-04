//package com.suvodeep.supergrocer
//
//import android.app.NotificationChannel
//import android.app.NotificationManager
//import android.app.PendingIntent
//import android.content.Context
//import android.content.Intent
//import android.os.Build
//import android.util.Log
//import androidx.core.app.NotificationCompat
//import com.google.firebase.messaging.FirebaseMessagingService
//import com.google.firebase.messaging.RemoteMessage
//
//class MyFirebaseMessagingService : FirebaseMessagingService() {
//    override fun onMessageReceived(remoteMessage: RemoteMessage) {
//        remoteMessage.notification?.let {
//            sendNotification(it.title ?: "Order Update", it.body ?: "Your order is placed!")
//        }
//    }
//
//    private fun sendNotification(title: String, messageBody: String) {
//        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        val channelId = "order_notifications"
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val channel = NotificationChannel(channelId, "Orders", NotificationManager.IMPORTANCE_HIGH)
//            notificationManager.createNotificationChannel(channel)
//        }
//
//        val notificationBuilder = NotificationCompat.Builder(this, channelId)
//            .setSmallIcon(android.R.drawable.ic_dialog_info)
//            .setContentTitle(title)
//            .setContentText(messageBody)
//            .setAutoCancel(true)
//
//        notificationManager.notify(0, notificationBuilder.build())
//    }
//}
