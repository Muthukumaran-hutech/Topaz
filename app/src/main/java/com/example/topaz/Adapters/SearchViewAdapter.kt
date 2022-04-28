package com.example.topaz.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.topaz.Activities.SearchActivity
import com.example.topaz.R

class SearchViewAdapter: RecyclerView.Adapter<SearchViewAdapter.MyViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_list_item, parent, false)
        return SearchViewAdapter.MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //val currentItem = searchList[position]
        //holder.Heading.text = currentItem.toString()

    }

    override fun getItemCount(): Int {
       // return searchList.size
        return 1
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val Heading:TextView = itemView.findViewById(R.id.search_textView)

    }
}