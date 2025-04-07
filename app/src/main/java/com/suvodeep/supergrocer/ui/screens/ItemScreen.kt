package com.suvodeep.supergrocer.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Replay
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.suvodeep.supergrocer.R
import com.suvodeep.supergrocer.SuperGrocerAppScreens
import com.suvodeep.supergrocer.SuperGrocerViewModel
import com.suvodeep.supergrocer.data.InternetItem

@Composable
fun ItemScreen(
    superGrocerViewModel: SuperGrocerViewModel,
    items: List<InternetItem>,
    navController: NavController
) {
    val categoryName = superGrocerViewModel.uiState.collectAsState()
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.outofstock))
    val selectedCategory = stringResource(categoryName.value.onCategoryState)
    val database = items.filter {
        it.itemCategoryId.lowercase() == selectedCategory.lowercase()
    }
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "$selectedCategory (${database.size})",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(76, 175, 80),
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        )

        if (!database.isEmpty()) {
            LazyVerticalGrid(columns = GridCells.Adaptive(150.dp)) {
                items(database) {
                    ItemCard(
                        stringResourceId = it.itemName,
                        itemCategoryId = it.itemCategoryId,
                        itemQuantity = it.itemQuantity,
                        itemPrice = it.itemPrice,
                        imageResourceId = it.imageResourceId,
                        superGrocerViewModel = superGrocerViewModel,
                        navController = navController
                    )
                }
            }
        } else {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(modifier = Modifier.fillMaxSize().padding(bottom = 110.dp),
                    verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                    LottieAnimation(composition = composition, iterations = 1000, modifier = Modifier.size(200.dp))
                    Text(
                        text = "No items are available right now!!",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    Button(
                        onClick = { navController.navigate(SuperGrocerAppScreens.Home.name){popUpTo(0)} },
                        colors = ButtonDefaults.buttonColors(Color.Transparent),
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 100.dp)
                    ) {
                        Text("Go back to Home", color = Color.Gray)
                    }
                }
            }
        }
    }
}

@Composable
fun InternetItemScreen(
    superGrocerViewModel: SuperGrocerViewModel,
    itemUiState: SuperGrocerViewModel.DownloadItemState,
    navController: NavController
) {
    when (itemUiState) {
        is SuperGrocerViewModel.DownloadItemState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(50.dp)
                )
            }
        }

        is SuperGrocerViewModel.DownloadItemState.Success -> {
            ItemScreen(
                superGrocerViewModel = superGrocerViewModel,
                items = itemUiState.item,
                navController = navController
            )
        }

        is SuperGrocerViewModel.DownloadItemState.Error -> {
            ErrorScreen(superGrocerViewModel)
        }
    }

}

@Composable
fun ItemCard(
    stringResourceId: String,
    itemCategoryId: String,
    itemQuantity: String,
    itemPrice: Int,
    imageResourceId: String,
    superGrocerViewModel: SuperGrocerViewModel,
    navController: NavController
) {
    val cartAdded = remember { mutableIntStateOf(0) }
//    superGrocerViewModel.addedToCart(cartAdded.intValue)
    Box(modifier = Modifier.padding(5.dp)) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color(233, 30, 99, 18),
            shape = RoundedCornerShape(10.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    colors = CardDefaults.cardColors(Color(0, 253, 10, 74)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = imageResourceId, contentDescription = stringResourceId,
                            modifier = Modifier.size(120.dp)
                        )
                    }
                }
                Text(
                    text = stringResourceId,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 2.dp),
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 15.sp,
                    maxLines = 1
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "Rs: $itemPrice/$itemQuantity",
                            textDecoration = TextDecoration.LineThrough,
                            fontSize = 10.sp
                        )
                        Text(
                            text = "Rs: ${itemPrice * (100 - 39) / 100}/$itemQuantity",
                            fontWeight = FontWeight.ExtraBold, fontSize = 12.sp
                        )
                    }
                    Row {
                        val cartIcon = if (cartAdded.intValue == 0) {
                            Icons.Outlined.ShoppingCart
                        } else {
                            Icons.Default.ShoppingCart
                        }
                        Icon(
                            imageVector = cartIcon,
                            contentDescription = "Cart Icon",
                            Modifier.size(23.dp)
                        )
//                        Text("${cartAdded.intValue}", fontSize = 13.sp)
                    }
                }
