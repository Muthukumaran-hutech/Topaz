package com.example.topaz.Activities

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.hardware.camera2.params.BlackLevelPattern
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Toast
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.topaz.Adapters.CategoryAdapter
import com.example.topaz.Adapters.MyCartAdapter
import com.example.topaz.Adapters.SearchViewAdapter
import com.example.topaz.ApiModels.ProductDetailsListApiModel
import com.example.topaz.Interface.JsonPlaceholder
import com.example.topaz.Interface.SearchListPageItemClickListner
import com.example.topaz.Models.CategoriesModel
import com.example.topaz.Models.SearchListModel
import com.example.topaz.R
import com.example.topaz.RetrofitApiInstance.UpdateAccountInfoInstance
import com.example.topaz.databinding.ActivityAccountInformationBinding
import com.example.topaz.databinding.ActivitySearchBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class
SearchActivity : AppCompatActivity(), SearchListPageItemClickListner {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var searchAdapter: SearchViewAdapter
    lateinit var activity: Activity
    var list = ArrayList<String>()
    var searchInnerlist = ArrayList<SearchListModel>()
    var filteredlist=ArrayList<SearchListModel>().toList()
    private val SPEECH_REQUEST_CODE = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.searchProduct.visibility = View.VISIBLE

        activity = this
       /* searchInnerlist.add(SearchListModel("","veneers"))
        searchInnerlist.add(SearchListModel("","Plywood"))
        searchInnerlist.add(SearchListModel("","waterproof Plywood"))
        searchInnerlist.add(SearchListModel("","Face veneers"))*/
        //searchAdapter = SearchViewAdapter(searchInnerlist, this)

        binding.userSearchList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//        binding.userSearchList.adapter = searchAdapter
        binding.userSearchList.setHasFixedSize(true)

       /* binding.wrong.setOnClickListener {
            binding.userSearchList.clearFocus()
        }*/

        binding.searchBackArrow.setOnClickListener{
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            finish()
        }
        displaySpeechRecognizer()
        getUserData()

        getProductList()
        //val names = arrayOf("HardwoodPlywood", "Plywood", "veneers", "Faceveneers", "waterproof")

        binding.searchEdit.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchEdit.clearFocus()
               /* var filteredlist=searchInnerlist.filter { s-> s.SearchText.contains(query.toString()) }
                searchAdapter= SearchViewAdapter(filteredlist as ArrayList<SearchListModel>,this@SearchActivity)
                binding.userSearchList.adapter = searchAdapter

*/

                Toast.makeText(applicationContext, " checking data$query",Toast. LENGTH_SHORT).show()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                if (newText?.length==0){
                    searchAdapter.clearList()
                    binding.searchProduct.visibility = View.VISIBLE
                    binding.userSearchList.visibility= View.GONE
                    binding.searchResult.text="0"+" "+"Result"

                }

                else{
                    binding.searchProduct.visibility = View.GONE
                    binding.userSearchList.visibility = View.VISIBLE
                    binding.userSearchList.visibility= View.VISIBLE
                    filteredlist=searchInnerlist.filter { s-> (s.SearchText.lowercase().contains(newText.toString().lowercase()) || s.CategoryName.lowercase().contains(newText.toString().lowercase())) }
                    searchAdapter= SearchViewAdapter(filteredlist as ArrayList<SearchListModel> ,this@SearchActivity)
                    binding.userSearchList.adapter = searchAdapter
                   binding.searchResult.text=filteredlist.toMutableList().size.toString() +" "+"Results"
                    if (filteredlist.size==0){
                        binding.productNotFound.visibility = View.VISIBLE
                        binding.userSearchList.visibility = View.GONE
                        //binding.searchResult.visibility = View.VISIBLE
                    }else{
                        binding.productNotFound.visibility = View.GONE
                    }
                }

              //  binding.searchProduct.visibility = View.GONE

                return true
            }
        })



        binding.searchBackHome.setOnClickListener {
            startActivity(Intent(activity, HomeScreen::class.java))
            finish()
        }
        binding.userSearchList.layoutManager = LinearLayoutManager(this)
        var searchViewAdapter = SearchViewAdapter(searchInnerlist, this@SearchActivity)
        binding.userSearchList.adapter = searchViewAdapter
        binding.userSearchList.setHasFixedSize(true)



    }

    private fun getProductList() {
        binding.searchListProgress.visibility= View.VISIBLE
        var res = UpdateAccountInfoInstance.getUpdateAccountInfoInstance()
            .create(JsonPlaceholder::class.java)
        res.viewProductList().enqueue(object : Callback<List<ProductDetailsListApiModel>?> {
            override fun onResponse(
                call: Call<List<ProductDetailsListApiModel>?>,
                response: Response<List<ProductDetailsListApiModel>?>
            ) {
                binding.searchListProgress.visibility= View.GONE
                if(response.isSuccessful){
                    for(productlist in response.body()!!){
                        var searchmodel=SearchListModel(
                            SearchText = productlist.productTitle,
                            ProductId = productlist.productid.toString(),
                            CategoryName = productlist.categoryType.categoryName

                        )

                        searchInnerlist.add(searchmodel)
                    }



                }
                else{
                    binding.searchListProgress.visibility= View.GONE
                }
                Log.d("Product list res","success")
            }

            override fun onFailure(call: Call<List<ProductDetailsListApiModel>?>, t: Throwable) {
                Log.d("Product list res","success")
                binding.searchListProgress.visibility= View.GONE
            }
        })

    }

    //Speech recognition for text
    private fun displaySpeechRecognizer() {
        binding.searchMic.setOnClickListener {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            }
            // This starts the activity and populates the intent with the speech text.
            startActivityForResult(intent, SPEECH_REQUEST_CODE)
        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val spokenText: String? =
                data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).let { results ->
                    results!![0]
                }
            binding.searchEdit.setQuery(spokenText.toString(),true)
            // Do something with spokenText.
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
    // This callback is invoked when the Speech Recognizer returns.
// This is where you process the intent and extract the speech text from the intent.


    override fun SearchListPageItemClickListner(data: SearchListModel) {
        try {
            val intent = Intent(activity, ProductDetails::class.java)
            intent.putExtra("inner_sunid", data.ProductId)
            intent.putExtra("product_name", data.SearchText)
            startActivity(intent)
        }
        catch (e:Exception){
            e.toString()
        }

    }


    private fun getUserData() {


    }

    /*override fun onBackPressed() {
        //super.onBackPressed()
        val message = "Are you sure yo want to exit"
        AlertDialog.Builder(this)
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
}