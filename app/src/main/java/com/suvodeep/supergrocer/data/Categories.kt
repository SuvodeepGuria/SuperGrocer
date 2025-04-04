package com.suvodeep.supergrocer.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Categories(
    @StringRes val stringResId:Int,
    @DrawableRes val imageResId:Int
)