//                val isInCart = superGrocerViewModel.cartItems.any { it.itemName == stringResourceId }
                if (/*cartAdded.intValue < 1*/cartAdded.intValue == 0) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(25.dp)
                            .padding(horizontal = 10.dp)
                            .clickable {
                                superGrocerViewModel./*addToCart*/addCartItemsToDatabase(
                                    InternetItem(
                                        stringResourceId,
                                        itemCategoryId,
                                        itemQuantity,
                                        itemPrice,
                                        imageResourceId,
                                    )
                                )
                                cartAdded.intValue++
                                //superGrocerViewModel.addedToCart(cartAdded.intValue)
                            },
                        colors = CardDefaults.cardColors(Color.Green)
                    ) {
                        Text(
                            text = "Add to cart",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            color = Color.Black
                        )
                    }
                } else {
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(25.dp)
//                            .padding(horizontal = 35.dp),
//                        horizontalArrangement = Arrangement.SpaceBetween
//                    ) {
//                        RoundedButton(
//                            imageVector = Icons.Default.Remove,
//                            onClick = {
//
//                                if (cartAdded.intValue > 0) {
//                                    cartAdded.intValue--
//                                    superGrocerViewModel.removeFromCart(
//                                        InternetItem(
//                                            stringResourceId,
//                                            itemCategoryId,
//                                            itemQuantity,
//                                            itemPrice,
//                                            imageResourceId
//                                        )
//                                    )
//                                    //superGrocerViewModel.addedToCart(cartAdded.intValue)
//                                }
//                            },
//                            tint = Color.Black,
//                            color = Color.Green
//                        )
//
//                        RoundedButton(
//                            imageVector = Icons.Outlined.Delete,
//                            onClick = {
//                                superGrocerViewModel.removeAll(
//                                    InternetItem(
//                                        stringResourceId,
//                                        itemCategoryId,
//                                        itemQuantity,
//                                        itemPrice,
//                                        imageResourceId
//                                    )
//                                )
//                                cartAdded.intValue = 0
//                            },
//                            tint = Color.Red,
//                            color = Color.Transparent
//                        )
//                        RoundedButton(
//                            imageVector = Icons.Outlined.Add,
//                            onClick = {
//                                if (cartAdded.intValue > 9) {
//                                    Toast.makeText(
//                                        context,
//                                        "The maximum number of items that can be added to the cart is 10.",
//                                        Toast.LENGTH_SHORT
//                                    ).show()
//                                } else if (cartAdded.intValue <= 9) {
//                                    cartAdded.intValue++
//                                    superGrocerViewModel./*addToCart*/addCartItemsToDatabase(
//                                        InternetItem(
//                                            stringResourceId,
//                                            itemCategoryId,
//                                            itemQuantity,
//                                            itemPrice,
//                                            imageResourceId
//                                        )
//                                    )
////                                    superGrocerViewModel.addedToCart(cartAdded.intValue)
//                                }
//                            },
//                            tint = Color.Black,
//                            color = Color.Green
//                        )
//                    }
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(25.dp)
                            .padding(horizontal = 10.dp)
                            .clickable {
//                                superGrocerViewModel./*addToCart*/addCartItemsToDatabase(
//                                    InternetItem(
//                                        stringResourceId,
//                                        itemCategoryId,
//                                        itemQuantity,
//                                        itemPrice,
//                                        imageResourceId,
//                                    )
//                                )
//                                cartAdded.intValue++

                                //superGrocerViewModel.addedToCart(cartAdded.intValue)
                                navController.navigate(SuperGrocerAppScreens.Cart.name)
                            },
                        colors = CardDefaults.cardColors(Color.Green)
                    ) {
                        Text(
                            text = "Go to the cart",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            color = Color.Black
                        )
                    }
                }
            }
        }
        Card(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .width(70.dp)
                .height(20.dp),
            colors = CardDefaults.cardColors(Color.Red),
            shape = RoundedCornerShape(bottomStart = 10.dp, topEnd = 10.dp)
        ) {
            Text(
                text = "39% off",
                modifier = Modifier.fillMaxSize(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

//@Composable
//fun RoundedButton(
//    imageVector: ImageVector,
//    onClick: () -> Unit,
//    tint: Color,
//    color: Color
//) {
//    Surface(
//        modifier = Modifier
//            .size(25.dp)
//            .clip(RoundedCornerShape(CornerSize(5.dp)))
//            .clickable { onClick() },
//        color = color
//    ) {
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color.Transparent),
//            contentAlignment = Alignment.Center
//        ) {
//            Icon(
//                imageVector = imageVector, contentDescription = "Increment or decrement",
//                modifier = Modifier
//                    .size(30.dp)
//                    .background(Color.Transparent),
//                tint = tint
//            )
//        }
//    }
//}

@Composable
fun ErrorScreen(superGrocerViewModel: SuperGrocerViewModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.networkerror),
            contentDescription = "Network Error",
            modifier = Modifier.size(200.dp)
        )
        Text(
            "Please check your Internet Connection !",
            fontSize = 15.sp,
            color = Color.DarkGray,
            fontWeight = FontWeight.Bold
        )
        IconButton(
            onClick = { superGrocerViewModel.getInternetItems() },
            modifier = Modifier.size(50.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Replay, contentDescription = "Retry",
                modifier = Modifier.size(30.dp)
            )
        }
    }
}