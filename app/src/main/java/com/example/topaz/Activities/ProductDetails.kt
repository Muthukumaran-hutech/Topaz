package com.example.topaz.Activities

import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.opengl.Visibility
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.topaz.ApiModels.ProductDetailsListApiModel
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


    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreference = getSharedPreferences("CUSTOMER_DATA", Context.MODE_PRIVATE)
        custId = sharedPreference.getString("customercode", "").toString()

        val item = intent.getStringExtra("inner_sunid")
        var productname = intent.getStringExtra("product_name")
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
            checkIfActiveCartExists()

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

        slidemodellist.add(SlideModel(R.drawable.ic_baseline_image_24))


        //-----------------Slide model list---------------------//

        //slidemodellist.add(SlideModel(imageDrawable, scaleType = ScaleTypes.FIT ))
        binding.productImageSlider.setImageList(slidemodellist)

        binding.appProgressBar3.visibility = View.VISIBLE


        onApiCallProductDetails()




        binding.whislistProdDetails.setOnClickListener {
            var detailsFirebaseModel = DetailsFirebaseModel(
                custId = custId.toString(),
                productImage = product.Productimage2.imagebyte,
                productid = product.productid,
                productTitle = product.productTitle,
                thickness = product.thickness,
                price = product.price,
                productDiscountId = "",
                addedToWishList = true
            )

            //creating database and child to fire base
            database = FirebaseDatabase.getInstance().getReference("WhishList")
            database.child(custId.toString()).child(product.productid.toString())
                .setValue(detailsFirebaseModel)

            Toast.makeText(
                applicationContext, " Product Added To WishList",
                Toast.LENGTH_LONG
            ).show()
            binding.whislistProdDetails.visibility=View.INVISIBLE
            binding.whislistProdDetails1.visibility=View.VISIBLE
        }

        binding.getAPrice.setOnClickListener {
            sendUserData()
            var intent = Intent(activity, ProductQuotation::class.java)
            var productDetailsModel1 = ProductDetailsModel(
                product.productid,
                product.Productimage2.imagepath,
                product.productTitle,
                product.price.toString(),
                product.size,
                product.thickness,
                product.brand,
                product.woodType,
                //productList.get(0).ProductImage
            )
            intent.putExtra("extra_item", productDetailsModel1)
            startActivity(intent)
        }

        binding.buyNow.setOnClickListener {
            startActivity(Intent(activity, MyCart::class.java))
        }
        binding.shareProdDetails.setOnClickListener {
            product.productid
            product.Productimage2.imagepath
            product.productTitle
            product.price
        }
    }

    private fun checkIfActiveCartExists() {
        val activecartquery = database.child(custId).orderByChild("cartActive").equalTo(true)
        activecartquery.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {
                    for (snap: DataSnapshot in snapshot.children) {
                        Log.d(TAG, "datafound: " + snap)
                        val cart_id = snap.getValue(CartList::class.java)
                        cartProductListItem.add(
                            CartProductList(
                                product_title = product.productTitle,
                                cartImage = product.Productimage2.imagepath,
                                price = product.price.toString(),
                                isActive = true,
                                product_id = product.productid.toString(),
                                cart_id = cart_id!!.cartId.toString()
                            )
                        )
                        database1.child(cart_id!!.cartId).child("Products")
                            .child(cartProductListItem.get(0).product_id)
                            .setValue(cartProductListItem.get(0))//

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
                            cartImage = product.Productimage2.imagepath,
                            price = product.price.toString(),
                            isActive = true,
                            product_id = product.productid.toString(),
                            cart_id = cart_id.toString()
                        )
                    )
                    //------------------------------------Cart creation------------------------------------------
                    database.child(custId.toString()).child(cart_id.toString())
                        .setValue(cartListItems)


                    //------------------------------------Cart insertion------------------------------------------
                    database1.child(cart_id.toString()).child("Products")
                        .child(cartProductListItem.get(0).product_id)
                        .setValue(cartProductListItem.get(0))//Inserting products into Cart

                    Toast.makeText(
                        this@ProductDetails, " Product Added To MyCart",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun sendUserData() {

    }

    private fun onApiCallProductDetails() {
        var res = UpdateAccountInfoInstance.getUpdateAccountInfoInstance()
            .create(JsonPlaceholder::class.java)


        res.viewProduct(productId).enqueue(object : Callback<ProductDetailsListApiModel> {
            override fun onResponse(
                call: Call<ProductDetailsListApiModel>,
                response: Response<ProductDetailsListApiModel>
            ) {
                if (response.isSuccessful) {
                    binding.appProgressBar3.visibility = View.GONE

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

                    binding.woodMaterialName.text = product?.productTitle
                    binding.textView14.text = rupees + product?.price + "/"
                    binding.productSpecificationSize.text = product?.size
                    binding.productSpecificationThickness.text = product?.thickness
                    binding.productSpecificationBrand.text = product?.brand
                    binding.productSpecificationWoodType.text = product?.woodType
                    binding.productSpecificationDesc.text = product?.discription
                    // Log.d(TAG, "onResponseProduct: " + response.body()?.get(0)?.discription)

                    binding.getAPrice.isEnabled = true
                    binding.buyNow.isEnabled = true
                    binding.addToCart.isEnabled = true
                    checkIfProductIsAddedToWishlist()

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
        setupCartCount(cartcount, cart)

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.search_bar -> startActivity(Intent(activity, SearchActivity::class.java))
            R.id.notification_bar -> startActivity(Intent(activity, Notifications::class.java))
            R.id.my_cart -> startActivity(Intent(activity, MyCart::class.java))
        }
        return super.onOptionsItemSelected(item)

    }


    fun checkIfProductIsAddedToWishlist(){
        database = FirebaseDatabase.getInstance().getReference("WhishList")
        var ref=database.child(custId.toString()).child(product.productid.toString())
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                 binding.whislistProdDetails.visibility=View.INVISIBLE
                 binding.whislistProdDetails1.visibility=View.VISIBLE

                }
            }

            override fun onCancelled(error: DatabaseError) {
               Log.e("Wishlist error","true")
            }
        })

    }


}