package com.yeungkc.gank.io.ui.chromium

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.yeungkc.gank.io.R

class ActionBroadcastReceiver : BroadcastReceiver() {
    companion object {
        const val KEY_ACTION_SOURCE = "ACTION_SOURCE"
        const val KEY_DES = "DES"

        const val ACTION_SHARE = 1
    }

    override fun onReceive(context: Context, intent: Intent) {
        val url = intent.dataString

        when (intent.getIntExtra(KEY_ACTION_SOURCE, -1)) {
            ACTION_SHARE -> {
                val des = intent.getStringExtra(KEY_DES)
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_TEXT, "$des:\n$url")

                val chooserIntent = Intent.createChooser(shareIntent, context.getString(R.string.share_title))
                chooserIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

                context.startActivity(chooserIntent)
            }
        }
    }
}