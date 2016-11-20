package com.yeungkc.gank.io.ui.chromium

import android.support.customtabs.CustomTabsClient

interface ServiceConnectionCallback {
    /**
     * Called when the service is connected.
     * @param client a CustomTabsClient
     */
    fun onServiceConnected(client: CustomTabsClient)

    /**
     * Called when the service is disconnected.
     */
    fun onServiceDisconnected()
}