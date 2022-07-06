package com.example.topaz.Adapters

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.topaz.Models.AddModels
import com.example.topaz.R

class HomeSliderAdapter(var context: Context,var sliderlist:List<AddModels>): BaseAdapter() {
    lateinit var inflater: LayoutInflater

    init {
       inflater=(LayoutInflater.from(context))
    }

    override fun getCount(): Int {
        return  sliderlist.size
    }

    override fun getItem(position: Int): Any {
       return sliderlist.size
    }

    override fun getItemId(position: Int): Long {

        return 0

    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        Log.d("Slider list", sliderlist.size.toString())
        var view=convertView
        if(view==null) {
            view = inflater.inflate(R.layout.home_slider_layout, parent,false)
        }

        var adImage=view?.findViewById<ImageView>(R.id.advertisement_image)
        var adTitle=view?.findViewById<TextView>(R.id.advertisement_title)
        var adDescription=view?.findViewById<TextView>(R.id.advertisement_description)

        try {
            val decodedString: ByteArray = Base64.decode(

                sliderlist.get(position).addImage,
                Base64.DEFAULT
            )

            val bitmap =
                BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)

            if (bitmap == null) {
                adTitle?.visibility = View.GONE
                adDescription?.visibility = View.GONE
                adImage?.setImageResource(R.drawable.ic_baseline_image_24)
            } else {
                adTitle?.visibility = View.VISIBLE
                adDescription?.visibility = View.VISIBLE
                adImage?.setImageBitmap(bitmap)
                //Commenting as layout change needs to be performed
                //adTitle?.text = sliderlist.get(position).addtitle
                //adDescription?.text = sliderlist.get(position).addDescription

            }
        }
        catch (e:Exception){
            e.printStackTrace()
        }





        return view!!

    }


}