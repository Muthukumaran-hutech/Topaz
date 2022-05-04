package com.example.topaz.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class NotificationAdapter (fa: FragmentActivity) : FragmentStateAdapter(fa) {
    private val notificationList = ArrayList<Fragment>()
    override fun getItemCount(): Int {
        return notificationList.size
    }

    override fun createFragment(position: Int): Fragment {
        return notificationList[position]
    }

    fun setNotificationList(list: List<Fragment>) {
        notificationList.clear()
        notificationList.addAll(list)
        notifyDataSetChanged()
    }
}