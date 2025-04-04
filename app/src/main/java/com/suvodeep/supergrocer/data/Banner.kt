package com.suvodeep.supergrocer.data

import com.suvodeep.supergrocer.R
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Banner(
    @DrawableRes val bannerImage: Int,
    @StringRes val category: Int)

fun getBanners(): List<Banner> {
    return listOf<Banner>(
        Banner(R.drawable.fruitsbanner,R.string.fresh_fruits),
        Banner(R.drawable.vegetablesbanner,R.string.vegetables),
        Banner(R.drawable.beveragesbanner,R.string.beverages)
    )
}