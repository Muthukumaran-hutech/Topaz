package com.example.topaz.Adapters

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.topaz.Models.FeetModel
import com.example.topaz.Models.QuatationListModel
import com.example.topaz.Models.ThicknessModel
import com.example.topaz.R
import com.example.topaz.Utility.Util
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.database.FirebaseDatabase

class QuotationListAdapter(var quotationlist:List<QuatationListModel>,var context:Context):RecyclerView.Adapter<QuotationListAdapter.QuotationViewHolder>()  {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuotationViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.item_quotation_layout,parent,false)
        return QuotationViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuotationViewHolder, position: Int) {
        holder.bindItems(quatationListModel = quotationlist[position],position,context)
        holder.enabledetails.setOnClickListener {
            quotationlist[position].expanded=true
            updateExpandStatus(quotationlist[position])
            //notifyDataSetChanged()
        }

        holder.disabledetails.setOnClickListener {
            quotationlist[position].expanded=false
            updateExpandStatus(quotationlist[position])
           // notifyDataSetChanged()
        }

    }

    private fun updateExpandStatus(quatationListModel: QuatationListModel) {
        try{
            val dataref=FirebaseDatabase.getInstance().getReference("QuotationProductList")
                .child(quatationListModel.quotationId).child("Products").child(quatationListModel.ProductId.toString())
            dataref.child("expanded").setValue(quatationListModel.expanded)


        }
        catch (e:Exception){
            e.toString()
        }

    }

    override fun getItemCount(): Int {

        return quotationlist.size

    }


    class QuotationViewHolder(itemview:View): RecyclerView.ViewHolder(itemview){

        var enabledetails=itemview.findViewById<ImageView>(R.id.showDetailsLayout)
        var disabledetails= itemview.findViewById<ImageView>(R.id.hideDetailsLayout)
        var detailslayout = itemview.findViewById<ConstraintLayout>(R.id.quatationDetailsLayout)
        var quantitylayout = itemview.findViewById<LinearLayout>(R.id.quantityLayout)
        var headerquantity= itemview.findViewById<TextView>(R.id.headerQuantity)
        var headerprice = itemview.findViewById<TextView>(R.id.headerPrice)
        var headerProductTitle = itemview.findViewById<TextView>(R.id.headerProductTitle)
        var thicknessrecyclerview = itemview.findViewById<RecyclerView>(R.id.product_thickness_recycler)
        var productimage=  itemview.findViewById<ImageView>(R.id.innercatimage)


        var producttitle= itemview.findViewById<TextView>(R.id.productDetailTitle)

        var quantityfield= itemview.findViewById<AppCompatButton>(R.id.quantityEntry)
        var sqfeetfield = itemview.findViewById<EditText>(R.id.priceEntry)
        var totalfield= itemview.findViewById<TextView>(R.id.totalValue)
        var sizelistrecyclerview=  itemview.findViewById<RecyclerView>(R.id.feet_recyclyer)
        var description= itemview.findViewById<EditText>(R.id.desc_details)




        fun bindItems(quatationListModel: QuatationListModel, position: Int, context: Context){
              val thickness_list=ArrayList<ThicknessModel>()
              val sizelist = ArrayList<FeetModel>()
            try {
                //Do implementation


                val sqfeetprice = quatationListModel.ProductPrice
                val size = Util.extractNumbersFromString(quatationListModel.ProductSize, "Size")
                val totalprice =
                    sqfeetprice.toDouble().toInt() * size[0].toDouble().toInt() * size[1].toDouble()
                        .toInt()*quatationListModel.quantity.toInt()

                //Setting header layout item
                headerProductTitle.text = quatationListModel.ProductTitle

                headerprice.text = context.getString(R.string.price_label)+" "+context.getString(R.string.Rs)+ totalprice.toString()

                headerquantity.text = context.getString(R.string.quantity_label) + quatationListModel.quantity
                quatationListModel.totalvalue=totalprice.toString()


                //Settting quotation details
                producttitle.text=quatationListModel.ProductTitle+","+quatationListModel.ProductThickness

                Glide.with(context)
                    .load(Util.getBitmapFromBase64(quatationListModel.productImage))
                    .error(R.drawable.ic_baseline_image_24)
                    .into(productimage)
                quantityfield.setText(quatationListModel.quantity.toString())
                sqfeetfield.setText(quatationListModel.ProductPrice)
                totalfield.setText(totalprice.toString())
                description.setText(quatationListModel.ProductDescription)

                sizelist.add(FeetModel(quatationListModel.ProductSize.toString()))

                val sizeAdapter= FeetAdapter(sizelist)

                sizelistrecyclerview.layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
                sizelistrecyclerview.setHasFixedSize(true)
                sizelistrecyclerview.adapter= sizeAdapter
                //Setting thickness value[Using recyclerview because in case  if data is provided as a list then this method can be reused]
                thickness_list.add(ThicknessModel(quatationListModel.ProductThickness.toString()))
                val productAdapter = ProductQuotationAdapter(thickness_list)
                thicknessrecyclerview.layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
                thicknessrecyclerview.adapter=productAdapter



                if(quatationListModel.expanded){
                    enabledetails.visibility = View.GONE
                    detailslayout.visibility = View.VISIBLE
                    disabledetails.visibility = View.VISIBLE
                    quantitylayout.visibility = View.INVISIBLE
                }
                else{
                    disabledetails.visibility = View.GONE
                    detailslayout.visibility = View.GONE
                    enabledetails.visibility = View.VISIBLE
                    quantitylayout.visibility = View.VISIBLE

                }

                quantityfield.setText(quatationListModel.quantity)

                quantityfield.setOnClickListener {
                    showBottomSheetDialog(context,quatationListModel,position)
                }

                quantityfield.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {

                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        try{

                            if(s.toString().isNotEmpty()){
                                val size1 = Util.extractNumbersFromString(quatationListModel.ProductSize,"Size")
                                val length = size1[0].toDouble().toInt()
                                val breadth = size1[1].toDouble().toInt()

                                val totalPrice =
                                    s.toString().toInt() * quatationListModel.ProductPrice.toDouble()!!.toInt() * (length * breadth)
                                totalfield.text=totalPrice.toString()
                                quatationListModel.quantity=s.toString()
                                quatationListModel.totalvalue=totalPrice.toString()

                                //-------Update total value-----------------------//
                                updateData(quatationListModel = quatationListModel,context = context,
                                    type = context.getString(R.string.updateqty))
                                updateData(quatationListModel = quatationListModel,context = context,
                                    type = context.getString(R.string.updatetotalvalue))
                            }



                        }
                        catch (e:Exception){
                            e.toString()
                        }
                    }

                    override fun afterTextChanged(s: Editable?) {


                    }
                })

                //Description text watcher

                description.setOnFocusChangeListener(object : View.OnFocusChangeListener {
                    override fun onFocusChange(v: View?, hasFocus: Boolean) {
                        if(!hasFocus){

                            if(!description.text.toString().isEmpty()) {
                                quatationListModel.ProductDescription = description.text.toString()
                                updateData(
                                    quatationListModel = quatationListModel,
                                    type = context.getString(R.string.updatequotationdesc),
                                    context
                                )
                            }
                            else{
                                Toast.makeText(context,"Requirement details cannot be empty",Toast.LENGTH_LONG).show()
                            }

                        }
                    }
                })
               /* description.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {

                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        try{
                            quatationListModel.ProductDescription=s.toString()
                            updateData(quatationListModel = quatationListModel,type = context.getString(R.string.updatequotationdesc),context)


                        }
                        catch (e:Exception){
                            e.toString()
                        }
                    }

                    override fun afterTextChanged(s: Editable?) {

                    }
                })*/




            }
            catch (e:Exception){
                e.printStackTrace()
            }
        }

        private fun showBottomSheetDialog(
            context: Context,
            quatationListModel: QuatationListModel,
            position: Int,) {
            try{

                val dialog = BottomSheetDialog(context, R.style.AppBottomSheetDialogTheme)

                val view = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_dialog, null)


                val btnConfirm = view.findViewById<Button>(R.id.idBtnconfirm)
                val quantity = view.findViewById<EditText>(R.id.count_text)
                val increment = view.findViewById<ImageButton>(R.id.incrementQuantity)
                val decrement = view.findViewById<ImageButton>(R.id.decrementQuantity)
                var closedialog= view.findViewById<ImageView>(R.id.closeDialog)

                quantity.setText(quatationListModel.quantity.toString())

                btnConfirm.setOnClickListener {
                    if(quantity.text.toString().isNotEmpty() || quantity.text.toString()!="") {
                        quantityfield.setText(quantity.text.toString())
                        //Update quantity value in database
                        dialog.dismiss()
                    }
                }

                closedialog.setOnClickListener {
                    dialog.dismiss()
                }


                decrement.setOnClickListener {

                    try {
                        if (quantity.text.toString().isNotEmpty()) {
                            var qty = quantity.text.toString().toInt()
                            qty--
                            if (qty >=1) {

                                quantity.setText(qty.toString())
                            }

                        }
                    }
                    catch (e:Exception){
                        e.toString()
                    }

                }

                increment.setOnClickListener {
                    try{
                        if(quantity.text.toString().isNotEmpty()){
                            //Check the maximum product user can select
                            var qty=quantity.text.toString().toInt()
                            qty++
                            quantity.setText(qty.toString())
                        }
                    }
                    catch (e:Exception){
                        e.toString()
                    }

                }
                dialog.setCancelable(false)

                dialog.setContentView(view)
                dialog.show()

            }
            catch (e:Exception){
                e.toString()
            }
        }


        fun initializeViews(itemview: View){

        }



        fun updateData(quatationListModel: QuatationListModel, type:String, context: Context){
            try{
                val dataref=FirebaseDatabase.getInstance().getReference("QuotationProductList")
                    .child(quatationListModel.quotationId).child("Products").child(quatationListModel.ProductId.toString())

                when(type){

                    context.getString(R.string.updateqty)->{

                        dataref.child("quantity").setValue(quatationListModel.quantity)
                    }

                    context.getString(R.string.updatetotalvalue)->{
                        dataref.child("totalvalue").setValue(quatationListModel.totalvalue)


                    }

                    context.getString(R.string.updatequotationdesc)->{
                        dataref.child("productDescription").setValue(quatationListModel.ProductDescription)


                    }

                }
            }
            catch (e:Exception){

            }
        }



    }
}