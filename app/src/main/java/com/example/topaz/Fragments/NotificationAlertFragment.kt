package com.example.topaz.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.topaz.Adapters.SubAdapter.AlertAdapter
import com.example.topaz.R


class NotificationAlertFragment : Fragment() {

    private lateinit var alertRecyclerView: RecyclerView
    private lateinit var alertAdapter: AlertAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification_alert, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       // alertRecyclerView = getView()findViewById(R.id.categories_recyclerview)
        alertAdapter = AlertAdapter()

        alertRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
        alertRecyclerView.adapter = alertAdapter
    }




}