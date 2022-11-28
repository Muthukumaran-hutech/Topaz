package com.example.topaz.Activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.topaz.Adapters.HomeCategoriesAdapter
import com.example.topaz.Adapters.IntroSliderAdapter
import com.example.topaz.Adapters.NotificationAdapter
import com.example.topaz.Fragments.*
import com.example.topaz.R
import com.example.topaz.databinding.ActivityIntroSliderBinding
import com.example.topaz.databinding.ActivityNotificationsBinding
import com.google.android.material.tabs.TabLayoutMediator
import java.util.ArrayList

class Notifications : AppCompatActivity() {


    private lateinit var binding: ActivityNotificationsBinding
    lateinit var activity: Activity
    private val notifyList = ArrayList<Fragment>()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.notificationsBackArrow.setOnClickListener{
           // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            finish()
        }

        activity = this
        val adapter = NotificationAdapter(this)
       // binding.tabLayout.setupWithViewPager(binding.viewPager2)

        binding.viewPager2.adapter = adapter

        notifyList.addAll(

            listOf( AlertFragment(), FragmentOffers() ) )
        adapter.setNotificationList(notifyList)
        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            if(position==0) {
                tab.text = "Alert"
            }
            else{
                tab.text = "Offer"
            }
        }.attach()
        //binding.indicatorLayout?.setIndicatorCount(notifyList.size)



    }

}