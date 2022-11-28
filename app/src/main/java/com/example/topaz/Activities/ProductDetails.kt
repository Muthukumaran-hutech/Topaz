package com.example.topaz.Activities

import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager

import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.topaz.Adapters.ProductImageSlider
import com.example.topaz.ApiModels.DiscountListApiModel
import com.example.topaz.ApiModels.ProductDetailsListApiModel
import com.example.topaz.ApiModels.ProductImageModel
import com.example.topaz.Interface.JsonPlaceholder
import com.example.topaz.Models.*
import com.example.topaz.R
import com.example.topaz.RetrofitApiInstance.UpdateAccountInfoInstance
import com.example.topaz.Utility.Util
import com.example.topaz.databinding.ActivityProductDetailsBinding
import com.google.firebase.database.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProductDetails : AppCompatActivity() {

    private lateinit var database1: DatabaseReference
    private lateinit var binding: ActivityProductDetailsBinding
    lateinit var activity: Activity
    val imageList = ArrayList<Bitmap>()
    var slidemodellist = ArrayList<SlideModel>()
    var productList = ArrayList<ProductDetailsModel>()
    var cartItemList = ArrayList<CartList>()
    var cartProductListItem = ArrayList<CartProductList>()
    var custId = ""
    var productId = ""
    var product = ProductDetailsListApiModel()
    lateinit var quatationRef:DatabaseReference
    lateinit var quatationProductRef:DatabaseReference
    var discountId="1"
    var productdiscount=""
    var actualprice="0"
    var productimagelist= ArrayList<String>()


    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreference = getSharedPreferences("CUSTOMER_DATA", Context.MODE_PRIVATE)
        custId = sharedPreference.getString("customercode", "").toString()

        val item = intent.getStringExtra("inner_sunid")
        val productname = intent.getStringExtra("product_name")
        productId = item!!

        binding.getAPrice.isEnabled = false
        binding.buyNow.isEnabled = false
        binding.addToCart.isEnabled = false

        activity = this
        binding.prodScrollView.isFillViewport = true
        setSupportActionBar(binding.prodDetailsToolbar)
        supportActionBar?.title = ""
        binding.textView9.text=productname


        binding.productBackArrow.setOnClickListener {
            //startActivity(Intent(activity, InnerCategories::class.java))
            finish()

        }

        binding.addToCart.setOnClickListener {
            database = FirebaseDatabase.getInstance().getReference("MyCart")
            database1 = FirebaseDatabase.getInstance().getReference("MyCartProducts")

            //Check if Cart exists, if exists then update the items to that cart else create a new cart item
            checkIfActiveCartExists(navigatetocartpage = false)

        }
        var rupees = getString(R.string.Rs) + binding.textView14.text + getString(R.string.slash)


       /* val decodedString: ByteArray = Base64.decode(
            product.Productimage2.imagepath,
            Base64.DEFAULT
        )
        val bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        imageList.add(bitmap)

        binding.productImage.setImageBitmap(bitmap)*/
        //  var imageDrawable = binding.productImage.drawable

        /*   Glide.with(context)
               .applyDefaultRequestOptions(RequestOptions().placeholder(R.drawable.ic_baseline_image_24).error(R.drawable.ic_baseline_image_24))
               .load(bitmap)
               .into(catImage)*/

        slidemodellist.add(SlideModel(R.drawable.ic_baseline_image_24,ScaleTypes.FIT))


        //-----------------Slide model list---------------------//

        //slidemodellist.add(SlideModel(imageDrawable, scaleType = ScaleTypes.FIT ))
        binding.productImageSlider.setImageList(slidemodellist)



        binding.appProgressBar3.visibility = View.VISIBLE


        onApiCallProductDetails()




        binding.whislistProdDetails.setOnClickListener {
            try {
                val detailsFirebaseModel = DetailsFirebaseModel(
                    custId = custId.toString(),
                    productImage = product.Productimage2.imagebyte,
                    productid = product.productid,
                    productTitle = product.productTitle,
                    thickness = product.thickness,
                    price = product.sqFeetPrice!!,
                    productDiscountId = productdiscount,
                    addedToWishList = true,
                    actualPrice = actualprice.toDouble()
                )

                //creating database and child to fire base
                database = FirebaseDatabase.getInstance().getReference("WhishList")
                database.child(custId.toString()).child(product.productid.toString())
                    .setValue(detailsFirebaseModel)

                Toast.makeText(
                    applicationContext, " Product Added To WishList",
                    Toast.LENGTH_LONG
                ).show()
                binding.whislistProdDetails.visibility = View.INVISIBLE
                binding.whislistProdDetails1.visibility = View.VISIBLE
            }
            catch (e:Exception){
                e.toString()
            }
        }

        binding.getAPrice.setOnClickListener {//Add quotation
            sendUserData()
            checkIfQuatationExists()
            val intent = Intent(activity, ProductQuotation::class.java)
           /* var productDetailsModel1 = ProductDetailsModel(
                product.productid,
                product.Productimage2.imagepath,
                product.productTitle,
                product.sqFeetPrice.toString(),
                product.size,
                product.thickness,
                product.brand,
                product.woodType,
                //productList.get(0).ProductImage
            )
            intent.putExtra("extra_item", productDetailsModel1)*/
            startActivity(intent)


        }

        binding.buyNow.setOnClickListener {
            database = FirebaseDatabase.getInstance().getReference("MyCart")
            database1 = FirebaseDatabase.getInstance().getReference("MyCartProducts")

            //Check if Cart exists, if exists then update the items to that cart else create a new cart item
            checkIfActiveCartExists(navigatetocartpage = true)

        }

        //To be implemented by using  firebase dynamic links for now implementing normal share functionality using Intents
        binding.shareProdDetails.setOnClickListener {
           /* product.productid
            product.Productimage2.imagepath
            product.productTitle
            product.price*/

            val shareintent=Intent(Intent.ACTION_SEND)
            shareintent.setType("text/plain")
            shareintent.putExtra(Intent.EXTRA_TEXT,"Hey, checkout this product on Topaz")
            Intent.createChooser(shareintent,"How do you want to share?")
            startActivity(shareintent)

        }

        quatationRef=FirebaseDatabase.getInstance().getReference("MyQuotation")
        quatationProductRef=FirebaseDatabase.getInstance().getReference("QuotationProductList")

        getProductImages()

    }

    private fun checkIfActiveCartExists(navigatetocartpage:Boolean) {
        try {
            val activecartquery = database.child(custId).orderByChild("cartActive").equalTo(true)
            activecartquery.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    try {

                        if (snapshot.exists()) {
                            for (snap: DataSnapshot in snapshot.children) {
                                Log.d(TAG, "datafound: " + snap)

                                val cart_id = snap.getValue(CartList::class.java)
                                cartProductListItem.add(
                                    CartProductList(
                                        product_title = product.productTitle,
                                        cartImage = product.Productimage2.imagebyte,
                                        price = product.sqFeetPrice.toString(),
                                        isActive = true,
                                        product_id = product.productid.toString(),
                                        cart_id = cart_id!!.cartId.toString(),
                                        quantity = "1",
                                        discount = productdiscount,
                                        discount_id = discountId.toInt(),
                                        size = product.size,
                                        actualPrice = actualprice,
                                        thickness = product.thickness
                                    )
                                )

                                checkIfProductAlreadyExists(cart_id!!.cartId,cartProductListItem.get(0).product_id,navigatetocartpage)

                                //Toast.makeText(this@ProductDetails,"Already  added to cart",Toast.LENGTH_LONG).show()

                            }
                        } else {
                            Log.d(TAG, "data not found: " + snapshot)
                            val cart_id = database.child(custId).push().key
                            val cartListItems = CartList(
                                custId = custId,
                                cartId = cart_id.toString(),
                                cartActive = true
                                //    product_list = cartProductListItem

                            )

                            cartProductListItem.add(
                                CartProductList(
                                    product_title = product.productTitle,
                                    cartImage = product.Productimage2.imagebyte,
                                    price = product.sqFeetPrice.toString(),
                                    isActive = true,
                                    product_id = product.productid.toString(),
                                    cart_id = cart_id.toString(),
                                    quantity = "1",
                                    discount = productdiscount,
                                    discount_id = discountId.toInt(),
                                    size = product.size,
                                    actualPrice = actualprice,
                                    thickness = product.thickness

                                )

                            )
                            //------------------------------------Cart creation------------------------------------------
                            database.child(custId.toString()).child(cart_id.toString())
                                .setValue(cartListItems)


                            //------------------------------------Cart insertion------------------------------------------
                            cartProductListItem.get(0).cart_id=cart_id.toString()
                            database1.child(cart_id.toString()).child("Products")
                                .child(cartProductListItem.get(0).product_id)
                                .setValue(cartProductListItem.get(0))//Inserting products into Cart

                            if (navigatetocartpage) {
                                startActivity(Intent(activity, MyCart::class.java))
                            }
                            Toast.makeText(
                                this@ProductDetails, "Product added to cart",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    } catch (e: Exception) {
                        e.toString()
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
        }
        catch (e:Exception){
            e.toString()
            Toast.makeText(this,"Something went wrong",Toast.LENGTH_LONG).show()
        }



    }

    private fun checkIfProductAlreadyExists(
        cart_id: String,
        productId: String,
        navigatetocartpage: Boolean
    ) {
        try{
            binding.appProgressBar3.visibility = View.VISIBLE
            val query=database1.child(cart_id).child("Products").child(productId)
            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    binding.appProgressBar3.visibility = View.GONE
                        if (snapshot.exists()) {

                            if (snapshot.value != null) {
                                if (navigatetocartpage) {
                                    startActivity(Intent(activity, MyCart::class.java))
                                }
                                var cartprodlist=snapshot.getValue(CartProductList::class.java)!!

                               if(cartprodlist.isActive){//Checking if product status is active and if the same product is added, then show a message that it is already added.
                                   Toast.makeText(this@ProductDetails,"Already added to the cart",Toast.LENGTH_LONG).show()
                               }
                                else{
                                   Toast.makeText(
                                       this@ProductDetails,
                                       "Added  to the cart",
                                       Toast.LENGTH_LONG
                                   ).show()
                                   database1.child(cart_id).child("Products")
                                       .child(cartProductListItem.get(0).product_id)
                                       .setValue(cartProductListItem.get(0))//
                               }
                            }


                        }



                        else  {//If new product is added for the first time
                            if (navigatetocartpage) {
                                startActivity(Intent(activity, MyCart::class.java))
                            }
                            database1.child(cart_id).child("Products")
                                .child(cartProductListItem.get(0).product_id)
                                .setValue(cartProductListItem.get(0))//
                            Toast.makeText(
                                this@ProductDetails,
                                "Added  to the cart",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                }

                override fun onCancelled(error: DatabaseError) {
                   Log.d("Product error","true")
                    binding.appProgressBar3.visibility = View.GONE
                }
            })


        }
        catch (e:Exception){
            e.toString()
            binding.appProgressBar3.visibility = View.GONE
        }
    }

    private fun sendUserData() {

    }

    private fun onApiCallProductDetails() {
        val res = UpdateAccountInfoInstance.getUpdateAccountInfoInstance()
            .create(JsonPlaceholder::class.java)

       //binding.productDetailsProgress.visibility= View.VISIBLE
        res.viewProduct(productId).enqueue(object : Callback<ProductDetailsListApiModel> {
            override fun onResponse(
                call: Call<ProductDetailsListApiModel>,
                response: Response<ProductDetailsListApiModel>
            ) {
                if (response.isSuccessful) {
                     binding.appProgressBar3.visibility = View.GONE
                       // binding.productDetailsProgress.visibility= View.GONE

                    product = response.body()!!

                    /*for (productlist in response.body()!!) {
                        var product = ProductDetailsModel(
                            productlist.productid,
                            productlist.Productimage2.imagebyte,
                            productlist.productTitle,
                            productlist.price.toString(),
                            productlist.size,
                            productlist.thickness,
                            productlist.brand,
                            productlist.woodType,
                            productlist.discription
                        )
                        productList.add(product)
                    }*/
                    //set detAILS...........
                    var rupees =
                        getString(R.string.Rs) + binding.textView14.text /*+ getString(R.string.slash)*/

                    binding.woodMaterialName.text = product?.productTitle+","+"\n"+"Thickness:"+" "+product?.thickness
                    binding.textView14.text = rupees + product?.sqFeetPrice.toString() + "/"
                    binding.productSpecificationSize.text = product?.size
                    binding.productSpecificationThickness.text = product?.thickness
                    binding.productSpecificationBrand.text = product?.brand
                    binding.productSpecificationWoodType.text = product?.woodType
                    binding.productSpecificationDesc.text = product?.discription
                    // Log.d(TAG, "onResponseProduct: " + response.body()?.get(0)?.discription)

                    binding.getAPrice.isEnabled = true
                    binding.buyNow.isEnabled = true
                    binding.addToCart.isEnabled = true
                    actualprice=product?.sqFeetPrice.toString()
                    checkIfProductIsAddedToWishlist()
                    getDiscountByProductId()

                } else {
                    val message = "SERVER ERROR "
                    AlertDialog.Builder(this@ProductDetails)
                        .setTitle("Something went wrong ")
                        .setMessage(message)
                        .setCancelable(false)
                        .setPositiveButton("OK") { _, _ ->
                            finish()

                            //  binding.phoneContainer.helperText = getString(R.id.Required)
                        }
                        .show()

                    //  Log.d(TAG, "OnFailure: " + response.body()?.get(0)?.discription)
                }
            }

            override fun onFailure(call: Call<ProductDetailsListApiModel>, t: Throwable) {
                // binding.appProgressBar3.visibility = View.VISIBLE
                Toast.makeText(
                    applicationContext, " Something Went Wrong Please Try Again Later",
                    Toast.LENGTH_LONG
                ).show()

            }

        })
    }


    /* override fun onBackPressed() {
         //super.onBackPressed()
         val message = "Are you sure yo want to exit"
         AlertDialog.Builder(this)
             .setTitle("Applcation will be logged out ")
             .setMessage(message)
             .setPositiveButton("OK") { _, _ ->
                 super.onBackPressed()

                 //  binding.phoneContainer.helperText = getString(R.id.Required)
             }.setNegativeButton("Cancel") { _, _ ->
                 //dismissDialog(0)
                 //  binding.phoneContainer.helperText = getString(R.id.Required)
             }
             .show()
     }*/

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.homepagemenu, menu)
        var menuitem=menu.findItem(R.id.my_cart)
        var actionview=menuitem.actionView
        var cartcount=actionview.findViewById<TextView>(R.id.cart_count)//Getting the textview reference from action layout defined for the menu item
        var cart=actionview.findViewById<ImageView>(R.id.cart_icon)

        var quoteitem = menu.findItem(R.id.quotationStatus)
        var quoteactionview= quoteitem.actionView
        var quotecount = quoteactionview.findViewById<TextView>(R.id.quotation_count)
        var quotationview = quoteactionview.findViewById<ImageView>(R.id.quotationstatus_icon)

        setupCartCount(cartcount, cart)
        setUpQuoteListCount(quotecount,quotationview)

        return true
    }

    private fun setupCartCount(cartcount: TextView?, cart: ImageView?) {
        cart?.setOnClickListener {
            startActivity(Intent(activity, MyCart::class.java))
        }


        Util.getCartCount(context = this,object : Util.CartCountListener {//Gets the cart count
        override fun getCartCount(cartsize: Int) {
            //Get the cart count entries
            Log.d("--Cart count--",cartsize.toString())
            if(cartsize==0) {
                cartcount?.visibility=View.GONE
            }else {
                cartcount?.visibility=View.VISIBLE
                cartcount?.text = cartsize.toString()
            }

        }
        })


    }


    private fun setUpQuoteListCount(quotecount: TextView?, quotationview: ImageView?) {
        quotationview?.setOnClickListener {
            startActivity(Intent(this,ProductQuotation::class.java))
        }


        Util.getQuoteListEntry(this,object : Util.QuotationCountListener {
            override fun getQuotationCount(quotationsize: Int) {

                if(quotationsize==0){
                    quotecount?.visibility= View.GONE
                }
                else{
                    quotecount?.visibility = View.VISIBLE
                    quotecount?.text  = quotationsize.toString()

                }

            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.search_bar -> startActivity(Intent(activity, SearchActivity::class.java))
            R.id.notification_bar -> startActivity(Intent(activity, Notifications::class.java))
            R.id.my_cart -> startActivity(Intent(activity, MyCart::class.java))
        }
        return super.onOptionsItemSelected(item)

    }

//Checking if product is already added to Wishlist
    fun checkIfProductIsAddedToWishlist(){
        database = FirebaseDatabase.getInstance().getReference("WhishList")
        var ref=database.child(custId.toString()).child(product.productid.toString())
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.value!=null) {
                    if (snapshot.exists()) {
                        val wishlistdata = snapshot.getValue(DetailsFirebaseModel::class.java)!!
                        Log.d("--Wishlistdata--",wishlistdata?.addedToWishList.toString())

                        if(wishlistdata.addedToWishList) {

                            binding.whislistProdDetails.visibility = View.INVISIBLE
                            binding.whislistProdDetails1.visibility = View.VISIBLE
                        }

                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
               Log.e("Wishlist error","true")
            }
        })

    }


    private fun getDiscountByProductId(){

        val discount = UpdateAccountInfoInstance.getUpdateAccountInfoInstance()
            .create(JsonPlaceholder::class.java)

        binding.productDetailsProgress.visibility= View.VISIBLE
        binding.textView17.visibility = View.VISIBLE
        binding.textView15.visibility = View.VISIBLE
        discount.getDiscountOnProduct(productId).enqueue(object : Callback<DiscountListApiModel?> {
            override fun onResponse(
                call: Call<DiscountListApiModel?>,
                response: Response<DiscountListApiModel?>
            ) {

              try {
                  if (response.isSuccessful) {
                      binding.productDetailsProgress.visibility= View.GONE
                      if (response.body() != null) {
                          Log.d("Discount", response.body()!!.discount!!)
                          response.body()?.let {
                              val discountListApiModel = it
                              binding.textView17.text = discountListApiModel?.discount + " " + "Off"
                              if (discountListApiModel.discount != null) {
                                  try {
                                      binding.textView15.text =
                                          getString(R.string.Rs) + product.sqFeetPrice!!.toString()
                                      val discount1 =
                                          Util.extractNumbersFromString(discountListApiModel.discount.toString(),"Percentage")//Extracts number from text

                                      val sqfeetprice =
                                          (product.sqFeetPrice!!.toInt() * discount1[0].toInt()) / 100

                                      if(sqfeetprice <product.sqFeetPrice!!.toInt()) {
                                          val discountedsqfeet =
                                              product.sqFeetPrice!!.toInt() - sqfeetprice
                                          // Log.d("--Discounted price--",(product.sqFeetPrice!!.toInt() - sqfeetprice).toString())
                                          product.sqFeetPrice = discountedsqfeet.toDouble()
                                      }
                                      else{
                                          val discountedsqfeet =
                                              sqfeetprice - product.sqFeetPrice!!.toInt()
                                          // Log.d("--Discounted price--",(product.sqFeetPrice!!.toInt() - sqfeetprice).toString())
                                          product.sqFeetPrice = discountedsqfeet.toDouble()
                                      }


                                      binding.textView14.text =
                                          getString(R.string.Rs) + product?.sqFeetPrice.toString() + "/"
                                      discountId=discountListApiModel.discountId.toString()
                                      productdiscount=discountListApiModel.discount!!
                                  } catch (e: Exception) {
                                      e.printStackTrace()
                                  }


                              }

                          }
                      } else {
                          binding.productDetailsProgress.visibility= View.GONE
                          binding.textView17.visibility = View.INVISIBLE
                          binding.textView15.visibility = View.INVISIBLE
                         // Toast.makeText(this@ProductDetails, "No data", Toast.LENGTH_LONG)
                      }
                  } else {
                      binding.productDetailsProgress.visibility= View.GONE
                      binding.textView17.visibility = View.INVISIBLE
                      binding.textView15.visibility = View.INVISIBLE
                      //Toast.makeText(this@ProductDetails, "No data", Toast.LENGTH_LONG).show()
                      Log.d("Discount Failed", "true")
                  }
              }
              catch (e:java.lang.Exception){
                  e.printStackTrace()
                  binding.productDetailsProgress.visibility= View.GONE
                  binding.textView17.visibility = View.INVISIBLE
                  binding.textView15.visibility = View.INVISIBLE
              }
            }

            override fun onFailure(call: Call<DiscountListApiModel?>, t: Throwable) {
                Log.d("Discount Failed","true")
                binding.productDetailsProgress.visibility= View.GONE
                binding.textView17.visibility = View.INVISIBLE
                binding.textView15.visibility = View.INVISIBLE
                Toast.makeText(this@ProductDetails,"Something went wrong:",Toast.LENGTH_LONG).show()
            }
        })
    }

    fun  initializeQuatationModel(){
        try{
            val quatationref=FirebaseDatabase.getInstance().getReference("MyQuotation")
            val quatationId=quatationref.child(custId).push().key.toString()
            val quatationDetailModel=QuatationDetailModel(
                quotationStatus = true,
                quotationId = quatationId,
                custId = custId
            )
            quatationref.child(custId).child(quatationId).setValue(quatationDetailModel)
            addQuatationList(quatationId = quatationDetailModel.quotationId)


        }
        catch (e:Exception){
            e.printStackTrace()
        }

    }

    fun addQuatationList(quatationId:String){
        try{
            val ref=quatationProductRef.child(quatationId).child("Products")
            ref.child(productId).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    if(!snapshot.exists()){//Only if product is not added earlier, then add the product
                        ref.child(productId).setValue(prepareQuatationModel())
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                }
            })


            Toast.makeText(this,"Added successfully",Toast.LENGTH_LONG).show()
        }
        catch (e:java.lang.Exception){
            e.printStackTrace()
        }
    }


    fun checkIfQuatationExists(){
        try{
            val qref=quatationRef.child(custId).orderByChild("quotationStatus").equalTo(true)
            qref.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        for(snap in snapshot.children){

                            val quatationDetailModel=snap.getValue(QuatationDetailModel::class.java)!!
                            Log.d("Quotation detail",quatationDetailModel.quotationId)

                            addQuatationList(quatationId = quatationDetailModel.quotationId)


                        }
                    }

                    else{
                        initializeQuatationModel()
                        Log.d("QDD Failure","true")
                    }

                }

                override fun onCancelled(error: DatabaseError) {

                Log.d("Quotation failure","true")
                }
            })


        }
        catch (e:java.lang.Exception){
            e.printStackTrace()
        }

    }

    fun prepareQuatationModel():QuatationListModel{
        try {
            val productDetailsModel1 = QuatationListModel(
                product.productid,
                product.Productimage2.imagebyte,
                product.productTitle,
                product.sqFeetPrice.toString(),
                product.size,
                product.thickness,
                product.brand,
                product.woodType,
                "",
                quantity = "1",
                totalvalue = product.sqFeetPrice.toString(),
                expanded = false,
                discountId = discountId
                //productList.get(0).ProductImage
            )

            return productDetailsModel1
        }
        catch (e:Exception){
            e.toString()
        }

        return QuatationListModel()

    }


    fun getProductImages(){
        try{
            val imagelist = UpdateAccountInfoInstance.getUpdateAccountInfoInstance()
                .create(JsonPlaceholder::class.java)

            imagelist.getProductImages(productId).enqueue(object : Callback<List<ProductImageModel>?> {
                override fun onResponse(
                    call: Call<List<ProductImageModel>?>,
                    response: Response<List<ProductImageModel>?>
                ) {
                    if(response.isSuccessful){
                        for(productimagedata in response.body()!! ){
                            Log.d("Data","true")
                            productimagelist.add(productimagedata.imagebyte!!)
                        }

                        initializeImageSliderList()
                    }
                    else{
                        Log.d("No data","true")

                    }
                }
                override fun onFailure(call: Call<List<ProductImageModel>?>, t: Throwable) {
                   Log.e("Product image failure","true")
                }
            })



        }
        catch (e:Exception){
            e.toString()
        }
    }

    private fun initializeImageSliderList() {
        try{
            var imageSlider=ProductImageSlider(this,productimagelist)
            binding.viewpagerData.adapter=imageSlider
            if(productimagelist.size > 0) {
                binding.indicatorData.visibility= View.VISIBLE
                binding.indicatorData.setIndicatorCount(productimagelist.size)
                binding.indicatorData.selectCurrentPosition(0)
            }
            else{
                binding.indicatorData.visibility= View.INVISIBLE
            }
            binding.viewpagerData.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    //Log.d("Page chnaged","true")
                }

                override fun onPageSelected(position: Int) {
                    binding.indicatorData.selectCurrentPosition(position)
                    Log.d("Page chnaged","true")
                }

                override fun onPageScrollStateChanged(state: Int) {
                    //Log.d("Page chnaged","true")
                }
            })



        }
        catch (e:Exception){
            e.toString()
        }
    }


}