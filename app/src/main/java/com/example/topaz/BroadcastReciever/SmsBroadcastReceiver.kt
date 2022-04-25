/*
package com.example.topaz.BroadcastReciever

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status


public class SmsBroadcastReceiver : BroadcastReceiver() {

    var smsBroadcastRecieverListener: smsBroadcasrRecieverListener? = null

    override fun onReceive(context: Context?, intent: Intent?) {
        if (SmsRetriever.SMS_RETRIEVED_ACTION == intent?.action) {

            val extras = intent.extras
            val SmsRetrieverStatus = extras?.get(SmsRetriever.EXTRA_STATUS) as Status

            when (SmsRetrieverStatus.statusCode) {
                CommonStatusCodes.SUCCESS -> {
                    val consentIntent = extras.getParcelable<Intent>(SmsRetriever.EXTRA_CONSENT_INTENT)
                    smsBroadcastRecieverListener?.onSuccess(consentIntent)
                }
                CommonStatusCodes.TIMEOUT -> {
                    smsBroadcastRecieverListener?.onFailure()
                }
            }
        }
    }


    interface smsBroadcasrRecieverListener {
        fun onSuccess(intent: Intent?)
        fun onFailure()
    }
}*/
