package com.example.topaz.Activities

import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.topaz.ApiModels.ProductDetailsListApiModel
import com.example.topaz.Interface.JsonPlaceholder
import com.example.topaz.Models.*
import com.example.topaz.R
import com.example.topaz.RetrofitApiInstance.UpdateAccountInfoInstance
import com.example.topaz.databinding.ActivityProductDetailsBinding
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_account_information.*
import kotlinx.android.synthetic.main.activity_my_orders.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProductDetails : AppCompatActivity() {

    private lateinit var database1: DatabaseReference
    private lateinit var binding: ActivityProductDetailsBinding
    lateinit var activity: Activity
    val imageList = ArrayList<Int>()
    var slidemodellist = ArrayList<SlideModel>()
    var productList = ArrayList<ProductDetailsModel>()
    var cartItemList = ArrayList<CartList>()
    var cartProductListItem = ArrayList<CartProductList>()
    var custId = ""


    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreference = getSharedPreferences("CUSTOMER_DATA", Context.MODE_PRIVATE)
        custId = sharedPreference.getString("customercode", "").toString()

        activity = this
        binding.prodScrollView.isFillViewport = true
        setSupportActionBar(binding.prodDetailsToolbar)
        supportActionBar?.title = ""

        binding.productBackArrow.setOnClickListener {
            startActivity(Intent(activity, InnerCategories::class.java))

        }

        binding.addToCart.setOnClickListener {
            database = FirebaseDatabase.getInstance().getReference("MyCart")
            database1 = FirebaseDatabase.getInstance().getReference("MyCartProducts")

            //Check if Cart exists, if exists then update the items to that cart else create a new cart item
            checkIfActiveCartExists()

        }
        var rupees = getString(R.string.Rs) + binding.textView14.text + getString(R.string.slash)

        imageList.add(R.drawable.home_slider_banner)
        imageList.add(R.drawable.home_slider_banner_2)


        //-----------------Slide model list---------------------//
        slidemodellist.add(SlideModel(imagePath = R.drawable.rectangle_2544))
        slidemodellist.add(SlideModel(imagePath = R.drawable.rectangle_2544))
        slidemodellist.add(SlideModel(imagePath = R.drawable.rectangle_2544))
        slidemodellist.add(SlideModel(imagePath = R.drawable.rectangle_2544))
        slidemodellist.add(SlideModel(imagePath = R.drawable.rectangle_2544))
        slidemodellist.add(SlideModel(imagePath = R.drawable.rectangle_2544))

        binding.productImageSlider.setImageList(slidemodellist, ScaleTypes.FIT)

        binding.appProgressBar3.visibility = View.VISIBLE

        onApiCallProductDetails()




        binding.whislistProdDetails.setOnClickListener {
            var detailsFirebaseModel = DetailsFirebaseModel(
                custId = custId.toString(),
                productImage = "",
                productid = productList.get(0).ProductId.toInt(),
                productTitle = productList.get(0).ProductTitle.toString(),
                thickness = productList.get(0).ProductThickness,
                price = productList.get(0).ProductPrice.toInt(),
                productDiscountId = "",
                addedToWishList = true
            )

            //creating database and child to fire base
            database = FirebaseDatabase.getInstance().getReference("WhishList")
            database.child(custId.toString()).child(productList.get(0).ProductId.toInt().toString())
                .setValue(detailsFirebaseModel)

            Toast.makeText(
                applicationContext, " Product Added To WishList",
                Toast.LENGTH_LONG
            ).show()
        }

        binding.getAPrice.setOnClickListener {
            sendUserData()
            var intent = Intent(activity, ProductQuotation::class.java)
            var productDetailsModel1 = ProductDetailsModel(
                productList.get(0).ProductId,
                "",
                productList.get(0).ProductTitle,
                productList.get(0).ProductPrice,
                productList.get(0).ProductSize,
                productList.get(0).ProductThickness,
                productList.get(0).ProductBrand,
                productList.get(0).ProductWoodType,
                //productList.get(0).ProductImage
            )
            intent.putExtra("extra_item", productDetailsModel1)
            startActivity(intent)
        }

        binding.buyNow.setOnClickListener {
            startActivity(Intent(activity, MyCart::class.java))
        }
        binding.shareProdDetails.setOnClickListener {
            Toast.makeText(
                applicationContext,
                "Will be placed with Google Playstore Link",
                Toast.LENGTH_SHORT
            )
                .show()
            //should pass the google playstorelink
            /*ShareCompat.IntentBuilder.from(activity)
                .setType("text/plain")
                .setChooserTitle("Chooser title")
                .setText("http://play.google.com/store/apps/details?id=" + activity.getPackageName())
                .startChooser();*/
        }
    }

    private fun checkIfActiveCartExists() {
        var activecartquery = database.child(custId).orderByChild("cartActive").equalTo(true)
        activecartquery.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {
                    for (snap: DataSnapshot in snapshot.children) {
                        Log.d(TAG, "datafound: " + snap)
                        val cart_id = snap.getValue(CartList ::class.java)
                        cartProductListItem.add(
                            CartProductList(
                                product_title = productList.get(0).ProductTitle,
                                cartImage = "",
                                price = productList.get(0).ProductPrice,
                                isActive = true,
                                product_id = productList.get(0).ProductId.toString(),
                                cart_id = cart_id!!.cartId.toString()
                            )
                        )
                        database1.child(cart_id!!.cartId).child("Products")
                            .child(cartProductListItem.get(0).product_id)
                            .setValue(cartProductListItem.get(0))//

                    }
                } else {
                    Log.d(TAG, "data not found: " + snapshot)
                    var cart_id = database.child(custId).push().key
                    var cartListItems = CartList(
                        custId = custId,
                        cartId = cart_id.toString(),
                        cartActive = true
                        //    product_list = cartProductListItem

                    )
                    cartProductListItem.add(
                        CartProductList(
                            product_title = productList.get(0).ProductTitle,
                            cartImage = "",
                            price = productList.get(0).ProductPrice,
                            isActive = true,
                            product_id = productList.get(0).ProductId.toString(),
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
                        applicationContext, " Product Added To MyCart",
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


        res.viewProduct().enqueue(object : Callback<List<ProductDetailsListApiModel>?> {
            override fun onResponse(
                call: Call<List<ProductDetailsListApiModel>?>,
                response: Response<List<ProductDetailsListApiModel>?>
            ) {
                if (response.isSuccessful) {
                    binding.appProgressBar3.visibility = View.GONE

                    for (productlist in response.body()!!) {
                        var product = ProductDetailsModel(
                            productlist.productid,
                            "",
                            productlist.productTitle,
                            productlist.price.toString(),
                            productlist.size,
                            productlist.thickness,
                            productlist.brand,
                            productlist.woodType,
                            productlist.discription
                        )
                        productList.add(product)
                    }
                    //set detAILS...........
                    var rupees =
                        getString(R.string.Rs) + binding.textView14.text /*+ getString(R.string.slash)*/

                    binding.woodMaterialName.text = productList.get(0).ProductTitle
                    binding.textView14.text = rupees + productList.get(0).ProductPrice + "/"
                    binding.productSpecificationSize.text = productList.get(0).ProductSize
                    binding.productSpecificationThickness.text = productList.get(0).ProductThickness
                    binding.productSpecificationBrand.text = productList.get(0).ProductBrand
                    binding.productSpecificationWoodType.text = productList.get(0).ProductWoodType
                    binding.productSpecificationDesc.text = productList.get(0).ProductDescription
                    Log.d(TAG, "onResponseProduct: " + response.body()?.get(0)?.discription)
                } else {
                    Log.d(TAG, "OnFailure: " + response.body()?.get(0)?.discription)
                }
            }

            override fun onFailure(call: Call<List<ProductDetailsListApiModel>?>, t: Throwable) {
                binding.appProgressBar3.visibility = View.VISIBLE
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
        inflater.inflate(R.menu.prod_page_menu, menu)
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