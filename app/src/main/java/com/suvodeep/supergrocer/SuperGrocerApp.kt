package com.suvodeep.supergrocer

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.sharp.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.suvodeep.supergrocer.data.InternetItem
import com.suvodeep.supergrocer.ui.screens.AccountScreen
import com.suvodeep.supergrocer.ui.screens.AddressScreen
import com.suvodeep.supergrocer.ui.screens.CartScreen
import com.suvodeep.supergrocer.ui.screens.ConfirmOrderScreen
import com.suvodeep.supergrocer.ui.screens.HomeScreen
import com.suvodeep.supergrocer.ui.screens.InternetItemScreen
import com.suvodeep.supergrocer.ui.screens.LoginScreen
import com.suvodeep.supergrocer.ui.screens.OrderScreen
import com.suvodeep.supergrocer.ui.screens.SplashScreen

enum class SuperGrocerAppScreens(val title: String) {
    Home("SuperGrocer"),
    Items("Items"),
    Cart("Your Cart"),
    //BannerClick("Items"),
    Account("Account"),
    Order("Order"),
    Address("Address"),
    ConfirmOrder("Confirm Order")
}

var canNavigate = false
val auth= FirebaseAuth.getInstance()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuperGrocerApp(
    superGrocerViewModel: SuperGrocerViewModel, modifier: Modifier,
    navController: NavHostController = rememberNavController(),
    item: List<InternetItem>
) {
    val user=superGrocerViewModel.user.collectAsState()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = SuperGrocerAppScreens.valueOf(
        backStackEntry?.destination?.route ?: SuperGrocerAppScreens.Home.name
    )
    canNavigate = navController.previousBackStackEntry?.destination?.route != null
    val isVisible = superGrocerViewModel.isVisible.collectAsState()
    val cartItemNumber = superGrocerViewModel.cartItem.collectAsState().value.size
    superGrocerViewModel.setUser(auth.currentUser) 
    if (isVisible.value) {
        SplashScreen()
    }
    else if(user.value==null){
        LoginScreen(superGrocerViewModel,navController)
    }else {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Text(
                                if (SuperGrocerAppScreens.Home.name == currentScreen.name) {
                                    "${currentScreen.title} 🛒"
                                } else {
                                    currentScreen.title
                                },
                                fontSize = 26.sp,
                                fontFamily = FontFamily.SansSerif,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.TopEnd
                            ) {
                                Box {
                                    IconButton(
                                        onClick = {
                                            if (currentScreen != SuperGrocerAppScreens.Cart) {
                                                navController.navigate(SuperGrocerAppScreens.Cart.name)
                                            }
                                        },
                                        modifier = Modifier.size(45.dp)
                                    ) {
                                        val cartIcon =
                                            if (currentScreen.name == SuperGrocerAppScreens.Cart.name) {
                                                Icons.Sharp.ShoppingCart
                                            } else {
                                                Icons.Outlined.ShoppingCart
                                            }
                                        Icon(
                                            imageVector = cartIcon,
                                            contentDescription = "Cart",
                                            modifier = Modifier.size(37.dp),
                                            tint = Color.Black
                                        )
                                    }
                                    if (cartItemNumber > 0) {
                                        Card(
                                            modifier = Modifier
//                                                .width(20.dp)
//                                                .height(26.dp)
                                                .size(20.dp)
//                                                .padding(bottom = 1.dp)
                                                .align(alignment = Alignment.TopEnd),
                                            colors = CardDefaults.cardColors(Color.Red)
                                        ) {
                                            Box(
                                                modifier = Modifier.size(100.dp),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    text = "$cartItemNumber",
                                                    fontSize = 12.sp,
                                                    fontWeight = FontWeight.ExtraBold,
//                                                    modifier = Modifier.fillMaxSize(),
//                                                    textAlign = TextAlign.Center,
                                                    color = Color.Black
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(Color(59, 248, 66, 230)),
                    navigationIcon = {
                        if (canNavigate) {
                            IconButton(onClick = { navController.navigateUp() }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Back",
                                    tint = Color.Black
                                )
                            }
                        }
                    }
                )
            },
            bottomBar = { BottomAppbar(navController, currentScreen) },
        ) { it ->
            NavHost(
                navController = navController,
                startDestination = SuperGrocerAppScreens.Home.name,
                modifier = Modifier.padding(it)
            ) {
                composable(route = SuperGrocerAppScreens.Home.name) {
                    HomeScreen(
                        superGrocerViewModel = superGrocerViewModel,
                        onCategoryClick = {
                            superGrocerViewModel.categoryClick(it)
                            Log.d("Navigation", "Navigating to: ${SuperGrocerAppScreens.Items.name}")
                            navController.navigate(SuperGrocerAppScreens.Items.name)
                        },
                        navController,
                        item
                    )
                }
                composable(route = SuperGrocerAppScreens.Items.name) {
                    InternetItemScreen(
                        superGrocerViewModel = superGrocerViewModel,
                        itemUiState = superGrocerViewModel.itemUiState,
                        navController=navController
                    )
                }
                composable(route = SuperGrocerAppScreens.Cart.name) {
                    CartScreen(superGrocerViewModel, navController, SuperGrocerAppScreens.Home.name)
                }
                composable(route = SuperGrocerAppScreens.Account.name) {
                    AccountScreen(navController,superGrocerViewModel)
                }
                composable (route= SuperGrocerAppScreens.Address.name){
                    AddressScreen(superGrocerViewModel)
                }
                composable(route= SuperGrocerAppScreens.Order.name) {
                    OrderScreen(superGrocerViewModel,navController)
                }
                composable(route=SuperGrocerAppScreens.ConfirmOrder.name){
                    ConfirmOrderScreen()
                }
            }
        }
    }
}

    @Composable
    fun BottomAppbar(navController: NavHostController, currentScreen: SuperGrocerAppScreens) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(68.dp)
            //.background(Brush.linearGradient(colors = listOf(Color(0xFF2AF32F), Color(0xFFF3F5F7)))),
            //color = Color(3, 169, 244, 69)
            //.background(brush = Brush.linearGradient(olors = listOf(Color(0xFF2AF32F), Color(0xFFF3F5F7)))
            //color = listOf(Color(0xFF2AF32F), Color(0xFFF3F5F7))

        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(68.dp)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF75F679), Color(0xFF84EFD9) /*, Color(
                                0xFFF3C091
                            )*/
                            )
                        )
                    ),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(68.dp)
                        .padding(horizontal = 40.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            navController.navigate(SuperGrocerAppScreens.Home.name) {
                                popUpTo(0)
                            }
                        },
                        modifier = Modifier.size(35.dp)
                    ) {
                        val homeIcon = if (currentScreen.name == SuperGrocerAppScreens.Home.name) {
                            Icons.Default.Home
                        } else {
                            Icons.Outlined.Home
                        }
                        Icon(
                            imageVector = homeIcon, contentDescription = "Home",
                            modifier = Modifier.size(35.dp),
                            tint = Color.Black
                        )
                    }


                    IconButton(
                        onClick = {
                            if (currentScreen != SuperGrocerAppScreens.Account) {
                                navController.navigate(SuperGrocerAppScreens.Account.name) {
                                }
                            }
                        }, modifier = Modifier.size(30.dp)
                    ) {
                        val accountIcon =
                            if (currentScreen.name == SuperGrocerAppScreens.Account.name) {
                                Icons.Default.AccountCircle
                            } else {
                                Icons.Outlined.AccountCircle
                            }
                        Icon(
                            imageVector = accountIcon, contentDescription = "Account",
                            modifier = Modifier.size(30.dp),
                            tint = Color.Black
                        )
                    }
                }
            }
        }
    }
