package com.suvodeep.supergrocer.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.suvodeep.supergrocer.R
import com.suvodeep.supergrocer.SuperGrocerAppScreens
import com.suvodeep.supergrocer.SuperGrocerViewModel
import com.suvodeep.supergrocer.data.InternetItem
import com.suvodeep.supergrocer.data.ItemWithQuantity

//@Composable
//fun CartScreen(
//    superGrocerViewModel: SuperGrocerViewModel,
//    navController: NavController,
//    superGrocerAppScreens: String,
//    items: List<InternetItem>
//) {
//    val cartItem = superGrocerViewModel.cartItem.collectAsState()
////    val itemWithQuantity=cartItem.value.groupBy {
////        it
////    }.map {
////        (it,quantity)->
////        ItemWithQuantity(it,quantity.size)
////    }
//    val itemWithQuantity = cartItem.value.groupBy { it.itemName }.map { (name, items) ->
//        ItemWithQuantity(items.first(), items.size)
//    }
//    val categoryName = superGrocerViewModel.uiState.collectAsState()
//    val selectedCategory= stringResource(categoryName.value.onCategoryState)
//    val database=items.filter {
//        it.itemCategoryId.lowercase()==selectedCategory.lowercase()
//    }
//
//
//    if(cartItem.value.isNotEmpty()){
//    LazyColumn {
//        item {
//
//        }
//        item {
//            Text(
//                "Items in Cart:",
//                modifier = Modifier.padding(10.dp),
//                fontSize = 20.sp,
//                fontWeight = FontWeight.Bold
//            )
//        }
//        items(itemWithQuantity) {
//            CartItem(it.item, superGrocerViewModel, it.quantity)
//        }
//        item {
//            Text(
//                "Bill Details:",
//                modifier = Modifier.padding(start = 10.dp),
//                fontSize = 20.sp,
//                fontWeight = FontWeight.Bold
//            )
//            HorizontalDivider(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .size(2.dp)
//                    .padding(horizontal = 10.dp),
//                color = Color.LightGray
//            )
//            val discount=superGrocerViewModel.discount.value
//            val iT=cartItem.value.sumOf { it.itemPrice * (100 - discount) / 100 }
//            val deliveryCharge = 30
//            val qty=cartItem.value.size
//            val total = if (iT <= 499) iT + deliveryCharge else iT
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(5.dp)
//                    .clip(
//                        RoundedCornerShape(
//                            CornerSize(10.dp)
//                        )
//                    )
//                    .height(100.dp)
//                    .background(
//                        color = Color(252, 137, 172, 70)
//                    ),
//                verticalArrangement = Arrangement.SpaceAround,
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                BillDetails("Item Total:", iT*qty, FontWeight.Normal, TextDecoration.None,cartItem)
//                BillDetails(
//                    "Delivery Charges:", deliveryCharge, FontWeight.Normal, if (iT*qty > 499) {
//                        TextDecoration.LineThrough
//                    } else {
//                        TextDecoration.None
//                    },
//                    cartItem
//                )
//                HorizontalDivider(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = 15.dp),
//                    color = Color.Black
//                )
//                BillDetails("Total:", total, FontWeight.ExtraBold, TextDecoration.None,cartItem)
//            }
//        }
//        item {
//            Box(
//                modifier = Modifier
//                    .padding(horizontal = 90.dp)
//                    .background(
//                        brush = Brush.linearGradient(
//                            colors = listOf(Color(0xFF75F679), Color(0xFF84EFD9))
//                        ),
//                        shape = RoundedCornerShape(50.dp)
//                    )
//                    .clip(RoundedCornerShape(50.dp))
//            ) {
//                Button(
//                    onClick = {},
//                    shape = RoundedCornerShape(50.dp),
//                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent), // Make button transparent
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .clip(RoundedCornerShape(50.dp))
//                ) {
//                    Text(
//                        text = "Place Order",
//                        fontSize = 20.sp,
//                        fontWeight = FontWeight.ExtraBold,
//                        color = Color.Black
//                    )
//                }
//            }
//        }
//    }
//    }else{
//            Column(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Image(
//                    painter = painterResource(id = R.drawable.emptycart),
//                    contentDescription = "Empty Cart",
//                    modifier = Modifier.size(250.dp)
//                )
//                    Button(onClick = {navController.navigate(superGrocerAppScreens){popUpTo(0)}},
//                        colors = ButtonDefaults.buttonColors(Color.LightGray)) {
//                        Text("Browse Products",
//                            color = Color.DarkGray,
//                            fontWeight = FontWeight.Bold)
//                    }
//
//
//            }
//        }
//    }

