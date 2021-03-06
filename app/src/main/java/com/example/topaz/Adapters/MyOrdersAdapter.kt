package com.example.topaz.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.topaz.Activities.MyOrders
import com.example.topaz.Interface.OrderItemClickListner
import com.example.topaz.Models.OrderModels
import com.example.topaz.R
import java.util.ArrayList

class MyOrdersAdapter(
    var orderListItem: ArrayList<OrderModels>,
    var orderItemClickListner: OrderItemClickListner,
    var context: Context
) : RecyclerView.Adapter<MyOrdersAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_my_orders_adapter, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = orderListItem[position]
        holder.bindItems(orderListItem, position, orderItemClickListner, context)
    }

    override fun getItemCount(): Int {
        return orderListItem.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var status = itemView.findViewById<TextView>(R.id.qutrequest)
        var orderId = itemView.findViewById<TextView>(R.id.oderi)
        var date = itemView.findViewById<TextView>(R.id.te)
        var quantity = itemView.findViewById<TextView>(R.id.unit)
        var buttonView = itemView.findViewById<Button>(R.id.detailsbtn)

        fun bindItems(
            orderListItem: ArrayList<OrderModels>,
            position: Int,
            orderItemClickListner: OrderItemClickListner,
            context: Context
        ) {
            status.text = orderListItem[position].QuotationStatus
            orderId.text = orderListItem[position].quotationID
            date.text = orderListItem[position].quotationDate
            quantity.text = orderListItem[position].quotationquantity.toString()

            if (status.text =="Order Placed"){
                status.getResources().getColor(R.color.black)
            }

            if (status.text =="Qutation Requested"){
                status.getResources().getColor(R.color.blue)
            }

            if (status.text =="Quotation Received"){
                status.getResources().getColor(R.color.green)
            }


            if (status.text =="Order Cancelled"){
                status.getResources().getColor(R.color.red)
            }




            buttonView.setOnClickListener {
                orderItemClickListner.OrderItemClickListner(orderListItem[position])
            }

        }


    }
}