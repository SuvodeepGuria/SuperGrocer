package com.suvodeep.supergrocer.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.suvodeep.supergrocer.SuperGrocerAppScreens
import com.suvodeep.supergrocer.SuperGrocerViewModel
import com.suvodeep.supergrocer.data.Banner
import com.suvodeep.supergrocer.data.getBanners
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun Banners(
    navController: NavController,
    superGrocerViewModel: SuperGrocerViewModel
) {
    val banners = getBanners()
    AutoScrollingLazyRow(banners, navController, superGrocerViewModel)
}

@Composable
fun AutoScrollingLazyRow(
    banners: List<Banner>,
    navController: NavController,
    superGrocerViewModel: SuperGrocerViewModel
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    LocalContext.current

    LaunchedEffect(Unit) {
        while (true) {
            delay(2500)
            val nextIndex = (listState.firstVisibleItemIndex + 1) % banners.size
            coroutineScope.launch {
                listState.animateScrollToItem(nextIndex)
            }
        }
    }

    LazyRow(state = listState, modifier = Modifier.fillMaxWidth()) {
        items(banners.size) { index ->
            val banner = banners[index]
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = banner.bannerImage),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(6.dp)
                        .clip(RoundedCornerShape(CornerSize(10.dp)))
                        .clickable {
                            superGrocerViewModel.categoryClick(banner.category)
                            navController.navigate(SuperGrocerAppScreens.Items.name)
                        }
                )
            }
        }
    }
}

