package com.example.topaz.Adapters

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.topaz.R

class ProductImageSlider(val context: Context, var imagelist:ArrayList<String>): PagerAdapter() {
    override fun getCount(): Int {

        return imagelist.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {


        return view === `object`


    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {//Instantiating the view
        var view:View=View(context)
        try {

             view=
                (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(
                    R.layout.productslider_item_layout, null
                )

            val image = view.findViewById<ImageView>(R.id.productSliderImage)
            val decodedString: ByteArray = Base64.decode(
                imagelist[position],
                Base64.DEFAULT
            )
            val bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)

            Glide.with(context)
                .load(bitmap)
                .placeholder(R.drawable.ic_baseline_image_24)
                .error(R.drawable.ic_baseline_image_24)
                .into(image)



            val vp = container as ViewPager
            vp.addView(view, 0)
            return view


        }
        catch (e:java.lang.Exception){
            e.toString()
        }

        return view

    }


    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        try {
            val vp = container as ViewPager2
            val view = `object` as View
            vp.removeView(view)
        }
        catch (e:Exception){
            e.toString()
        }
    }


}