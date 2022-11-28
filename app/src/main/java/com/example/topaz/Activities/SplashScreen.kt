package com.example.topaz.Activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import com.example.topaz.Fragments.SliderFragment
import com.example.topaz.R

class SplashScreen : AppCompatActivity() {

    private var topazImage: ImageView? = null
    lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        checkForInternet(this)

        topazImage = findViewById(R.id.topaz)
        handler = Handler()
        handler.postDelayed({
            val sharedPreference =  getSharedPreferences("CUSTOMER_DATA", Context.MODE_PRIVATE)

           if( sharedPreference.getString("isUserLoggedIn","").toString()=="true") {

               val intent = Intent(this, HomeScreen::class.java)
               startActivity(intent)
               finish()

           }
            else{

              /* val intent = Intent(this, OtpVerfification::class.java)
               startActivity(intent)
               finish()*/
               val intent = Intent(this, IntroSlider::class.java)
               startActivity(intent)
               finish()
           }
        },3000)  //Delaying 3 seconds to open IntroPage


    }


    private fun checkForInternet(context: Context): Boolean {

        // register activity with the connectivity manager service
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // if the android version is equal to M
        // or greater we need to use the
        // NetworkCapabilities to check what type of
        // network has the internet connection
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // Returns a Network object corresponding to
            // the currently active default data network.
            val network = connectivityManager.activeNetwork ?: return false

            // Representation of the capabilities of an active network.
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                // Indicates this network uses a Wi-Fi transport,
                // or WiFi has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

                // Indicates this network uses a Cellular transport. or
                // Cellular has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                // else return false
                else -> false
            }
        } else {
            // if the android version is below M
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }
}