package com.example.topaz.Activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.topaz.Adapters.HomeCategoriesAdapter
import com.example.topaz.Adapters.SubAdapter.AlertAdapter
import com.example.topaz.Fragments.*
import com.example.topaz.R
import java.util.ArrayList

class Notifications : AppCompatActivity() {

    val alertFragment = NotificationAlertFragment()
    val notificationFragment2 = NotificationOfferFragment()
    lateinit var adapter: AlertAdapter
    lateinit var activity: Activity
    lateinit var view_pager2: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications)

        view_pager2 =findViewById(R.id.view_pager2)
        activity = this





    /*    view_pager2?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                TODO("Not yet implemented")
            }

            override fun onPageScrollStateChanged(state: Int) {
                TODO("Not yet implemented")
            }


            class myPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {

                val list: MutableList<Fragment> = ArrayList()


                override fun getCount(): Int {
                    return list.size
                }

                override fun getItem(position: Int): Fragment {
                    return list[position]
                }

            }
        }
*/
    }
}