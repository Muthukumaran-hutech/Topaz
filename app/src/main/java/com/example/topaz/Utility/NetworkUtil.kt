package com.example.topaz.Utility

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

object NetworkUtil {

    fun checkInternet(context:Context): Boolean {
        try {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                // Returns a Network object corresponding to
                // the currently active default data network.
                val network = connectivityManager.activeNetwork?: return false

                // Representation of the capabilities of an active network.
                val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

                return when {
                    // Indicates this network uses a Wi-Fi transport,
                    // or WiFi has network connectivity
                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ->  true

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
        catch (e:Exception){
            e.toString()
        }



        return false


    }

    fun showNoNetworkDialog(context: Context,activity: Activity){
        try{
            val alertDialogBuilder=AlertDialog.Builder(context)
            alertDialogBuilder.setTitle("No Connection")
            alertDialogBuilder.setMessage("Please check your connection")
            alertDialogBuilder.setPositiveButton("Okay",object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {

                    activity.finish()
                }
            })

            val alertdialog=alertDialogBuilder.create()
            alertdialog.setCancelable(false)
            alertdialog.show()



        }
        catch (e:Exception){
            e.toString()
        }
    }
}