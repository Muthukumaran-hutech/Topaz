package com.example.topaz.Activities

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.topaz.Fragments.SliderFragment
import com.example.topaz.Fragments.SliderFragment2
import com.example.topaz.Fragments.SliderFragment3
import com.example.topaz.R
import java.util.ArrayList

class IntroSlider : AppCompatActivity() {
    private var skipBtn: TextView? = null
    lateinit var nextBtn: ImageButton
    lateinit var view_pager: ViewPager
    private var getStarted: Button? = null
    var indicator1: TextView? = null
    var indicator2: TextView? = null
    var indicator3: TextView? = null
    val fragment = SliderFragment()
    val fragment2 = SliderFragment2()
    val fragment3 = SliderFragment3()
    lateinit var adapter: myPagerAdapter
    lateinit var activity: Activity


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro_slider)



        skipBtn = findViewById(R.id.btn_skip)
        nextBtn = findViewById(R.id.btn_next)
        view_pager =findViewById(R.id.view_pager)




        adapter = myPagerAdapter(supportFragmentManager)
        adapter.list.add(fragment)
        adapter.list.add(fragment2)
        adapter.list.add(fragment3)
        getStarted = findViewById(R.id.get_start)
        activity = this

        view_pager?.adapter = adapter
        skipBtn?.setOnClickListener{
            startActivity(Intent(activity,LoginActivity::class.java))
            finish()
        }
        /* getStarted?.setOnClickListener{
             startActivity(Intent(activity,LoginActivity::class.java))
             finish()
         }*/
        nextBtn.setOnClickListener {
            view_pager.currentItem++
        }
        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }


            override fun onPageSelected(position: Int) {
                if (position == adapter.list.size - 1) {
                    getStarted?.setOnClickListener {
                        startActivity(Intent(activity, EmailLogin::class.java))
                        finish()

                    }
                } else {
                    nextBtn.setOnClickListener {
                        view_pager.currentItem++
                    }
                }
                when (view_pager.currentItem) {
                    0 -> {
                       indicator1?.setTextColor(Color.parseColor("#00ADEF"))
                        indicator2?.setTextColor(Color.parseColor("#FFFFFFFF"))
                        indicator3?.setTextColor(Color.parseColor("#FFFFFFFF"))
                        indicator1?.visibility = View.VISIBLE
                        indicator2?.visibility = View.VISIBLE
                        indicator3?.visibility = View.VISIBLE
                        getStarted?.visibility = View.GONE
                        skipBtn?.visibility = View.VISIBLE
                        nextBtn.visibility = View.VISIBLE
                    }
                    1 -> {
                        indicator1?.setTextColor(Color.parseColor("#FFFFFFFF"))
                        indicator2?.setTextColor(Color.parseColor("#00ADEF"))
                        indicator3?.setTextColor(Color.parseColor("#FFFFFFFF"))
                        indicator1?.visibility = View.VISIBLE
                        indicator2?.visibility = View.VISIBLE
                        indicator3?.visibility = View.VISIBLE
                        getStarted?.visibility = View.GONE
                        skipBtn?.visibility = View.VISIBLE
                        nextBtn.visibility = View.VISIBLE

                    }
                    2 -> {
                        indicator1?.visibility = View.GONE
                        indicator2?.visibility = View.GONE
                        indicator3?.visibility = View.GONE
                        getStarted?.visibility = View.VISIBLE
                        skipBtn?.visibility = View.GONE
                        nextBtn.visibility = View.GONE
                    }
                }
            }


            override fun onPageScrollStateChanged(state: Int) {

            }

        })

    /*    val timer = object: CountDownTimer(20000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {}
        }
        timer.start()*/
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