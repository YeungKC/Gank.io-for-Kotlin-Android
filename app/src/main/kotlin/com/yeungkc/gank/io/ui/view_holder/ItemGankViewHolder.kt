package com.yeungkc.gank.io.ui.view_holder

import android.app.Activity
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Intent
import android.net.Uri
import android.support.customtabs.CustomTabsIntent
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.ViewGroup
import com.yeungkc.gank.io.R
import com.yeungkc.gank.io.extensions.getBitmap
import com.yeungkc.gank.io.model.bean.AutoBean
import com.yeungkc.gank.io.model.bean.Result
import com.yeungkc.gank.io.ui.chromium.ActionBroadcastReceiver
import com.yeungkc.gank.io.ui.chromium.CustomTabsHelper
import kotlinx.android.synthetic.main.item_gank.view.*
import java.text.SimpleDateFormat

class ItemGankViewHolder(parent: ViewGroup) : BaseViewHolder<AutoBean>(LayoutInflater.from(parent.context).inflate(R.layout.item_gank, parent, false)) {
    var data: Result? = null


    init {
        itemView.setOnClickListener {
            data?.run {
                val url = url

                val share = context.getString(R.string.share)
                val icon = context.getBitmap(R.drawable.ic_share)
                val pendingIntent = createPendingIntent(ActionBroadcastReceiver.ACTION_SHARE, desc)

                val intentBuilder = CustomTabsIntent.Builder()
                        .setShowTitle(true)
                        .setActionButton(icon, share, pendingIntent, true)
                        .setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary))
                        .enableUrlBarHiding()

                val packageNameToUse = CustomTabsHelper.getPackageNameToUse(context)
                val build = intentBuilder.build()
                build.intent.`package` = packageNameToUse
                build.launchUrl(context as Activity, Uri.parse(url))
            }
        }
    }


    override fun bind(data: AutoBean) {
        if (data !is Result) return

        this.data = data

        val des = data.desc?.trim()

        itemView.item_title.text = des
        itemView.item_who.text = itemView.context.getString(R.string.by, data.who?.trim())
        itemView.item_date.text = SimpleDateFormat("yy/MM/dd").format(data.publishedAt)
    }

    private fun createPendingIntent(actionSourceId: Int, des: String?): PendingIntent {
        val actionIntent = Intent(context.applicationContext, ActionBroadcastReceiver::class.java)
        actionIntent.putExtra(ActionBroadcastReceiver.KEY_ACTION_SOURCE, actionSourceId)
        actionIntent.putExtra(ActionBroadcastReceiver.KEY_DES, des)
        return PendingIntent.getBroadcast(
                context.applicationContext, actionSourceId, actionIntent, FLAG_UPDATE_CURRENT)
    }
}