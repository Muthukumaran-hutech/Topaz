package com.example.topaz.Activities

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.example.topaz.R
import com.example.topaz.databinding.ActivityAccountInformationBinding
import com.example.topaz.databinding.ActivityFaqBinding

class FaqActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFaqBinding

    var faq1Clicked=false
    var faq2Clicked = false
    var faq3Clicked=false
    var faq4Clicked=false
    var faq5Clicked=false
    var faq6Clicked=false
    var faq7Clicked=false
    var faq8Clicked=false
    var faq9Clicked=false


    lateinit var activity: Activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFaqBinding.inflate(layoutInflater)
        setContentView(binding.root)


        activity = this

        binding.q1.setOnClickListener {

            if(!faq1Clicked) {
                faq1Clicked = true
                binding.q1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rectangle_471,0,getIconBasedOnStatus(faq1Clicked),0)
                binding.q1ans.visibility=View.VISIBLE
            }
            else{
                faq1Clicked=false
                binding.q1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rectangle_471,0,getIconBasedOnStatus(faq1Clicked),0)
                binding.q1ans.visibility=View.GONE
            }

        }

        binding.q2.setOnClickListener {
            if(!faq2Clicked) {
                faq2Clicked = true
                binding.q2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rectangle_471,0,getIconBasedOnStatus(faq2Clicked),0)
                binding.q2ans.visibility=View.VISIBLE
            }
            else{
                faq2Clicked=false
                binding.q2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rectangle_471,0,getIconBasedOnStatus(faq2Clicked),0)
                binding.q2ans.visibility=View.GONE
            }
        }



        binding.q3.setOnClickListener {
            if(!faq3Clicked) {
                faq3Clicked = true
                binding.q3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rectangle_471,0,getIconBasedOnStatus(faq3Clicked),0)
                binding.q3ans.visibility=View.VISIBLE
            }
            else{
                faq3Clicked=false
                binding.q3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rectangle_471,0,getIconBasedOnStatus(faq3Clicked),0)
                binding.q3ans.visibility=View.GONE
            }
        }


        binding.q4.setOnClickListener {
            if(!faq4Clicked) {
                faq4Clicked = true
                binding.q4.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rectangle_471,0,getIconBasedOnStatus(faq4Clicked),0)
                binding.q4ans.visibility=View.VISIBLE
            }
            else{
                faq4Clicked=false
                binding.q4.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rectangle_471,0,getIconBasedOnStatus(faq4Clicked),0)
                binding.q4ans.visibility=View.GONE
            }
        }


        binding.q6.setOnClickListener {
            if(!faq6Clicked) {
                faq6Clicked = true
                binding.q6.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rectangle_471,0,getIconBasedOnStatus(faq6Clicked),0)
                binding.q6.visibility=View.VISIBLE
            }
            else{
                faq6Clicked=false
                binding.q6.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rectangle_471,0,getIconBasedOnStatus(faq6Clicked),0)
                binding.q6ans.visibility=View.GONE
            }
        }

        binding.q7.setOnClickListener {
            if(!faq7Clicked) {
                faq7Clicked = true
                binding.q7.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rectangle_471,0,getIconBasedOnStatus(faq7Clicked),0)
                binding.q7.visibility=View.VISIBLE
            }
            else{
                faq7Clicked=false
                binding.q7.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rectangle_471,0,getIconBasedOnStatus(faq7Clicked),0)
                binding.q7ans.visibility=View.GONE
            }
        }

        binding.q7.setOnClickListener {
            if(!faq7Clicked) {
                faq7Clicked = true
                binding.q7.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rectangle_471,0,getIconBasedOnStatus(faq7Clicked),0)
                binding.q7.visibility=View.VISIBLE
            }
            else{
                faq7Clicked=false
                binding.q7.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rectangle_471,0,getIconBasedOnStatus(faq7Clicked),0)
                binding.q7ans.visibility=View.GONE
            }
        }

        binding.q9.setOnClickListener {
            if(!faq9Clicked) {
                faq9Clicked = true
                binding.q9.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rectangle_471,0,getIconBasedOnStatus(faq7Clicked),0)
                binding.q9ans.visibility=View.VISIBLE
            }
            else{
                faq9Clicked=false
                binding.q9.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rectangle_471,0,getIconBasedOnStatus(faq7Clicked),0)
                binding.q9ans.visibility=View.GONE
            }
        }








        binding.backarrow.setOnClickListener{
            startActivity(Intent(activity,MyAccount::class.java))
            finish()
        }


    }


    fun getIconBasedOnStatus(status:Boolean):Int{
        var drawable=0
        if(status){
            drawable=R.drawable.minus

        }
        else{
            drawable=R.drawable.pls
        }


        return drawable
    }
}