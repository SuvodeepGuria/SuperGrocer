package com.suvodeep.supergrocer.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Items(
    @StringRes val stringResourceId: Int,
    @StringRes val itemCategoryId: Int,
    val itemQuantity: String,
    val itemPrice: Int,
    val discount:Int,
    @DrawableRes val imageResourceId: Int
)
