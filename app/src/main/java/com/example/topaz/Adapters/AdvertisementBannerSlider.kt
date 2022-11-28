package com.example.topaz.Adapters

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.topaz.Models.AddModels
import com.example.topaz.R

class AdvertisementBannerSlider(var context:Context, var sliderlist:List<AddModels>):
    PagerAdapter() {
    override fun getCount(): Int {
        return sliderlist.size
    }



    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        var view=View(context)
        try{
            view=
                (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(
                    R.layout.home_slider_layout, null
                )

            val adImage=view?.findViewById<ImageView>(R.id.advertisement_image)
            val adTitle=view?.findViewById<TextView>(R.id.advertisement_title)
            val adDescription=view?.findViewById<TextView>(R.id.advertisement_description)

                val decodedString: ByteArray = Base64.decode(

                    sliderlist.get(position).addImage,
                    Base64.DEFAULT
                )

                val bitmap =
                    BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)

                if (bitmap == null) {
                    adTitle?.visibility = View.GONE
                    adDescription?.visibility = View.GONE
                } else {
                    adTitle?.visibility = View.VISIBLE
                    adDescription?.visibility = View.VISIBLE
                    //adImage?.setImageBitmap(bitmap)
                    //Commenting as layout change needs to be performed
                    //adTitle?.text = sliderlist.get(position).addtitle
                    //adDescription?.text = sliderlist.get(position).addDescription

                }


            Glide.with(context)
                .load(bitmap)
                .placeholder(R.drawable.ic_baseline_image_24)
                .error(R.drawable.ic_baseline_image_24)
                .into(adImage!!)


            val vp=container as ViewPager
            vp.addView(view,0)









        }
        catch (e:Exception){
            e.toString()
        }
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        try {
            val vp = container as ViewPager
            val view = `object` as View
            vp.removeView(view)
        }
        catch (e:Exception){
            e.toString()
        }
    }


}