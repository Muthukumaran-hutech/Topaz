package com.example.topaz.Activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.topaz.Adapters.FeetAdapter
import com.example.topaz.Adapters.ProductQuotationAdapter
import com.example.topaz.Models.FeetModel
import com.example.topaz.Models.ProductDetailsModel
import com.example.topaz.Models.ProductQuotationsModel
import com.example.topaz.Models.ThicknessModel
import com.example.topaz.R
import com.example.topaz.databinding.ActivityProductQuotationBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_product_quotation.*

class ProductQuotation : AppCompatActivity() {


    private lateinit var binding: ActivityProductQuotationBinding
    private lateinit var productAdapter: ProductQuotationAdapter
    private lateinit var feetAdapter: FeetAdapter
    lateinit var activity: Activity
    var productList = ArrayList<ProductQuotationsModel>()
    var thickness_list = ArrayList<ThicknessModel>()
    var feet_list = ArrayList<FeetModel>()
    //lateinit var btnShowBottomSheet: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductQuotationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        activity = this
        setSupportActionBar(binding.quotationToolbar)
        supportActionBar?.title = ""

        // onApiCallProductDetails()
        productAdapter = ProductQuotationAdapter(thickness_list)
        feetAdapter = FeetAdapter(feet_list)

        val item = intent.getParcelableExtra<ProductDetailsModel>("extra_item")
        var img = item?.ProductImage
        var title = item?.ProductTitle
        var price = item?.ProductPrice
        var size = item?.ProductSize
        var thickness = item?.ProductThickness
        var brand = item?.ProductBrand
        var woodType = item?.ProductWoodType

        //Log.d(TAG, "onCreate: "+title)
        binding.priceentry.setText(price)
        binding.woodMaterialName.setText(title)
        binding.thic1.setText(thickness)
        binding.priceentry.isEnabled = false
        binding.totalvalue.isEnabled = false

        var quantity = binding.qutyentry.text.toString()

        binding.qutyentry.setOnClickListener {
            val dialog = BottomSheetDialog(this)

            val view = layoutInflater.inflate(R.layout.bottom_sheet_dialog, null)

            val btnConfirm = view.findViewById<Button>(R.id.idBtnconfirm)

            btnConfirm.setOnClickListener {
                dialog.dismiss()
            }
            dialog.setCancelable(false)

            dialog.setContentView(view)
            dialog.show()
        }

        binding.qutyentry.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if (s.toString().isNotEmpty()){
                    val totalValue = s.toString().toInt() * price!!.toInt()
                    if (totalValue<=0){
                        binding.totalvalue.setText(price)
                    }else{
                        binding.totalvalue.setText(totalValue.toString())
                    }
                }else{
                   //do nothing
                    binding.totalvalue.setText(price)
                }



            }
        })

        //thickness adapter
        thickness_list.add(ThicknessModel(thickness.toString()))
        productAdapter = ProductQuotationAdapter(thickness_list)
        binding.productQuotqtionRecycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        binding.productQuotqtionRecycler.adapter = productAdapter



        //Feet Adapter

        feet_list.add(FeetModel(size.toString()))
        feetAdapter = FeetAdapter(feet_list)
        binding.feetRecyclyer.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.feetRecyclyer.adapter = feetAdapter


        binding.productQuotationBackArrow.setOnClickListener {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            finish()
        }
    }




    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.product_quotation_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.search_bar -> startActivity(Intent(activity, SearchActivity::class.java))
            R.id.notification_bar -> startActivity(Intent(activity, Notifications::class.java))
            R.id.my_cart -> startActivity(Intent(activity, MyCart::class.java))
        }
        return super.onOptionsItemSelected(item)

    }
}