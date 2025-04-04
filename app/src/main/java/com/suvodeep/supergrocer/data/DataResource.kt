package com.suvodeep.supergrocer.data

//import androidx.annotation.StringRes
import com.suvodeep.supergrocer.R

object DataResource {
    fun getCategories():List<Categories>{
        return listOf(
            Categories(R.string.fresh_fruits, R.drawable.fruits),
            Categories(R.string.vegetables, R.drawable.vegetable),
            Categories(R.string.kitchen_essentials, R.drawable.kitchen),
            Categories(R.string.dry_fruits, R.drawable.dryfriutes),
            Categories(R.string.beverages, R.drawable.beverages),
            Categories(R.string.sweet_tooth, R.drawable.sweet_tooth),
            Categories(R.string.stationery, R.drawable.stationery),
            Categories(R.string.pet_food, R.drawable.pet_food),
            Categories(R.string.packaged_food, R.drawable.packaged_food),
            Categories(R.string.munchies, R.drawable.munchies),
            Categories(R.string.cleaning_essentials, R.drawable.cleaning_essentials),
            Categories(R.string.bread_biscuits, R.drawable.bread_biscuits)
//            Categories(R.string.bath_body, R.drawable.bath_body)
                )

    }

//    fun getItems(@StringRes itemCategoryId: Int):List<Items>{
//        return listOf(
//            Items(R.string.banana, R.string.fresh_fruits, "1 kg", 100,10, R.drawable.banana),
//            Items(R.string.pineapple, R.string.fresh_fruits, "1 kg", 100,20, R.drawable.pineapple),
//            Items(R.string.papaya, R.string.fresh_fruits, "1 kg", 100,15, R.drawable.papaya_ripe),
//            Items(R.string.pomegranate, R.string.fresh_fruits, "1 kg", 100,12, R.drawable.promagraneta),
//            Items(R.string.shimla_apple, R.string.fresh_fruits, "1 kg", 100,30, R.drawable.shimla_apple),
//            Items(R.string.pepsi, R.string.beverages, "1 kg", 100,5, R.drawable.pepsi)
//        ).filter {
//            it.itemCategoryId==itemCategoryId
//        }
//    }
}