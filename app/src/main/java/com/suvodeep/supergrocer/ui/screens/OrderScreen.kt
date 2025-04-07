package com.suvodeep.supergrocer.ui.screens

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.suvodeep.supergrocer.MainActivity
import com.suvodeep.supergrocer.R
import com.suvodeep.supergrocer.SuperGrocerAppScreens
import com.suvodeep.supergrocer.SuperGrocerViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun OrderScreen(superGrocerViewModel: SuperGrocerViewModel,
                navController: NavController
) {
    val addresses = superGrocerViewModel.address.collectAsState()
    val addressList = addresses.value.toList()
    var selectedAddress by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current
    val addressSelect = remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("COD") }
    var buttonText by remember { mutableStateOf("Place Order") }
    var hasNotificationPermission by remember { mutableStateOf(false) }
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasNotificationPermission = isGranted
    }

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            hasNotificationPermission = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED

            if (!hasNotificationPermission) {
                permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        } else {
            hasNotificationPermission = true
        }
    }

    LaunchedEffect(addressList) {
        if (addressList.isEmpty()) {
            Toast.makeText(context, "Please add address", Toast.LENGTH_SHORT).show()
            navController.navigate(SuperGrocerAppScreens.Address.name)
        }
    }
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        item {
            Text(
                "Select Address:",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(8.dp)
            )
        }
        items(addressList.size) { index ->
            val address = addressList[index]
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .height(130.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedAddress == address,
                        onClick = {
                            selectedAddress = address
                            addressSelect.value = true
                            superGrocerViewModel.setSelectedAddressFromOrderScreen(address)
                        },
                        colors = RadioButtonDefaults.colors(Color(255, 165, 0))
                    )
                    Text(
                        text = address,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
        item {
            Row(
                modifier = Modifier
                .fillMaxWidth(0.5f)
                .clickable(onClick = { navController.navigate(SuperGrocerAppScreens.Address.name) }),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Address",
                    modifier = Modifier.size(30.dp)
                )
                Text("Add Address", fontWeight = FontWeight.ExtraBold)
            }
        }
        if (addressSelect.value) {
            item {
                Column {
                    HorizontalDivider(
                        thickness = 3.dp, modifier = Modifier.clip(
                            RoundedCornerShape(CornerSize(10.dp))
                        )
                    )
                    Text(
                        "Select Payment Method:",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(8.dp)
                    )
                    Box {
                        Column(modifier = Modifier.padding(horizontal = 25.dp)) {
                            Text(
                                "( To pay: ${superGrocerViewModel.grandTotal.collectAsState().value} )",
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Right,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(
                                    selected = selectedOption == "COD",
                                    onClick = {
                                        selectedOption = "COD"
                                        buttonText = "Place Order"
                                    },
                                    colors = RadioButtonDefaults.colors(Color(255, 165, 0))
                                )
                                Text("Cash on Delivery")
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(
                                    selected = selectedOption == "Online",
                                    onClick = {
                                        selectedOption = "Online"

                                        buttonText = "Proceed now"
                                    },
                                    colors = RadioButtonDefaults.colors(Color(255, 165, 0))
                                )
                                Text("Pay Online")
                            }
                        }
                    }

                }
                Button(
                    onClick = {
                        if (selectedOption == "Online") {
                            val activity = context as? MainActivity
                            if (activity != null) {
                                navController.navigate(SuperGrocerAppScreens.Home.name)
                                activity.initPayment()
                                navController.navigate(SuperGrocerAppScreens.Cart.name)
                                CoroutineScope(Dispatchers.Main).launch {
                                    delay(7000)
//                                    superGrocerViewModel.cleanCart()
                                    navController.navigate(SuperGrocerAppScreens.Home.name) {
                                        popUpTo(0)
//                                        Toast.makeText(context, "Your Order has been placed successfully", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            } else {
                                Toast.makeText(
                                    context,
                                    "Error: Unable to process payment",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            CoroutineScope(Dispatchers.Main).launch {
                                delay(1000)
                                navController.navigate(SuperGrocerAppScreens.ConfirmOrder.name)
                                val currentDateTime = SimpleDateFormat(
                                    "dd/MM/yyyy\nhh:mm a",
                                    Locale.getDefault()
                                ).format(Date())
                                val groupedCart = superGrocerViewModel.cartItem.value
                                    .groupBy { it.itemName }
                                    .map { (name, itemList) ->
                                        val quantityCount = itemList.size
                                        val imageUrl = itemList.first().imageResourceId
                                        hashMapOf(
                                            "name" to name,
                                            "quantity" to "$quantityCount", // or just "$quantityCount"
                                            "imageUrl" to imageUrl
                                        )
                                    }

                                val order = hashMapOf(
                                    "items" to groupedCart,
                                    "paymentMethod" to "Cash on Delivery",
                                    "totalPaid" to superGrocerViewModel.grandTotal.value,
                                    "address" to superGrocerViewModel.selectedAddress.value,
                                    "time&Date" to currentDateTime
                                )

                                superGrocerViewModel.yourOrder(order)

                                CoroutineScope(Dispatchers.Main).launch {
                                    delay(7000)
                                    superGrocerViewModel.cleanCart()
                                    Toast.makeText(
                                        context,
                                        "Your Order has been placed successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    navController.navigate(SuperGrocerAppScreens.Home.name) {
                                        popUpTo(0)
                                    }
                                    if (hasNotificationPermission) {
                                        showNotification(context)
                                    }
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 50.dp),
                    colors = ButtonDefaults.buttonColors()
                ) {
                    Text(buttonText, fontWeight = FontWeight.Bold, color = Color.Black)
                }

            }
        }
    }
}

    fun showNotification(context: Context) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("channel_id", "Order Updates", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        val intent = Intent(context, MainActivity::class.java).apply {
            putExtra("navigateTo", "yourOrders")
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }

        PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, "channel_id")
            .setContentTitle("Hey 👋")
            .setContentText("Your order has been confirmed! 🎉")
            .setSmallIcon(R.drawable.supergrocerapplogo)
//            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000, 1000, 1000))
//            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(1, notification)
    }
