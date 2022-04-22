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
import com.example.topaz.databinding.ActivityIntroSliderBinding
import com.example.topaz.databinding.ActivityOtpVerfificationBinding
import java.util.ArrayList


class IntroSlider : AppCompatActivity() {

    private lateinit var binding: ActivityIntroSliderBinding
    lateinit var viewpager: ViewPager2
    lateinit var activity: Activity
    private val fragmentList = ArrayList<Fragment>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroSliderBinding.inflate(layoutInflater)
        setContentView(binding.root)


        activity = this

        val adapter = IntroSliderAdapter(this)
        binding.viewPager.adapter = adapter
        fragmentList.addAll(

            listOf( SliderFragment(), SliderFragment2(), SliderFragment3() ) )

        adapter.setFragmentList(fragmentList)
        binding.indicatorLayout?.setIndicatorCount(adapter.itemCount)
        binding.indicatorLayout?.selectCurrentPosition(0)
          registerListeners()
    }

    private fun registerListeners() {
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {


            override fun onPageSelected(position: Int) {
                //   indicatorLayout.selectCurrentPosition(position)
                if (position < fragmentList.lastIndex) {
                    binding.btnSkip.visibility = View.VISIBLE
                    binding.btnNext.visibility = View.VISIBLE
                    binding.getStart.visibility = View.GONE

                } else {
                    binding.btnSkip.visibility = View.GONE
                    binding.btnNext.visibility = View.GONE
                    binding.getStart.visibility = View.VISIBLE
                }
            }
        })
        binding.btnSkip.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        binding.btnNext.setOnClickListener {
            viewpager.currentItem++
        }

        binding.getStart.setOnClickListener {
            val position =  binding.viewPager.currentItem
            if (position < fragmentList.lastIndex) {
                binding.viewPager.currentItem = position + 1
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }


    }

}