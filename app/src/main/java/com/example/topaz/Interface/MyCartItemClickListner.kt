package com.example.topaz.Interface

import com.example.topaz.Models.CartList
import com.example.topaz.Models.CartProductList
import com.example.topaz.Models.CategoriesModel

interface MyCartItemClickListner {
    fun MyCartItemClickListner(data: CartProductList)
}