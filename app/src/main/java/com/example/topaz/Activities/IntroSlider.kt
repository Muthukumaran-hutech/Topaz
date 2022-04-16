package com.example.topaz.Activities

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.topaz.Adapters.IntroSliderAdapter
import com.example.topaz.Activities.IndicatorLayout
import com.example.topaz.Fragments.SliderFragment
import com.example.topaz.Fragments.SliderFragment2
import com.example.topaz.Fragments.SliderFragment3
import com.example.topaz.R
import java.util.ArrayList


class IntroSlider : AppCompatActivity() {
    private var skipBtn: TextView? = null
    lateinit var nextBtn: ImageButton
    lateinit var viewpager: ViewPager2
    //private var indicatorLayout: SeekBar? = null
    private var getStarted: Button? = null
    private lateinit var indicatorLayout: IndicatorLayout
    //lateinit var adapter1: MyPagerAdapter
    lateinit var activity: Activity

    // lateinit var indicatorLayout: SeekBar
    private val fragmentList = ArrayList<Fragment>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // making the status bar transparent
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ) {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        setContentView(R.layout.activity_intro_slider)




        skipBtn = findViewById(R.id.btn_skip)
        nextBtn = findViewById(R.id.btn_next)
         indicatorLayout = findViewById(R.id.indicatorLayout)
        viewpager = findViewById(R.id.view_pager)
        getStarted = findViewById(R.id.get_start)
        activity = this

        val adapter = IntroSliderAdapter(this)
        viewpager.adapter = adapter
        fragmentList.addAll(
            listOf(
                SliderFragment(), SliderFragment2(), SliderFragment3()

            )
        )

        adapter.setFragmentList(fragmentList)
          indicatorLayout.setIndicatorCount(adapter.itemCount)
          indicatorLayout.selectCurrentPosition(0)
          registerListeners()
    }

    private fun registerListeners() {
        viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {


            override fun onPageSelected(position: Int) {
                //   indicatorLayout.selectCurrentPosition(position)
                if (position < fragmentList.lastIndex) {
                    skipBtn?.visibility = View.VISIBLE
                    nextBtn.visibility = View.VISIBLE
                    getStarted?.visibility = View.GONE

                } else {
                    skipBtn?.visibility = View.GONE
                    nextBtn.visibility = View.GONE
                    getStarted?.visibility = View.VISIBLE
                }
            }
        })
        skipBtn?.setOnClickListener {
            startActivity(Intent(this, EmailLogin::class.java))
            finish()
        }

        nextBtn.setOnClickListener {
            viewpager.currentItem++
        }

        getStarted?.setOnClickListener {
            val position = viewpager.currentItem
            if (position < fragmentList.lastIndex) {
                viewpager.currentItem = position + 1
            } else {
                startActivity(Intent(this, EmailLogin::class.java))
                finish()
            }
        }


    }

}