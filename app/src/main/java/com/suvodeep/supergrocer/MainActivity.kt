package com.suvodeep.supergrocer

import android.Manifest
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.razorpay.Checkout
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import com.suvodeep.supergrocer.ui.theme.SuperGrocerTheme
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity(), PaymentResultWithDataListener {

    private val superGrocerViewModel: SuperGrocerViewModel by viewModels()
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private var hasNotificationPermission by mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Checkout.preload(this)
        enableEdgeToEdge()

        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            hasNotificationPermission = isGranted
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            hasNotificationPermission = ContextCompat.checkSelfPermission(
                this, Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED

            if (!hasNotificationPermission) {
                permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        } else {
            hasNotificationPermission = true
        }

        setContent {
            SuperGrocerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SuperGrocerApp(
                        modifier = Modifier.padding(innerPadding),
                        superGrocerViewModel = superGrocerViewModel
                    )
                }
            }
        }
    }

    fun initPayment() {
        val activity: Activity = this
        val co = Checkout()
        co.setKeyID("rzp_test_uUNApDaMZ0mQHg")
        try {
            val options = JSONObject()
            options.put("name", "SuperGrocer")
            options.put("description", "Grocery app")
            options.put("image", R.drawable.supergrocerapplogo)
            options.put("theme.color", "#00FB0A5C")
            options.put("currency", "INR")
            options.put("amount", superGrocerViewModel.grandTotal.value * 100)

            val retryObj = JSONObject()
            retryObj.put("enabled", true)
            retryObj.put("max_count", 4)
            options.put("retry", retryObj)

            co.open(activity, options)
        } catch (e: Exception) {
            Toast.makeText(activity, "Error in payment: ${e.message}", Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    override fun onPaymentSuccess(p0: String?, p1: PaymentData?) {
        superGrocerViewModel.cleanCart()
        Toast.makeText(this, "Payment Success!\nYour Order has been placed successfully", Toast.LENGTH_SHORT).show()
        val currentDateTime = SimpleDateFormat("dd/MM/yyyy\nhh:mm a", Locale.getDefault()).format(Date())

        val groupedCart = superGrocerViewModel.cartItem.value
            .groupBy { it.itemName }
            .map { (name, itemList) ->
                val quantityCount = itemList.size
                val imageUrl = itemList.first().imageResourceId
                hashMapOf(
                    "name" to name,
                    "quantity" to "$quantityCount",
                    "imageUrl" to imageUrl
                )
            }

        val order = hashMapOf(
            "items" to groupedCart,
            "paymentMethod" to "Online",
            "totalPaid" to superGrocerViewModel.grandTotal.value,
            "address" to superGrocerViewModel.selectedAddress.value,
            "time&Date" to currentDateTime
        )

        superGrocerViewModel.yourOrder(order)

        if (hasNotificationPermission) {
            showNotification()
        }
    }

    override fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) {
        // Handle failure
    }

    private fun showNotification() {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Create NotificationChannel for Android O+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("channel_id", "Order Updates", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        // Intent to go to YourOrders screen
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("navigateTo", "yourOrders")
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(this, "channel_id")
            .setContentTitle("Hey\uD83D\uDC4B\n")
            .setContentText("Your order has been confirmed! 🎉")
            .setSmallIcon(R.drawable.supergrocerapplogo)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000,1000,1000))
            .setContentIntent(pendingIntent)
            .build()
        notificationManager.notify(1, notification)
    }
}
