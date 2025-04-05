package com.suvodeep.supergrocer.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.suvodeep.supergrocer.SuperGrocerViewModel

@Composable
fun YourOrderScreen(superGrocerViewModel: SuperGrocerViewModel) {
    val orders = superGrocerViewModel.orderItems.collectAsState()

    LaunchedEffect(Unit) {
        superGrocerViewModel.readOrdersFromDatabase()
    }

    if (orders.value.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No orders yet", fontWeight = FontWeight.Bold)
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            items(orders.value.size) { order ->
                OrderItemCard(order=orders.value[order])
            }
        }
    }
}


@Composable
fun OrderItemCard(order: Map<String, Any>) {
    val itemsList = order["items"] as? List<HashMap<String, Any>>
    val paymentMethod = order["paymentMethod"]?.toString() ?: "N/A"
    val totalPaid = order["totalPaid"]?.toString() ?: "0"
    val address = order["address"]?.toString() ?: "No address"
    val date = order["time&Date"]?.toString() ?: "Unknown"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp, horizontal = 10.dp),
        elevation = CardDefaults.cardElevation(6.dp),
//        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("🧾 Order Summary", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text("Order placed on: $date", fontSize = 14.sp, color = Color.Gray, modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End)
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (!itemsList.isNullOrEmpty()) {
                Text("🛒 Items:", fontWeight = FontWeight.SemiBold)

                itemsList.forEach { item ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 6.dp)
                    ) {
                        AsyncImage(
                            model = item["imageUrl"],
                            contentDescription = item["name"].toString(),
                            modifier = Modifier.size(120.dp).padding(5.dp).clip(RoundedCornerShape(10.dp))
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(text = item["name"].toString(), fontWeight = FontWeight.Bold)
                            Text(text = "Qty: ${item["quantity"]}")
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))
            Text("💳 Payment Method: $paymentMethod", fontSize = 14.sp)
            Text("💰 Total Paid: ₹$totalPaid", fontSize = 14.sp)
            Text("📦 Delivery Address:", fontWeight = FontWeight.SemiBold)
            Text(address, fontSize = 14.sp)
        }
    }
}
