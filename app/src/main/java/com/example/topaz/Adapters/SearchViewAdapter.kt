package com.example.topaz.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.topaz.Activities.SearchActivity
import com.example.topaz.Interface.SearchListPageItemClickListner
import com.example.topaz.Models.SearchListModel
import com.example.topaz.R

class SearchViewAdapter(var searchList: ArrayList<SearchListModel>, var searchListPageItemClickListner: SearchListPageItemClickListner): RecyclerView.Adapter<SearchViewAdapter.MyViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = searchList[position]
        holder.bindItems(searchList,position,searchListPageItemClickListner)
        //holder.search.text = currentItem.toString()

    }

    override fun getItemCount(): Int {
        return searchList.size

    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val Heading:TextView = itemView.findViewById(R.id.search_textView)
       // val search:TextView = itemView.findViewById(R.id.search_icon)

        fun bindItems(searchList:List<SearchListModel>, position: Int, searchListPageItemClickListner: SearchListPageItemClickListner){

            try {
                Heading.text = searchList[position].SearchText

                Heading.setOnClickListener {
                    //Onclick will trigger the interface in activity
                    searchListPageItemClickListner.SearchListPageItemClickListner(searchList[position])
                }
            }
            catch (e:Exception){
                e.printStackTrace()
            }
        }

    }

    public fun clearList(){
        searchList.clear()
    }
}