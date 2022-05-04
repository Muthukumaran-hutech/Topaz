package com.example.topaz.Activities

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.hardware.camera2.params.BlackLevelPattern
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
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
import com.example.topaz.Interface.SearchListPageItemClickListner
import com.example.topaz.Models.CategoriesModel
import com.example.topaz.Models.SearchListModel
import com.example.topaz.R
import com.example.topaz.databinding.ActivityAccountInformationBinding
import com.example.topaz.databinding.ActivitySearchBinding
import java.util.*
import kotlin.collections.ArrayList

class
SearchActivity : AppCompatActivity(), SearchListPageItemClickListner {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var searchAdapter: SearchViewAdapter
    lateinit var activity: Activity
    var list = ArrayList<String>()
    var searchInnerlist = java.util.ArrayList<SearchListModel>()
    private val SPEECH_REQUEST_CODE = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.searchProduct.visibility = View.VISIBLE

        activity = this
        searchInnerlist.add(SearchListModel("","veneers"))
        searchInnerlist.add(SearchListModel("","Plywood"))
        searchInnerlist.add(SearchListModel("","waterproof Plywood"))
        searchInnerlist.add(SearchListModel("","Face veneers"))
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
                    binding.searchProduct.visibility = View.VISIBLE
                }

                else{
                    binding.searchProduct.visibility = View.GONE
                    binding.userSearchList.visibility = View.VISIBLE
                    var filteredlist=searchInnerlist.filter { s-> s.SearchText.lowercase().contains(newText.toString().lowercase()) }
                    searchAdapter= SearchViewAdapter(filteredlist as ArrayList<SearchListModel>,this@SearchActivity)
                    binding.userSearchList.adapter = searchAdapter
                    binding.searchResult.text = filteredlist.toString()
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


    override fun SearchListPageItemClickListner(search: SearchListModel) {


    }


    private fun getUserData() {


    }

    override fun onBackPressed() {
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
    }
}