package com.suvodeep.supergrocer.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.suvodeep.supergrocer.SuperGrocerAppScreens
import com.suvodeep.supergrocer.SuperGrocerViewModel
import com.suvodeep.supergrocer.data.DataResource
import com.suvodeep.supergrocer.data.InternetItem

@Composable
fun HomeScreen(
    superGrocerViewModel: SuperGrocerViewModel,
    onCategoryClick: (Int) -> Unit,
    navController: NavController
) {
    val context = LocalContext.current
    val searchItem = remember { mutableStateOf("") }
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = Modifier.fillMaxSize().padding(10.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        item(span = { GridItemSpan(2) }) {
            Column(modifier = Modifier.fillMaxWidth()) {
//                SearchBox(
//                    modifier = Modifier.fillMaxWidth(),
//                    enabled = true,
//                    labelId = "Search Item",
//                    valueState = searchItem,
//                    keyboardType = KeyboardType.Text,
//                    imeAction = ImeAction.Search,
//                    keyboardActions = KeyboardActions {},
//                    singleLined = true,
//                    items = items,
//                    superGrocerViewModel = superGrocerViewModel,
//                    navController = navController
//                )
//                Spacer(modifier = Modifier.height(5.dp))
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                        .padding(1.dp)
                        .clip(RoundedCornerShape(CornerSize(5.dp))),
                    color = Color(76, 175, 80, 255)
                ) {}
                Banners(navController, superGrocerViewModel)
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                        .padding(1.dp)
                        .clip(RoundedCornerShape(CornerSize(5.dp))),
                    color = Color(76, 175, 80, 255)
                ) {}
            }
        }
        this.items(DataResource.getCategories()) {
            CategoryCard(
                stringResId = it.stringResId,
                imageResId = it.imageResId,
                superGrocerViewModel = superGrocerViewModel,
                onCategoryClick = onCategoryClick
            )
        }
    }
}

@Composable
fun CategoryCard(
    stringResId: Int,
    imageResId: Int,
    superGrocerViewModel: SuperGrocerViewModel,
    onCategoryClick: (Int) -> Unit
) {
    val categoryName = stringResource(id = stringResId)
    Card(
        modifier = Modifier
            .padding(5.dp)
            .clickable {
                superGrocerViewModel.updateText(categoryName)
                onCategoryClick(stringResId)
            },
        shape = RoundedCornerShape(CornerSize(10.dp)),
        colors = CardDefaults.cardColors(Color(0, 253, 10, 92))// 248, 192, 124, 112

    ) {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopStart
            ) {
                Text(
                    text = categoryName, fontSize = 15.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
            Image(
                painterResource(id = imageResId), contentDescription = "$stringResId",
                modifier = Modifier.size(110.dp)
            )
        }
    }
}

@Composable
fun SearchBox(
    modifier: Modifier = Modifier,
    valueState: MutableState<String>,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Search,
    singleLined: Boolean = true,
    labelId: String,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    enabled: Boolean = true,
    items: List<InternetItem>,
    superGrocerViewModel: SuperGrocerViewModel,
    navController: NavController
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current

    fun performSearch() {
        keyboardController?.hide()

        if (items.isEmpty()) {
            Log.d("SearchBox", "Item list is empty!")
            Toast.makeText(context, "No items available", Toast.LENGTH_SHORT).show()
            return
        }

        val searchQuery = valueState.value.trim()
        val matchingItems = items.filter {it.itemName.contains(searchQuery, ignoreCase = true) }

        if (matchingItems.isNotEmpty()) {
            val firstMatchingCategory = matchingItems.first().itemCategoryId.toIntOrNull()

            if (firstMatchingCategory != null) {
                Log.d("SearchBox", "Match found! Navigating to category $firstMatchingCategory")
                superGrocerViewModel.categoryClick(firstMatchingCategory)
                navController.navigate(SuperGrocerAppScreens.Items.name)
            } else {
                Log.e("SearchBox", "Invalid category ID: ${matchingItems.first().itemCategoryId}")
                Toast.makeText(context, "Invalid category ID", Toast.LENGTH_SHORT).show()
            }
        } else {
            Log.d("SearchBox", "No matching items found.")
            Toast.makeText(context, "No items found", Toast.LENGTH_SHORT).show()
        }
    }


    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = valueState.value,
            onValueChange = { valueState.value = it },
            label = { Text(text = labelId) },
            singleLine = singleLined,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            keyboardActions = KeyboardActions(
                onSearch = { performSearch() }
            ),
            enabled = enabled,
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(CornerSize(15.dp))
        )
        IconButton(
            onClick = {
                Log.d("SearchBox", "Search button clicked")
                performSearch()
            },
            modifier = Modifier.size(50.dp)
        ) { 
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                modifier = Modifier.size(40.dp)
            )
        }
    }
}