//@Composable
//fun CartScreen(
//    superGrocerViewModel: SuperGrocerViewModel,
//    navController: NavController,
//    superGrocerAppScreens: String
//) {
//    val cartItem = superGrocerViewModel.cartItem.collectAsState()
////    val categoryName = superGrocerViewModel.uiState.collectAsState()
////    val selectedCategory = stringResource(categoryName.value.onCategoryState)
////    val database = items.filter {
////        it.itemCategoryId.lowercase() == selectedCategory.lowercase()
////    }
//
//    val itemWithQuantity =
//        cartItem.value.groupBy { Pair(it.itemName, it.itemCategoryId) }.map { (_, groupedItems) ->
//            ItemWithQuantity(groupedItems.first(), groupedItems.size)
//        }
//    superGrocerViewModel.getQuantity(itemWithQuantity.size)
//
//    if (cartItem.value.isNotEmpty()) {
//        LazyColumn {
//            item {
//                Text(
//                    "Items in Cart:",
//                    modifier = Modifier.padding(10.dp),
//                    fontSize = 20.sp,
//                    fontWeight = FontWeight.Bold
//                )
//            }
//            items(itemWithQuantity) {
//                CartItem(it.item, superGrocerViewModel, it.quantity)
//            }
//            item {
//                Text(
//                    "Bill Details:",
//                    modifier = Modifier.padding(start = 10.dp),
//                    fontSize = 20.sp,
//                    fontWeight = FontWeight.Bold
//                )
//                HorizontalDivider(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .size(2.dp)
//                        .padding(horizontal = 10.dp),
//                    color = Color.LightGray
//                )
//                val discount = superGrocerViewModel.discount.collectAsState().value
//                val itemTotal = cartItem.value.sumOf { it.itemPrice * (100 - discount) / 100 }
//                val deliveryCharge = 30
//                val total = if (itemTotal < 499) {
//                    itemTotal + deliveryCharge
//                } else {
//                    itemTotal
//                }
//
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(5.dp)
//                        .clip(RoundedCornerShape(10.dp))
//                        .background(Color(252, 137, 172, 70))
//                        .padding(10.dp),
//                    verticalArrangement = Arrangement.SpaceAround,
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    BillDetails(
//                        "Item Total:",
//                        itemTotal,
//                        FontWeight.Normal,
//                        TextDecoration.None,
//                        cartItem
//                    )
//                    BillDetails(
//                        "Delivery Charges:", deliveryCharge, FontWeight.Normal,
//                        if (total > 499) TextDecoration.LineThrough else TextDecoration.None,
//                        cartItem
//                    )
//                    HorizontalDivider(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(horizontal = 15.dp),
//                        color = Color.Black
//                    )
//                    BillDetails(
//                        "Total:",
//                        total,
//                        FontWeight.ExtraBold,
//                        TextDecoration.None,
//                        cartItem
//                    )
//                }
//            }
//            item {
//                Box(
//                    modifier = Modifier
//                        .padding(horizontal = 90.dp)
//                        .background(
//                            brush = Brush.linearGradient(
//                                colors = listOf(Color(0xFF75F679), Color(0xFF84EFD9))
//                            ),
//                            shape = RoundedCornerShape(50.dp)
//                        )
//                        .clip(RoundedCornerShape(50.dp))
//                ) {
//                    Button(
//                        onClick = { navController.navigate(SuperGrocerAppScreens.Order.name) },
//                        shape = RoundedCornerShape(50.dp),
//                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
//                        modifier = Modifier.fillMaxWidth()
//                    ) {
//                        Text(
//                            text = "Place Order",
//                            fontSize = 20.sp,
//                            fontWeight = FontWeight.ExtraBold,
//                            color = Color.Black
//                        )
//                    }
//                }
//            }
//        }
//    } else {
//        Column(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Image(
//                painter = painterResource(id = R.drawable.emptycart),
//                contentDescription = "Empty Cart",
//                modifier = Modifier.size(250.dp)
//            )
//            Button(
//                onClick = { navController.navigate(superGrocerAppScreens) { popUpTo(0) } },
//                colors = ButtonDefaults.buttonColors(Color.LightGray)
//            ) {
//                Text(
//                    "Browse Products",
//                    color = Color.DarkGray,
//                    fontWeight = FontWeight.Bold
//                )
//            }
//        }
//    }
//}
//
//
//
//
//@Composable
//fun CartItem(
//    cartItem: InternetItem,
//    superGrocerViewModel: SuperGrocerViewModel,
//    quantity: Int
//) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
////            .height(150.dp)
//            .padding(8.dp)
//            .clip(RoundedCornerShape(CornerSize(8.dp)))
//            .background(
//                color = Color(
//                    147,
//                    144,
//                    144,
//                    29
//                )
//            ),
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.SpaceAround
//    ) {
//        AsyncImage(
//            model = cartItem.imageResourceId, contentDescription = cartItem.itemName,
//            modifier = Modifier
//                .size(120.dp)
//                .padding(5.dp)
//                .clip(RoundedCornerShape(CornerSize(10.dp)))
//                .background(Color(0, 253, 10, 92))
//        )
//        Column(
//            modifier = Modifier.height(80.dp),
//            verticalArrangement = Arrangement.SpaceAround,
//            horizontalAlignment = Alignment.Start
//        ) {
//            Text(
//                cartItem.itemName,
//                fontWeight = FontWeight.Bold,
//                fontSize = 15.sp,
//                maxLines = 1,
//                modifier = Modifier.width(110.dp)
//            )
////            Text(cartItem.itemQuantity, fontSize = 12.sp)
//            Text(
//                "Rs. ${cartItem.itemPrice}" + "/" + cartItem.itemQuantity,
//                fontSize = 10.sp,
//                textDecoration = TextDecoration.LineThrough
//            )
//
//            val discount = superGrocerViewModel.discount.collectAsState().value
//            Text(
//                "Rs. ${cartItem.itemPrice * (100 - discount) / 100}",
//                fontWeight = FontWeight.Bold,
//                fontSize = 15.sp
//            )
//        }
////        Column(
////            modifier = Modifier.height(80.dp),
////            verticalArrangement = Arrangement.SpaceAround,
////            horizontalAlignment = Alignment.CenterHorizontally
////        ) {
//////            Text(
//////                "Rs. ${cartItem.itemPrice}",
//////                fontSize = 10.sp,
//////                textDecoration = TextDecoration.LineThrough
//////            )
//////
//////            val discount=superGrocerViewModel.discount.collectAsState().value
//////            Text(
//////                "Rs. ${cartItem.itemPrice * (100 - discount) / 100}",
//////                fontWeight = FontWeight.Bold,
//////                fontSize = 15.sp
//////            )
////        }
//        Column(
//            modifier = Modifier.height(90.dp),
//            verticalArrangement = Arrangement.SpaceAround,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Text("Quantity: $quantity", fontSize = 11.sp)
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(25.dp)
//                    .padding(horizontal = 35.dp),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                RoundedButton(
//                    imageVector = Icons.Default.Remove,
//                    onClick = {
//                            superGrocerViewModel.removeFromCart(
//                                InternetItem(
//                                    stringResourceId,
//                                    itemCategoryId,
//                                    itemQuantity,
//                                    itemPrice,
//                                    imageResourceId
//                                )
//                            )
//                            //superGrocerViewModel.addedToCart(cartAdded.intValue)
//
//                    },
//                    tint = Color.Black,
//                    color = Color.Green
//                )
//
//                RoundedButton(
//                    imageVector = Icons.Outlined.Delete,
//                    onClick = {
//                        superGrocerViewModel.removeAll(
//                            InternetItem(
//                                stringResourceId,
//                                itemCategoryId,
//                                itemQuantity,
//                                itemPrice,
//                                imageResourceId
//                            )
//                        )
////                        cartAdded.intValue = 0
//                    },
//                    tint = Color.Red,
//                    color = Color.Transparent
//                )
//                RoundedButton(
//                    imageVector = Icons.Outlined.Add,
//                    onClick = {
//                            superGrocerViewModel./*addToCart*/addCartItemsToDatabase(
//                                InternetItem(
//                                    stringResourceId,
//                                    itemCategoryId,
//                                    itemQuantity,
//                                    itemPrice,
//                                    imageResourceId
//                                )
//                            )
////                                    superGrocerViewModel.addedToCart(cartAdded.intValue)
//
//                    },
//                    tint = Color.Black,
//                    color = Color.Green
//                )
//            }
////            Button(
////                onClick = {
////                    superGrocerViewModel.removeAll(cartItem)
////                },
////                modifier = Modifier
////                    .width(100.dp)
////                    .height(35.dp),
////                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
////            ) {
////                Text(
////                    "Remove",
////                    fontSize = 11.sp,
////                    fontWeight = FontWeight.Bold
////                )
////
////            }
//        }
//    }
//}
@Composable
fun CartScreen(
    superGrocerViewModel: SuperGrocerViewModel,
    navController: NavController,
    superGrocerAppScreens: String
) {
    val cartItem = superGrocerViewModel.cartItem.collectAsState()

    val itemWithQuantity = cartItem.value.groupBy { it.itemName to it.itemCategoryId }
        .map { (_, groupedItems) -> ItemWithQuantity(groupedItems.first(), groupedItems.size) }

    superGrocerViewModel.getQuantity(itemWithQuantity.size)

    if (cartItem.value.isNotEmpty()) {
        LazyColumn {
            item {
                Text("Items in Cart:", modifier = Modifier.padding(10.dp), fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
            items(itemWithQuantity) {
                CartItem(it.item, superGrocerViewModel, it.quantity)
            }
            item {
                val itemTotal = cartItem.value.sumOf { it.itemPrice * (100 - 39) / 100 }
                val deliveryCharge = 30
                val total = if (itemTotal < 499) itemTotal + deliveryCharge else itemTotal
                superGrocerViewModel.getGrandTotal(total)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(252, 137, 172, 70))
                        .padding(10.dp),
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BillDetails("Item Total:", itemTotal, FontWeight.Normal, TextDecoration.None, cartItem)
                    BillDetails("Delivery Charges:", deliveryCharge, FontWeight.Normal, if (total > 499) TextDecoration.LineThrough else TextDecoration.None, cartItem)
                    HorizontalDivider(modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp), color = Color.Black)
                    BillDetails("Grand Total:", total, FontWeight.ExtraBold, TextDecoration.None, cartItem)
                }
            }
            item {
                Box(
                    modifier = Modifier.padding(horizontal = 90.dp)
                        .background(Brush.linearGradient(listOf(Color(0xFF75F679), Color(0xFF84EFD9))), shape = RoundedCornerShape(50.dp))
                        .clip(RoundedCornerShape(50.dp))
                ) {
                    Button(
                        onClick = { navController.navigate(SuperGrocerAppScreens.Order.name) },
                        shape = RoundedCornerShape(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Place Order", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = Color.Black)
                    }
                }
            }
        }
    } else {
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painter = painterResource(id = R.drawable.emptycart), contentDescription = "Empty Cart", modifier = Modifier.size(250.dp))
            Button(onClick = { navController.navigate(superGrocerAppScreens) { popUpTo(0) } }, colors = ButtonDefaults.buttonColors(Color.LightGray)) {
                Text("Browse Products", color = Color.DarkGray, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun CartItem(cartItem: InternetItem, superGrocerViewModel: SuperGrocerViewModel, quantity: Int) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(8.dp).clip(RoundedCornerShape(8.dp)).background(Color(147, 144, 144, 29)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        AsyncImage(model = cartItem.imageResourceId, contentDescription = cartItem.itemName, modifier = Modifier.size(120.dp).padding(5.dp).clip(RoundedCornerShape(10.dp)).background(Color(0, 253, 10, 92)))
        Column(modifier = Modifier.height(80.dp), verticalArrangement = Arrangement.SpaceAround, horizontalAlignment = Alignment.Start) {
            Text(cartItem.itemName, fontWeight = FontWeight.Bold, fontSize = 15.sp, maxLines = 1, modifier = Modifier.width(110.dp))
            Text("Rs. ${cartItem.itemPrice}/${cartItem.itemQuantity}", fontSize = 10.sp, textDecoration = TextDecoration.LineThrough)
//            superGrocerViewModel.discount.collectAsState().value
            Text("Rs. ${cartItem.itemPrice * (100 - 39) / 100}", fontWeight = FontWeight.Bold, fontSize = 15.sp)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Qty: $quantity", fontSize = 11.sp)
            Row(
                modifier = Modifier.fillMaxWidth().height(25.dp).padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                RoundedButton(imageVector = Icons.Default.Remove, onClick = { superGrocerViewModel.removeFromCart(cartItem) }, tint = Color.Black, color = Color(
                    3,
                    169,
                    244,
                    255
                )
                )
                RoundedButton(imageVector = Icons.Outlined.Delete, onClick = { superGrocerViewModel.removeAll(cartItem) }, tint = Color.Red, color = Color.Transparent)
                RoundedButton(imageVector = Icons.Outlined.Add, onClick = { superGrocerViewModel.addCartItemsToDatabase(cartItem) }, tint = Color.Black, color = Color(
                    3,
                    169,
                    244,
                    255
                )
                )
            }
        }
    }
}


@Composable
fun BillDetails(
    title: String,
    bill: Int,
    fontWeight: FontWeight,
    textDecoration: TextDecoration,
    cartItem: State<List<InternetItem>>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(title, fontWeight = fontWeight, fontSize = 18.sp)
        Text(
            "Rs. $bill",
            fontWeight = fontWeight,
            fontSize = 18.sp,
            textDecoration = textDecoration
        )
    }
}

@Composable
fun RoundedButton(
    imageVector: ImageVector,
    onClick: () -> Unit,
    tint: Color,
    color: Color
) {
    Surface(
        modifier = Modifier
            .size(25.dp)
            .clip(RoundedCornerShape(CornerSize(5.dp)))
            .clickable { onClick() },
        color = color
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = imageVector, contentDescription = "Increment or decrement",
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.Transparent),
                tint = tint
            )
        }
    }
}